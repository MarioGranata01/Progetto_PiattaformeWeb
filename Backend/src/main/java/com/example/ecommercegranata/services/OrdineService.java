package com.example.ecommercegranata.services;
import com.example.ecommercegranata.dto.OrdineDto;
import com.example.ecommercegranata.dto.ProdottiCarrelloDto;
import com.example.ecommercegranata.dto.ProdottiOrdineDto;
import com.example.ecommercegranata.dto.ProdottoDto;
import com.example.ecommercegranata.entities.*;
import com.example.ecommercegranata.exception.AppException;
import com.example.ecommercegranata.repository.OrdineRepository;
import com.example.ecommercegranata.repository.ProdottoRepository;
import com.example.ecommercegranata.repository.UtenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrdineService {
    private final OrdineRepository ordineRepository;
    private final UtenteRepository utenteRepository;
    private final ProdottoRepository prodottoRepository;
    private final CarrelloService carrelloService;

    public List<OrdineDto> getOrdersByUserId(int idUtente, Authentication authentication) {
        Utente utente = utenteRepository.findById(idUtente).orElseThrow(() -> new AppException("Utente non trovato.", HttpStatus.NOT_FOUND));
        List<Ordine> ordini = ordineRepository.findAllByUtenteId(idUtente);
        List<OrdineDto> ordineDtos = new ArrayList<>();
        for (Ordine ordine : ordini) {
            OrdineDto ordineDto = new OrdineDto();
            ordineDto.setId(ordine.getId());
            ordineDto.setNomeProdotto(ordine.getNomeProdotto());
            ordineDto.setPrezzoTotale(ordine.getPrezzoTotale());
            ordineDto.setDataOrdine(ordine.getDataOrdine());
            List<ProdottiOrdineDto> prodottiOrdineDtos = new ArrayList<>();
            for (ProdottiOrdine prodottiOrdine : ordine.getProdottiOrdine()) {
                ProdottiOrdineDto prodottiOrdineDto = new ProdottiOrdineDto();
                prodottiOrdineDto.setId(prodottiOrdine.getId());
                prodottiOrdineDto.setQuantita(prodottiOrdine.getQuantità());
                prodottiOrdineDto.setPrezzo(prodottiOrdine.getPrezzo());

                // Popola il ProductDto nel OrderProdDto
                ProdottoDto prodottoDto = new ProdottoDto();
                prodottoDto.setId(prodottiOrdine.getProdotto().getId());
                prodottoDto.setNomeProdotto(prodottiOrdine.getProdotto().getNomeProdotto());
                // Altri campi di ProductDto se necessario

                prodottiOrdineDto.setProdottoDto(prodottoDto);

                prodottiOrdineDtos.add(prodottiOrdineDto);
            }

            ordineDto.setProdottiOrdineDto(prodottiOrdineDtos);
            ordineDtos.add(ordineDto);
        }

        return ordineDtos;
    }

    public List<Ordine> getAllOrders(){
        return ordineRepository.findAll();
    } //Eventualmente se admin vuole vedere gli ordini totali

    public int getNumberOrders(){
        return ordineRepository.findAll().size();
    } //Eventualmente se admin vuole sapere il numero degli ordini totali

    @Transactional
    public OrdineDto createOrderFromCart(int idUtente, List<ProdottiCarrelloDto> cartItems) {
        Utente utente = utenteRepository.findById(idUtente)
                .orElseThrow(() -> new AppException("Utente non trovato", HttpStatus.NOT_FOUND));

        Ordine ordine = new Ordine();
        ordine.setUtente(utente);
        ordine.setDataOrdine(LocalDateTime.now());

        double prezzoTotale = 0.0;
        String nomeProdotto="";

        List<ProdottiOrdine> prodottiOrdine = new ArrayList<>();

        for (ProdottiCarrelloDto cartItem : cartItems) {
            Prodotto prodotto = prodottoRepository.findById(cartItem.getIdProdotto())
                    .orElseThrow(() -> new AppException("Prodotto non trovato tramite id: ", HttpStatus.NOT_FOUND));

            ProdottiOrdine prodottoOrdine = new ProdottiOrdine();
            prodottoOrdine.setProdotto(prodotto);
            prodottoOrdine.setOrdine(ordine);
            prodottoOrdine.setQuantità(cartItem.getQuantita());
            prodottoOrdine.setPrezzo(cartItem.getPrezzo());
            nomeProdotto=prodotto.getNomeProdotto();
            prezzoTotale += cartItem.getSubTotal();

            prodottiOrdine.add(prodottoOrdine);
        }
        if(prodottiOrdine.isEmpty()) return null;

        double tot = Math.round(prezzoTotale*100.0)/100.0;
        ordine.setNomeProdotto(nomeProdotto);
        ordine.setProdottiOrdine(prodottiOrdine);
        ordine.setPrezzoTotale(tot);


        cartItems.forEach(cartItem -> carrelloService.removeProductFromCart(idUtente, cartItem.getIdProdotto()));

        // Aggiorna le quantità nel magazzino
        for (ProdottiCarrelloDto cartItem : cartItems) {
            Prodotto prodotto = prodottoRepository.findById(cartItem.getIdProdotto())
                    .orElseThrow(() -> new AppException("Prodotto non trovato: ", HttpStatus.NOT_FOUND));

            int newQuantita = prodotto.getQuantita() - cartItem.getQuantita();
            if (newQuantita < 0) {
                // Gestisci la situazione in cui la quantità nel carrello supera la quantità disponibile nel magazzino
                throw new AppException("Errore: " + prodotto.getId(), HttpStatus.BAD_REQUEST);
            }

            prodotto.setQuantita(newQuantita);
            prodottoRepository.save(prodotto);
        }

        ordineRepository.save(ordine);

        // Converto l'ordine in un DTO
        OrdineDto ordineDto = new OrdineDto();
        ordineDto.setId(ordine.getId());
        ordineDto.setNomeProdotto(ordine.getNomeProdotto());
        ordineDto.setDataOrdine(ordine.getDataOrdine());
        ordineDto.setPrezzoTotale(ordine.getPrezzoTotale());

        return ordineDto;
    }
}
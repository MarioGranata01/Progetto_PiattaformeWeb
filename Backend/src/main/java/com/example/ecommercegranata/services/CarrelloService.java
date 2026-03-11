package com.example.ecommercegranata.services;
import com.example.ecommercegranata.dto.CarrelloDto;
import com.example.ecommercegranata.dto.ProdottiCarrelloDto;
import com.example.ecommercegranata.dto.ProdottoDto;
import com.example.ecommercegranata.entities.Carrello;
import com.example.ecommercegranata.entities.ProdottiCarrello;
import com.example.ecommercegranata.entities.Prodotto;
import com.example.ecommercegranata.entities.Utente;
import com.example.ecommercegranata.exception.AppException;
import com.example.ecommercegranata.repository.CarrelloRepository;
import com.example.ecommercegranata.repository.ProdottiCarrelloRepository;
import com.example.ecommercegranata.repository.ProdottoRepository;
import com.example.ecommercegranata.repository.UtenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Service
public class CarrelloService {

    private final CarrelloRepository carrelloRepository;
    private final ProdottiCarrelloRepository prodottiCarrelloRepository;
    private final UtenteRepository utenteRepository;
    private final ProdottoRepository prodottoRepository;

    public void createCartForUser(int idUtente) {
        Optional<Carrello> esisteCarrello = carrelloRepository.findByIdUtente(idUtente);
        if (esisteCarrello.isPresent()) {
            return;
        }
        Carrello nuovoCarrello = new Carrello();
        nuovoCarrello.setIdUtente(idUtente);
        carrelloRepository.save(nuovoCarrello);
    }
    public CarrelloDto getCartByUserId(int idUtente) {
        Optional<Carrello> carrelloUtente = carrelloRepository.findByIdUtente(idUtente);
        if (carrelloUtente.isPresent()) {
            Carrello carrello = carrelloUtente.get();

            CarrelloDto carrelloDto = new CarrelloDto();
            carrelloDto.setId(carrello.getId());
            carrelloDto.setIdUtente(carrello.getIdUtente());

            List<ProdottiCarrelloDto> prodottiCarelloDtos = getCartItemDto(carrello);

            Map<Integer, ProdottiCarrelloDto> prodottiCarrelloMap = new HashMap<>();

            for (ProdottiCarrelloDto prodottiCarrelloDto : prodottiCarelloDtos) {
                int idProdotto = prodottiCarrelloDto.getIdProdotto();
                if (prodottiCarrelloMap.containsKey(idProdotto)) {
                    ProdottiCarrelloDto esisteProdotto = prodottiCarrelloMap.get(idProdotto);
                    esisteProdotto.setQuantita(esisteProdotto.getQuantita() + prodottiCarrelloDto.getQuantita());
                    esisteProdotto.setSubTotal(esisteProdotto.getSubTotal()+(prodottiCarrelloDto.getSubTotal()));
                } else {
                    prodottiCarrelloMap.put(idProdotto, prodottiCarrelloDto);
                }
            }

            List<ProdottiCarrelloDto> prodottiCarrelloEffettivi = new ArrayList<>(prodottiCarrelloMap.values());
            carrelloDto.setProdottiCarrelloDto(prodottiCarrelloEffettivi);

            for (ProdottiCarrelloDto prodottiCarrelloEffettivo : prodottiCarrelloEffettivi) {
                int idProdotto = prodottiCarrelloEffettivo.getIdProdotto();
                Prodotto prodotto = prodottoRepository.findById(idProdotto)
                        .orElseThrow(() -> new AppException("Prodotto non trovato", HttpStatus.NOT_FOUND));

                ProdottoDto prodottoDto = new ProdottoDto();
                prodottoDto.setImmagine(prodotto.getImmagine());
                prodottiCarrelloEffettivo.setProdotto(prodottoDto);
            }

            double prezzoTotale = prodottiCarrelloEffettivi.stream().mapToDouble(ProdottiCarrelloDto::getSubTotal).sum();

            carrelloDto.setPrezzoTotale(prezzoTotale);

            return carrelloDto;
        } else {
            return null;
        }
    }

    public int getNumberOfItemsInCart(int idUtente) {
        Optional<Carrello> carrelloUtente = carrelloRepository.findByIdUtente(idUtente);
        if (carrelloUtente.isPresent()) {
            Carrello cart = carrelloUtente.get();
            return cart.getProdottiCarrello().stream().mapToInt(ProdottiCarrello::getQuantita).sum();
        } else {
            return 0;
        }
    }

    private static List<ProdottiCarrelloDto> getCartItemDto(Carrello carrello) {
        List<ProdottiCarrelloDto> prodottiCarrelloDtos = new ArrayList<>();
        for (ProdottiCarrello cartItem : carrello.getProdottiCarrello()) {
            ProdottiCarrelloDto prodottiCarrelloDto = new ProdottiCarrelloDto();
            prodottiCarrelloDto.setIdProdotto(cartItem.getIdProdotto());
            prodottiCarrelloDto.setNomeProdotto(cartItem.getNomeProdotto());
            prodottiCarrelloDto.setQuantita(cartItem.getQuantita());
            prodottiCarrelloDto.setPrezzo(cartItem.getPrezzo());

            prodottiCarrelloDto.setSubTotal(cartItem.getPrezzo()*(cartItem.getQuantita()));
            prodottiCarrelloDtos.add(prodottiCarrelloDto);
        }
        return prodottiCarrelloDtos;
    }

    @Transactional
    public CarrelloDto addItemToCart(int idUtente, int idProdotto, int quantita) {
        Utente utente = utenteRepository.findById(idUtente)
                .orElseThrow(() -> new AppException("Utente non trovato", HttpStatus.NOT_FOUND));

        Prodotto prodotto = prodottoRepository.findById(idProdotto)
                .orElseThrow(() -> new AppException("Prodotto non trovato", HttpStatus.NOT_FOUND));

        double prezzoProdotto = prodotto.getPrezzo() * quantita;
        Optional<Carrello> cartOptional = carrelloRepository.findByIdUtente(idUtente);
        Carrello userCart = cartOptional.orElse(new Carrello());

        if (cartOptional.isEmpty()) {
            userCart.setIdUtente(idUtente);
            carrelloRepository.save(userCart);
        }

        if (prodotto.getQuantita() < quantita) {
            return null;
        }

        ProdottiCarrello existingCartProd = recoveryCartProd(idUtente, idProdotto);

        if (existingCartProd != null) {

            existingCartProd.setQuantita(quantita);
            existingCartProd.setPrezzo(prodotto.getPrezzo());
            existingCartProd.setSubTotal(prezzoProdotto);

            prodottiCarrelloRepository.save(existingCartProd);
        } else {

            ProdottiCarrello newProd = new ProdottiCarrello();
            newProd.setIdProdotto(prodotto.getId());
            newProd.setNomeProdotto(prodotto.getNomeProdotto());
            newProd.setQuantita(quantita);
            newProd.setPrezzo(prodotto.getPrezzo());
            newProd.setCarrello(userCart);
            newProd.setSubTotal(prezzoProdotto);

            prodottiCarrelloRepository.save(newProd);
            userCart.getProdottiCarrello().add(newProd);
        }

        double totalPrice = userCart.getProdottiCarrello().stream()
                .mapToDouble(ProdottiCarrello::getSubTotal)
                .sum();
        userCart.setPrezzoTotale(totalPrice);
        carrelloRepository.save(userCart);

        return new CarrelloDto(userCart.getId(), userCart.getIdUtente(), totalPrice, getCartItemDto(userCart));
    }
    private ProdottiCarrello recoveryCartProd(int idUtente, int idProdotto) {
        Optional<Carrello> userCart = carrelloRepository.findByIdUtente(idUtente);

        if (userCart.isPresent()) {
            Carrello carrello = userCart.get();
            Optional<ProdottiCarrello> cartProdOptional = carrello.getProdottiCarrello().stream()
                    .filter(cartProd -> cartProd.getIdProdotto() == idProdotto)
                    .findFirst();

            return cartProdOptional.orElse(null);
        }

        return null;
    }

    @Transactional
    public CarrelloDto removeProductFromCart(int idUtente, int idProdotto) {
        Utente utente = utenteRepository.findById(idUtente).orElseThrow(() -> new AppException("Utente non trovato", HttpStatus.NOT_FOUND));

        Prodotto prodotto = prodottoRepository.findById(idProdotto).orElseThrow(() -> new AppException("Prodotto non trovato", HttpStatus.NOT_FOUND));

        Carrello carrelloUtente = carrelloRepository.findByIdUtente(idUtente).orElseThrow(() -> new AppException("Carrello non trovato", HttpStatus.NOT_FOUND));

        ProdottiCarrello cartItemToRemove = carrelloUtente.getProdottiCarrello().stream().filter(item -> item.getIdProdotto()==(idProdotto)).findFirst().orElseThrow(() -> new AppException("Prodotto del carrello non trovato", HttpStatus.NOT_FOUND));

        carrelloUtente.getProdottiCarrello().remove(cartItemToRemove);
        prodottiCarrelloRepository.delete(cartItemToRemove);

        double prezzoTotale = carrelloUtente.getProdottiCarrello().stream().mapToDouble(ProdottiCarrello::getPrezzo).sum();
        carrelloUtente.setPrezzoTotale(prezzoTotale);
        carrelloRepository.save(carrelloUtente);

        CarrelloDto carrelloDto = new CarrelloDto(carrelloUtente.getId(), idUtente, prezzoTotale, getCartItemDto(carrelloUtente));
        return carrelloDto;
    }

    @Transactional
    public void clearCart(int idUtente) {
        Carrello carrelloUtente = carrelloRepository.findByIdUtente(idUtente).orElseThrow(() -> new AppException("Carrello non trovato", HttpStatus.NOT_FOUND));
        carrelloUtente.getProdottiCarrello().clear();
        carrelloUtente.setPrezzoTotale(0.00);
        carrelloRepository.save(carrelloUtente);
    }
}
package com.example.ecommercegranata.services;


import com.example.ecommercegranata.entities.Prodotto;
import com.example.ecommercegranata.exception.AppException;
import com.example.ecommercegranata.repository.ProdottoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.Optional;

@RequiredArgsConstructor

@Service

public class ProdottoService {

    private final ProdottoRepository prodottoRepository;

    public List<Prodotto> getAll() {
        return prodottoRepository.findVisible();
    }

    public Optional<Prodotto> getProductById(int productId) {
        return prodottoRepository.findById(productId);
    }

    public Prodotto addProduct(Prodotto prodotto) {
        return prodottoRepository.save(prodotto);
    }
    @Transactional
    public Prodotto deleteById(int idProdotto, Prodotto updatedProduct){
        Optional<Prodotto> existingProductOptional = prodottoRepository.findById(idProdotto);
        if (existingProductOptional.isPresent()) {
            Prodotto existingProduct = existingProductOptional.get();
            existingProduct.setStato(false);
            return prodottoRepository.save(existingProduct);
        } else {
            // Se il prodotto non esiste, puoi gestire l'errore o restituire null
            return null;
        }

    }
    public List<Prodotto> searchProducts(String term) {
        return prodottoRepository.findByNomeProdottoContainingIgnoreCase(term);
    }

    @Transactional
    public Prodotto updateProduct(int idProdotto, Prodotto updatedProduct) {
        Optional<Prodotto> existingProductOptional = prodottoRepository.findById(idProdotto);

        if (existingProductOptional.isPresent()) {
            Prodotto existingProduct = existingProductOptional.get();

            // Aggiornare le caratteristiche del prodotto esistente con quelle del prodotto aggiornato
            existingProduct.setNomeProdotto(updatedProduct.getNomeProdotto());
            existingProduct.setDescrizione(updatedProduct.getDescrizione());
            existingProduct.setPrezzo(updatedProduct.getPrezzo());
            existingProduct.setImmagine(updatedProduct.getImmagine());
            existingProduct.setQuantita(updatedProduct.getQuantita());

            // Salva il prodotto aggiornato nel repository
            return prodottoRepository.save(existingProduct);
        } else {
            // Se il prodotto non esiste, puoi gestire l'errore o restituire null
            return null;
        }
    }
}
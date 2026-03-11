package com.example.ecommercegranata.controllers;

import com.example.ecommercegranata.dto.ProdottoDto;
import com.example.ecommercegranata.entities.Prodotto;
import com.example.ecommercegranata.exception.AppException;
import com.example.ecommercegranata.services.ProdottoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/products")

public class ProdottoController {

    private final ProdottoService prodottoService;

    @GetMapping("/all")
    public List<Prodotto> getAll() {
        return prodottoService.getAll();
    }

    @PostMapping("/add")
    public Prodotto addProduct(@RequestBody ProdottoDto prodottoDto) {
        if (
                prodottoDto.getNomeProdotto() == null ||
                        prodottoDto.getNomeProdotto().isEmpty() ||
                        prodottoDto.getDescrizione() == null || prodottoDto.getDescrizione().isEmpty() ||
                       // prodottoDto.getImmagine() == null || prodottoDto.getImmagine().isEmpty() ||
                        prodottoDto.getPrezzo() <= 0.0) {

            throw new AppException("tutti i campi sono obbligatori.", HttpStatus.BAD_REQUEST);
        }
        if(prodottoDto.getImmagine()==null)
            prodottoDto.setImmagine("");
        Prodotto nuovoProdotto = new Prodotto();
        nuovoProdotto.setNomeProdotto(prodottoDto.getNomeProdotto());
        nuovoProdotto.setDescrizione(prodottoDto.getDescrizione());
        nuovoProdotto.setPrezzo(prodottoDto.getPrezzo());
        nuovoProdotto.setImmagine(prodottoDto.getImmagine());
        nuovoProdotto.setQuantita(prodottoDto.getQuantita());
        return prodottoService.addProduct(nuovoProdotto);
    }

    @GetMapping("/{idProdotto}")
    public ResponseEntity<?> getProductById(@PathVariable int idProdotto) {
        Optional<Prodotto> prodottoOptional = prodottoService.getProductById(idProdotto);

        if (prodottoOptional .isPresent()) {
            Prodotto product = prodottoOptional .get();
            return ResponseEntity.ok(product);
        } else {
            throw new AppException("Prodotto non trovato", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Prodotto>> searchProducts(@RequestParam String term) {
        List<Prodotto> searchResults = prodottoService.searchProducts(term);
        return ResponseEntity.ok(searchResults);
    }

    @PutMapping("/delete/{idProdotto}")
    public ResponseEntity<?> deleteById(@PathVariable int idProdotto, @RequestBody ProdottoDto updatedProductDto){
        Optional<Prodotto> prod=prodottoService.getProductById(idProdotto);
        if (prod.isPresent()) {
            Prodotto prodotto = prod.get();
            prodottoService.deleteById(idProdotto, prodotto);
            return new ResponseEntity<>(prodotto, HttpStatus.OK);
        } else {
            throw new AppException("Product not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{idProdotto}")
    public ResponseEntity<?> updateProduct(@PathVariable int idProdotto, @RequestBody ProdottoDto updatedProductDto) {
        if (
                updatedProductDto.getNomeProdotto() == null ||
                        updatedProductDto.getNomeProdotto().isEmpty() ||
                        updatedProductDto.getDescrizione() == null || updatedProductDto.getDescrizione().isEmpty() ||
                        updatedProductDto.getPrezzo() <= 0.0) {

            throw new AppException("All fields are required.", HttpStatus.BAD_REQUEST);
        }
        if (updatedProductDto.getImmagine() == null) {
            updatedProductDto.setImmagine("");
        }

        Prodotto updatedProduct = new Prodotto();
        updatedProduct.setNomeProdotto(updatedProductDto.getNomeProdotto());
        updatedProduct.setDescrizione(updatedProductDto.getDescrizione());
        updatedProduct.setPrezzo(updatedProductDto.getPrezzo());
        updatedProduct.setImmagine(updatedProductDto.getImmagine());
        updatedProduct.setQuantita(updatedProductDto.getQuantita());

        Prodotto result = prodottoService.updateProduct(idProdotto, updatedProduct);

        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            throw new AppException("Product not found", HttpStatus.NOT_FOUND);
        }
    }
}
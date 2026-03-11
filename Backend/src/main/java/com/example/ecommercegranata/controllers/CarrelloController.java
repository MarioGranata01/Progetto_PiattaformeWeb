package com.example.ecommercegranata.controllers;

import com.example.ecommercegranata.dto.CarrelloDto;
import com.example.ecommercegranata.exception.AppException;
import com.example.ecommercegranata.services.CarrelloService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/cart")

public class CarrelloController {

    private final CarrelloService carrelloService;

    @GetMapping("/{idUtente}")
    public ResponseEntity<Map<String, Object>> getCartByUserId(@PathVariable int idUtente) {
        Map<String, Object> response = new HashMap<>();
        CarrelloDto carrelloDto = carrelloService.getCartByUserId(idUtente);
        if (carrelloDto != null) {
            response.put("carrello", carrelloDto);
            response.put("numero di prodotti nel carrello", carrelloService.getNumberOfItemsInCart(idUtente));
            return ResponseEntity.ok().body(response);
        } else {
            throw new AppException("Carrello non trovato", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{idUtente}/{idProdotto}/{quantita}")
    public ResponseEntity<CarrelloDto> addItemToCart(@PathVariable int idUtente, @PathVariable int idProdotto, @PathVariable int quantita) {
        try {
            CarrelloDto cartDto = carrelloService.addItemToCart(idUtente, idProdotto, quantita);
            return ResponseEntity.ok().body(cartDto);
        } catch (AppException e) {
            return ResponseEntity.status(e.getHttpStato()).build();
        }
    }

    @DeleteMapping("/{idUtente}/{idProdotto}")
    public ResponseEntity<CarrelloDto> removeItemFromCart(@PathVariable int idUtente, @PathVariable int idProdotto) {
        CarrelloDto carrelloDto = carrelloService.removeProductFromCart(idUtente, idProdotto);
        return ResponseEntity.ok().body(carrelloDto);
    }

    @DeleteMapping("/clear/{idUtente}")
    public ResponseEntity<Void> clearCart(@PathVariable int idUtente) {
        carrelloService.clearCart(idUtente);
        return ResponseEntity.ok().build();
    }
}
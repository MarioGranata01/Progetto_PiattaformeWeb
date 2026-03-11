package com.example.ecommercegranata.controllers;

import com.example.ecommercegranata.dto.OrdineDto;
import com.example.ecommercegranata.dto.ProdottiCarrelloDto;
import com.example.ecommercegranata.services.CarrelloService;
import com.example.ecommercegranata.services.OrdineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")

public class OrdineController {
    private final OrdineService ordineService;
    private final CarrelloService carrelloService;

    @GetMapping("/{idUtente}")
    public List<OrdineDto> getOrdersByUserId(@PathVariable int idUtente, Authentication authentication) {
        return ordineService.getOrdersByUserId(idUtente, authentication);
    }

    @PostMapping("/createFromCart")
    public ResponseEntity<OrdineDto> createOrderFromCart(@RequestParam int idUtente, @RequestBody List<ProdottiCarrelloDto> cartItems, Principal usr) {
        OrdineDto ordineDto = ordineService.createOrderFromCart(idUtente, cartItems);
            return ResponseEntity.ok().body(ordineDto);
    }
}
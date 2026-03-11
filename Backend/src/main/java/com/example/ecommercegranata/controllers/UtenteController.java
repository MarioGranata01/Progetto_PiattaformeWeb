package com.example.ecommercegranata.controllers;

import com.example.ecommercegranata.dto.UtenteDto;
import com.example.ecommercegranata.entities.Utente;
import com.example.ecommercegranata.services.UtenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/user")

public class UtenteController {

    private final UtenteService utenteService;

    @GetMapping("/{idUtente}")
    public Utente getUserById(@PathVariable int idUtente, Authentication authentication) {
        return utenteService.getUserById(idUtente, authentication);
    }

    @PutMapping("/update/{idUtente}")
    public Utente updateUser(@PathVariable int idUtente, @RequestBody UtenteDto utenteDto, Authentication authentication) {
        return utenteService.updateUser(idUtente, utenteDto, authentication);
    }
}
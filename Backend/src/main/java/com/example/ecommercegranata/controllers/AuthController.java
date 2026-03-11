package com.example.ecommercegranata.controllers;

import com.example.ecommercegranata.dto.LoginUtenteDto;
import com.example.ecommercegranata.dto.UtenteDto;
import com.example.ecommercegranata.entities.Utente;
import com.example.ecommercegranata.exception.AppException;
import com.example.ecommercegranata.services.AuthenticationService;
import com.example.ecommercegranata.services.CarrelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private CarrelloService carrelloService;

    @PostMapping("/register")
    public Utente register(@Valid @RequestBody UtenteDto utente) {
        if (utente.getEmail() == null || utente.getEmail().isEmpty() || utente.getPassword() == null || utente.getPassword().isEmpty()) {
            throw new AppException("Devi inserire tutte le credenziali.", HttpStatus.BAD_REQUEST);
        }
        Utente registeredUser = authenticationService.registrazione(utente.getUsername(), utente.getEmail(), utente.getPassword());

        carrelloService.createCartForUser(registeredUser.getId());
        return registeredUser;
        }

    @PostMapping("/login")
    public LoginUtenteDto login(@Valid @RequestBody UtenteDto user) {
        LoginUtenteDto loginUtenteDto = authenticationService.login(user.getUsername(), user.getPassword());

        if (loginUtenteDto.getUtente() == null) {
            throw new AppException("Credenziali non valide.", HttpStatus.NOT_FOUND);
        }

        return loginUtenteDto;
    }
}
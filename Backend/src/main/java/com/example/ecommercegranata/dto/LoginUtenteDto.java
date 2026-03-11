package com.example.ecommercegranata.dto;

import com.example.ecommercegranata.entities.Utente;
import lombok.Data;

@Data
public class LoginUtenteDto {

    private int id;
    private Utente utente;
    private String jwt;

    public LoginUtenteDto() {
        super();
    }

    public LoginUtenteDto(int id, Utente utente, String jwt) {
        this.id = id;
        this.utente = utente;
        this.jwt = jwt;
    }

}
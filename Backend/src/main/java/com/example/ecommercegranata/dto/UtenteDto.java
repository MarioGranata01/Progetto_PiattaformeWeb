package com.example.ecommercegranata.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UtenteDto {

    private String username;

    private String email;

    private String password;


    public UtenteDto() {
        super();
    }

    public UtenteDto(String username, String email, String password) {
        super();
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
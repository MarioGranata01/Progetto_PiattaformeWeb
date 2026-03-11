package com.example.ecommercegranata.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
public class AppException extends RuntimeException {

    private final HttpStatus httpStato;

    public AppException(String messaggio, HttpStatus httpStato) {
        super(messaggio);
        this.httpStato = httpStato;
    }
}
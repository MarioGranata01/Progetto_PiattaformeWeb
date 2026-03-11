package com.example.ecommercegranata.dto;

import lombok.Data;

@Data
public class ProdottoDto {
    private int id;
    private String nomeProdotto;
    private String descrizione;
    private double prezzo;
    private String immagine;
    private int quantita;

    public ProdottoDto(){

    }

}
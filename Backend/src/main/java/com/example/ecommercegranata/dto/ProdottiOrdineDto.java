package com.example.ecommercegranata.dto;

import lombok.Data;

@Data
public class ProdottiOrdineDto {
    private int id;

    private OrdineDto ordineDto;

    private ProdottoDto prodottoDto;

    private int quantita;

    private double prezzo;

}

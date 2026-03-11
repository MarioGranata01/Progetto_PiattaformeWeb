package com.example.ecommercegranata.dto;

import lombok.Data;

@Data
public class ProdottiCarrelloDto {

    private int idProdotto;
    private String nomeProdotto;
    private int quantita;
    private double prezzo;
    private double subTotal;
    private ProdottoDto prodotto;

    public ProdottiCarrelloDto(){

    }

    public ProdottiCarrelloDto(int idProdotto, String nomeProdotto, int quantita, double prezzo, double subTotal, ProdottoDto prodotto) {
        this.idProdotto = idProdotto;
        this.nomeProdotto = nomeProdotto;
        this.prezzo = prezzo;
        this.quantita = quantita;
        this.subTotal = subTotal;
        this.prodotto = prodotto;
    }
}
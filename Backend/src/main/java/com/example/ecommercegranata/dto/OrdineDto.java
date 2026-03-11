package com.example.ecommercegranata.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data

public class OrdineDto {

    private int id;

    private LocalDateTime dataOrdine;

    private double prezzoTotale;

    private String nomeProdotto;

    private List<ProdottiOrdineDto> prodottiOrdineDto;

    public OrdineDto(){

    }

    public OrdineDto(int id, LocalDateTime dataOrdine, double prezzoTotale){
        this.id = id;
        this.dataOrdine = dataOrdine;
        this.prezzoTotale = prezzoTotale;
    }
}
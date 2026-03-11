package com.example.ecommercegranata.dto;
import lombok.Data;

import java.util.List;

@Data
public class CarrelloDto {

    private int id;
    private int idUtente;
    private double prezzoTotale;
    private List<ProdottiCarrelloDto> prodottiCarrelloDto;

    public CarrelloDto(){

    }

    public CarrelloDto(int id, int idUtente, double prezzoTotale, List<ProdottiCarrelloDto> prodottiCarrelloDto){
        this.id = id;
        this.idUtente = idUtente;
        this.prezzoTotale = prezzoTotale;
        this.prodottiCarrelloDto = prodottiCarrelloDto;
    }

}
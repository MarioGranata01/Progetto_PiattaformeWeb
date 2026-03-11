package com.example.ecommercegranata.entities;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "PRODOTTI_ORDINE")
public class ProdottiOrdine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_ordine")
    private Ordine ordine;

    @ManyToOne
    @JoinColumn(name = "id_prodotto")
    private Prodotto prodotto;

    @Column(name = "quantità")
    private int quantità;

    @Column(name = "prezzo")
    private double prezzo;

    public ProdottiOrdine(){

    }

    public ProdottiOrdine(Ordine ordine, Prodotto prodotto, int quantità, double prezzo){
        this.ordine = ordine;
        this.prodotto = prodotto;
        this.quantità = quantità;
        this.prezzo = prezzo;
    }
}
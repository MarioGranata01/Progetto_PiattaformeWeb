package com.example.ecommercegranata.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "PRODOTTI_CARRELLO")
@Data

public class ProdottiCarrello{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_prodotto")
    private int idProdotto;

    @Column(name = "nome_prodotto")
    private String nomeProdotto;

    @Column(name = "quantità")
    private int quantita;

    @Column(name = "prezzo")
    private double prezzo;

    @Column(name = "sub_total")
    private double subTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carrello")
    private Carrello carrello;

    public ProdottiCarrello(){

    }

    public ProdottiCarrello(int idProdotto, String nomeProdotto, int quantita, double prezzo, double subTotal, Carrello carrello){
        this.idProdotto = idProdotto;
        this.nomeProdotto = nomeProdotto;
        this.quantita = quantita;
        this.prezzo = prezzo;
        this.subTotal=subTotal;
        this.carrello=carrello;
    }
}
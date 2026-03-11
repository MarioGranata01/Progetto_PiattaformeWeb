package com.example.ecommercegranata.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.repository.Query;

//@Query(name = "findVisibale" ,  = "")
@Entity
@Table(name="PRODOTTO")
@Data
public class Prodotto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="nomeProdotto")
    private String nomeProdotto;
    @Column(name="descrizione")
    private String descrizione;
    @Column(name="prezzo")
    private double prezzo;
    @Column(name="immagine")
    private String immagine;
    @Column(name="quantità")
    private int quantita;
    @Column(name = "stato")
    private boolean stato;

    public Prodotto() {
    }

    public Prodotto(String nomeProdotto, String descrizione, double prezzo, String immagine, int quantita){
        this.nomeProdotto=nomeProdotto;
        this.descrizione=descrizione;
        this.prezzo=prezzo;
        this.immagine=immagine;
        this.quantita=quantita;
    }
}
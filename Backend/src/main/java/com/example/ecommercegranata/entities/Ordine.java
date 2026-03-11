package com.example.ecommercegranata.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="ORDINI")
@Data
public class Ordine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name="idUtente")
    private Utente utente;
    @Column(name="dataOrdine")
    private LocalDateTime dataOrdine;
    @Column(name="prezzoTotale")
    private double prezzoTotale;
    @Column(name="nomeProdotto")
    private String nomeProdotto;
    @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL)
    private List<ProdottiOrdine> prodottiOrdine;

    public Ordine(){

    }
    public Ordine(Utente utente, LocalDateTime dataOrdine, double prezzoTotale, String nomeProdotto, List<ProdottiOrdine> prodottiOrdine){
        this.utente=utente;
        this.dataOrdine=dataOrdine;
        this.prezzoTotale=prezzoTotale;
        this.nomeProdotto=nomeProdotto;
        this.prodottiOrdine=prodottiOrdine;
    }

}
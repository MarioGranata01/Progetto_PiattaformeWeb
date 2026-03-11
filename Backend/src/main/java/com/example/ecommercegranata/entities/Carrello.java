package com.example.ecommercegranata.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CARRELLO")
@Data
public class Carrello {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_utente")
    private int idUtente;

    @Column(name = "prezzo_totale")
    private double prezzoTotale;

    @OneToMany(mappedBy = "carrello", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProdottiCarrello> prodottiCarrello = new ArrayList<>();

    public Carrello(){
    }

    public Carrello(int idUtente, double prezzoTotale, List<ProdottiCarrello> prodottiCarrello){
        this.idUtente=idUtente;
        this.prezzoTotale=prezzoTotale;
        this.prodottiCarrello=prodottiCarrello;
    }
}
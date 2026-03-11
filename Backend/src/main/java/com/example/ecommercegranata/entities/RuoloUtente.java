package com.example.ecommercegranata.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "RUOLO_UTENTE")
@Data
public class RuoloUtente implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="IdRuolo")
    private int id;
    private String authority;
    public RuoloUtente(){
        super();
    }
    public RuoloUtente(String authority){
        this.authority = authority;
    }
    public RuoloUtente(int id, String authority){
        this.id = id;
        this.authority = authority;
    }
}
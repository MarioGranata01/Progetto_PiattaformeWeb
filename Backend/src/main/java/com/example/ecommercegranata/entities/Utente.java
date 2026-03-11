package com.example.ecommercegranata.entities;

import jakarta.persistence.*;
import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Table(name="UTENTI")
@Entity
@Data

public class Utente {
    private static final Logger logger = LoggerFactory.getLogger(Utente.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="username")
    private String username;
    @Email
    @Column(name="email")
    private String email;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
            message = "La password deve contenere almeno 8 caratteri, una maiuscola, una minuscola, un numero e un carattere speciale.")
    @Column(name="password")
    private String password;
    @Column(name = "first_login", nullable = false)
    private boolean firstLogin = true;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
            name="utente_ruolo",
            joinColumns = {@JoinColumn(name="id_utente")},
            inverseJoinColumns = {@JoinColumn(name="id_ruolo")}
    )
    private Set<RuoloUtente> authorities;
    public Utente() {
        authorities = new HashSet<>();
    }
    public Utente(String username, String email, String password, Set<RuoloUtente> authorities) {
        this.username = username;
        this.email = email;
        if (!isValidPassword(password)) {
            logger.error("Tentativo di creare un utente con una password non valida: {}", password);
            // Puoi gestire l'errore qui, ad esempio lanciando un'eccezione.
        }
        this.password = password;
        this.authorities = authorities;
    }

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{6,}$");
    }
    public Utente(String username, String password, Set<RuoloUtente> authorities) {
        this.username=username;
        this.password = password;
        this.authorities = authorities;
    }
}
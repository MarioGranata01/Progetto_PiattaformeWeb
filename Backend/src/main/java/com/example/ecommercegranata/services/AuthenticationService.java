package com.example.ecommercegranata.services;
import com.example.ecommercegranata.dto.LoginUtenteDto;
import com.example.ecommercegranata.entities.RuoloUtente;
import com.example.ecommercegranata.entities.Utente;
import com.example.ecommercegranata.repository.RuoloUtenteRepository;
import com.example.ecommercegranata.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AuthenticationService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private RuoloUtenteRepository ruoloUtenteRepository;

    //@Autowired
    private BCryptPasswordEncoder passwordEncoder;

    //@Autowired
    private AuthenticationManager authenticationManager;

    //@Autowired
    private TokenService tokenService;

    public AuthenticationService(BCryptPasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }
    public Utente registrazione(String username, String email, String password) {

        String encodedPassword = passwordEncoder.encode(password);
        RuoloUtente ruoloUtente = ruoloUtenteRepository.findByAuthority("UTENTE").get();
        Set<RuoloUtente> authorities = new HashSet<>();

        if (ruoloUtente != null) {
            authorities.add(ruoloUtente);
        }

        return utenteRepository.save(new Utente(username, email, encodedPassword, authorities));
    }
    public LoginUtenteDto login(String username, String password) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            String token = tokenService.generateToken(auth);

            Utente utente = utenteRepository.findByUsername(username).orElse(null);
            if (utente != null) {
                return new LoginUtenteDto(utente.getId(), utente, token);
            } else {
                return new LoginUtenteDto(0, null, "");
            }

        } catch (AuthenticationException e) {
            return new LoginUtenteDto(0, null, "");
        }
    }
}
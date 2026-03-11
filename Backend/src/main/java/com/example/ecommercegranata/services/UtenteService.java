package com.example.ecommercegranata.services;

import com.example.ecommercegranata.repository.UtenteRepository;
import com.example.ecommercegranata.entities.Utente;
import com.example.ecommercegranata.dto.UtenteDto;
import com.example.ecommercegranata.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UtenteService{

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public Utente getUserById(int id, Authentication authentication) {
        Utente utente = utenteRepository.findById(id).orElseThrow(() -> new AppException("Utente non trovato.", HttpStatus.NOT_FOUND));
        if (!utente.getUsername().equals(authentication.getName())) {
            throw new AppException("Accesso negato.", HttpStatus.BAD_REQUEST);
        }
        return utente;
    }

    @Transactional
    public Utente updateUser(int id, UtenteDto utenteDto, Authentication authentication) {
        Utente utente = utenteRepository.findById(id).orElseThrow(() -> new AppException("Utente non trovato", HttpStatus.NOT_FOUND));
        if (!utente.getUsername().equals(authentication.getName())) {
            throw new AppException("Accesso negato", HttpStatus.BAD_REQUEST);
        }
        String newUsername = utenteDto.getUsername();
        String newEmail = utenteDto.getEmail();
        String newPassword = utenteDto.getPassword();

        if (newUsername != null && !newUsername.isEmpty()) {
            utente.setUsername(newUsername);
        }
        if (newEmail != null && !newEmail.isEmpty()) {
            utente.setEmail(newEmail);
        }
        if (newPassword != null && !newPassword.isEmpty()) {
            String hashedNewPassword = passwordEncoder.encode(newPassword);
            utente.setPassword(hashedNewPassword);
        }
        utenteRepository.save(utente);
        return utente;
    }
}
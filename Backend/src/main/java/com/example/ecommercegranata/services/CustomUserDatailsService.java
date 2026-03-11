package com.example.ecommercegranata.services;

import com.example.ecommercegranata.exception.AppException;
import com.example.ecommercegranata.repository.UtenteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDatailsService implements UserDetailsService {
    private final UtenteRepository utenteRepository;

    @Autowired
    public CustomUserDatailsService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.example.ecommercegranata.entities.Utente utente = utenteRepository.findByUsername(username).orElseThrow(() -> new AppException("Utente non trovato", HttpStatus.NOT_FOUND));
        if (utente == null) {
            throw new UsernameNotFoundException("Utente non trovato" + username);
        }
        List<GrantedAuthority> authorities = utente.getAuthorities().stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .collect(Collectors.toList());

        return new User(utente.getUsername(), utente.getPassword(), authorities);
    }
}

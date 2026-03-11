package com.example.ecommercegranata.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TokenService {

  private final Key jwtSigningKey;

    public TokenService(@Value("gLJRkasb4UH4Rh6UMPYw1WqZVUWkEQf9") String jwtSecret) {
        // Usa la chiave specificata nel file YAML
        jwtSigningKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }
    public Key getSigningKey() {
        return jwtSigningKey;
    }
    public String generateToken(Authentication auth) {
        return generateToken(new HashMap<>(), auth);
    }

    private String generateToken(Map<String, Object> extraClaims, Authentication auth) {
        // Estrai il ruolo dall'oggetto Authentication
        String userRole = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setClaims(extraClaims)
                .claim("ruolo", userRole) // Aggiungi il ruolo come claim separato
                .setSubject(auth.getName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                .getBody();
    }


    public boolean isTokenValid(String token, UserDetails auth) {
        final String userName = extractUsername(token);
        return (userName.equals(auth.getUsername())) && !isTokenExpired(token);
    }
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}

package com.example.ecommercegranata.repository;
import com.example.ecommercegranata.entities.RuoloUtente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RuoloUtenteRepository extends JpaRepository<RuoloUtente, Integer> {
    Optional<RuoloUtente> findByAuthority(String authority);
}
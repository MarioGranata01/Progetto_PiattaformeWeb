package com.example.ecommercegranata.repository;

import com.example.ecommercegranata.entities.Carrello;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CarrelloRepository extends JpaRepository<Carrello, Integer> {
    Optional<Carrello> findByIdUtente(int idUtente);
}
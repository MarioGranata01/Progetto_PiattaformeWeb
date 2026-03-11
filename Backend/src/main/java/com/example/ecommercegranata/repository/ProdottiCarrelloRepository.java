package com.example.ecommercegranata.repository;
import com.example.ecommercegranata.entities.ProdottiCarrello;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdottiCarrelloRepository extends JpaRepository<ProdottiCarrello, Integer> {
}
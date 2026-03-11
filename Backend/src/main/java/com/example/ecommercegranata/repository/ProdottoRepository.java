package com.example.ecommercegranata.repository;
import com.example.ecommercegranata.entities.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdottoRepository extends JpaRepository<Prodotto, Integer> {
    List<Prodotto> findByNomeProdottoContainingIgnoreCase(String term);

    @Query("SELECT p FROM Prodotto p WHERE p.stato = true")
    List<Prodotto> findVisible();

}
package com.example.ecommercegranata.repository;
import com.example.ecommercegranata.entities.Ordine;
import com.example.ecommercegranata.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrdineRepository extends JpaRepository<Ordine, Integer> {
    List<Ordine> findAllByUtenteId(int idUtente);
}
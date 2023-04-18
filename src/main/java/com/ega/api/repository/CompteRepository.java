package com.ega.api.repository;

import com.ega.api.entity.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompteRepository extends JpaRepository<Compte, Integer> {
    Optional<Compte> findByNumeroCompte(Integer id);
    //Optional<Compte>findCompte(Integer id);

    Optional<Compte> findByNumeroCompte(String numeroCompte);

}

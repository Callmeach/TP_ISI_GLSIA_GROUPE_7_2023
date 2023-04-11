package com.ega.api.service;


import com.ega.api.entity.Compte;
import com.ega.api.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CompteService {
    private final CompteRepository compteRepository;

    @Autowired
    public CompteService(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }
    //public List<Compte> ShowComptes() {
        //return compteRepository.findAll();
    //}

    public Optional<List<Compte>> ShowComptes(){
        List<Compte> comptes = compteRepository.findAll();
        if(comptes.isEmpty()){
            return Optional.empty();
        } else {
            System.out.println(comptes); // afficher la liste de comptes
            return Optional.of(comptes);
        }
    }
    public Optional<Compte> findByNumeroCompte(String numeroCompte) {
        return compteRepository.findByNumeroCompte(numeroCompte);
    }

    public Compte SaveCompte(Compte compte){
        return compteRepository.save(compte);
    }
    public Optional<Compte> findCompte(Integer id) {
        return compteRepository.findById(id);
    }
    public Compte updateCompte(Integer id, Compte compte) {
        Optional<Compte> optionalCompte = findCompte(id);
        if (optionalCompte.isPresent()) {
            Compte existingCompte = optionalCompte.get();
            existingCompte.setNumeroCompte(compte.getNumeroCompte());
            existingCompte.setTitulaire(compte.getTitulaire());
            existingCompte.setSolde(compte.getSolde());
            return compteRepository.save(existingCompte);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le compte n'existe pas");
        }
    }



    //public void deleteCompte(Integer id){
        //compteRepository.deleteById(id);
    //}
    public void deleteCompte(Integer id) {
        Optional<Compte> optionalCompte = compteRepository.findByNumeroCompte(id);
        if (optionalCompte.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Compte introuvable");
        } else {
            compteRepository.deleteById(optionalCompte.get().getId());
        }
    }

}

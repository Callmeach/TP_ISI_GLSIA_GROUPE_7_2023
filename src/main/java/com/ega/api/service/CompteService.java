package com.ega.api.service;


import com.ega.api.entity.Compte;
import com.ega.api.repository.CompteRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Data
@Service
public class CompteService {
    @Autowired
    private CompteRepository compteRepository;

//    public CompteService(CompteRepository compteRepository) {
//        this.compteRepository = compteRepository;
//    }
    //public List<Compte> ShowComptes() {
        //return compteRepository.findAll();
    //}

    public List<Compte> getComptes(){
        return compteRepository.findAll();
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
            existingCompte.setTypeCompte(compte.getTypeCompte());
            existingCompte.setClient(compte.getClient());
            existingCompte.setSolde(compte.getSolde());
            return compteRepository.save(existingCompte);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le compte n'existe pas");
        }
    }


    public void deleteCompte(Integer id) {
        Optional<Compte> compte = compteRepository.findById(id);
        if (compte.isPresent()) {
            compteRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ce compte n'existe pas");
        }
    }

}

package com.ega.api.controller;


import com.ega.api.entity.Compte;
import com.ega.api.repository.CompteRepository;
import com.ega.api.service.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/comptes")
public class CompteController {
    @Autowired
    CompteService compteService;


    @GetMapping("/all")
    public List<Compte> getComptes(){
        List<Compte> comptesOptional = compteService.getComptes();
        if(comptesOptional.isEmpty()){
            return Collections.emptyList();
        } else {
            return comptesOptional;
        }
    }

    @GetMapping("/{id}")
    public Compte findCompte(@PathVariable Integer id) {
        Optional<Compte> optionalCompte = compteService.findCompte(id);
        if (optionalCompte.isPresent()) {
            return optionalCompte.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    //@PostMapping("comptes/create")
    //public Compte SaveCompte(@RequestBody Compte compte){
        //return compteService.SaveCompte(compte);
    //}

    @PostMapping("/create")
    public Compte SaveCompte(@RequestBody Compte compte) {

        Optional<Compte> optionalCompte;
        String numeroCompte;
        //Regenerer le numero de compte s'il en existe un avec le meme
        do {
            numeroCompte = generateRandomString(5).toUpperCase() + LocalDate.now().getYear();
            optionalCompte = compteService.findByNumeroCompte(numeroCompte);
        } while (optionalCompte.isPresent());

        compte.setNumeroCompte(numeroCompte);
        compte.setDateCreation(LocalDate.now());
        compte.setSolde(0.0);

        return compteService.SaveCompte(compte);
    }

    @PutMapping("/{id}")
    public Compte updateCompte(@PathVariable Integer id, @RequestBody Compte compte) {
        return compteService.updateCompte(id, compte);
    }
    @DeleteMapping("/{id}")
    public void deleteCompte(@PathVariable Integer id) {
        compteService.deleteCompte(id);
    }

    // Méthode utilitaire pour générer une chaîne aléatoire
    private String generateRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * chars.length());
            builder.append(chars.charAt(index));
        }
        return builder.toString();
    }

}

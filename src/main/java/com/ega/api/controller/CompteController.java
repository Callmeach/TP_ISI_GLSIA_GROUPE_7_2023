package com.ega.api.controller;


import com.ega.api.entity.Compte;
import com.ega.api.repository.CompteRepository;
import com.ega.api.service.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/comptes")
public class CompteController {
    private final CompteService compteService;

    @Autowired
    public CompteController(CompteService compteService) {
        this.compteService = compteService;
    }

    //@GetMapping("comptes/all")
    //public List<Compte> ShowCompte(){
        //return compteService.ShowComptes();
    //}

    @GetMapping("/all")
    public List<Compte> ShowCompte(){
        Optional<List<Compte>> comptesOptional = compteService.ShowComptes();
        if(comptesOptional.isPresent()){
            return comptesOptional.get();
        } else {
            return new ArrayList<Compte>();
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
        // Vérifier si un compte avec le même numéro de compte existe déjà
        Optional<Compte> optionalCompte = compteService.findByNumeroCompte(compte.getNumeroCompte());
        if (optionalCompte.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Un compte avec ce numéro de compte existe déjà");
        }
        // Enregistrer le compte
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




}

package com.ega.api.controller;


import com.ega.api.entity.Compte;
import com.ega.api.request.VirementRequest;
import com.ega.api.request.versementRequest;
import com.ega.api.service.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
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

    @PostMapping("/virement")
    public ResponseEntity<?> virement(@RequestBody VirementRequest virementRequest){

        Compte compteSource = compteService.findCompte(virementRequest.getCompteSource())
                .orElseThrow(()->new ResourceNotFoundException("Compte source non trouvé"));

        Compte compteDest = compteService.findCompte(virementRequest.getCompteDest())
                .orElseThrow(()-> new ResourceNotFoundException("Compte destinataire non trouvé"));

        Double montant = virementRequest.getMontant();

        Double soldeSource = compteSource.getSolde();

        if(montant > 0){
            if(montant <= soldeSource){

                Double soldeDest = compteDest.getSolde() + montant;
                soldeSource -= montant;
                compteSource.setSolde(soldeSource);
                compteDest.setSolde(soldeDest);
                compteService.SaveCompte(compteSource);
                compteService.SaveCompte(compteDest);

                return ResponseEntity.ok().body("Virement effectué avec succes");

            } else {
                return ResponseEntity.badRequest().body("Solde insuffisant");
            }
        } else {
            return ResponseEntity.badRequest().body("Montant incorrect");
        }

    }

    //@PostMapping("/{id}/versement")
    //public Compte verser(@PathVariable Integer id, @RequestBody Double montant) {
        //Optional<Compte> compteOptional = compteService.findCompte(id);
        //Compte compte = compteOptional.orElseThrow(() -> new ResourceNotFoundException("Compte non trouvée"));
        //Double solde = compte.getSolde() + montant;
        //compte.setSolde(solde);

        //return compteService.SaveCompte(compte);
   // }

    @PostMapping("/{id}/versement")
    public Compte verser(@PathVariable Integer id, @RequestBody versementRequest versementRequest) {

        Compte compte = compteService.findCompte(id)
                .orElseThrow(() -> new ResourceNotFoundException("Compte non trouvé"));
        if(versementRequest.getMontant() > 0){
            Double solde = compte.getSolde() + versementRequest.getMontant();
            compte.setSolde(solde);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Montant incorrect");
        }

        return compteService.SaveCompte(compte);
    }

    @PostMapping("/{id}/retrait")
    public Compte retirer(@PathVariable Integer id, @RequestBody versementRequest versementRequest) {

        Compte compte = compteService.findCompte(id)
                .orElseThrow(() -> new ResourceNotFoundException("Compte non trouvé"));

        Double solde = compte.getSolde();
        Double montant = versementRequest.getMontant();
        if(montant <= solde && montant > 0){
            solde -= montant;
            compte.setSolde(solde);
            return compteService.SaveCompte(compte);
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Le montant demandé est supérieur au solde disponible sur le compte ou invalide");
        }

    }


}

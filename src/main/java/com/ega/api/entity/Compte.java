package com.ega.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="comptes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Compte {

    public enum TypeCompte{
        courant, epargne
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String numeroCompte;

    @Enumerated(EnumType.STRING)
    private TypeCompte typeCompte;

    private LocalDate dateCreation;

    private Double solde;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Client client;

//    public String getNumeroCompte() {
//        return numeroCompte;
//    }
//
//    public void setNumeroCompte(String numeroCompte) {
//        this.numeroCompte = numeroCompte;
//    }
//
//    public String getTitulaire() {
//        return titulaire;
//    }
//
//    public void setTitulaire(String titulaire) {
//        this.titulaire = titulaire;
//    }
//
//    public double getSolde() {
//        return solde;
//    }
//
//    public void setSolde(double solde) {
//        this.solde = solde;
//    }
}

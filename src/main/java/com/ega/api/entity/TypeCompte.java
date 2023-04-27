package com.ega.api.entity;

public enum TypeCompte {
    Epargne("Compte Epargne"),
    Courant("Compte Courant");

    private final String libelle;

    TypeCompte(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}

package com.ega.api.request;

public class VirementRequest {
    private Integer compteSource;
    private Integer compteDest;
    private Double montant;

    public Integer getCompteSource() {
        return compteSource;
    }

    public void setCompteSource(Integer compteSource) {
        this.compteSource = compteSource;
    }

    public Integer getCompteDest() {
        return compteDest;
    }

    public void setCompteDest(Integer compteDest) {
        this.compteDest = compteDest;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }
}

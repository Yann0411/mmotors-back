package com.mmotors.mmotors;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

     private String marque;
    private String modele;
    private int annee;
    private double prix;
    private int kilometrage;

    private String typeOffre;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMarque() { return marque; }
    public void setMarque(String marque) { this.marque = marque; }

    public String getModele() { return modele; }
    public void setModele(String modele) { this.modele = modele; }

    public int getAnnee() { return annee; }
    public void setAnnee(int annee) { this.annee = annee; }

    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }

    public int getKilometrage() { return kilometrage; }
    public void setKilometrage(int kilometrage) { this.kilometrage = kilometrage; }

    public String getTypeOffre() { return typeOffre; }
    public void setTypeOffre(String typeOffre) { this.typeOffre = typeOffre; }

}

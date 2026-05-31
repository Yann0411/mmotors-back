package com.mmotors.mmotors;

import jakarta.persistence.*;

@Entity
public class Dossier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String clientEmail;
    private String typeOffre;
    private String statut;
    private String dateDepot;
    private String message;
    private String telephone;
    private String vehiculeInfo;
    private String commentaireInterne;



    public Long getId() { return id; }
    public String getClientEmail() { return clientEmail; }
    public void setClientEmail(String clientEmail) { this.clientEmail = clientEmail; }
    public String getTypeOffre() { return typeOffre; }
    public void setTypeOffre(String typeOffre) { this.typeOffre = typeOffre; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    public String getDateDepot() { return dateDepot; }
    public void setDateDepot(String dateDepot) { this.dateDepot = dateDepot; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public String getVehiculeInfo() { return vehiculeInfo; }
    public void setVehiculeInfo(String vehiculeInfo) { this.vehiculeInfo = vehiculeInfo; }
    public String getCommentaireInterne() { return commentaireInterne; }
    public void setCommentaireInterne(String commentaireInterne) { this.commentaireInterne = commentaireInterne; }



}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package netatlas.model;



public class Invitation {
    private Long id;
    private Membre expediteur;
    private Membre destinataire;
    private String statut; // EN_ATTENTE / ACCEPTEE / REFUSEE

    public Invitation(Long id, Membre exp, Membre dest) {
        this.id = id;
        this.expediteur = exp;
        this.destinataire = dest;
        this.statut = "EN_ATTENTE";
    }

    public Long getId() {
        return id;
    }

    public Membre getExpediteur() {
        return expediteur;
    }

    public Membre getDestinataire() {
        return destinataire;
    }

    public String getStatut() {
        return statut;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setExpediteur(Membre expediteur) {
        this.expediteur = expediteur;
    }

    public void setDestinataire(Membre destinataire) {
        this.destinataire = destinataire;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    
}

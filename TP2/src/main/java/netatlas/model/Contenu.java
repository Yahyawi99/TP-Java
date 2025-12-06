/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package netatlas.model;



import java.util.Date;

public class Contenu {
    private Long id;
    private Membre auteur;
    private String texte;
    private Date datePub;

    public Contenu(Long id, Membre auteur, String texte) {
        this.id = id;
        this.auteur = auteur;
        this.texte = texte;
        this.datePub = new Date();
    }

    public Long getId() {
        return id;
    }

    public Membre getAuteur() {
        return auteur;
    }

    public String getTexte() {
        return texte;
    }

    public Date getDatePub() {
        return datePub;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAuteur(Membre auteur) {
        this.auteur = auteur;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public void setDatePub(Date datePub) {
        this.datePub = datePub;
    }

    
}

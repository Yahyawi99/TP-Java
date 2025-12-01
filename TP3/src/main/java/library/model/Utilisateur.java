package main.java.library.model;

import java.util.Date;

public class Utilisateur {
  private int id;
  private String nom;
  private String prenom;
  private String email;
  private Date dateInscription;

  public Utilisateur() {
  }

  public Utilisateur(int id, String nom, String prenom, String email, Date dateInscription) {
    this.id = id;
    this.nom = nom;
    this.prenom = prenom;
    this.email = email;
    this.dateInscription = dateInscription;
  }

  public Utilisateur(String nom, String prenom, String email, Date dateInscription) {
    this.nom = nom;
    this.prenom = prenom;
    this.email = email;
    this.dateInscription = dateInscription;
  }

  // Getters and Setters
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNom() {
    return nom;
  }

  public String getPrenom() {
    return prenom;
  }

  public String getEmail() {
    return email;
  }

  public Date getDateInscription() {
    return dateInscription;
  }
}
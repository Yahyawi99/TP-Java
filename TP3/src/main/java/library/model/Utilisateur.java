package main.java.library.model;

import java.util.Date;

public class Utilisateur {
  private Long id;
  private String nom;
  private String prenom;
  private String email;
  private Date dateInscription;

  public Utilisateur(Long id, String nom, String prenom, String email, Date dateInscription) {
    this.id = id;
    this.nom = nom;
    this.prenom = prenom;
    this.email = email;
    this.dateInscription = dateInscription;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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
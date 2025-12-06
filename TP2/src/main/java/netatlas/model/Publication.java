package netatlas.model;

import java.sql.*;

public class Publication {
  private Long id;
  private Membre auteur;
  private Ressource ressource;
  private Date datePublication;
  private Enums.PublicationStatus status;

  public Publication() {
  }

  public Publication(Membre auteur, Ressource ressource, Date datePublication, Enums.PublicationStatus status) {
    this.auteur = auteur;
    this.ressource = ressource;
    this.datePublication = datePublication;
    this.status = status;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Membre getAuteur() {
    return auteur;
  }

  public void setAuteur(Membre auteur) {
    this.auteur = auteur;
  }

  public Enums.PublicationStatus getStatus() {
    return status;
  }

  public void setStatus(Enums.PublicationStatus status) {
    this.status = status;
  }

  public Ressource getRessource() {
    return ressource;
  }

  public void setRessource(Ressource ressource) {
    this.ressource = ressource;
  }

  public Date getDatePublication() {
    return datePublication;
  }

  public void setDatePublication(Date datePublication) {
    this.datePublication = datePublication;
  }
}
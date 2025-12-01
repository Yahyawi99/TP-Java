package main.java.library.model;

import java.util.Date;

public abstract class DocumentBibliotheque implements Empruntable {
  protected int id;
  protected String titre;
  protected Date anneePublication;
  protected boolean disponible;

  public DocumentBibliotheque(int id, String titre, Date anneePublication, boolean disponible) {
    this.id = id;
    this.titre = titre;
    this.anneePublication = anneePublication;
    this.disponible = disponible;
  }

  public abstract String getType();

  public abstract int getDureeEmpruntMax();

  @Override
  public boolean emprunter(Utilisateur utilisateur, Date dateEmprunt) {
    if (this.disponible) {
      this.disponible = false;
      return true;
    }
    return false;
  }

  @Override
  public boolean retourner(Date dateRetour) {
    this.disponible = true;
    return true;
  }

  @Override
  public boolean isDisponible() {
    return this.disponible;
  }

  // Getters and Setters
  public int getId() {
    return id;
  }

  public String getTitre() {
    return titre;
  }

  public void setTitre(String titre) {
    this.titre = titre;
  }

  public Date getAnneePublication() {
    return anneePublication;
  }

  public void setAnneePublication(Date anneePublication) {
    this.anneePublication = anneePublication;
  }

  public boolean getDisponible() {
    return disponible;
  }

  public void setDisponible(boolean disponible) {
    this.disponible = disponible;
  }
}
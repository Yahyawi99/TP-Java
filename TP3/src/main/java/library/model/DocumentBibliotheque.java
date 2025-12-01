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
  public String toString() {
    return "ID: " + id + ", Titre: " + titre + ", Année: " + anneePublication;
  }

  @Override
  public boolean emprunter(Utilisateur utilisateur, Date dateEmprunt) {
    if (isDisponible()) {
      this.disponible = false;
      System.out.println(titre + " emprunté par " + utilisateur.getNom());
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

  // Getters and Setters (omitted for brevity)
  // ...
}

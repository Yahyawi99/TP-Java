package main.java.library.model;

import java.util.Date;

public class Magazine extends DocumentBibliotheque {
  private int numeroEdition;
  private String mois;
  private String editeur;

  public Magazine(int id, String titre, Date anneePublication, boolean disponible,
      int numeroEdition, String mois, String editeur) {
    super(id, titre, anneePublication, disponible);
    this.numeroEdition = numeroEdition;
    this.mois = mois;
    this.editeur = editeur;
  }

  @Override
  public String getType() {
    return "MAGAZINE";
  }

  @Override
  public int getDureeEmpruntMax() {
    return 7;
  }

  // Getters
  public int getNumeroEdition() {
    return numeroEdition;
  }

  public String getMois() {
    return mois;
  }

  public String getEditeur() {
    return editeur;
  }
}
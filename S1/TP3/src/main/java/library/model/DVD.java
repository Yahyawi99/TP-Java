package main.java.library.model;

import java.util.Date;

public class DVD extends DocumentBibliotheque {
  private String realisateur;
  private int duree;
  private String genre;
  private String classification;

  public DVD(int id, String titre, Date anneePublication, boolean disponible,
      String realisateur, int duree, String genre, String classification) {
    super(id, titre, anneePublication, disponible);
    this.realisateur = realisateur;
    this.duree = duree;
    this.genre = genre;
    this.classification = classification;
  }

  @Override
  public String getType() {
    return "DVD";
  }

  @Override
  public int getDureeEmpruntMax() {
    return 14;
  }

  public String getRealisateur() {
    return realisateur;
  }

  public String getGenre() {
    return genre;
  }

  public int getDuree() {
    return duree;
  }

  public String getClassification() {
    return classification;
  }
}
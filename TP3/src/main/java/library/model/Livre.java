package main.java.library.model;

import java.util.Date;

public class Livre extends DocumentBibliotheque {
  private String auteur;
  private String isbn; // Added for completeness, if not used, remove.
  private int nombrePages;
  private String genre;

  public Livre(int id, String titre, Date anneePublication, boolean disponible,
      String auteur, int nombrePages, String isbn, String genre) {
    super(id, titre, anneePublication, disponible);
    this.auteur = auteur;
    this.nombrePages = nombrePages;
    this.isbn = isbn;
    this.genre = genre;
  }

  @Override
  public String getType() {
    return "LIVRE";
  }

  @Override
  public int getDureeEmpruntMax() {
    return 21; // Example: 3 weeks
  }
  // Getters/Setters/toString...
}

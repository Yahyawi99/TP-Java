package main.java.library.model;

import java.util.Date;

public class Livre extends DocumentBibliotheque {
  private String auteur;
  private String isbn;
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
    return 21;
  }

  public String getAuteur() {
    return auteur;
  }

  public String getGenre() {
    return genre;
  }

  public String getIsbn() {
    return isbn;
  }

  public int getNombrePages() {
    return nombrePages;
  }
}
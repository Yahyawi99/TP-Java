package main.java.library.model;

import java.util.List;

public interface Recherchable {
  List<DocumentBibliotheque> rechercherParTitre(String titre);

  List<DocumentBibliotheque> rechercherParAuteur(String auteur);

  List<DocumentBibliotheque> rechercherParGenre(String genre);
}
package main.java.library.model;

import java.util.Date;

public interface Empruntable {
  boolean emprunter(Utilisateur utilisateur, Date dateEmprunt);

  boolean retourner(Date dateRetour);

  boolean isDisponible();
}
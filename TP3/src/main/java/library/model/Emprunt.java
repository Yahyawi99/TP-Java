package main.java.library.model;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Emprunt {
  private int id;
  private Utilisateur utilisateur;
  private DocumentBibliotheque document;
  private Date dateEmprunt;
  private Date dateRetourPrevue;

  public Emprunt(int id, Utilisateur utilisateur, DocumentBibliotheque document, Date dateEmprunt,
      Date dateRetourPrevue) {
    this.id = id;
    this.utilisateur = utilisateur;
    this.document = document;
    this.dateEmprunt = dateEmprunt;
    this.dateRetourPrevue = dateRetourPrevue;
  }

  public Emprunt(Utilisateur utilisateur, DocumentBibliotheque document, Date dateEmprunt, Date dateRetourPrevue) {
    this.utilisateur = utilisateur;
    this.document = document;
    this.dateEmprunt = dateEmprunt;
    this.dateRetourPrevue = dateRetourPrevue;
  }

  public double calculerPenalites(Date dateRetourReelle) {
    if (dateRetourReelle == null) {
      return 0.0;
    }

    if (dateRetourReelle.after(dateRetourPrevue)) {
      long diffInMillies = Math.abs(dateRetourReelle.getTime() - dateRetourPrevue.getTime());
      long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
      return diff * 0.50;
    }
    return 0.0;
  }

  // Getters and Setters
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Utilisateur getUtilisateur() {
    return utilisateur;
  }

  public DocumentBibliotheque getDocument() {
    return document;
  }

  public Date getDateEmprunt() {
    return dateEmprunt;
  }

  public Date getDateRetourPrevue() {
    return dateRetourPrevue;
  }

}

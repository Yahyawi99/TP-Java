package main.java.library.service;

import main.java.library.dao.*;
import main.java.library.model.*;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class BibliothequeService implements Recherchable {

  private final UtilisateurDAO utilisateurDAO;
  private final DocumentDAO documentDAO;
  private final EmpruntDAO empruntDAO;

  public BibliothequeService() {
    this.utilisateurDAO = new UtilisateurDAO();
    this.documentDAO = new DocumentDAO();
    this.empruntDAO = new EmpruntDAO();
  }

  // =======================================================

  public boolean emprunterDocument(int utilisateurId, int documentId) {
    Utilisateur user = utilisateurDAO.findById(utilisateurId);
    DocumentBibliotheque doc = documentDAO.findById(documentId);

    if (user == null) {
      System.err.println("Erreur: Utilisateur ID " + utilisateurId + " non trouvé.");
      return false;
    }
    if (doc == null) {
      System.err.println("Erreur: Document ID " + documentId + " non trouvé.");
      return false;
    }
    if (!doc.getDisponible()) {
      System.err.println("Erreur: Document ID " + documentId + " n'est pas disponible.");
      return false;
    }

    try {
      Date dateEmprunt = new Date();
      // Calcul de la date de retour prévue
      long dureeMaxMillis = TimeUnit.DAYS.toMillis(doc.getDureeEmpruntMax());
      Date dateRetourPrevue = new Date(dateEmprunt.getTime() + dureeMaxMillis);

      Emprunt nouvelEmprunt = new Emprunt(user, doc, dateEmprunt, dateRetourPrevue);

      if (empruntDAO.create(nouvelEmprunt)) {
        doc.setDisponible(false);
        if (documentDAO.update(doc)) {
          System.out.println("Emprunt réussi pour le document ID " + documentId + " par utilisateur " + utilisateurId);
          return true;
        } else {
          System.err.println("Avertissement: Emprunt créé mais échec de la mise à jour du document. Incohérence BD.");
          System.err.println("ROLLBACK!");
          return false;
        }
      }
    } catch (Exception e) {
      System.err.println("Erreur lors de l'emprunt: " + e.getMessage());
    }
    return false;
  }

  public double retournerDocument(int empruntId) {
    Emprunt emprunt = empruntDAO.findById(empruntId);

    if (emprunt == null) {
      System.err.println("Erreur: Emprunt ID " + empruntId + " non trouvé.");
      return -1;
    }

    try {
      Date dateRetourEffective = new Date();

      double penalite = emprunt.calculerPenalites(dateRetourEffective);

      if (empruntDAO.update(emprunt)) {
        DocumentBibliotheque doc = emprunt.getDocument();
        doc.setDisponible(true);
        documentDAO.update(doc);

        System.out.println("Retour réussi. Pénalité : " + penalite);
        return penalite;
      }
    } catch (Exception e) {
      System.err.println("Erreur lors du retour du document: " + e.getMessage());
    }
    return 0;
  }

  // =======================================================

  @Override
  public List<DocumentBibliotheque> rechercherParTitre(String titre) {
    return documentDAO.rechercherParTitre(titre);
  }

  @Override
  public List<DocumentBibliotheque> rechercherParAuteur(String auteur) {
    return documentDAO.rechercherParAuteur(auteur);
  }

  @Override
  public List<DocumentBibliotheque> rechercherParGenre(String genre) {
    return documentDAO.rechercherParGenre(genre);
  }

  // =======================================================

  // --- Utilisateurs ---
  public Utilisateur findUtilisateurById(int id) {
    return utilisateurDAO.findById(id);
  }

  public List<Utilisateur> getAllUtilisateurs() {
    return utilisateurDAO.getAll();
  }

  public boolean createUtilisateur(Utilisateur user) {
    return utilisateurDAO.create(user);
  }

  public boolean updateUtilisateur(Utilisateur user) {
    return utilisateurDAO.update(user);
  }

  public boolean deleteUtilisateur(int id) {
    return utilisateurDAO.delete(id);
  }

  // --- Documents ---
  public DocumentBibliotheque findDocumentById(int id) {
    return documentDAO.findById(id);
  }

  public List<DocumentBibliotheque> getAllDocuments() {
    return documentDAO.getAll();
  }

  public boolean createDocument(DocumentBibliotheque doc) {
    return documentDAO.create(doc);
  }

  public boolean updateDocument(DocumentBibliotheque doc) {
    return documentDAO.update(doc);
  }

  public boolean deleteDocument(int id) {
    return documentDAO.delete(id);
  }

  // --- Emprunts ---
  public Emprunt findEmpruntById(int id) {
    return empruntDAO.findById(id);
  }

  public List<Emprunt> getAllEmprunts() {
    return empruntDAO.getAll();
  }

  public boolean deleteEmprunt(int id) {
    return empruntDAO.delete(id);
  }

  // ==========================================
  public double calculerPenaliteTotaleUtilisateur(int userId) {
    List<Emprunt> tousEmprunts = empruntDAO.getAll();

    double penaliteTotale = tousEmprunts.stream()
        .filter(e -> e.getUtilisateur().getId() == userId)
        .mapToDouble(e -> e.calculerPenalites(new Date()))
        .sum();

    return penaliteTotale;
  }
}

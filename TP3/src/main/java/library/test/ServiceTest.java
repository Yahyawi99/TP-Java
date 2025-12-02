package main.java.library.test;

import main.java.library.dao.*;
import main.java.library.service.*;
import main.java.library.model.*;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

class ServiceTest {

  private UtilisateurDAO utilisateurDAO;
  private DocumentDAO documentDAO;
  private EmpruntDAO empruntDAO;

  private BibliothequeService service;

  private Utilisateur mockUser;
  private Livre mockLivre;

  void setUp() {
    mockUser = new Utilisateur(1, "User", "Test", "test@test.com", new Date());
    mockLivre = new Livre(10, "Mock Livre", new Date(), true, "Auteur", 100, "123", "Science");
  }

  void testEmprunterDocument_Succes() {
    utilisateurDAO.findById(1);
    documentDAO.findById(10);

    boolean result = service.emprunterDocument(1, 10);

  }

  void testEmprunterDocument_EchecDocumentNonDisponible() {
    // Configuration: Document non disponible
    mockLivre.setDisponible(false);
    utilisateurDAO.findById(1);
    documentDAO.findById(10);

    // Exécution
    boolean result = service.emprunterDocument(1, 10);
  }

  void testRetournerDocument_PenaliteCalculee() {
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    cal.add(Calendar.DAY_OF_YEAR, -5);
    Date dateRetourPrevue = cal.getTime();

    Emprunt mockEmprunt = new Emprunt(50, mockUser, mockLivre, new Date(), dateRetourPrevue);

    empruntDAO.findById(50);

    double penalite = service.retournerDocument(50);
  }
}
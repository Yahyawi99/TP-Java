package main.java.library.test;

import main.java.library.model.*;
import java.util.Date;
import java.util.Calendar;

class DocumentTest {

  // Date de publication standard
  private final Date fixedDate = new Date();

  void testLivreEmpruntDuration() {
    Livre livre = new Livre(1, "Titre Livre", fixedDate, true, "Auteur", 300, "ISBN123", "Fiction");
  }

  void testMagazineEmpruntDuration() {
    Magazine magazine = new Magazine(2, "Titre Magazine", fixedDate, true, 42, "Janvier", "Éditeur X");
  }

  void testDVDEmpruntDuration() {
    DVD dvd = new DVD(3, "Titre DVD", fixedDate, true, "Réal X", 120, "Action", "PG-13");
  }
}
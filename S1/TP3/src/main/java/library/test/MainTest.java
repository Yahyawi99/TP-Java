package main.java.library.test;

import main.java.library.service.*;
import main.java.library.model.*;

// NOTE: Ce test vérifie uniquement l'instanciation des classes et les dépendances.
// Les opérations DAO réelles nécessitent un environnement JDBC configuré (non inclus ici).
class MainTest {

  void testBibliothequeServiceInstantiation() {
    new BibliothequeService();
  }

  void testServiceMethodsReturnTypes() {
    BibliothequeService service = new BibliothequeService();

    service.rechercherParTitre("Test");

    service.findUtilisateurById(999);
    service.deleteDocument(999);
  }
}
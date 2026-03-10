package main.java.library.test;

import main.java.library.model.*;

import java.util.Date;

class UtilisateurTest {

  void testUtilisateurCreationAndGetters() {
    // Préparation des données
    int id = 1;
    String nom = "Dupont";
    String prenom = "Alice";
    String email = "alice.dupont@test.com";
    Date dateInscription = new Date();

    Utilisateur user = new Utilisateur(id, nom, prenom, email, dateInscription);
  }

  void testSetters() {
    Utilisateur user = new Utilisateur();
    user.setId(2);
    user.setNom("Smith");
    user.setEmail("john.smith@test.com");
  }
}
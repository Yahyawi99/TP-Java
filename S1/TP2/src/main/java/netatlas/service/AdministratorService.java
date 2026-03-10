package netatlas.service;

import netatlas.dao.MembreDAO;
import netatlas.model.Administrator;
import netatlas.model.Membre;

public class AdministratorService {

  private final MembreDAO membreDAO;

  public AdministratorService(MembreDAO membreDAO) {
    this.membreDAO = membreDAO;
  }

  public boolean supprimerCompte(Membre m) {
    Membre membre = membreDAO.findById(m.getId());

    if (membre == null) {
      System.err.println("Erreur: Membre non trouvé.");
      return false;
    }

    if (membre.getNbAvertissements() >= 3) {

      membreDAO.delete(m.getId());
      return true;

    } else {
      System.out.println(
          "Impossible de supprimer " + m.getNom() + ": seulement " + m.getNbAvertissements() + " avertissement(s).");
      return false;
    }
  }

  public boolean validerInscription(Membre m) {
    if (!(m instanceof Administrator)) {
      Membre membre = membreDAO.findById(m.getId());

      if (membre == null) {
        System.err.println("Erreur: Membre non trouvé.");
        return false;
      }

      if (m.getNbAvertissements() < 3) {
        m.setIsActive(true);

        membreDAO.update(m);
        return true;

      } else {
        System.out.println(
            "Impossible de valider " + m.getNom() + ": seulement " + m.getNbAvertissements() + " avertissement(s).");
        return false;
      }
    }
    return false;
  }
}
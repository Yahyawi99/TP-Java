package netatlas.service;

import netatlas.dao.*;
import netatlas.model.*;

public class ModeratorService {

  private final PublicationDAO publicationDAO;
  private final MembreDAO membreDAO;

  public ModeratorService(PublicationDAO publicationDAO, MembreDAO membreDAO) {
    this.publicationDAO = publicationDAO;
    this.membreDAO = membreDAO;
  }

  public boolean avertirMembre(Membre m) {
    if (!(m instanceof Moderator)) {
      Membre membre = membreDAO.findById(m.getId());

      if (membre == null) {
        System.err.println("Erreur: Membre non trouvé.");
        return false;
      }

      if (m.getNbAvertissements() < 3) {
        m.setNbAvertissements(m.getNbAvertissements() + 1);

        membreDAO.update(m);
        return true;

      } else {
        System.out.println(
            "Impossible de avertir " + m.getNom() + ": seulement " + m.getNbAvertissements() + " avertissement(s).");
        return false;
      }
    }
    return false;
  }

  public boolean consulterContenu(Publication P, Enums.PublicationStatus newStatus) {
    Publication pub = publicationDAO.findById(P.getId());

    if (pub == null) {
      System.err.println("Erreur: publication non trouvé.");
      return false;
    }

    P.setStatus(newStatus);

    publicationDAO.update(P);
    return true;

  }
}

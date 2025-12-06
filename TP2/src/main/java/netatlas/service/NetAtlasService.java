package netatlas.service;

import java.time.LocalDate;

import netatlas.dao.*;
import netatlas.model.*;
import java.sql.*;

public class NetAtlasService {
  private final MembreDAO membreDAO = new MembreDAO();
  private final PublicationDAO publicationDAO = new PublicationDAO();
  private final InvitationDAO invitationDAO = new InvitationDAO();

  public boolean register(Membre m) {
    if (membreDAO.findByEmail(m.getEmail()) != null) {
      throw new IllegalArgumentException("L'adresse e-mail est déjà utilisée.");
    }

    boolean isCreated = membreDAO.create(m);

    return isCreated;
  }

  public boolean login(Membre m) {
    Membre membre = membreDAO.findByEmail(m.getEmail());

    if (membre == null || !membreDAO.verifyPassword(m.getHashedPassword(), membre.getHashedPassword())) {
      return false;
    }

    if (membre.isBanned()) {
      System.out.println("Compte banni. Connexion refusée.");
      return false;
    }

    System.out.println(membre.getNom() + " s'est connecté avec succès.");
    return true;
  }

  public boolean inviteFriend(Long inviterId, Long recipientId) {
    Membre inviter = membreDAO.findById(inviterId);
    Membre recipient = membreDAO.findById(recipientId);

    if (inviter == null || recipient == null) {
      return false;
    }

    Invitation inv = new Invitation(inviter, recipient);

    boolean isSuccess = invitationDAO.create(inv);

    System.out.println(inviter.getNom() + " a invité " + recipient.getNom() + " en ami.");
    return isSuccess;
  }

  public boolean acceptFriend(Long memberId, Long friendId) {
    Membre member = membreDAO.findById(memberId);
    Membre friend = membreDAO.findById(friendId);

    Invitation inv = invitationDAO.findBySenderIdAndRecipientId(member, friend);

    Invitation invData = new Invitation(inv.getSender(), inv.getRecipient(), inv.getDateSent(), inv.getStatus());

    invData.setStatus(Enums.InvitationStatus.ACCEPTEE);

    boolean isSuccess = invitationDAO.update(invData);
    System.out.println(member.getNom() + " et " + friend.getNom() + " sont maintenant amis.");
    return isSuccess;
  }

  public boolean removeFriend(Long memberId, Long friendId) {
    System.out.println("Amitié retirée entre les membres " + memberId + " et " + friendId);
    return true;
  }

  public boolean publierContenu(Long auteurId, Ressource ressource) {
    Membre auteur = membreDAO.findById(auteurId);

    if (auteur == null) {
      throw new IllegalArgumentException("Auteur non trouvé.");
    }

    Publication publication = new Publication(auteur, ressource, Date.valueOf(LocalDate.now()),
        Enums.PublicationStatus.EN_ATTENTE_MODERATION);

    return publicationDAO.create(publication);
  }
}
package netatlas.model;

public class Enums {
  public enum Role {
    MEMBRE,
    MODERATEUR,
    ADMINISTRATEUR
  }

  public enum PublicationStatus {
    EN_ATTENTE_MODERATION,
    APPROUVEE,
    SIGNALEE,
  }

  public enum RessourceType {
    PHOTO,
    VIDEO,
    LIEN,
    MESSAGE
  }

  public enum InvitationStatus {
    EN_ATTENTE,
    ACCEPTEE,
    REFUSEE
  }
}

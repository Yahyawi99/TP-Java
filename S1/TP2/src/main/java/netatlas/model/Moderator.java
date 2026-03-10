package netatlas.model;

public class Moderator extends Membre {

  public void consulterContenu(Publication publication) {
    System.out.println("Modérateur " + this.nom + " consulte la publication.");
  }

  public void avertirMembre(Membre membreCible) {
    if (!(membreCible instanceof Moderator)) {
      membreCible.nbAvertissements++;
    }
  }
}
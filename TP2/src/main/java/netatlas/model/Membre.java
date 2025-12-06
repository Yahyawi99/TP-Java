package netatlas.model;

import java.util.ArrayList;
import java.util.List;

public class Membre {
    protected Long id;
    protected String email;
    protected String nom;
    protected String prenom;
    protected boolean isActive;
    protected int nbAvertissements;

    protected List<Membre> amis = new ArrayList<>();

    public Membre() {
    }

    public Membre(Long id, String email, String nom, String prenom) {
        this.id = id;
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.isActive = false;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public int getNbAvertissements() {
        return nbAvertissements;
    }

    public List<Membre> getAmis() {
        return amis;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setNbAvertissements(int nbAvertissements) {
        this.nbAvertissements = nbAvertissements;
    }

    public void setAmis(List<Membre> amis) {
        this.amis = amis;
    }

    public void ajouterAmi(Membre m) {
        if (!amis.contains(m))
            amis.add(m);
    }

    public void retirerAmi(Membre m) {
        amis.remove(m);
    }

    public boolean isBanned() {
        return this.nbAvertissements >= 3;
    }

    @Override
    public String toString() {
        return nom + " " + prenom + " (" + email + ")";
    }
}

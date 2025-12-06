/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package netatlas.service;

import netatlas.dao.*;
import netatlas.model.*;

public class NetAtlasService {

    private MembreDAO membreDAO = new MembreDAO();
    private InvitationDAO invitationDAO = new InvitationDAO();
    private ContenuDAO contenuDAO = new ContenuDAO();

    public void inscrire(Membre m) {
        membreDAO.create(m);
        System.out.println("Inscription en attente pour : " + m.getEmail());
    }

    public void valider(Membre m) {
        m.setIsActive(true);
        membreDAO.update(m);
        System.out.println("Inscription validée.");
    }

    public void inviter(Membre exp, Membre dest) {
        Invitation inv = new Invitation(null, exp, dest);
        invitationDAO.save(inv);
        System.out.println(exp.getNom() + " invite " + dest.getNom());
    }

    public void publier(Membre m, String texte) {
        Contenu c = new Contenu(null, m, texte);
        contenuDAO.save(c);
        System.out.println("Contenu publié.");
    }
}

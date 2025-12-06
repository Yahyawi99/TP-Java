/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package netatlas.test;



import netatlas.model.*;
import netatlas.service.NetAtlasService;

public class TestNetAtlasDB {

    public static void main(String[] args) {

        NetAtlasService service = new NetAtlasService();

        Membre m1 = new Membre(null, "SARAS@gmail.com", "SARA", "SAKHI");
        Membre m2 = new Membre(null, "sALMA@gmail.com", "SaLMA", "AOUZAL");

        service.inscrire(m1);
        service.inscrire(m2);

        service.valider(m1);
        service.valider(m2);

        service.inviter(m1, m2);

        service.publier(m1, "Bonjour à tous !");
    }
}

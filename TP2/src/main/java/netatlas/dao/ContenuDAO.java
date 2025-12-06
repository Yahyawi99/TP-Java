/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package netatlas.dao;



import netatlas.db.ConnectionDB;
import netatlas.model.Contenu;
import netatlas.model.Membre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContenuDAO {

    public boolean save(Contenu c) {
        String sql = "INSERT INTO contenu(auteur, texte, datePub) VALUES(?,?,?)";

        try (Connection cn = ConnectionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setLong(1, c.getAuteur().getId());
            ps.setString(2, c.getTexte());
            ps.setTimestamp(3, new Timestamp(c.getDatePub().getTime()));

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

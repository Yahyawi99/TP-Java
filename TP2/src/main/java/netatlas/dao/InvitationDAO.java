package netatlas.dao;

import netatlas.connexion.*;
import netatlas.model.Invitation;
import netatlas.model.Membre;

import java.sql.*;
import java.util.*;

public class InvitationDAO {

    public boolean save(Invitation inv) {
        String sql = "INSERT INTO invitation(expediteur, destinataire, statut) VALUES(?,?,?)";

        try (Connection cn = ConnectionDB.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, inv.getExpediteur().getId());
            ps.setLong(2, inv.getDestinataire().getId());
            ps.setString(3, inv.getStatut());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

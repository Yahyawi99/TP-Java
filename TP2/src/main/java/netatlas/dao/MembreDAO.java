
package netatlas.dao;

import netatlas.connexion.Connexion;
import netatlas.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembreDAO implements IDAO<Membre> {

    public boolean update(Membre data) {
        String sql = "UPDATE membres SET email=?, nom=?, prenom=?, active=?, avertissements=? WHERE id=?";

        try {
            PreparedStatement stmt = Connexion.getInstance().getCn().prepareStatement(sql);

            stmt.setString(1, data.getEmail());
            stmt.setString(2, data.getNom());
            stmt.setString(3, data.getPrenom());
            stmt.setBoolean(4, data.isIsActive());
            stmt.setInt(5, data.getNbAvertissements());
            stmt.setLong(6, data.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean create(Membre m) {
        String sql = "INSERT INTO membres(email, nom, prenom, active, avertissements) VALUES(?,?,?,?,?)";

        try {
            PreparedStatement stmt = Connexion.getInstance().getCn().prepareStatement(sql);

            stmt.setString(1, m.getEmail());
            stmt.setString(2, m.getNom());
            stmt.setString(3, m.getPrenom());
            stmt.setBoolean(4, false);
            stmt.setInt(5, m.getNbAvertissements());

            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next())
                m.setId(keys.getLong(1));

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Membre findById(Long id) {
        String sql = "SELECT * FROM membres WHERE id=?";

        try {
            PreparedStatement stmt = Connexion.getInstance().getCn().prepareStatement(sql);

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return map(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Membre findByEmail(String email) {
        String sql = "SELECT * FROM membres WHERE email=?";

        try {
            PreparedStatement stmt = Connexion.getInstance().getCn().prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return map(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean delete(Long id) {
        String SQL = "DELETE FROM members WHERE id = ?";
        try (PreparedStatement stmt = Connexion.getInstance().getCn().prepareStatement(SQL)) {
            stmt.setLong(1, id);
            if (stmt.executeUpdate() != 0) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erreur delete member: " + e.getMessage());
        }
        return false;
    }

    public List<Membre> findAll() {
        List<Membre> members = new ArrayList<>();
        String SQL = "SELECT * FROM members";

        try (PreparedStatement stmt = Connexion.getInstance().getCn().prepareStatement(SQL);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Membre mem = map(rs);
                if (mem != null) {
                    members.add(mem);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur getAll Document: " + e.getMessage());
        }
        return members;
    }

    private Membre map(ResultSet rs) throws SQLException {
        Membre m = new Membre();
        m.setId(rs.getLong("id"));
        m.setEmail(rs.getString("email"));
        m.setNom(rs.getString("nom"));
        m.setPrenom(rs.getString("prenom"));
        m.setIsActive(rs.getBoolean("active"));
        m.setNbAvertissements(rs.getInt("avertissements"));
        return m;
    }
}

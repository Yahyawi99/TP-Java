
package netatlas.dao;

import netatlas.connexion.*;
import netatlas.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class PublicationDAO implements IDAO<Publication> {
    private final MembreDAO membreDAO = new MembreDAO();
    private final RessourceDAO ressourceDAO = new RessourceDAO();

    public boolean update(Publication data) {
        String sql = "UPDATE publications SET auteurId=?, ressourceId=?, datePublication = ?, status = ? WHERE id=?";

        try {
            PreparedStatement stmt = Connexion.getInstance().getCn().prepareStatement(sql);

            stmt.setLong(1, data.getAuteur().getId());
            stmt.setLong(2, data.getRessource().getId());

            data.setDatePublication(java.sql.Date.valueOf(LocalDate.now()));
            stmt.setDate(3, data.getDatePublication());

            stmt.setString(4, data.getStatus() + "");

            stmt.setLong(5, data.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean create(Publication data) {
        String sql = "INSERT INTO publications( authorId, ressourceId, datePublication) VALUES(?,?,?)";

        try {
            PreparedStatement stmt = Connexion.getInstance().getCn().prepareStatement(sql);

            stmt.setLong(1, data.getAuteur().getId());
            stmt.setLong(2, data.getRessource().getId());

            data.setDatePublication(java.sql.Date.valueOf(LocalDate.now()));
            stmt.setDate(3, data.getDatePublication());

            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next())
                data.setId(keys.getLong(1));

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Publication findById(Long id) {
        String sql = "SELECT * FROM publications WHERE id=?";

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

    public boolean delete(Long id) {
        String SQL = "DELETE FROM publications WHERE id = ?";
        try (PreparedStatement stmt = Connexion.getInstance().getCn().prepareStatement(SQL)) {
            stmt.setLong(1, id);
            if (stmt.executeUpdate() != 0) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erreur delete publications: " + e.getMessage());
        }
        return false;
    }

    public List<Publication> findAll() {
        List<Publication> publications = new ArrayList<>();
        String SQL = "SELECT * FROM publications";

        try (PreparedStatement stmt = Connexion.getInstance().getCn().prepareStatement(SQL);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Publication pub = map(rs);
                if (pub != null) {
                    publications.add(pub);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur getAll Document: " + e.getMessage());
        }
        return publications;
    }

    private Publication map(ResultSet rs) throws SQLException {
        Publication p = new Publication();
        p.setId(rs.getLong("id"));

        Long authorId = rs.getLong("authorId");
        Membre m = membreDAO.findById(authorId);
        p.setAuteur(m);

        Long ressourceId = rs.getLong("ressourceId");
        Ressource r = ressourceDAO.findById(ressourceId);
        p.setRessource(r);

        p.setStatus(Enums.PublicationStatus.valueOf(rs.getString("status")));
        p.setDatePublication(rs.getDate("datePublication"));

        return p;
    }
}
package netatlas.dao;

import netatlas.connexion.*;
import netatlas.model.*;

import java.sql.*;
import java.time.*;
import java.util.*;

// Long id;
//   Membre sender;
//   Membre recipient;
//   Enums.InvitationStatus status;
//   Date dateSent;

public class InvitationDAO implements IDAO<Invitation> {
    public final MembreDAO membreDAO = new MembreDAO();

    public boolean update(Invitation data) {
        String sql = "UPDATE invitations SET senderId=?, recipientId=?,  status = ? WHERE id=?";

        try {
            PreparedStatement stmt = Connexion.getInstance().getCn().prepareStatement(sql);

            stmt.setLong(1, data.getSender().getId());
            stmt.setLong(2, data.getRecipient().getId());

            stmt.setString(4, data.getStatus() + "");

            stmt.setLong(5, data.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean create(Invitation data) {
        String sql = "INSERT INTO invitations(senderId, recipientId, dateSent) VALUES(?,?,?)";

        try {
            PreparedStatement stmt = Connexion.getInstance().getCn().prepareStatement(sql);

            stmt.setLong(1, data.getSender().getId());
            stmt.setLong(2, data.getRecipient().getId());

            data.setDateSent(java.sql.Date.valueOf(LocalDate.now()));
            stmt.setDate(3, data.getDateSent());

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

    public Invitation findById(Long id) {
        String sql = "SELECT * FROM invitations WHERE id=?";

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

    public Invitation findBySenderIdAndRecipientId(Membre sender, Membre recipient) {
        String sql = "SELECT * FROM invitations WHERE senderId=? AND recipientId=?";

        try {
            PreparedStatement stmt = Connexion.getInstance().getCn().prepareStatement(sql);

            stmt.setLong(1, sender.getId());
            stmt.setLong(2, recipient.getId());

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
        String SQL = "DELETE FROM invitations WHERE id = ?";
        try (PreparedStatement stmt = Connexion.getInstance().getCn().prepareStatement(SQL)) {
            stmt.setLong(1, id);
            if (stmt.executeUpdate() != 0) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erreur delete invitations: " + e.getMessage());
        }
        return false;
    }

    public List<Invitation> findAll() {
        List<Invitation> invitations = new ArrayList<>();
        String SQL = "SELECT * FROM invitations";

        try (PreparedStatement stmt = Connexion.getInstance().getCn().prepareStatement(SQL);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Invitation pub = map(rs);
                if (pub != null) {
                    invitations.add(pub);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur getAll Document: " + e.getMessage());
        }
        return invitations;
    }

    private Invitation map(ResultSet rs) throws SQLException {
        Invitation inv = new Invitation();
        inv.setId(rs.getLong("id"));

        Long senderId = rs.getLong("senderId");
        Membre m = membreDAO.findById(senderId);
        inv.setSender(m);

        Long recipientId = rs.getLong("recipientId");
        Membre m2 = membreDAO.findById(recipientId);
        inv.setRecipient(m2);

        inv.setStatus(Enums.InvitationStatus.valueOf(rs.getString("status")));
        inv.setDateSent(rs.getDate("dateSent"));

        return inv;
    }
}

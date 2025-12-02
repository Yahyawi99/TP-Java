package main.java.library.dao;

import main.java.library.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import main.java.library.connexion.DatabaseManager;

public class EmpruntDAO implements IDAO<Emprunt> {

  private final UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
  private final DocumentDAO documentDAO = new DocumentDAO();

  private Emprunt mapResultSetToEmprunt(ResultSet rs) throws SQLException {
    int empruntId = rs.getInt("id");
    int utilisateurId = rs.getInt("utilisateur_id");
    int documentId = rs.getInt("document_id");

    Date dateEmprunt = rs.getDate("date_emprunt");
    Date dateRetourPrevue = rs.getDate("date_retour_prevue");

    Utilisateur user = utilisateurDAO.findById(utilisateurId);
    DocumentBibliotheque doc = documentDAO.findById(documentId);

    if (user != null && doc != null) {
      return new Emprunt(empruntId, user, doc,
          new java.util.Date(dateEmprunt.getTime()),
          new java.util.Date(dateRetourPrevue.getTime()));
    }

    throw new SQLException("Impossible de charger l'utilisateur ou le document pour l'emprunt ID: " + empruntId);
  }

  @Override
  public Emprunt findById(int id) {
    String SQL = "SELECT * FROM emprunts WHERE id = ?";
    try (PreparedStatement stmt = DatabaseManager.getInstance().getCn().prepareStatement(SQL)) {
      stmt.setLong(1, id);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return mapResultSetToEmprunt(rs);
        }
      }
    } catch (SQLException e) {
      System.err.println("Erreur findById Emprunt: " + e.getMessage());
    }
    return null;
  }

  @Override
  public List<Emprunt> getAll() {
    List<Emprunt> emprunts = new ArrayList<>();
    String SQL = "SELECT * FROM emprunts ORDER BY date_emprunt DESC";

    try (PreparedStatement stmt = DatabaseManager.getInstance().getCn().prepareStatement(SQL);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        try {
          emprunts.add(mapResultSetToEmprunt(rs));
        } catch (SQLException e) {
          System.err
              .println("Erreur lors du chargement d'un emprunt (ID: " + rs.getLong("id") + "): " + e.getMessage());
        }
      }
    } catch (SQLException e) {
      System.err.println("Erreur getAll Emprunt: " + e.getMessage());
    }
    return emprunts;
  }

  @Override
  public boolean create(Emprunt data) {
    String SQL = "INSERT INTO emprunts (utilisateur_id, document_id, date_emprunt, date_retour_prevue) " +
        "VALUES (?, ?, ?, ?)";
    try (
        PreparedStatement stmt = DatabaseManager.getInstance().getCn().prepareStatement(SQL,
            Statement.RETURN_GENERATED_KEYS)) {

      stmt.setLong(1, data.getUtilisateur().getId());
      stmt.setLong(2, data.getDocument().getId());
      stmt.setDate(3, new java.sql.Date(data.getDateEmprunt().getTime()));
      stmt.setDate(4, new java.sql.Date(data.getDateRetourPrevue().getTime()));

      if (stmt.executeUpdate() != 0) {
        ResultSet generatedKeys = stmt.getGeneratedKeys();
        if (generatedKeys.next()) {
          data.setId(generatedKeys.getInt(1));
        }
        return true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public boolean update(Emprunt emprunt) {
    String query = "UPDATE emprunts SET date_retour_prevue = ? WHERE id = ?";
    try {
      PreparedStatement stmt = DatabaseManager.getInstance().getCn().prepareStatement(query);

      if (emprunt.getDateRetourPrevue() != null) {
        stmt.setDate(1, new java.sql.Date(emprunt.getDateRetourPrevue().getTime()));
      } else {
        stmt.setNull(1, java.sql.Types.DATE);
      }
      stmt.setInt(2, emprunt.getId());

      if (stmt.executeUpdate() != 0) {
        return true;
      }
    } catch (SQLException e) {
      System.err.println("Erreur update Emprunt: " + e.getMessage());
    }
    return false;
  }

  @Override
  public boolean delete(int id) {
    String query = "DELETE FROM emprunts WHERE id = ?";
    try {
      PreparedStatement stmt = DatabaseManager.getInstance().getCn().prepareStatement(query);
      stmt.setInt(1, id);

      if (stmt.executeUpdate() != 0) {
        return true;
      }
    } catch (SQLException e) {
      System.err.println("Erreur delete Emprunt: " + e.getMessage());
    }
    return false;
  }
}
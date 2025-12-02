package main.java.library.dao;

import main.java.library.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import main.java.library.connexion.DatabaseManager;

public class UtilisateurDAO implements IDAO<Utilisateur> {

  private Utilisateur mapResultSetToUtilisateur(ResultSet rs) throws SQLException {
    int id = rs.getInt("id");
    String nom = rs.getString("nom");
    String email = rs.getString("email");
    String prenom = rs.getString("prenom");
    Date dateInscription = rs.getDate("dateInscription");

    return new Utilisateur(id, nom, prenom, email, new java.util.Date(dateInscription.getTime()));
  }

  @Override
  public Utilisateur findById(int id) {
    String query = "SELECT id, nom, prenom, email, dateInscription FROM utilisateurs WHERE id = ?";

    Utilisateur user = new Utilisateur();
    try {
      PreparedStatement stmt = DatabaseManager.getInstance().getCn().prepareStatement(query);

      stmt.setLong(1, id);

      ResultSet rs = stmt.executeQuery();

      if (rs.next())
        return mapResultSetToUtilisateur(rs);

    } catch (SQLException e) {
      System.err.println("Erreur findById Utilisateur: " + e.getMessage());
    }
    return user;
  }

  @Override
  public List<Utilisateur> getAll() {
    List<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();

    String query = "SELECT id, nom, prenom, email, dateInscription FROM utilisateurs";
    try {
      PreparedStatement stmt = DatabaseManager.getInstance().getCn().prepareStatement(query);

      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        utilisateurs.add(mapResultSetToUtilisateur(rs));
      }
    } catch (SQLException e) {
      System.err.println("Erreur getAll Utilisateur: " + e.getMessage());
    }
    return utilisateurs;
  }

  @Override
  public boolean create(Utilisateur data) {
    String query = "INSERT INTO utilisateurs (nom, prenom, email, dateInscription) VALUES (?, ?, ?, ?)";
    try {
      PreparedStatement stmt = DatabaseManager.getInstance().getCn().prepareStatement(query);

      stmt.setString(1, data.getNom());
      stmt.setString(2, data.getPrenom());
      stmt.setString(3, data.getEmail());
      stmt.setDate(4, new java.sql.Date(data.getDateInscription().getTime()));

      if (stmt.executeUpdate() != 0)
        return true;

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public boolean update(Utilisateur utilisateur) {
    String query = "UPDATE utilisateurs SET nom = ?, prenom = ?, email = ?, dateInscription = ? WHERE id = ?";
    try {
      PreparedStatement stmt = DatabaseManager.getInstance().getCn().prepareStatement(query);

      stmt.setString(1, utilisateur.getNom());
      stmt.setString(2, utilisateur.getPrenom());
      stmt.setString(3, utilisateur.getEmail());
      stmt.setDate(4, new java.sql.Date(utilisateur.getDateInscription().getTime()));
      stmt.setLong(5, utilisateur.getId());

      if (stmt.executeUpdate() != 0)
        return true;

      return true;
    } catch (SQLException e) {
      System.err.println("Erreur update Utilisateur: " + e.getMessage());
    }
    return false;
  }

  @Override
  public boolean delete(int id) {
    String query = "DELETE FROM utilisateurs WHERE id = ?";
    try {
      PreparedStatement stmt = DatabaseManager.getInstance().getCn().prepareStatement(query);
      stmt.setLong(1, id);

      if (stmt.executeUpdate() != 0)
        return true;
    } catch (SQLException e) {
      System.err.println("Erreur delete Utilisateur: " + e.getMessage());
    }
    return false;
  }
}
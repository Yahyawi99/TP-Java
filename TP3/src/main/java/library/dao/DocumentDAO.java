package main.java.library.dao;

import main.java.library.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connexion.Connexion;

public class DocumentDAO implements IDAO<DocumentBibliotheque>, Recherchable {

  private DocumentBibliotheque mapResultSetToDocument(int id, ResultSet baseRs) throws SQLException {
    String type = baseRs.getString("type");
    java.util.Date anneePublication = new java.util.Date(baseRs.getDate("anneePublication").getTime());
    boolean disponible = baseRs.getBoolean("disponible");
    String titre = baseRs.getString("titre");

    String specificSQL = "";
    // Use the shared connection provided by Connexion.getInstance().getCn()
    try (Connection conn = Connexion.getInstance().getCn()) {
      switch (type) {
        case "LIVRE":
          // NOTE: Assumes columns 'isbn' and 'nombrePages' exist in the 'livres' table
          specificSQL = "SELECT auteur, isbn, nombrePages, genre FROM livres WHERE id = ?";
          try (PreparedStatement stmt = conn.prepareStatement(specificSQL)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
              if (rs.next()) {
                return new Livre(id, titre, anneePublication, disponible,
                    rs.getString("auteur"), rs.getInt("nombrePages"),
                    rs.getString("isbn"), rs.getString("genre"));
              }
            }
          }
          break;
        case "MAGAZINE":
          // NOTE: Assumes columns 'numeroEdition', 'mois', 'editeur' exist in 'magazines'
          // table
          specificSQL = "SELECT numeroEdition, mois, editeur FROM magazines WHERE id = ?";
          try (PreparedStatement stmt = conn.prepareStatement(specificSQL)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
              if (rs.next()) {
                return new Magazine(id, titre, anneePublication, disponible,
                    rs.getInt("numeroEdition"), rs.getString("mois"),
                    rs.getString("editeur"));
              }
            }
          }
          break;
        case "DVD":
          // NOTE: Assumes columns 'realisateur', 'duree', 'genre', 'classification' exist
          // in 'dvds' table
          specificSQL = "SELECT realisateur, duree, genre, classification FROM dvds WHERE id = ?";
          try (PreparedStatement stmt = conn.prepareStatement(specificSQL)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
              if (rs.next()) {
                return new DVD(id, titre, anneePublication, disponible,
                    rs.getString("realisateur"), rs.getInt("duree"),
                    rs.getString("genre"), rs.getString("classification"));
              }
            }
          }
          break;
      }
    } catch (SQLException e) {
      System.err.println("Erreur lors du mapping des données spécifiques du document ID " + id + ": " + e.getMessage());
      throw e;
    }
    return null; // Unknown or missing document type data
  }

  private List<DocumentBibliotheque> executeSearchQuery(String sql, String parameter) {
    List<DocumentBibliotheque> documents = new ArrayList<>();

    try {
      PreparedStatement stmt = Connexion.getInstance().getCn().prepareStatement(sql);

      stmt.setString(1, parameter);
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        DocumentBibliotheque doc = this.findById(rs.getInt("id"));

        if (doc != null) {
          documents.add(doc);
        }
      }
    } catch (SQLException e) {
      System.err.println("Erreur lors de la recherche de documents: " + e.getMessage());
    }
    return documents;
  }

  @Override
  public List<DocumentBibliotheque> rechercherParTitre(String titre) {
    String SQL = "SELECT id FROM documents WHERE titre LIKE ?";
    return executeSearchQuery(SQL, "%" + titre + "%");
  }

  @Override
  public List<DocumentBibliotheque> rechercherParAuteur(String auteur) {
    String SQL = "SELECT d.id FROM documents d JOIN livres l ON d.id = l.id WHERE l.auteur LIKE ?";
    return executeSearchQuery(SQL, "%" + auteur + "%");
  }

  @Override
  public List<DocumentBibliotheque> rechercherParGenre(String genre) {
    String SQL = "SELECT d.id FROM documents d JOIN livres l ON d.id = l.id WHERE l.genre LIKE ?";
    return executeSearchQuery(SQL, "%" + genre + "%");
  }

  // =======================================================

  @Override
  public DocumentBibliotheque findById(int id) {
    String query = "SELECT id, titre, type, anneePublication, disponible FROM documents WHERE id = ?";
    try {
      PreparedStatement stmt = Connexion.getInstance().getCn().prepareStatement(query);

      stmt.setInt(1, id);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return mapResultSetToDocument(id, rs);
        }
      }
    } catch (SQLException e) {
      System.err.println("Erreur findById Document: " + e.getMessage());
    }
    return null;
  }

  @Override
  public List<DocumentBibliotheque> getAll() {
    List<DocumentBibliotheque> documents = new ArrayList<>();
    String SQL = "SELECT id FROM documents";

    // Retrieves all document IDs and uses findById for complex loading
    try (PreparedStatement stmt = Connexion.getInstance().getCn().prepareStatement(SQL);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        DocumentBibliotheque doc = this.findById(rs.getInt("id"));
        if (doc != null) {
          documents.add(doc);
        }
      }
    } catch (SQLException e) {
      System.err.println("Erreur getAll Document: " + e.getMessage());
    }
    return documents;
  }

  @Override
  public boolean create(DocumentBibliotheque data) {
    String query = "INSERT INTO documents (titre, type, anneePublication, disponible) VALUES (?, ?, ?, ?)";
    int generatedId;

    try {
      PreparedStatement stmt = Connexion.getInstance().getCn().prepareStatement(query);

      stmt.setString(1, data.getTitre());
      stmt.setString(2, data.getType());
      stmt.setDate(3, new java.sql.Date(data.getAnneePublication().getTime()));
      stmt.setBoolean(4, data.getDisponible());

      if (stmt.executeUpdate() == 0)
        return false;

      ResultSet generatedKeys = stmt.getGeneratedKeys();
      if (generatedKeys.next()) {
        generatedId = generatedKeys.getInt(1);
        data.setId(generatedId);
      } else {
        return false;
      }

      String specificSQL = "";
      PreparedStatement specificStmt = null;

      if (data instanceof Livre) {
        Livre livre = (Livre) data;
        specificSQL = "INSERT INTO livres (id, auteur, isbn, nombrePages, genre) VALUES (?, ?, ?, ?, ?)";
        specificStmt = Connexion.getInstance().getCn().prepareStatement(specificSQL);
        specificStmt.setLong(1, generatedId);
        specificStmt.setString(2, livre.getAuteur());
        specificStmt.setString(3, livre.getIsbn());
        specificStmt.setInt(4, livre.getNombrePages());
        specificStmt.setString(5, livre.getGenre());
      } else if (data instanceof Magazine) {
        Magazine magazine = (Magazine) data;
        specificSQL = "INSERT INTO magazines (id, numeroEdition, mois, editeur) VALUES (?, ?, ?, ?)";
        specificStmt = Connexion.getInstance().getCn().prepareStatement(specificSQL);
        specificStmt.setLong(1, generatedId);
        specificStmt.setInt(2, magazine.getNumeroEdition());
        specificStmt.setString(3, magazine.getMois());
        specificStmt.setString(4, magazine.getEditeur());
      } else if (data instanceof DVD) {
        DVD dvd = (DVD) data;
        specificSQL = "INSERT INTO dvds (id, realisateur, duree, genre, classification) VALUES (?, ?, ?, ?, ?)";
        specificStmt = Connexion.getInstance().getCn().prepareStatement(specificSQL);
        specificStmt.setLong(1, generatedId);
        specificStmt.setString(2, dvd.getRealisateur());
        specificStmt.setInt(3, dvd.getDuree());
        specificStmt.setString(4, dvd.getGenre());
        specificStmt.setString(5, dvd.getClassification());
      } else {
        System.err.println("Type de document inconnu ou non supporté pour la création.");
        System.err.println("ROLLBACK!!!");
        return false;
      }

      if (specificStmt.executeUpdate() != 0)
        return true;

    } catch (SQLException e) {
      System.err.println("Erreur create Document: " + e.getMessage());
    }
    return false;
  }

  @Override
  public boolean update(DocumentBibliotheque document) {
    // Step 1: Update base properties
    String baseSQL = "UPDATE documents SET titre = ?, anneePublication = ?, disponible = ? WHERE id = ?";
    try (PreparedStatement baseStmt = Connexion.getInstance().getCn().prepareStatement(baseSQL)) {
      baseStmt.setString(1, document.getTitre());
      baseStmt.setDate(2, new java.sql.Date(document.getAnneePublication().getTime()));
      baseStmt.setBoolean(3, document.getDisponible());
      baseStmt.setLong(4, document.getId());

      baseStmt.executeUpdate();

      String specificSQL = "";
      PreparedStatement specificStmt = null;

      if (document instanceof Livre) {
        Livre livre = (Livre) document;
        specificSQL = "UPDATE livres SET auteur = ?, genre = ? WHERE id = ?";
        specificStmt = Connexion.getInstance().getCn().prepareStatement(specificSQL);
        specificStmt.setString(1, livre.getAuteur());
        specificStmt.setString(2, livre.getGenre());
        specificStmt.setLong(3, livre.getId());
      } else if (document instanceof DVD) {
        DVD dvd = (DVD) document;
        specificSQL = "UPDATE dvds SET realisateur = ?, genre = ? WHERE id = ?";
        specificStmt = Connexion.getInstance().getCn().prepareStatement(specificSQL);
        specificStmt.setString(1, dvd.getRealisateur());
        specificStmt.setString(2, dvd.getGenre());
        specificStmt.setLong(3, dvd.getId());
      }
      // Magazines usually don't have fields updated after creation.

      if (specificStmt != null) {
        try (PreparedStatement s = specificStmt) {
          s.executeUpdate();
        }
      }
      // If we reached here without exception, both updates succeeded (even if 0 rows
      // were affected).
      return true;

    } catch (SQLException e) {
      System.err.println("Erreur update Document: " + e.getMessage());
      return false;
    }
  }

  @Override
  public boolean delete(int id) {
    String SQL = "DELETE FROM documents WHERE id = ?";
    try (PreparedStatement stmt = Connexion.getInstance().getCn().prepareStatement(SQL)) {
      stmt.setInt(1, id);
      if (stmt.executeUpdate() != 0) {
        return true;
      }
    } catch (SQLException e) {
      System.err.println("Erreur delete Document: " + e.getMessage());
    }
    return false;
  }
}
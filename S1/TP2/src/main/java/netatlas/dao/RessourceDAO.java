package netatlas.dao;

import netatlas.connexion.*;
import netatlas.model.*;
import java.sql.*;
import java.util.*;

public class RessourceDAO implements IDAO<Ressource> {

  public boolean create(Ressource data) {
    return true;
  };

  public boolean update(Ressource data) {
    return true;
  };

  public Ressource findById(Long id) {
    String sql = "SELECT * FROM ressources WHERE id=?";

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
    String SQL = "DELETE FROM ressources WHERE id = ?";
    try (PreparedStatement stmt = Connexion.getInstance().getCn().prepareStatement(SQL)) {
      stmt.setLong(1, id);
      if (stmt.executeUpdate() != 0) {
        return true;
      }
    } catch (SQLException e) {
      System.err.println("Erreur delete ressource: " + e.getMessage());
    }
    return false;
  }

  public List<Ressource> findAll() {
    List<Ressource> ressources = new ArrayList<>();
    String SQL = "SELECT * FROM ressources";

    try (PreparedStatement stmt = Connexion.getInstance().getCn().prepareStatement(SQL);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        Ressource res = map(rs);
        if (res != null) {
          ressources.add(res);
        }
      }
    } catch (SQLException e) {
      System.err.println("Erreur getAll ressources: " + e.getMessage());
    }
    return ressources;
  }

  private Ressource map(ResultSet rs) throws SQLException {
    Ressource r = new Ressource();

    r.setId(rs.getLong("id"));
    r.setName(rs.getString("email"));
    r.setType(Enums.RessourceType.valueOf(rs.getString("nom")));
    return r;
  }
}

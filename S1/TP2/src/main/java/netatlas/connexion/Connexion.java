package netatlas.connexion;

import java.io.IOException;
import java.io.InputStream;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Properties;

public class Connexion {
  private static Connection cn = null;
  private static Connexion instance = null;

  private Connexion() {
    Properties props = new Properties();

    try (InputStream input = getClass().getClassLoader().getResourceAsStream("database.properties")) {

      if (input == null) {
        throw new IOException("Fichier database.properties introuvable ");
      }

      props.load(input);

      String url = props.getProperty("db.url");
      String login = props.getProperty("db.user");
      String password = props.getProperty("db.password");
      String driver = props.getProperty("db.driver");

      Class.forName(driver);
      cn = DriverManager.getConnection(url, login, password);

    } catch (IOException | ClassNotFoundException | SQLException e) {
      System.out.println("Erreur lors de l'initialisation de la connexion : " + e.getMessage());

      e.printStackTrace();

    }
  }

  public static synchronized Connexion getInstance() {
    if (instance == null) {
      instance = new Connexion();
    }

    return instance;

  }

  public Connection getCn() {
    return cn;
  }
}

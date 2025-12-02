package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.IDac;
import bean.Employee;
import connexion.Connexion;

public class EmployeService implements IDac<Employee> {
  @Override
  public boolean create(Employee data) {
    try {
      String query = "insert into employees (Nom,Prenom,dateEmbauche,specialite,sexe) values (?,?,?,?,?)";
      PreparedStatement pr = Connexion.getInstance().getCn().prepareStatement(query);

      pr.setString(1, data.getNom());
      pr.setString(2, data.getPrenom());
      pr.setDate(3, data.getDateEmbaucheD());

      String spec = String.join(",", data.getSpecialite());
      pr.setString(4, spec);

      pr.setString(5, data.getSexe());

      if (pr.executeUpdate() != 0)
        return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;

  };

  public boolean update(Employee data) {
    try {
      String query = "update employees set Nom = ?, Prenom = ?, dateEmbauche = ?, sexe = ?, specialite = ? where Matricule = ?";
      PreparedStatement pr = Connexion.getInstance().getCn().prepareStatement(query);

      pr.setString(1, data.getNom());
      pr.setString(2, data.getPrenom());
      pr.setDate(3, data.getDateEmbaucheD());
      pr.setString(4, data.getSexe());
      pr.setString(4, data.getSexe());

      String spec = String.join(",", data.getSpecialite());
      pr.setString(5, spec);

      pr.setInt(6, data.getMatricule());

      if (pr.executeUpdate() != 0)
        return true;

      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;

  };

  public boolean delete(Employee data) {
    try {
      String query = "delete from employees where Matricule = ?";
      PreparedStatement pr = Connexion.getInstance().getCn().prepareStatement(query);

      pr.setInt(1, data.getMatricule());

      if (pr.executeUpdate() != 0)
        return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;

  };

  public Employee findById(int id) {

    try {
      String query = "Select * from employees where Matricule = ?";
      PreparedStatement pr = Connexion.getInstance().getCn().prepareStatement(query);
      pr.setInt(1, id);

      ResultSet rs = pr.executeQuery();

      if (rs.next()) {
        Employee employee = new Employee();
        employee.setMatricule(rs.getInt("Matricule"));
        employee.setNom(rs.getString("Nom"));
        employee.setPrenom(rs.getString("Prenom"));
        employee.setDateEmbaucheD(rs.getDate("dateEmbauche"));
        employee.setSexe(rs.getString("sexe"));
        employee.setSpecialite(rs.getString("specialite").split(","));

        return employee;
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  };

  public List<Employee> findAll() {
    List<Employee> employees = new ArrayList<Employee>();
    String req = "select * from employees";

    try {
      PreparedStatement pr = Connexion.getInstance().getCn().prepareStatement(req);

      ResultSet rs = pr.executeQuery();

      while (rs.next()) {
        Employee emp = new Employee();
        emp.setMatricule(rs.getInt("Matricule"));
        emp.setNom(rs.getString("Nom"));
        emp.setPrenom(rs.getString("Prenom"));
        emp.setDateEmbaucheD(rs.getDate("dateEmbauche"));
        emp.setSexe(rs.getString("sexe"));
        emp.setSpecialite(rs.getString("specialite").split(","));

        employees.add(emp);

      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return employees;
  }

  List<Employee> findByCritere(String Specialite, String nom, Integer matricule) {
    String query = "Select * from employees";
    List<Employee> employees = new ArrayList<Employee>();
    List<String> conditions = new ArrayList<>();

    if (Specialite != null && !Specialite.isEmpty()) {
      conditions.add("specialite LIKE ?");
    }
    if (nom != null && !nom.isEmpty()) {
      conditions.add("nom = ?");
    }
    if (matricule != null) {
      conditions.add("matricule = ?");
    }

    if (!conditions.isEmpty()) {
      query += " WHERE " + String.join(" AND ", conditions);
    }
    try {
      PreparedStatement stmt = Connexion.getInstance().getCn().prepareStatement(query);
      int numOfParams = 1;

      if (Specialite != null && !Specialite.isEmpty()) {
        stmt.setString(numOfParams++, "%" + Specialite + "%");
      }
      if (nom != null && !nom.isEmpty()) {
        stmt.setString(numOfParams++, nom);
      }
      if (matricule != null) {
        stmt.setInt(numOfParams++, matricule);
      }

      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        Employee emp = new Employee();
        emp.setMatricule(rs.getInt("Matricule"));
        emp.setNom(rs.getString("Nom"));
        emp.setPrenom(rs.getString("Prenom"));
        emp.setDateEmbaucheD(rs.getDate("dateEmbauche"));
        emp.setSexe(rs.getString("sexe"));
        emp.setSpecialite(rs.getString("specialite").split(","));

        employees.add(emp);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return employees;
  }

  // List <Employee> findBetweenDate(Date d1, Date d2){}

}

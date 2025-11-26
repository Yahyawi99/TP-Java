package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.IDac;
import bean.Employee;
import connexion.Connexion;

public class EmployeService implements IDac<Employee> {
  @Override
  public boolean create(Employee data) {
    return true;
  };

  public boolean update(Employee data) {
    return true;

  };

  public boolean delete(Employee data) {
    return true;

  };

  public Employee findById(int id) {
    return new Employee();
  };

  public List<Employee> findAll() {
    List<Employee> employees = new ArrayList<Employee>();

    try {
      String req = "select * from employees";
      PreparedStatement pr = Connexion.getInstance().getCn().prepareStatement(req);

      ResultSet rs = pr.executeQuery();

      while (rs.next()) {
        // Matricule | Nom | Prénom | Spécialité | Date_Embauche | Sexe
        Employee emp = new Employee();
        emp.setMatricule(rs.getInt("Matricule"));
        emp.setNom(rs.getString("Nom"));
        emp.setPrenom(rs.getString("Prénom"));
        emp.setDateEmbaucheD(rs.getDate("Date_Embauche"));
        emp.setSexe(rs.getString("Sexe"));

        employees.add(emp);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return employees;
  }
}

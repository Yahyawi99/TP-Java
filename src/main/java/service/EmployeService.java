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
    Employee employee = null;

    try {
      String query = "Select * from employees where id=" + id;
      PreparedStatement pr = Connexion.getInstance().getCn().prepareStatement(query);

      ResultSet rs = pr.executeQuery();

      if (rs.next())
        employee = new Employee();
      employee.setMatricule(rs.getInt("Matricule"));
      employee.setNom(rs.getString("Nom"));
      employee.setPrenom(rs.getString("Prénom"));
      employee.setDateEmbaucheD(rs.getDate("Date_Embauche"));
      employee.setSexe(rs.getString("Sexe"));

    } catch (Exception e) {
      e.printStackTrace();
    }

    return employee;
  };

  public List<Employee> findAll() {
    List<Employee> employees = new ArrayList<Employee>();

    try {
      String req = "select * from employees";
      PreparedStatement pr = Connexion.getInstance().getCn().prepareStatement(req);

      ResultSet rs = pr.executeQuery();

      while (rs.next()) {
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

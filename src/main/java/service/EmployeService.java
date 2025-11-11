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
      String req = "select * from Employee";
      PreparedStatement pr = Connexion.getInstance().getCn().prepareStatement(req);

      ResultSet rs = pr.executeQuery();

      while (rs.next())
        System.out.println(rs.getDate("dateEmbaucheD"));

      // Employee emp = new Employee(rs.getInt("Matricule"), rs.getString("Nom"),
      // rs.getString("Prenom"), rs.getDate("dateEmbaucheD").,rs.getString("Sexe"));

      // employees.add(, ts.findById(rs.getInt("tracker_id"))));
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return employees;
  }
}

package test;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.format.*;
import java.time.*;

import bean.Employee;
import connexion.Connexion;
// import connexion.DatabaseInitializer;
// import connexion.DatabaseInitializer.;
import service.EmployeService;

public class Test {
  public static void main(String[] args) {
    try {

      Statement st = Connexion.getInstance().getCn().createStatement();
      EmployeService empService = new EmployeService();

      List<Employee> employees = empService.findAll();

      Employee emp1 = empService.findById(1003);

      System.out.println(employees);
      System.out.println(emp1.getNom() + " " + emp1.getPrenom());

      empService.update(new Employee(1003, "Yassine", "Yahyawi", emp1.getDateEmbaucheD(), emp1.getSexe(),
          emp1.getSpecialite()));

      LocalDate date = LocalDate.of(2025, 12, 1);

      empService.create(new Employee(1003, "Deidara", "kats",
          Date.valueOf(date), "M",
          new ArrayList<String>(Arrays.asList("JAVA", "JAVASCRIPT"))));

      empService.delete(emp1);

    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

}

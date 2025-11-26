package test;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import bean.Employee;
import connexion.Connexion;
// import connexion.DatabaseInitializer;
// import connexion.DatabaseInitializer.;
import service.EmployeService;

public class Test {
  public static void main(String[] args) {
    // DatabaseInitializer.createTablesIfNotExists();
    try {
      // String TTracker = "create table tracker ("
      // + "id int primary key auto_increment,"
      // + "simNumber varchar(20));";
      // String TPosition = "create table position ("
      // + "id int primary key auto_increment,"
      // + "latitude double,"
      // + "longitude double,"
      // + "date date, "
      // + "idTracker int);";
      // String fk = "alter table position add "
      // + "constraint fk foreign key (idTracker) "
      // + "references tracker(id);";

      Statement st = Connexion.getInstance().getCn().createStatement();
      // st.execute("select * from employees");

      // st.execute("drop table if exists position");
      // st.execute("drop table if exists tracker");
      // st.executeUpdate(TTracker);
      // st.executeUpdate(TPosition);
      // st.executeUpdate(fk);
      EmployeService empService = new EmployeService();

      List<Employee> employees = empService.findAll();

      System.out.println(employees);

    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

}

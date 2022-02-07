package edu.wpi.teame;

import edu.wpi.teame.db.DepartmentType;
import edu.wpi.teame.db.Employee;
import edu.wpi.teame.db.EmployeeManager;
import java.io.IOException;

public class Main {

  public static void main(String[] args) throws IOException {

    // TODO: REMOVE BEFORE PULL REQUESTING
    Employee testE =
        new Employee("ABC123", DepartmentType.CLINICALSERVICES, "Brinda Venkataraman", true);
    EmployeeManager testEManager = new EmployeeManager();
    String csvFile =
        System.getProperty("user.dir")
            + "\\src\\main\\resources\\edu\\wpi\\teame\\csv\\EmployeesE.csv";
    testEManager.readCSV(csvFile);
    // testEManager.insert(testE);
    // testEManager.writeToCSV(csvFile);
    // end todo

    // App.launch(App.class, args);
  }
}

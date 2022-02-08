package edu.wpi.teame.db.managers;

import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import java.sql.Connection;
import java.sql.Statement;

public class EmployeeManager extends ObjectManager<Employee> {
  private static Connection connection;
  private static Statement stmt;

  public EmployeeManager() {
    super(DataBaseObjectType.Employee);
  }
}

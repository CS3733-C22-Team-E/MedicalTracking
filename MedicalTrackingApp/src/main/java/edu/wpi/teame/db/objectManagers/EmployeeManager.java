package edu.wpi.teame.db.objectManagers;

import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.enums.*;
import java.io.*;
import java.sql.SQLException;

public final class EmployeeManager extends ObjectManager<Employee> {
  public EmployeeManager() throws SQLException {
    super(DataBaseObjectType.Employee, 5);
  }

  public Employee getByAssignee(String assignee) throws SQLException {
    return super.getBy("WHERE name = '" + assignee + "'").get(0);
  }
}

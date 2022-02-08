package edu.wpi.teame.db.objectManagers;

import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import java.sql.SQLException;

public final class EmployeeManager extends ObjectManager<Employee> {
  public EmployeeManager() {
    super(DataBaseObjectType.Employee);
  }

  public Employee getByAssignee(String assignee) throws SQLException {
    return super.getBy("WHERE name = " + assignee).get(0);
  }
}

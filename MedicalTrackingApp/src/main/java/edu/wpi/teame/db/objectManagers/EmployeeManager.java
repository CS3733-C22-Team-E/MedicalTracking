package edu.wpi.teame.db.objectManagers;

import static com.mongodb.client.model.Filters.eq;

import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.enums.*;
import java.io.*;
import java.sql.SQLException;

public final class EmployeeManager extends ObjectManager<Employee> {
  public EmployeeManager() throws SQLException {
    super(DataBaseObjectType.Employee, 5);
  }

  public Employee getByAssignee(String assignee) throws SQLException {
    if (DBManager.getInstance().getCurrentType() == DBType.MongoDB) {
      System.out.println("Mogo getAss");

      Employee employee =
          DBManager.getInstance()
              .getMongoDatabase()
              .getCollection(objectType.toTableName(), Employee.class)
              .withCodecRegistry(DBManager.getInstance().getObjectCodecs())
              .find(eq("name", assignee))
              .first();
      return employee;
    }
    return super.getBy("WHERE name = '" + assignee + "'").get(0);
  }
}

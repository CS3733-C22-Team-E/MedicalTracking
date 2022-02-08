package edu.wpi.teame.db.objectManagers;

import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import java.io.IOException;
import java.sql.SQLException;

public final class EmployeeManager extends ObjectManager<Employee> {
  public EmployeeManager() {
    super(DataBaseObjectType.Employee);
  }

  public Employee getByAssignee(String assignee) throws SQLException {
    return super.getBy("WHERE name = " + assignee).get(0);
  }

  @Override
  public void readCSV(String inputFileName)
      throws IOException, CsvValidationException, SQLException {}

  @Override
  public void writeToCSV(String outputFileName) throws IOException {}
}

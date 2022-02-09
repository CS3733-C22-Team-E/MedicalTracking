package edu.wpi.teame.db.objectManagers;

// import com.opencsv.exceptions.CsvValidationException;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.db.CSVLineData;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.enums.*;
import java.io.FileReader;
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
      throws IOException, CsvValidationException, SQLException {
    String filePath =
        System.getProperty("user.dir") + "/src/main/resources/edu/wpi/teame/" + inputFileName;
    CSVReader csvReader = new CSVReader(new FileReader(filePath));
    CSVLineData lineData = new CSVLineData(csvReader);

    String[] record;
    while ((record = csvReader.readNext()) != null) {
      lineData.setParsedData(record);

      String name = lineData.getColumnString("name");
      String nodeId = lineData.getColumnString("id");
      boolean isDoctor = lineData.getColumnBoolean("isDoctor");
      DepartmentType departmentType =
          DepartmentType.valueOf(lineData.getColumnString("department"));

      Employee newEmployee = new Employee(0, departmentType, name, isDoctor);
      DBManager.getInstance().getEmployeeManager().insert(newEmployee);
    }
  }

  @Override
  public void writeToCSV(String outputFileName) throws IOException {}
}

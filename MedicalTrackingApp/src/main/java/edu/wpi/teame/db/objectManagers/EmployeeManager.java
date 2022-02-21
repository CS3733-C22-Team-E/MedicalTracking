package edu.wpi.teame.db.objectManagers;

// import com.opencsv.exceptions.CsvValidationException;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.App;
import edu.wpi.teame.db.CSVLineData;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.enums.*;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class EmployeeManager extends ObjectManager<Employee> {
  public EmployeeManager() throws SQLException {
    super(DataBaseObjectType.Employee);
  }

  public Employee getByAssignee(String assignee) throws SQLException {
    return super.getBy("WHERE name = '" + assignee + "'").get(0);
  }

  @Override
  public void readCSV(String inputFileName)
      throws IOException, CsvValidationException, SQLException {
    InputStream filePath = App.class.getResourceAsStream("csv/" + inputFileName);
    CSVReader csvReader = new CSVReader(new InputStreamReader(filePath));
    CSVLineData lineData = new CSVLineData(csvReader);

    String[] record;
    while ((record = csvReader.readNext()) != null) {
      lineData.setParsedData(record);

      String name = lineData.getColumnString("name");
      EmployeeType type = EmployeeType.values()[(lineData.getColumnInt("type"))];
      DepartmentType departmentType =
          DepartmentType.values()[(lineData.getColumnInt("department"))];
      Employee newEmployee = new Employee(0, departmentType, name, type);
      DBManager.getInstance().getEmployeeManager().insert(newEmployee);
    }
  }

  @Override
  public void writeToCSV(String outputFileName) throws IOException, SQLException {
    String filePath = App.class.getResource("csv/" + outputFileName).getPath();
    FileWriter outputFile = new FileWriter(filePath);
    CSVWriter writer =
        new CSVWriter(
            outputFile,
            ',',
            CSVWriter.NO_QUOTE_CHARACTER,
            CSVWriter.DEFAULT_ESCAPE_CHARACTER,
            CSVWriter.DEFAULT_LINE_END);

    List<Employee> listOfEmployees = this.getAll();

    List<String[]> data = new ArrayList<String[]>();
    data.add(new String[] {"employeeID", "department", "name", "type"});

    for (Employee employee : listOfEmployees) {
      data.add(
          new String[] {
            Integer.toString(employee.getId()),
            Integer.toString(employee.getDepartment().ordinal()),
            employee.getName(),
            Integer.toString(employee.getType().ordinal())
          });
    }
    writer.writeAll(data);
    writer.close();
  }
}

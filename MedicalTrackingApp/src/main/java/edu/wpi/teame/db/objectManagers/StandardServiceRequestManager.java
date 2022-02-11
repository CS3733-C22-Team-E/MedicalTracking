package edu.wpi.teame.db.objectManagers;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.db.CSVLineData;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import edu.wpi.teame.model.serviceRequests.ServiceRequest;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class StandardServiceRequestManager extends ObjectManager<ServiceRequest> {
  public StandardServiceRequestManager(DataBaseObjectType dbType) {
    super(dbType);
  }

  @Override
  public void readCSV(String inputFileName)
      throws IOException, ParseException, CsvValidationException, SQLException {
    String filePath =
        System.getProperty("user.dir") + "/src/main/resources/edu/wpi/teame/csv/" + inputFileName;
    CSVReader csvReader = new CSVReader(new FileReader(filePath));
    CSVLineData lineData = new CSVLineData(csvReader);

    String[] record;
    while ((record = csvReader.readNext()) != null) {
      lineData.setParsedData(record);

      ServiceRequestStatus status =
          ServiceRequestStatus.values()[lineData.getColumnInt("requestStatus")];
      int employeeID = lineData.getColumnInt("employeeID");
      int locationID = lineData.getColumnInt("locationID");
      Date closeDate = lineData.getColumnDate("closeDate");
      Date openDate = lineData.getColumnDate("openDate");
      int id = lineData.getColumnInt("id");

      // select assignee where id = employeeID
      EmployeeManager employeeManager = new EmployeeManager();
      Employee newEmployee = employeeManager.get(employeeID);
      // select location where id = locationID
      LocationManager locationManager = new LocationManager();
      Location newLocation = locationManager.get(locationID);

      //      SanitationServiceRequest newSanitationSR =
      //          new SanitationServiceRequest(status, newEmployee, newLocation, closeDate,
      // openDate, id);
      //      DBManager.getInstance().getSanitationSRManager().insert(newSanitationSR);
    }
  }

  @Override
  public void writeToCSV(String outputFileName) throws IOException, SQLException {
    String filePath =
        System.getProperty("user.dir") + "/src/main/resources/edu/wpi/teame/csv/" + outputFileName;

    FileWriter outputFile = new FileWriter(filePath);
    CSVWriter writer =
        new CSVWriter(
            outputFile,
            ',',
            CSVWriter.NO_QUOTE_CHARACTER,
            CSVWriter.DEFAULT_ESCAPE_CHARACTER,
            CSVWriter.DEFAULT_LINE_END);

    List<ServiceRequest> listOfSerReq = this.getAll();

    List<String[]> data = new ArrayList<String[]>();
    data.add(new String[] {"id", "locationID", "status", "employeeID", "closeDate", "openDate"});

    for (ServiceRequest serReq : listOfSerReq) {
      data.add(
          new String[] {
            Integer.toString(serReq.getId()),
            Integer.toString(serReq.getLocation().getId()),
            serReq.getStatus().toString(),
            Integer.toString(serReq.getAssignee().getId()),
            serReq.getCloseDate().toString(),
            serReq.getOpenDate().toString()
          });
    }
    writer.writeAll(data);
    writer.close();
  }
}

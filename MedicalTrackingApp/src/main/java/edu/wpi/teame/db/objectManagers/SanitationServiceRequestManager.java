package edu.wpi.teame.db.objectManagers;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.db.CSVLineData;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Equipment;
import edu.wpi.teame.model.Location;
import com.opencsv.CSVWriter;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import edu.wpi.teame.model.serviceRequests.MedicalEquipmentServiceRequest;
import edu.wpi.teame.model.serviceRequests.SanitationServiceRequest;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SanitationServiceRequestManager extends ObjectManager<SanitationServiceRequest> {
  public SanitationServiceRequestManager() {
    super(DataBaseObjectType.SanitationSR);
  }

  @Override
  public void readCSV(String inputFileName) throws IOException, ParseException, CsvValidationException, SQLException {
    String filePath =
            System.getProperty("user.dir") + "/src/main/resources/edu/wpi/teame/" + inputFileName;
    CSVReader csvReader = new CSVReader(new FileReader(filePath));
    CSVLineData lineData = new CSVLineData(csvReader);

    String[] record;
    while ((record = csvReader.readNext()) != null) {
      lineData.setParsedData(record);

      ServiceRequestStatus status = ServiceRequestStatus.values()[lineData.getColumnInt("requestStatus")];
      int employeeID = lineData.getColumnInt("employeeID");
      int locationID = lineData.getColumnInt("locationID");
      Date closeDate = lineData.getColumnDate("closeDate");
      Date openDate = lineData.getColumnDate("openDate");
      int id = lineData.getColumnInt("id");

      //select employee where id = employeeID
      EmployeeManager employeeManager = new EmployeeManager();
      Employee newEmployee = employeeManager.get(employeeID);
      //select location where id = locationID
      LocationManager locationManager = new LocationManager();
      Location newLocation = locationManager.get(locationID);

      SanitationServiceRequest newSanitationSR = new SanitationServiceRequest(status, newEmployee, newLocation, closeDate, openDate, id);
      DBManager.getInstance().getSanitationSRManager().insert(newSanitationSR);

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

    List<SanitationServiceRequest> listOfSerReq = this.getAll();

    List<String[]> data = new ArrayList<String[]>();
    data.add(new String[] {"id", "locationID", "status", "employeeID", "closeDate", "openDate"});

    for (SanitationServiceRequest serReq : listOfSerReq) {
      data.add(
          new String[] {
            Integer.toString(serReq.getId()),
            Integer.toString(serReq.getLocation().getId()),
            serReq.getStatus().toString(),
            Integer.toString(serReq.getEmployee().getId()),
            serReq.getCloseDate().toString(),
            serReq.getOpenDate().toString()
          });
    }
    writer.writeAll(data);
    writer.close();
  }
}

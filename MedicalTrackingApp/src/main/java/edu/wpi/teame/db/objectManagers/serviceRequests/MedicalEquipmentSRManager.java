package edu.wpi.teame.db.objectManagers.serviceRequests;

// import com.opencsv.exceptions.CsvValidationException;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.App;
import edu.wpi.teame.db.CSVLineData;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.objectManagers.EmployeeManager;
import edu.wpi.teame.db.objectManagers.EquipmentManager;
import edu.wpi.teame.db.objectManagers.LocationManager;
import edu.wpi.teame.db.objectManagers.ObjectManager;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Equipment;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.*;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.serviceRequests.MedicalEquipmentServiceRequest;
import java.io.*;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public final class MedicalEquipmentSRManager extends ObjectManager<MedicalEquipmentServiceRequest> {
  public MedicalEquipmentSRManager() throws SQLException {
    super(DataBaseObjectType.MedicalEquipmentSR);
  }

  @Override
  public void readCSV(String inputFileName)
      throws IOException, CsvValidationException, SQLException, ParseException {
    InputStream filePath = App.class.getResourceAsStream("csv/" + inputFileName);
    CSVReader csvReader = new CSVReader(new InputStreamReader(filePath));
    CSVLineData lineData = new CSVLineData(csvReader);

    String[] record;
    csvReader.readNext();
    while ((record = csvReader.readNext()) != null) {
      lineData.setParsedData(record);

      ServiceRequestPriority priority =
          ServiceRequestPriority.values()[lineData.getColumnInt("priority")];
      ServiceRequestStatus requestStatus =
          ServiceRequestStatus.values()[lineData.getColumnInt("status")];
      String additionalInfo = lineData.getColumnString("additionalInfo");
      int assignee = lineData.getColumnInt("assigneeID");
      int location = lineData.getColumnInt("locationID");
      Date requestDate = lineData.getColumnDate("requestDate");
      Date closeDate = lineData.getColumnDate("closeDate");
      Date openDate = lineData.getColumnDate("openDate");
      String title = lineData.getColumnString("title");
      int id = lineData.getColumnInt("id");
      int equipment = lineData.getColumnInt("equipmentID");

      // select assignee where id = employeeID
      EmployeeManager employeeManager = new EmployeeManager();
      Employee newEmployee = employeeManager.get(assignee);
      // select location where id = locationID
      LocationManager locationManager = new LocationManager();
      Location newLocation = locationManager.get(location);
      // select equipment where id = equipment
      EquipmentManager equipmentManager = new EquipmentManager();
      Equipment newEquipment = equipmentManager.get(equipment);

      // new ServiceRequest
      MedicalEquipmentServiceRequest newSR =
          new MedicalEquipmentServiceRequest(
              priority,
              requestStatus,
              additionalInfo,
              newEmployee,
              newLocation,
              requestDate,
              closeDate,
              openDate,
              title,
              id,
              newEquipment);
      DBManager.getInstance().getMedicalEquipmentSRManager().insert(newSR);
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

    List<MedicalEquipmentServiceRequest> listOfSerReq = this.getAll();

    List<String[]> data = new ArrayList<String[]>();
    data.add(
        new String[] {
          "id",
          "locationID",
          "assigneeID",
          "openDate",
          "closeDate",
          "status",
          "title",
          "additionalInfo",
          "priority",
          "requestDate",
          "equipmentID"
        });

    for (MedicalEquipmentServiceRequest serReq : listOfSerReq) {
      data.add(
          new String[] {
            Integer.toString(serReq.getId()),
            Integer.toString(serReq.getLocation().getId()),
            serReq.getAssignee() == null ? "" : Integer.toString(serReq.getAssignee().getId()),
            serReq.getOpenDate().toString(),
            serReq.getCloseDate() == null ? "" : serReq.getCloseDate().toString(),
            Integer.toString(serReq.getStatus().ordinal()),
            serReq.getTitle(),
            serReq.getAdditionalInfo(),
            Integer.toString(serReq.getPriority().ordinal()),
            serReq.getRequestDate() == null ? "" : serReq.getRequestDate().toString(),
            //      serReq.getRequestDate().toString(),
            Integer.toString(serReq.getEquipment().getId())
          });
    }
    writer.writeAll(data);
    writer.close();
  }
}

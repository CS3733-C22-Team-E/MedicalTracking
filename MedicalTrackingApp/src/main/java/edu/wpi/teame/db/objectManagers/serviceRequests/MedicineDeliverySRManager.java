package edu.wpi.teame.db.objectManagers.serviceRequests;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.db.CSVLineData;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.objectManagers.EmployeeManager;
import edu.wpi.teame.db.objectManagers.LocationManager;
import edu.wpi.teame.db.objectManagers.ObjectManager;
import edu.wpi.teame.db.objectManagers.PatientManager;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.Patient;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.ServiceRequestPriority;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import edu.wpi.teame.model.serviceRequests.MedicineDeliveryServiceRequest;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public final class MedicineDeliverySRManager extends ObjectManager<MedicineDeliveryServiceRequest> {
  public MedicineDeliverySRManager() throws SQLException {
    super(DataBaseObjectType.MedicineDeliverySR);
  }

  @Override
  public void readCSV(String inputFileName)
      throws IOException, ParseException, SQLException, CsvValidationException {
    String filePath =
        System.getProperty("user.dir") + "/src/main/resources/edu/wpi/teame/csv/" + inputFileName;
    CSVReader csvReader = new CSVReader(new FileReader(filePath));
    CSVLineData lineData = new CSVLineData(csvReader);

    String[] record;
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
      String medicineQuantity = lineData.getColumnString("medicineQuantity");
      String medicineName = lineData.getColumnString("medicineName");
      int patient = lineData.getColumnInt("patientID");

      // select assignee where id = employeeID
      EmployeeManager employeeManager = new EmployeeManager();
      Employee newEmployee = employeeManager.get(assignee);
      // select location where id = locationID
      LocationManager locationManager = new LocationManager();
      Location newLocation = locationManager.get(location);
      // select patient where id = patient
      PatientManager patientManager = new PatientManager();
      Patient newPatient = patientManager.get(patient);

      // new ServiceRequest
      MedicineDeliveryServiceRequest newSR =
          new MedicineDeliveryServiceRequest(
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
              medicineQuantity,
              medicineName,
              newPatient);
      DBManager.getInstance().getMedicineDeliverySRManager().insert(newSR);
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

    List<MedicineDeliveryServiceRequest> listOfSerReq = this.getAll();

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
          "medicineName",
          "patientID",
          "medicineQuantity"
        });

    for (MedicineDeliveryServiceRequest serReq : listOfSerReq) {
      data.add(
          new String[] {
            Integer.toString(serReq.getId()),
            Integer.toString(serReq.getLocation().getId()),
            serReq.getAssignee() == null ? "" : Integer.toString(serReq.getAssignee().getId()),
            serReq.getOpenDate().toString(),
            serReq.getCloseDate() == null ? "" : serReq.getCloseDate().toString(),
            serReq.getStatus().toString(),
            serReq.getTitle(),
            serReq.getAdditionalInfo(),
            Integer.toString(serReq.getPriority().ordinal()),
            serReq.getRequestDate().toString(),
            serReq.getMedicineName(),
            Integer.toString(serReq.getPatient().getId()),
            serReq.getMedicineQuantity()
          });
    }
    writer.writeAll(data);
    writer.close();
  }
}

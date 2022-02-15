package edu.wpi.teame.db.objectManagers.serviceRequests;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.db.CSVLineData;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.objectManagers.*;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Equipment;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.Patient;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.ServiceRequestPriority;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import edu.wpi.teame.model.serviceRequests.PatientTransportationServiceRequest;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public final class PatientTransportationSRManager
    extends ObjectManager<PatientTransportationServiceRequest> {
  public PatientTransportationSRManager(boolean isInternal) throws SQLException {
    super(
        isInternal
            ? DataBaseObjectType.InternalPatientTransferSR
            : DataBaseObjectType.ExternalPatientSR);
  }

  @Override
  public void readCSV(String inputFileName)
      throws IOException, SQLException, CsvValidationException, ParseException {
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
      Integer assignee =
          lineData.getColumnString("assigneeID").equals("")
              ? -1
              : lineData.getColumnInt("assigneeID");
      int location = lineData.getColumnInt("locationID");
      Date requestDate = lineData.getColumnDate("requestDate");
      Date closeDate =
          lineData.getColumnString("closeDate").equals("")
              ? null
              : lineData.getColumnDate("closeDate");
      Date openDate = lineData.getColumnDate("openDate");
      String title = lineData.getColumnString("title");
      int id = lineData.getColumnInt("id");
      int destination = lineData.getColumnInt("destinationID");
      int equipment = lineData.getColumnInt("equipmentID");
      int patient = lineData.getColumnInt("patientID");

      // select assignee where id = assignee
      EmployeeManager employeeManager = new EmployeeManager();
      Employee newEmployee = employeeManager.get(assignee);
      // select location where id = locationID, select destination where id = destination
      LocationManager locationManager = new LocationManager();
      Location newLocation = locationManager.get(location);
      Location newDestination = locationManager.get(destination);
      // select equipment where id = equipment
      EquipmentManager equipmentManager = new EquipmentManager();
      Equipment newEquipment = equipmentManager.get(equipment);
      // select patient where id = patient
      PatientManager patientManager = new PatientManager();
      Patient newPatient = patientManager.get(patient);

      // new ServiceRequest
      PatientTransportationServiceRequest newSR =
          new PatientTransportationServiceRequest(
              getInternalBool(),
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
              newDestination,
              newEquipment,
              newPatient);
      if (getInternalBool()) {
        DBManager.getInstance().getInternalPatientSRManager().insert(newSR);
      } else {
        DBManager.getInstance().getExternalPatientSRManager().insert(newSR);
      }
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

    List<PatientTransportationServiceRequest> listOfSerReq = this.getAll();

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
          "destinationID",
          "patientID",
          "equipmentID"
        });

    for (PatientTransportationServiceRequest serReq : listOfSerReq) {
      data.add(
          new String[] {
            Integer.toString(serReq.getId()),
            Integer.toString(serReq.getLocation().getId()),
            serReq.getAssignee() == null ? "" : Integer.toString(serReq.getAssignee().getId()),
            serReq.getOpenDate() == null ? "" : serReq.getOpenDate().toString(),
            serReq.getCloseDate() == null ? "" : serReq.getCloseDate().toString(),
            Integer.toString(serReq.getStatus().ordinal()),
            serReq.getTitle(),
            serReq.getAdditionalInfo(),
            Integer.toString(serReq.getPriority().ordinal()),
            serReq.getRequestDate().toString(),
            Integer.toString(serReq.getDestination().getId()),
            Integer.toString(serReq.getPatient().getId()),
            Integer.toString(serReq.getEquipment().getId())
          });
    }
    writer.writeAll(data);
    writer.close();
  }

  public boolean getInternalBool() {
    if (super.objectType == DataBaseObjectType.InternalPatientTransferSR) {
      return true;
    } else {
      return false;
    }
  }
}
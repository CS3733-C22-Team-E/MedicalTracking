package edu.wpi.teame.model.serviceRequests;

import edu.wpi.teame.db.CSVLineData;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Equipment;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.Patient;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.ServiceRequestPriority;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public final class PatientTransportationServiceRequest extends ServiceRequest {
  private Location destination;
  private Equipment equipment;
  private Patient patient;

  public PatientTransportationServiceRequest(
      boolean isInternal,
      ServiceRequestPriority priority,
      ServiceRequestStatus status,
      String additionalInfo,
      Employee assignee,
      Location location,
      Date requestDate,
      Date closeDate,
      Date openDate,
      String title,
      int id,
      Location destination,
      Equipment equipment,
      Patient patient) {
    super(
        isInternal
            ? DataBaseObjectType.InternalPatientTransferSR
            : DataBaseObjectType.ExternalPatientSR,
        priority,
        status,
        additionalInfo,
        assignee,
        location,
        requestDate,
        closeDate,
        openDate,
        title,
        id);
    this.destination = destination;
    this.equipment = equipment;
    this.patient = patient;
  }

  public PatientTransportationServiceRequest(ResultSet resultSet, boolean isInternal)
      throws SQLException {
    super(
        resultSet,
        isInternal
            ? DataBaseObjectType.InternalPatientTransferSR
            : DataBaseObjectType.ExternalPatientSR);
    this.destination =
        (Location)
            DBManager.getInstance()
                .getManager(DataBaseObjectType.Location)
                .get(resultSet.getInt("destinationID"));
    this.equipment =
        (Equipment)
            DBManager.getInstance()
                .getManager(DataBaseObjectType.Equipment)
                .get(resultSet.getInt("equipmentID"));
    this.patient =
        (Patient)
            DBManager.getInstance()
                .getManager(DataBaseObjectType.Patient)
                .get(resultSet.getInt("patientID"));
  }

  public PatientTransportationServiceRequest(CSVLineData lineData, boolean isInternal)
      throws SQLException, ParseException {
    super(
        lineData,
        isInternal
            ? DataBaseObjectType.InternalPatientTransferSR
            : DataBaseObjectType.ExternalPatientSR);
    this.destination =
        (Location) lineData.getDBObject(DataBaseObjectType.Location, "destinationID");
    this.equipment = (Equipment) lineData.getDBObject(DataBaseObjectType.Equipment, "equipmentID");
    this.patient = (Patient) lineData.getDBObject(DataBaseObjectType.Patient, "patientID");
  }

  // in order for a Codec registry to work properly, this constructor needs to exist
  // for now, it only sets dbType
  public PatientTransportationServiceRequest() {}

  @Override
  public String getSQLUpdateString() {
    return getRawUpdateString()
        + ", "
        + "destinationID = "
        + destination.getId()
        + ", "
        + "equipmentID = "
        + equipment.getId()
        + ", "
        + "patientID = "
        + patient.getId()
        + " WHERE id = "
        + id;
  }

  @Override
  public String getSQLInsertString() {
    return super.getSQLInsertString()
        + ", "
        + destination.getId()
        + ", "
        + equipment.getId()
        + ", "
        + patient.getId();
  }

  @Override
  public String[] toCSVData() {
    List<String> csvData = new ArrayList<>();
    csvData.addAll(List.of(super.toCSVData()));
    csvData.add(Integer.toString(destination.getId()));
    csvData.add(Integer.toString(patient.getId()));
    csvData.add(Integer.toString(equipment.getId()));

    String[] retArr = new String[csvData.size()];
    return csvData.toArray(retArr);
  }

  @Override
  public String[] getCSVHeaders() {
    List<String> csvHeaders = new ArrayList<>();
    csvHeaders.addAll(List.of(super.getCSVHeaders()));
    csvHeaders.add("destinationID");
    csvHeaders.add("patientID");
    csvHeaders.add("equipmentID");

    String[] retArr = new String[csvHeaders.size()];
    return csvHeaders.toArray(retArr);
  }

  @Override
  public String getTableColumns() {
    return "(locationID, assigneeID, openDate, closeDate, status, title, additionalInfo, priority, requestDate, destinationID, equipmentID, patientID)";
  }

  // Getters and Setters
  public Location getDestination() {
    return destination;
  }

  public void setDestination(Location destination) {
    this.destination = destination;
  }

  public Equipment getEquipment() {
    return equipment;
  }

  public void setEquipment(Equipment equipment) {
    this.equipment = equipment;
  }

  public Patient getPatient() {
    return patient;
  }

  public void setPatient(Patient patient) {
    this.patient = patient;
  }
}

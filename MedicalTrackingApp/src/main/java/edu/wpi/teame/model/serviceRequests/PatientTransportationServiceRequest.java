package edu.wpi.teame.model.serviceRequests;

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
            : DataBaseObjectType.ExternalPatientTransportation,
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

  public PatientTransportationServiceRequest(boolean isInternal, ResultSet resultSet)
      throws SQLException {
    super(
        resultSet,
        isInternal
            ? DataBaseObjectType.InternalPatientTransferSR
            : DataBaseObjectType.ExternalPatientTransportation);
    this.destination =
        DBManager.getInstance().getLocationManager().get(resultSet.getInt("destinationID"));
    this.equipment =
        DBManager.getInstance().getEquipmentManager().get(resultSet.getInt("equipmentID"));
    this.patient = DBManager.getInstance().getPatientManager().get(resultSet.getInt("patientID"));
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
  public String getSQLUpdateString() {
    return super.getSQLUpdateString()
        + ", "
        + "destinationID = "
        + destination.getId()
        + ", "
        + "equipmentID = "
        + equipment.getId()
        + ", "
        + "patientID = "
        + patient.getId()
        + "WHERE id = "
        + id;
  }

  @Override
  public String getTableColumns() {
    return super.getTableColumns() + ", destinationID, equipmentID, patientID)";
  }

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

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

public final class InternalPatientTransporationServiceRequest extends ServiceRequest {
  private Location destination;
  private Equipment equipment;
  private Patient patient;

  public InternalPatientTransporationServiceRequest(
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
        DataBaseObjectType.InternalPatientTransferSR,
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

  public InternalPatientTransporationServiceRequest(ResultSet resultSet) throws SQLException {
    super(resultSet, DataBaseObjectType.InternalPatientTransferSR);
    this.destination =
        DBManager.getInstance().getLocationManager().get(resultSet.getInt("locationID"));
    this.equipment =
        DBManager.getInstance().getEquipmentManager().get(resultSet.getInt("equipmentID"));
    this.patient = DBManager.getInstance().getPatientManager().get(resultSet.getInt("patientID"));
  }

  @Override
  public String getSQLInsertString() {
    // TODO: Needs to be updated
    return null;
    // return super.getSQLInsertString();
  }

  @Override
  public String getSQLUpdateString() {
    // TODO: Needs to be updated
    return null;
    // return super.getSQLUpdateString()
  }

  @Override
  public String getTableColumns() {
    // TODO: Needs to be updated
    return null;
    // return super.getTableColumns();
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

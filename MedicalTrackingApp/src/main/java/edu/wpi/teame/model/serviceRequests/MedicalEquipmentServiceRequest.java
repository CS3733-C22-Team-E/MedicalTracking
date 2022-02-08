package edu.wpi.teame.model.serviceRequests;

import edu.wpi.teame.model.*;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicalEquipmentServiceRequest extends ServiceRequest {
  private Equipment equipment;
  private String patient;

  public MedicalEquipmentServiceRequest(
      ServiceRequestStatus requestStatus,
      Employee assignee,
      Location location,
      Date closeDate,
      Date openDate,
      int id,
      Equipment equipment,
      String patient) {
    super(requestStatus, assignee, location, closeDate, openDate, id);
    this.equipment = equipment;
    this.patient = patient;
  }

  public MedicalEquipmentServiceRequest(ResultSet resultSet) throws SQLException {
    // TODO: actually call employee, location, equipment in constructor
    super(
        ServiceRequestStatus.values()[resultSet.getInt("requestStatus")],
        null,
        null,
        resultSet.getDate("closeDate"),
        resultSet.getDate("openDate"),
        resultSet.getInt("id"));
    this.equipment = null;
    this.patient = resultSet.getString("patient");
  }

  public String toString() {
    return "ID: "
        + id
        + ", Patient: "
        + patient
        + ", location: "
        + location.getId()
        + ", openDate: "
        + openDate
        + ", endTime: "
        + closeDate
        + ", employee: "
        + employee
        + ", equipment: "
        + equipment.getId()
        + ", status: "
        + status;
  }

  public Equipment getEquipment() {
    return equipment;
  }

  public void setEquipment(Equipment equipment) {
    this.equipment = equipment;
  }

  public String getPatient() {
    return patient;
  }

  public void setPatient(String patient) {
    this.patient = patient;
  }

  @Override
  public DataBaseObjectType getDBType() {
    return DataBaseObjectType.MedicalEquipmentSR;
  }

  @Override
  public String toSQLInsertString() {
    return super.toSQLInsertString() + ", " + equipment.getId() + ", " + patient.toString();
  }

  @Override
  public String toSQLUpdateString() {
    return super.toSQLInsertString()
        + ", equipment = "
        + equipment.getId()
        + ", patient = "
        + patient.toString();
  }
}

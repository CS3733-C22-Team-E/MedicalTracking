package edu.wpi.teame.model.serviceRequests;

import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.Patient;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.ServiceRequestPriority;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class GiftAndFloralServiceRequest extends ServiceRequest {
  private Patient patient;

  public GiftAndFloralServiceRequest(
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
      Patient patient) {
    super(
        DataBaseObjectType.GiftAndFloralSR,
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
    this.patient = patient;
  }

  public GiftAndFloralServiceRequest(ResultSet resultSet) throws SQLException {
    super(resultSet, DataBaseObjectType.GiftAndFloralSR);
    this.patient = DBManager.getInstance().getPatientManager().get(resultSet.getInt("patientID"));
  }

  @Override
  public String getSQLInsertString() {
    return super.getSQLInsertString() + ", " + patient.getId();
    // return super.getSQLInsertString();
  }

  @Override
  public String getSQLUpdateString() {
    return super.getSQLUpdateString() + ", " + "patientID = " + patient.getId() + "WHERE id = " + id;
    // return super.getSQLUpdateString()
  }

  @Override
  public String getTableColumns() {
    return super.getTableColumns() + ", patientID";
    // return super.getTableColumns();
  }

  public Patient getPatient() {
    return patient;
  }

  public void setPatient(Patient patient) {
    this.patient = patient;
  }
}

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

public final class ReligiousServiceRequest extends ServiceRequest {
  private Patient patient;
  private String religion;

  public ReligiousServiceRequest(
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
      Patient patient,
      String religion) {
    super(
        DataBaseObjectType.ReligiousSR,
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
    this.religion = religion;
  }

  public ReligiousServiceRequest(ResultSet resultSet) throws SQLException {
    super(resultSet, DataBaseObjectType.ReligiousSR);
    this.patient = DBManager.getInstance().getPatientManager().get(resultSet.getInt("patientID"));
    this.religion = resultSet.getString("religion");
  }

  @Override
  public String getSQLInsertString() {
    return super.getSQLInsertString() + ", " + patient.getId() + ", '" + religion + "'";
  }

  @Override
  public String getSQLUpdateString() {
    return super.getSQLUpdateString()
        + ", "
        + "patientID = "
        + patient.getId()
        + ", "
        + "religion = '"
        + religion
        + "'"
        + "WHERE id = "
        + id;
  }

  @Override
  public String getTableColumns() {
    return "(locationID, assigneeID, openDate, closeDate, status, title, additionalInfo, priority, requestDate, patientID, religion)";
  }

  public Patient getPatient() {
    return patient;
  }

  public void setPatient(Patient patient) {
    this.patient = patient;
  }

  public String getReligion() {
    return religion;
  }

  public void setReligion(String religion) {
    this.religion = religion;
  }
}

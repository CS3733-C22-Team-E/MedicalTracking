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

public class ReligiousServiceRequest extends ServiceRequest {
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
    // TODO: Needs to be updated. Done

    return super.getSQLInsertString() + ", " + patient.getId() + ", '" + religion + "')";
    // return super.getSQLInsertString();
  }

  @Override
  public String getSQLUpdateString() {
    // TODO: Needs to be updated. Done
    return super.getSQLUpdateString()
        + ", "
        + "patient = "
        + patient.getId()
        + ", "
        + "religion = '"
        + religion
        + "'"
        + "WHERE id = "
        + id;
    // return super.getSQLUpdateString()
  }

  @Override
  public String getTableColumns() {
    // TODO: Needs to be updated. Done
    return super.getTableColumns() + ", patient, religion)";
    // return super.getTableColumns();
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

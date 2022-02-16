package edu.wpi.teame.model.serviceRequests;

import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.Patient;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.LanguageType;
import edu.wpi.teame.model.enums.ServiceRequestPriority;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class LanguageInterpreterServiceRequest extends ServiceRequest {
  private LanguageType language;
  private Patient patient;

  public LanguageInterpreterServiceRequest(
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
      LanguageType language,
      Patient patient) {
    super(
        DataBaseObjectType.LanguageInterpreterSR,
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
    this.language = language;
    this.patient = patient;
  }

  public LanguageInterpreterServiceRequest(ResultSet resultSet) throws SQLException {
    super(resultSet, DataBaseObjectType.LanguageInterpreterSR);
    this.patient = DBManager.getInstance().getPatientManager().get(resultSet.getInt("patientID"));
    this.language = LanguageType.values()[resultSet.getInt("language")];
  }

  @Override
  public String getSQLInsertString() {
    return getRawUpdateString() + ", " + language.ordinal() + ", " + patient.getId();
  }

  @Override
  public String getSQLUpdateString() {
    return super.getSQLUpdateString()
        + ", "
        + "language = "
        + language.ordinal()
        + ", "
        + "patientID = "
        + patient.getId()
        + " WHERE id = "
        + id;
  }

  @Override
  public String getTableColumns() {
    return "(locationID, assigneeID, openDate, closeDate, status, title, additionalInfo, priority, requestDate, language, patientID)";
  }

  public LanguageType getLanguage() {
    return language;
  }

  public void setLanguage(LanguageType language) {
    this.language = language;
  }

  public Patient getPatient() {
    return patient;
  }

  public void setPatient(Patient patient) {
    this.patient = patient;
  }
}

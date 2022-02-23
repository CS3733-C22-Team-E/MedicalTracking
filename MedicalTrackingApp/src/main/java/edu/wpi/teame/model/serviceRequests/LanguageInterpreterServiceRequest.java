package edu.wpi.teame.model.serviceRequests;

import edu.wpi.teame.db.CSVLineData;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
    this.patient =
        (Patient)
            DBManager.getInstance()
                .getManager(DataBaseObjectType.Patient)
                .get(resultSet.getInt("patientID"));
    this.language = LanguageType.values()[resultSet.getInt("language")];
  }

  public LanguageInterpreterServiceRequest(CSVLineData lineData)
      throws SQLException, ParseException {
    super(lineData, DataBaseObjectType.LanguageInterpreterSR);
    this.patient = (Patient) lineData.getDBObject(DataBaseObjectType.Patient, "patientID");
    this.language = LanguageType.values()[lineData.getColumnInt("language")];
  }

  @Override
  public String getSQLUpdateString() {
    return getRawUpdateString()
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
  public String getSQLInsertString() {
    return super.getSQLInsertString() + ", " + language.ordinal() + ", " + patient.getId();
  }

  @Override
  public String[] toCSVData() {
    List<String> csvData = new ArrayList<>();
    csvData.addAll(List.of(super.toCSVData()));
    csvData.add(Integer.toString(language.ordinal()));
    csvData.add(Integer.toString(patient.getId()));

    String[] retArr = new String[csvData.size()];
    return csvData.toArray(retArr);
  }

  @Override
  public String[] getCSVHeaders() {
    List<String> csvHeaders = new ArrayList<>();
    csvHeaders.addAll(List.of(super.getCSVHeaders()));
    csvHeaders.add("language");
    csvHeaders.add("patientID");

    String[] retArr = new String[csvHeaders.size()];
    return csvHeaders.toArray(retArr);
  }

  @Override
  public String getTableColumns() {
    return "(locationID, assigneeID, openDate, closeDate, status, title, additionalInfo, priority, requestDate, language, patientID)";
  }

  // Getters and Setters
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

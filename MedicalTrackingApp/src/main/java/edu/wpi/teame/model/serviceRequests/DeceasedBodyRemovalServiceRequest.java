package edu.wpi.teame.model.serviceRequests;

import edu.wpi.teame.db.CSVLineData;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public final class DeceasedBodyRemovalServiceRequest extends ServiceRequest {
  private Patient patient;

  public DeceasedBodyRemovalServiceRequest(
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
        DataBaseObjectType.DeceasedBodySR,
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

  public DeceasedBodyRemovalServiceRequest(ResultSet resultSet) throws SQLException {
    super(resultSet, DataBaseObjectType.DeceasedBodySR);
    this.patient =
        (Patient)
            DBManager.getInstance()
                .getManager(DataBaseObjectType.Patient)
                .get(resultSet.getInt("patientID"));
  }

  public DeceasedBodyRemovalServiceRequest(CSVLineData lineData)
      throws SQLException, ParseException {
    super(lineData, DataBaseObjectType.DeceasedBodySR);
    this.patient = (Patient) lineData.getDBObject(DataBaseObjectType.Patient, "patientID");
  }

  // in order for a Codec registry to work properly, this constructor needs to exist
  // for now, it only sets dbType
  public DeceasedBodyRemovalServiceRequest() {
    dbType = DataBaseObjectType.DeceasedBodySR;
  }

  @Override
  public String getSQLUpdateString() {
    return getRawUpdateString() + ", " + "patientID = " + patient.getId() + "WHERE id = " + id;
  }

  @Override
  public String getSQLInsertString() {
    return super.getSQLInsertString() + ", " + patient.getId();
  }

  @Override
  public String[] toCSVData() {
    List<String> csvData = new ArrayList<>();
    csvData.addAll(List.of(super.toCSVData()));

    String[] retArr = new String[csvData.size()];
    return csvData.toArray(retArr);
  }

  @Override
  public String[] getCSVHeaders() {
    List<String> csvHeaders = new ArrayList<>();
    csvHeaders.addAll(List.of(super.getCSVHeaders()));
    csvHeaders.add("patientID");
    return (String[]) csvHeaders.toArray();
  }

  @Override
  public String getTableColumns() {
    return "(locationID, assigneeID, openDate, closeDate, status, title, additionalInfo, priority, requestDate, patientID)";
  }

  // Getters and Setters
  public Patient getPatient() {
    return patient;
  }

  public void setPatient(Patient patient) {
    this.patient = patient;
  }
}

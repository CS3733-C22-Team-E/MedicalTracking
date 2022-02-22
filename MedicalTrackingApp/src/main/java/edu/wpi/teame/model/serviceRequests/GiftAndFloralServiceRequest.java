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
    this.patient =
        (Patient)
            DBManager.getManager(DataBaseObjectType.Patient).get(resultSet.getInt("patientID"));
  }

  public GiftAndFloralServiceRequest(CSVLineData lineData) throws SQLException, ParseException {
    super(lineData, DataBaseObjectType.GiftAndFloralSR);
    this.patient =
        (Patient)
            DBManager.getManager(DataBaseObjectType.Patient)
                .get(lineData.getColumnInt("patientID"));
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

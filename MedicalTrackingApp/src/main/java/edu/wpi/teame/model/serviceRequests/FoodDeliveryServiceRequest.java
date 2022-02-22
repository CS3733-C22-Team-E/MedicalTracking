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

public final class FoodDeliveryServiceRequest extends ServiceRequest {
  private Patient patient;
  private String food;

  public FoodDeliveryServiceRequest(
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
      String food) {
    super(
        DataBaseObjectType.FoodDeliverySR,
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
    this.food = food;
  }

  public FoodDeliveryServiceRequest(ResultSet resultSet) throws SQLException {
    super(resultSet, DataBaseObjectType.FoodDeliverySR);
    this.patient =
        (Patient)
            DBManager.getManager(DataBaseObjectType.Patient).get(resultSet.getInt("patientID"));
    this.food = resultSet.getString("food");
  }

  public FoodDeliveryServiceRequest(CSVLineData lineData) throws SQLException, ParseException {
    super(lineData, DataBaseObjectType.FoodDeliverySR);
    this.patient =
        (Patient)
            DBManager.getManager(DataBaseObjectType.Patient)
                .get(lineData.getColumnInt("patientID"));
    this.food = lineData.getColumnString("food");
  }

  @Override
  public String getSQLUpdateString() {
    return getRawUpdateString()
        + ", "
        + "patientID = "
        + patient.getId()
        + ", "
        + "food = '"
        + food
        + "' WHERE id = "
        + id;
  }

  @Override
  public String getSQLInsertString() {
    return super.getSQLInsertString() + ", " + patient.getId() + ", '" + food + "'";
  }

  @Override
  public String getTableColumns() {
    return "(locationID, assigneeID, openDate, closeDate, status, title, additionalInfo, priority, requestDate, patientID, food)";
  }

  // Getters and Setters
  public Patient getPatient() {
    return patient;
  }

  public void setPatient(Patient patient) {
    this.patient = patient;
  }

  public String getFood() {
    return food;
  }

  public void setFood(String food) {
    this.food = food;
  }

  public String toString() {
    return super.dbType + " id is " + id;
  }
}

package edu.wpi.teame.model.serviceRequests;

import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.ISQLSerializable;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.ServiceRequestPriority;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;

public class ServiceRequest implements ISQLSerializable {
  protected DataBaseObjectType dbType;
  protected ServiceRequestPriority priority;
  protected ServiceRequestStatus status;
  protected String additionalInfo;
  protected Employee assignee;
  protected Location location;
  protected Date requestDate;
  protected Date closeDate;
  protected Date openDate;
  protected String title;
  protected int id;

  public ServiceRequest(
      DataBaseObjectType type,
      ServiceRequestPriority priority,
      ServiceRequestStatus status,
      String additionalInfo,
      Employee assignee,
      Location location,
      Date requestDate,
      Date closeDate,
      Date openDate,
      String title,
      int id) {
    this.dbType = type;
    this.priority = priority;
    this.status = status;
    this.additionalInfo = additionalInfo;
    this.assignee = assignee;
    this.location = location;
    this.requestDate = requestDate;
    this.closeDate = closeDate;
    this.openDate = openDate;
    this.title = title;
    this.id = id;
  }

  public ServiceRequest(ResultSet resultSet, DataBaseObjectType type) throws SQLException {
    this.priority = ServiceRequestPriority.values()[resultSet.getInt("status")];
    this.location =
        DBManager.getInstance().getLocationManager().get(resultSet.getInt("locationID"));
    this.assignee =
        DBManager.getInstance().getEmployeeManager().get(resultSet.getInt("assigneeID"));
    this.status = ServiceRequestStatus.values()[resultSet.getInt("status")];
    this.additionalInfo = resultSet.getString("additionalInfo");
    this.requestDate = resultSet.getDate("requestDate");
    this.closeDate = resultSet.getDate("closeDate");
    this.openDate = resultSet.getDate("openDate");
    this.title = resultSet.getString("title");
    this.id = resultSet.getInt("id");
    this.dbType = type;
  }

  @Override
  public String getSQLInsertString() {
    String closeDateString = closeDate == null ? "NULL" : " '" + dateToSQLString(closeDate) + "'";
    String assigneeString = assignee == null ? "NULL" : " " + assignee.getId();
    return location.getId()
        + ", "
        + assigneeString
        + ", '"
        + dateToSQLString(openDate)
        + "', "
        + closeDateString
        + ", "
        + status.ordinal()
        + ", '"
        + title
        + "', '"
        + additionalInfo
        + "', "
        + priority.ordinal()
        + ", '"
        + dateToSQLString(requestDate)
        + "'";
  }

  @Override
  public String getSQLUpdateString() {
    String closeDateString = closeDate == null ? "NULL" : " '" + dateToSQLString(closeDate) + "'";
    return "locationID = "
        + location.getId()
        + ", "
        + "assigneeID = "
        + assignee.getId()
        + ", "
        + "openDate = '"
        + dateToSQLString(openDate)
        + "', "
        + "closeDate = "
        + closeDateString
        + ", "
        + "status = "
        + status.ordinal()
        + ", "
        + "title = '"
        + title
        + "', "
        + "additionalInfo = '"
        + additionalInfo
        + "', "
        + "priority = "
        + priority.ordinal()
        + ", "
        + "requestDate = '"
        + dateToSQLString(requestDate)
        + "'";
  }

  @Override
  public String getTableColumns() {
    return "(locationID, assigneeID, openDate, closeDate, status, title, additionalInfo, priority, requestDate)";
  }

  @Override
  public DataBaseObjectType getDBType() {
    return dbType;
  }

  protected String dateToSQLString(Date date) {
    String dateAsString =
        openDate.toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant().toString();
    return dateAsString.replace("T", " ").replace("Z", "");
  }

  public ServiceRequestPriority getPriority() {
    return priority;
  }

  public void setPriority(ServiceRequestPriority priority) {
    this.priority = priority;
  }

  public ServiceRequestStatus getStatus() {
    return status;
  }

  public void setStatus(ServiceRequestStatus status) {
    this.status = status;
  }

  public String getAdditionalInfo() {
    return additionalInfo;
  }

  public void setAdditionalInfo(String additionalInfo) {
    this.additionalInfo = additionalInfo;
  }

  public Employee getAssignee() {
    return assignee;
  }

  public void setAssignee(Employee assignee) {
    this.assignee = assignee;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public Date getRequestDate() {
    return requestDate;
  }

  public void setRequestDate(Date requestDate) {
    this.requestDate = requestDate;
  }

  public Date getCloseDate() {
    return closeDate;
  }

  public void setCloseDate(Date closeDate) {
    this.closeDate = closeDate;
  }

  public Date getOpenDate() {
    return openDate;
  }

  public void setOpenDate(Date openDate) {
    this.openDate = openDate;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String toString() {
    return this.dbType + " id is " + id;
  }
}

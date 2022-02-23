package edu.wpi.teame.model.serviceRequests;

import edu.wpi.teame.db.CSVLineData;
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
import java.text.ParseException;
import java.time.LocalTime;

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
  protected boolean isDeleted;

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
    this.isDeleted = false;
  }

  public ServiceRequest(ResultSet resultSet, DataBaseObjectType type) throws SQLException {
    this.priority = ServiceRequestPriority.values()[resultSet.getInt("priority")];
    this.location =
        (Location)
            DBManager.getManager(DataBaseObjectType.Location).get(resultSet.getInt("locationID"));
    this.assignee =
        (Employee)
            DBManager.getManager(DataBaseObjectType.Employee).get(resultSet.getInt("assigneeID"));
    this.status = ServiceRequestStatus.values()[resultSet.getInt("status")];
    this.additionalInfo = resultSet.getString("additionalInfo");
    this.requestDate = resultSet.getDate("requestDate");
    this.isDeleted = resultSet.getBoolean("isDeleted");
    this.closeDate = resultSet.getDate("closeDate");
    this.openDate = resultSet.getDate("openDate");
    this.title = resultSet.getString("title");
    this.id = resultSet.getInt("id");
    this.dbType = type;
  }

  public ServiceRequest(CSVLineData lineData, DataBaseObjectType type)
      throws SQLException, ParseException {
    this.priority = ServiceRequestPriority.values()[lineData.getColumnInt("priority")];
    this.status = ServiceRequestStatus.values()[lineData.getColumnInt("status")];
    this.assignee = (Employee) lineData.getDBObject(DataBaseObjectType.Employee, "assigneeID");
    this.location = (Location) lineData.getDBObject(DataBaseObjectType.Location, "locationID");
    this.additionalInfo = lineData.getColumnString("additionalInfo");
    this.requestDate = lineData.getColumnDate("requestDate");
    this.closeDate = lineData.getColumnDate("closeDate");
    this.openDate = lineData.getColumnDate("openDate");
    this.title = lineData.getColumnString("title");
    this.id = lineData.getColumnInt("id");
    this.dbType = type;
  }

  @Override
  public String toString() {
    return new StringBuilder()
        .append("id = ")
        .append(id)
        .append(", locationID = ")
        .append(location.getId())
        .append(", assigneeID = ")
        .append(assignee.getId())
        .append(", openDate = '")
        .append(dateToSQLString(openDate))
        .append("', closeDate = ")
        .append(closeDate.toString())
        .append(", status = ")
        .append(status.ordinal())
        .append(", title = '")
        .append(title)
        .append("', additionalInfo = '")
        .append(additionalInfo)
        .append("', priority = ")
        .append(priority.ordinal())
        .append(", requestDate = '")
        .append(dateToSQLString(requestDate))
        .append("'")
        .toString();
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
    return getRawUpdateString() + " WHERE id = " + id;
  }

  protected String getRawUpdateString() {
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
  public String[] toCSVData() {
    return new String[] {
      Integer.toString(id),
      Integer.toString(location.getId()),
      assignee == null ? "" : Integer.toString(assignee.getId()),
      openDate.toString(),
      closeDate == null ? "" : closeDate.toString(),
      Integer.toString(status.ordinal()),
      title,
      additionalInfo,
      Integer.toString(priority.ordinal()),
      requestDate.toString()
    };
  }

  @Override
  public String[] getCSVHeaders() {
    return new String[] {
      "id",
      "locationID",
      "assigneeID",
      "openDate",
      "closeDate",
      "status",
      "title",
      "additionalInfo",
      "priority",
      "requestDate"
    };
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
    LocalTime localTime = LocalTime.now();
    String dateAsString = openDate.toLocalDate().atTime(localTime).toString();
    dateAsString = dateAsString.substring(0, dateAsString.indexOf('.'));
    return dateAsString.replace("T", " ").replace("Z", "");
  }

  // Getters and Setters
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

  public boolean getIsDeleted() {
    return isDeleted;
  }
}

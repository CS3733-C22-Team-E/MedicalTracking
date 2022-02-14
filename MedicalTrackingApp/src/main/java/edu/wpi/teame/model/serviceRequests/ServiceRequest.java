package edu.wpi.teame.model.serviceRequests;

import edu.wpi.teame.db.ISQLSerializable;
import edu.wpi.teame.db.objectManagers.EmployeeManager;
import edu.wpi.teame.db.objectManagers.LocationManager;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.ServiceRequestPriority;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class ServiceRequest implements ISQLSerializable {
  protected ServiceRequestPriority priority;
  protected ServiceRequestStatus status;
  protected DataBaseObjectType dbType;
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
    this.assignee = new EmployeeManager().get(resultSet.getInt("employeeID"));
    this.location = new LocationManager().get(resultSet.getInt("locationID"));
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
    // TODO: Needs to be updated
    return null;
  }

  @Override
  public String getSQLUpdateString() {
    // TODO: Needs to be updated
    return null;

    //    return "locationID = "
    //        + location.getId()
    //        + ", status = "
    //        + status.ordinal()
    //        + ", employeeID = "
    //        + assignee.getId()
    //        + ", closeDate = '"
    //        + closeDate.toString()
    //        + "', openDate = '"
    //        + openDate.toString()
    //        + "' WHERE id = "
    //        + id;
  }

  @Override
  public String getTableColumns() {
    // TODO: Needs to be updated
    return null;

    // return "(locationID, status, employeeID, closeDate, openDate, ";
  }

  @Override
  public DataBaseObjectType getDBType() {
    return dbType;
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
}

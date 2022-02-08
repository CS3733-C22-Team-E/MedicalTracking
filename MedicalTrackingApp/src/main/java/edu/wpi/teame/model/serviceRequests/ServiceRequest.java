package edu.wpi.teame.model.serviceRequests;

import edu.wpi.teame.db.ISQLSerializable;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import java.sql.Date;

public abstract class ServiceRequest implements ISQLSerializable {
  protected ServiceRequestStatus status;
  protected Employee assignee;
  protected Location location;
  protected Date closeDate;
  protected Date openDate;
  protected int id;

  protected ServiceRequest(
      ServiceRequestStatus requestStatus,
      Employee assignee,
      Location location,
      Date closeDate,
      Date openDate,
      int id) {
    this.status = requestStatus;
    this.closeDate = closeDate;
    this.assignee = assignee;
    this.location = location;
    this.openDate = openDate;
    this.id = id;
  }

  @Override
  public String toSQLInsertString() {
    return status.toString()
        + ", "
        + assignee
        + ", "
        + location.getId()
        + ", "
        + closeDate.toString()
        + ", "
        + openDate.toString();
  }

  public String toSQLUpdateString() {
    return "status = "
        + status.toString()
        + ", assignee = "
        + assignee
        + ", location = "
        + location.getId()
        + ", closeDate = "
        + closeDate.toString()
        + ", openDate = "
        + openDate.toString();
  }

  public ServiceRequestStatus getStatus() {
    return status;
  }

  public void setStatus(ServiceRequestStatus status) {
    this.status = status;
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

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}

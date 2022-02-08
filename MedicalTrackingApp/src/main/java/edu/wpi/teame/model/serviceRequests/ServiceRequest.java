package edu.wpi.teame.model.serviceRequests;

import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.FloorType;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import java.sql.Date;

public abstract class ServiceRequest implements ISQLSerializable {
  protected ServiceRequestStatus status;
  protected Employee assignee;
  protected Location location;
  protected FloorType floor;
  protected Date closeDate;
  protected Date openDate;
  protected int id;

  protected ServiceRequest(
      ServiceRequestStatus requestStatus,
      Employee assignee,
      Location location,
      FloorType floorType,
      Date closeDate,
      Date openDate,
      int id) {
    this.status = requestStatus;
    this.closeDate = closeDate;
    this.assignee = assignee;
    this.location = location;
    this.openDate = openDate;
    this.floor = floorType;
    this.id = id;
  }

  @Override
  public String toSQLString() {
    return status.toString()
        + ", "
        + assignee
        + ", "
        + location.getId()
        + ", "
        + floor.toString()
        + ", "
        + closeDate.toString()
        + ", "
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

  public FloorType getFloor() {
    return floor;
  }

  public void setFloor(FloorType floor) {
    this.floor = floor;
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

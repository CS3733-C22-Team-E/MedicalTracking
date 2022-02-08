package edu.wpi.teame.model.serviceRequests;

import edu.wpi.teame.db.ISQLSerializable;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import java.sql.Date;

public abstract class ServiceRequest implements ISQLSerializable {
  protected ServiceRequestStatus status;
  protected Employee employee;
  protected Location location;
  protected Date closeDate;
  protected Date openDate;
  protected int id;

  protected ServiceRequest(
      ServiceRequestStatus requestStatus,
      Employee employee,
      Location location,
      Date closeDate,
      Date openDate,
      int id) {
    this.status = requestStatus;
    this.closeDate = closeDate;
    this.employee = employee;
    this.location = location;
    this.openDate = openDate;
    this.id = id;
  }

  public ServiceRequestStatus getStatus() {
    return status;
  }

  public void setStatus(ServiceRequestStatus status) {
    this.status = status;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
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

  @Override
  public String toSQLInsertString() {
    return id
        + ", "
        + location.getId()
        + ", "
        + status.toString()
        + ", "
        + employee
        + ", "
        + closeDate.toString()
        + ", "
        + openDate.toString();
  }

  @Override
  public String toSQLUpdateString() {
    return "locationID = "
        + location.getId()
        + ", status = "
        + status.toString()
        + ", employeeID = "
        + employee.getId()
        + ", closeDate = "
        + closeDate.toString()
        + ", openDate = "
        + openDate.toString();
  }

  @Override
  public String getTableColumns() {
    return "(locationID, status, employeeID, closeDate, endTime, openDate, ";
  }
}

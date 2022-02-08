package edu.wpi.teame.model.serviceRequests;

import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SecurityServiceRequest extends ServiceRequest {
  public SecurityServiceRequest(
      ServiceRequestStatus requestStatus,
      Employee assignee,
      Location location,
      Date closeDate,
      Date openDate,
      int id) {
    super(requestStatus, assignee, location, closeDate, openDate, id);
  }

  public SecurityServiceRequest(ResultSet resultSet) throws SQLException {
    super(
        ServiceRequestStatus.values()[resultSet.getInt("status")],
        null,
        null,
        resultSet.getDate("closeDate"),
        resultSet.getDate("openDate"),
        resultSet.getInt("id"));
  }

  @Override
  public DataBaseObjectType getDBType() {
    return DataBaseObjectType.SecuritySR;
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
    return "(locationID, status, employeeID, closeDate, endTime, openDate) ";
  }
}
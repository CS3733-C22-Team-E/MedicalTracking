package edu.wpi.teame.model.serviceRequests;

import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SanitationServiceRequest extends ServiceRequest {
  public SanitationServiceRequest(
      ServiceRequestStatus requestStatus,
      Employee assignee,
      Location location,
      Date closeDate,
      Date openDate,
      int id) {
    super(requestStatus, assignee, location, closeDate, openDate, id);
  }

  public SanitationServiceRequest(ResultSet resultSet) throws SQLException {
    // TODO: actually call employee, location, equipment in constructor
    super(
        ServiceRequestStatus.values()[resultSet.getInt("requestStatus")],
        null,
        null,
        resultSet.getDate("closeDate"),
        resultSet.getDate("openDate"),
        resultSet.getInt("id"));
  }

  @Override
  public DataBaseObjectType getDBType() {
    return DataBaseObjectType.SanitationSR;
  }
}

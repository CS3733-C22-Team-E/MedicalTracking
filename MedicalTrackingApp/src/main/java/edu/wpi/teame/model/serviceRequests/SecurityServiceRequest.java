package edu.wpi.teame.model.serviceRequests;

import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import java.sql.Date;

public class SecurityServiceRequest extends ServiceRequest {
  protected SecurityServiceRequest(
      ServiceRequestStatus requestStatus,
      Employee assignee,
      Location location,
      Date closeDate,
      Date openDate,
      int id) {
    super(requestStatus, assignee, location, closeDate, openDate, id);
  }

  @Override
  public DataBaseObjectType getDBType() {
    return DataBaseObjectType.SecuritySR;
  }
}

package edu.wpi.teame.db.objectManagers;

import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.serviceRequests.ServiceRequest;
import java.sql.SQLException;

public final class ServiceRequestManager extends ObjectManager<ServiceRequest> {
  public ServiceRequestManager(DataBaseObjectType dbType) throws SQLException {
    super(dbType);
  }
}

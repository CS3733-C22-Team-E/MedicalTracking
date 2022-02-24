package edu.wpi.teame.db.objectManagers;

import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.serviceRequests.ServiceRequest;

public final class ServiceRequestManager extends ObjectManager<ServiceRequest> {
  public ServiceRequestManager(DataBaseObjectType dbType) {
    super(dbType, 0);
  }
}

package edu.wpi.teame.db.objectManagers.serviceRequests;

import edu.wpi.teame.db.objectManagers.ObjectManager;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.serviceRequests.ServiceRequest;
import java.sql.SQLException;

public final class StandardSRManager extends ObjectManager<ServiceRequest> {
  public StandardSRManager(DataBaseObjectType dbType) throws SQLException {
    super(dbType);
  }
}

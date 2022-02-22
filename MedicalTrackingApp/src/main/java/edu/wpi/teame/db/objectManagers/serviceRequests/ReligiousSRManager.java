package edu.wpi.teame.db.objectManagers.serviceRequests;

import edu.wpi.teame.db.objectManagers.ObjectManager;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.serviceRequests.ReligiousServiceRequest;
import java.sql.SQLException;

public final class ReligiousSRManager extends ObjectManager<ReligiousServiceRequest> {
  public ReligiousSRManager() throws SQLException {
    super(DataBaseObjectType.ReligiousSR);
  }
}

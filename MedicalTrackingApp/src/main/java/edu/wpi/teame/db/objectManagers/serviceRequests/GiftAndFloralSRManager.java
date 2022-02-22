package edu.wpi.teame.db.objectManagers.serviceRequests;

import edu.wpi.teame.db.objectManagers.ObjectManager;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.serviceRequests.GiftAndFloralServiceRequest;
import java.sql.SQLException;

public final class GiftAndFloralSRManager extends ObjectManager<GiftAndFloralServiceRequest> {
  public GiftAndFloralSRManager() throws SQLException {
    super(DataBaseObjectType.GiftAndFloralSR);
  }
}

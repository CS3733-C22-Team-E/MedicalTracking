package edu.wpi.teame.db.objectManagers.serviceRequests;

import edu.wpi.teame.db.objectManagers.ObjectManager;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.serviceRequests.FoodDeliveryServiceRequest;
import java.sql.SQLException;

public final class FoodDeliverySRManager extends ObjectManager<FoodDeliveryServiceRequest> {
  public FoodDeliverySRManager() throws SQLException {
    super(DataBaseObjectType.FoodDeliverySR);
  }
}

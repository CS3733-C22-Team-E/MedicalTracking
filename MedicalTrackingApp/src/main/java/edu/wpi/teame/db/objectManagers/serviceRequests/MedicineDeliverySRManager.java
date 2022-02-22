package edu.wpi.teame.db.objectManagers.serviceRequests;

import edu.wpi.teame.db.objectManagers.ObjectManager;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.serviceRequests.MedicineDeliveryServiceRequest;
import java.sql.SQLException;

public final class MedicineDeliverySRManager extends ObjectManager<MedicineDeliveryServiceRequest> {
  public MedicineDeliverySRManager() throws SQLException {
    super(DataBaseObjectType.MedicineDeliverySR);
  }
}

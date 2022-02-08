package edu.wpi.teame.db.objectManagers;

import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.serviceRequests.MedicineDeliveryServiceRequest;
import java.io.IOException;

public class MedicineDeliveryServiceRequestManager
    extends ObjectManager<MedicineDeliveryServiceRequest> {
  public MedicineDeliveryServiceRequestManager() {
    super(DataBaseObjectType.MedicineDeliverySR);
  }

  @Override
  public void readCSV(String inputFileName) throws IOException {}

  @Override
  public void writeToCSV(String outputFileName) throws IOException {}
}

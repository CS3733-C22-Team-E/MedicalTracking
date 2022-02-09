package edu.wpi.teame.db.objectManagers;

import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.serviceRequests.SecurityServiceRequest;
import java.io.IOException;

public class SecurityServiceRequestManager extends ObjectManager<SecurityServiceRequest> {
  public SecurityServiceRequestManager() {
    super(DataBaseObjectType.SecuritySR);
  }

  @Override
  public void readCSV(String inputFileName) throws IOException {}

  @Override
  public void writeToCSV(String outputFileName) throws IOException {}
}

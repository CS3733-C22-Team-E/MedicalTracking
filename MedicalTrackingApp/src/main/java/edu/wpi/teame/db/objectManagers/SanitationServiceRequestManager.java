package edu.wpi.teame.db.objectManagers;

import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.serviceRequests.SanitationServiceRequest;
import java.io.IOException;

public class SanitationServiceRequestManager extends ObjectManager<SanitationServiceRequest> {
  public SanitationServiceRequestManager() {
    super(DataBaseObjectType.SanitationSR);
  }

  @Override
  public void readCSV(String inputFileName) throws IOException {}

  @Override
  public void writeToCSV(String outputFileName) throws IOException {}
}

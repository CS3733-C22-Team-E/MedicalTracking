package edu.wpi.teame.db.objectManagers.serviceRequests;

import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.db.objectManagers.ObjectManager;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.serviceRequests.PatientTransportationServiceRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public final class PatientTransportationSRManager
    extends ObjectManager<PatientTransportationServiceRequest> {
  public PatientTransportationSRManager(boolean isInternal) {
    super(
        isInternal
            ? DataBaseObjectType.InternalPatientTransferSR
            : DataBaseObjectType.ExternalPatientTransportation);
  }

  @Override
  public void readCSV(String inputFileName)
      throws IOException, SQLException, CsvValidationException, ParseException {}

  @Override
  public void writeToCSV(String outputFileName) throws IOException, SQLException {}
}

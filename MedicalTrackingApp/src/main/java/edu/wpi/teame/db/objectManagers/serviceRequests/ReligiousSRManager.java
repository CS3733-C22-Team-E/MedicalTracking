package edu.wpi.teame.db.objectManagers.serviceRequests;

import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.db.objectManagers.ObjectManager;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.serviceRequests.ReligiousServiceRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public final class ReligiousSRManager extends ObjectManager<ReligiousServiceRequest> {
  public ReligiousSRManager() throws SQLException {
    super(DataBaseObjectType.ReligiousSR);
  }

  @Override
  public void readCSV(String inputFileName)
      throws IOException, SQLException, CsvValidationException, ParseException {}

  @Override
  public void writeToCSV(String outputFileName) throws IOException, SQLException {}
}

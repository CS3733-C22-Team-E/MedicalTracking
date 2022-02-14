package edu.wpi.teame.db.objectManagers.serviceRequests;

import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.db.objectManagers.ObjectManager;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.serviceRequests.FoodDeliveryServiceRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public final class FoodDeliverySRManager extends ObjectManager<FoodDeliveryServiceRequest> {
  public FoodDeliverySRManager() throws SQLException {
    super(DataBaseObjectType.FoodDeliverySR);
  }

  @Override
  public void readCSV(String inputFileName)
      throws IOException, SQLException, CsvValidationException, ParseException {}

  @Override
  public void writeToCSV(String outputFileName) throws IOException, SQLException {}
}

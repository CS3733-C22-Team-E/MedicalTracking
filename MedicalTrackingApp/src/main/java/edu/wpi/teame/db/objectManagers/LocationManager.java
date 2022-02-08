package edu.wpi.teame.db.objectManagers;

// import com.opencsv.CSVReader;
// import com.opencsv.exceptions.CsvValidationException;
// import edu.wpi.teame.db.CSVLineData;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import java.io.IOException;
import java.sql.SQLException;

public final class LocationManager extends ObjectManager<Location> {
  public LocationManager() {
    super(DataBaseObjectType.Location);
  }

  public Location getByName(String locationName) throws SQLException {
    return super.getBy("WHERE locationName = " + locationName).get(0);
  }

  @Override
  public void readCSV(String inputFileName) throws IOException, SQLException {}

  @Override
  public void writeToCSV(String outputFileName) throws IOException {}
}

package edu.wpi.teame.db.objectManagers;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.db.CSVLineData;
import edu.wpi.teame.db.CSVManager;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.BuildingType;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.FloorType;
import edu.wpi.teame.model.enums.LocationType;
import java.io.FileReader;
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
  public void readCSV(String inputFileName)
      throws IOException, CsvValidationException, SQLException {
    String filePath =
        System.getProperty("user.dir") + "/src/main/resources/edu/wpi/teame/" + inputFileName;
    CSVReader csvReader = new CSVReader(new FileReader(filePath));
    CSVLineData lineData = new CSVLineData(csvReader);

    String[] record;
    while ((record = csvReader.readNext()) != null) {
      lineData.setParsedData(record);

      int x = lineData.getColumnInt("xcoord");
      int y = lineData.getColumnInt("ycoord");
      String nodeId = lineData.getColumnString("nodeID");
      String longName = lineData.getColumnString("longName");
      String shortName = lineData.getColumnString("shortName");
      FloorType floor = FloorType.values()[lineData.getColumnInt("floor")];
      BuildingType building = BuildingType.valueOf(lineData.getColumnString("building"));
      LocationType locationType = LocationType.valueOf(lineData.getColumnString("nodeType"));

      Location newLocation =
          new Location(0, longName, x, y, floor, building, locationType, shortName);
      newLocation = DBManager.getInstance().getLocationManager().insert(newLocation);

      // Add the locationID to the HashMap
      CSVManager.getInstance().locationIDMap.put(nodeId, newLocation.getId());
    }
  }

  @Override
  public void writeToCSV(String outputFileName) throws IOException {}
}

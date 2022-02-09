package edu.wpi.teame.db.objectManagers;

// import com.opencsv.CSVReader;
// import com.opencsv.exceptions.CsvValidationException;
// import edu.wpi.teame.db.CSVLineData;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
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
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class LocationManager extends ObjectManager<Location> {
  public LocationManager() {
    super(DataBaseObjectType.Location);
  }

  public Location getByName(String longName) throws SQLException {
    return super.getBy("WHERE longName = '" + longName + "'").get(0);
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
      CSVManager.getInstance().locationIDMap.put(nodeId, newLocation);
    }
  }

  @Override
  public void writeToCSV(String outputFileName) throws IOException, SQLException {
    String filePath =
        System.getProperty("user.dir") + "/src/main/resources/edu/wpi/teame/csv/" + outputFileName;

    FileWriter outputFile = new FileWriter(filePath);
    CSVWriter writer =
        new CSVWriter(
            outputFile,
            ',',
            CSVWriter.NO_QUOTE_CHARACTER,
            CSVWriter.DEFAULT_ESCAPE_CHARACTER,
            CSVWriter.DEFAULT_LINE_END);

    List<Location> listOfLocation = this.getAll();

    List<String[]> data = new ArrayList<String[]>();
    data.add(
        new String[] {
          "nodeID", "xcoord", "ycoord", "floor", "building", "nodeType", "longName", "shortName"
        });

    for (Location location : listOfLocation) {
      data.add(
          new String[] {
            Integer.toString(location.getId()),
            Integer.toString(location.getX()),
            Integer.toString(location.getY()),
            location.getFloor().toString(),
            location.getBuilding().toString(),
            location.getType().toString(),
            location.getLongName(),
            location.getShortName()
          });
    }
    writer.writeAll(data);
    writer.close();
  }
}

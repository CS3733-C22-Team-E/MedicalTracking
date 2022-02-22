package edu.wpi.teame.db.objectManagers;

// import com.opencsv.CSVReader;
// import com.opencsv.exceptions.CsvValidationException;
// import edu.wpi.teame.db.CSVLineData;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.db.CSVLineData;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class LocationManager extends ObjectManager<Location> {
  public LocationManager() throws SQLException {
    super(DataBaseObjectType.Location);
  }

  public Location getByName(String longName) throws SQLException {
    return super.getBy("WHERE longName = '" + longName + "'").get(0);
  }

  @Override
  public void readCSV(String inputFileName)
      throws IOException, CsvValidationException, SQLException {
    String filePath =
        System.getProperty("user.dir") + "/src/main/resources/edu/wpi/teame/csv/" + inputFileName;
    CSVReader csvReader = new CSVReader(new FileReader(filePath));
    CSVLineData lineData = new CSVLineData(csvReader);

    while (lineData.readNext()) {
      insert(new Location(lineData));
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
      data.add(location.toCSVData());
    }
    writer.writeAll(data);
    writer.close();
  }
}

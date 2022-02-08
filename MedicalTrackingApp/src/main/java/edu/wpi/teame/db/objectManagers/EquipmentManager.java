package edu.wpi.teame.db.objectManagers;

// import com.opencsv.exceptions.CsvValidationException;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.db.CSVLineData;
import edu.wpi.teame.db.CSVManager;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Equipment;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.*;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

public final class EquipmentManager extends ObjectManager<Equipment> {
  public EquipmentManager() {
    super(DataBaseObjectType.Equipment);
  }

  public Equipment getByAvailability(EquipmentType equipmentType, boolean hasPatient)
      throws SQLException {
    return super.getBy(
            "WHERE hasPatient = " + hasPatient + " AND type = " + equipmentType.ordinal())
        .get(0);
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

      String nodeId = lineData.getColumnString("nodeID");
      String name = lineData.getColumnString("longName");
      boolean isClean = lineData.getColumnBoolean("isClean");
      boolean hasPatient = lineData.getColumnBoolean("hasPatient");
      EquipmentType equipmentType = EquipmentType.valueOf(lineData.getColumnString("nodeType"));

      String locationNodeID = lineData.getColumnString("locationNodeID");
      Location location = CSVManager.getInstance().locationIDMap.get(locationNodeID);

      Equipment newEquipment = new Equipment(0, location, equipmentType, name, hasPatient, isClean);
      DBManager.getInstance().getEquipmentManager().insert(newEquipment);
    }
  }

  @Override
  public void writeToCSV(String outputFileName) throws IOException {}
}

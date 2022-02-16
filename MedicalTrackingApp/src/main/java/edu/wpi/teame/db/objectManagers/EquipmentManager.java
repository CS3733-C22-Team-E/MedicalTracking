package edu.wpi.teame.db.objectManagers;

// import com.opencsv.exceptions.CsvValidationException;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.db.CSVLineData;
import edu.wpi.teame.db.CSVManager;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Equipment;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class EquipmentManager extends ObjectManager<Equipment> {
  public EquipmentManager() throws SQLException {
    super(DataBaseObjectType.Equipment);
  }

  public Equipment getByAvailability(EquipmentType equipmentType, boolean hasPatient)
      throws SQLException {
    return super.getBy(
            "WHERE hasPatient = " + (hasPatient ? 1 : 0) + " AND type = " + equipmentType.ordinal())
        .get(0);
  }

  @Override
  public void readCSV(String inputFileName)
      throws IOException, CsvValidationException, SQLException {
    String filePath =
        System.getProperty("user.dir") + "/src/main/resources/edu/wpi/teame/csv/" + inputFileName;
    CSVReader csvReader = new CSVReader(new FileReader(filePath));
    CSVLineData lineData = new CSVLineData(csvReader);

    String[] record;
    while ((record = csvReader.readNext()) != null) {
      lineData.setParsedData(record);

      String nodeId = lineData.getColumnString("nodeID");
      String name = lineData.getColumnString("longName");
      boolean isClean = lineData.getColumnBoolean("isClean");
      boolean hasPatient = lineData.getColumnBoolean("hasPatient");
      EquipmentType equipmentType = EquipmentType.values()[(lineData.getColumnInt("nodeType"))];

      String locationNodeID = lineData.getColumnString("locationNodeID");
      Location location = CSVManager.getInstance().locationIDMap.get(locationNodeID);

      Equipment newEquipment = new Equipment(0, location, equipmentType, name, hasPatient, isClean);
      DBManager.getInstance().getEquipmentManager().insert(newEquipment);
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

    List<Equipment> listOfEquipment = this.getAll();

    List<String[]> data = new ArrayList<String[]>();
    data.add(
        new String[] {"nodeID", "locationNodeID", "nodeType", "longName", "hasPatient", "isClean"});

    for (Equipment equipment : listOfEquipment) {
      data.add(
          new String[] {
            Integer.toString(equipment.getId()),
            Integer.toString(equipment.getLocation().getId()),
            Integer.toString(equipment.getType().ordinal()),
            equipment.getName(),
            equipment.isHasPatient() ? "1" : "0",
            equipment.isClean() ? "1" : "0"
          });
    }
    writer.writeAll(data);
    writer.close();
  }
}

package edu.wpi.teame.db.objectManagers;

// import com.opencsv.exceptions.CsvValidationException;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.db.CSVLineData;
import edu.wpi.teame.model.Equipment;
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

  public List<Equipment> getByAllAvailable() throws SQLException {
    return super.getBy("WHERE hasPatient = 0 AND isClean = 0");
  }

  public Equipment getByName(String name) throws SQLException {
    return super.getBy("WHERE name = '" + name + "'").get(0);
  }

  @Override
  public void readCSV(String inputFileName)
      throws IOException, CsvValidationException, SQLException {
    String filePath =
        System.getProperty("user.dir") + "/src/main/resources/edu/wpi/teame/csv/" + inputFileName;
    CSVReader csvReader = new CSVReader(new FileReader(filePath));
    CSVLineData lineData = new CSVLineData(csvReader);

    while (lineData.readNext()) {
      insert(new Equipment(lineData));
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
      data.add(equipment.toCSVData());
    }
    writer.writeAll(data);
    writer.close();
  }
}

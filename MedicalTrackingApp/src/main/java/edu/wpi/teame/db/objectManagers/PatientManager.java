package edu.wpi.teame.db.objectManagers;

import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.model.Patient;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class PatientManager extends ObjectManager<Patient> {
  public PatientManager() {
    super(DataBaseObjectType.Patient);
  }

  @Override
  public void readCSV(String inputFileName)
      throws IOException, SQLException, CsvValidationException, ParseException {
    // TODO: Needs to be implemented
  }

  @Override
  public void writeToCSV(String outputFileName) throws IOException, SQLException {
    // TODO: Needs to be implemented. Almost done. Check TODO below
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

    List<Patient> listOfPatients = this.getAll();

    List<String[]> data = new ArrayList<String[]>();
    // todo: Figure out if we're doing DOB and medications or not
    data.add(new String[] {"name", "currentLocation", "id"});

    for (Patient patient : listOfPatients) {
      data.add(
          new String[] {
            patient.getName(),
            Integer.toString(patient.getCurrentLocation().getId()),
            Integer.toString(patient.getId())
          });
    }

    writer.writeAll(data);
    writer.close();
  }
}

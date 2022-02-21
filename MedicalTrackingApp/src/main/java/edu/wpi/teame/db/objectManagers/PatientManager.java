package edu.wpi.teame.db.objectManagers;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.App;
import edu.wpi.teame.db.CSVLineData;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.Patient;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import java.io.*;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public final class PatientManager extends ObjectManager<Patient> {
  public PatientManager() throws SQLException {
    super(DataBaseObjectType.Patient);
  }

  public Patient getByName(String name) throws SQLException {
    return super.getBy("WHERE name = '" + name + "'").get(0);
  }

  @Override
  public void readCSV(String inputFileName)
      throws IOException, SQLException, CsvValidationException, ParseException {
    InputStream filePath = App.class.getResourceAsStream("csv/" + inputFileName);
    // System.getProperty("user.dir") + "/src/main/resources/edu/wpi/teame/csv/" + inputFileName;
    CSVReader csvReader = new CSVReader(new InputStreamReader(filePath));
    CSVLineData lineData = new CSVLineData(csvReader);

    String[] record;
    while ((record = csvReader.readNext()) != null) {
      lineData.setParsedData(record);

      String name = lineData.getColumnString("name");
      Date dateOfBirth = (lineData.getColumnDate("dateOfBirth"));
      Location currentLocation =
          DBManager.getInstance()
              .getLocationManager()
              .get(lineData.getColumnInt("currentLocation"));

      Patient newPatient = new Patient(currentLocation, dateOfBirth, name, 0);
      DBManager.getInstance().getPatientManager().insert(newPatient);
    }
  }

  @Override
  public void writeToCSV(String outputFileName) throws IOException, SQLException {
    String filePath = App.class.getResource("csv/" + outputFileName).getPath();
    // System.getProperty("user.dir") + "/src/main/resources/edu/wpi/teame/csv/" + outputFileName;

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
    data.add(new String[] {"name", "dateOfBirth", "currentLocation", "id"});

    for (Patient patient : listOfPatients) {
      data.add(
          new String[] {
            patient.getName(),
            patient.getDateOfBirth().toString(),
            Integer.toString(patient.getCurrentLocation().getId()),
            Integer.toString(patient.getId())
          });
    }
    writer.writeAll(data);
    writer.close();
  }
}

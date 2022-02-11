package edu.wpi.teame.db.objectManagers;

import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.model.Patient;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

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
    // TODO: Needs to be implemented
  }
}

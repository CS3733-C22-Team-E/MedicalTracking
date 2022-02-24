package edu.wpi.teame;

import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.objectManagers.CredentialManager;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;

public class Main {

  public static void main(String[] args)
      throws IOException, SQLException, CsvValidationException, ParseException,
          NoSuchAlgorithmException {

    // Setup the DB
    DBManager.getInstance().setupDB();
    DBManager.getInstance().loadDB();
    CredentialManager.getInstance().readCSV("backup/Credentials.csv");

    // Launch App
    App.launch(App.class, args);
  }
}

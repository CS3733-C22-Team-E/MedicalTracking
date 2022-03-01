package edu.wpi.teame;

import com.mongodb.client.*;
import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.*;
import edu.wpi.teame.model.enums.*;
import edu.wpi.teame.model.mongoCodecs.*;
import edu.wpi.teame.model.serviceRequests.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class Main {
  public static void main(String[] args)
      throws IOException, SQLException, CsvValidationException, ParseException,
          org.apache.hc.core5.http.ParseException {

    // Setup the DB
    DBManager.getInstance().setupDB();
    DBManager.getInstance().loadDB();

    // Launch App
    App.launch(App.class, args);
  }
}

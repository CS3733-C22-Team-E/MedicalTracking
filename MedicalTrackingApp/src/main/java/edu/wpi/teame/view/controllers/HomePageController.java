package edu.wpi.teame.view.controllers;

// import com.opencsv.exceptions.CsvValidationException;
import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.db.DBManager;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import javafx.fxml.FXML;

public class HomePageController {
  boolean hasLoaded = false;

  @FXML
  private void gitCallback() throws URISyntaxException, IOException {
    Desktop.getDesktop().browse(new URI("https://github.com/CS3733-C22-Team-E/MedicalTracking"));
  }

  @FXML
  private void onLoadCSVButtonClick() throws IOException, CsvValidationException, SQLException {
    if (hasLoaded) {
      return;
    }

    DBManager.getInstance().getLocationManager().readCSV("csv/TowerLocationsE.csv");
    DBManager.getInstance().getEquipmentManager().readCSV("csv/EquipmentE.csv");
    DBManager.getInstance().getEmployeeManager().readCSV("csv/EmployeesE.csv");
    // hasLoaded = true;
  }
}

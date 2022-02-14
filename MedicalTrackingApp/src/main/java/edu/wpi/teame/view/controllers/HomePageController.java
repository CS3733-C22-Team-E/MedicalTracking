package edu.wpi.teame.view.controllers;

// import com.opencsv.exceptions.CsvValidationException;

import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.objectManagers.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.ParseException;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class HomePageController {
  @FXML
  private void gitCallback() throws URISyntaxException, IOException {
    Desktop.getDesktop().browse(new URI("https://github.com/CS3733-C22-Team-E/MedicalTracking"));
  }
}

package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teamname.App;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class languageInterpreterServiceRequestController {
  @FXML private TextField patientName;
  @FXML private TextField roomNumber;
  @FXML private TextField floor;
  @FXML JFXComboBox languageDropdown;
  @FXML DatePicker startDate;
  @FXML DatePicker endDate;
  @FXML TextField startTime;
  @FXML TextField endTime;

  @FXML
  private void setup() {
    languageDropdown.setItems(
        FXCollections.observableArrayList(
            "English", "Spanish", "Hindi", "Mandarin", "French", "Arabic"));
  }

  @FXML
  private void send() throws IOException {
    App.backToLandingPage();
  }
}

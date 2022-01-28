package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class LanguageInterpreterServiceRequestController extends ServiceRequest {
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
}

package edu.wpi.teame.controllers.serviceRequests;

import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class LanguageInterpreterServiceRequestPageServiceRequestController
    extends ServiceRequestController {
  @FXML private TextField patientName;
  @FXML private TextField roomNumber;
  @FXML private TextField floor;
  @FXML JFXComboBox<String> languageDropdown;
  @FXML DatePicker startDate;
  @FXML DatePicker endDate;

  @FXML ComboBox<String> selectStartTime;
  @FXML ComboBox<String> selectEndTime;

  @FXML
  public void initialize(URL location, ResourceBundle resources) {
    languageDropdown.setItems(
        FXCollections.observableArrayList(
            "English", "Spanish", "Hindi", "Mandarin", "French", "Arabic"));

    ComputerServiceRequestPageServiceRequestController.selectTime(selectStartTime, selectEndTime);
  }
}

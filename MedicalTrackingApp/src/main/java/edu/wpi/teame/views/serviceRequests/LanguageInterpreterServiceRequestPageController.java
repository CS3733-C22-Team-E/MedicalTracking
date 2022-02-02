package edu.wpi.teame.views.serviceRequests;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teame.views.Controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class LanguageInterpreterServiceRequestPageController extends Controller {
  @FXML private TextField patientName;
  @FXML private TextField roomNumber;
  @FXML private TextField floor;
  @FXML JFXComboBox<String> languageDropdown;
  @FXML DatePicker startDate;
  @FXML DatePicker endDate;
  @FXML TextField startTime;
  @FXML TextField endTime;

  @FXML
  public void initialize(URL location, ResourceBundle resources) {
    languageDropdown.setItems(
        FXCollections.observableArrayList(
            "English", "Spanish", "Hindi", "Mandarin", "French", "Arabic"));
  }
}

package edu.wpi.teame.views.serviceRequests;

import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class SanitationServiceRequestPageServiceRequestController extends ServiceRequestController {

  @FXML public Button sendButton;
  @FXML private TextField requestName;
  @FXML private TextField roomNumber;
  @FXML private TextField floorNumber;
  @FXML private TextField startTime;
  @FXML private DatePicker datePicker;
  @FXML private JFXComboBox cleaningOptions;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    cleaningOptions.setItems(
        FXCollections.observableArrayList("Spill", "Entire Room", "Public Space"));
  }
}

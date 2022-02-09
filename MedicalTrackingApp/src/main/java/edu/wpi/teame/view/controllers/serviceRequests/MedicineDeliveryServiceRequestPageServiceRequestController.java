package edu.wpi.teame.view.controllers.serviceRequests;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class MedicineDeliveryServiceRequestPageServiceRequestController
    extends ServiceRequestController {

  @FXML public Button sendButton;
  @FXML private TextField patientName;
  @FXML private TextField roomNumber;
  @FXML private TextField floorNumber;
  @FXML private TextField medicineRequested;
  @FXML private TextField timeNeeded;
  @FXML private DatePicker datePicker;

  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  @FXML
  private void clearText() {
    patientName.setText("");
    roomNumber.setText("");
    floorNumber.setText("");
    medicineRequested.setText("");
    timeNeeded.setText("");
    datePicker.setValue(null);
    datePicker.getEditor().clear();
  }
}

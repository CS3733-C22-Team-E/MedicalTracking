package edu.wpi.teame.controllers.serviceRequests;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class MedicineDeliveryServiceRequestPageServiceRequestController
    extends ServiceRequestController {

  @FXML public Button sendButton;
  @FXML private TextField patientName;
  @FXML private TextField roomNumber;
  @FXML private TextField floorNumber;
  @FXML private TextField medicineRequested;
  @FXML private DatePicker datePicker;
  @FXML private ComboBox<String> medicineTime;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ExternalPatientTransportationServiceRequestPageServiceRequestController.selectGeneralTime(
        medicineTime);
  }
}

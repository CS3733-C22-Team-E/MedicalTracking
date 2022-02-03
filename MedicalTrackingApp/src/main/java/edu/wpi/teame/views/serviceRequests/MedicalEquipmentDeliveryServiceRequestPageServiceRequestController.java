package edu.wpi.teame.views.serviceRequests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;

public class MedicalEquipmentDeliveryServiceRequestPageServiceRequestController
    extends ServiceRequestController {

  @FXML private JFXTextArea patientName;
  @FXML private JFXTextArea roomNumber;
  @FXML private JFXTextArea startTime;
  @FXML private JFXTextArea endTime;
  @FXML private JFXTextArea nameAssigned;

  @FXML private DatePicker datePicker;

  @FXML public JFXButton sendButton;
  @FXML public JFXButton clearButton;

  @FXML private JFXComboBox equipmentNeeded;
  @FXML private JFXComboBox requestState;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    equipmentNeeded.setItems(
        FXCollections.observableArrayList("Bed", "X-Ray", "Infusion Pump", "Recliner"));
    requestState.setItems(
        FXCollections.observableArrayList("Blank", "Waiting For Equipment", "Cancelled", "Done"));
  }
}

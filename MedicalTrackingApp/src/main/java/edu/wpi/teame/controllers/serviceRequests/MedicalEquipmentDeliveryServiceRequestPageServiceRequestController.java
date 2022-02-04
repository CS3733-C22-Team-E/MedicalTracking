package edu.wpi.teame.controllers.serviceRequests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class MedicalEquipmentDeliveryServiceRequestPageServiceRequestController
    extends ServiceRequestController {

  @FXML private TextField patientName;
  @FXML private TextField roomNumber;
  @FXML private TextField startTime;
  @FXML private TextField endTime;
  @FXML private TextField nameAssigned;
  @FXML private TextField floor;

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

  @FXML
  private void clearText() {
    patientName.setText("");
    roomNumber.setText("");
    nameAssigned.setText("");
    startTime.setText("");
    endTime.setText("");
    floor.setText("");
    datePicker.setValue(null);
    datePicker.getEditor().clear();
    equipmentNeeded.valueProperty().set(null);
    requestState.valueProperty().set(null);
  }
}

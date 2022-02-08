package edu.wpi.teame.controllers.serviceRequests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class MedicineDeliveryServiceRequestPageServiceRequestController
    extends ServiceRequestController {

  @FXML public JFXButton sendButton;
  @FXML public JFXButton clearButton;

  @FXML private TextField patientName;
  @FXML private TextField medicineRequested;
  @FXML private TextField timeNeeded;

  @FXML private DatePicker datePicker;

  @FXML public JFXCheckBox completed;

  @FXML public JFXComboBox serviceLocation;
  @FXML public JFXComboBox serviceAssignee;

  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  @FXML
  private void clearText() {
    patientName.setText("");
    medicineRequested.setText("");
    timeNeeded.setText("");
    datePicker.setValue(null);
    datePicker.getEditor().clear();
    serviceLocation.valueProperty().set(null);
    serviceAssignee.valueProperty().set(null);
    completed.setSelected(false);
  }
}

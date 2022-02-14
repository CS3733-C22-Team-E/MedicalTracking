package edu.wpi.teame.view.controllers.serviceRequests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teame.model.enums.EquipmentType;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class InternalPatientTransportationServiceRequestPageServiceRequestController
    extends ServiceRequestController {
  @FXML private TextField patientName;
  @FXML private TextArea additionalInfo;

  @FXML private JFXComboBox priorityDropdown;
  @FXML private JFXComboBox statusDropdown;
  @FXML private JFXComboBox equipmentDropdown;
  @FXML private JFXComboBox locationDropdown;
  @FXML private JFXComboBox destinationDropdown;
  @FXML private JFXComboBox assigneeDropdown;

  @FXML private DatePicker requestDate;

  @FXML public JFXButton clearButton;
  @FXML public JFXButton submitButton;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    statusDropdown.setItems(FXCollections.observableArrayList(ServiceRequestStatus.values()));
    equipmentDropdown.setItems(FXCollections.observableArrayList(EquipmentType.values()));
  }

  @FXML
  private void clearText() {
    patientName.setText("");
    additionalInfo.setText("");
    requestDate.setValue(null);
    requestDate.getEditor().clear();
    priorityDropdown.valueProperty().set(null);
    statusDropdown.valueProperty().set(null);
    equipmentDropdown.valueProperty().set(null);
    locationDropdown.valueProperty().set(null);
    destinationDropdown.valueProperty().set(null);
    assigneeDropdown.valueProperty().set(null);
  }
}

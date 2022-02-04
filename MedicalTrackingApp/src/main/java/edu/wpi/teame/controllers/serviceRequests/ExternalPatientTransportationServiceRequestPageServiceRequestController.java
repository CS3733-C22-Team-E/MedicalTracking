package edu.wpi.teame.controllers.serviceRequests;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

public class ExternalPatientTransportationServiceRequestPageServiceRequestController
    extends ServiceRequestController {
  @FXML private Text externalPatientTransportationServiceRequest;
  @FXML private Text patientNameText;
  @FXML private Text roomText;
  @FXML private Text floorText;
  @FXML private Text dateText;
  @FXML private Text pickupTimeText;
  @FXML private Text chooseTransportationText;
  @FXML private Text dropoffAddressText;

  @FXML private TextField patientName;
  @FXML private TextField floorNum;
  @FXML private TextField roomNum;
  @FXML private TextField pickupTime;
  @FXML private TextField dropoffAddress;

  @FXML private DatePicker datePickup;

  @FXML private ComboBox<String> chooseTransportation;

  @FXML public Button sendButton;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    chooseTransportation.setItems(
        FXCollections.observableArrayList("Ambulance", "Helicopter", "Car", "Other"));
  }

  @FXML
  private void clearText() {
    patientName.setText("");
    floorNum.setText("");
    roomNum.setText("");
    pickupTime.setText("");
    dropoffAddress.setText("");
    datePickup.setValue(null);
    datePickup.getEditor().clear();
    chooseTransportation.valueProperty().set(null);
  }
}

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
  @FXML private TextField dropoffAddress;

  @FXML private DatePicker datePickup;

  @FXML private ComboBox<String> chooseTransportation;

  @FXML public Button sendButton;

  @FXML private ComboBox<String> pickupTime;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    chooseTransportation.setItems(
        FXCollections.observableArrayList("Ambulance", "Helicopter", "Car", "Other"));
    selectGeneralTime(pickupTime);
  }

  static void selectGeneralTime(ComboBox<String> pickupTime) {
    pickupTime.setItems(
        FXCollections.observableArrayList(
            "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00",
            "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30",
            "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00",
            "21:30", "22:00", "22:30", "23:00"));
  }
}

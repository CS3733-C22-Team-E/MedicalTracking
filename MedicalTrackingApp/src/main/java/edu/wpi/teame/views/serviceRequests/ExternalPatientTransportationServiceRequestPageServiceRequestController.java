package edu.wpi.teame.views.serviceRequests;

import java.net.URL;
import java.util.ResourceBundle;
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
  @FXML private Text PickupAddressText;
  @FXML private Text dropoffAddressText;

  @FXML private TextField patientName;
  @FXML private TextField floorNum;
  @FXML private TextField roomNum;
  @FXML private TextField pickupTime;
  @FXML private TextField pickupAddress;
  @FXML private TextField dropoffAddress;

  @FXML private DatePicker datePickup;

  @FXML public Button sendButton;

  @Override
  public void initialize(URL location, ResourceBundle resources) {}
}

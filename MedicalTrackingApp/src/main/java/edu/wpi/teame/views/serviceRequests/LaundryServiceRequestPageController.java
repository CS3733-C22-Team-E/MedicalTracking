package edu.wpi.teame.views.serviceRequests;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LaundryServiceRequestPageController extends ServiceRequest {
  @FXML public Button sendButton;
  @FXML private TextField patientName;
  @FXML private TextField roomNumber;
  @FXML private TextField floor;
}

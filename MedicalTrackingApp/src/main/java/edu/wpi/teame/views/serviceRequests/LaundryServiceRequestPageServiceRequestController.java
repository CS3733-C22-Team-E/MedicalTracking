package edu.wpi.teame.views.serviceRequests;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LaundryServiceRequestPageServiceRequestController extends ServiceRequestController {
  @FXML public Button sendButton;
  @FXML private TextField patientName;
  @FXML private TextField roomNumber;
  @FXML private TextField floor;

  public void initialize(URL location, ResourceBundle resources) {}
}

package edu.wpi.teame.view.controllers.serviceRequests;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LaundryServiceRequestPageServiceRequestController extends ServiceRequestController {
  @FXML public JFXButton sendButton;
  @FXML private TextField patientName;
  @FXML private TextField roomNumber;
  @FXML private TextField floor;

  public void initialize(URL location, ResourceBundle resources) {}
}
package edu.wpi.teame.view.controllers.serviceRequests;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SecurityServiceRequestPageServiceRequestController extends ServiceRequestController {
  @FXML private TextField floorTextBox;
  @FXML private TextField roomTextBox;
  @FXML private JFXButton clearButton;
  @FXML private JFXButton sendButton;

  @FXML
  public void initialize(URL location, ResourceBundle resources) {}

  @FXML
  void clearText() {
    floorTextBox.setText("");
    roomTextBox.setText("");
  }
}

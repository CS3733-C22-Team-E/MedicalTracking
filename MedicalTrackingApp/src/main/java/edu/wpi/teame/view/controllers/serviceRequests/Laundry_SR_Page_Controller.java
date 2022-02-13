package edu.wpi.teame.view.controllers.serviceRequests;

import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Laundry_SR_Page_Controller extends ServiceRequestController {
  @FXML public Button send;
  @FXML public Button clear;
  @FXML private TextField requestDate;
  @FXML private TextField location;
  @FXML private TextField assignee;
  @FXML private JFXComboBox<String> priority;
  @FXML private JFXComboBox<String> status;

  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  @FXML
  private void clearText() {
    requestDate.setText("");
    location.setText("");
    assignee.setText("");
    priority.valueProperty().set(null);
    status.valueProperty().set(null);
  }
}

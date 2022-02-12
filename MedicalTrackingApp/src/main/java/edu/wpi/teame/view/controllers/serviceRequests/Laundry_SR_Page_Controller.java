package edu.wpi.teame.view.controllers.serviceRequests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;

public class Laundry_SR_Page_Controller extends ServiceRequestController {
  @FXML public JFXButton send;
  @FXML public JFXButton clear;
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

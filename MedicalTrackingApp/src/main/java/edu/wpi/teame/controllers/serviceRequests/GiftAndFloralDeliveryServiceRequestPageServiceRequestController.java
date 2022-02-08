package edu.wpi.teame.controllers.serviceRequests;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class GiftAndFloralDeliveryServiceRequestPageServiceRequestController
    extends ServiceRequestController {
  @FXML private TextField patientNameTextBox;
  @FXML private TextField roomTextBox;
  @FXML private TextField floorTextBox;
  @FXML private CheckBox scheduleCheckbox;
  @FXML private CheckBox fragileCheckbox;
  // @FXML private Button sendButton; Looks like you don't actually need this because the button can
  // call a method by itself (?)
  @FXML private AnchorPane scheduleAnchorPane;
  @FXML private TextField deliveryTimeTextbox;

  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  @FXML
  private void ScheduleCallBack() {
    scheduleAnchorPane.setVisible(!scheduleAnchorPane.isVisible());
  }

  @FXML
  private void clearText() {
    patientNameTextBox.setText("");
    roomTextBox.setText("");
    floorTextBox.setText("");
    scheduleCheckbox.setSelected(false);
    fragileCheckbox.setSelected(false);
  }
}
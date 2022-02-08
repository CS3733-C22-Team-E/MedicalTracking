package edu.wpi.teame.view.controllers.serviceRequests;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class GiftAndFloralDeliveryServiceRequestPageServiceRequestController
    extends ServiceRequestController {
  @FXML private TextField patientNameTextBox;
  @FXML private TextField roomTextBox;
  @FXML private TextField floorTextBox;
  @FXML private CheckBox scheduleCheckbox;
  // @FXML private Button sendButton; Looks like you don't actually need this because the button can
  // call a method by itself (?)
  @FXML private AnchorPane scheduleAnchorPane;
  @FXML private TextField deliveryTimeTextbox;
  @FXML private DatePicker deliveryDateDatebox;

  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  @FXML
  private void ScheduleCallBack() {
    scheduleAnchorPane.setVisible(!scheduleAnchorPane.isVisible());
  }
}

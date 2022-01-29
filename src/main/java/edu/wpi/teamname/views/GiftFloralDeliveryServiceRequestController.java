package edu.wpi.teamname.views;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class GiftFloralDeliveryServiceRequestController extends ServiceRequest {
  @FXML private TextField patientNameTextBox;
  @FXML private TextField roomTextBox;
  @FXML private TextField floorTextBox;
  @FXML private CheckBox scheduleCheckbox;
  // @FXML private Button sendButto n; Looks like you don't actually need this because the button can
  // call a method by itself (?)
  @FXML private AnchorPane scheduleAnchorPane;
  @FXML private TextField deliveryTimeTextbox;
  @FXML private DatePicker deliveryDateDatebox;

  @FXML
  private void setup() {}

  @FXML
  private void ScheduleCallBack() {
    scheduleAnchorPane.setVisible(!scheduleAnchorPane.isVisible());
  }
}

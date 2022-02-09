package edu.wpi.teame.controllers.serviceRequests;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AudioVisualServiceRequestPageServiceRequestController
    extends ServiceRequestController {
  @FXML private TextField patientName;
  @FXML private TextField roomNumber;
  @FXML private TextField floor;

  @FXML DatePicker startDate;
  @FXML DatePicker endDate;

  @FXML ComboBox<String> selectStartTime;
  @FXML ComboBox<String> selectEndTime;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ComputerServiceRequestPageServiceRequestController.selectTime(selectStartTime, selectEndTime);
  }
}

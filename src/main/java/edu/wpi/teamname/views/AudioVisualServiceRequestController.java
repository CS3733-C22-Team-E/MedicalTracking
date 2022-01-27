package edu.wpi.teamname.views;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AudioVisualServiceRequestController extends ServiceRequest {
  @FXML private TextField patientName;
  @FXML private TextField roomNumber;
  @FXML private TextField floor;
  @FXML DatePicker startDate;
  @FXML DatePicker endDate;
  @FXML TextField startTime;
  @FXML TextField endTime;

  @FXML
  private void setup() {}
}

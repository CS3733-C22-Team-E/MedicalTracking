package edu.wpi.teamname.views;

import edu.wpi.teamname.App;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AudioVisualServiceRequestController {
  @FXML private TextField patientName;
  @FXML private TextField roomNumber;
  @FXML private TextField floor;
  @FXML DatePicker startDate;
  @FXML DatePicker endDate;
  @FXML TextField startTime;
  @FXML TextField endTime;

  @FXML
  private void setup() {}

  @FXML
  private void sendServiceRequest() throws IOException {
    App.backToLandingPage();
  }
}

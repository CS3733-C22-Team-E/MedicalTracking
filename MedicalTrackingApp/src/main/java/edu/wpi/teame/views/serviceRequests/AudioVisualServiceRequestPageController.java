package edu.wpi.teame.views.serviceRequests;

import edu.wpi.teame.views.Controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AudioVisualServiceRequestPageController extends Controller {
  @FXML private TextField patientName;
  @FXML private TextField roomNumber;
  @FXML private TextField floor;
  @FXML DatePicker startDate;
  @FXML DatePicker endDate;
  @FXML TextField startTime;
  @FXML TextField endTime;

  @Override
  public void initialize(URL location, ResourceBundle resources) {}
}

package edu.wpi.teame.controllers.serviceRequests;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class LaundryServiceRequestPageServiceRequestController extends ServiceRequestController {
  @FXML public JFXButton sendButton;
  @FXML private TextField patientName;
  @FXML private TextField roomNumber;
  @FXML private TextField floor;
  @FXML private TextField time;
  @FXML private DatePicker startDate;
  @FXML private DatePicker endDate;

  public void initialize(URL location, ResourceBundle resources) {}

  @FXML
  private void clearText() {
    patientName.setText("");
    roomNumber.setText("");
    floor.setText("");
    time.setText("");
    startDate.setValue(null);
    startDate.getEditor().clear();
    endDate.setValue(null);
    endDate.getEditor().clear();
  }
}

package edu.wpi.teame.view.controllers.serviceRequests;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class SanitationServiceRequestPageServiceRequestController extends ServiceRequestController {
  @FXML public JFXButton sendButton;
  @FXML public JFXButton clearButton;
  @FXML private TextField timeNeeded;
  @FXML private TextField roomNumber;
  @FXML private TextField floor;
  @FXML private DatePicker startDate;
  @FXML private DatePicker endDate;

  public void initialize(URL location, ResourceBundle resources) {}

  @FXML
  private void clearText() {
    timeNeeded.setText("");
    roomNumber.setText("");
    floor.setText("");
    startDate.setValue(null);
    startDate.getEditor().clear();
    endDate.setValue(null);
    endDate.getEditor().clear();
  }
}

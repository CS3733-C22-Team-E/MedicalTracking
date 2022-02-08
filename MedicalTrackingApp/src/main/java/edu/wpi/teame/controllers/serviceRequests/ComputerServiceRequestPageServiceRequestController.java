package edu.wpi.teame.controllers.serviceRequests;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ComputerServiceRequestPageServiceRequestController extends ServiceRequestController {

  @FXML public Button sendButton;
  @FXML private Text nameText;
  @FXML private Text roomText;
  @FXML private Text floorText;
  @FXML private Text dateText;
  @FXML private Text startText;
  @FXML private Text endText;
  @FXML private Text remarksText;
  @FXML private TextField patientName;
  @FXML private TextField roomNumber;
  @FXML private TextField floorNumber;
  @FXML private TextField date;
  @FXML private TextField startTime;
  @FXML private TextField endTime;
  @FXML private TextField remarks;
  @FXML private DatePicker datePicker;

  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  @FXML
  private void clearText() {
    patientName.setText("");
    roomNumber.setText("");
    floorNumber.setText("");
    startTime.setText("");
    endTime.setText("");
    remarks.setText("");
    datePicker.setValue(null);
    datePicker.getEditor().clear();
  }
}

package edu.wpi.teame.view.controllers.serviceRequests;

import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NewLaundryServiceRequestPageServiceRequestController extends ServiceRequestController {
  @FXML public Button send;
  @FXML public Button clear;
  @FXML private DatePicker requestDate;
  @FXML private TextField location;
  @FXML private TextField assignee;
  @FXML private JFXComboBox<String> priority;
  @FXML private JFXComboBox<String> status;
  @FXML private TextArea additionalInfo;

  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  @FXML
  private void clearText() {
    requestDate.setValue(null);
    requestDate.getEditor().clear();
    location.setText("");
    assignee.setText("");
    priority.valueProperty().set(null);
    status.valueProperty().set(null);
    additionalInfo.setText("");
  }
}

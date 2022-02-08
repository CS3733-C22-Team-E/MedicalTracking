package edu.wpi.teame.controllers.serviceRequests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class SecurityServiceRequestPageServiceRequestController extends ServiceRequestController {
  @FXML private TextField startTime;
  @FXML private TextField endTime;

  @FXML private JFXButton clearButton;
  @FXML private JFXButton sendButton;

  @FXML private JFXComboBox serviceLocation;
  @FXML private JFXComboBox serviceAssignee;

  @FXML private DatePicker datePicker;

  @FXML private JFXCheckBox completed;

  @FXML
  public void initialize(URL location, ResourceBundle resources) {}

  @FXML
  void clearText() {
    startTime.setText("");
    endTime.setText("");
    serviceLocation.valueProperty().set(null);
    serviceAssignee.valueProperty().set(null);
    datePicker.setValue(null);
    datePicker.getEditor().clear();
    completed.setSelected(false);
  }
}

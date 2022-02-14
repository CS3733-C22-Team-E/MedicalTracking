package edu.wpi.teame.view.controllers.serviceRequests;

import com.jfoenix.controls.JFXButton;
import edu.wpi.teame.view.controllers.AutoCompleteTextField;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AudioVisualServiceRequestPageServiceRequestController
    extends ServiceRequestController {
  @FXML private JFXButton clearButton;
  @FXML private JFXButton sendButton;
  @FXML private AutoCompleteTextField patientName;
  @FXML private AutoCompleteTextField roomNumber;
  @FXML private TextField floor;
  @FXML DatePicker startDate;
  @FXML DatePicker endDate;
  @FXML TextField startTime;
  @FXML TextField endTime;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    patientName.getEntries().addAll(Arrays.asList("Madison", "Kalina", "Samay"));
  }

  @FXML
  private void clearText() {
    patientName.setText("");
    roomNumber.setText("");
    floor.setText("");
    startTime.setText("");
    endTime.setText("");
    startDate.setValue(null);
    startDate.getEditor().clear();
    endDate.setValue(null);
    endDate.getEditor().clear();
  }
}

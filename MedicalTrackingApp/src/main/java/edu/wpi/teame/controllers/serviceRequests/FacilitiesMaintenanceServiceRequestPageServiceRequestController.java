package edu.wpi.teame.controllers.serviceRequests;

import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class FacilitiesMaintenanceServiceRequestPageServiceRequestController
    extends ServiceRequestController {
  @FXML public Button sendButton;

  @FXML private Text facilitiesMaintenanceServiceRequestText;
  @FXML private Text nameText;
  @FXML private Text roomNumText;
  @FXML private Text floorText;
  @FXML private Text dateText;
  @FXML private Text issueDescriptionText;
  @FXML private Text chooseIssueText;

  @FXML private TextField reporterName;
  @FXML private TextField roomNum;
  @FXML private TextField floorNum;
  @FXML private TextField issueDescription;

  @FXML private DatePicker reportDate;

  @FXML private JFXComboBox<String> chooseIssue;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    chooseIssue.setItems(FXCollections.observableArrayList("Elevator", "Power", "Other"));
  }
}

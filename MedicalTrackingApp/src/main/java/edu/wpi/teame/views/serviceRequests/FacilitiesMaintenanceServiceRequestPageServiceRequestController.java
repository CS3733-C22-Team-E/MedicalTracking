package edu.wpi.teame.views.serviceRequests;

import java.net.URL;
import java.util.ResourceBundle;
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
  @FXML private Text facilityNameText;
  @FXML private Text floorText;
  @FXML private Text dateText;
  @FXML private Text issueDescriptionText;

  @FXML private TextField reporterName;
  @FXML private TextField facilityName;
  @FXML private TextField floorNum;
  @FXML private TextField issueDescription;

  @FXML private DatePicker reportDate;

  @Override
  public void initialize(URL location, ResourceBundle resources) {}
}

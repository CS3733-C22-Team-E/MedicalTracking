package edu.wpi.teame.views.serviceRequests;

import edu.wpi.teame.views.Controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ComputerServiceRequestPageController extends Controller {
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

  @Override
  public void initialize(URL location, ResourceBundle resources) {}
}

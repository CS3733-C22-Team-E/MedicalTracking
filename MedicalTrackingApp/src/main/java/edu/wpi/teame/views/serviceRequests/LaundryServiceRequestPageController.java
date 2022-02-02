package edu.wpi.teame.views.serviceRequests;

import edu.wpi.teame.views.Controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LaundryServiceRequestPageController extends Controller {
  @FXML public Button sendButton;
  @FXML private TextField patientName;
  @FXML private TextField roomNumber;
  @FXML private TextField floor;

  public void initialize(URL location, ResourceBundle resources) {}
}

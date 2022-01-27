package edu.wpi.teamname.views;

import edu.wpi.teamname.App;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LaundryServiceRequestController {
  @FXML public Button sendButton;
  @FXML private TextField patientName;
  @FXML private TextField roomNumber;
  @FXML private TextField floor;

  @FXML
  private void sendServiceRequest() throws IOException {
    App.backToLandingPage();
  }
}

package edu.wpi.teamname.views;

import edu.wpi.teamname.App;
import java.io.IOException;
import javafx.fxml.FXML;

public class ServiceRequest {

  @FXML
  public void sendServiceRequest() throws IOException {
    App.backToLandingPage();
  }
}

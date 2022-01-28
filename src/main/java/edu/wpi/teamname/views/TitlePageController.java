package edu.wpi.teamname.views;

import edu.wpi.teamname.App;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class TitlePageController {

  @FXML
  private void serviceRequestCallback() throws IOException {
    Parent pane = FXMLLoader.load(App.class.getResource("Views/ServiceRequestLandingPage.fxml"));
    App.changeScene(pane);
  }

  @FXML
  private void mapViewCallback() throws IOException {
    Parent pane = FXMLLoader.load(App.class.getResource("Views/MapViewPage.FXML"));
    App.changeScene(pane);
  }

  @FXML
  private void gitCallback() throws URISyntaxException, IOException {
    Desktop.getDesktop().browse(new URI("https://github.com/CS3733-C22-Team-E/MedicalTracking"));
  }
}

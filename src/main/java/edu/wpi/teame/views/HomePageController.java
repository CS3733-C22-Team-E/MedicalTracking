package edu.wpi.teame.views;

import edu.wpi.teame.App;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class HomePageController {

  @FXML
  private void serviceRequestCallback() throws IOException {
    Parent pane =
        FXMLLoader.load(Objects.requireNonNull(App.class.getResource("Views/LandingPage.fxml")));
    App.changeScene(pane);
  }

  @FXML
  private void mapViewCallback() throws IOException {
    Parent pane = FXMLLoader.load(App.class.getResource("Views/MapPage.fxml"));
    App.changeScene(pane);
  }

  @FXML
  private void gitCallback() throws URISyntaxException, IOException {
    Desktop.getDesktop().browse(new URI("https://github.com/CS3733-C22-Team-E/MedicalTracking"));
  }
}

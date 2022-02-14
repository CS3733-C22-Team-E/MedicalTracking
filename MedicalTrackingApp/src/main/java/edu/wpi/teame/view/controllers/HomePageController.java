package edu.wpi.teame.view.controllers;

// import com.opencsv.exceptions.CsvValidationException;

import edu.wpi.teame.App;
import edu.wpi.teame.db.objectManagers.*;
import java.awt.*;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class HomePageController {
  @FXML
  private void logout() throws IOException {
    Scene scene = new Scene(FXMLLoader.load(App.class.getResource("view/LoginPage.fxml")));
    App.getAppPrimaryStage().setScene(scene);
    App.getAppPrimaryStage().setFullScreen(true);
  }
}

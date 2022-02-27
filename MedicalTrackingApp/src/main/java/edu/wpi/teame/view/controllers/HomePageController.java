package edu.wpi.teame.view.controllers;

// import com.opencsv.exceptions.CsvValidationException;

import edu.wpi.teame.App;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.objectManagers.CredentialManager;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class HomePageController {
  @FXML private Label welcomeLabel;
  @FXML private VBox welcomeBox;

  public void showWelcomeMessage() {
    FadeTransition fadeTransition = new FadeTransition(new Duration(2500), welcomeBox);
    fadeTransition.setInterpolator(Interpolator.LINEAR);
    fadeTransition.setDelay(Duration.millis(1000));
    fadeTransition.setFromValue(1);
    fadeTransition.setToValue(0);
    fadeTransition.play();
  }

  public void setWelcomeMessage(String name) {
    welcomeBox.setVisible(true);
    welcomeLabel.setText("Welcome, " + name + "!");
  }

  @FXML
  private void logout() throws IOException, SQLException, NoSuchAlgorithmException {
    Scene scene = new Scene(FXMLLoader.load(App.class.getResource("view/LoginPage.fxml")));
    App.getAppPrimaryStage().setScene(scene);
    App.getAppPrimaryStage().setFullScreen(true);
    ((CredentialManager) DBManager.getInstance().getManager(DataBaseObjectType.Credential))
        .logOut();
  }

  @FXML
  private void openAboutPopup() throws IOException {
    Scene scene = new Scene(FXMLLoader.load(App.class.getResource("view/tabs/AboutPage.fxml")));
    App.getAppPrimaryStage().setScene(scene);
    App.getAppPrimaryStage().setFullScreen(true);
  }
}

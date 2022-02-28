package edu.wpi.teame.view.controllers;

import edu.wpi.teame.App;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.objectManagers.CredentialManager;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.view.style.IStyleable;
import edu.wpi.teame.view.style.StyleManager;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class HomePageController implements Initializable, IStyleable {
  @FXML private Label welcomeLabel;
  @FXML private VBox welcomeBox;
  @FXML private Label titleText;

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

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    StyleManager.getInstance().subscribe(this);
    welcomeBox.setVisible(false);
  }

  @Override
  public void updateStyle() {
    welcomeLabel.setTextFill(StyleManager.getInstance().getCurrentStyle().getForegroundColor());
    welcomeBox.setBackground(StyleManager.getInstance().getCurrentStyle().getBackground());
    titleText.setBackground(StyleManager.getInstance().getCurrentStyle().getBackground());
    titleText.setTextFill(StyleManager.getInstance().getCurrentStyle().getTextColor());
  }
}

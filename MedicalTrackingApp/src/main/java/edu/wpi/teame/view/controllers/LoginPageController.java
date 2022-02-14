package edu.wpi.teame.view.controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.teame.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class LoginPageController implements Initializable {

  @FXML private JFXButton loginButton;
  @FXML private TextField usernameTextInput;
  @FXML private TextField passwordTextInput;
  @FXML private Text usernameText;
  @FXML private Text passwordText;
  @FXML private Line usernameFillLine;
  @FXML private Line passwordFillLine;
  @FXML private ImageView usernameImage;
  @FXML private ImageView passwordImage;
  private Parent logInPage = null;

  @FXML
  private void loginButtonPressed() {
    Scene landingPage = new Scene(logInPage);
    App.getAppPrimaryStage().setScene(landingPage);
    App.getAppPrimaryStage().show();
    App.getAppPrimaryStage().setFullScreen(true);
  }

  @FXML
  private void updateUsernameText() {
    usernameText.setText(usernameTextInput.getText());
    enableLoginButton();
    checkFocus();
  }

  private void animateUsername(boolean activate) {
    displayTextLine(usernameFillLine, activate);
    moveTextImage(usernameImage, activate);
  }

  @FXML
  private void usernameAction() {}

  @FXML
  private void usernameTextSelected() {
    checkFocus();
  }

  @FXML
  private void usernameMouseEnter() {
    checkFocus();
  }

  @FXML
  private void usernameMouseExit() {
    if (usernameText.getText().isEmpty()) {
      animateUsername(false);
    }
    checkFocus();
  }

  @FXML
  private void updatePasswordText() {
    passwordText.setText(passwordTextInput.getText());
    enableLoginButton();
    checkFocus();
  }

  private void animatePassword(boolean activate) {
    displayTextLine(passwordFillLine, activate);
    moveTextImage(passwordImage, activate);
  }

  @FXML
  private void passwordAction() {
    displayTextLine(passwordFillLine, true);
  }

  @FXML
  private void passwordTextSelected() {
    checkFocus();
  }

  @FXML
  private void passwordMouseEnter() {
    checkFocus();
  }

  @FXML
  private void passwordMouseExit() {
    if (passwordText.getText().isEmpty()) {
      animatePassword(false);
    }
    checkFocus();
  }

  private void displayTextLine(Line l, boolean display) {
    ScaleTransition t = new ScaleTransition(new Duration(300), l);
    t.setInterpolator(Interpolator.EASE_OUT);
    if (display) {
      if (l.getScaleX() > 0) {
        return;
      }
      t.setFromX(0);
      t.setToX(1);
    } else {
      if (l.getScaleX() < 1) {
        return;
      }
      t.setFromX(1);
      t.setToX(0);
    }
    t.play();
  }

  private void moveTextImage(ImageView i, boolean dislocate) {
    TranslateTransition t = new TranslateTransition(new Duration(200), i);
    if (dislocate) {
      if (i.getTranslateY() < -5) {
        return;
      }
      t.setFromY(-5);
      t.setToY(-35);
    } else {
      if (i.getTranslateY() > -35) {
        return;
      }
      t.setFromY(-35);
      t.setToY(-5);
    }
    t.play();
  }

  private void moveText(Text t, boolean dislocate) {
    if (dislocate) {
      t.setTranslateX(-35);
    } else {
      t.setTranslateX(0);
    }
  }

  private void enableLoginButton() {
    loginButton.setDisable(usernameText.getText().equals("") || passwordText.getText().equals(""));
  }

  @FXML
  private void checkFocus() {
    if (!usernameTextInput.focusedProperty().getValue()) {
      animateUsername(!usernameText.getText().isEmpty());
    } else {
      animateUsername(true);
    }
    if (!passwordTextInput.focusedProperty().getValue()) {
      animatePassword(!passwordText.getText().isEmpty());
    } else {
      animatePassword(true);
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try {
      logInPage = FXMLLoader.load(App.class.getResource("view/LandingPage.fxml"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

package edu.wpi.teame.view.controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.teame.App;
import java.io.IOException;
import java.util.Objects;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class LoginPageController {

  @FXML private JFXButton loginButton;
  @FXML private TextField usernameTextInput;
  @FXML private TextField passwordTextInput;
  @FXML private Text usernameText;
  @FXML private Text passwordText;
  @FXML private Text usernamePromptText;
  @FXML private Text passwordPromptText;
  @FXML private Line usernameFillLine;
  @FXML private Line passwordFillLine;
  @FXML private ImageView usernameImage;
  @FXML private ImageView passwordImage;

  @FXML
  private void loginButtonPressed() throws IOException {
    Scene landingPage =
        new Scene(
            FXMLLoader.load(
                Objects.requireNonNull(getClass().getResource("view/LandingPage.fxml"))));
    App.getAppPrimaryStage().setScene(landingPage);
  }

  @FXML
  private void updateUsernameText() {
    usernameText.setText(usernameTextInput.getText());
    boolean hasText = usernameText.getText().equals("");
    usernamePromptText.setVisible(hasText);
    displayTextLine(usernameFillLine, !hasText);
    moveTextImage(usernameImage, !hasText);
    moveText(usernameText, !hasText);
    enableLoginButton();
  }

  @FXML
  private void usernameTextSelected() {}

  @FXML
  private void usernameMouseEnter() {
    // displayTextLine(usernameFillLine, true);
  }

  @FXML
  private void usernameMouseExit() {
    // displayTextLine(usernameFillLine, false);
  }

  @FXML
  private void updatePasswordText() {
    passwordText.setText(passwordTextInput.getText());
    boolean hasText = passwordText.getText().equals("");
    passwordPromptText.setVisible(hasText);
    displayTextLine(passwordFillLine, !hasText);
    moveTextImage(passwordImage, !hasText);
    moveText(passwordText, !hasText);
    enableLoginButton();
  }

  @FXML
  private void passwordTextSelected() {}

  @FXML
  private void passwordMouseEnter() {
    // displayTextLine(passwordFillLine, true);
  }

  @FXML
  private void passwordMouseExit() {
    // displayTextLine(passwordFillLine, false);
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
}

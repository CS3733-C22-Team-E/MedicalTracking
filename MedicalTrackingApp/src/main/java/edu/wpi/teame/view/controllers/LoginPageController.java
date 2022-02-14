package edu.wpi.teame.view.controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.teame.App;
import java.io.IOException;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

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
    displayTextLine(usernameFillLine, true);
  }

  @FXML
  private void usernameMouseExit() {
    displayTextLine(usernameFillLine, false);
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
    displayTextLine(passwordFillLine, true);
  }

  @FXML
  private void passwordMouseExit() {
    displayTextLine(passwordFillLine, false);
  }

  private void displayTextLine(Line l, boolean display) {
    if (display) {
      l.setScaleX(1);
    } else {
      l.setScaleX(0);
    }
  }

  private void moveTextImage(ImageView i, boolean dislocate) {
    if (dislocate) {
      i.setTranslateY(-35);
    } else {
      i.setTranslateY(-5);
    }
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

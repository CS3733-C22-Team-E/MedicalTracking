package edu.wpi.teame.view.controllers;

import static javafx.animation.Interpolator.EASE_OUT;

import com.jfoenix.controls.JFXButton;
import edu.wpi.teame.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
  @FXML private ImageView icon;
  @FXML private ImageView iconHole;
  @FXML private Text title;
  private Scene landingPage = null;

  @FXML
  private void loginButtonPressed() {

    Media sound = new Media(App.class.getResource("audio/Shoop.m4a").toString());
    MediaPlayer mediaPlayer = new MediaPlayer(sound);
    mediaPlayer.setVolume(1.0);

    TranslateTransition t2 = new TranslateTransition(new Duration(100), icon);
    t2.setFromY(-50);
    t2.setToY(0);
    t2.setOnFinished(
        e -> {
          switchToLandingPage();
        });

    RotateTransition r = new RotateTransition(new Duration(300), icon);
    r.setFromAngle(0);
    r.setToAngle(360);
    r.setInterpolator(EASE_OUT);
    r.setOnFinished(
        e -> {
          t2.play();
        });

    TranslateTransition t1 = new TranslateTransition(new Duration(200), icon);
    t1.setInterpolator(EASE_OUT);
    t1.setFromY(0);
    t1.setToY(-50);
    t1.setOnFinished(e -> {});
    t1.play();
    r.play();
    mediaPlayer.play();
  }

  private void switchToLandingPage() {
    App.getAppPrimaryStage().setScene(landingPage);
    App.getAppPrimaryStage().show();
    App.getAppPrimaryStage().setFullScreen(true);
  }

  @FXML
  private void updateUsernameText() {
    String t = usernameTextInput.getText();
    int numChars = usernameTextInput.getText().length();
    if (numChars > 30) {
      usernameText.setText(t.substring(0, 30));
    } else {
      usernameText.setText(t);
    }
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
    checkFocus();
  }

  @FXML
  private void updatePasswordText() {
    String p = "";
    int numStars = passwordTextInput.getText().length();
    if (numStars > 30) {
      numStars = 30;
    }
    for (int i = 0; i < numStars; i++) {
      p += "*";
    }
    passwordText.setText(p);
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
    checkFocus();
  }

  private void displayTextLine(Line l, boolean display) {
    ScaleTransition t = new ScaleTransition(new Duration(300), l);
    t.setInterpolator(EASE_OUT);
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

  private void enableLoginButton() {
    loginButton.setDisable(usernameText.getText().equals("") || passwordText.getText().equals(""));
  }

  @FXML
  private void checkFocus() {
    if (usernameTextInput.focusedProperty().getValue()) {
      animateUsername(true);
    } else animateUsername(!usernameText.getText().isEmpty());
    if (passwordTextInput.focusedProperty().getValue()) {
      animatePassword(true);
    } else animatePassword(!passwordText.getText().isEmpty());
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try {
      landingPage = new Scene(FXMLLoader.load(App.class.getResource("view/LandingPage.fxml")));
    } catch (IOException e) {
      e.printStackTrace();
    }
    usernameImage.setOnMousePressed(e -> checkFocus());
    passwordImage.setOnMousePressed(e -> checkFocus());

    usernameTextInput.setOnMousePressed(e -> checkFocus());
    passwordTextInput.setOnMousePressed(e -> checkFocus());

    passwordText.setScaleX(1.5);
    passwordText.setScaleY(1.5);
  }

  @FXML
  public void closeApp() {
    Dialog dialog = new Dialog();
    dialog.setTitle("Are you sure you want to quit?");
    ImageView newIcon =
        new ImageView(new Image(App.class.getResource("images/icons/ExitIcon.png").toString()));
    newIcon.setFitHeight(30);
    newIcon.setFitWidth(30);
    dialog.getDialogPane().setGraphic(newIcon);
    ButtonType OK = new ButtonType("Exit", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(OK, ButtonType.CANCEL);
    dialog.setResultConverter(
        dialogButton -> {
          if (dialogButton == OK) {
            App.getAppPrimaryStage().close();
          }
          return null;
        });
    dialog.showAndWait();
  }
}

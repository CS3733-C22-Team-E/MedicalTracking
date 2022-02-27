package edu.wpi.teame.view.controllers;

import static javafx.animation.Interpolator.EASE_OUT;

import com.github.sarxos.webcam.Webcam;
import com.jfoenix.controls.JFXButton;
import edu.wpi.teame.App;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.objectManagers.CredentialManager;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.effect.*;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import javax.swing.*;

public class LoginPageController implements Initializable {

  @FXML private JFXButton loginButton;
  @FXML private TextField usernameTextInput;
  @FXML private TextField passwordTextInput;
  @FXML private Text usernameText;
  @FXML private Text passwordText;
  @FXML private Line usernameFillLine;
  @FXML private Line passwordFillLine;
  @FXML private Line usernameBackgroundLine;
  @FXML private Line passwordBackgroundLine;
  @FXML private ImageView usernameImage;
  @FXML private ImageView passwordImage;
  @FXML private ImageView icon;
  @FXML private ImageView iconHole;
  @FXML private Text title;
  @FXML private GridPane mainGridPane;

  // Face ID Stack Panes
  @FXML private StackPane imageViewStackPane;
  @FXML private ImageView cameraImageView;
  @FXML private VBox credentialLogInVbox;
  @FXML private VBox faceIDVbox;

  // Face ID Requirements
  private boolean useFaceID = false;
  private Webcam webcam = null;

  private Scene landingPage = null;
  private Media loginSound = null;

  @FXML
  private void loginButtonPressed() throws SQLException, NoSuchAlgorithmException, IOException {
    boolean loggedIn = false;
    if (!useFaceID) {
      String username = usernameTextInput.getText();
      String password = passwordTextInput.getText();
      loggedIn =
          ((CredentialManager) DBManager.getInstance().getManager(DataBaseObjectType.Credential))
              .logIn(username, password);
    } else {
      // get image
      BufferedImage image = webcam.getImage();

      // save image to PNG file
      File imageFile =
          new File(
              App.class
                  .getClassLoader()
                  .getResource("edu/wpi/teame/images/facial-recognition/userLogInImage.png")
                  .getFile());
      ImageIO.write(image, "PNG", imageFile);
      cameraImageView.setImage(new Image(imageFile.getAbsolutePath()));
    }

    // Check if we were able to log in.
    if (!loggedIn) {
      loginFailedAnimation();
      return;
    }

    // Load the sound
    MediaPlayer mediaPlayer = new MediaPlayer(loginSound);
    mediaPlayer.setVolume(0.3);

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

    mediaPlayer.play();
    t1.play();
    r.play();
    webcam.close();
  }

  @FXML
  private void switchToFaceID() {
    useFaceID = !useFaceID;
    faceIDVbox.setVisible(useFaceID);
    credentialLogInVbox.setVisible(!useFaceID);

    // Set Image View
    cameraImageView.setFitHeight(imageViewStackPane.getHeight() / 1.5);
  }

  private void loginFailedAnimation() {
    RotateTransition r3 = new RotateTransition(new Duration(50), icon);
    r3.setFromAngle(15);
    r3.setToAngle(0);
    r3.setOnFinished(e -> icon.setEffect(new DropShadow(0, 0, 0, Color.TRANSPARENT)));
    RotateTransition r2 = new RotateTransition(new Duration(100), icon);
    r2.setFromAngle(-15);
    r2.setToAngle(15);
    r2.setOnFinished(r -> r3.play());
    RotateTransition r1 = new RotateTransition(new Duration(50), icon);
    r1.setFromAngle(0);
    r1.setToAngle(-15);
    r1.setOnFinished(e -> r2.play());
    r1.play();
    icon.setEffect(new DropShadow(30, 0, 0, Color.RED));
  }

  private void switchToLandingPage() {
    System.out.println("Switching to landing page...");
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
      landingPage =
          new Scene(
              FXMLLoader.load(
                  Objects.requireNonNull(App.class.getResource("view/LandingPage.fxml"))));
    } catch (IOException e) {
      e.printStackTrace();
    }

    applyStyle();

    // Set up face id
    //    webcam = Webcam.getDefault();
    //    Dimension[] supportedSizes = webcam.getViewSizes();
    //    webcam.setViewSize(supportedSizes[supportedSizes.length - 1]);
    //    webcam.open();

    // Set up window

    faceIDVbox.setVisible(false);

    loginSound = new Media(App.class.getResource("audio/Shoop.mp3").toString());
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
        new ImageView(new Image(App.class.getResource("images/Icons/ExitIcon.png").toString()));
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
    webcam.close();
  }

  private void applyStyle() {
    Background colorBG =
        new Background(
            new BackgroundFill(App.getColorScheme().getColor1(), CornerRadii.EMPTY, Insets.EMPTY));
    credentialLogInVbox.setBackground(colorBG);
    usernameFillLine.setStroke(App.getColorScheme().getColor2());
    passwordFillLine.setStroke(App.getColorScheme().getColor2());
    usernameBackgroundLine.setStroke(App.getColorScheme().getColor2());
    passwordBackgroundLine.setStroke(App.getColorScheme().getColor2());
    // TODO overlay images or just reload with different ones if we have to
  }
}

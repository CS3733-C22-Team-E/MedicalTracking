package edu.wpi.teame.view.controllers;

import static javafx.animation.Interpolator.EASE_OUT;

import com.github.sarxos.webcam.Webcam;
import com.jfoenix.controls.JFXButton;
import com.microsoft.azure.storage.StorageException;
import edu.wpi.teame.App;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.objectManagers.CredentialManager;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.view.style.IStyleable;
import edu.wpi.teame.view.style.StyleManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.sql.SQLException;
import java.util.*;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
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
import lombok.SneakyThrows;
import org.apache.hc.core5.http.ParseException;

public class LoginPageController implements Initializable, IStyleable {
  @FXML private JFXButton loginButton;
  @FXML public JFXButton loginButton2;
  @FXML private TextField usernameTextInput;
  @FXML private TextField passwordTextInput;
  @FXML private Label switchToFaceIdButton;
  @FXML private Label errorMessageLabel;
  @FXML private VBox errorMessageVbox;
  @FXML private Text usernameText;
  @FXML private Text passwordText;
  @FXML private Line usernameFillLine;
  @FXML private Line passwordFillLine;
  @FXML private Line usernameBackgroundLine;
  @FXML private Line passwordBackgroundLine;
  @FXML private ImageView usernameImage;
  @FXML private ImageView passwordImage;
  @FXML private ImageView icon;
  @FXML private ImageView icon1;
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
  private String username = null;
  private String password = null;
  private File imageFile = null;
  private Webcam webcam = null;

  private LandingPageController Tabber;
  private Scene landingPage = null;
  private Media loginSound = null;
  private ImageView currentIcon;

  @FXML
  private void loginButtonPressed() throws IOException {
    username = null;
    password = null;
    imageFile = null;

    // Get info from user screen
    if (!useFaceID) {
      username = usernameTextInput.getText();
      password = passwordTextInput.getText();
    } else {
      imageFile = getImageFromWebcam();
      cameraImageView.setImage(new Image(imageFile.getAbsolutePath()));
    }

    // Run log in async
    Platform.runLater(
        () -> {
          try {
            processLogIn(username, password, imageFile);
          } catch (Exception ex) {
            ex.printStackTrace();
          }
        });
  }

  private void processLogIn(String username, String password, File imageFile)
      throws SQLException, IOException, URISyntaxException, InvalidKeyException, StorageException,
          ParseException {
    CredentialManager credentialManager =
        ((CredentialManager) DBManager.getInstance().getManager(DataBaseObjectType.Credential));

    // Run log in
    boolean loggedIn = false;
    if (useFaceID && imageFile != null) {
      String imageURL = credentialManager.uploadImage(imageFile, false);
      loggedIn = credentialManager.logIn(imageURL);
    } else if (!username.isEmpty() && !password.isEmpty()) {
      loggedIn = credentialManager.logIn(username, password);
    }

    // Check for bad faceId log in with mask on
    boolean isMaskOn =
        credentialManager.getLastScannedFace() == null
            || credentialManager.getLastScannedFace().isMouthOccluded();
    if (useFaceID && !loggedIn && isMaskOn) {
      errorMessageLabel.setText("Please remove your mask for better results...");
      errorMessageVbox.setVisible(true);

      FadeTransition fadeTransition = new FadeTransition(new Duration(2500), errorMessageVbox);
      fadeTransition.setInterpolator(Interpolator.DISCRETE);
      fadeTransition.setFromValue(1);
      fadeTransition.setToValue(0);
      fadeTransition.play();
    }

    // Check if we were able to log in.
    if (!loggedIn) {
      loginFailedAnimation();
      return;
    }

    // Set welcome message
    ((HomePageController) Tabber.homeTabPage.controller)
        .setWelcomeMessage("@" + credentialManager.getCurrentUser().getUsername());

    // Load the sound
    MediaPlayer mediaPlayer = new MediaPlayer(loginSound);
    mediaPlayer.setVolume(0.3);

    TranslateTransition t2 = new TranslateTransition(new Duration(100), currentIcon);
    t2.setFromY(-50);
    t2.setToY(0);
    t2.setOnFinished(
        e -> {
          switchToLandingPage();
        });

    RotateTransition r = new RotateTransition(new Duration(300), currentIcon);
    r.setFromAngle(0);
    r.setToAngle(360);
    r.setInterpolator(EASE_OUT);
    r.setOnFinished(
        e -> {
          t2.play();
        });

    TranslateTransition t1 = new TranslateTransition(new Duration(200), currentIcon);
    t1.setInterpolator(EASE_OUT);
    t1.setFromY(0);
    t1.setToY(-50);
    t1.setOnFinished(e -> {});

    mediaPlayer.play();
    t1.play();
    r.play();
  }

  @FXML
  private void switchToFaceID() {
    useFaceID = !useFaceID;
    faceIDVbox.setVisible(useFaceID);
    credentialLogInVbox.setVisible(!useFaceID);

    // Set Image View
    cameraImageView.setFitHeight(imageViewStackPane.getHeight());
    if (useFaceID) {
      currentIcon = icon1;
    } else {
      currentIcon = icon;
    }
  }

  private void loginFailedAnimation() {
    RotateTransition r3 = new RotateTransition(new Duration(50), currentIcon);
    r3.setFromAngle(15);
    r3.setToAngle(0);
    r3.setOnFinished(e -> currentIcon.setEffect(new DropShadow(0, 0, 0, Color.TRANSPARENT)));
    RotateTransition r2 = new RotateTransition(new Duration(100), currentIcon);
    r2.setFromAngle(-15);
    r2.setToAngle(15);
    r2.setOnFinished(r -> r3.play());
    RotateTransition r1 = new RotateTransition(new Duration(50), currentIcon);
    r1.setFromAngle(0);
    r1.setToAngle(-15);
    r1.setOnFinished(e -> r2.play());
    r1.play();
    currentIcon.setEffect(new DropShadow(30, 0, 0, Color.RED));
  }

  private void switchToLandingPage() {
    System.out.println("Switching to landing page...");
    App.getAppPrimaryStage().setScene(landingPage);
    App.getAppPrimaryStage().show();
    App.getAppPrimaryStage().setFullScreen(true);

    // Show welcome message
    ((HomePageController) Tabber.homeTabPage.controller).showWelcomeMessage();
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
  @SneakyThrows
  public void initialize(URL location, ResourceBundle resources) {
    FXMLLoader landingPageLoader = new FXMLLoader(App.class.getResource("view/LandingPage.fxml"));
    try {
      landingPage = new Scene(landingPageLoader.load());
      Tabber = landingPageLoader.getController();
    } catch (IOException e) {
      e.printStackTrace();
    }

    // Set up face id
    webcam = Webcam.getDefault();
    if (webcam != null) {
      Dimension[] supportedSizes = webcam.getViewSizes();
      webcam.setViewSize(supportedSizes[supportedSizes.length - 1]);
      faceIDVbox.setVisible(false);

      // Set up the FaceId stuff
      ((CredentialManager) DBManager.getInstance().getManager(DataBaseObjectType.Credential))
          .setupDBFaces();
    } else {
      // We do not have a webcam
      switchToFaceIdButton.setVisible(false);
    }

    loginSound = new Media(App.class.getResource("audio/Shoop.mp3").toString());
    usernameImage.setOnMousePressed(e -> checkFocus());
    passwordImage.setOnMousePressed(e -> checkFocus());

    usernameTextInput.setOnMousePressed(e -> checkFocus());
    passwordTextInput.setOnMousePressed(e -> checkFocus());

    errorMessageVbox.setVisible(false);
    passwordText.setScaleX(1.5);
    passwordText.setScaleY(1.5);

    // Set default to credentials
    useFaceID = true;
    switchToFaceID();

    StyleManager.getInstance().subscribe(this);
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
  }

  private File getImageFromWebcam() throws IOException {
    // Get image from webcam
    webcam.open();
    BufferedImage image = webcam.getImage();

    // Save image to PNG file
    String home = System.getProperty("user.home");
    File imageFile = new File(home + "/Downloads/MedicalTracking/images/userLogInImage.png");
    ImageIO.write(image, "PNG", imageFile);

    cameraImageView.setImage(new Image(imageFile.getAbsolutePath()));
    webcam.close();
    return imageFile;
  }

  @Override
  public void updateStyle() {
    StyleManager.getInstance().getCurrentStyle().setPaneStyle(credentialLogInVbox, true);
    StyleManager.getInstance().getCurrentStyle().setPaneStyle(faceIDVbox, true);
    StyleManager.getInstance().getCurrentStyle().setLineStyle(usernameBackgroundLine);
    StyleManager.getInstance().getCurrentStyle().setLineStyle(passwordBackgroundLine);
    StyleManager.getInstance().getCurrentStyle().setTextFieldStyle(usernameTextInput);
    StyleManager.getInstance().getCurrentStyle().setTextFieldStyle(passwordTextInput);
    StyleManager.getInstance().getCurrentStyle().setLineStyle(usernameFillLine);
    StyleManager.getInstance().getCurrentStyle().setLineStyle(passwordFillLine);

    StyleManager.getInstance().getCurrentStyle().setLabelStyle(switchToFaceIdButton);
    StyleManager.getInstance().getCurrentStyle().setButtonStyle(loginButton2);
    StyleManager.getInstance().getCurrentStyle().setButtonStyle(loginButton);
  }
}

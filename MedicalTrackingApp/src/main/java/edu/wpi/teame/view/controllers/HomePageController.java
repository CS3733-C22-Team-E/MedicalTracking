package edu.wpi.teame.view.controllers;

// import com.opencsv.exceptions.CsvValidationException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
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
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class HomePageController implements Initializable, IStyleable {
  @FXML private JFXButton creditsButton;
  @FXML private JFXButton aboutPageButton;
  @FXML private Button logOutButton;
  @FXML private Label welcomeLabel;
  @FXML private VBox welcomeBox;
  @FXML private Label titleText;
  @FXML private StackPane stackPane;
  @FXML private JFXDialog dialog;

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
    StyleManager.getInstance().getCurrentStyle().setPaneStyle(welcomeBox, true);
    StyleManager.getInstance().getCurrentStyle().setButtonStyle(aboutPageButton);
    StyleManager.getInstance().getCurrentStyle().setButtonStyle(creditsButton);
    StyleManager.getInstance().getCurrentStyle().setButtonStyle(logOutButton);
    StyleManager.getInstance().getCurrentStyle().setTitleStyle(titleText);

    welcomeLabel.setTextFill(StyleManager.getInstance().getCurrentStyle().getHighlightColor());
  }

  @FXML
  public void creditsPage(MouseEvent Event) {
    JFXDialogLayout credits = new JFXDialogLayout();
    Paint textColor = StyleManager.getInstance().getCurrentStyle().getParagraphColor();
    Text heading = new Text("API Credits <3");
    heading.setFill(textColor);
    credits.setHeading(heading);
    Text text =
        new Text(
            "CSV Read and Write Library\n"
                + "MSSQL Connection Library\n"
                + "Azure Cognitive Services\n"
                + "AzureSqlServer API\n"
                + "Azure Blob Storage API\n"
                + "Apache HTTP Client\n"
                + "Google/Azure Language API");
    text.setFont(Font.font("System", FontWeight.SEMI_BOLD, 20));
    text.setFill(textColor);
    credits.setBody(text);
    Paint color = StyleManager.getInstance().getCurrentStyle().getBackgroundColor();
    credits.setBackground(
        new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));

    dialog = new JFXDialog(stackPane, credits, JFXDialog.DialogTransition.CENTER);

    JFXButton backButton = new JFXButton("Back");
    StyleManager.getInstance().getCurrentStyle().setButtonStyle(backButton);

    backButton.setOnAction(event -> dialog.close());
    credits.setActions(backButton);
    dialog.show();
  }


}

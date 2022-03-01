package edu.wpi.teame.view.controllers;

// import com.opencsv.exceptions.CsvValidationException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import edu.wpi.teame.App;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.objectManagers.CredentialManager;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class HomePageController {

  @FXML StackPane stackPane;

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

  @FXML
  public void creditsPage(MouseEvent Event) {
    JFXDialogLayout info = new JFXDialogLayout();

    info.setHeading(new Text("About Jeremy"));
    Text text = new Text("");
    text.setFont(Font.font("System", FontWeight.BOLD, 25));
    info.setBody(text);

    dialog = new JFXDialog(stackPane, info, JFXDialog.DialogTransition.CENTER);

    JFXButton backButton = new JFXButton("Back");
    backButton.setOnAction(event -> dialog.close());
    info.setActions(backButton);
    dialog.show();
  }
}

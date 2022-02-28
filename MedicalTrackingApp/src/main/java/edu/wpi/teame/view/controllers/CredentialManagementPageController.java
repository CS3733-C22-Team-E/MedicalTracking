package edu.wpi.teame.view.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import edu.wpi.teame.App;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CredentialManagementPageController implements Initializable {
  private List<Object> itemsList = null;

  @FXML JFXListView resultView;
  @FXML JFXButton addCredential;
  @FXML JFXButton removeCredential;
  @FXML TextField usernameBox;
  @FXML TextField passwordBox;

  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  @FXML
  public void removeCredentialButtonClick() throws SQLException {}

  @FXML
  void handleButtonAction() {
    try {
      Scene scene =
          new Scene(
              FXMLLoader.load(App.class.getResource("view/tabs/AddCredentialPopupPage.fxml")));
      Stage stage = new Stage();
      stage.setTitle("Test");
      stage.setScene(scene);
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void addCredentialButtonClick() throws IOException {
    Scene scene =
        new Scene(FXMLLoader.load(App.class.getResource("view/tabs/AddCredentialPopupPage.fxml")));
    Stage stage = new Stage();
    stage.setTitle("Test");
    stage.setScene(scene);
    stage.initModality(Modality.WINDOW_MODAL);
    stage.initStyle(StageStyle.UNDECORATED);
    stage.show();
  }
}

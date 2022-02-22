package edu.wpi.teame.view.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import edu.wpi.teame.db.objectManagers.CredentialManager;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

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
  public void removeCredentialButtonClick() throws SQLException, NoSuchAlgorithmException {
    String username = usernameBox.getText().trim();
    CredentialManager.getInstance().remove(username);
  }

  @FXML
  public void addCredentialButtonClick() throws SQLException, NoSuchAlgorithmException {
    String username = usernameBox.getText().trim();
    String password = passwordBox.getText().trim();
    CredentialManager.getInstance().insert(username, password);
  }
}

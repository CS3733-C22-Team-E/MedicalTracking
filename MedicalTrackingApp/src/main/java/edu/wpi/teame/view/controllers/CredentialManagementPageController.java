package edu.wpi.teame.view.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import edu.wpi.teame.db.CredentialManager;
import javafx.collections.FXCollections;
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

  private void updateListView() {
    String searchQuery = usernameBox.getText().trim();
    List<Object> newItemsList = new ArrayList<>();
    for (Object item : itemsList) {
      if (item.toString().contains(searchQuery)) {
        newItemsList.add(item);
      }
    }
    resultView.setItems(FXCollections.observableArrayList(newItemsList));
  }

  @FXML
  public void removeCredentialButtonClick() {
    String username = usernameBox.getText().trim();
    for (Object item : itemsList) {
      List<Object> newItemsList = new ArrayList<>();
      if (!item.toString().contains(username)) {
        newItemsList.add(item);
      }
    }
    resultView.setItems(FXCollections.observableArrayList(newItemsList));
  }

  @FXML
  public void addCredentialButtonClick() throws SQLException, NoSuchAlgorithmException {
    String username = usernameBox.getText().trim();
    String password = passwordBox.getText().trim();
    for (Object item : itemsList) {
      if (item.toString().contains(username)) {
        return;
      }
    }
    CredentialManager.getInstance().insert(username, password);
  }
}

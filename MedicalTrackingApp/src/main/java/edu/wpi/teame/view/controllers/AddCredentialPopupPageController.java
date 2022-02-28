package edu.wpi.teame.view.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.microsoft.azure.storage.StorageException;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.objectManagers.CredentialManager;
import edu.wpi.teame.model.Credential;
import edu.wpi.teame.model.enums.AccessLevel;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class AddCredentialPopupPageController implements Initializable {
  @FXML private TextField accessLevelTextBox;
  @FXML private TextField passwordTextBox2;
  @FXML private TextField usernameTextBox;
  @FXML private TextField passwordTextBox;
  @FXML private TextField imageTextBox;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }

  @FXML
  public void addCredential() throws SQLException, IOException, URISyntaxException, InvalidKeyException, StorageException {
    AccessLevel accessLevel = AccessLevel.valueOf(accessLevelTextBox.getText());
    String username = usernameTextBox.getText();
    String password = passwordTextBox.getText();
    String imageURL = imageTextBox.getText();

    if (!password.equals(passwordTextBox2.getText())) {
      // Failed Password
      return;
    }

    File imageFile = null;
    if (!imageURL.isEmpty()) {
      imageFile = new File(imageURL);

      if (!imageFile.isFile()) {
        // Image file does not exist
        return;
      }
    }

    CredentialManager credentialManager =
            (CredentialManager) DBManager.getInstance().getManager(DataBaseObjectType.Credential);

    if (credentialManager.hasUsername(username)) {
      // Username taken
      return;
    }

    String uploadedImageURL = credentialManager.uploadImage(imageFile, true);
    credentialManager.insert(new Credential(0, username, password, uploadedImageURL, accessLevel));
    CredentialManagementPageController.addCredentialStage.close();
  }

  @FXML
  public void selectFile() {
    FileChooser fil_chooser = new FileChooser();
    File file = fil_chooser.showSaveDialog(CredentialManagementPageController.addCredentialStage);

    if (file != null) {
      imageTextBox.setText(file.getAbsolutePath());
    }
  }

  @FXML
  public void backButtonClick() {
    CredentialManagementPageController.addCredentialStage.close();
  }
}

package edu.wpi.teame.view.controllers;

import com.microsoft.azure.storage.StorageException;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.objectManagers.CredentialManager;
import edu.wpi.teame.model.Credential;
import edu.wpi.teame.model.enums.AccessLevel;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.view.style.IStyleable;
import edu.wpi.teame.view.style.StyleManager;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

public class AddCredentialPopupPageController implements Initializable, IStyleable {
  @FXML private ComboBox<AccessLevel> accessLevelComboBox;
  @FXML private Button addCredentialButton;
  @FXML private TextField passwordTextBox2;
  @FXML private TextField usernameTextBox;
  @FXML private TextField passwordTextBox;
  @FXML private Button selectFileButton;
  @FXML private TextField imageTextBox;
  @FXML private AnchorPane anchorPane;
  @FXML private Button backButton;

  @FXML private Label headerLabel;
  @FXML private Label label1;
  @FXML private Label label2;
  @FXML private Label label3;
  @FXML private Label label4;
  @FXML private Label label5;
  @FXML private Label label6;

  private Credential editingCredential = null;
  private boolean isEditing = false;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    accessLevelComboBox.setItems(FXCollections.observableArrayList(AccessLevel.values()));
    StyleManager.getInstance().subscribe(this);
  }

  @FXML
  public void addCredential()
      throws SQLException, IOException, URISyntaxException, InvalidKeyException, StorageException {
    AccessLevel accessLevel = accessLevelComboBox.getValue();
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

    if (editingCredential == null && credentialManager.hasUsername(username)) {
      // Username taken
      return;
    }

    String uploadedImageURL = credentialManager.uploadImage(imageFile, true);

    // Reset textBoxes
    accessLevelComboBox.setValue(null);
    passwordTextBox2.clear();
    passwordTextBox.clear();
    usernameTextBox.clear();
    imageTextBox.clear();

    if (editingCredential != null) {
      editingCredential.setImageURL(uploadedImageURL);
      editingCredential.setAccessLevel(accessLevel);
      editingCredential.setUsername(username);
      editingCredential.setPassword(password);
      credentialManager.update(editingCredential);

      CredentialManagementPageController.addCredentialStage.close();
      editingCredential = null;
      return;
    }

    credentialManager.insert(new Credential(0, username, password, uploadedImageURL, accessLevel));

    // Close Scene
    CredentialManagementPageController.addCredentialStage.close();
  }

  @FXML
  public void selectFile() {
    FileChooser filePicker = new FileChooser();
    filePicker.setTitle("Select User Image...");
    filePicker.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Images", "*.png"));
    File file = filePicker.showOpenDialog(CredentialManagementPageController.addCredentialStage);

    if (file != null) {
      imageTextBox.setText(file.getAbsolutePath());
    }
  }

  @FXML
  public void backButtonClick() {
    CredentialManagementPageController.addCredentialStage.close();
  }

  public void startEditCredential(Credential credential) {
    accessLevelComboBox.setValue(credential.getAccessLevel());
    usernameTextBox.setText(credential.getUsername());
    addCredentialButton.setText("Update Credential");
    editingCredential = credential;

    if (credential.getImageURL() != null && credential.getImageURL().startsWith("https://")) {
      imageTextBox.setText(credential.getImageURL());
    }
  }

  @Override
  public void updateStyle() {
    StyleManager.getInstance().getCurrentStyle().setPaneStyle(anchorPane, true);
    StyleManager.getInstance().getCurrentStyle().setComboBoxStyle(accessLevelComboBox);
    StyleManager.getInstance().getCurrentStyle().setButtonStyle(addCredentialButton);
    StyleManager.getInstance().getCurrentStyle().setTextFieldStyle(passwordTextBox2);
    StyleManager.getInstance().getCurrentStyle().setTextFieldStyle(usernameTextBox);
    StyleManager.getInstance().getCurrentStyle().setTextFieldStyle(passwordTextBox);
    StyleManager.getInstance().getCurrentStyle().setButtonStyle(selectFileButton);
    StyleManager.getInstance().getCurrentStyle().setTextFieldStyle(imageTextBox);
    StyleManager.getInstance().getCurrentStyle().setButtonStyle(backButton);

    StyleManager.getInstance().getCurrentStyle().setHeaderStyle(headerLabel);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(label1);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(label2);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(label3);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(label4);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(label5);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(label6);
  }
}

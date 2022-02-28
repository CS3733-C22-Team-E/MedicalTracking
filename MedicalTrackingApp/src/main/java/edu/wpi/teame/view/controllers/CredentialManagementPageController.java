package edu.wpi.teame.view.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import edu.wpi.teame.App;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.ISQLSerializable;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.SneakyThrows;

public class CredentialManagementPageController implements Initializable {
  public static Stage addCredentialStage = null;
  public Scene addCredentialScene = null;

  @FXML JFXListView<ISQLSerializable> resultView;
  @FXML JFXButton removeCredential;
  @FXML JFXButton addCredential;

  @Override
  @SneakyThrows
  public void initialize(URL location, ResourceBundle resources) {
    addCredentialScene =
        new Scene(FXMLLoader.load(App.class.getResource("view/tabs/AddCredentialPopupPage.fxml")));

    resultView.setCellFactory(
        lv ->
            new ListCell<ISQLSerializable>() {
              @Override
              protected void updateItem(ISQLSerializable dbObject, boolean empty) {
                super.updateItem(dbObject, empty);
                if (empty) {
                  setStyle("");
                  setText("");
                  return;
                }

                if (dbObject.getIsDeleted()) {
                  setStyle("-fx-background-color: #9C9C9C");
                }
                setText(dbObject.toString());
              }
            });

    updateListView();
  }

  @FXML
  public void deleteCredentialButtonClick() throws SQLException {
    int selectedIndex = resultView.getSelectionModel().getSelectedIndex();
    ISQLSerializable dbObject = resultView.getItems().get(selectedIndex);
    DBManager.getInstance().getManager(DataBaseObjectType.Credential).remove(dbObject.getId());

    updateListView();
  }

  @FXML
  public void restoreCredentialButtonClick() throws SQLException {
    int selectedIndex = resultView.getSelectionModel().getSelectedIndex();
    ISQLSerializable dbObject = resultView.getItems().get(selectedIndex);
    DBManager.getInstance().getManager(DataBaseObjectType.Credential).restore(dbObject.getId());

    updateListView();
  }

  @FXML
  public void addCredentialButtonClick() {
    addCredentialStage = new Stage();
    addCredentialStage.setTitle("Test");
    addCredentialStage.setScene(addCredentialScene);

    addCredentialStage.initModality(Modality.WINDOW_MODAL);
    addCredentialStage.initStyle(StageStyle.UNDECORATED);
    addCredentialStage.show();
  }

  private void updateListView() throws SQLException {
    // Empty the result view at start
    resultView.setItems(FXCollections.observableArrayList(new ArrayList<>()));
    resultView.applyCss();

    List<ISQLSerializable> itemsList =
        DBManager.getInstance().getManager(DataBaseObjectType.Credential).getDeleted();
    itemsList.addAll(DBManager.getInstance().getManager(DataBaseObjectType.Credential).getAll());

    if (itemsList.isEmpty()) {
      resultView.setItems(FXCollections.observableArrayList(new ArrayList<>()));
      return;
    }

    resultView.setItems(FXCollections.observableArrayList(itemsList));
    resultView.applyCss();
  }

  @FXML
  public void refreshListView() throws SQLException {
    DBManager.getInstance().getManager(DataBaseObjectType.Credential).forceGetAll();
    updateListView();
  }
}

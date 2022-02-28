package edu.wpi.teame.view.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import edu.wpi.teame.App;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.ISQLSerializable;
import edu.wpi.teame.model.Credential;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.view.style.IStyleable;
import edu.wpi.teame.view.style.StyleManager;
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
import javafx.scene.layout.Background;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.SneakyThrows;

public class CredentialManagementPageController implements Initializable, IStyleable {
  private AddCredentialPopupPageController addCredentialController;
  public static Stage addCredentialStage = null;
  private Background defaultBackground = null;
  public Scene addCredentialScene = null;

  @FXML JFXListView<ISQLSerializable> resultView;
  @FXML JFXButton removeCredential;
  @FXML JFXButton addCredential;

  @Override
  @SneakyThrows
  public void initialize(URL location, ResourceBundle resources) {
    FXMLLoader loader =
        new FXMLLoader(App.class.getResource("view/tabs/AddCredentialPopupPage.fxml"));
    addCredentialScene = new Scene(loader.load());
    addCredentialController = loader.getController();

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
  public void editCredentialButtonClick() {
    int selectedIndex = resultView.getSelectionModel().getSelectedIndex();
    Credential credential = (Credential) resultView.getItems().get(selectedIndex);
    addCredentialController.startEditCredential(credential);

    addCredentialStage = new Stage();
    addCredentialStage.setTitle("Add Credential");
    addCredentialStage.setScene(addCredentialScene);

    addCredentialStage.initModality(Modality.WINDOW_MODAL);
    addCredentialStage.initStyle(StageStyle.UNDECORATED);
    addCredentialStage.show();
  }

  @FXML
  public void addCredentialButtonClick() {
    addCredentialStage = new Stage();
    addCredentialStage.setTitle("Add Credential");
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

  @Override
  public void updateStyle() {
    resultView.setBackground(StyleManager.getInstance().getCurrentStyle().getBackground());
    resultView.setCellFactory(
        lv ->
            new ListCell<ISQLSerializable>() {
              @Override
              protected void updateItem(ISQLSerializable dbObject, boolean empty) {
                super.updateItem(dbObject, empty);
                if (defaultBackground == null) {
                  defaultBackground = getBackground();
                }

                if (empty) {
                  setBackground(defaultBackground);
                  setText("");
                  return;
                }

                if (dbObject.getIsDeleted()) {
                  setBackground(
                      StyleManager.getInstance().getCurrentStyle().getSecondaryBackground());
                  setTextFill(StyleManager.getInstance().getCurrentStyle().getTextColor());
                }
                setText(dbObject.toString());
              }
            });
  }
}

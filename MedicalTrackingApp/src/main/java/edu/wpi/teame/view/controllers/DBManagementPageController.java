package edu.wpi.teame.view.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.ISQLSerializable;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;

public class DBManagementPageController implements Initializable {
  @FXML JFXListView<ISQLSerializable> resultView;
  @FXML JFXComboBox tableComboBox;
  @FXML TextField searchTextBox;

  private List<ISQLSerializable> itemsList = null;
  private DataBaseObjectType currentType = null;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    DataBaseObjectType[] dbTables = DataBaseObjectType.values();
    tableComboBox.setItems(FXCollections.observableArrayList(dbTables));

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
  }

  @FXML
  public void switchTable() throws SQLException {
    String selectedText = tableComboBox.getValue().toString();
    currentType = DataBaseObjectType.getValue(selectedText);

    itemsList = DBManager.getInstance().getManager(currentType).getDeleted();
    itemsList.addAll(DBManager.getInstance().getManager(currentType).getAll());

    searchTextBox.clear();
    updateListView();
  }

  private void updateListView() {
    // Empty the result view
    resultView.setItems(FXCollections.observableArrayList(new ArrayList<>()));
    resultView.applyCss();

    if (itemsList.isEmpty()) {
      resultView.setItems(FXCollections.observableArrayList(new ArrayList<>()));
      return;
    }

    String searchQuery = searchTextBox.getText().trim();
    List<ISQLSerializable> newItemsList = new ArrayList<>();
    for (ISQLSerializable item : itemsList) {
      if (item.toString().contains(searchQuery)) {
        newItemsList.add(item);
      }
    }

    resultView.setItems(FXCollections.observableArrayList(newItemsList));
    resultView.applyCss();
  }

  @FXML
  public void deleteFromDB() throws SQLException {
    int selectedIndex = resultView.getSelectionModel().getSelectedIndex();
    ISQLSerializable dbObject = resultView.getItems().get(selectedIndex);
    DBManager.getInstance().getManager(currentType).remove(dbObject.getId());

    updateListView();
  }

  @FXML
  public void restoreToDB() throws SQLException {
    int selectedIndex = resultView.getSelectionModel().getSelectedIndex();
    ISQLSerializable dbObject = resultView.getItems().get(selectedIndex);
    DBManager.getInstance().getManager(currentType).restore(dbObject.getId());

    updateListView();
  }

  @FXML
  public void searchBoxChanged() {
    updateListView();
  }

  @FXML
  public void searchButtonClick() {
    updateListView();
  }
}

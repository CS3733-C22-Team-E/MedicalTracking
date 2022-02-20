package edu.wpi.teame.view.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class DBManagementPageController implements Initializable {
  private List<Object> itemsList = null;

  @FXML JFXComboBox tableComboBox;
  @FXML TextField searchTextBox;
  @FXML JFXListView resultView;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    DataBaseObjectType[] dbTables = DataBaseObjectType.values();
    tableComboBox.setItems(FXCollections.observableArrayList(dbTables));
  }

  @FXML
  public void switchTable() throws SQLException {
    String selectedText = tableComboBox.getValue().toString();
    DataBaseObjectType selectedTable = DataBaseObjectType.valueOf(selectedText);
    itemsList = DBManager.getInstance().getManager(selectedTable).getAll();
    searchTextBox.clear();
    updateListView();
  }

  private void updateListView() {
    String searchQuery = searchTextBox.getText().trim();
    List<Object> newItemsList = new ArrayList<>();
    for (Object item : itemsList) {
      if (item.toString().contains(searchQuery)) {
        newItemsList.add(item);
      }
    }
    resultView.setItems(FXCollections.observableArrayList(newItemsList));
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

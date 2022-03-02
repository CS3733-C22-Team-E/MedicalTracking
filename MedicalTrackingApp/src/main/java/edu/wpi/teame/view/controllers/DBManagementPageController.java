package edu.wpi.teame.view.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.ISQLSerializable;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.view.style.IStyleable;
import edu.wpi.teame.view.style.StyleManager;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DBManagementPageController implements Initializable, IStyleable {
  @FXML JFXListView<ISQLSerializable> resultView;
  @FXML JFXComboBox tableComboBox;
  @FXML TextField searchTextBox;
  @FXML JFXButton restoreButton;
  @FXML JFXButton deleteButton;
  @FXML Button searchButton;
  @FXML Label headerLabel;

  private DataBaseObjectType currentType = null;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    DataBaseObjectType[] dbTables = DataBaseObjectType.values();
    ArrayList<DataBaseObjectType> dbTablesList = new ArrayList<>(List.of(dbTables));
    dbTablesList.remove(DataBaseObjectType.Credential); // Remove the credentials from this page

    tableComboBox.setItems(FXCollections.observableArrayList(dbTablesList));
    StyleManager.getInstance().subscribe(this);
  }

  @FXML
  public void switchTable() throws SQLException {
    String selectedText = tableComboBox.getValue().toString();
    currentType = DataBaseObjectType.getValue(selectedText);
    searchTextBox.clear();
    updateListView();
  }

  private void updateListView() throws SQLException {
    // Empty the result view at start
    resultView.setItems(FXCollections.observableArrayList(new ArrayList<>()));
    resultView.applyCss();

    List<ISQLSerializable> itemsList = DBManager.getInstance().getManager(currentType).getDeleted();
    itemsList.addAll(DBManager.getInstance().getManager(currentType).getAll());

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
    System.out.println(selectedIndex);
    System.out.println(resultView);
    ISQLSerializable dbObject = resultView.getItems().get(selectedIndex);
    DBManager.getInstance().getManager(currentType).restore(dbObject.getId());

    updateListView();
  }

  @FXML
  public void searchBoxChanged() throws SQLException {
    updateListView();
  }

  @FXML
  public void searchButtonClick() throws SQLException {
    updateListView();
  }

  @Override
  public void updateStyle() {
    StyleManager.getInstance().getCurrentStyle().setTextFieldStyle(searchTextBox);
    StyleManager.getInstance().getCurrentStyle().setComboBoxStyle(tableComboBox);
    StyleManager.getInstance().getCurrentStyle().setButtonStyle(restoreButton);
    StyleManager.getInstance().getCurrentStyle().setButtonStyle(deleteButton);
    StyleManager.getInstance().getCurrentStyle().setButtonStyle(searchButton);
    StyleManager.getInstance().getCurrentStyle().setListViewStyle(resultView);
    StyleManager.getInstance().getCurrentStyle().setHeaderStyle(headerLabel);
  }
}

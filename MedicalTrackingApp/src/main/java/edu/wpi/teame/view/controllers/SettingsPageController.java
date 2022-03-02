package edu.wpi.teame.view.controllers;

import com.jfoenix.controls.JFXButton;
import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.App;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.objectManagers.*;
import edu.wpi.teame.model.enums.DBType;
import edu.wpi.teame.model.enums.LanguageType;
import edu.wpi.teame.view.style.IStyleable;
import edu.wpi.teame.view.style.StyleManager;
import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lombok.SneakyThrows;

public class SettingsPageController implements Initializable, IStyleable {
  @FXML public ComboBox accessibilityComboBox;
  @FXML public JFXButton loadFromCSVButton;
  @FXML public JFXButton writeToCSVButton;
  @FXML public ComboBox dbSwitchComboBox;
  @FXML public ComboBox languageComboBox;
  @FXML public ComboBox colorComboBox;
  @FXML public Label headerLabel;
  @FXML public Label label1;
  @FXML public AnchorPane wholePane;

  private InputStream settingsPath = App.class.getResourceAsStream("settings.properties");
  private boolean dialogConfirmed = false;
  private Properties properties;

  @Override
  @SneakyThrows
  public void initialize(URL location, ResourceBundle resources) {
    InputStreamReader reader = new InputStreamReader(settingsPath);
    properties = new Properties();
    properties.load(reader);

    dbSwitchComboBox.setItems(FXCollections.observableArrayList(DBType.values()));
    dbSwitchComboBox.setValue(DBType.values()[DBManager.getInstance().getCurrentType().ordinal()]);

    languageComboBox.setItems(FXCollections.observableArrayList(LanguageType.values()));

    colorComboBox.setItems(
        FXCollections.observableArrayList(StyleManager.getInstance().getStyleNames()));
    colorComboBox.setValue(StyleManager.getInstance().getStyleName());

    accessibilityComboBox.setItems(
        FXCollections.observableArrayList(new String[] {"None", "Color Blind Mode"}));

    StyleManager.getInstance().subscribe(this);
  }

  @FXML
  private void changeDBConnection()
      throws SQLException, IOException, CsvValidationException, ParseException,
          org.apache.hc.core5.http.ParseException {
    DBType dbType = DBType.valueOf(dbSwitchComboBox.getValue().toString());
    DBManager.getInstance().switchConnection(dbType);
  }

  @FXML
  private void changeLanguage() {
    System.out.println("Technically switched language");
  }

  @FXML
  private void changeColor() {
    String selectedTheme = colorComboBox.getSelectionModel().getSelectedItem().toString();
    StyleManager.getInstance().selectTheme(selectedTheme);
    StyleManager.getInstance().updateStyle();
  }

  @FXML
  private void changeAccessibility() {
    System.out.println("Technically changed accessibility");
  }

  @FXML
  private void loadFromCSV()
      throws IOException, CsvValidationException, SQLException, ParseException {
    if (createDialog(
        "Loading data from CSV will overwrite data on the Server. Do you wish to continue?")) {
      DBManager.getInstance().loadDBFromCSV(true);
    }
  }

  @FXML
  public void writeToCSV() throws SQLException, IOException {
    if (createDialog(
        "Writing data to CSV will overwrite any pre-existing data on them. Do you wish to continue?")) {
      DBManager.getInstance().writeDBToCSV(true);
    }
  }

  private boolean createDialog(String content) {
    dialogConfirmed = false;
    Dialog dialog = new Dialog();
    dialog.setTitle("Loading Warning!");
    dialog.setContentText(content);

    ImageView newIcon =
        new ImageView(new Image(App.class.getResource("images/Icons/WarningIcon.png").toString()));
    newIcon.setFitHeight(30);
    newIcon.setFitWidth(30);
    dialog.getDialogPane().setGraphic(newIcon);

    ButtonType OK = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(OK, ButtonType.CANCEL);
    dialog.showAndWait();
    return dialog.getResult() == OK;
  }

  @Override
  public void updateStyle() {
    StyleManager.getInstance().getCurrentStyle().setComboBoxStyle(dbSwitchComboBox);
    StyleManager.getInstance().getCurrentStyle().setButtonStyle(loadFromCSVButton);
    StyleManager.getInstance().getCurrentStyle().setButtonStyle(writeToCSVButton);
    StyleManager.getInstance().getCurrentStyle().setComboBoxStyle(colorComboBox);
    StyleManager.getInstance().getCurrentStyle().setHeaderStyle(headerLabel);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(label1);
    StyleManager.getInstance().getCurrentStyle().setPaneStyle(wholePane, true);
  }
}

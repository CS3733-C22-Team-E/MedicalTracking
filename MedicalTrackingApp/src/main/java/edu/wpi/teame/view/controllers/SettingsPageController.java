package edu.wpi.teame.view.controllers;

import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.App;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.objectManagers.*;
import edu.wpi.teame.model.enums.DBType;
import edu.wpi.teame.model.enums.LanguageType;
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
import lombok.SneakyThrows;

public class SettingsPageController implements Initializable {
  @FXML public ComboBox accessibilityComboBox;
  @FXML private ComboBox dbSwitchComboBox;
  @FXML public ComboBox languageComboBox;
  @FXML public ComboBox colorComboBox;

  private boolean dialogConfirmed = false;
  private Properties properties;
  private InputStream settingsPath = App.class.getResourceAsStream("settings.properties");;
  // System.getProperty("user.dir") + "/src/main/resources/edu/wpi/teame//settings.properties";

  @Override
  @SneakyThrows
  public void initialize(URL location, ResourceBundle resources) {
    InputStreamReader reader = new InputStreamReader(settingsPath);
    properties = new Properties();
    properties.load(reader);

    dbSwitchComboBox.setItems(FXCollections.observableArrayList(DBType.values()));
    dbSwitchComboBox.setValue(DBType.values()[DBManager.getInstance().getCurrentType().ordinal()]);

    languageComboBox.setItems(FXCollections.observableArrayList(LanguageType.values()));

    colorComboBox.setItems(FXCollections.observableArrayList(new String[] {"Default", "Blue"}));

    accessibilityComboBox.setItems(
        FXCollections.observableArrayList(new String[] {"None", "Color Blind Mode"}));

    // dbSwitchComboBox.setValue(properties.getProperty("dbConnection"));
    accessibilityComboBox.setValue(properties.getProperty("accessibility"));
    languageComboBox.setValue(properties.getProperty("language"));
    colorComboBox.setValue(properties.getProperty("color"));

    dbSwitchComboBox
        .valueProperty()
        .addListener(
            listen -> {
              //              try {
              //                properties.setProperty("dbConnection",
              // dbSwitchComboBox.getValue().toString());
              //                FileWriter writer = new FileWriter(settingsPath);
              //                properties.store(writer, "App Settings");
              //                changeDBConnection();
              //                writer.close();
              //              } catch (IOException e) {
              //                e.printStackTrace();
              //                System.out.println("Could not write to settings.properties");
              //              } catch (CsvValidationException e) {
              //                e.printStackTrace();
              //              } catch (SQLException e) {
              //                e.printStackTrace();
              //              } catch (ParseException e) {
              //                e.printStackTrace();
              //              }
            });

    languageComboBox
        .valueProperty()
        .addListener(
            listen -> {
              //              try {
              //                properties.setProperty("language",
              // languageComboBox.getValue().toString());
              //                FileWriter writer = new FileWriter(settingsPath);
              //                properties.store(writer, "App Settings");
              //                changeLanguage();
              //                writer.close();
              //              } catch (IOException e) {
              //                e.printStackTrace();
              //                System.out.println("Could not write to settings.properties");
              //              }
            });

    colorComboBox
        .valueProperty()
        .addListener(
            listen -> {
              //              try {
              //                properties.setProperty("color",
              // colorComboBox.getValue().toString());
              //                FileWriter writer = new FileWriter(settingsPath);
              //                properties.store(writer, "App Settings");
              //                changeColor();
              //                writer.close();
              //              } catch (IOException e) {
              //                e.printStackTrace();
              //                System.out.println("Could not write to settings.properties");
              //              }
            });

    accessibilityComboBox
        .valueProperty()
        .addListener(
            listen -> {
              //              try {
              //                properties.setProperty(
              //                    "accessibility", accessibilityComboBox.getValue().toString());
              //                FileWriter writer = new FileWriter(settingsPath);
              //                properties.store(writer, "App Settings");
              //                changeAccessibility();
              //                writer.close();
              //              } catch (IOException e) {
              //                e.printStackTrace();
              //                System.out.println("Could not write to settings.properties");
              //              }
            });
  }

  @FXML
  private void changeDBConnection()
      throws SQLException, IOException, CsvValidationException, ParseException {
    DBType dbType = DBType.valueOf(dbSwitchComboBox.getValue().toString());
    DBManager.getInstance().switchConnection(dbType);
  }

  @FXML
  private void changeLanguage() {
    System.out.println("Technically switched language");
  }

  @FXML
  private void changeColor() {
    System.out.println("Technically changed color");
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
}

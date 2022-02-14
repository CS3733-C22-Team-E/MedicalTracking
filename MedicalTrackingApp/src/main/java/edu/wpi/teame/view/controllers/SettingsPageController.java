package edu.wpi.teame.view.controllers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Properties;
import java.util.ResourceBundle;

import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.objectManagers.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;

public class SettingsPageController implements Initializable {
  @FXML public Button logoutButton;
  @FXML public ComboBox languageComboBox;
  @FXML public ComboBox colorComboBox;
  @FXML public ComboBox accessibilityComboBox;
  @FXML private ComboBox dbSwitchComboBox;

  private Properties properties;
  private String settingsPath =
      System.getProperty("user.dir") + "/src/main/resources/edu/wpi/teame//settings.properties";

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try {
      FileReader reader = new FileReader(settingsPath);
      properties = new Properties();
      properties.load(reader);

      dbSwitchComboBox.setItems(
          FXCollections.observableArrayList(
              new String[] {"Embedded Database", "Client/Server Database"}));

      languageComboBox.setItems(
          FXCollections.observableArrayList(
              new String[] {
                "Afrikanns",
                "Albanian",
                "Arabic",
                "Armenian",
                "Basque",
                "Bengali",
                "Bulgarian",
                "Catalan",
                "Cambodian",
                "Chinese",
                "Croation",
                "Czech",
                "Danish",
                "Dutch",
                "English",
                "Estonian",
                "Fiji",
                "Finnish",
                "French",
                "Georgian",
                "German",
                "Greek",
                "Gujarati",
                "Hebrew",
                "Hindi",
                "Hungarian",
                "Icelandic",
                "Indonesian",
                "Irish",
                "Italian",
                "Japanese",
                "Javanese",
                "Korean",
                "Latin",
                "Latvian",
                "Lithuanian",
                "Macedonian",
                "Malay",
                "Malayalam",
                "Maltese",
                "Maori",
                "Marathi",
                "Mongolian",
                "Nepali",
                "Norwegian",
                "Persian",
                "Polish",
                "Portuguese",
                "Punjabi",
                "Quechua",
                "Romanian",
                "Russian",
                "Samoan",
                "Serbian",
                "Slovak",
                "Slovenian",
                "Spanish",
                "Swahili",
                "Swedish",
                "Tamil",
                "Tatar",
                "Telugu",
                "Thai",
                "Tibetan",
                "Tonga",
                "Turkish",
                "Ukranian",
                "Urdu",
                "Uzbek",
                "Vietnamese",
                "Welsh",
                "Xhosa"
              }));

      colorComboBox.setItems(FXCollections.observableArrayList(new String[] {"Default", "Blue"}));

      accessibilityComboBox.setItems(
          FXCollections.observableArrayList(new String[] {"None", "Color Blind Mode"}));

      dbSwitchComboBox.setValue(properties.getProperty("dbConnection"));
      languageComboBox.setValue(properties.getProperty("language"));
      colorComboBox.setValue(properties.getProperty("color"));
      accessibilityComboBox.setValue(properties.getProperty("accessibility"));

      dbSwitchComboBox
          .valueProperty()
          .addListener(
              listen -> {
                try {
                  properties.setProperty("dbConnection", dbSwitchComboBox.getValue().toString());
                  FileWriter writer = new FileWriter(settingsPath);
                  properties.store(writer, "App Settings");
                  changeDBConnection();
                  writer.close();
                } catch (IOException e) {
                  e.printStackTrace();
                  System.out.println("Could not write to settings.properties");
                }
              });

      languageComboBox
          .valueProperty()
          .addListener(
              listen -> {
                try {
                  properties.setProperty("language", languageComboBox.getValue().toString());
                  FileWriter writer = new FileWriter(settingsPath);
                  properties.store(writer, "App Settings");
                  changeLanguage();
                  writer.close();
                } catch (IOException e) {
                  e.printStackTrace();
                  System.out.println("Could not write to settings.properties");
                }
              });

      colorComboBox
          .valueProperty()
          .addListener(
              listen -> {
                try {
                  properties.setProperty("color", colorComboBox.getValue().toString());
                  FileWriter writer = new FileWriter(settingsPath);
                  properties.store(writer, "App Settings");
                  changeColor();
                  writer.close();
                } catch (IOException e) {
                  e.printStackTrace();
                  System.out.println("Could not write to settings.properties");
                }
              });

      accessibilityComboBox
          .valueProperty()
          .addListener(
              listen -> {
                try {
                  properties.setProperty(
                      "accessibility", accessibilityComboBox.getValue().toString());
                  FileWriter writer = new FileWriter(settingsPath);
                  properties.store(writer, "App Settings");
                  changeAccessibility();
                  writer.close();
                } catch (IOException e) {
                  e.printStackTrace();
                  System.out.println("Could not write to settings.properties");
                }
              });

    } catch (FileNotFoundException e) {
      e.printStackTrace();
      System.out.println("Could note initialize properties");
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Could note initialize properties");
    }
  }

  @FXML
  private void changeDBConnection() {
    System.out.println("Technically Switched DBConnection");
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
    DBManager.getInstance().getLocationManager().readCSV("csv/TowerLocationsE.csv");
    DBManager.getInstance().getEquipmentManager().readCSV("csv/EquipmentE.csv");
    DBManager.getInstance().getEmployeeManager().readCSV("csv/EmployeesE.csv");
    DBManager.getInstance()
            .getMedicalEquipmentSRManager()
            .readCSV("MedicalEquipmentDeliverServiceRequestSave.csv");
    DBManager.getInstance()
            .getMedicineDeliverySRManager()
            .readCSV("MedicineDeliveryServiceRequestSave.csv");
    DBManager.getInstance().getSanitationSRManager().readCSV("SanitationServiceRequest.csv");
    DBManager.getInstance().getSecuritySRManager().readCSV("SecurityServiceRequest.csv");
  }

  @FXML
  public void writeToCSV(MouseEvent mouseEvent) throws SQLException, IOException {
    LocationManager locationManager = new LocationManager();
    EquipmentManager equipmentManager = new EquipmentManager();
    EmployeeManager employeeManager = new EmployeeManager();
    MedicalEquipmentSRManager medEqpManager = new MedicalEquipmentSRManager();
    MedicineDeliveryServiceRequestManager medReqManager =
            new MedicineDeliveryServiceRequestManager();
    SanitationServiceRequestManager sanReqManager = new SanitationServiceRequestManager();
    SecurityServiceRequestManager secReqManager = new SecurityServiceRequestManager();

    locationManager.writeToCSV("TowerLocationsESave.csv");
    equipmentManager.writeToCSV("EquipmentESave.csv");
    employeeManager.writeToCSV("EmployeesESave.csv");
    medEqpManager.writeToCSV("MedicalEquipmentDeliverServiceRequestSave.csv");
    medReqManager.writeToCSV("MedicineDeliveryServiceRequestSave.csv");
    sanReqManager.writeToCSV("SanitationServiceRequest.csv");
    secReqManager.writeToCSV("SecurityServiceRequest.csv");
  }
}

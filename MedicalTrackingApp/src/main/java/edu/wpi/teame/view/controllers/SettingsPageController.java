package edu.wpi.teame.view.controllers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

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

      

      if (properties.getProperty("dbConnection").equals("Embedded Database")) {
        dbSwitchComboBox.setValue("Embedded Database");
      } else {
        dbSwitchComboBox.setValue("Client/Server Database");
      }

      dbSwitchComboBox
          .valueProperty()
          .addListener(
              listen -> {
                try {
                  properties.setProperty("dbConnection", dbSwitchComboBox.getValue().toString());
                  FileWriter writer = new FileWriter(settingsPath);
                  properties.store(writer, "App Settings");
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

  public void logout() {}
}

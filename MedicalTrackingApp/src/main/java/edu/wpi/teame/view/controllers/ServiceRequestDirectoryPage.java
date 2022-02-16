package edu.wpi.teame.view.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import edu.wpi.teame.App;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import static edu.wpi.teame.model.enums.DataBaseObjectType.LaundrySR;

public class ServiceRequestDirectoryPage implements Initializable {

  @FXML private Button securitySR;
  @FXML private Button audioVisualSR;
  @FXML private Button externalSR;
  @FXML private Button facilitiesSR;
  @FXML private Button computerSR;
  @FXML private Button giftFloralSR;
  @FXML private Button internalSR;
  @FXML private Button foodSR;
  @FXML private Button languageSR;
  @FXML private Button laundrySR;
  @FXML private Button medEquipSR;
  @FXML private Button medDeliverySR;
  @FXML private Button religiousSR;
  @FXML private Button sanitationSR;

  @Override
  public void initialize(URL location, ResourceBundle resources) {}


}

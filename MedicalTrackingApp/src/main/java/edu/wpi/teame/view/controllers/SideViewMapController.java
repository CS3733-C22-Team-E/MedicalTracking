package edu.wpi.teame.view.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import edu.wpi.teame.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class SideViewMapController implements Initializable {
  @FXML public Button thirdFloor;
  @FXML public Button secondFloor;
  @FXML public Button firstFloor;
  @FXML public Button lowerLevel1;
  @FXML public Button lowerLevel2;


  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }

  @FXML
  private void makeThirdFloorPieVisible() {
    thirdFloor.setVisible(true);
  }

  @FXML
  private void makeSecondFloorPieVisible() {
    secondFloor.setVisible(true);
  }

  @FXML
  private void makeFirstFloorPieVisible() {
    firstFloor.setVisible(true);
  }

  @FXML
  private void makeLowerLevel1PieVisible() {
    lowerLevel1.setVisible(true);
  }

  @FXML
  private void makeLowerLevel2PieVisible() {
    lowerLevel2.setVisible(true);
  }

} 

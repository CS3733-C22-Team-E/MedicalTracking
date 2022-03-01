package edu.wpi.teame.view.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import edu.wpi.teame.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;

public class SideViewMapController implements Initializable {
  @FXML public Button thirdFloor;
  @FXML public Button secondFloor;
  @FXML public Button firstFloor;
  @FXML public Button lowerLevel1;
  @FXML public Button lowerLevel2;

  @FXML public PieChart thirdFloorPie;
  @FXML public PieChart secondFloorPie;
  @FXML public PieChart firstFloorPie;
  @FXML public PieChart lowerLevel1Pie;
  @FXML public PieChart lowerLevel2Pie;


  @Override
  public void initialize(URL location, ResourceBundle resources) {
  }

  @FXML
  private void makeThirdFloorPieVisible() {
    thirdFloorPie.setVisible(true);
  }

  @FXML
  private void makeSecondFloorPieVisible() {
    secondFloorPie.setVisible(true);
  }

  @FXML
  private void makeFirstFloorPieVisible() {
    firstFloorPie.setVisible(true);
  }

  @FXML
  private void makeLowerLevel1PieVisible() {
    lowerLevel1Pie.setVisible(true);
  }

  @FXML
  private void makeLowerLevel2PieVisible() {
    lowerLevel2Pie.setVisible(true);
  }

}



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
  private void makeChart1Visible() {
    thirdFloor.setVisible(true);
  }

  @FXML
  private void makeChart2Visible() {
    secondFloor.setVisible(true);
  }

  @FXML
  private void makeChart3Visible() {
    firstFloor.setVisible(true);
  }

  @FXML
  private void makeChart4Visible() {
    lowerLevel1.setVisible(true);
  }

  @FXML
  private void makeChart5Visible() {
    lowerLevel2.setVisible(true);
  }

} 

package edu.wpi.teame.controllers.serviceRequests;

import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

public class FoodDeliveryServiceRequestPageServiceRequestController
    extends ServiceRequestController {
  @FXML public Button sendButton;
  @FXML private TextField patientName;
  @FXML private TextField roomNumber;
  @FXML private TextField floor;
  @FXML private CheckBox dietaryRestrictions;
  @FXML private TextArea dietaryRestrictionsDescribe;
  @FXML private Text dietaryRestrictionsDescribeText;
  @FXML private CheckBox breakfastCheck;
  @FXML private CheckBox lunchCheck;
  @FXML private CheckBox dinnerCheck;
  @FXML private Text breakfastText;
  @FXML private Text lunchText;
  @FXML private Text dinnerText;
  @FXML private Text TimeTextB;
  @FXML private Text TimeTextL;
  @FXML private Text TimeTextD;
  @FXML private Text MealTextB;
  @FXML private Text MealTextL;
  @FXML private Text MealTextD;
  @FXML private JFXComboBox<String> breakfastMealDropdown;
  @FXML private JFXComboBox<String> lunchMealDropdown;
  @FXML private JFXComboBox<String> dinnerMealDropdown;
  @FXML private ComboBox<String> selectBreakfastTime;
  @FXML private ComboBox<String> selectLunchTime;
  @FXML private ComboBox<String> selectDinnerTime;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    breakfastMealDropdown.setItems(FXCollections.observableArrayList("Eggs", "Yogurt", "Oatmeal"));
    lunchMealDropdown.setItems(
        FXCollections.observableArrayList("Ham Sandwich", "Turkey Sandwich", "Chicken Bake"));
    dinnerMealDropdown.setItems(
        FXCollections.observableArrayList("Steak", "Thanksgiving Dinner", "Chicken Tacos"));
    selectBreakfastTime.setItems(
        FXCollections.observableArrayList("8:00", "8:30", "9:00", "9:30", "10:00"));
    selectLunchTime.setItems(
        FXCollections.observableArrayList("11:00", "11:30", "12:00", "12:30", "13:00"));
    selectDinnerTime.setItems(
        FXCollections.observableArrayList(
            "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00"));
  }

  @FXML
  private void BreakfastOptionsCallBack() {
    breakfastText.setVisible(!breakfastText.isVisible());
    MealTextB.setVisible(!MealTextB.isVisible());
    TimeTextB.setVisible(!TimeTextB.isVisible());
    breakfastMealDropdown.setVisible(!breakfastMealDropdown.isVisible());
    selectBreakfastTime.setVisible(!selectBreakfastTime.isVisible());
  }

  @FXML
  private void LunchOptionsCallBack() {
    lunchText.setVisible(!lunchText.isVisible());
    MealTextL.setVisible(!MealTextL.isVisible());
    TimeTextL.setVisible(!TimeTextL.isVisible());
    lunchMealDropdown.setVisible(!lunchMealDropdown.isVisible());
    selectLunchTime.setVisible(!selectLunchTime.isVisible());
  }

  @FXML
  private void DinnerOptionsCallBack() {
    dinnerText.setVisible(!dinnerText.isVisible());
    MealTextD.setVisible(!MealTextD.isVisible());
    TimeTextD.setVisible(!TimeTextD.isVisible());
    dinnerMealDropdown.setVisible(!dinnerMealDropdown.isVisible());
    selectDinnerTime.setVisible(!selectDinnerTime.isVisible());
  }

  @FXML
  private void DietaryRestrictionsCallBack() {
    dietaryRestrictionsDescribe.setVisible(!dietaryRestrictionsDescribe.isVisible());
    dietaryRestrictionsDescribeText.setVisible(!dietaryRestrictionsDescribeText.isVisible());
  }
}

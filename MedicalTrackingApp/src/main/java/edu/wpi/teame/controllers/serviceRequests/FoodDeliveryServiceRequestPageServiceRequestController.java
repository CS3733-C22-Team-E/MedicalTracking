package edu.wpi.teame.controllers.serviceRequests;

import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
  @FXML private TextField breakfastTimeTextField;
  @FXML private TextField lunchTimeTextField;
  @FXML private TextField dinnerTimeTextField;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    breakfastMealDropdown.setItems(FXCollections.observableArrayList("Eggs", "Yogurt", "Oatmeal"));
    lunchMealDropdown.setItems(
        FXCollections.observableArrayList("Ham Sandwich", "Turkey Sandwich", "Chicken Bake"));
    dinnerMealDropdown.setItems(
        FXCollections.observableArrayList("Steak", "Thanksgiving Dinner", "Chicken Tacos"));
  }

  @FXML
  private void BreakfastOptionsCallBack() {
    breakfastText.setVisible(!breakfastText.isVisible());
    MealTextB.setVisible(!MealTextB.isVisible());
    TimeTextB.setVisible(!TimeTextB.isVisible());
    breakfastMealDropdown.setVisible(!breakfastMealDropdown.isVisible());
    breakfastTimeTextField.setVisible(!breakfastTimeTextField.isVisible());
  }

  @FXML
  private void LunchOptionsCallBack() {
    lunchText.setVisible(!lunchText.isVisible());
    MealTextL.setVisible(!MealTextL.isVisible());
    TimeTextL.setVisible(!TimeTextL.isVisible());
    lunchMealDropdown.setVisible(!lunchMealDropdown.isVisible());
    lunchTimeTextField.setVisible(!lunchTimeTextField.isVisible());
  }

  @FXML
  private void DinnerOptionsCallBack() {
    dinnerText.setVisible(!dinnerText.isVisible());
    MealTextD.setVisible(!MealTextD.isVisible());
    TimeTextD.setVisible(!TimeTextD.isVisible());
    dinnerMealDropdown.setVisible(!dinnerMealDropdown.isVisible());
    dinnerTimeTextField.setVisible(!dinnerTimeTextField.isVisible());
  }

  @FXML
  private void DietaryRestrictionsCallBack() {
    dietaryRestrictionsDescribe.setVisible(!dietaryRestrictionsDescribe.isVisible());
    dietaryRestrictionsDescribeText.setVisible(!dietaryRestrictionsDescribeText.isVisible());
  }
}

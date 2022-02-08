package edu.wpi.teame.controllers.serviceRequests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.Location;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class SanitationServiceRequestPageServiceRequestController extends ServiceRequestController {
  @FXML public JFXButton sendButton;
  @FXML public JFXButton clearButton;

  @FXML private TextField timeNeeded;

  @FXML private DatePicker startDate;
  @FXML private DatePicker endDate;

  @FXML public JFXComboBox serviceLocation;
  @FXML public JFXComboBox serviceAssignee;

  @FXML public JFXCheckBox completed;

  public void initialize(URL location, ResourceBundle resources) {
    // creates a linkedList of locations and sets all the values as one of roomNumber comboBox items
    LinkedList<Location> locations = DBManager.getInstance().getLocationManager().getAll();
    LinkedList<String> locationName = new LinkedList<String>();
    for (Location loc : locations) {
      locationName.add(loc.getLongName());
    }

    // Set the comboBox items
    serviceLocation.setItems(FXCollections.observableArrayList(locationName));
    serviceAssignee.setItems(
            FXCollections.observableArrayList("Test Name", "Test Name", "Test Name", "Test Name"));
  }

  @FXML
  private void clearText() {
    timeNeeded.setText("");
    startDate.setValue(null);
    startDate.getEditor().clear();
    endDate.setValue(null);
    endDate.getEditor().clear();
    serviceLocation.valueProperty().set(null);
    serviceAssignee.valueProperty().set(null);
    completed.setSelected(false);
  }
}

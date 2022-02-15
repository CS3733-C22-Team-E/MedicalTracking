package edu.wpi.teame.view.controllers.serviceRequests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Location;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import edu.wpi.teame.model.enums.ServiceRequestStatus;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import lombok.SneakyThrows;

public class FacilitiesMaintenanceServiceRequestPageServiceRequestController
    extends ServiceRequestController {
  @FXML private DatePicker requestDatePicker;
  @FXML private JFXComboBox locationComboBox;
  @FXML private JFXComboBox employeeComboBox;
  @FXML private JFXComboBox priorityComboBox;
  @FXML private JFXComboBox statusComboBox;
  @FXML private TextArea descriptionTextArea;

  @FXML private JFXButton clearButton;
  @FXML public JFXButton sendButton;
  private boolean hasRun = false;

  @FXML
  @SneakyThrows
  public void initialize(URL location, ResourceBundle resources) {
    //todo: implement initialize
    locationComboBox.setItems(FXCollections.observableArrayList());
    employeeComboBox.setItems(FXCollections.observableArrayList());
    priorityComboBox.setItems(FXCollections.observableArrayList());
    statusComboBox.setItems(FXCollections.observableArrayList(ServiceRequestStatus.values()));


  }

  @FXML
  private void clearText() {
    requestDatePicker.valueProperty().set(null);
    locationComboBox.valueProperty().set(null);
    employeeComboBox.valueProperty().set(null);
    priorityComboBox.valueProperty().set(null);
    statusComboBox.valueProperty().set(null);
    descriptionTextArea.setText("");
  }

  @FXML
  public void sendToDB() throws SQLException { //todo: make functional
    // get values from ComboBoxes
    Date date;
    String destination = (String) locationComboBox.getValue();
    int employeeID = (int) employeeComboBox.getValue();
    int priority = (int) priorityComboBox.getValue();

    // make a new facilities SR, select * from Facilities SR table, insert

  }

  @FXML
  public void updateFromDB() throws SQLException {
    if (hasRun) {
      return;
    }
    hasRun = true;

    // creates a linkedList of locations and sets all the values as one of roomNumber comboBox items
    List<Location> locations = DBManager.getInstance().getLocationManager().getAll();
    List<Employee> employees = DBManager.getInstance().getEmployeeManager().getAll();

    List<String> locationNames = new LinkedList<String>();
    for (Location loc : locations) {
      locationNames.add(loc.getLongName());
    }

    List<String> employeeNames = new LinkedList<String>();
    for (Employee emp : employees) {
      employeeNames.add(emp.getName());
    }

    locationComboBox.setItems(FXCollections.observableArrayList(locationNames));
    employeeComboBox.setItems(FXCollections.observableArrayList(employeeNames));
  }
}

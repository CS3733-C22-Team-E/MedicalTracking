package edu.wpi.teame.view.controllers.serviceRequests;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import edu.wpi.teame.model.serviceRequests.SecurityServiceRequest;
import edu.wpi.teame.view.controllers.AutoCompleteTextField;
import java.awt.*;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodDeliveryServiceRequestPageServiceRequestController
    extends ServiceRequestController {

  @FXML private DatePicker requestDate;
  @FXML private AutoCompleteTextField locationText;
  @FXML private AutoCompleteTextField assignee;
  @FXML private TextField patientName;
  @FXML private TextField food;
  @FXML private JFXComboBox priority;
  @FXML private JFXComboBox status;
  @FXML private TextArea additionalInfo;
  @FXML private Button clearButton;
  @FXML private Button submitButton;
  private boolean hasRun = false;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // TODO: Change priority comboBox to actual values

    priority.setItems(FXCollections.observableArrayList(new String[] {"Low", "Medium", "High"}));
    status.setItems(FXCollections.observableArrayList(ServiceRequestStatus.values()));

    requestDate
        .valueProperty()
        .addListener(
            (listener) -> {
              validateSubmitButton();
            });

    locationText.setOnMousePressed(
        listener -> {
          validateSubmitButton();
        });

    assignee.setOnMousePressed(
        listener -> {
          validateSubmitButton();
        });

    priority
        .valueProperty()
        .addListener(
            listener -> {
              validateSubmitButton();
            });

    status
        .valueProperty()
        .addListener(
            listener -> {
              validateSubmitButton();
            });

    patientName
        .onActionProperty()
        .addListener(
            listener -> {
              validateSubmitButton();
            });

    food.onActionProperty()
        .addListener(
            listener -> {
              validateSubmitButton();
            });
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

    locationText.getEntries().addAll(locationNames);
    assignee.getEntries().addAll(employeeNames);
  }

  @FXML
  void sendToDB() throws SQLException {
    Employee employee =
        DBManager.getInstance().getEmployeeManager().getByAssignee(assignee.getText());
    Location location =
        DBManager.getInstance().getLocationManager().getByName(locationText.getText());

    SecurityServiceRequest serviceRequest =
        new SecurityServiceRequest(
            ServiceRequestStatus.OPEN,
            employee,
            location,
            new Date(0),
            new Date(new java.util.Date().getTime()),
            0);
    DBManager.getInstance().getSecuritySRManager().insert(serviceRequest);
  }

  public void validateSubmitButton() {
    submitButton.setDisable(
        requestDate.getValue() == null
            || locationText.getEntries() == null
            || assignee.getEntries() == null
            || priority.getValue() == null
            || status.getValue() == null
            || patientName.getText().isEmpty()
            || food.getText().isEmpty());
  }

  public void clearText() {
    additionalInfo.setText("");
    locationText.setText("");
    assignee.setText("");
    requestDate.setValue(null);
    requestDate.getEditor().clear();
    priority.valueProperty().setValue(null);
    status.valueProperty().setValue(null);
    patientName.setText("");
    food.setText("");
  }
}

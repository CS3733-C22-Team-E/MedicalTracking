package edu.wpi.teame.view.controllers.serviceRequests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.EquipmentType;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NewInternalPatientTransportationServiceRequestPageServiceRequestController
    extends ServiceRequestController {
  @FXML private TextField patientName;
  @FXML private TextArea additionalInfo;
  @FXML private JFXComboBox priorityDropdown;
  @FXML private JFXComboBox statusDropdown;
  @FXML private JFXComboBox equipmentDropdown;
  @FXML private JFXComboBox locationDropdown;
  @FXML private JFXComboBox destinationDropdown;
  @FXML private JFXComboBox assigneeDropdown;
  @FXML private DatePicker requestDate;
  @FXML public JFXButton clearButton;
  @FXML public JFXButton submitButton;
  private boolean hasRun = false;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    priorityDropdown.setItems(
        FXCollections.observableArrayList(new String[] {"Low", "Medium", "High"}));
    statusDropdown.setItems(FXCollections.observableArrayList(ServiceRequestStatus.values()));
    equipmentDropdown.setItems(FXCollections.observableArrayList(EquipmentType.values()));
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

    locationDropdown.setItems(FXCollections.observableArrayList(locationNames));
    destinationDropdown.setItems(FXCollections.observableArrayList(locationNames));
    assigneeDropdown.setItems(FXCollections.observableArrayList(employeeNames));
  }

  @FXML
  private void clearText() {
    patientName.setText("");
    additionalInfo.setText("");
    requestDate.setValue(null);
    requestDate.getEditor().clear();
    priorityDropdown.valueProperty().set(null);
    statusDropdown.valueProperty().set(null);
    equipmentDropdown.valueProperty().set(null);
    locationDropdown.valueProperty().set(null);
    destinationDropdown.valueProperty().set(null);
    assigneeDropdown.valueProperty().set(null);
  }
}

package edu.wpi.teame.view.controllers.serviceRequests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Location;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import lombok.SneakyThrows;

public class SanitationServiceRequestPageServiceRequestController extends ServiceRequestController {
  @FXML public JFXButton sendButton;
  @FXML public JFXButton clearButton;

  @FXML private TextField timeNeeded;

  @FXML private DatePicker startDate;
  @FXML private DatePicker endDate;

  @FXML public JFXComboBox serviceLocation;
  @FXML public JFXComboBox serviceAssignee;

  @FXML public JFXCheckBox completed;
  boolean hasRun = false;

  @SneakyThrows
  public void initialize(URL location, ResourceBundle resources) {}

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

    serviceLocation.setItems(FXCollections.observableArrayList(locationNames));
    serviceAssignee.setItems(FXCollections.observableArrayList(employeeNames));
  }

  @FXML
  private void sendToDB() throws SQLException {
    Employee employee =
        DBManager.getInstance()
            .getEmployeeManager()
            .getByAssignee(serviceAssignee.getValue().toString());
    Location location =
        DBManager.getInstance()
            .getLocationManager()
            .getByName(serviceLocation.getValue().toString());

    //    SanitationServiceRequest serviceRequest =
    //        new SanitationServiceRequest(
    //            ServiceRequestStatus.OPEN,
    //            employee,
    //            location,
    //            new Date(0),
    //            new Date(new java.util.Date().getTime()),
    //            0);
    //    DBManager.getInstance().getSanitationSRManager().insert(serviceRequest);
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

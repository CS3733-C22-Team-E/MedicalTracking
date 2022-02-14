package edu.wpi.teame.view.controllers.serviceRequests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import edu.wpi.teame.model.serviceRequests.SecurityServiceRequest;
import edu.wpi.teame.view.controllers.AutoCompleteTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;

public class SecurityServiceRequestPageServiceRequestController extends ServiceRequestController {
  @FXML private JFXButton clearButton;
  @FXML private JFXButton sendButton;

  @FXML private DatePicker datePicker;

  @FXML public JFXCheckBox completed;

  @FXML public AutoCompleteTextField serviceLocation;
  @FXML public AutoCompleteTextField serviceAssignee;
  private boolean hasRun = false;

  @FXML
  public void initialize(URL location, ResourceBundle resources) {}

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

    serviceLocation.getEntries().addAll(locationNames);
    serviceAssignee.getEntries().addAll(employeeNames);
  }

  @FXML
  void sendToDB() throws SQLException {
    Employee employee =
        DBManager.getInstance().getEmployeeManager().getByAssignee(serviceAssignee.getText());
    Location location =
        DBManager.getInstance().getLocationManager().getByName(serviceLocation.getText());

    //    SecurityServiceRequest serviceRequest =
    //        new SecurityServiceRequest(
    //            ServiceRequestStatus.OPEN,
    //            employee,
    //            location,
    //            new Date(0),
    //            new Date(new java.util.Date().getTime()),
    //            0);
    //    DBManager.getInstance().getSecuritySRManager().insert(serviceRequest);
  }

  @FXML
  void clearText() {
    // TODO implement clear
  }
}

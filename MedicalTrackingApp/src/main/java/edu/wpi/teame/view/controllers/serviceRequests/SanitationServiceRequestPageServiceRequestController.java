package edu.wpi.teame.view.controllers.serviceRequests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import edu.wpi.teame.model.serviceRequests.SanitationServiceRequest;
import edu.wpi.teame.view.SRSentAnimation;
import edu.wpi.teame.view.controllers.AutoCompleteTextField;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.GridPane;
import lombok.SneakyThrows;

public class SanitationServiceRequestPageServiceRequestController extends ServiceRequestController {
  @FXML private GridPane pagePane;

  @FXML public JFXButton sendButton;
  @FXML public JFXButton clearButton;

  @FXML private AutoCompleteTextField timeNeeded;

  @FXML private DatePicker startDate;
  @FXML private DatePicker endDate;

  @FXML public AutoCompleteTextField serviceLocation;
  @FXML public AutoCompleteTextField serviceAssignee;

  @FXML public JFXCheckBox completed;
  boolean hasRun = false;

  @SneakyThrows
  public void initialize(URL location, ResourceBundle resources) {
    timeNeeded.getEntries().addAll(createTime());
  }

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
  private void sendToDB() throws SQLException {
    Employee employee =
        DBManager.getInstance().getEmployeeManager().getByAssignee(serviceAssignee.getText());
    Location location =
        DBManager.getInstance().getLocationManager().getByName(serviceLocation.getText());

    SanitationServiceRequest serviceRequest =
        new SanitationServiceRequest(
            ServiceRequestStatus.OPEN,
            employee,
            location,
            new Date(0),
            new Date(new java.util.Date().getTime()),
            0);
    DBManager.getInstance().getSanitationSRManager().insert(serviceRequest);
    SRSentAnimation a = new SRSentAnimation(serviceRequest);
    pagePane.setAlignment(Pos.CENTER);
    pagePane.getChildren().add(a.getStackPane());
    a.play();
  }

  @FXML
  private void clearText() {
    timeNeeded.setText("");
    startDate.setValue(null);
    startDate.getEditor().clear();
    endDate.setValue(null);
    endDate.getEditor().clear();
    serviceLocation.clear();
    serviceAssignee.clear();
    completed.setSelected(false);
  }
}

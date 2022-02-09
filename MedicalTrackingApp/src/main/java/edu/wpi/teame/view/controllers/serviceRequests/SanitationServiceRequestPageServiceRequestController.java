package edu.wpi.teame.view.controllers.serviceRequests;

import com.jfoenix.controls.JFXButton;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import edu.wpi.teame.model.serviceRequests.SanitationServiceRequest;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
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

  @SneakyThrows
  public void initialize(URL location, ResourceBundle resources) {
    // creates a linkedList of locations and sets all the values as one of roomNumber comboBox items
    List<Location> locations = DBManager.getInstance().getLocationManager().getAll();
    List<String> locationName = new LinkedList<String>();
    for (Location loc : locations) {
      locationName.add(loc.getLongName());
    }

    // Set the comboBox items
    serviceLocation.setItems(FXCollections.observableArrayList(locationName));
    serviceAssignee.setItems(
        FXCollections.observableArrayList("Test Name", "Test Name", "Test Name", "Test Name"));
  }

  @FXML
  private void sendToDB() throws SQLException {
    Employee employee = DBManager.getInstance().getEmployeeManager().getAll().get(0);

    int roomID = Integer.parseInt(roomNumber.getText());
    Location location = DBManager.getInstance().getLocationManager().get(roomID);

    SanitationServiceRequest serviceRequest =
        new SanitationServiceRequest(
            ServiceRequestStatus.OPEN,
            employee,
            location,
            new Date(0),
            new Date(new java.util.Date().getTime()),
            0);
    DBManager.getInstance().getSanitationSRManager().insert(serviceRequest);
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

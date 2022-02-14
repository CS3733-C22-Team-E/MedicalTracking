package edu.wpi.teame.view.controllers.serviceRequests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Equipment;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.EquipmentType;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import edu.wpi.teame.model.serviceRequests.MedicalEquipmentServiceRequest;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import lombok.SneakyThrows;

public class NewMedicalEquipmentDeliveryServiceRequestPageServiceRequestController
    extends ServiceRequestController {

  @FXML private TextField patientName;
  @FXML private TextField startTime;
  @FXML private TextField endTime;

  @FXML private DatePicker datePicker;

  @FXML public JFXButton sendButton;
  @FXML public JFXButton clearButton;

  @FXML private JFXComboBox equipmentNeeded;
  @FXML private JFXComboBox requestState;
  @FXML private JFXComboBox requestAssignee;
  @FXML private JFXComboBox requestLocation;

  @FXML private JFXCheckBox completed;
  private boolean hasRun = false;

  @FXML
  @SneakyThrows
  public void initialize(URL location, ResourceBundle resources) {
    requestState.setItems(FXCollections.observableArrayList(ServiceRequestStatus.values()));
    equipmentNeeded.setItems(FXCollections.observableArrayList(EquipmentType.values()));
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

    requestLocation.setItems(FXCollections.observableArrayList(locationNames));
    requestAssignee.setItems(FXCollections.observableArrayList(employeeNames));
  }

  @FXML
  public void sendToDB() throws SQLException {
    String pName = patientName.getText();
    String roomNum = (String) requestLocation.getValue();
    String assignee = (String) requestAssignee.getValue();
    EquipmentType equipNeeded = EquipmentType.getValue(equipmentNeeded.getValue().toString());

    List<MedicalEquipmentServiceRequest> allSerReq =
        DBManager.getInstance().getMedicalEquipmentSRManager().getAll();
    for (MedicalEquipmentServiceRequest serviceReq : allSerReq) {
      System.out.println(serviceReq);
    }

    Employee employee = DBManager.getInstance().getEmployeeManager().getByAssignee(assignee);
    Location location = DBManager.getInstance().getLocationManager().getByName(roomNum);
    Equipment equipment =
        DBManager.getInstance().getEquipmentManager().getByAvailability(equipNeeded, false);

    MedicalEquipmentServiceRequest serviceRequest =
        new MedicalEquipmentServiceRequest(
            ServiceRequestStatus.OPEN,
            employee,
            location,
            new Date(0),
            new Date(new java.util.Date().getTime()),
            0,
            equipment,
            pName);
    DBManager.getInstance().getMedicalEquipmentSRManager().insert(serviceRequest);
  }

  @FXML
  private void clearText() {
    patientName.setText("");
    startTime.setText("");
    endTime.setText("");
    datePicker.setValue(null);
    datePicker.getEditor().clear();
    equipmentNeeded.valueProperty().set(null);
    requestAssignee.valueProperty().set(null);
    requestLocation.valueProperty().set(null);
    requestState.valueProperty().set(null);
    completed.setSelected(false);
  }
}

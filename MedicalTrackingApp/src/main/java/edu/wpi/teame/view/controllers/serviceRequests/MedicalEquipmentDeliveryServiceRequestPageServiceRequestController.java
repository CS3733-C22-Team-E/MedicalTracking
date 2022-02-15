package edu.wpi.teame.view.controllers.serviceRequests;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Equipment;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.EquipmentType;
import edu.wpi.teame.model.enums.ServiceRequestPriority;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import edu.wpi.teame.model.serviceRequests.MedicalEquipmentServiceRequest;
import edu.wpi.teame.view.controllers.AutoCompleteTextField;
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

public class MedicalEquipmentDeliveryServiceRequestPageServiceRequestController
    extends ServiceRequestController {
  @FXML private DatePicker requestDate;
  @FXML private AutoCompleteTextField locationText;
  @FXML private AutoCompleteTextField assignee;
  @FXML private AutoCompleteTextField equipment;
  @FXML private JFXComboBox priority;
  @FXML private JFXComboBox status;
  @FXML private TextArea additionalInfo;
  @FXML private TextArea patientName;
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

    equipment.setOnMousePressed(
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
    List<Equipment> equipments = DBManager.getInstance().getEquipmentManager().getAll();

    List<String> locationNames = new LinkedList<String>();
    for (Location loc : locations) {
      locationNames.add(loc.getLongName());
    }

    List<String> employeeNames = new LinkedList<String>();
    for (Employee emp : employees) {
      employeeNames.add(emp.getName());
    }

    List<String> equipmentNames = new LinkedList<String>();
    for (Equipment equ : equipments) {
      equipmentNames.add(equ.getName());
    }

    locationText.getEntries().addAll(locationNames);
    assignee.getEntries().addAll(employeeNames);
    equipment.getEntries().addAll(equipmentNames);
  }

  @FXML
  public void sendToDB() throws SQLException {
    String pName = patientName.getText();
    String roomNum = (String) locationText.getText();
    String emp = (String) assignee.getText();
    EquipmentType equipNeeded = EquipmentType.getValue(equipment.getText());

    List<MedicalEquipmentServiceRequest> allSerReq =
        DBManager.getInstance().getMedicalEquipmentSRManager().getAll();
    for (MedicalEquipmentServiceRequest serviceReq : allSerReq) {
      System.out.println(serviceReq);
    }

    Employee employee = DBManager.getInstance().getEmployeeManager().getByAssignee(emp);
    Location location = DBManager.getInstance().getLocationManager().getByName(roomNum);
    Equipment equipment =
        DBManager.getInstance().getEquipmentManager().getByAvailability(equipNeeded, false);

    MedicalEquipmentServiceRequest serviceRequest =
            new MedicalEquipmentServiceRequest(
                    (ServiceRequestPriority) priority.getValue(),
                    (ServiceRequestStatus) status.getValue(),
                    additionalInfo.getText(),
                    employee,
                    location,
                    Date.valueOf(requestDate.getValue()),
                    new Date(0),
                    new Date(new java.util.Date().getTime()),
                    "",
                    0,
                    equipment);
    DBManager.getInstance().getMedicalEquipmentSRManager().insert(serviceRequest);
  }

  public void validateSubmitButton() {
    submitButton.setDisable(
        requestDate.getValue() == null
            || locationText.getEntries() == null
            || assignee.getEntries() == null
            || priority.getValue() == null
            || status.getValue() == null
            || equipment.getEntries() == null);
  }

  public void clearText() {
    additionalInfo.setText("");
    locationText.setText("");
    assignee.setText("");
    equipment.setText("");
    requestDate.setValue(null);
    requestDate.getEditor().clear();
    priority.valueProperty().setValue(null);
    status.valueProperty().setValue(null);
  }
}

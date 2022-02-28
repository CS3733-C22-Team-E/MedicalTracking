package edu.wpi.teame.view.controllers.serviceRequests;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.objectManagers.EmployeeManager;
import edu.wpi.teame.db.objectManagers.EquipmentManager;
import edu.wpi.teame.db.objectManagers.LocationManager;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Equipment;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.ServiceRequestPriority;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import edu.wpi.teame.model.serviceRequests.MedicalEquipmentServiceRequest;
import edu.wpi.teame.view.controllers.AutoCompleteTextField;
import edu.wpi.teame.view.style.SRSentAnimation;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;

public class MedicalEquipmentDeliveryServiceRequestPageServiceRequestController
    extends ServiceRequestController {
  @FXML private AnchorPane mainAnchorPane;
  @FXML public DatePicker requestDate;
  @FXML public AutoCompleteTextField locationText;
  @FXML private AutoCompleteTextField assignee;
  @FXML public AutoCompleteTextField equipment;
  @FXML public JFXComboBox priority;
  @FXML public JFXComboBox status;
  @FXML private TextArea additionalInfo;
  @FXML private TextArea patientName;
  @FXML private Button clearButton;
  @FXML private Button submitButton;
  private boolean hasRun = false;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    mainAnchorPane.setEffect(new DropShadow(20, DataBaseObjectType.MedicalEquipmentSR.getColor()));
    priority.setItems(FXCollections.observableArrayList(ServiceRequestPriority.values()));
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
    List<Location> locations =
        DBManager.getInstance().getManager(DataBaseObjectType.Location).getAll();
    List<Employee> employees =
        DBManager.getInstance().getManager(DataBaseObjectType.Employee).getAll();
    List<Equipment> equipments =
        ((EquipmentManager) DBManager.getInstance().getManager(DataBaseObjectType.Equipment))
            .getByAllAvailable();

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
    String roomNum = (String) locationText.getText();
    String emp = (String) assignee.getText();

    Employee employee =
        ((EmployeeManager) DBManager.getInstance().getManager(DataBaseObjectType.Employee))
            .getByAssignee(emp);
    Location location =
        ((LocationManager) DBManager.getInstance().getManager(DataBaseObjectType.Location))
            .getByName(roomNum);
    Equipment equipmentNeeded =
        ((EquipmentManager) DBManager.getInstance().getManager(DataBaseObjectType.Equipment))
            .getByName(equipment.getText());

    MedicalEquipmentServiceRequest serviceRequest =
        new MedicalEquipmentServiceRequest(
            ServiceRequestPriority.valueOf(priority.getValue().toString()),
            ServiceRequestStatus.valueOf(status.getValue().toString()),
            additionalInfo.getText(),
            employee,
            location,
            new Date(
                Date.from(requestDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())
                    .getTime()),
            new Date(0),
            new Date(new java.util.Date().getTime()),
            "",
            0,
            equipmentNeeded);
    DBManager.getInstance()
        .getManager(DataBaseObjectType.MedicalEquipmentSR)
        .insert(serviceRequest);
    SRSentAnimation a = new SRSentAnimation();
    a.getStackPane().setLayoutX(mainAnchorPane.getWidth() / 2 - 50);
    a.getStackPane().setLayoutY(submitButton.getLayoutY());
    mainAnchorPane.getChildren().add(a.getStackPane());
    a.play();
    clearText();
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

  @Override
  public void updateStyle() {}
}

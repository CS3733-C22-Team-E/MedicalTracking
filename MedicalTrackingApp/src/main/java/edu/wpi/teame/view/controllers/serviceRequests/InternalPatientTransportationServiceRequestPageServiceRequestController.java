package edu.wpi.teame.view.controllers.serviceRequests;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.objectManagers.EmployeeManager;
import edu.wpi.teame.db.objectManagers.EquipmentManager;
import edu.wpi.teame.db.objectManagers.LocationManager;
import edu.wpi.teame.db.objectManagers.PatientManager;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Equipment;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.Patient;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.ServiceRequestPriority;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import edu.wpi.teame.model.serviceRequests.PatientTransportationServiceRequest;
import edu.wpi.teame.view.animations.SRSentAnimation;
import edu.wpi.teame.view.controllers.AutoCompleteTextField;
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

public class InternalPatientTransportationServiceRequestPageServiceRequestController
    extends ServiceRequestController {
  @FXML private AnchorPane mainAnchorPane;
  @FXML private DatePicker requestDate;
  @FXML private AutoCompleteTextField patientName;
  @FXML private AutoCompleteTextField locationText;
  @FXML private AutoCompleteTextField destinationLocation;
  @FXML private AutoCompleteTextField assignee;
  @FXML private AutoCompleteTextField equipment;
  @FXML private JFXComboBox priority;
  @FXML private JFXComboBox status;
  @FXML private TextArea additionalInfo;
  @FXML private Button clearButton;
  @FXML private Button submitButton;
  private boolean hasRun = false;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    mainAnchorPane.setEffect(
        new DropShadow(20, DataBaseObjectType.InternalPatientTransferSR.getColor()));
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

    //    equipment.setOnMousePressed(
    //        listener -> {
    //          validateSubmitButton();
    //        });

    destinationLocation.setOnMousePressed(
        listener -> {
          validateSubmitButton();
        });

    patientName
        .onActionProperty()
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
    List<Location> locations = DBManager.getManager(DataBaseObjectType.Location).getAll();
    List<Employee> employees = DBManager.getManager(DataBaseObjectType.Employee).getAll();
    List<Equipment> equipments =
        ((EquipmentManager) DBManager.getManager(DataBaseObjectType.Equipment)).getByAllAvailable();

    List<Patient> patients = DBManager.getManager(DataBaseObjectType.Patient).getAll();
    List<String> patientNames = new LinkedList<>();
    for (Patient p : patients) {
      patientNames.add(p.getName());
    }
    patientName.getEntries().addAll(patientNames);

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
    destinationLocation.getEntries().addAll(locationNames);
    assignee.getEntries().addAll(employeeNames);
    equipment.getEntries().addAll(equipmentNames);
  }

  @FXML
  void sendToDB() throws SQLException {
    Employee employee =
        ((EmployeeManager) DBManager.getManager(DataBaseObjectType.Employee))
            .getByAssignee(assignee.getText());
    Location location =
        ((LocationManager) DBManager.getManager(DataBaseObjectType.Location))
            .getByName(locationText.getText());
    Location dest =
        ((LocationManager) DBManager.getManager(DataBaseObjectType.Location))
            .getByName(destinationLocation.getText());
    Equipment equipBring =
        ((EquipmentManager) DBManager.getManager(DataBaseObjectType.Equipment))
            .getByName(equipment.getText());
    Patient patient =
        ((PatientManager) DBManager.getManager(DataBaseObjectType.Patient))
            .getByName(patientName.getText());

    PatientTransportationServiceRequest serviceRequest =
        new PatientTransportationServiceRequest(
            true,
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
            dest,
            equipBring,
            patient);
    DBManager.getManager(DataBaseObjectType.InternalPatientTransferSR).insert(serviceRequest);
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
            || destinationLocation.getEntries() == null
            || patientName.getText().isEmpty());
  }

  public void clearText() {
    additionalInfo.setText("");
    patientName.setText("");
    locationText.setText("");
    assignee.setText("");
    destinationLocation.setText("");
    equipment.setText("");
    requestDate.setValue(null);
    requestDate.getEditor().clear();
    priority.valueProperty().setValue(null);
    status.valueProperty().setValue(null);
  }
}

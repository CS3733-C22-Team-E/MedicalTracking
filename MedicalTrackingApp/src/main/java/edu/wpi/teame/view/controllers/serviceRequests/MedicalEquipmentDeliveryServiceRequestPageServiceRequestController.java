package edu.wpi.teame.view.controllers.serviceRequests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teame.db.*;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Equipment;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.EquipmentType;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import edu.wpi.teame.model.serviceRequests.MedicalEquipmentServiceRequest;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class MedicalEquipmentDeliveryServiceRequestPageServiceRequestController
    extends ServiceRequestController {
  @FXML private JFXComboBox equipmentNeeded;
  @FXML private JFXComboBox requestState;

  @FXML private TextField patientName;
  @FXML private TextField startTime;
  @FXML private TextField endTime;
  @FXML private TextField nameAssigned;
  @FXML private TextField floor;

  @FXML private DatePicker datePicker;

  @FXML public JFXButton sendButton;
  @FXML public JFXButton clearButton;

  @FXML private JFXComboBox equipmentNeeded;
  @FXML private JFXComboBox requestState;
  @FXML private JFXComboBox roomNumber;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // creates a linkedList of locations and sets all the values as one of roomNumber comboBox items
    List<Location> locations = null;
    try {
      locations = DBManager.getInstance().getLocationManager().getAll();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    LinkedList<String> locationName = new LinkedList<String>();
    for (Location loc : locations) {
      locationName.add(loc.getLongName());
    }

    // Set the comboBox items
    roomNumber.setItems(FXCollections.observableArrayList(locationName));
    equipmentNeeded.setItems(
        FXCollections.observableArrayList("Bed", "X-Ray", "Infusion Pump", "Recliner"));
    requestState.setItems(
        FXCollections.observableArrayList("Open", "Waiting For Equipment", "Cancelled", "Done"));
  }

  // todo either make the submit button disabled until everything is filled or add error handling
  public void sendToMEDB(MouseEvent mouseEvent) throws SQLException {
    // store all the values from the fields
    String pName = patientName.getText();
    String startingTime = startTime.getText();
    String endingTime = endTime.getText();
    String assignee = nameAssigned.getText();
    LocalDate srDate = datePicker.getValue();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddYYYY");
    String serviceRequestDate = srDate.format(formatter);
    String MRSRStatus = (String) requestState.getValue();
    EquipmentType equipmentType = comboBoxValToType((String) equipmentNeeded.getValue());
    String locationName = (String) roomNumber.getValue();

    // calls a function that returns an equipment object in the database
    Equipment availableEquipment =
        DBManager.getInstance().getEquipmentManager().getByAvailability(equipmentType, true);

    Location location = DBManager.getInstance().getLocationManager().getByName(locationName);

    Employee employee = DBManager.getInstance().getEmployeeManager().getByAssignee(assignee);

    MedicalEquipmentServiceRequest serReq =
        new MedicalEquipmentServiceRequest(
            ServiceRequestStatus.OPEN,
            employee,
            location,
            null,
            null,
            -1,
            availableEquipment,
            pName);

    DBManager.getInstance().getMedicalEquipmentSRManager().insert(serReq);

    List<MedicalEquipmentServiceRequest> allSerReq =
        DBManager.getInstance().getMedicalEquipmentSRManager().getAll();
    for (MedicalEquipmentServiceRequest serviceReq : allSerReq) {
      System.out.println(serviceReq);
    }
    System.out.println(availableEquipment);
  }

  private EquipmentType comboBoxValToType(String val) {
    switch (val) {
      case "Bed":
        return EquipmentType.PBED;
      case "X-Ray":
        return EquipmentType.XRAY;
      case "Infusion Pump":
        return EquipmentType.PUMP;
      case "Recliner":
        return EquipmentType.RECL;
      default:
        return null;
    }
  }

  // "Open", "Waiting For Equipment", "Cancelled", "Done"
  private ServiceRequestStatus comboBoxValToStatus(String val) {
    switch (val) {
      case "Open":
        return ServiceRequestStatus.OPEN;
      case "Waiting For Equipment":
        return ServiceRequestStatus.PENDING;
      case "Cancelled":
        return ServiceRequestStatus.CANCELLED;
      case "Done":
        return ServiceRequestStatus.CLOSED;
      default:
        return null;
    }
  }

  @FXML
  private void clearText() {
    patientName.setText("");
    nameAssigned.setText("");
    startTime.setText("");
    endTime.setText("");
    floor.setText("");
    datePicker.setValue(null);
    datePicker.getEditor().clear();
    equipmentNeeded.valueProperty().set(null);
    roomNumber.valueProperty().set(null);
    requestState.valueProperty().set(null);
  }
}

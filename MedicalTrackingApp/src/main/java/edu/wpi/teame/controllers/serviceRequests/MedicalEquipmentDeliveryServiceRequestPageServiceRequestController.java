package edu.wpi.teame.controllers.serviceRequests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teame.db.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class MedicalEquipmentDeliveryServiceRequestPageServiceRequestController
    extends ServiceRequestController {

  @FXML private TextField patientName;
  @FXML private JFXComboBox roomNumber;
  @FXML private TextField startTime;
  @FXML private TextField endTime;
  @FXML private TextField nameAssigned;
  @FXML private TextField floor;

  @FXML private DatePicker datePicker;

  @FXML public JFXButton sendButton;
  @FXML public JFXButton clearButton;

  @FXML private JFXComboBox equipmentNeeded;
  @FXML private JFXComboBox requestState;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // creates a linkedList of locations and sets all the values as one of roomNumber comboBox items
    LinkedList<Location> locations = DBManager.getInstance().getLocationManager().getAll();
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
  public void sendToMEDB(MouseEvent mouseEvent) {
    // store all the values from the fields
    String pName = patientName.getText();
    String startingTime = startTime.getText();
    String endingTime = endTime.getText();
    String assignee = nameAssigned.getText();
    LocalDate srDate = datePicker.getValue();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddYYYY");
    String serviceRequestDate = srDate.format(formatter);
    String MRSRStatus = (String) requestState.getValue();
    String equipNeeded = (String) equipmentNeeded.getValue();
    String roomNum = (String) roomNumber.getValue();

    // prints them to see what they are
    System.out.println(pName);
    System.out.println(roomNum);
    System.out.println(startingTime);
    System.out.println(endingTime);
    System.out.println(assignee);
    System.out.println(serviceRequestDate);
    System.out.println(MRSRStatus);
    System.out.println(equipNeeded);
    System.out.println(
        DBManager.getInstance().getLocationManager().getFromLongName(roomNum).getId());

    // calls a function that returns an equipment object in the database
    Equipment availableEquipment =
        DBManager.getInstance().getEquipmentManager().getAvailable(comboBoxValToType(equipNeeded));

    System.out.println(availableEquipment);

    MedicalEquipmentServiceRequest serReq =
        new MedicalEquipmentServiceRequest(
            0,
            pName,
            DBManager.getInstance().getLocationManager().getFromLongName(roomNum),
            startingTime,
            endingTime,
            serviceRequestDate,
            assignee,
            availableEquipment,
            comboBoxValToStatus(MRSRStatus));

    DBManager.getInstance().getMEServiceRequestManager().insert(serReq);

    LinkedList<MedicalEquipmentServiceRequest> allSerReq =
        DBManager.getInstance().getMEServiceRequestManager().getAll();
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
        return EquipmentType.PBED;
      case "Recliner":
        return EquipmentType.RECL;
      default:
        return null;
    }
  }

  // "Open", "Waiting For Equipment", "Cancelled", "Done"
  private MedicalEquipmentServiceRequestStatus comboBoxValToStatus(String val) {
    switch (val) {
      case "Open":
        return MedicalEquipmentServiceRequestStatus.OPEN;
      case "Waiting For Equipment":
        return MedicalEquipmentServiceRequestStatus.WAITING;
      case "Cancelled":
        return MedicalEquipmentServiceRequestStatus.CANCELLED;
      case "Done":
        return MedicalEquipmentServiceRequestStatus.DONE;
      default:
        return null;
    }
  }

  @FXML
  private void clearText() {
    patientName.setText("");
    roomNumber.setText("");
    nameAssigned.setText("");
    startTime.setText("");
    endTime.setText("");
    floor.setText("");
    datePicker.setValue(null);
    datePicker.getEditor().clear();
    equipmentNeeded.valueProperty().set(null);
    requestState.valueProperty().set(null);
  }
}

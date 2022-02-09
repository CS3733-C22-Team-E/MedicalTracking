package edu.wpi.teame.view.controllers.serviceRequests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teame.db.*;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.EquipmentType;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import edu.wpi.teame.model.serviceRequests.MedicalEquipmentServiceRequest;
import java.net.URL;
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
import lombok.SneakyThrows;

public class MedicalEquipmentDeliveryServiceRequestPageServiceRequestController
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

  @Override
  @SneakyThrows
  public void initialize(URL location, ResourceBundle resources) {
    // creates a linkedList of locations and sets all the values as one of roomNumber comboBox items
    List<Location> locations = DBManager.getInstance().getLocationManager().getAll();
    List<String> locationName = new LinkedList<String>();
    for (Location loc : locations) {
      locationName.add(loc.getLongName());
    }

    // Set the comboBox items
    requestLocation.setItems(FXCollections.observableArrayList(locationName));
    equipmentNeeded.setItems(
        FXCollections.observableArrayList("Bed", "X-Ray", "Infusion Pump", "Recliner"));
    requestState.setItems(
        FXCollections.observableArrayList("Open", "Waiting For Equipment", "Cancelled", "Done"));
    requestAssignee.setItems(
        FXCollections.observableArrayList("Test Name", "Test Name", "Test Name", "Test Name"));
  }

  // todo either make the submit button disabled until everything is filled or add error handling
  @SneakyThrows
  public void sendToMEDB(MouseEvent mouseEvent) {
    // store all the values from the fields
    String pName = patientName.getText();
    String startingTime = startTime.getText();
    String endingTime = endTime.getText();
    LocalDate srDate = datePicker.getValue();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
    String serviceRequestDate = srDate.format(formatter);
    String MRSRStatus = (String) requestState.getValue();
    String equipNeeded = (String) equipmentNeeded.getValue();
    String roomNum = (String) requestLocation.getValue();
    String assignee = (String) requestAssignee.getValue();

    // DBManager.getInstance().getMedicalEquipmentSRManager().insert(serReq);

    List<MedicalEquipmentServiceRequest> allSerReq =
        DBManager.getInstance().getMedicalEquipmentSRManager().getAll();
    for (MedicalEquipmentServiceRequest serviceReq : allSerReq) {
      System.out.println(serviceReq);
    }
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

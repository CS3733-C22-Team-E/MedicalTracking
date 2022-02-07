package edu.wpi.teame.controllers.serviceRequests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.Location;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.ResourceBundle;

import edu.wpi.teame.db.MedicalEquipmentServiceRequest;
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

  @FXML private DatePicker datePicker;

  @FXML public JFXButton sendButton;
  @FXML public JFXButton clearButton;

  @FXML private JFXComboBox equipmentNeeded;
  @FXML private JFXComboBox requestState;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    LinkedList<Location> locations = DBManager.getInstance().getLocationManager().getAll();
    LinkedList<String> locationName = new LinkedList<String>();
    for (Location loc : locations) {
      locationName.add(loc.getLongName());
    }
    System.out.println(locations.size());

    roomNumber.setItems(FXCollections.observableArrayList(locationName));
    equipmentNeeded.setItems(
        FXCollections.observableArrayList("Bed", "X-Ray", "Infusion Pump", "Recliner"));
    requestState.setItems(
        FXCollections.observableArrayList("Waiting For Equipment", "Cancelled", "Done"));
  }

  public void sendToMEDB(MouseEvent mouseEvent) {
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

    System.out.println(pName);
    System.out.println(roomNum);
    System.out.println(startingTime);
    System.out.println(endingTime);
    System.out.println(assignee);
    System.out.println(serviceRequestDate);
    System.out.println(MRSRStatus);
    System.out.println(equipNeeded);

    System.out.println(DBManager.getInstance().getLocationManager().getFromLongName(roomNum).getId());
    MedicalEquipmentServiceRequest serReq = new MedicalEquipmentServiceRequest(null, pName, DBManager.getInstance().getLocationManager().getFromLongName(roomNum), startingTime, endingTime, srDate, assignee, );
    DBManager.getInstance().getMEServiceRequestManager().insert(serReq);
  }
}

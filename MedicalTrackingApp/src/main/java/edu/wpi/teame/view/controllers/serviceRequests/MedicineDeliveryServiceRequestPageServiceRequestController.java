package edu.wpi.teame.view.controllers.serviceRequests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.serviceRequests.MedicalEquipmentServiceRequest;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import lombok.SneakyThrows;

public class MedicineDeliveryServiceRequestPageServiceRequestController
    extends ServiceRequestController {

  @FXML public JFXButton sendButton;
  @FXML public JFXButton clearButton;

  @FXML private TextField patientName;
  @FXML private TextField medicineRequested;
  @FXML private TextField timeNeeded;

  @FXML private DatePicker datePicker;

  @FXML public JFXCheckBox completed;

  @FXML public JFXComboBox serviceLocation;
  @FXML public JFXComboBox serviceAssignee;
  private boolean hasRun = false;

  @FXML
  @SneakyThrows
  public void initialize(URL location, ResourceBundle resources) {}

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

    serviceLocation.setItems(FXCollections.observableArrayList(locationNames));
    serviceAssignee.setItems(FXCollections.observableArrayList(employeeNames));
  }

  @FXML
  public void sendToDB() throws SQLException, ParseException {
    String roomNum = (String) serviceLocation.getValue();
    String assignee = (String) serviceAssignee.getValue();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String srDate = datePicker.getValue().format(formatter);
    long time = new SimpleDateFormat("yyyy-MM-dd").parse(srDate).getTime();

    List<MedicalEquipmentServiceRequest> allSerReq =
        DBManager.getInstance().getMedicalEquipmentSRManager().getAll();
    for (MedicalEquipmentServiceRequest serviceReq : allSerReq) {
      System.out.println(serviceReq);
    }

    Employee employee = DBManager.getInstance().getEmployeeManager().getByAssignee(assignee);
    Location location = DBManager.getInstance().getLocationManager().getByName(roomNum);

    //    MedicineDeliveryServiceRequest serviceRequest =
    //        new MedicineDeliveryServiceRequest(
    //            ServiceRequestStatus.OPEN,
    //            employee,
    //            location,
    //            new Date(0),
    //            new Date(new java.util.Date().getTime()),
    //            0,
    //            new Date(time));
    //    DBManager.getInstance().getMedicineDeliverySRManager().insert(serviceRequest);
  }

  @FXML
  private void clearText() {
    patientName.setText("");
    medicineRequested.setText("");
    timeNeeded.setText("");
    datePicker.setValue(null);
    datePicker.getEditor().clear();
    serviceLocation.valueProperty().set(null);
    serviceAssignee.valueProperty().set(null);
    completed.setSelected(false);
  }
}

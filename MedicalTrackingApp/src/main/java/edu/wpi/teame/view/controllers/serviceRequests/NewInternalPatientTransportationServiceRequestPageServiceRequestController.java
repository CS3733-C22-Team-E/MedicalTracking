package edu.wpi.teame.view.controllers.serviceRequests;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teame.model.enums.EquipmentType;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NewInternalPatientTransportationServiceRequestPageServiceRequestController
    extends ServiceRequestController {
  @FXML private TextField patientName;
  @FXML private TextArea additionalInfo;
  @FXML private JFXComboBox priorityDropdown;
  @FXML private JFXComboBox statusDropdown;
  @FXML private JFXComboBox equipmentDropdown;
  @FXML private JFXComboBox locationDropdown;
  @FXML private JFXComboBox destinationDropdown;
  @FXML private JFXComboBox assigneeDropdown;
  @FXML private DatePicker requestDate;
  @FXML public Button clearButton;
  @FXML public Button submitButton;
  private boolean hasRun = false;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    priorityDropdown.setItems(
        FXCollections.observableArrayList(new String[] {"Low", "Medium", "High"}));
    statusDropdown.setItems(FXCollections.observableArrayList(ServiceRequestStatus.values()));
    equipmentDropdown.setItems(FXCollections.observableArrayList(EquipmentType.values()));

    locationDropdown.setItems(FXCollections.observableArrayList(new String[] {"test"}));
    destinationDropdown.setItems(FXCollections.observableArrayList(new String[] {"test"}));
    assigneeDropdown.setItems(FXCollections.observableArrayList(new String[] {"test"}));

    requestDate
        .valueProperty()
        .addListener(
            (listener) -> {
              validateSubmitButton();
            });

    locationDropdown
        .valueProperty()
        .addListener(
            listener -> {
              validateSubmitButton();
            });

    equipmentDropdown
        .valueProperty()
        .addListener(
            listener -> {
              validateSubmitButton();
            });

    destinationDropdown
        .valueProperty()
        .addListener(
            listener -> {
              validateSubmitButton();
            });

    assigneeDropdown
        .valueProperty()
        .addListener(
            listener -> {
              validateSubmitButton();
            });

    priorityDropdown
        .valueProperty()
        .addListener(
            listener -> {
              validateSubmitButton();
            });

    statusDropdown
        .valueProperty()
        .addListener(
            listener -> {
              validateSubmitButton();
            });
  }

  //  @FXML
  //  public void updateFromDB() throws SQLException {
  //    if (hasRun) {
  //      return;
  //    }
  //    hasRun = true;
  //
  //    // creates a linkedList of locations and sets all the values as one of roomNumber comboBox
  // items
  //    List<Location> locations = DBManager.getInstance().getLocationManager().getAll();
  //    List<Employee> employees = DBManager.getInstance().getEmployeeManager().getAll();
  //
  //    List<String> locationNames = new LinkedList<String>();
  //    for (Location loc : locations) {
  //      locationNames.add(loc.getLongName());
  //    }
  //
  //    List<String> employeeNames = new LinkedList<String>();
  //    for (Employee emp : employees) {
  //      employeeNames.add(emp.getName());
  //    }
  //
  //    locationDropdown.setItems(FXCollections.observableArrayList(locationNames));
  //    destinationDropdown.setItems(FXCollections.observableArrayList(locationNames));
  //    assigneeDropdown.setItems(FXCollections.observableArrayList(employeeNames));
  //  }

  @FXML
  private void clearText() {
    patientName.setText("");
    additionalInfo.setText("");
    requestDate.setValue(null);
    requestDate.getEditor().clear();
    priorityDropdown.valueProperty().set(null);
    statusDropdown.valueProperty().set(null);
    equipmentDropdown.valueProperty().set(null);
    locationDropdown.valueProperty().set(null);
    destinationDropdown.valueProperty().set(null);
    assigneeDropdown.valueProperty().set(null);
  }

  public void validateSubmitButton() {
    submitButton.setDisable(
        patientName.getText().isEmpty()
            || requestDate.getValue() == null
            || equipmentDropdown.getValue() == null
            || locationDropdown.getValue() == null
            || destinationDropdown.getValue() == null
            || assigneeDropdown.getValue() == null
            || priorityDropdown.getValue() == null
            || statusDropdown.getValue() == null);
  }
}

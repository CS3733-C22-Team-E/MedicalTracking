package edu.wpi.teame.view.controllers.serviceRequests;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.Patient;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.LanguageType;
import edu.wpi.teame.model.enums.ServiceRequestPriority;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import edu.wpi.teame.model.serviceRequests.LanguageInterpreterServiceRequest;
import edu.wpi.teame.view.SRSentAnimation;
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

public class LanguageInterpreterServiceRequestPageServiceRequestController
    extends ServiceRequestController {
  @FXML private AnchorPane mainAnchorPane;
  @FXML private DatePicker requestDate;
  @FXML private AutoCompleteTextField locationText;
  @FXML private AutoCompleteTextField assignee;
  @FXML private AutoCompleteTextField patientName;
  @FXML private JFXComboBox language;
  @FXML private JFXComboBox priority;
  @FXML private JFXComboBox status;
  @FXML private TextArea additionalInfo;
  @FXML private Button clearButton;
  @FXML private Button submitButton;
  private boolean hasRun = false;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    mainAnchorPane.setEffect(
        new DropShadow(20, DataBaseObjectType.LanguageInterpreterSR.getColor()));
    priority.setItems(FXCollections.observableArrayList(ServiceRequestPriority.values()));
    status.setItems(FXCollections.observableArrayList(ServiceRequestStatus.values()));
    language.setItems(FXCollections.observableArrayList(LanguageType.values()));

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

    patientName
        .onActionProperty()
        .addListener(
            listener -> {
              validateSubmitButton();
            });

    language
        .valueProperty()
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
    List<Location> locations = DBManager.getInstance().getLocationManager().getAll();
    List<Employee> employees = DBManager.getInstance().getEmployeeManager().getAll();

    List<Patient> patients = DBManager.getInstance().getPatientManager().getAll();
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

    locationText.getEntries().addAll(locationNames);
    assignee.getEntries().addAll(employeeNames);
  }

  @FXML
  void sendToDB() throws SQLException {
    Employee employee =
        DBManager.getInstance().getEmployeeManager().getByAssignee(assignee.getText());
    Location location =
        DBManager.getInstance().getLocationManager().getByName(locationText.getText());
    Patient patient = DBManager.getInstance().getPatientManager().getByName(patientName.getText());

    LanguageInterpreterServiceRequest serviceRequest =
        new LanguageInterpreterServiceRequest(
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
            (LanguageType) language.getValue(),
            patient);
    DBManager.getInstance().getLanguageSRManager().insert(serviceRequest);
    SRSentAnimation a = new SRSentAnimation();
    a.getStackPane().setLayoutX(mainAnchorPane.getWidth() / 2 - 50);
    a.getStackPane().setLayoutY(submitButton.getLayoutY());
    mainAnchorPane.getChildren().add(a.getStackPane());
    a.play();
  }

  public void validateSubmitButton() {
    submitButton.setDisable(
        requestDate.getValue() == null
            || locationText.getEntries() == null
            || assignee.getEntries() == null
            || priority.getValue() == null
            || status.getValue() == null
            || patientName.getText().isEmpty()
            || language.getValue() == null);
  }

  public void clearText() {
    additionalInfo.setText("");
    locationText.setText("");
    assignee.setText("");
    requestDate.setValue(null);
    requestDate.getEditor().clear();
    priority.valueProperty().setValue(null);
    status.valueProperty().setValue(null);
    patientName.setText("");
    language.valueProperty().setValue(null);
  }
}

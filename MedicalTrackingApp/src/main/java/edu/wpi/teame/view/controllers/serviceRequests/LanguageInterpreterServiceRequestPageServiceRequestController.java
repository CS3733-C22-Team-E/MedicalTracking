package edu.wpi.teame.view.controllers.serviceRequests;

import static com.mongodb.client.model.Sorts.descending;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.objectManagers.EmployeeManager;
import edu.wpi.teame.db.objectManagers.LocationManager;
import edu.wpi.teame.db.objectManagers.PatientManager;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.Patient;
import edu.wpi.teame.model.enums.*;
import edu.wpi.teame.model.serviceRequests.LanguageInterpreterServiceRequest;
import edu.wpi.teame.view.controllers.AutoCompleteTextField;
import edu.wpi.teame.view.style.SRSentAnimation;
import edu.wpi.teame.view.style.StyleManager;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class LanguageInterpreterServiceRequestPageServiceRequestController
    extends ServiceRequestController {
  @FXML private AnchorPane mainAnchorPane;
  @FXML private GridPane gridPane;
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
  @FXML private Label title;
  @FXML private Label locationT;
  @FXML private Label assigneeT;
  @FXML private Label priorityT;
  @FXML private Label statusT;
  @FXML private Label additionalInfoT;
  @FXML private Label requestDateT;
  @FXML private Label patientNameT;
  @FXML private Label languageT;
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

    StyleManager.getInstance().subscribe(this);

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

    StyleManager.getInstance().subscribe(this);
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

    List<Patient> patients =
        DBManager.getInstance().getManager(DataBaseObjectType.Patient).getAll();
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
        ((EmployeeManager) DBManager.getInstance().getManager(DataBaseObjectType.Employee))
            .getByAssignee(assignee.getText());
    Location location =
        ((LocationManager) DBManager.getInstance().getManager(DataBaseObjectType.Location))
            .getByName(locationText.getText());
    Patient patient =
        ((PatientManager) DBManager.getInstance().getManager(DataBaseObjectType.Patient))
            .getByName(patientName.getText());

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

    if (DBManager.getInstance().getCurrentType() == DBType.MongoDB) {
      LanguageInterpreterServiceRequest serviceRequest1 =
          DBManager.getInstance()
              .getMongoDatabase()
              .withCodecRegistry(DBManager.getInstance().getObjectCodecs())
              .getCollection("LanguageInterpreterSR", LanguageInterpreterServiceRequest.class)
              .find()
              .sort(descending("_id"))
              .first();
      int lastIntID = serviceRequest1 == null ? 1 : serviceRequest1.getId() + 1;
      serviceRequest.setId(lastIntID);
      DBManager.getInstance()
          .getManager(DataBaseObjectType.LanguageInterpreterSR)
          .insert(serviceRequest);

    } else {
      DBManager.getInstance()
          .getManager(DataBaseObjectType.LanguageInterpreterSR)
          .insert(serviceRequest);
    }
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

  @Override
  public void updateStyle() {
    StyleManager.getInstance().getCurrentStyle().setPaneStyle(mainAnchorPane, true);
    StyleManager.getInstance().getCurrentStyle().setPaneStyle(gridPane, true);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(additionalInfoT);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(requestDateT);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(patientNameT);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(locationT);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(assigneeT);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(priorityT);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(languageT);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(statusT);

    StyleManager.getInstance().getCurrentStyle().setAutoCompleteTextBoxStyle(locationText);
    StyleManager.getInstance().getCurrentStyle().setAutoCompleteTextBoxStyle(patientName);
    StyleManager.getInstance().getCurrentStyle().setAutoCompleteTextBoxStyle(assignee);
    StyleManager.getInstance().getCurrentStyle().setDatePickerStyle(requestDate);
    StyleManager.getInstance().getCurrentStyle().setComboBoxStyle(language);

    StyleManager.getInstance().getCurrentStyle().setTextAreaStyle(additionalInfo);
    StyleManager.getInstance().getCurrentStyle().setButtonStyle(submitButton);
    StyleManager.getInstance().getCurrentStyle().setButtonStyle(clearButton);
    StyleManager.getInstance().getCurrentStyle().setComboBoxStyle(priority);
    StyleManager.getInstance().getCurrentStyle().setComboBoxStyle(status);
    StyleManager.getInstance().getCurrentStyle().setHeaderStyle(title);
  }
}

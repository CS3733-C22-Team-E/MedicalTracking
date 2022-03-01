package edu.wpi.teame.view.controllers.serviceRequests;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.objectManagers.EmployeeManager;
import edu.wpi.teame.db.objectManagers.LocationManager;
import edu.wpi.teame.db.objectManagers.PatientManager;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.Patient;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.ServiceRequestPriority;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import edu.wpi.teame.model.serviceRequests.FoodDeliveryServiceRequest;
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
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class FoodDeliveryServiceRequestPageServiceRequestController
    extends ServiceRequestController {

  @FXML private AnchorPane mainAnchorPane;
  @FXML private GridPane gridPane;
  @FXML private DatePicker requestDate;
  @FXML private AutoCompleteTextField locationText;
  @FXML private AutoCompleteTextField assignee;
  @FXML private AutoCompleteTextField patientName;
  @FXML private TextField food;
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
  @FXML private Label patientNameT;
  @FXML private Label additionalInfoT;
  @FXML private Label requestDateT;

  private boolean hasRun = false;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    mainAnchorPane.setEffect(new DropShadow(20, DataBaseObjectType.FoodDeliverySR.getColor()));
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

    patientName
        .onActionProperty()
        .addListener(
            listener -> {
              validateSubmitButton();
            });

    food.onActionProperty()
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

    FoodDeliveryServiceRequest serviceRequest =
        new FoodDeliveryServiceRequest(
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
            patient,
            food.getText());
    DBManager.getInstance().getManager(DataBaseObjectType.FoodDeliverySR).insert(serviceRequest);
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
            || food.getText().isEmpty());
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
    food.setText("");
  }

  @Override
  public void updateStyle() {
    locationT.setTextFill(StyleManager.getInstance().getCurrentStyle().getTextColor());
    assigneeT.setTextFill(StyleManager.getInstance().getCurrentStyle().getTextColor());
    priorityT.setTextFill(StyleManager.getInstance().getCurrentStyle().getTextColor());
    statusT.setTextFill(StyleManager.getInstance().getCurrentStyle().getTextColor());
    requestDateT.setTextFill(StyleManager.getInstance().getCurrentStyle().getTextColor());
    additionalInfoT.setTextFill(StyleManager.getInstance().getCurrentStyle().getTextColor());
    patientNameT.setTextFill(StyleManager.getInstance().getCurrentStyle().getTextColor());
    title.setTextFill(StyleManager.getInstance().getCurrentStyle().getTextColor());

    gridPane.setBackground(StyleManager.getInstance().getCurrentStyle().getSecondaryBackground());
    mainAnchorPane.setBackground(
        StyleManager.getInstance().getCurrentStyle().getSecondaryBackground());

    requestDate.setBackground(
        StyleManager.getInstance().getCurrentStyle().getForegroundAsBackground());
    locationText.setBackground(
        StyleManager.getInstance().getCurrentStyle().getForegroundAsBackground());
    assignee.setBackground(
        StyleManager.getInstance().getCurrentStyle().getForegroundAsBackground());
    patientName.setBackground(
        StyleManager.getInstance().getCurrentStyle().getForegroundAsBackground());
    additionalInfo.setBackground(
        StyleManager.getInstance().getCurrentStyle().getForegroundAsBackground());
    priority.setBackground(
        StyleManager.getInstance().getCurrentStyle().getForegroundAsBackground());
    status.setBackground(StyleManager.getInstance().getCurrentStyle().getForegroundAsBackground());

    submitButton.setBackground(
        StyleManager.getInstance().getCurrentStyle().getForegroundAsBackground());
    submitButton.setTextFill(StyleManager.getInstance().getCurrentStyle().getTextColor());
    clearButton.setTextFill(StyleManager.getInstance().getCurrentStyle().getTextColor());
    clearButton.setBackground(
        StyleManager.getInstance().getCurrentStyle().getForegroundAsBackground());
  }
}

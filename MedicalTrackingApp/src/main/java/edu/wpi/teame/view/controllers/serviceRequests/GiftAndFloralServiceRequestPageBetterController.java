package edu.wpi.teame.view.controllers.serviceRequests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import lombok.SneakyThrows;

import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class GiftAndFloralServiceRequestPageBetterController
        extends ServiceRequestController{

    @FXML public JFXButton sendButton;
    @FXML public JFXButton clearButton;

    @FXML public JFXComboBox patientComboBox;
    @FXML public JFXComboBox destinationComboBox;
    @FXML public JFXComboBox assigneeComboBox;
    @FXML public JFXComboBox priorityComboBox;
    @FXML public JFXComboBox statusComboBox;

    @FXML public JFXDatePicker requestDateDatePicker;

    @FXML public TextArea additionalInfoTextArea;
    private boolean hasRun = false;

    @FXML
    @SneakyThrows
    public void initialize(URL location, ResourceBundle resources) {}

    @FXML
    public void updateFromDB() throws SQLException {
        if (hasRun){
            return;
        }
        hasRun = true;

        // creates a linkedList of locations and sets all the values as one of roomNumber comboBox items
        java.util.List<Location> locations = DBManager.getInstance().getLocationManager().getAll();
        List<Employee> employees = DBManager.getInstance().getEmployeeManager().getAll();

        List<String> locationNames = new LinkedList<String>();
        for (Location loc : locations) {
            locationNames.add(loc.getLongName());
        }

        List<String> employeeNames = new LinkedList<String>();
        for (Employee emp : employees) {
            employeeNames.add(emp.getName());
        }

        destinationComboBox.setItems(FXCollections.observableArrayList(locationNames));
        assigneeComboBox.setItems(FXCollections.observableArrayList(employeeNames));
    }

    @FXML
    public void sendToDB() throws SQLException, ParseException {
        Employee employee =
                DBManager.getInstance().getEmployeeManager().getByAssignee(assigneeComboBox.getAccessibleText());
        Location location =
                DBManager.getInstance().getLocationManager().getByName(destinationComboBox.getAccessibleText());

        // TODO Create the class GiftAndFloralServiceRequest
        // TODO Mimic every other ServiceRequest class
        // TODO Include additional info
        GiftAndFloralServiceRequest serviceRequest =
                new GiftAndFloralServiceRequest(
                        statusComboBox,
                        employee,
                        location,
                        new Date(0),
                        new Date(new java.util.Date().getTime()),
                        0); // TODO Change to vary with amount of service requests

        // TODO Create method getGiftAndFloralSRManager, mimic others
        DBManager.getInstance().getGiftAndFloralSRManager().insert(serviceRequest);
    }

    @FXML
    void clearText() {
        // TODO implement clear, this might work

        patientComboBox.setValue("");
        destinationComboBox.setValue("");
        assigneeComboBox.setValue("");
        priorityComboBox.setValue("");
        statusComboBox.setValue("");
        requestDateDatePicker.setValue(null);
        requestDateDatePicker.getEditor().clear();
        additionalInfoTextArea.setText("");
    }

    // When send button pressed, calls sendToDB and then clearText
    // TODO Maybe add a cute 'sent' notification
    @FXML
    void send() {
        try {
            this.sendToDB();
            this.clearText();
        } catch (Exception e) {
            // TODO Yell at user that all fields aren't complete
            System.out.println("Error with Gift and Floral, send() failed");
        }
    }

    // TODO implement clear button
    // Calls clearText method

}

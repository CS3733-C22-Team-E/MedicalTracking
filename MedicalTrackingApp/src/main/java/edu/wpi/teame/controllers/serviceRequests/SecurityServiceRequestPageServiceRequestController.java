package edu.wpi.teame.controllers.serviceRequests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.view.controllers.serviceRequests.ServiceRequestController;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import lombok.SneakyThrows;

public class SecurityServiceRequestPageServiceRequestController extends ServiceRequestController {
  @FXML private TextField startTime;
  @FXML private TextField endTime;

  @FXML private JFXButton clearButton;
  @FXML private JFXButton sendButton;

  @FXML private JFXComboBox serviceLocation;
  @FXML private JFXComboBox serviceAssignee;

  @FXML private DatePicker datePicker;

  @FXML private JFXCheckBox completed;

  @FXML
  @SneakyThrows
  public void initialize(URL location, ResourceBundle resources) {
    // creates a linkedList of locations and sets all the values as one of roomNumber comboBox items
    List<Location> locations = DBManager.getInstance().getLocationManager().getAll();
    List<String> locationName = new LinkedList<String>();
    for (Location loc : locations) {
      locationName.add(loc.getLongName());
    }

    // Set the comboBox items
    serviceLocation.setItems(FXCollections.observableArrayList(locationName));
    serviceAssignee.setItems(
        FXCollections.observableArrayList("Test Name", "Test Name", "Test Name", "Test Name"));
  }

  @FXML
  void clearText() {
    startTime.setText("");
    endTime.setText("");
    serviceLocation.valueProperty().set(null);
    serviceAssignee.valueProperty().set(null);
    datePicker.setValue(null);
    datePicker.getEditor().clear();
    completed.setSelected(false);
  }
}

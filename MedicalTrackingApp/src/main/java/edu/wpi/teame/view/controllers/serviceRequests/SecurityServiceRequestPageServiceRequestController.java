package edu.wpi.teame.view.controllers.serviceRequests;

import com.jfoenix.controls.JFXButton;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import edu.wpi.teame.model.serviceRequests.SecurityServiceRequest;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SecurityServiceRequestPageServiceRequestController extends ServiceRequestController {
  @FXML private TextField floorTextBox;
  @FXML private TextField roomTextBox;
  @FXML private JFXButton clearButton;
  @FXML private JFXButton sendButton;

  @FXML
  public void initialize(URL location, ResourceBundle resources) {}

  @FXML
  void sendToDB() throws SQLException {
    Employee employee = DBManager.getInstance().getEmployeeManager().getAll().get(0);

    int roomID = Integer.parseInt(roomTextBox.getText());
    Location location = DBManager.getInstance().getLocationManager().get(roomID);

    SecurityServiceRequest serviceRequest =
        new SecurityServiceRequest(
            ServiceRequestStatus.OPEN, employee, location, new Date(new java.util.Date().getTime()), new Date(0), 0);
    serviceRequest = DBManager.getInstance().getSecuritySRManager().insert(serviceRequest);

    System.out.println();
  }

  @FXML
  void clearText() {
    floorTextBox.setText("");
    roomTextBox.setText("");
  }
}

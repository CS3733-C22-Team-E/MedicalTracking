package edu.wpi.teame.view.controllers.serviceRequests;

import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class InternalPatientTransportationServiceRequestPageServiceRequestController
    extends ServiceRequestController {
  @FXML private GridPane patientInfoGridPlane;
  @FXML private TextField roomTextBox;
  @FXML private TextField floorTextBox;
  @FXML private TextField patientNameTextBox;
  @FXML private GridPane transferTimeGridPlane;
  @FXML private TextField deliveryTimeTextbox;
  @FXML private DatePicker deliveryDateDatebox;
  @FXML private JFXComboBox<String> transferToPicker;
  @FXML private GridPane toOtherRoomGridPlane;
  @FXML private TextField toOtherFloorTextbox;
  @FXML private TextField toOtherRoomTextbox;
  @FXML private GridPane toTheirRoomGridPlane;
  @FXML private TextField toTheirFloorTextbox;
  @FXML private TextField toTheirRoomTextbox;
  @FXML private GridPane toOtherLocationGridPlane;
  @FXML private TextField toOtherLocationTextbox;
  @FXML private JFXComboBox<String> transferFromPicker;
  @FXML private GridPane fromOtherRoomGridPlane;
  @FXML private TextField fromOtherFloorTextbox;
  @FXML private TextField fromOtherRoomTextbox;
  @FXML private GridPane fromTheirRoomGridPlane;
  @FXML private TextField fromTheirFloorTextbox;
  @FXML private TextField fromTheirRoomTextbox;
  @FXML private GridPane fromOtherLocationGridPlane;
  @FXML private TextField fromOtherLocationTextbox;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    transferFromPicker.setItems(
        FXCollections.observableArrayList("Their Room", "Other Room", "Other Location"));
    transferToPicker.setItems(
        FXCollections.observableArrayList("Their Room", "Other Room", "Other Location"));
  }

  @FXML
  private void clearText() {
    roomTextBox.setText("");
    patientNameTextBox.setText("");
    floorTextBox.setText("");
    deliveryTimeTextbox.setText("");
    deliveryDateDatebox.setValue(null);
    deliveryDateDatebox.getEditor().clear();
    transferFromPicker.valueProperty().set(null);
    transferToPicker.valueProperty().set(null);
  }

  @FXML
  private void fromPickerCallback() {
    // This throws a bunch of exceptions that are non-fatal. I'll look into it later.
    String fromLocation = transferFromPicker.getValue();
    switch (fromLocation) {
      case "Their Room":
        fromTheirRoomGridPlane.setVisible(true);
        fromOtherRoomGridPlane.setVisible(false);
        fromOtherLocationGridPlane.setVisible(false);
        break;
      case "Other Room":
        fromTheirRoomGridPlane.setVisible(false);
        fromOtherRoomGridPlane.setVisible(true);
        fromOtherLocationGridPlane.setVisible(false);
        break;
      case "Other Location":
        fromTheirRoomGridPlane.setVisible(false);
        fromOtherRoomGridPlane.setVisible(false);
        fromOtherLocationGridPlane.setVisible(true);
        break;
      case "":
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + fromLocation);
    }
  }

  @FXML
  private void toPickerCallback() {
    // This throws a bunch of exceptions that are non-fatal. I'll look into it later.
    String toLocation = transferFromPicker.getValue();
    switch (toLocation) {
      case "Their Room":
        toOtherRoomGridPlane.setVisible(false);
        toOtherLocationGridPlane.setVisible(false);
        toTheirRoomGridPlane.setVisible(true);
        break;
      case "Other Room":
        toTheirRoomGridPlane.setVisible(false);
        toOtherRoomGridPlane.setVisible(true);
        toOtherLocationGridPlane.setVisible(false);
        break;
      case "Other Location":
        toTheirRoomGridPlane.setVisible(false);
        toOtherRoomGridPlane.setVisible(false);
        toOtherLocationGridPlane.setVisible(true);
        break;
      case "":
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + toLocation);
    }
  }
}

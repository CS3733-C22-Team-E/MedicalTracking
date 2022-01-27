package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class InternalPatientTransportationServiceRequestController extends ServiceRequest {
  @FXML private GridPane patientInfoGridPlane;
  @FXML private TextField roomTextBox;
  @FXML private TextField floorTextBox;
  @FXML private TextField patientNameTextBox;
  @FXML private GridPane transferTimeGridPlane;
  @FXML private TextField deliveryTimeTextbox;
  @FXML private DatePicker deliveryDateDatebox;
  @FXML private JFXComboBox transferToPicker;
  @FXML private GridPane toOtherRoomGridPlane;
  @FXML private TextField toOtherFloorTextbox;
  @FXML private TextField toOtherRoomTextbox;
  @FXML private GridPane toTheirRoomGridPlane;
  @FXML private TextField toTheirFloorTextbox;
  @FXML private TextField toTheirRoomTextbox;
  @FXML private GridPane toOtherLocationGridPlane;
  @FXML private TextField toOtherLocationTextbox;
  @FXML private JFXComboBox transferFromPicker;
  @FXML private GridPane fromOtherRoomGridPlane;
  @FXML private TextField fromOtherFloorTextbox;
  @FXML private TextField fromOtherRoomTextbox;
  @FXML private GridPane fromTheirRoomGridPlane;
  @FXML private TextField fromTheirFloorTextbox;
  @FXML private TextField fromTheirRoomTextbox;
  @FXML private GridPane fromOtherLocationGridPlane;
  @FXML private TextField fromOtherLocationTextbox;

  @FXML
  private void setup() {
    transferFromPicker.setItems(
        FXCollections.observableArrayList("Their Room", "Other Room", "Other Location"));
    transferToPicker.setItems(
        FXCollections.observableArrayList("Their Room", "Other Room", "Other Location"));
  }

  @FXML
  private void fromPickerCallback() {
    String fromLocation = transferFromPicker.focusedProperty().toString();
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
    String toLocation = transferFromPicker.focusedProperty().toString();
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

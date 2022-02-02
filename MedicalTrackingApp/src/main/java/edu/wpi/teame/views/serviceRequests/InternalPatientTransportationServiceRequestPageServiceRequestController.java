package edu.wpi.teame.views.serviceRequests;

import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

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
  @FXML private TextField toOtherFloorTextbox;
  @FXML private TextField toOtherRoomTextbox;
  @FXML private TextField toTheirFloorTextbox;
  @FXML private TextField toTheirRoomTextbox;
  @FXML private TextField toOtherLocationTextbox;
  @FXML private JFXComboBox<String> transferFromPicker;
  @FXML private TextField fromOtherFloorTextbox;
  @FXML private TextField fromOtherRoomTextbox;
  @FXML private TextField fromTheirFloorTextbox;
  @FXML private TextField fromTheirRoomTextbox;
  @FXML private TextField fromOtherLocationTextbox;

  @FXML private Text fromRoomText;
  @FXML private Text fromFloorText;
  @FXML private Text fromOtherLocationText;
  @FXML private Text toRoomText;
  @FXML private Text toFloorText;
  @FXML private Text toOtherLocationText;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    transferFromPicker.setItems(
        FXCollections.observableArrayList("Their Room", "Other Room", "Other Location"));
    transferToPicker.setItems(
        FXCollections.observableArrayList("Their Room", "Other Room", "Other Location"));
  }

  @FXML
  private void fromPickerCallback() {
    // This throws a bunch of exceptions that are non-fatal. I'll look into it later.
    String fromLocation = transferFromPicker.getValue();
    switch (fromLocation) {
      case "Their Room":
        fromRoomText.setVisible(true);
        fromFloorText.setVisible(true);
        fromTheirRoomTextbox.setVisible(true);
        fromTheirRoomTextbox.setText(roomTextBox.getText());
        fromTheirFloorTextbox.setVisible(true);
        fromTheirFloorTextbox.setText(floorTextBox.getText());
        fromOtherRoomTextbox.setVisible(false);
        fromOtherFloorTextbox.setVisible(false);
        fromOtherLocationText.setVisible(false);
        fromOtherLocationTextbox.setVisible(false);
        break;
      case "Other Room":
        fromRoomText.setVisible(true);
        fromFloorText.setVisible(true);
        fromOtherRoomTextbox.setVisible(true);
        fromOtherFloorTextbox.setVisible(true);
        fromTheirRoomTextbox.setVisible(false);
        fromTheirFloorTextbox.setVisible(false);
        fromOtherLocationText.setVisible(false);
        fromOtherLocationTextbox.setVisible(false);
        break;
      case "Other Location":
        fromOtherLocationText.setVisible(true);
        fromOtherLocationTextbox.setVisible(true);
        fromRoomText.setVisible(false);
        fromFloorText.setVisible(false);
        fromOtherRoomTextbox.setVisible(false);
        fromOtherFloorTextbox.setVisible(false);
        fromTheirRoomTextbox.setVisible(false);
        fromTheirFloorTextbox.setVisible(false);
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
    String toLocation = transferToPicker.getValue();
    switch (toLocation) {
      case "Their Room":
        toRoomText.setVisible(true);
        toFloorText.setVisible(true);
        toTheirRoomTextbox.setVisible(true);
        toTheirRoomTextbox.setText(roomTextBox.getText());
        toTheirFloorTextbox.setVisible(true);
        toTheirFloorTextbox.setText(floorTextBox.getText());
        toOtherRoomTextbox.setVisible(false);
        toOtherFloorTextbox.setVisible(false);
        toOtherLocationText.setVisible(false);
        toOtherLocationTextbox.setVisible(false);
        break;
      case "Other Room":
        toRoomText.setVisible(true);
        toFloorText.setVisible(true);
        toOtherRoomTextbox.setVisible(true);
        toOtherFloorTextbox.setVisible(true);
        toTheirRoomTextbox.setVisible(false);
        toTheirFloorTextbox.setVisible(false);
        toOtherLocationText.setVisible(false);
        toOtherLocationTextbox.setVisible(false);
        break;
      case "Other Location":
        toOtherLocationText.setVisible(true);
        toOtherLocationTextbox.setVisible(true);
        toRoomText.setVisible(false);
        toFloorText.setVisible(false);
        toOtherRoomTextbox.setVisible(false);
        toOtherFloorTextbox.setVisible(false);
        toTheirRoomTextbox.setVisible(false);
        toTheirFloorTextbox.setVisible(false);
        break;
      case "":
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + toLocation);
    }
  }
}

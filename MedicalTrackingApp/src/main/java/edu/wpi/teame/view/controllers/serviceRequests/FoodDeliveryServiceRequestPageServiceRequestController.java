package edu.wpi.teame.view.controllers.serviceRequests;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class FoodDeliveryServiceRequestPageServiceRequestController
    extends ServiceRequestController {
  @FXML private TextField titleTextField;
  @FXML private DatePicker serviceDatePicker;
  @FXML private JFXComboBox locationComboBox;
  @FXML private JFXComboBox assigneeComboBox;
  @FXML private JFXComboBox priorityComboBox;
  @FXML private JFXComboBox statusComboBox;
  @FXML private TextArea additionalInfoTextArea;
  @FXML private JFXComboBox patientComboBox;
  @FXML private Button clearButton;
  @FXML private Button submitButton;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // TODO: Change priority comboBox to actual values
    priorityComboBox.setItems(
        FXCollections.observableArrayList(new String[] {"Low", "Medium", "High"}));
    statusComboBox.setItems(FXCollections.observableArrayList(ServiceRequestStatus.values()));

    locationComboBox.setItems(FXCollections.observableArrayList(new String[] {"test"}));
    assigneeComboBox.setItems(FXCollections.observableArrayList(new String[] {"test"}));
    patientComboBox.setItems(FXCollections.observableArrayList(new String[] {"test"}));

    serviceDatePicker
        .valueProperty()
        .addListener(
            (listener) -> {
              validateSubmitButton();
            });

    locationComboBox
        .valueProperty()
        .addListener(
            listener -> {
              validateSubmitButton();
            });

    assigneeComboBox
        .valueProperty()
        .addListener(
            listener -> {
              validateSubmitButton();
            });

    priorityComboBox
        .valueProperty()
        .addListener(
            listener -> {
              validateSubmitButton();
            });

    statusComboBox
        .valueProperty()
        .addListener(
            listener -> {
              validateSubmitButton();
            });

    patientComboBox
        .valueProperty()
        .addListener(
            listener -> {
              validateSubmitButton();
            });
  }

  public void clearText() {
    titleTextField.setText("");
    serviceDatePicker.setValue(null);
    serviceDatePicker.getEditor().clear();
    locationComboBox.valueProperty().setValue(null);
    assigneeComboBox.valueProperty().setValue(null);
    priorityComboBox.valueProperty().setValue(null);
    statusComboBox.valueProperty().setValue(null);
    additionalInfoTextArea.setText("");
    patientComboBox.valueProperty().setValue(null);
  }

  public void validateSubmitButton() {
    submitButton.setDisable(
        titleTextField.getText().isEmpty()
            || serviceDatePicker.getValue() == null
            || locationComboBox.getValue() == null
            || assigneeComboBox.getValue() == null
            || priorityComboBox.getValue() == null
            || statusComboBox.getValue() == null
            || patientComboBox.getValue() == null
            || additionalInfoTextArea.getText().isEmpty());
  }
}

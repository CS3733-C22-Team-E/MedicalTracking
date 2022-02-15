package edu.wpi.teame.view.controllers.serviceRequests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import lombok.SneakyThrows;

public class SanitationServiceRequestPageServiceRequestController extends ServiceRequestController {

  @FXML private DatePicker requestDate;
  @FXML private JFXComboBox locationDropdown;
  @FXML private JFXComboBox assigneeDropdown;
  @FXML private JFXComboBox statusDropdown;
  @FXML private JFXComboBox priorityDropdown;
  @FXML private TextArea additionalInfo;
  @FXML private JFXButton clearButton;
  @FXML private JFXButton submitButton;

  @SneakyThrows
  public void initialize(URL location, ResourceBundle resources) {
    priorityDropdown.setItems(
        FXCollections.observableArrayList(new String[] {"Low", "Medium", "High"}));
    statusDropdown.setItems(FXCollections.observableArrayList(ServiceRequestStatus.values()));
    locationDropdown.setItems(FXCollections.observableArrayList(new String[] {"test"}));
    assigneeDropdown.setItems(FXCollections.observableArrayList(new String[] {"test"}));

    requestDate
        .valueProperty()
        .addListener(
            (listener) -> {
              validateSubmitButton();
            });

    locationDropdown
        .valueProperty()
        .addListener(
            listener -> {
              validateSubmitButton();
            });

    assigneeDropdown
        .valueProperty()
        .addListener(
            listener -> {
              validateSubmitButton();
            });

    priorityDropdown
        .valueProperty()
        .addListener(
            listener -> {
              validateSubmitButton();
            });

    statusDropdown
        .valueProperty()
        .addListener(
            listener -> {
              validateSubmitButton();
            });
  }

  @FXML
  private void clearText() {
    additionalInfo.setText("");
    locationDropdown.valueProperty().set(null);
    assigneeDropdown.valueProperty().set(null);
    statusDropdown.valueProperty().set(null);
    priorityDropdown.valueProperty().set(null);
    requestDate.setValue(null);
    requestDate.getEditor().clear();
  }

  public void validateSubmitButton() {
    submitButton.setDisable(
        requestDate.getValue() == null
            || locationDropdown.getValue() == null
            || assigneeDropdown.getValue() == null
            || priorityDropdown.getValue() == null
            || statusDropdown.getValue() == null);
  }
}

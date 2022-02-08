package edu.wpi.teame.view.controllers.serviceRequests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ReligiousServiceRequestPageServiceRequestController extends ServiceRequestController {

  @FXML public JFXButton sendButton;
  @FXML private TextField patientName;
  @FXML private TextField roomNumber;
  @FXML private TextField floorNumber;
  @FXML private DatePicker datePicker;

  @FXML private TextField startTime;
  @FXML private TextField finishTime;

  @FXML private JFXCheckBox christianityBox;
  @FXML private JFXComboBox<String> christianityChoices;

  @FXML private JFXCheckBox islamBox;
  @FXML private JFXComboBox<String> islamChoices;

  @FXML private JFXCheckBox judaismBox;
  @FXML private JFXComboBox<String> judaismChoices;

  @FXML private JFXComboBox<String> hinduismChoices;
  @FXML private JFXCheckBox hinduismBox;

  @FXML private JFXCheckBox otherBox;
  @FXML private Text otherText;
  @FXML private TextField otherServiceChoices;

  @FXML
  public void initialize(URL location, ResourceBundle resources) {
    christianityChoices.setItems(
        FXCollections.observableArrayList("Blessing", "Last Rites", "Communion"));
    judaismChoices.setItems(
        FXCollections.observableArrayList("Chaplain", "Shabbat", "Request Kosher Food"));
    islamChoices.setItems(
        FXCollections.observableArrayList("Mosque Trip", "Jumuah", "Prayer Time"));
    hinduismChoices.setItems(FXCollections.observableArrayList("Chaplain", "Prayer Time"));
  }

  @FXML
  private void ChristianityOptionsCallBack() {
    christianityChoices.setVisible(!christianityChoices.isVisible());
  }

  @FXML
  private void JudaismOptionsCallBack() {
    judaismChoices.setVisible(!judaismChoices.isVisible());
  }

  @FXML
  private void IslamOptionsCallBack() {
    islamChoices.setVisible(!islamChoices.isVisible());
  }

  @FXML
  private void HinduismOptionsCallBack() {
    hinduismChoices.setVisible(!hinduismChoices.isVisible());
  }

  @FXML
  private void OtherOptionsCallBack() {
    otherText.setVisible(!otherText.isVisible());
    otherServiceChoices.setVisible(!otherServiceChoices.isVisible());
  }
}

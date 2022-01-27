package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teamname.App;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ReligiousServiceRequest {

  @FXML private TextField patientName;
  @FXML private TextField roomNumber;
  @FXML private TextField floorNumber;
  @FXML private DatePicker datePicker;

  @FXML private TextField startTime;
  @FXML private TextField finishTime;

  @FXML private JFXCheckBox christianityBox;
  @FXML private JFXComboBox christianityChoices;

  @FXML private JFXCheckBox islamBox;
  @FXML private JFXComboBox islamChoices;

  @FXML private JFXCheckBox judaismBox;
  @FXML private JFXComboBox judaismChoices;

  @FXML private JFXComboBox hinduismChoices;
  @FXML private JFXCheckBox hinduismBox;

  @FXML private JFXCheckBox otherBox;
  @FXML private Text otherText;
  @FXML private TextField otherServiceChoices;

  @FXML private JFXButton sendButton;

  @FXML
  private void setup() {
    christianityChoices.setItems(
        FXCollections.observableArrayList("Blessing", "Last Rites", "Communion"));
    judaismChoices.setItems(
        FXCollections.observableArrayList("Chaplain", "Shabbat", "Request Kosher Food"));
    islamChoices.setItems(
        FXCollections.observableArrayList("Mosque Trip", "Jumuah", "Prayer Time"));
    hinduismChoices.setItems(FXCollections.observableArrayList("Chaplain", "Prayer Time"));
  }

  @FXML
  private void sendServiceRequest() throws IOException {
    App.backToLandingPage();
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

package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXComboBox;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MapViewController {
  private HashMap<String, Image> mapImages = new HashMap<String, Image>();
  @FXML JFXComboBox mapSelectionComboBox;
  @FXML ImageView mapImageView;

  @FXML
  private void setup() {
    mapSelectionComboBox.setItems(
        FXCollections.observableArrayList(
            "Ground Floor",
            "Lower Level 1",
            "Lower Level 2",
            "First Floor",
            "Second Floor",
            "Third Floor"));
  }

  @FXML
  private void comboBoxChanged() {
    switch (mapSelectionComboBox.getValue().toString()) {
      case "Ground Floor":
        mapImageView.setImage(new Image("@../images/00_thegroundfloor.png"));
        break;
      case "Lower Level 1":
        mapImageView.setImage(new Image("@../images/00_thelowerlevel1.png"));
        break;
      case "Lower Level 2":
        mapImageView.setImage(new Image("@../images/00_thelowerlevel2.png"));
        break;
      case "First Floor":
        mapImageView.setImage(new Image("@../images/01_thefirstfloor.png"));
        break;
      case "Second Floor":
        mapImageView.setImage(new Image("@../images/02_thesecondfloor.png"));
        break;
      case "Third Floor":
        mapImageView.setImage(new Image("@../images/03_thethirdfloor.png"));
        break;
    }
  }
}

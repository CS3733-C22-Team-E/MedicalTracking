package edu.wpi.teamname.views;

import edu.wpi.teamname.App;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MapViewController {
  private HashMap<String, Image> mapImages = new HashMap<String, Image>();
  @FXML private ComboBox dropDown;
  @FXML private ImageView mapImageView;
  private boolean set = false;

  @FXML
  void switchImage(String name) {
    mapImageView.setImage(new Image(App.class.getResource(name).toString()));
  }

  @FXML
  private void setup() {

    if (!set) {
      dropDown.setItems(
          FXCollections.observableArrayList(
              "Ground Floor",
              "Lower Level 1",
              "Lower Level 2",
              "First Floor",
              "Second Floor",
              "Third Floor"));
      set = true;
    }
  }

  @FXML
  private void comboBoxChanged() {
    System.out.println(dropDown.getValue().toString());
    switch (dropDown.getValue().toString()) {
      case "Ground Floor":
        switchImage("images/00_thegroundfloor.png");
        break;
      case "Lower Level 1":
        switchImage("images/00_thelowerlevel1.png");
        break;
      case "Lower Level 2":
        switchImage("images/00_thelowerlevel2.png");
        break;
      case "First Floor":
        switchImage("images/01_thefirstfloor.png");
        break;
      case "Second Floor":
        switchImage("images/02_thesecondfloor.png");
        break;
      case "Third Floor":
        switchImage("images/03_thethirdfloor.png");
        break;
    }
  }
}

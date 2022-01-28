package edu.wpi.teamname.views;

import java.io.FileNotFoundException;
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
  private void comboBoxChanged() throws FileNotFoundException {
    System.out.println(dropDown.getValue().toString());
    // need to figure out how to switch images
    //    switch (dropDown.getValue().toString()) {
    //      case "Ground Floor":
    //        FileInputStream stream =
    //            new
    // FileInputStream("resources\\edu\\wpi\\teamname\\images\\00_thegroundfloor.png");
    //        mapImageView.setImage(new Image(stream));
    //        break;
    //      case "Lower Level 1":
    //        mapImageView.setImage(new Image("@../images/00_thelowerlevel1.png"));
    //        break;
    //      case "Lower Level 2":
    //        mapImageView.setImage(new Image("images/00_thelowerlevel2.png"));
    //        break;
    //      case "First Floor":
    //        mapImageView.setImage(new Image("@images/01_thefirstfloor.png"));
    //        break;
    //      case "Second Floor":
    //        mapImageView.setImage(new Image("@images/02_thesecondfloor.png"));
    //        break;
    //      case "Third Floor":
    //        mapImageView.setImage(new Image("@../images/03_thethirdfloor.png"));
    //        break;
    //    }
  }
}

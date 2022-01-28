package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXComboBox;
import java.io.FileNotFoundException;
import java.util.HashMap;
import javafx.fxml.FXML;
import javafx.scene.image.Image;

public class MainPageViewController {
  @FXML private JFXComboBox mapSelectionComboBox;
  private HashMap<String, Image> mapImages = new HashMap<String, Image>();

  @FXML
  private void setup() throws FileNotFoundException {
    mapImages.put("Ground Floor", new Image("\\images\\00_thegroundfloor.png"));
    mapImages.put("Level 1", new Image("\\images\\00_thelowerlevel1.png"));

    for (String levelName : mapImages.keySet()) {
      mapSelectionComboBox.getItems().add(levelName);
    }
  }
}

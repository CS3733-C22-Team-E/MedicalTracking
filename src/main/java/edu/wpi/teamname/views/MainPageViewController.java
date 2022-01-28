package edu.wpi.teamname.views;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class MainPageViewController {
  @FXML
  private void setup() throws FileNotFoundException {
    StackPane sp = new StackPane();
    Image img = new Image("../images/00_thegroundfloor.png");
    ImageView imgView = new ImageView(img);
    sp.getChildren().add(imgView);
  }
}

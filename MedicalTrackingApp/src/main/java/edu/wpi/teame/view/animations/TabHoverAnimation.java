package edu.wpi.teame.view.animations;

import edu.wpi.teame.view.StyledTab;
import javafx.animation.ScaleTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class TabHoverAnimation {

  private static Duration d = new Duration(100);

  public static void install(StyledTab t) {
    AnchorPane ap = t.getAnchorPane();
    ap.setOnMouseEntered(
        e -> {
          enterAnimation(t);
        });
    ap.setOnMouseExited(
        e -> {
          exitAnimation(t);
        });
  }

  private static void enterAnimation(StyledTab t) {
    ImageView i = t.getImageView();
    ScaleTransition transition = new ScaleTransition(d, i);
    transition.setFromX(i.getScaleX());
    transition.setToX(1.3);
    transition.setFromY(i.getScaleY());
    transition.setToY(1.3);
    transition.play();
  }

  private static void exitAnimation(StyledTab t) {
    ImageView i = t.getImageView();
    ScaleTransition transition = new ScaleTransition(d, i);
    transition.setFromX(i.getScaleX());
    transition.setToX(1);
    transition.setFromY(i.getScaleY());
    transition.setToY(1);
    transition.play();
  }
}

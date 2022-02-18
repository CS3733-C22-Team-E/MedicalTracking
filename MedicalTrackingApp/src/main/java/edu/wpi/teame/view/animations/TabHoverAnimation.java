package edu.wpi.teame.view.animations;

import edu.wpi.teame.view.StyledTab;
import javafx.animation.ScaleTransition;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class TabHoverAnimation {

  private static Duration d = new Duration(100);

  public static StyledTab install(StyledTab t) {
    AnchorPane ap = t.getAnchorPane();
    ap.setOnMouseEntered(
        e -> {
          enterAnimation(t);
        });
    ap.setOnMouseExited(
        e -> {
          exitAnimation(t);
        });
    return t;
  }

  private static void enterAnimation(StyledTab t) {
    AnchorPane a = t.getAnchorPane();

    ScaleTransition transition = new ScaleTransition(d, a);
    transition.setFromX(a.getScaleX());
    transition.setToX(1.3);
    transition.setFromY(a.getScaleY());
    transition.setToY(1.3);
    transition.play();
  }

  private static void exitAnimation(StyledTab t) {
    AnchorPane a = t.getAnchorPane();
    ScaleTransition transition = new ScaleTransition(d, a);
    transition.setFromX(a.getScaleX());
    transition.setToX(1);
    transition.setFromY(a.getScaleY());
    transition.setToY(1);
    transition.play();
  }
}

package edu.wpi.teame.view;

import com.jfoenix.controls.JFXButton;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;

public class MapIcon {
  JFXButton Button;
  String Descriptor;

  public MapIcon(JFXButton button, String descriptor) {
    Button = button;
    Descriptor = descriptor;
  }

  public MapIcon(Double x, Double y, String descriptor, Node graphic) {
    Button = new JFXButton();
    Button.setGraphic(graphic);
    Tooltip tooltip = new Tooltip(descriptor);
    Tooltip.install(Button, tooltip);
    Button.setTranslateX(x - (2500));
    Button.setTranslateY(y - (1700));
    Button.setOpacity(.5);
  }

  public JFXButton getButton() {
    return Button;
  }

  public String getDescriptor() {
    return Descriptor;
  }

  public void setDescriptor(String descriptor) {
    Descriptor = descriptor;
  }
}

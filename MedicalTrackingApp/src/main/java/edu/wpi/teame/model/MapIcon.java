package edu.wpi.teame.model;

import com.jfoenix.controls.JFXButton;
import javafx.scene.Node;

public class MapIcon {
  JFXButton Button;
  String Descriptor;

  public MapIcon(JFXButton button, String descriptor) {
    Button = button;
    Descriptor = descriptor;
  }

  public MapIcon(Double x, Double y, String descriptor, Node graphic) {
    Button = new JFXButton(descriptor, graphic);
    Button.setTranslateX(x);
    Button.setTranslateY(y);
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

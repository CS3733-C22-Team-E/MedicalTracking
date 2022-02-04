package edu.wpi.teame.model;

import com.jfoenix.controls.JFXButton;

public class MapIcon {
  JFXButton Button;
  String Descriptor;

  public MapIcon(JFXButton button, String descriptor) {
    Button = button;
    Descriptor = descriptor;
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

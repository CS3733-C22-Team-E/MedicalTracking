package edu.wpi.teame.views;

import javafx.scene.image.ImageView;

public class MapIcon {
  ImageView Icon;
  String Descriptor;

  public MapIcon(ImageView icon, String descriptor) {
    Icon = icon;
    Descriptor = descriptor;
  }

  public ImageView getIcon() {
    return Icon;
  }

  public void setIcon(ImageView icon) {
    Icon = icon;
  }

  public String getDescriptor() {
    return Descriptor;
  }

  public void setDescriptor(String descriptor) {
    Descriptor = descriptor;
  }
}

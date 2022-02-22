package edu.wpi.teame.model.enums;

import javafx.scene.paint.Color;

public enum ServiceRequestPriority {
  Critical,
  High,
  Normal,
  Low;

  public Color getColor() {
    switch (this) {
      case Critical:
        return Color.RED;
      case High:
        return Color.ORANGE;
      case Normal:
        return Color.YELLOW;
      case Low:
        return Color.WHITE;
    }
    return null;
  }
}

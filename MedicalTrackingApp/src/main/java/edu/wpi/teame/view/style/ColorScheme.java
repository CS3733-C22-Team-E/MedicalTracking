package edu.wpi.teame.view.style;

import java.io.*;
import java.util.HashMap;
import javafx.scene.paint.Color;

public class ColorScheme {
  private final Color background;
  private final Color foreground;

  public ColorScheme(Color background, Color foreground) {
    this.background = background;
    this.foreground = foreground;
  }

  public Color getBackground() {
    return this.background;
  }

  public Color getForeground() {
    return this.foreground;
  }

  public HashMap<String, Double> getColor1RGB() {
    HashMap<String, Double> rgb = new HashMap<>();
    rgb.put("r", background.getRed());
    rgb.put("g", background.getGreen());
    rgb.put("b", background.getBlue());
    return rgb;
  }

  public HashMap<String, Double> getColor2RGB() {
    HashMap<String, Double> rgb = new HashMap<>();
    rgb.put("r", foreground.getRed());
    rgb.put("g", foreground.getGreen());
    rgb.put("b", foreground.getBlue());
    return rgb;
  }

  public HashMap<String, Integer> getColor1RGBIntegers() {
    HashMap<String, Integer> rgb = new HashMap<>();
    rgb.put("r", (int) (background.getRed() * 255));
    rgb.put("g", (int) (background.getGreen() * 255));
    rgb.put("b", (int) (background.getBlue() * 255));
    return rgb;
  }

  public HashMap<String, Integer> getColor2RGBIntegers() {
    HashMap<String, Integer> rgb = new HashMap<>();
    rgb.put("r", (int) (foreground.getRed() * 255));
    rgb.put("g", (int) (foreground.getGreen() * 255));
    rgb.put("b", (int) (foreground.getBlue() * 255));
    return rgb;
  }
}

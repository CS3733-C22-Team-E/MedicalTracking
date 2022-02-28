package edu.wpi.teame.view.style;

import java.util.HashMap;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class ColorScheme {
  private final Color secondaryBackground;
  private final Color background;
  private final Color foreground;
  private final Color textColor;

  public ColorScheme(
      Color background, Color secondaryBackground, Color foreground, Color textColor) {
    this.secondaryBackground = secondaryBackground;
    this.background = background;
    this.foreground = foreground;
    this.textColor = textColor;
  }

  public Color getTextColor() {
    return this.textColor;
  }

  public Color getForegroundColor() {
    return this.foreground;
  }

  public Color getBackgroundColor() {
    return this.background;
  }

  public Color getSecondaryBackgroundColor() {
    return this.secondaryBackground;
  }

  public Background getBackground() {
    return new Background(
        new BackgroundFill(getBackgroundColor(), new CornerRadii(10), Insets.EMPTY));
  }

  public Background getSecondaryBackground() {
    return new Background(
            new BackgroundFill(getSecondaryBackgroundColor(), new CornerRadii(10), Insets.EMPTY));
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

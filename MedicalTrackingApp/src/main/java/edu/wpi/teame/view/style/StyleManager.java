package edu.wpi.teame.view.style;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.scene.paint.Color;

public class StyleManager {
  private HashMap<String, ColorScheme> colorSchemes;
  private List<IStyleable> styleableObjects;
  private static StyleManager instance;
  private String currentStyle = null;

  public static synchronized StyleManager getInstance() {
    if (instance == null) {
      instance = new StyleManager();
    }
    return instance;
  }

  private StyleManager() {
    styleableObjects = new ArrayList<>();
    colorSchemes = new HashMap<>();
  }

  public void loadStyles() {
    Color grayAccents = Color.color(0.30980392156, 0.30980392156, 0.30980392156);
    Color whiteText = Color.color(1, 1, 1);
    Color spotifyBlack = Color.color(0.09803921568, 0.07843137254, 0.07843137254);
    Color spotifyGreen = Color.color(0.11764705882, 0.8431372549, 0.376470588);

    Color stepBroBackground = rgb(15, 14, 23);
    Color stepBroHeadline = rgb(255, 255, 254);
    Color stepBroParagraph = rgb(167, 169, 190);
    Color stepBroButton = rgb(255, 137, 6);
    Color stepBroButtonText = rgb(255, 255, 254);
    Color stepBroStroke = rgb(255, 255, 255);
    Color stepBroMain = rgb(255, 255, 254);
    Color stepBroHighlight = rgb(255, 137, 6);
    Color stepBroSecondary = rgb(242, 95, 76);
    Color stepBroTertiary = rgb(229, 49, 112);
    colorSchemes.put(
        "Step-Bro",
        new ColorScheme(
            stepBroBackground,
            stepBroHeadline,
            stepBroParagraph,
            stepBroButton,
            stepBroButtonText,
            stepBroStroke,
            stepBroMain,
            stepBroHighlight,
            stepBroSecondary,
            stepBroTertiary));

    currentStyle = "Step-Bro";
  }

  public void selectTheme(String colorSchemeName) {
    currentStyle = colorSchemeName;
  }

  public void updateStyle() {
    for (IStyleable styleableObject : styleableObjects) {
      styleableObject.updateStyle();
    }
  }

  public void subscribe(IStyleable styleableObject) {
    styleableObjects.add(styleableObject);
    styleableObject.updateStyle();
  }

  public List<String> getStyleNames() {
    List<String> styleNames = new ArrayList<>();
    for (String styleKey : colorSchemes.keySet()) {
      styleNames.add(styleKey);
    }
    return styleNames;
  }

  public ColorScheme getCurrentStyle() {
    return colorSchemes.get(currentStyle);
  }

  public String getStyleName() {
    return currentStyle;
  }

  private Color rgb(double r, double g, double b) {
    return Color.color(r / 255, g / 255, b / 255);
  }
}

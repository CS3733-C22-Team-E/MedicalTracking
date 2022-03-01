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
    Color spotifyBlack = Color.color(0.09803921568, 0.07843137254, 0.07843137254);
    Color spotifyGreen = Color.color(0.11764705882, 0.8431372549, 0.37647058823);
    Color spotifyGray = Color.color(0.759803922, 0.769803922, 0.759803922);
    Color spotifyText = Color.color(1, 1, 1);
    colorSchemes.put(
        "spotify", new ColorScheme(spotifyBlack, spotifyGray, spotifyGreen, spotifyText));

    colorSchemes.put(
        "black", new ColorScheme(spotifyBlack, spotifyBlack, spotifyBlack, spotifyBlack));

    currentStyle = "spotify";
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
}

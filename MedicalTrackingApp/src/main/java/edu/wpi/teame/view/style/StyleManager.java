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
    Color spotifyGreen = Color.color(0.11764705882, 0.8431372549, 0.376470588);
    Color spotifyGray = Color.color(0.30980392156, 0.30980392156, 0.30980392156);
    Color whiteText = Color.color(1, 1, 1);
    colorSchemes.put(
        "Spotify", new ColorScheme(spotifyBlack, spotifyGray, spotifyGreen, whiteText));

    Color blueBackground = Color.color(0.17254902, 0.2, 0.2);
    Color blueForeground = Color.color(0.14901960, 0.4, 0.81176470);
    colorSchemes.put(
        "Blue", new ColorScheme(blueBackground, spotifyGray, blueForeground, whiteText));

    Color purpleBackground = Color.color(0.54117647, 0.22352941, 0.88235294);
    Color purpleForeground = Color.color(0.71372549, 0.40392156, 0.94509803);
    colorSchemes.put(
        "Purple", new ColorScheme(purpleBackground, spotifyGray, purpleForeground, whiteText));

    currentStyle = "Spotify";
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

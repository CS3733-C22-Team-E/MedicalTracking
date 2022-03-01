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
    colorSchemes.put(
        "Not Spotify",
        new ColorScheme(spotifyBlack, grayAccents, spotifyGreen, whiteText, whiteText));

    Color eclipseAccents = Color.color(0.6549019607843137, 0.6627450980392157, 0.7450980392156863);
    Color eclipseBackground = Color.color(0, 0, 0);
    Color eclipseForeground = Color.color(1, 0.5372549019607843, 0.02352941176);
    colorSchemes.put(
        "Eclipse",
        new ColorScheme(
            eclipseBackground, eclipseAccents, eclipseForeground, whiteText, whiteText));

    Color neutronBackground = Color.color(1, 1, 0.99607843137);
    Color neutronForeground = Color.color(0.38431372549, 0.27450980392, 0.91764705882);
    Color neutronText = Color.color(0.16862745098, 0.1725490196, 0.20392156862);
    Color neutronAccents = Color.color(0.81960784313, 0.81960784313, 0.91372549019);
    colorSchemes.put(
        "Neutron Star",
        new ColorScheme(
            neutronBackground, neutronAccents, neutronForeground, neutronText, whiteText));

    Color pigBackground = Color.color(0.33333333333, 0.25882352941, 0.23921568627);
    Color pigForeground = Color.color(1, 0.75294117647, 0.67843137254);
    Color pigAccents = Color.color(0.90588235294, 0.56078431372, 0.70196078431);
    colorSchemes.put(
        "Pig Pen", new ColorScheme(pigBackground, pigAccents, pigForeground, whiteText, whiteText));

    currentStyle = "Not Spotify";
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

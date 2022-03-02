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

    Color halloweenBackground = rgb(15, 14, 23);
    Color halloweenHeadline = rgb(255, 255, 254);
    Color halloweenParagraph = rgb(167, 169, 190);
    Color halloweenButton = rgb(255, 137, 6);
    Color halloweenButtonText = rgb(255, 255, 254);
    Color halloweenStroke = rgb(255, 255, 255);
    Color halloweenMain = rgb(255, 255, 254);
    Color halloweenHighlight = rgb(255, 137, 6);
    Color halloweenSecondary = rgb(242, 95, 76);
    Color halloweenTertiary = rgb(229, 49, 112);
    colorSchemes.put(
        "Trick or Treat",
        new ColorScheme(
            halloweenBackground,
            halloweenHeadline,
            halloweenParagraph,
            halloweenButton,
            halloweenButtonText,
            halloweenStroke,
            halloweenMain,
            halloweenHighlight,
            halloweenSecondary,
            halloweenTertiary));

    Color stepBroBackground = rgb(27, 27, 27);
    Color stepBroHeadline = rgb(255, 255, 254);
    Color stepBroParagraph = rgb(255, 255, 254);
    Color stepBroButton = rgb(255, 163, 26);
    Color stepBroButtonText = rgb(255, 255, 254);
    Color stepBroStroke = rgb(0, 0, 0);
    Color stepBroMain = rgb(47, 47, 47);
    Color stepBroHighlight = rgb(255, 163, 26);
    Color stepBroSecondary = rgb(128, 128, 128);
    Color stepBroTertiary = rgb(41, 41, 41);
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

    Color ysBG = rgb(186, 232, 232);
    Color ysHead = rgb(39, 35, 67);
    Color ysP = rgb(45, 51, 74);
    Color ysB = rgb(255, 216, 3);
    Color ysBT = rgb(39, 35, 67);
    Color ysSt = rgb(39, 35, 67);
    Color ysM = rgb(255, 255, 254);
    Color ysH = rgb(255, 216, 3);
    Color ysS = rgb(227, 246, 245);
    Color ysT = rgb(186, 232, 232);
    colorSchemes.put(
        "Yellow Snow", new ColorScheme(ysBG, ysHead, ysP, ysB, ysBT, ysSt, ysM, ysH, ysS, ysT));

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

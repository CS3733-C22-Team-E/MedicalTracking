package edu.wpi.teame.view.style;

import edu.wpi.teame.App;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

public class StyleManager {
  private HashMap<String, ColorScheme> colorSchemes;
  private List<IStyleable> styleableObjects;
  private static StyleManager instance;
  private String currentStyle = null;
  private Media themeSound = null;

  public static synchronized StyleManager getInstance() {
    if (instance == null) {
      instance = new StyleManager();
    }
    return instance;
  }

  private StyleManager() {
    themeSound = new Media(App.class.getResource("audio/ThemeIntro.mp3").toString());
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

    Color pigBG = rgb(85, 66, 61);
    Color pigHead = rgb(255, 255, 254);
    Color pigP = rgb(255, 243, 236);
    Color pigB = rgb(255, 192, 173);
    Color pigBT = rgb(39, 28, 25);
    Color pigSt = rgb(20, 13, 11);
    Color pigM = rgb(255, 243, 236);
    Color pigH = rgb(231, 143, 179);
    Color pigS = rgb(255, 192, 173);
    Color pigT = rgb(150, 86, 161);
    colorSchemes.put(
        "Pig Pen",
        new ColorScheme(pigBG, pigHead, pigP, pigB, pigBT, pigSt, pigM, pigH, pigS, pigT));

    Color iceBG = rgb(255, 255, 254);
    Color iceHead = rgb(24, 24, 24);
    Color iceP = rgb(46, 46, 46);
    Color iceB = rgb(79, 196, 207);
    Color iceBT = rgb(24, 24, 24);
    Color iceSt = rgb(24, 24, 24);
    Color iceM = rgb(242, 238, 245);
    Color iceH = rgb(79, 196, 207);
    Color iceS = rgb(153, 79, 243);
    Color iceT = rgb(251, 221, 116);
    colorSchemes.put(
        "Frozone",
        new ColorScheme(iceBG, iceHead, iceP, iceB, iceBT, iceSt, iceM, iceH, iceS, iceT));

    Color mapleBG = rgb(0, 70, 67);
    Color mapleHead = rgb(255, 255, 254);
    Color mapleParagraph = rgb(255, 255, 254);
    Color mapleButton = rgb(242, 95, 76);
    Color mapleButtonText = rgb(255, 255, 254);
    Color mapleStroke = rgb(0, 0, 0);
    Color mapleMain = rgb(44, 182, 125);
    Color mapleHighlight = rgb(242, 95, 76);
    Color mapleSecondary = rgb(249, 188, 96);
    Color mapleTertiary = rgb(225, 97, 98);
    colorSchemes.put(
        "Maple Treeway",
        new ColorScheme(
            mapleBG,
            mapleHead,
            mapleParagraph,
            mapleButton,
            mapleButtonText,
            mapleStroke,
            mapleMain,
            mapleHighlight,
            mapleSecondary,
            mapleTertiary));

    currentStyle = "Step-Bro";
  }

  public void selectTheme(String colorSchemeName) {
    if (colorSchemeName.equals("Step-Bro")) {
      MediaPlayer mediaPlayer = new MediaPlayer(themeSound);
      mediaPlayer.setVolume(1);
      mediaPlayer.play();
    }
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

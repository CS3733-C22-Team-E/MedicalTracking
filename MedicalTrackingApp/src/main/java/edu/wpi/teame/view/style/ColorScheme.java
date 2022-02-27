package edu.wpi.teame.view.style;

import javafx.scene.paint.Color;

import java.io.File;
import java.util.HashMap;

public class ColorScheme {
    private final File css;
    private final Color color1, color2;

    public ColorScheme(String cssPath, Color color1, Color color2) {
        this.css = new File(cssPath);
        this.color1 = color1;
        this.color2 = color2;
    }

    public File getCSS() {
        return this.css;
    }

    public Color getColor1() {
        return this.color1;
    }

    public Color getColor2() {
        return this.color2;
    }

    public HashMap<String, Double> getColor1RGB() {
        HashMap<String, Double> rgb = new HashMap<>();
        rgb.put("r", color1.getRed());
        rgb.put("g", color1.getGreen());
        rgb.put("b", color1.getBlue());
        return rgb;
    }

    public HashMap<String, Integer> getColor1RGBIntegers() {
        HashMap<String, Integer> rgb = new HashMap<>();
        rgb.put("r", (int) (color1.getRed() * 255));
        rgb.put("g", (int) (color1.getGreen() * 255));
        rgb.put("b", (int) (color1.getBlue() * 255));
        return rgb;
    }

    public HashMap<String, Integer> getColor2RGBIntegers() {
        HashMap<String, Integer> rgb = new HashMap<>();
        rgb.put("r", (int) (color2.getRed() * 255));
        rgb.put("g", (int) (color2.getGreen() * 255));
        rgb.put("b", (int) (color2.getBlue() * 255));
        return rgb;
    }
}

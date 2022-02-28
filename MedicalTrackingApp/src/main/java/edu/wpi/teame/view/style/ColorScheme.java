package edu.wpi.teame.view.style;

import edu.wpi.teame.App;
import java.io.*;
import java.util.HashMap;
import javafx.scene.paint.Color;

public class ColorScheme {
  private final File mainCSS;
  private final File loginCSS;
  private final Color color1, color2;

  public ColorScheme(String schemeName, Color color1, Color color2) {
    this.mainCSS =
        new File(App.class.getResource("css/schemes/" + schemeName + "Login.css").toString());
    this.loginCSS =
        new File(App.class.getResource("css/schemes/" + schemeName + "Login.css").toString());
    this.color1 = color1;
    this.color2 = color2;
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

  public HashMap<String, Double> getColor2RGB() {
    HashMap<String, Double> rgb = new HashMap<>();
    rgb.put("r", color2.getRed());
    rgb.put("g", color2.getGreen());
    rgb.put("b", color2.getBlue());
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

  public void replaceCSS() throws IOException {
    // TODO figure out why this method is busted. I'll have to talk with Amitai, probably.
    //    {
    //      FileInputStream in = new FileInputStream(this.mainCSS);
    //      FileOutputStream out = new
    // FileOutputStream(App.class.getResource("css/mainStyle.css").toString());
    //      try {
    //        int n;
    //        while ((n = in.read()) != -1) {
    //          out.write(n);
    //        }
    //      } catch (IOException e) {
    //        e.printStackTrace();
    //      } finally {
    //        if (in != null) {
    //          in.close();
    //        }
    //        if (out != null) {
    //          out.close();
    //        }
    //      }
    //      System.out.println("File Overwritten");
    //    }
  }
}

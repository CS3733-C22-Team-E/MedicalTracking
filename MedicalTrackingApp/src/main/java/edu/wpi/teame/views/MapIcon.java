package edu.wpi.teame.views;

public class MapIcon {
  Double PixelX;
  Double PixelY;
  String IconResourcePath;
  String Descriptor;

  public MapIcon(Double pixelX, Double pixelY, String iconResourcePath, String descriptor) {
    PixelX = pixelX;
    PixelY = pixelY;
    IconResourcePath = iconResourcePath;
    Descriptor = descriptor;
  }

  public Double getPixelX() {
    return PixelX;
  }

  public void setPixelX(Double pixelX) {
    PixelX = pixelX;
  }

  public Double getPixelY() {
    return PixelY;
  }

  public void setPixelY(Double pixelY) {
    PixelY = pixelY;
  }

  public String getIconResourcePath() {
    return IconResourcePath;
  }

  public void setIconResourcePath(String iconResourcePath) {
    IconResourcePath = iconResourcePath;
  }

  public String getDescriptor() {
    return Descriptor;
  }

  public void setDescriptor(String descriptor) {
    Descriptor = descriptor;
  }
}

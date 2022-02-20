package edu.wpi.teame.view.map.Icons;

import edu.wpi.teame.model.Location;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MapLocationDot {
  Location location;
  Circle dot;

  public MapLocationDot(Location location, double TranslateX, double TranslateY) {
    this.dot = new Circle(5, Color.BLACK);
    this.dot.setTranslateX(TranslateX);
    this.dot.setTranslateY(TranslateY);
    this.location = location;
  }

  public Circle getIcon() {
    return this.dot;
  }

  public Location getLocation() {
    return this.location;
  }

  public double getDistanceToLocation(double xCoordinate, double yCoordinate) {
    return Math.sqrt(
        Math.pow((xCoordinate - this.location.getX()), 2)
            + Math.pow((yCoordinate - this.location.getY()), 2));
  }
}

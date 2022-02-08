package edu.wpi.teame.model;

import edu.wpi.teame.db.Location;
import javafx.scene.image.ImageView;

public class MapLocationDot {
  ImageView dot;
  Location location;

  public MapLocationDot(ImageView dot, Location location) {
    this.dot = dot;
    this.location = location;
  }

  public ImageView getImageView() {
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

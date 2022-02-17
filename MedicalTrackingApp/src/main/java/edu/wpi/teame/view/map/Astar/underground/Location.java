package edu.wpi.teame.view.map.Astar.underground;

import edu.wpi.teame.view.map.Astar.GraphNode;
import java.util.StringJoiner;

public class Location implements GraphNode {
  private final String id;
  private final String name;
  private final double X;
  private final double Y;

  public Location(String id, String name, double latitude, double longitude) {
    this.id = id;
    this.name = name;
    this.X = latitude;
    this.Y = longitude;
  }

  @Override
  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public double getX() {
    return X;
  }

  public double getY() {
    return Y;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Location.class.getSimpleName() + "[", "]")
        .add("id='" + id + "'")
        .add("name='" + name + "'")
        .add("X=" + X)
        .add("Y=" + Y)
        .toString();
  }
}

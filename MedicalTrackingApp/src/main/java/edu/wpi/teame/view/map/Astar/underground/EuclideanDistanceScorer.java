package edu.wpi.teame.view.map.Astar.underground;

import edu.wpi.teame.model.Location;
import edu.wpi.teame.view.map.Astar.Scorer;

public class EuclideanDistanceScorer implements Scorer<Location> {
  @Override
  public double computeCost(Location from, Location to) {
    return Math.sqrt(Math.pow(from.getX() - to.getX(), 2) + Math.pow(from.getY() - to.getY(), 2));
  }
}

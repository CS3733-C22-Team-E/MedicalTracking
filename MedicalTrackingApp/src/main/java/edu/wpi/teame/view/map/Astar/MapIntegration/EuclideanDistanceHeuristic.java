package edu.wpi.teame.view.map.Astar.MapIntegration;

import edu.wpi.teame.model.Location;
import edu.wpi.teame.view.map.Astar.Heuristic;

public class EuclideanDistanceHeuristic implements Heuristic<Location> {
  @Override
  public double computeCost(Location from, Location to) {
     return Math.sqrt(Math.pow(from.getX() - to.getX(), 2) + Math.pow(from.getY() - to.getY(), 2));
  }
}

package edu.wpi.teame.view.map.Astar.MapIntegration;

import edu.wpi.teame.model.Location;
import edu.wpi.teame.view.map.Astar.Heuristic;

public class ManhattanDistanceHeuristic implements Heuristic<Location> {
    @Override
    public double computeCost(Location from, Location to) {
        return ((to.getX() - from.getX()) + (to.getY() - from.getY()));
    }
}
package edu.wpi.teame.view.map.Astar;

public interface Heuristic<T extends GraphNode> {
  double computeCost(T from, T to);
}

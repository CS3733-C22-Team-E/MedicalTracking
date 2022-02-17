package edu.wpi.teame.view.map.Astar;

public interface Scorer<T extends GraphNode> {
  double computeCost(T from, T to);
}

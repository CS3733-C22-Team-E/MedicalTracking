package edu.wpi.teame.view.map.Astar;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Graph<T extends GraphNode> {
  private final Set<T> nodes;

  private final Map<Integer, Set<Integer>> connections;

  public Graph(Set<T> nodes, Map<Integer, Set<Integer>> connections) {
    this.nodes = nodes;
    this.connections = connections;
  }

  public T getNode(int id) {
    return nodes.stream()
        .filter(node -> node.getId() == id)
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("No node found with ID"));
  }

  public Set<T> getConnections(T node) {
    return connections.get(node.getId()).stream().map(this::getNode).collect(Collectors.toSet());
  }
}

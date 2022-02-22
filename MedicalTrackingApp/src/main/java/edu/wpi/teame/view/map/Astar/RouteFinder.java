package edu.wpi.teame.view.map.Astar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RouteFinder<T extends GraphNode> {
  private final Graph<T> graph;
  private final Heuristic<T> nextNodeHeuristic;
  private final Heuristic<T> targetHeuristic;

  public RouteFinder(Graph<T> graph, Heuristic<T> nextNodeHeuristic, Heuristic<T> targetHeuristic) {
    this.graph = graph;
    this.nextNodeHeuristic = nextNodeHeuristic;
    this.targetHeuristic = targetHeuristic;
  }

  public List<T> findRoute(T from, T to) {
    Map<T, RouteNode<T>> allNodes = new HashMap<>();
    Queue<RouteNode> openSet = new PriorityQueue<>();

    RouteNode<T> start = new RouteNode<>(from, null, 0d, targetHeuristic.computeCost(from, to));
    allNodes.put(from, start);
    openSet.add(start);

    while (!openSet.isEmpty()) {
      log.debug(
          "Open Set contains: "
              + openSet.stream().map(RouteNode::getCurrent).collect(Collectors.toSet()));
      RouteNode<T> next = openSet.poll();
      log.debug("Looking at node: " + next);
      if (next.getCurrent().equals(to)) {
        log.debug("Found our destination!");

        List<T> route = new ArrayList<>();
        RouteNode<T> current = next;
        do {
          route.add(0, current.getCurrent());
          current = allNodes.get(current.getPrevious());
        } while (current != null);

        log.debug("Route: " + route);
        return route;
      }

      graph
          .getConnections(next.getCurrent())
          .forEach(
              connection -> {
                double newScore =
                    next.getRouteScore()
                        + nextNodeHeuristic.computeCost(next.getCurrent(), connection);
                RouteNode<T> nextNode =
                    allNodes.getOrDefault(connection, new RouteNode<>(connection));
                allNodes.put(connection, nextNode);

                if (nextNode.getRouteScore() > newScore) {
                  nextNode.setPrevious(next.getCurrent());
                  nextNode.setRouteScore(newScore);
                  nextNode.setEstimatedScore(
                      newScore + targetHeuristic.computeCost(connection, to));
                  openSet.add(nextNode);
                  log.debug("Found a better route to node: " + nextNode);
                }
              });
    }

    throw new IllegalStateException("No route found");
  }
}

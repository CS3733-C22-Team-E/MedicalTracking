package edu.wpi.teame.view.map.Astar.MapIntegration;

import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.objectManagers.EdgeManager;
import edu.wpi.teame.model.Edge;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.view.map.Astar.AstarVisualizer;
import edu.wpi.teame.view.map.Astar.Graph;
import edu.wpi.teame.view.map.Astar.RouteFinder;
import edu.wpi.teame.view.map.Icons.MapEquipmentIcon;
import java.sql.SQLException;
import java.util.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class PathFinder {
  private Graph<Location> locationGraph;
  private RouteFinder<Location> routeFinder;
  private Set<Location> locations = new HashSet<>();
  private Map<Integer, Set<Integer>> connections = new HashMap<>();
  private AstarVisualizer Visual;
  private StackPane DrawPane;
  private Location startTest;
  private Location endTest;
  private ArrayList<Rectangle> routeVisual = new ArrayList<>();
  private List<Edge> edgeList = null;

  public PathFinder(StackPane Draw, double MapWidth, double MapHeight) throws SQLException {
    DrawPane = Draw;
    Visual = new AstarVisualizer(Draw, MapWidth, MapHeight);
    createConnections();
    locationGraph = new Graph<>(locations, connections);
    routeFinder =
        new RouteFinder<>(
            locationGraph, new EuclideanDistanceHeuristic(), new EuclideanDistanceHeuristic());
  }

  public void refreshLocationsFromDB() throws SQLException {
    locations.addAll(DBManager.getManager(DataBaseObjectType.Location).getAll());
  }

  public void createConnections() throws SQLException {
    connections.clear();
    if (edgeList == null || edgeList.size() == 0) {
      edgeList = ((EdgeManager) DBManager.getManager(DataBaseObjectType.Edge)).getAll();
    }

    for (Edge edge : edgeList) {
      connections.put(edge.getStart().getId(), new HashSet<Integer>());
    }

    for (Edge edge : edgeList) {
      connections.get(edge.getStart().getId()).add(edge.getEnd().getId());
    }
  }

  public void SelectFloor(double WIDTH, double HEIGHT) throws SQLException {
    Visual.clearConnections();
    Visual.setMap(WIDTH, HEIGHT);
  }

  public void RemoveRoute() {
    DrawPane.getChildren().removeAll(routeVisual);
    routeVisual.clear();
  }

  public List<Location> FindAndDrawRoute(int StartID, int EndID) throws SQLException {
    // TODO there's gotta be a better way to do this ->tried call DBManager....get(ID) and it kept
    // returning null
    // DBManager.getManager(DataBaseObjectType.Location).get(StartID);
    Visual.clearConnections();
    locations.stream()
        .forEach(
            node -> {
              if (node.getId() == StartID) {
                startTest = node;
                System.out.println("Hit Start");
              }
              if (node.getId() == EndID) {
                endTest = node;
                System.out.println("Hit End");
              }
            });
    Location From = startTest;
    Location To = endTest;
    try {
      List<Location> route = routeFinder.findRoute(From, To);
      for (int i = 1; i < route.size(); i++) {
        Location initNode = route.get(i - 1);
        Location endNode = route.get(i);
        routeVisual.add(
            Visual.createConnection(
                initNode.getX(), initNode.getY(), endNode.getX(), endNode.getY()));
      }
      System.out.println(route.size());
      return route;
    } catch (IllegalStateException e) {
      System.out.println("No Valid Route From " + From.getId() + " To " + To.getId());
    }
    return null;
  }

  public List<Location> FindAndDrawRoute(int StartID, int EndID, Paint color) throws SQLException {
    // TODO there's gotta be a better way to do this ->tried call DBManager....get(ID) and it kept
    // returning null
    // DBManager.getInstance().getLocationManager().get(StartID);
    Visual.clearConnections();
    locations.stream()
        .forEach(
            node -> {
              if (node.getId() == StartID) {
                startTest = node;
                System.out.println("Hit Start: " + node.getId());
              }
              if (node.getId() == EndID) {
                endTest = node;
                System.out.println("Hit End: " + node.getId());
              }
            });
    Location From = startTest;
    Location To = endTest;
    List<Location> route;
    try {
      route = routeFinder.findRoute(From, To);
      for (int i = 1; i < route.size(); i++) {
        Location initNode = route.get(i - 1);
        Location endNode = route.get(i);
        routeVisual.add(
            Visual.createConnection(
                initNode.getX(), initNode.getY(), endNode.getX(), endNode.getY(), color));
      }
      return route;
    } catch (IllegalStateException e) {
      System.out.println("No Valid Route From " + From.getId() + " To " + To.getId());
      return null;
    }
  }

  public void FindAndDrawRoute(
      int StartID, int EndID, MapEquipmentIcon iconToBeMoved, double timerSeconds)
      throws SQLException {
    // TODO there's gotta be a better way to do this ->tried call DBManager....get(ID) and it kept
    // returning null
    // DBManager.getInstance().getLocationManager().get(StartID);
    Visual.clearConnections();
    locations.stream()
        .forEach(
            node -> {
              if (node.getId() == StartID) {
                startTest = node;
                System.out.println("Hit Start");
              }
              if (node.getId() == EndID) {
                endTest = node;
                System.out.println("Hit End");
              }
            });
    Location From = startTest;
    Location To = endTest;
    List<Location> route = null;
    try {
      route = routeFinder.findRoute(From, To);
    } catch (IllegalStateException e) {
      System.out.println("No Valid Route From " + From.getId() + " To " + To.getId());
    }
  }
}

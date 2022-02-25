package edu.wpi.teame.view.map.Astar.MapIntegration;

import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.objectManagers.EdgeManager;
import edu.wpi.teame.model.Edge;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.FloorType;
import edu.wpi.teame.view.map.Astar.AstarVisualizer;
import edu.wpi.teame.view.map.Astar.Graph;
import edu.wpi.teame.view.map.Astar.RouteFinder;
import java.sql.SQLException;
import java.util.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class PathFinder {
  private Graph<Location> locationGraph;
  private RouteFinder<Location> routeFinder;
  private Set<Location> locations = new HashSet<>();
  private Map<Integer, Set<Integer>> connections = new HashMap<>();
  private AstarVisualizer Visual;
  private StackPane DrawPane;
  private Location startTest;
  private Location endTest;
  private List<Edge> edgeList = null;
  private HashMap<FloorType, ArrayList<RouteVisual>> RoutesByFloor = new HashMap<>();

  public PathFinder(StackPane Draw, double MapWidth, double MapHeight) throws SQLException {
    DrawPane = Draw;
    Visual = new AstarVisualizer(Draw, MapWidth, MapHeight);
    createConnections();
    locationGraph = new Graph<>(locations, connections);
    routeFinder =
        new RouteFinder<>(
            locationGraph, new EuclideanDistanceHeuristic(), new EuclideanDistanceHeuristic());
    for (FloorType currFloor : FloorType.values()) {
      RoutesByFloor.put(currFloor, new ArrayList<>());
    }
  }

  public void refreshLocationsFromDB() throws SQLException {
    locations.addAll(DBManager.getInstance().getManager(DataBaseObjectType.Location).getAll());
  }

  public void createConnections() throws SQLException {
    connections.clear();
    if (edgeList == null || edgeList.size() == 0) {
      edgeList =
          ((EdgeManager) DBManager.getInstance().getManager(DataBaseObjectType.Edge)).getAll();
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

  public void switchFloors(FloorType floor) {
    for (FloorType currFloor : FloorType.values()) {
      if (currFloor == floor) {
        for (RouteVisual route : RoutesByFloor.get(floor)) {
          DrawPane.getChildren().addAll(route.route);
        }
      } else {
        for (RouteVisual route : RoutesByFloor.get(currFloor)) {
          DrawPane.getChildren().removeAll(route.route);
        }
      }
    }
  }

  public void RemoveRoute(Location start, Location end) {
    System.out.println("Route To Delete");
    for (RouteVisual Route : RoutesByFloor.get(start.getFloor())) {
      System.out.println("Found Route From: " + Route.StartID + " To " + Route.EndID);
      System.out.println("Trying to Delete Route From: " + start.getId() + " To " + end.getId());
      if (Route.StartID == start.getId() & Route.EndID == end.getId()) {
        DrawPane.getChildren().removeAll(Route.route);
        RoutesByFloor.get(start.getFloor()).remove(Route);
      }
    }
  }

  public List<Location> FindAndDrawRoute(int StartID, int EndID, FloorType FloorBeingShown)
      throws SQLException {
    // TODO there's gotta be a better way to do this ->tried call DBManager....get(ID) and it kept
    // returning null
    // DBManager.getInstance().getManager(DataBaseObjectType.Location).get(StartID);
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
      //      RouteVisual currRoute = new RouteVisual(From.getId(), To.getId());
      //      for (int i = 1; i < route.size(); i++) {
      //        Location initNode = route.get(i - 1);
      //        Location endNode = route.get(i);
      //        currRoute.addRectangle(
      //            Visual.createConnection(
      //                initNode.getX(), initNode.getY(), endNode.getX(), endNode.getY()),
      // initNode.getFloor());
      //      }
      //      RoutesByFloor.get(From.getFloor()).add(currRoute);
      HashMap<FloorType, RouteVisual> Router = new HashMap<>();
      Color random = Color.color(Math.random(), Math.random(), Math.random());
      for (int i = 1; i < route.size(); i++) {
        Location start = route.get(i - 1);
        Location end = route.get(i);
        if (start.getFloor() == end.getFloor()) {
          if (Router.get(start.getFloor()) == null) {
            Router.put(start.getFloor(), new RouteVisual(From.getId(), To.getId(), random));
            Router.get(start.getFloor())
                .addRectangle(
                    Visual.createConnection(
                        start.getX(),
                        start.getY(),
                        end.getX(),
                        end.getY(),
                        start.getFloor(),
                        FloorBeingShown),
                    start.getFloor());
          } else {
            Router.get(start.getFloor())
                .addRectangle(
                    Visual.createConnection(
                        start.getX(),
                        start.getY(),
                        end.getX(),
                        end.getY(),
                        start.getFloor(),
                        FloorBeingShown),
                    start.getFloor());
          }
        } else {
          System.out.println("Switching Floors");
        }
      }
      for (FloorType currFloor : FloorType.values()) {
        if (Router.get(currFloor) != null) {
          RoutesByFloor.get(currFloor).add(Router.get(currFloor));
        }
      }
    } catch (IllegalStateException e) {
      System.out.println("No Valid Route From " + From.getId() + " To " + To.getId());
    }
    return null;
  }

  public List<Location> FindAndDrawRoute(
      int StartID, int EndID, Paint color, FloorType FloorBeingShown) throws SQLException {
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
    try {
      List<Location> route = routeFinder.findRoute(From, To);
      //      RouteVisual currRoute = new RouteVisual(From.getId(), To.getId());
      //      for (int i = 1; i < route.size(); i++) {
      //        Location initNode = route.get(i - 1);
      //        Location endNode = route.get(i);
      //        currRoute.addRectangle(
      //            Visual.createConnection(
      //                initNode.getX(), initNode.getY(), endNode.getX(), endNode.getY()),
      // initNode.getFloor());
      //      }
      //      RoutesByFloor.get(From.getFloor()).add(currRoute);
      HashMap<FloorType, RouteVisual> Router = new HashMap<>();
      Color random = Color.color(Math.random(), Math.random(), Math.random());
      for (int i = 1; i < route.size(); i++) {
        Location start = route.get(i - 1);
        Location end = route.get(i);
        if (start.getFloor() == end.getFloor()) {
          if (Router.get(start.getFloor()) == null) {
            Router.put(start.getFloor(), new RouteVisual(From.getId(), To.getId(), random));
            Router.get(start.getFloor())
                .addRectangle(
                    Visual.createConnection(
                        start.getX(),
                        start.getY(),
                        end.getX(),
                        end.getY(),
                        start.getFloor(),
                        FloorBeingShown),
                    start.getFloor());
          } else {
            Router.get(start.getFloor())
                .addRectangle(
                    Visual.createConnection(
                        start.getX(),
                        start.getY(),
                        end.getX(),
                        end.getY(),
                        start.getFloor(),
                        FloorBeingShown),
                    start.getFloor());
          }
        } else {
          System.out.println("Switching Floors");
        }
      }
      for (FloorType currFloor : FloorType.values()) {
        if (Router.get(currFloor) != null) {
          RoutesByFloor.get(currFloor).add(Router.get(currFloor));
        }
      }
      return route;
    } catch (IllegalStateException e) {
      System.out.println("No Valid Route From " + From.getId() + " To " + To.getId());
      return null;
    }
  }
}

package edu.wpi.teame.view.map.Astar.MapIntegration;

import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.objectManagers.EdgeManager;
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
    ((EdgeManager) DBManager.getManager(DataBaseObjectType.Edge))
        .getAll()
        .forEach(
            edge -> {
              connections.put(edge.getStart().getId(), new HashSet<Integer>());
            });
    ((EdgeManager) DBManager.getManager(DataBaseObjectType.Edge))
        .getAll()
        .forEach(
            edge -> {
              System.out.println("Init");
              connections.get(edge.getStart().getId()).add(edge.getEnd().getId());
            });
    //    connections.put(1, Stream.of(22).collect(Collectors.toSet()));
    //    connections.put(2, Stream.of(29).collect(Collectors.toSet()));
    //    connections.put(3, Stream.of(26).collect(Collectors.toSet()));
    //    connections.put(4, Stream.of(30).collect(Collectors.toSet()));
    //    connections.put(5, Stream.of(9, 42).collect(Collectors.toSet()));
    //    connections.put(6, Stream.of(41).collect(Collectors.toSet()));
    //    connections.put(7, Stream.of(12).collect(Collectors.toSet()));
    //    connections.put(8, Stream.of(37).collect(Collectors.toSet()));
    //    connections.put(9, Stream.of(5).collect(Collectors.toSet()));
    //    connections.put(10, Stream.of(11).collect(Collectors.toSet()));
    //
    //    connections.put(11, Stream.of(53, 12, 10, 40).collect(Collectors.toSet()));
    //    connections.put(12, Stream.of(7, 13, 11).collect(Collectors.toSet()));
    //    connections.put(13, Stream.of(14, 12, 46).collect(Collectors.toSet()));
    //    connections.put(14, Stream.of(48, 13, 15).collect(Collectors.toSet()));
    //    connections.put(15, Stream.of(14, 56).collect(Collectors.toSet()));
    //    connections.put(16, Stream.of(56, 17, 27).collect(Collectors.toSet()));
    //    connections.put(17, Stream.of(16, 18).collect(Collectors.toSet()));
    //    connections.put(18, Stream.of(17, 22, 20).collect(Collectors.toSet()));
    //    connections.put(19, Stream.of(22, 54).collect(Collectors.toSet()));
    //    connections.put(20, Stream.of(18, 21, 23).collect(Collectors.toSet()));
    //    connections.put(21, Stream.of(20).collect(Collectors.toSet()));
    //    connections.put(22, Stream.of(18, 19, 1).collect(Collectors.toSet()));
    //    connections.put(23, Stream.of(20, 49, 24).collect(Collectors.toSet()));
    //    connections.put(24, Stream.of(23, 25).collect(Collectors.toSet()));
    //    connections.put(25, Stream.of(24, 28, 41).collect(Collectors.toSet()));
    //    connections.put(26, Stream.of(27, 51, 3).collect(Collectors.toSet()));
    //    connections.put(27, Stream.of(16, 41, 26).collect(Collectors.toSet()));
    //
    //    connections.put(28, Stream.of(25, 29, 30).collect(Collectors.toSet()));
    //    connections.put(29, Stream.of(2, 50, 28).collect(Collectors.toSet()));
    //    connections.put(30, Stream.of(28, 4, 31).collect(Collectors.toSet()));
    //    connections.put(31, Stream.of(30, 57, 37).collect(Collectors.toSet()));
    //    connections.put(32, Stream.of(33, 57, 44).collect(Collectors.toSet()));
    //    connections.put(33, Stream.of(34, 32).collect(Collectors.toSet()));
    //    connections.put(34, Stream.of(35, 33, 55).collect(Collectors.toSet()));
    //    connections.put(35, Stream.of(40, 34, 36).collect(Collectors.toSet()));
    //    connections.put(36, Stream.of(35, 42).collect(Collectors.toSet()));
    //    connections.put(37, Stream.of(8, 31, 38).collect(Collectors.toSet()));
    //    connections.put(38, Stream.of(37, 43, 52).collect(Collectors.toSet()));
    //    connections.put(39, Stream.of(52, 47).collect(Collectors.toSet()));
    //    connections.put(40, Stream.of(11, 45, 35).collect(Collectors.toSet()));
    //    connections.put(41, Stream.of(25, 6, 27).collect(Collectors.toSet()));
    //    connections.put(42, Stream.of(5, 36).collect(Collectors.toSet()));
    //    connections.put(43, Stream.of(38).collect(Collectors.toSet()));
    //    connections.put(44, Stream.of(32).collect(Collectors.toSet()));
    //    connections.put(45, Stream.of(40).collect(Collectors.toSet()));
    //    connections.put(46, Stream.of(13).collect(Collectors.toSet()));
    //    connections.put(47, Stream.of(39).collect(Collectors.toSet()));
    //    connections.put(48, Stream.of(14).collect(Collectors.toSet()));
    //    connections.put(49, Stream.of(23).collect(Collectors.toSet()));
    //    connections.put(50, Stream.of(29).collect(Collectors.toSet()));
    //    connections.put(51, Stream.of(26).collect(Collectors.toSet()));
    //    connections.put(52, Stream.of(38, 39).collect(Collectors.toSet()));
    //    connections.put(53, Stream.of(11, 73).collect(Collectors.toSet()));
    //    connections.put(54, Stream.of(19).collect(Collectors.toSet()));
    //    connections.put(55, Stream.of(34).collect(Collectors.toSet()));
    //    connections.put(56, Stream.of(15, 76, 101, 16, 122, 114).collect(Collectors.toSet()));
    //    connections.put(57, Stream.of(32, 77, 102, 31, 123, 115).collect(Collectors.toSet()));
    //    connections.put(58, Stream.of(71).collect(Collectors.toSet()));
    //    connections.put(59, Stream.of(69).collect(Collectors.toSet()));
    //    connections.put(60, Stream.of(61, 63, 76).collect(Collectors.toSet()));
    //    connections.put(61, Stream.of(70, 62, 60).collect(Collectors.toSet()));
    //    connections.put(62, Stream.of(73, 61).collect(Collectors.toSet()));
    //    connections.put(63, Stream.of(64, 60).collect(Collectors.toSet()));
    //    connections.put(64, Stream.of(71, 72, 65, 63).collect(Collectors.toSet()));
    //    connections.put(65, Stream.of(66, 64).collect(Collectors.toSet()));
    //    connections.put(66, Stream.of(74, 67, 65).collect(Collectors.toSet()));
    //    connections.put(67, Stream.of(75, 68, 66).collect(Collectors.toSet()));
    //    connections.put(68, Stream.of(78, 77, 67).collect(Collectors.toSet()));
    //    connections.put(69, Stream.of(59, 77).collect(Collectors.toSet()));
    //    connections.put(70, Stream.of(61).collect(Collectors.toSet()));
    //    connections.put(71, Stream.of(58, 64).collect(Collectors.toSet()));
    //    connections.put(72, Stream.of(64).collect(Collectors.toSet()));
    //    connections.put(73, Stream.of(53, 62).collect(Collectors.toSet()));
    //    connections.put(74, Stream.of(66).collect(Collectors.toSet()));
    //    connections.put(75, Stream.of(67).collect(Collectors.toSet()));
    //    connections.put(76, Stream.of(60, 101, 122, 114, 56).collect(Collectors.toSet()));
    //    connections.put(77, Stream.of(69, 102, 68, 123, 115, 57).collect(Collectors.toSet()));
    //    connections.put(78, Stream.of(68).collect(Collectors.toSet()));
    //    connections.put(79, Stream.of(92).collect(Collectors.toSet()));
    //    connections.put(80, Stream.of(97).collect(Collectors.toSet()));
    //    connections.put(81, Stream.of(89).collect(Collectors.toSet()));
    //    connections.put(82, Stream.of(93).collect(Collectors.toSet()));
    //    connections.put(83, Stream.of(85).collect(Collectors.toSet()));
    //    connections.put(84, Stream.of(98, 85).collect(Collectors.toSet()));
    //    connections.put(85, Stream.of(84, 83, 86).collect(Collectors.toSet()));
    //    connections.put(86, Stream.of(85, 87).collect(Collectors.toSet()));
    //    connections.put(87, Stream.of(100, 86, 102).collect(Collectors.toSet()));
    //    connections.put(88, Stream.of(89, 101, 97).collect(Collectors.toSet()));
    //    connections.put(89, Stream.of(81, 90, 88).collect(Collectors.toSet()));
    //    connections.put(90, Stream.of(99, 91, 89).collect(Collectors.toSet()));
    //    connections.put(91, Stream.of(92, 93, 90).collect(Collectors.toSet()));
    //    connections.put(92, Stream.of(79, 91).collect(Collectors.toSet()));
    //    connections.put(93, Stream.of(82, 94, 91).collect(Collectors.toSet()));
    //    connections.put(94, Stream.of(95, 93, 142).collect(Collectors.toSet()));
    //    connections.put(95, Stream.of(96, 94).collect(Collectors.toSet()));
    //    connections.put(96, Stream.of(97, 95).collect(Collectors.toSet()));
    //    connections.put(97, Stream.of(80, 88, 96).collect(Collectors.toSet()));
    //    connections.put(98, Stream.of(84).collect(Collectors.toSet()));
    //    connections.put(99, Stream.of(90).collect(Collectors.toSet()));
    //    connections.put(100, Stream.of(87).collect(Collectors.toSet()));
    //    connections.put(101, Stream.of(88, 122, 114, 56, 76).collect(Collectors.toSet()));
    //    connections.put(102, Stream.of(87, 123, 115, 57, 77).collect(Collectors.toSet()));
    //    connections.put(103, Stream.of(104).collect(Collectors.toSet()));
    //    connections.put(104, Stream.of(109, 103).collect(Collectors.toSet()));
    //    connections.put(105, Stream.of(112, 114).collect(Collectors.toSet()));
    //    connections.put(106, Stream.of(115, 111).collect(Collectors.toSet()));
    //    connections.put(107, Stream.of(108, 113).collect(Collectors.toSet()));
    //    connections.put(108, Stream.of(107).collect(Collectors.toSet()));
    //    connections.put(109, Stream.of(114, 104).collect(Collectors.toSet()));
    //    connections.put(110, Stream.of(113, 115).collect(Collectors.toSet()));
    //
    //    connections.put(111, Stream.of(106, 112).collect(Collectors.toSet()));
    //    connections.put(112, Stream.of(105, 111).collect(Collectors.toSet()));
    //    connections.put(113, Stream.of(107, 110).collect(Collectors.toSet()));
    //    connections.put(114, Stream.of(105, 56, 76, 101, 109, 122).collect(Collectors.toSet()));
    //    connections.put(115, Stream.of(110, 57, 77, 102, 106, 123).collect(Collectors.toSet()));
    //    connections.put(116, Stream.of(117, 123, 124).collect(Collectors.toSet()));
    //    connections.put(117, Stream.of(118, 116).collect(Collectors.toSet()));
    //    connections.put(118, Stream.of(119, 117).collect(Collectors.toSet()));
    //    connections.put(119, Stream.of(120, 122, 118).collect(Collectors.toSet()));
    //    connections.put(120, Stream.of(121, 119).collect(Collectors.toSet()));
    //    connections.put(121, Stream.of(120).collect(Collectors.toSet()));
    //    connections.put(122, Stream.of(114, 56, 76, 101, 119).collect(Collectors.toSet()));
    //    connections.put(123, Stream.of(115, 57, 77, 102, 116).collect(Collectors.toSet()));
    //    connections.put(124, Stream.of(116).collect(Collectors.toSet()));
    //
    //    connections.put(125, Stream.of(21).collect(Collectors.toSet()));
    //    connections.put(
    //        126,
    //        Stream.of(130, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 95)
    //            .collect(Collectors.toSet()));
    //    connections.put(
    //        127,
    //        Stream.of(131, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 94)
    //            .collect(Collectors.toSet()));
    //    connections.put(128, Stream.of(93, 94, 91).collect(Collectors.toSet()));
    //    connections.put(129, Stream.of(95, 96).collect(Collectors.toSet()));
    //    connections.put(130, Stream.of(126).collect(Collectors.toSet()));
    //    connections.put(131, Stream.of(127).collect(Collectors.toSet()));
    //    connections.put(132, Stream.of(94, 95).collect(Collectors.toSet()));
    //    connections.put(133, Stream.of(127, 94, 134).collect(Collectors.toSet())); // Patient Bed
    // Start
    //    connections.put(134, Stream.of(127, 94, 133, 135).collect(Collectors.toSet()));
    //    connections.put(135, Stream.of(127, 94, 134, 136).collect(Collectors.toSet()));
    //    connections.put(136, Stream.of(127, 94, 135, 137).collect(Collectors.toSet()));
    //    connections.put(137, Stream.of(127, 94, 136, 138).collect(Collectors.toSet()));
    //    connections.put(138, Stream.of(127, 94, 137, 139).collect(Collectors.toSet()));
    //    connections.put(139, Stream.of(127, 94, 138, 140).collect(Collectors.toSet()));
    //    connections.put(140, Stream.of(127, 94, 139, 141).collect(Collectors.toSet()));
    //    connections.put(141, Stream.of(127, 94, 140, 142).collect(Collectors.toSet()));
    //    connections.put(142, Stream.of(127, 94, 141).collect(Collectors.toSet()));
    //    connections.put(143, Stream.of(126, 95, 144).collect(Collectors.toSet())); // Next Pod
    //    connections.put(144, Stream.of(126, 95, 143, 145).collect(Collectors.toSet()));
    //    connections.put(145, Stream.of(126, 95, 144, 146).collect(Collectors.toSet()));
    //    connections.put(146, Stream.of(126, 95, 145, 147).collect(Collectors.toSet()));
    //    connections.put(147, Stream.of(126, 95, 146, 148).collect(Collectors.toSet()));
    //    connections.put(148, Stream.of(126, 95, 147, 149).collect(Collectors.toSet()));
    //    connections.put(149, Stream.of(126, 95, 148, 150).collect(Collectors.toSet()));
    //    connections.put(150, Stream.of(126, 95, 149, 151).collect(Collectors.toSet()));
    //    connections.put(151, Stream.of(126, 95, 150, 152).collect(Collectors.toSet()));
    //    connections.put(152, Stream.of(126, 95, 151).collect(Collectors.toSet())); // Patient Bed
    // End
    //    connections.put(153, Stream.of(105).collect(Collectors.toSet()));
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

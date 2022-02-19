package edu.wpi.teame.view.map.Astar.MapIntegration;

import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.FloorType;
import edu.wpi.teame.view.map.Astar.AstarVisualizer;
import edu.wpi.teame.view.map.Astar.Graph;
import edu.wpi.teame.view.map.Astar.RouteFinder;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.scene.layout.StackPane;

public class PathFinder {
  private Graph<Location> locationGraph;
  private RouteFinder<Location> routeFinder;
  private Set<Location> locations = new HashSet<>();
  private Map<Integer, Set<Integer>> connections = new HashMap<>();
  private AstarVisualizer Visual;
  private StackPane DrawPane;
  private Location startTest;
  private Location endTest;

  public PathFinder(StackPane Draw) throws SQLException {
    System.out.println("Attempt");
    DrawPane = Draw;
    Visual = new AstarVisualizer(Draw);
    // TODO add connections for our nodes that we placed on the map
    //TODO need to check for Nodal correctness
    connections.put(1, Stream.of(22).collect(Collectors.toSet()));
    connections.put(2, Stream.of(29).collect(Collectors.toSet()));
    connections.put(3, Stream.of(26).collect(Collectors.toSet()));
    connections.put(4, Stream.of(30).collect(Collectors.toSet()));
    connections.put(5, Stream.of(9).collect(Collectors.toSet()));
    connections.put(10, Stream.of(11).collect(Collectors.toSet()));
    connections.put(11, Stream.of(53, 12).collect(Collectors.toSet()));
    connections.put(12, Stream.of(7, 13).collect(Collectors.toSet()));
    connections.put(13, Stream.of(14).collect(Collectors.toSet()));
    connections.put(14, Stream.of(48).collect(Collectors.toSet()));
    connections.put(15, Stream.of(14).collect(Collectors.toSet()));
    connections.put(16, Stream.of(56).collect(Collectors.toSet()));
    connections.put(17, Stream.of(16).collect(Collectors.toSet()));
    connections.put(18, Stream.of(17, 22).collect(Collectors.toSet()));
    connections.put(19, Stream.of(22).collect(Collectors.toSet()));
    connections.put(20, Stream.of(18).collect(Collectors.toSet()));
    connections.put(21, Stream.of(20).collect(Collectors.toSet()));
    connections.put(23, Stream.of(20, 49).collect(Collectors.toSet()));
    connections.put(24, Stream.of(23).collect(Collectors.toSet()));
    connections.put(25, Stream.of(24).collect(Collectors.toSet()));
    connections.put(26, Stream.of(27).collect(Collectors.toSet()));
    connections.put(27, Stream.of(16, 41).collect(Collectors.toSet()));
    connections.put(28, Stream.of(25, 29, 30).collect(Collectors.toSet()));
    connections.put(31, Stream.of(30, 57, 37).collect(Collectors.toSet()));
    connections.put(32, Stream.of(33).collect(Collectors.toSet()));
    connections.put(33, Stream.of(34).collect(Collectors.toSet()));
    connections.put(34, Stream.of(35).collect(Collectors.toSet()));
    connections.put(35, Stream.of(40).collect(Collectors.toSet()));
    connections.put(36, Stream.of(35, 42).collect(Collectors.toSet()));
    connections.put(37, Stream.of(8).collect(Collectors.toSet()));
    connections.put(38, Stream.of(37, 43).collect(Collectors.toSet()));
    connections.put(40, Stream.of(11, 45).collect(Collectors.toSet()));
    connections.put(41, Stream.of(25, 6).collect(Collectors.toSet()));
    connections.put(42, Stream.of(5).collect(Collectors.toSet()));
    connections.put(44, Stream.of(32).collect(Collectors.toSet()));
    connections.put(46, Stream.of(13).collect(Collectors.toSet()));
    connections.put(47, Stream.of(39).collect(Collectors.toSet()));
    connections.put(50, Stream.of(29).collect(Collectors.toSet()));
    connections.put(51, Stream.of(26).collect(Collectors.toSet()));
    connections.put(52, Stream.of(38, 39).collect(Collectors.toSet()));
    connections.put(54, Stream.of(19).collect(Collectors.toSet()));
    connections.put(55, Stream.of(34).collect(Collectors.toSet()));
    connections.put(56, Stream.of(15, 76, 101).collect(Collectors.toSet()));
    connections.put(57, Stream.of(32, 77, 102).collect(Collectors.toSet()));
    connections.put(60, Stream.of(61, 63).collect(Collectors.toSet()));
    connections.put(61, Stream.of(70, 62).collect(Collectors.toSet()));
    connections.put(62, Stream.of(73).collect(Collectors.toSet()));
    connections.put(63, Stream.of(64).collect(Collectors.toSet()));
    connections.put(64, Stream.of(71, 72, 65).collect(Collectors.toSet()));
    connections.put(65, Stream.of(66).collect(Collectors.toSet()));
    connections.put(66, Stream.of(74, 67).collect(Collectors.toSet()));
    connections.put(67, Stream.of(75, 68).collect(Collectors.toSet()));
    connections.put(68, Stream.of(78, 77).collect(Collectors.toSet()));
    connections.put(69, Stream.of(59).collect(Collectors.toSet()));
    connections.put(71, Stream.of(58).collect(Collectors.toSet()));
    connections.put(73, Stream.of(53).collect(Collectors.toSet()));
    connections.put(76, Stream.of(60, 101).collect(Collectors.toSet()));
    connections.put(77, Stream.of(69, 102).collect(Collectors.toSet()));
    connections.put(84, Stream.of(98).collect(Collectors.toSet()));
    connections.put(85, Stream.of(84, 83).collect(Collectors.toSet()));
    connections.put(86, Stream.of(85).collect(Collectors.toSet()));
    connections.put(87, Stream.of(100, 86).collect(Collectors.toSet()));
    connections.put(88, Stream.of(89).collect(Collectors.toSet()));
    connections.put(89, Stream.of(81, 90).collect(Collectors.toSet()));
    connections.put(90, Stream.of(99, 91).collect(Collectors.toSet()));
    connections.put(91, Stream.of(92, 93).collect(Collectors.toSet()));
    connections.put(92, Stream.of(79).collect(Collectors.toSet()));
    connections.put(93, Stream.of(82, 94).collect(Collectors.toSet()));
    connections.put(94, Stream.of(95).collect(Collectors.toSet()));
    connections.put(95, Stream.of(96).collect(Collectors.toSet()));
    connections.put(96, Stream.of(97).collect(Collectors.toSet()));
    connections.put(97, Stream.of(80, 88).collect(Collectors.toSet()));
    connections.put(101, Stream.of(88).collect(Collectors.toSet()));
    connections.put(102, Stream.of(87).collect(Collectors.toSet()));
    connections.put(103, Stream.of(104).collect(Collectors.toSet()));
    connections.put(104, Stream.of(109).collect(Collectors.toSet()));
    connections.put(105, Stream.of(112).collect(Collectors.toSet()));
    connections.put(106, Stream.of(115).collect(Collectors.toSet()));
    connections.put(107, Stream.of(108).collect(Collectors.toSet()));
    connections.put(109, Stream.of(114).collect(Collectors.toSet()));
    connections.put(110, Stream.of(113).collect(Collectors.toSet()));
    connections.put(111, Stream.of(106, 112).collect(Collectors.toSet()));
    connections.put(113, Stream.of(107).collect(Collectors.toSet()));
    connections.put(114, Stream.of(105, 56, 76, 101).collect(Collectors.toSet()));
    connections.put(115, Stream.of(110, 57, 77, 102).collect(Collectors.toSet()));
    connections.put(116, Stream.of(117, 123).collect(Collectors.toSet()));
    connections.put(117, Stream.of(118).collect(Collectors.toSet()));
    connections.put(118, Stream.of(119).collect(Collectors.toSet()));
    connections.put(119, Stream.of(120, 122).collect(Collectors.toSet()));
    connections.put(120, Stream.of(121).collect(Collectors.toSet()));
    connections.put(122, Stream.of(114, 56, 76, 101).collect(Collectors.toSet()));
    connections.put(123, Stream.of(115, 57, 77, 102).collect(Collectors.toSet()));
    connections.put(124, Stream.of(116).collect(Collectors.toSet()));
    locationGraph = new Graph<>(locations, connections);
    routeFinder =
        new RouteFinder<>(
            locationGraph, new EuclideanDistanceScorer(), new EuclideanDistanceScorer());
  }

  public void SelectFloor(FloorType floor) throws SQLException {
    locations.clear();
    DBManager.getInstance().getLocationManager().getAll().stream()
        .forEach(
            node -> {
              if (node.getFloor() == floor) {
                locations.add(node);
                System.out.print("Loading Nodes to Path: ");
                System.out.println(node.getId());
              }
            });
  }

  public void FindAndDrawRoute(int StartID, int EndID) throws SQLException {
    // TODO there's gotta be a better way to do this ->tried call DBManager....get(ID) and it kept
    // returning null
    // DBManager.getInstance().getLocationManager().get(StartID);
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
    List<Location> route = routeFinder.findRoute(From, To);
    for (Location location : route) {
      System.out.println(location.getId());
    }
    for (int i = 1; i < route.size(); i++) {
      Location initNode = route.get(i - 1);
      Location endNode = route.get(i);
      Visual.createConnection(initNode.getX(), initNode.getY(), endNode.getX(), endNode.getY());
    }
  }
}

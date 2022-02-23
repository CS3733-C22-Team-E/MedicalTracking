package edu.wpi.teame.view.map.Astar;

import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class AstarVisualizer {
  StackPane layout;
  private double MAPWIDTH;
  private double MAPHEIGHT;
  private static final double MapConnectionWidth = 5;
  private ArrayList<Rectangle> routeConnections = new ArrayList<>();

  public AstarVisualizer(StackPane VisualizationPane, double Width, double Height) {
    layout = VisualizationPane;
    MAPWIDTH = Width;
    MAPHEIGHT = Height;
  }

  public void setMap(double MAPWIDTH_, double MAPHEIGHT_) {
    MAPWIDTH = MAPWIDTH_;
    MAPHEIGHT = MAPHEIGHT_;
  }

  public Rectangle createConnection(
      double StartX, double StartY, double EndX, double EndY, Paint color) {
    System.out.println("Creating Connection");
    Point2D distancePoint = new Point2D(EndX - StartX, EndY - StartY);
    double distance =
        Math.sqrt(Math.pow(distancePoint.getX(), 2) + Math.pow(distancePoint.getY(), 2));
    double theta = Math.atan2(distancePoint.getY(), distancePoint.getX());
    double ProjectX = StartX + Math.cos(theta) * distance / 2;
    double ProjectY = StartY + Math.sin(theta) * distance / 2;
    ProjectX = ProjectX - MAPWIDTH / 2;
    ProjectY = ProjectY - MAPHEIGHT / 2;
    Rectangle connection = new Rectangle();
    connection.setHeight(6);
    connection.setWidth(distance);
    connection.setTranslateX(ProjectX);
    connection.setTranslateY(ProjectY);
    connection.setRotate(Math.toDegrees(theta));
    connection.setFill(color);
    layout.getChildren().add(connection);
    routeConnections.add(connection);
    return connection;
  }

  public Rectangle createConnection(double StartX, double StartY, double EndX, double EndY) {
    Point2D distancePoint = new Point2D(EndX - StartX, EndY - StartY);
    double distance =
        Math.sqrt(Math.pow(distancePoint.getX(), 2) + Math.pow(distancePoint.getY(), 2));
    double theta = Math.atan2(distancePoint.getY(), distancePoint.getX());
    double ProjectX = StartX + Math.cos(theta) * distance / 2;
    double ProjectY = StartY + Math.sin(theta) * distance / 2;
    ProjectX = ProjectX - MAPWIDTH / 2;
    ProjectY = ProjectY - MAPHEIGHT / 2;
    Rectangle connection = new Rectangle();
    connection.setHeight(6);
    connection.setWidth(distance);
    connection.setTranslateX(ProjectX);
    connection.setTranslateY(ProjectY);
    connection.setRotate(Math.toDegrees(theta));
    layout.getChildren().add(connection);
    routeConnections.add(connection);
    return connection;
  }

  public void clearConnections() {
    layout.getChildren().stream()
        .forEach(
            Node -> {
              if (routeConnections.contains(Node)) {
                routeConnections.remove(Node);
              }
            });
  }
}

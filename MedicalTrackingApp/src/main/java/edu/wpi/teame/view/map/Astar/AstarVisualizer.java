package edu.wpi.teame.view.map.Astar;

import javafx.geometry.Point2D;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class AstarVisualizer {
  StackPane layout;
  private static final double MAPWIDTH = 5000;
  private static final double MAPHEIGHT = 1650;
  private static final double MapConnectionWidth = 6;

  public AstarVisualizer(StackPane VisualizationPane) {
    layout = VisualizationPane;
  }

  public void setMap(double MAPWIDTH_, double MAPHEIGHT_) {
    //    MAPWIDTH = MAPWIDTH_;
    //    MAPHEIGHT = MAPHEIGHT_;
  }

  public void createConnection(double StartX, double StartY, double EndX, double EndY) {
    Point2D distancePoint = new Point2D(EndX - StartX, EndY - StartY);
    double distance =
        Math.sqrt(Math.pow(distancePoint.getX(), 2) + Math.pow(distancePoint.getY(), 2));
    double theta = Math.atan2(distancePoint.getY(), distancePoint.getX());
    System.out.println(theta);
    double ProjectX = StartX + Math.cos(theta) * distance / 2;
    double ProjectY = StartY + Math.sin(theta) * distance / 2;
    System.out.println(ProjectX + " " + ProjectY);
    ProjectX = ProjectX - MAPWIDTH / 2;
    ProjectY = ProjectY - MAPHEIGHT / 2;
    Rectangle connection = new Rectangle();
    connection.setHeight(6);
    connection.setWidth(distance);
    connection.setTranslateX(ProjectX);
    connection.setTranslateY(ProjectY);
    connection.setRotate(Math.toDegrees(theta));
    layout.getChildren().add(connection);
  }
}

package edu.wpi.teame.view.map.Astar;

import javafx.geometry.Point2D;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class AstarVisualizer {
  StackPane layout;
  double MAPWIDTH;
  double MAPHEIGHT;
  private static final double MapConnectionWidth = 6;

  public AstarVisualizer(StackPane VisualizationPane) {
    layout = VisualizationPane;
  }

  public void setMap(double MAPWIDTH_, double MAPHEIGHT_) {
    MAPWIDTH = MAPWIDTH_;
    MAPHEIGHT = MAPHEIGHT_;
  }

  public void createConnection(Point2D startPoint, Point2D endPoint) {
    Point2D distancePoint =
        new Point2D(endPoint.getX() - startPoint.getX(), endPoint.getY() - startPoint.getY());
    double distance =
        Math.sqrt(Math.pow(distancePoint.getX(), 2) + Math.pow(distancePoint.getY(), 2));
    double theta = Math.atan2(distancePoint.getY(), distancePoint.getX());
    System.out.println(theta);
    double ProjectX = startPoint.getX() + Math.cos(theta) * distance / 2;
    double ProjectY = startPoint.getY() + Math.sin(theta) * distance / 2;
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

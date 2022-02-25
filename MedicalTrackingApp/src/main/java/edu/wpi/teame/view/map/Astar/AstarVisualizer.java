package edu.wpi.teame.view.map.Astar;

import edu.wpi.teame.model.enums.FloorType;
import edu.wpi.teame.view.map.Astar.MapIntegration.HeightWidth;
import edu.wpi.teame.view.map.Astar.MapIntegration.VisualNode;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.geometry.Point2D;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class AstarVisualizer {
  StackPane layout;
  private double MAPWIDTH;
  private double MAPHEIGHT;
  private static final double MapConnectionWidth = 5;
  private ArrayList<Rectangle> routeConnections = new ArrayList<>();
  private static final HashMap<FloorType, HeightWidth> imageSizes = new HashMap<>();

  static {
    imageSizes.put(FloorType.LowerLevel2, new HeightWidth(5000, 3400));
    imageSizes.put(FloorType.LowerLevel1, new HeightWidth(5000, 2301));
    imageSizes.put(FloorType.FirstFloor, new HeightWidth(5000, 1600));
    imageSizes.put(FloorType.SecondFloor, new HeightWidth(5000, 3400));
    imageSizes.put(FloorType.ThirdFloor, new HeightWidth(5000, 1650));
  }

  public AstarVisualizer(StackPane VisualizationPane, double Width, double Height) {
    layout = VisualizationPane;
    MAPWIDTH = Width;
    MAPHEIGHT = Height;
  }

  public void setMap(double MAPWIDTH_, double MAPHEIGHT_) {
    MAPWIDTH = MAPWIDTH_;
    MAPHEIGHT = MAPHEIGHT_;
  }

  public VisualNode createConnection(
      double StartX,
      double StartY,
      double EndX,
      double EndY,
      FloorType floor,
      FloorType ShownFloor) {
    System.out.println("Creating Edge");
    Point2D distancePoint = new Point2D(EndX - StartX, EndY - StartY);
    double distance =
        Math.sqrt(Math.pow(distancePoint.getX(), 2) + Math.pow(distancePoint.getY(), 2));
    double theta = Math.atan2(distancePoint.getY(), distancePoint.getX());
    double ProjectX = StartX + Math.cos(theta) * distance / 2;
    double ProjectY = StartY + Math.sin(theta) * distance / 2;
    ProjectX = ProjectX - imageSizes.get(floor).getWidth() / 2;
    ProjectY = ProjectY - imageSizes.get(floor).getHeight() / 2;
    VisualNode connection = new VisualNode();
    connection.setHeight(MapConnectionWidth);
    connection.setWidth(distance);
    connection.setTranslateX(ProjectX);
    connection.setTranslateY(ProjectY);
    connection.setRotate(Math.toDegrees(theta));
    if (floor == ShownFloor) {
      layout.getChildren().add(connection);
    }
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

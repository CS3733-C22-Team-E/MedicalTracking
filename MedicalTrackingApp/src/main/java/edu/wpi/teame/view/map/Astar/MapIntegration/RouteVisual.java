package edu.wpi.teame.view.map.Astar.MapIntegration;

import edu.wpi.teame.model.enums.FloorType;
import java.util.ArrayList;
import javafx.scene.paint.Color;

public class RouteVisual {
  public ArrayList<VisualNode> route = new ArrayList<>();
  int StartID;
  int EndID;
  Color randomColor;

  public RouteVisual(int start, int end) {
    this.StartID = start;
    this.EndID = end;
    randomColor = Color.color(Math.random(), Math.random(), Math.random());
  }

  public RouteVisual(int start, int end, Color RandomColor) {
    this.StartID = start;
    this.EndID = end;
    this.randomColor = RandomColor;
  }

  public VisualNode addRectangle(VisualNode node, FloorType floor) {
    node.setFill(randomColor);
    node.setFloor(floor);
    route.add(node);
    return node;
  }
}

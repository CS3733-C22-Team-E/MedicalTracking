package edu.wpi.teame.view.map.Astar.MapIntegration;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RouteVisual {
  public ArrayList<Rectangle> route = new ArrayList<>();
  int StartID;
  int EndID;
  Color randomColor;

  public RouteVisual(int start, int end) {
    this.StartID = start;
    this.EndID = end;
    randomColor = Color.color(Math.random(), Math.random(), Math.random());
  }

  public Rectangle addRectangle(Rectangle rect) {
    rect.setFill(randomColor);
    route.add(rect);
    return rect;
  }
}

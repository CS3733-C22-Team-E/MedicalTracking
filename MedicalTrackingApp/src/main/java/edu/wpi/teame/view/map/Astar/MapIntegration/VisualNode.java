package edu.wpi.teame.view.map.Astar.MapIntegration;

import edu.wpi.teame.model.enums.FloorType;
import javafx.scene.shape.Rectangle;

public class VisualNode extends Rectangle {
  FloorType floor;

  public FloorType getFloor() {
    return floor;
  }

  public void setFloor(FloorType floor) {
    this.floor = floor;
  }

  public VisualNode() {
    this.floor = FloorType.ThirdFloor;
  }
}

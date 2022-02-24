package edu.wpi.teame.db.objectManagers;

import edu.wpi.teame.model.Edge;
import edu.wpi.teame.model.enums.DataBaseObjectType;

public class EdgeManager extends ObjectManager<Edge> {
  public EdgeManager() {
    super(DataBaseObjectType.Edge, Long.MIN_VALUE);
  }
}

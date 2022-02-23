package edu.wpi.teame.db.objectManagers;

import edu.wpi.teame.model.Edge;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import java.sql.SQLException;

public class EdgeManager extends ObjectManager<Edge> {
  public EdgeManager() throws SQLException {
    super(DataBaseObjectType.Edge);
  }
}

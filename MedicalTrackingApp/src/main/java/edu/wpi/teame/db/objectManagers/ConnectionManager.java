package edu.wpi.teame.db.objectManagers;

import edu.wpi.teame.model.Edge;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import java.sql.SQLException;

public class ConnectionManager extends ObjectManager<Edge> {
  public ConnectionManager() throws SQLException {
    super(DataBaseObjectType.Connection);
  }
}

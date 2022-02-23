package edu.wpi.teame.db.objectManagers;

import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import java.sql.SQLException;

public final class LocationManager extends ObjectManager<Location> {
  public LocationManager() throws SQLException {
    super(DataBaseObjectType.Location);
  }

  public Location getByName(String longName) throws SQLException {
    return super.getBy("WHERE longName = '" + longName + "'").get(0);
  }
}

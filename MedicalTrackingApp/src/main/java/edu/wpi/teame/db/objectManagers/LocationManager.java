package edu.wpi.teame.db.objectManagers;

import static com.mongodb.client.model.Filters.eq;

import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.DBType;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import java.sql.SQLException;

public final class LocationManager extends ObjectManager<Location> {
  public LocationManager() throws SQLException {
    super(DataBaseObjectType.Location, 5);
  }

  public Location getByName(String longName) throws SQLException {
    if (DBManager.getInstance().getCurrentType() == DBType.MongoDB) {
      System.out.println("Mogo getbyname");

      Location location =
          DBManager.getInstance()
              .getMongoDatabase()
              .getCollection(objectType.toTableName(), Location.class)
              .withCodecRegistry(DBManager.getInstance().getObjectCodecs())
              .find(eq("longName", longName))
              .first();
      return location;
    }
    return super.getBy("WHERE longName = '" + longName + "'").get(0);
  }
}

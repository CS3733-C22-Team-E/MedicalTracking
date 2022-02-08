package edu.wpi.teame;

import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.objectManagers.LocationManager;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.BuildingType;
import edu.wpi.teame.model.enums.FloorType;
import edu.wpi.teame.model.enums.LocationType;
import java.io.IOException;
import java.sql.SQLException;

public class Main {

  public static void main(String[] args) throws IOException, SQLException {
    DBManager.getInstance();

    Location loc1 =
        new Location(
            1,
            "Center for International Medicine",
            1617,
            825,
            FloorType.FirstFloor,
            BuildingType.Tower,
            LocationType.DEPT,
            "ds");
    Location loc2 =
        new Location(
            2,
            "Bretholtz Center for Patients and Families",
            1610,
            1120,
            FloorType.FirstFloor,
            BuildingType.Tower,
            LocationType.DEPT,
            "");
    Location loc3 =
        new Location(
            3,
            "Multifaith Chapel",
            1721,
            931,
            FloorType.FirstFloor,
            BuildingType.Tower,
            LocationType.DEPT,
            "as");

    LocationManager location = new LocationManager();
    location.insert(loc1);
    // App.launch(App.class, args);
  }
}

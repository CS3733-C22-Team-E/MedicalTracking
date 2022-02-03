package edu.wpi.teame.db;

import java.sql.*;
import java.util.LinkedList;

public class LocationManager implements IManager<Location> {
  private static Connection connection;
  private static Statement stmt;

  public LocationManager() {
    connection = DBManager.getInstance().getConnection();

    try {
      stmt = connection.createStatement();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Location get(String id) {
    Location result = null;
    String getQuery = "SELECT * FROM LOCATIONS WHERE id='" + id + "'";
    try {
      ResultSet rset = stmt.executeQuery(getQuery);
      while (rset.next()) {
        int x = rset.getInt("x");
        int y = rset.getInt("y");
        String name = rset.getString("name");
        FloorType floor = FloorType.values()[rset.getInt("floor")];
        BuildingType building = BuildingType.values()[rset.getInt("building")];
        LocationType locationType = LocationType.values()[rset.getInt("locationType")];

        // convert strings to proper type
        result = new Location(id, name, x, y, floor, building, locationType);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public LinkedList<Location> getAll() {
    String getAllQuery = "SELECT * FROM LOCATIONS";
    LinkedList<Location> result = new LinkedList<>();

    try {
      ResultSet rset = stmt.executeQuery(getAllQuery);
      while (rset.next()) {
        int x = rset.getInt("x");
        int y = rset.getInt("y");
        String id = rset.getString("id");
        String name = rset.getString("name");
        FloorType floor = FloorType.values()[rset.getInt("floor")];
        BuildingType building = BuildingType.values()[rset.getInt("building")];
        LocationType locationType = LocationType.values()[rset.getInt("locationType")];

        // convert strings to proper type
        result.add(new Location(id, name, x, y, floor, building, locationType));
      }
    } catch (SQLException e) {
      System.out.println("Could not retrieve all of the locations from the table");
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public void insert(Location newObject) {
    String insertQuery =
        "INSERT INTO LOCATIONS VALUES('"
            + newObject.getId()
            + "',"
            + newObject.getX()
            + ","
            + newObject.getY()
            + ","
            + newObject.getFloor().ordinal()
            + ","
            + newObject.getBuilding().ordinal()
            + ","
            + newObject.getType().ordinal()
            + ",'"
            + newObject.getName()
            + "')";
    try {
      stmt.executeUpdate(insertQuery);
    } catch (SQLException e) {
      System.out.println("Case 3: Could not create new node");
      e.printStackTrace();
    }
  }

  @Override
  public void remove(String id) {
    String removeQuery = "DELETE FROM LOCATIONS WHERE id='" + id + "'";
    try {
      stmt.executeUpdate(removeQuery);
    } catch (SQLException e) {
      System.out.println("Case 4: Could not drop nodeID");
      e.printStackTrace();
    }
  }

  // floor,building,nodeType,longName,shortName
  @Override
  public void update(Location updatedObject) {
    String updateQuery =
        "UPDATE LOCATIONS SET x = "
            + updatedObject.getY()
            + ", y = "
            + updatedObject.getX()
            + ", floor = "
            + updatedObject.getFloor().ordinal()
            + ", building = "
            + updatedObject.getBuilding().ordinal()
            + ", locationType = "
            + updatedObject.getType().ordinal()
            + ", name = '"
            + updatedObject.getName()
            + "' WHERE id = '"
            + updatedObject.getId()
            + "'";
    try {
      stmt.executeUpdate(updateQuery);
      System.out.println("Location updated successfully");
    } catch (SQLException e) {
      System.out.println("Case2: Could not update Locations table");
      e.printStackTrace();
    }
  }
}

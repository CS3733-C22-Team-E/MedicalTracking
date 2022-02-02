package edu.wpi.teame.db;

import java.sql.*;
import java.util.LinkedList;

public class EquipmentManager implements IManager<Equipment> {
  private Connection connection;
  private Statement stmt;

  public EquipmentManager() {
    connection = DBManager.getInstance().getConnection();

    try {
      stmt = connection.createStatement();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  // get the Equipment object with the specified ID
  public Equipment get(String id) {
    String getQuery = "SELECT * FROM Equipment WHERE ID = '" + id + "'";
    Equipment result = null;
    LocationManager locationTable = DBManager.getInstance().getLocationManager();
    try {
      ResultSet rset = stmt.executeQuery(getQuery);

      while (rset.next()) {
        String nodeID = rset.getString("ID");
        Location locationNode = locationTable.get(rset.getString("locationNodeID"));
        // FloorType floor = FloorType.valueOf(rset.getString("floor"));
        // BuildingType building = BuildingType.valueOf(rset.getString("building"));
        LocationType type = LocationType.values()[rset.getInt("type")];
        String name = rset.getString("name");
        boolean hasPatient = rset.getBoolean("hasPatient");
        boolean isClean = rset.getBoolean("isClean");

        // save the tuple that resulted from the query
        result = new Equipment(nodeID, locationNode, type, name, hasPatient, isClean);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public LinkedList<Equipment> getAll() {
    String getQuery = "SELECT * FROM Equipment";
    LinkedList<Equipment> result = null;

    LocationManager locationTable = DBManager.getInstance().getLocationManager();
    try {
      ResultSet rset = stmt.executeQuery(getQuery);

      while (rset.next()) {
        String nodeID = rset.getString("ID");
        Location locationNode = locationTable.get(rset.getString("locationNodeID"));
        // FloorType floor = FloorType.valueOf(rset.getString("floor"));
        // BuildingType building = BuildingType.valueOf(rset.getString("building"));
        LocationType type = LocationType.values()[rset.getInt("type")];
        String name = rset.getString("name");
        boolean hasPatient = rset.getBoolean("hasPatient");
        boolean isClean = rset.getBoolean("isClean");


        // add next tuple to return list
        result.add(new Equipment(nodeID, locationNode, type, name, hasPatient, isClean));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public void insert(Equipment newObject) {
    int hasPatient = newObject.isHasPatient() ? 1 : 0;
    int isClean = newObject.isClean() ? 1 : 0;

    String insertStmt =
        "INSERT INTO Equipment VALUES('"
            + newObject.getNodeID()
            + "', '"
            + newObject.getLocationNode().getId()
            + "', '"
            + newObject.getType().ordinal()
            + "', '"
            + newObject.getName()
            + "', "
            + hasPatient
            + ", "
            + isClean
            + ")";

    try {
      stmt.executeUpdate(insertStmt);
      System.out.println("Location updated successfully");
    } catch (SQLException e) {
      System.out.println("Case2: Could not update Locations table");
      e.printStackTrace();
    }
  }

  @Override
  public void remove(String id) {
    String removeQuery = "DELETE FROM Equipment WHERE nodeID = '" + id + "'";
    try {
      stmt.executeUpdate(removeQuery);
    } catch (SQLException e) {
      System.out.println("Could not drop nodeID");
      e.printStackTrace();
    }
  }

  @Override
  public void update(Equipment updatedObject) {
    /*
    String updateQuery =
            "UPDATE Equipment SET ID = '"
                    + updatedObject.getNodeID()
                    + "', locationNodeID = '"
                    + updatedObject.getLocationNodeID()
                    + "', floor = '"
                    + updatedObject.getFloor()
                    + "', building = '"
                    + updatedObject.getBuilding()
                    + "', hasPatient = '"
                    + updatedObject.getType()
                    + "', longName = '"
                    + updatedObject.getName()
                    + "' WHERE id = '"
                    + updatedObject.getID()
                    + "'";
    try {
      stmt.executeUpdate(updateQuery);
      System.out.println("Location updated successfully");
    } catch (SQLException e) {
      System.out.println("Case2: Could not update Locations table");
      e.printStackTrace();
    }

     */

    int hasPatient = updatedObject.isHasPatient() ? 1 : 0;
    int isClean = updatedObject.isClean() ? 1 : 0;

    String updateQuery =
        "UPDATE Equipment SET "
            + "locationNode = '"
            + updatedObject.getLocationNode().getId()
            + "', type = '"
            + updatedObject.getType().ordinal()
            + "', hasPatient = '"
            + hasPatient
            + "', isClean = '"
            + isClean
            + "' WHERE id = '"
            + updatedObject.getNodeID()
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

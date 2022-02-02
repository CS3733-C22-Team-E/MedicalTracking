package edu.wpi.teame.db;

import java.util.LinkedList;
import java.sql.*;

public class LocationManager implements IManager<Location> {
    private static Connection connection;
    private static Statement stmt;

    public LocationManager(Connection connection) {
        DBManager.getInstance().getConnection();
    }

    @Override
    public Location get(String id) {
        Location result = null;
        String getQuery = "SELECT * FROM LOCATIONS WHERE nodeID='" + id + "'";
        try{
            ResultSet rset = stmt.executeQuery(getQuery);
            while(rset.next()) {
                int x = rset.getInt("x");
                int y = rset.getInt("y");
                String name = rset.getString("longName");
                FloorType floor = FloorType.valueOf(rset.getString("floor"));
                BuildingType building = BuildingType.valueOf(rset.getString("building"));
                LocationType locationType = LocationType.valueOf(rset.getString("locationType"));

                //convert strings to proper type
                result = new Location(id, name, x, y, floor, building, locationType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public LinkedList<Location> getAll() {
        String getAllQuery = "SELECT * FROM Locations";
        LinkedList<Location> result = null;

        try {
            ResultSet rset = stmt.executeQuery(getAllQuery);
            while (rset.next()) {
                int x = rset.getInt("x");
                int y = rset.getInt("y");
                String id = rset.getString("id");
                String name = rset.getString("longName");
                FloorType floor = FloorType.valueOf(rset.getString("floor"));
                BuildingType building = BuildingType.valueOf(rset.getString("building"));
                LocationType locationType = LocationType.valueOf(rset.getString("locationType"));

                //convert strings to proper type
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
                        + newObject.getId() + "','"
                        + newObject.getX() + "','"
                        + newObject.getY() + "','"
                        + newObject.getFloor() + "','"
                        + newObject.getBuilding() + "','"
                        + newObject.getType() + "','"
                        + newObject.getName() + "')";
        try {
            stmt.executeUpdate(insertQuery);
        } catch (SQLException e) {
            System.out.println("Case 3: Could not create new node");
            e.printStackTrace();
        }

    }

    @Override
    public void remove(String id) {
        String removeQuery = "DELETE FROM LOCATIONS WHERE nodeID='" + id + "'";
        try {
            stmt.executeUpdate(removeQuery);
        } catch (SQLException e) {
            System.out.println("Case 4: Could not drop nodeID");
            e.printStackTrace();
        }
    }

    //floor,building,nodeType,longName,shortName
    @Override
    public void update(Location updatedObject) {
        String updateQuery = "UPDATE LOCATIONS SET x = '" + updatedObject.getY()
                + "', y = '" + updatedObject.getX()
                + "', floor = '" + updatedObject.getFloor()
                + "', building = '" + updatedObject.getBuilding()
                + "', type = '" + updatedObject.getType()
                + "', longName = '" + updatedObject.getName()
                + "' WHERE id = '" + updatedObject.getId() + "'";
        try {
            stmt.executeUpdate(updateQuery);
            System.out.println("Location updated successfully");
        } catch (SQLException e) {
            System.out.println("Case2: Could not update Locations table");
            e.printStackTrace();
        }
    }
}


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
                String nodeID4 = rset.getString("id");
                String xcoord4 = rset.getString("x");
                String ycoord4 = rset.getString("y");
                String floor4 = rset.getString("floor");
                String building4 = rset.getString("building");
                String nodeType4 = rset.getString("locationType");
                String longName4 = rset.getString("longName");

                //convert strings to proper type
                result=  new Location(nodeID4, xcoord4, ycoord4, floor4, building4, nodeType4, longName4);
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
                Location tempLoc =
                        new Location(
                                rset.getString("nodeID"),
                                rset.getString("longName"),
                                rset.getString("xcoord"),
                                rset.getString("ycoord"),
                                rset.getString("floor"),
                                rset.getString("building"),
                                rset.getString("nodeType"));
                result.add(tempLoc);
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
                        + newObject.getNodeID() + "','"
                        + newObject.getxcoord() + "','"
                        + newObject.getycoord() + "','"
                        + newObject.getFloor() + "','"
                        + newObject.getBuilding() + "','"
                        + newObject.getNodeType() + "','"
                        + newObject.getLongName() + "','"
                        + newObject.getShortName() + "')";
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


package edu.wpi.teame.db;

import java.util.LinkedList;
import java.sql.*;

public class DBLocationTableDAO implements MedDAO<Location> {

    private static Connection connection;
    private static Statement stmt;

    public DBLocationTableDAO(Connection connection) {
        //todo figure out whether to connect to the db for each class or pass in the connection object as param
        this.connection = connection;

        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Could not create db statement");
            e.printStackTrace();
        }
    }

    @Override
    public Location get(String id) {
        Location result = null;
        String getQuery = "SELECT * FROM LOCATIONS WHERE nodeID='" + id + "'";
        try{
            ResultSet rset = stmt.executeQuery(getQuery);


            while(rset.next()) {
                String nodeID4 = rset.getString("nodeID");
                String xcoord4 = rset.getString("xcoord");
                String ycoord4 = rset.getString("ycoord");
                String floor4 = rset.getString("floor");
                String building4 = rset.getString("building");
                String nodeType4 = rset.getString("nodeType");
                String longName4 = rset.getString("longName");
                String shortName4 = rset.getString("shortName");

                result=  new Location(nodeID4, xcoord4, ycoord4, floor4, building4, nodeType4, longName4, shortName4);
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
                                rset.getString("xcoord"),
                                rset.getString("ycoord"),
                                rset.getString("floor"),
                                rset.getString("building"),
                                rset.getString("nodeType"),
                                rset.getString("longName"),
                                rset.getString("shortName"));
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
        String updateQuery = "UPDATE LOCATIONS SET xcoord = '" + updatedObject.getxcoord()
                + "', ycoord = '" + updatedObject.getycoord()
                + "', floor = '" + updatedObject.getFloor()
                + "', building = '" + updatedObject.getBuilding()
                + "', nodeType = '" + updatedObject.getNodeType()
                + "', longName = '" + updatedObject.getLongName()
                + "', shortName = '" + updatedObject.getShortName()
                + "' WHERE nodeID = '" + updatedObject.getNodeID() + "'";
        try {
            stmt.executeUpdate(updateQuery);
            System.out.println("Location updated successfully");
        } catch (SQLException e) {
            System.out.println("Case2: Could not update Locations table");
            e.printStackTrace();
        }
    }
}


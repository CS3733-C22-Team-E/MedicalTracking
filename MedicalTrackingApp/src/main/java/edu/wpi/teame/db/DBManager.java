package edu.wpi.teame.db;

import java.sql.*;

public final class DBManager {
  private static DBManager instance;
  private Connection connection;
  private Statement stmt;

  public static DBManager getInstance() {
    if (instance != null) {
      instance = new DBManager();
    }
    return instance;
  }

  private DBManager() {
    // add jdbc driver
    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      return;
    }
    // connect to the database
    try {
      connection =
          DriverManager.getConnection(
              "jdbc:derby:memory:ESpikeB;create=true;username=admin;password=admin;");
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {
      stmt = connection.createStatement();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    // creating the table for the locations
    try {
      String createLocationsTable =
          "CREATE TABLE LOCATIONS(id VARCHAR(10) Primary Key,"
              + "x VARCHAR(4), "
              + "y VARCHAR(4), "
              + "floor VARCHAR(2), "
              + "building VARCHAR(25), "
              + "locationType VARCHAR(4))";
      stmt.execute(createLocationsTable);

      /*
      String createEquipmentTable = "CREATE TABLE EQUIPMENT(id VARCHAR(10) Primary Key,"
              + "x VARCHAR(4), "
              + "y VARCHAR(4), "
              + "floor VARCHAR(2), "
              + "building VARCHAR(25), "
              + "locationType VARCHAR(4))";
      stmt.execute(createLocationsTable);

      String createEquipmentServiceRequestTable = "CREATE TABLE SERVICEREQUEST(id VARCHAR(10) Primary Key,"
              + "x VARCHAR(4), "
              + "y VARCHAR(4), "
              + "floor VARCHAR(2), "
              + "building VARCHAR(25), "
              + "locationType VARCHAR(4))";
      stmt.execute(createLocationsTable);

      */
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public Connection getConnection() {
    return connection;
  }
}

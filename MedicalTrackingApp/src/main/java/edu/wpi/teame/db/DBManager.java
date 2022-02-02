package edu.wpi.teame.db;

import java.sql.*;

public final class DBManager {
  private static DBManager instance;
  private Connection connection;
  private Statement stmt;

  private MedicalEquipmentServiceRequestManager MEServiceRequestManager;
  private EquipmentManager equipmentManager;
  private LocationManager locationManager;

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

    // create statement object
    try {
      stmt = connection.createStatement();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    // creating the table for the locations
    try {
      String createLocationsTable =
          "CREATE TABLE LOCATION(id VARCHAR(10) Primary Key,"
              + "x int, "
              + "y int, "
              + "floor int, "
              + "building int, "
              + "locationType int,"
              + "name VARCHAR(100))";

      stmt.execute(createLocationsTable);


      String createEquipmentTable = "CREATE TABLE EQUIPMENT(id VARCHAR(10) Primary Key,"
              + "locationNodeId VARCHAR(10), "
              + "locationType VARCHAR(4),"
              + "name VARCHAR(100),"
              +"FOREIGN KEY (locationNodeId) REFERENCES LOCATION(id))";
      stmt.execute(createEquipmentTable);
      /*
      String createEquipmentServiceRequestTable = "CREATE TABLE EQUIPMENTSERVICEREQUEST(id VARCHAR(10) Primary Key,"
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

    MEServiceRequestManager = new MedicalEquipmentServiceRequestManager();
    equipmentManager = new EquipmentManager();
    locationManager = new LocationManager();
  }

  public Connection getConnection() {
    return connection;
  }

  public MedicalEquipmentServiceRequestManager getMEServiceRequestManager() {
    return MEServiceRequestManager;
  }

  public EquipmentManager getEquipmentManager() {
    return equipmentManager;
  }

  public LocationManager getLocationManager() {
    return locationManager;
  }
}

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

      String createEquipmentTable =
          "CREATE TABLE EQUIPMENT(id VARCHAR(10) Primary Key,"
              + "locationNode VARCHAR(10), "
              + "type VARCHAR(10) ,"
              + "name VARCHAR(100),"
              + "hasPatient BIT,"
              + "isClean BIT,"
              + "FOREIGN KEY (locationNodeId) REFERENCES LOCATION(id))";
      stmt.execute(createEquipmentTable);

      String createEquipmentServiceRequestTable = "CREATE TABLE EQUIPMENT(id VARCHAR(10) Primary Key,"
              + "patient VARCHAR(100), "
              + "roomID VARCHAR(10) ,"
              + "stateTime VARCHAR(50),"
              + "endTime VARCHAR(50),"
              + "date VARCHAR(100),"
              + "assignee VARCHAR(100),"
              + "equipmentID VARCHAR(10),"
              + "status int,"
              + "FOREIGN KEY (roomID) REFERENCES LOCATION(id),"
              + "FOREIGN KEY (equipmentID) REFERENCES EQUIPMENT(id))";
      stmt.execute(createEquipmentServiceRequestTable);


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

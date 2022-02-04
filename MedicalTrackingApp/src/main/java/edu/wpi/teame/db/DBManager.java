package edu.wpi.teame.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class DBManager {
  private static DBManager instance;
  private Connection connection;
  private Statement stmt;

  private MedicalEquipmentServiceRequestManager MEServiceRequestManager;
  private EquipmentManager equipmentManager;
  private LocationManager locationManager;

  public static DBManager getInstance() {
    if (instance == null) {
      instance = new DBManager();
    }
    return instance;
  }

  private DBManager() {}

  public Connection getConnection() {
    return connection;
  }

  public void setupDB() {
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
          "CREATE TABLE LOCATIONS(id VARCHAR(10) Primary Key,"
              + "x int, "
              + "y int, "
              + "floor int, "
              + "building int, "
              + "locationType int,"
              + "longName VARCHAR(100), "
              + "shortName VARCHAR(100))";

      stmt.execute(createLocationsTable);
      System.out.println("Locations created");

      String createEquipmentTable =
          "CREATE TABLE EQUIPMENT(id VARCHAR(10) Primary Key,"
              + "locationNode VARCHAR(10), "
              + "type int,"
              + "name VARCHAR(100),"
              + "hasPatient BOOLEAN,"
              + "isClean BOOLEAN,"
              + "FOREIGN KEY (locationNode) REFERENCES LOCATIONS(id))";
      stmt.execute(createEquipmentTable);
      System.out.println("EQUIPMENT created");

      String createEquipmentServiceRequestTable =
          "CREATE TABLE EQUIPMENTSERVICEREQUEST(id VARCHAR(10) Primary Key,"
              + "patient VARCHAR(100), "
              + "roomID VARCHAR(10) ,"
              + "startTime VARCHAR(50),"
              + "endTime VARCHAR(50),"
              + "date VARCHAR(100),"
              + "assignee VARCHAR(100),"
              + "equipmentID VARCHAR(10),"
              + "status int,"
              + "FOREIGN KEY (roomID) REFERENCES LOCATIONS(id),"
              + "FOREIGN KEY (equipmentID) REFERENCES EQUIPMENT(id))";
      stmt.execute(createEquipmentServiceRequestTable);
      System.out.println("EQUIPMENTSERVICEREQUEST created");

      MEServiceRequestManager = new MedicalEquipmentServiceRequestManager();
      equipmentManager = new EquipmentManager();
      locationManager = new LocationManager();

    } catch (SQLException e) {

      e.printStackTrace();
      return;
    }
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

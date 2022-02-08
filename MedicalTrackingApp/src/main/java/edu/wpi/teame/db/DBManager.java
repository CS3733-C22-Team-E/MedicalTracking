package edu.wpi.teame.db;

import edu.wpi.teame.db.objectManagers.EmployeeManager;
import edu.wpi.teame.db.objectManagers.EquipmentManager;
import edu.wpi.teame.db.objectManagers.LocationManager;
import edu.wpi.teame.db.objectManagers.ObjectManager;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.serviceRequests.MedicalEquipmentServiceRequest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class DBManager {
  private static DBManager instance;
  private Connection connection;
  private Statement stmt;

  public static synchronized DBManager getInstance() {
    if (instance == null) {
      try {
        instance = new DBManager();
      } catch (SQLException sqlException) {
        sqlException.printStackTrace();
      }
    }
    return instance;
  }

  private DBManager() throws SQLException {
    setupDB();
  }

  public Connection getConnection() {
    return connection;
  }

  public void setupDB() throws SQLException {
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
    String createLocationsTable =
        "CREATE TABLE LOCATION(id int Primary Key GENERATED ALWAYS AS IDENTITY, "
            + "locationType int, "
            + "shortName VARCHAR(100), "
            + "longName VARCHAR(100), "
            + "building int, "
            + "floor int, "
            + "x int, "
            + "y int)";
    stmt.execute(createLocationsTable);
    System.out.println("Location Table created");

    String createEquipmentTable =
        "CREATE TABLE Equipment(id int Primary Key GENERATED ALWAYS AS IDENTITY, "
            + "locationID int, "
            + "name VARCHAR(100), "
            + "type int, "
            + "isClean int, "
            + "hasPatient int, "
            + "FOREIGN KEY (locationID) REFERENCES LOCATION(id))";
    stmt.execute(createEquipmentTable);
    System.out.println("Equipment Table created");

    String createEmployeeTable =
        "CREATE TABLE Employee(id int Primary Key GENERATED ALWAYS AS IDENTITY, "
            + "department int, "
            + "name VARCHAR(100), "
            + "isDoctor int)";
    stmt.execute(createEmployeeTable);
    System.out.println("Employee Table created");

    String createEquipmentServiceRequestTable =
        "CREATE TABLE MedicalEquipmentSR(id int Primary Key GENERATED ALWAYS AS IDENTITY, "
            + "locationID int, "
            + "status int, "
            + "employeeID int, "
            + "closeDate Date, "
            + "endTime VARCHAR(50), "
            + "openDate VARCHAR(100), "
            + "equipmentID int, "
            + "patient VARCHAR(100), "
            + "FOREIGN KEY (locationID) REFERENCES LOCATION(id), "
            + "FOREIGN KEY (equipmentID) REFERENCES EQUIPMENT(id))";
    stmt.execute(createEquipmentServiceRequestTable);
    System.out.println("MedicalEquipmentSR Table created");
  }

  public ObjectManager<MedicalEquipmentServiceRequest> getMEServiceRequestManager() {
    return new ObjectManager<MedicalEquipmentServiceRequest>(DataBaseObjectType.MedicalEquipmentSR);
  }

  public EquipmentManager getEquipmentManager() {
    return new EquipmentManager();
  }

  public LocationManager getLocationManager() {
    return new LocationManager();
  }

  public EmployeeManager getEmployeeManager() {
    return new EmployeeManager();
  }
}

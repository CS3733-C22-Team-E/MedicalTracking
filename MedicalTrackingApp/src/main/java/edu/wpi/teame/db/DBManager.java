package edu.wpi.teame.db;

import edu.wpi.teame.db.objectManagers.*;
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
            + "longName VARCHAR(250), "
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
        "CREATE TABLE Employee(id int Primary Key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
            + "department int, "
            + "name VARCHAR(100), "
            + "isDoctor int)";
    stmt.execute(createEmployeeTable);
    System.out.println("Employee Table created");

    String createEquipmentServiceRequestTable =
        "CREATE TABLE MedicalEquipmentSR(id int Primary Key NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
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

    String createMedicineDeliverySRTable =
        "CREATE TABLE MedicineDeliverySR(id int Primary Key NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
            + "locationID int, "
            + "status int, "
            + "employeeID int, "
            + "closeDate Date, "
            + "endTime VARCHAR(50), "
            + "openDate VARCHAR(100), "
            + "FOREIGN KEY (locationID) REFERENCES LOCATION(id))";
    stmt.execute(createMedicineDeliverySRTable);
    System.out.println("MedicineDeliverySR Table created");

    String createSanitationSRTable =
        "CREATE TABLE SanitationSR(id int Primary Key NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
            + "locationID int, "
            + "status int, "
            + "employeeID int, "
            + "closeDate Date, "
            + "endTime VARCHAR(50), "
            + "openDate VARCHAR(100), "
            + "FOREIGN KEY (locationID) REFERENCES LOCATION(id))";
    stmt.execute(createSanitationSRTable);
    System.out.println("SanitationSR Table created");

    String createSecuritySRTable =
        "CREATE TABLE createSecuritySRTable(id int Primary Key NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
            + "locationID int, "
            + "status int, "
            + "employeeID int, "
            + "closeDate Date, "
            + "endTime VARCHAR(50), "
            + "openDate VARCHAR(100), "
            + "FOREIGN KEY (locationID) REFERENCES LOCATION(id))";
    stmt.execute(createSecuritySRTable);
    System.out.println("MedicalEquipmentSR Table created");
  }

  public MedicalEquipmentSRManager getMedicalEquipmentSRManager() {
    return new MedicalEquipmentSRManager();
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

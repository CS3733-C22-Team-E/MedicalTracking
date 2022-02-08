package edu.wpi.teame.db;

import edu.wpi.teame.db.managers.ObjectManager;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Equipment;
import edu.wpi.teame.model.Location;
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
        "CREATE TABLE LOCATIONS(id VARCHAR(10) Primary Key GENERATED ALWAYS AS IDENTITY,"
            + "x int, "
            + "y int, "
            + "floor int, "
            + "building int, "
            + "locationType int,"
            + "longName VARCHAR(100), "
            + "shortName VARCHAR(100))";
    stmt.execute(createLocationsTable);
    System.out.println("LOCATIONS created");

    String createEquipmentServiceRequestTable =
        "CREATE TABLE EQUIPMENTSERVICEREQUEST(id int Primary Key GENERATED ALWAYS AS IDENTITY,"
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

    String createEmployeesTable =
        "CREATE TABLE EMPLOYEES(employeeID VARCHAR(20) Primary Key GENERATED ALWAYS AS IDENTITY,"
            + "dept INTEGER, "
            + "name VARCHAR(100), "
            + "isDoctor BOOLEAN)";
    stmt.execute(createEmployeesTable);
    System.out.println("EMPLOYEES created");

    String createEquipmentTable =
        "CREATE TABLE EQUIPMENT(id VARCHAR(10) Primary Key GENERATED ALWAYS AS IDENTITY,"
            + "locationNode VARCHAR(10), "
            + "type int,"
            + "name VARCHAR(100),"
            + "hasPatient BOOLEAN,"
            + "isClean BOOLEAN,"
            + "FOREIGN KEY (locationNode) REFERENCES LOCATIONS(id))";
    stmt.execute(createEquipmentTable);
    System.out.println("EQUIPMENT created");
  }

  public ObjectManager<MedicalEquipmentServiceRequest> getMEServiceRequestManager() {
    return new ObjectManager<MedicalEquipmentServiceRequest>(DataBaseObjectType.MedicalEquipmentSR);
  }

  public ObjectManager<Equipment> getEquipmentManager() {
    return new ObjectManager<Equipment>(DataBaseObjectType.Equipment);
  }

  public ObjectManager<Location> getLocationManager() {
    return new ObjectManager<Location>(DataBaseObjectType.Location);
  }

  public ObjectManager<Employee> getEmployeeManager() {
    return new ObjectManager<Employee>(DataBaseObjectType.Employee);
  }
}

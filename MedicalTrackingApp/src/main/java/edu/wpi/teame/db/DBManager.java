package edu.wpi.teame.db;

import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.db.objectManagers.*;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

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

  private void createDBTables() throws SQLException {
    // creating the table for the locations
    String createLocationsTable =
            "CREATE TABLE LOCATION(id int Primary Key GENERATED BY DEFAULT AS IDENTITY, "
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
            "CREATE TABLE Equipment(id int Primary Key GENERATED BY DEFAULT AS IDENTITY, "
                    + "locationID int, "
                    + "name VARCHAR(100), "
                    + "type int, "
                    + "isClean int, "
                    + "hasPatient int, "
                    + "FOREIGN KEY (locationID) REFERENCES LOCATION(id))";
    stmt.execute(createEquipmentTable);
    System.out.println("Equipment Table created");

    String createEmployeeTable =
            "CREATE TABLE Employee(id int Primary Key GENERATED BY DEFAULT AS IDENTITY, "
                    + "department int, "
                    + "name VARCHAR(100), "
                    + "type int)";
    stmt.execute(createEmployeeTable);
    System.out.println("Employee Table created");

    String createEquipmentServiceRequestTable =
            "CREATE TABLE MedicalEquipmentSR(id int Primary Key NOT NULL GENERATED BY DEFAULT AS IDENTITY, "
                    + "locationID int, "
                    + "status int, "
                    + "employeeID int, "
                    + "closeDate DATE, "
                    + "openDate DATE, "
                    + "equipmentID int, "
                    + "patient VARCHAR(100), "
                    + "FOREIGN KEY (locationID) REFERENCES LOCATION(id), "
                    + "FOREIGN KEY (equipmentID) REFERENCES EQUIPMENT(id), "
                    + "FOREIGN KEY (employeeID) REFERENCES EMPLOYEE(id))";
    stmt.execute(createEquipmentServiceRequestTable);
    System.out.println("MedicalEquipmentSR Table created");

    String createMedicineDeliverySRTable =
            "CREATE TABLE MedicineDeliverySR(id int Primary Key NOT NULL GENERATED BY DEFAULT  AS IDENTITY, "
                    + "locationID int, "
                    + "status int, "
                    + "employeeID int, "
                    + "closeDate DATE, "
                    + "openDate DATE, "
                    + "deliveryDate DATE, "
                    + "FOREIGN KEY (locationID) REFERENCES LOCATION(id), "
                    + "FOREIGN KEY (employeeID) REFERENCES EMPLOYEE(id))";
    stmt.execute(createMedicineDeliverySRTable);
    System.out.println("MedicineDeliverySR Table created");

    String createSanitationSRTable =
            "CREATE TABLE SanitationSR(id int Primary Key NOT NULL GENERATED BY DEFAULT AS IDENTITY, "
                    + "locationID int, "
                    + "status int, "
                    + "employeeID int, "
                    + "closeDate DATE, "
                    + "openDate DATE, "
                    + "FOREIGN KEY (locationID) REFERENCES LOCATION(id), "
                    + "FOREIGN KEY (employeeID) REFERENCES EMPLOYEE(id))";
    stmt.execute(createSanitationSRTable);
    System.out.println("SanitationSR Table created");

    String createSecuritySRTable =
            "CREATE TABLE SECURITYSR(id int Primary Key NOT NULL GENERATED BY DEFAULT AS IDENTITY, "
                    + "locationID int, "
                    + "status int, "
                    + "employeeID int, "
                    + "closeDate DATE, "
                    + "openDate DATE, "
                    + "FOREIGN KEY (locationID) REFERENCES LOCATION(id), "
                    + "FOREIGN KEY (employeeID) REFERENCES EMPLOYEE(id))";
    stmt.execute(createSecuritySRTable);
    System.out.println("MedicalEquipmentSR Table created");
  }

  public void loadDBFromCSV()
      throws CsvValidationException, SQLException, IOException, ParseException {
    getLocationManager().readCSV("csv/TowerLocationsE.csv");
    getEquipmentManager().readCSV("csv/EquipmentE.csv");
    getEmployeeManager().readCSV("csv/EmployeesE.csv");

    getMedicalEquipmentSRManager().readCSV("MedicalEquipmentDeliverServiceRequestSave.csv");
    getMedicineDeliverySRManager().readCSV("MedicineDeliveryServiceRequestSave.csv");
    getSanitationSRManager().readCSV("SanitationServiceRequest.csv");
    getSecuritySRManager().readCSV("SecurityServiceRequest.csv");
  }

  public void writeDBToCSV() throws SQLException, IOException {
    getLocationManager().writeToCSV("TowerLocationsESave.csv");
    getEquipmentManager().writeToCSV("EquipmentESave.csv");
    getEmployeeManager().writeToCSV("EmployeesESave.csv");

    getMedicalEquipmentSRManager().writeToCSV("MedicalEquipmentDeliverServiceRequestSave.csv");
    getMedicineDeliverySRManager().writeToCSV("MedicineDeliveryServiceRequestSave.csv");
    getSanitationSRManager().writeToCSV("SanitationServiceRequest.csv");
    getSecuritySRManager().writeToCSV("SecurityServiceRequest.csv");
  }

  private void createDBConnection(boolean isClientServer) throws SQLException {
    String connectionString = "jdbc:derby:memory:ESpikeB;create=true;username=admin;password=admin;";
    if (isClientServer) {
      connectionString = ""; //TODO: Add Client/Server connection string
    }

    connection = DriverManager.getConnection(connectionString);
    stmt = connection.createStatement();
  }

  public void setupDB() throws SQLException {
    // add jdbc driver
    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      return;
    }

    //Create connection to Embedded DB
    createDBConnection(false);

    //Create DB Tables
    createDBTables();
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

  public PatientManager getPatientManager() {
    return new PatientManager();
  }

  public MedicineDeliveryServiceRequestManager getMedicineDeliverySRManager() {
    return new MedicineDeliveryServiceRequestManager();
  }

  public StandardServiceRequestManager getFacilitiesMainteneceSRManager() {
    return new StandardServiceRequestManager(DataBaseObjectType.FacilitiesMaintenanceSR);
  }

  public StandardServiceRequestManager getAudioVisualSRManager() {
    return new StandardServiceRequestManager(DataBaseObjectType.AudioVisualSR);
  }

  public StandardServiceRequestManager getSanitationSRManager() {
    return new StandardServiceRequestManager(DataBaseObjectType.SanitationSR);
  }

  public StandardServiceRequestManager getSecuritySRManager() {
    return new StandardServiceRequestManager(DataBaseObjectType.SecuritySR);
  }

  public StandardServiceRequestManager getComputerSRManager() {
    return new StandardServiceRequestManager(DataBaseObjectType.ComputerSR);
  }

  public StandardServiceRequestManager getLaundrySRManager() {
    return new StandardServiceRequestManager(DataBaseObjectType.LaundrySR);
  }
}

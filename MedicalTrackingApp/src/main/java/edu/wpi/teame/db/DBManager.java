package edu.wpi.teame.db;

import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.db.objectManagers.*;
import edu.wpi.teame.db.objectManagers.serviceRequests.*;
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
    // <editor-fold desc="Create Standard Types on DB">
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

    String createPatientTable =
        "CREATE TABLE Patient(id int Primary Key GENERATED BY DEFAULT AS IDENTITY, "
            + "name VARCHAR(100), "
            + "dateOfBirth DATE, "
            + "currentLocationID int,"
            + "FOREIGN KEY (currentLocationID) REFERENCES LOCATION(id))";
    stmt.execute(createPatientTable);
    System.out.println("Patient Table created");
    // </editor-fold>

    String createAudioVisualSRTable =
        "CREATE TABLE AudioVisualSR(id int Primary Key NOT NULL GENERATED BY DEFAULT AS IDENTITY, "
            + "locationID int, "
            + "assigneeID int, "
            + "openDate DATE, "
            + "closeDate DATE, "
            + "status int, "
            + "title VARCHAR(50), "
            + "additionalInfo VARCHAR(2500), "
            + "priority int, "
            + "requestDate DATE, "
            + "FOREIGN KEY (locationID) REFERENCES LOCATION(id), "
            + "FOREIGN KEY (assigneeID) REFERENCES EMPLOYEE(id))";
    stmt.execute(createAudioVisualSRTable);
    System.out.println("AudioVisualSR Table created");

    String createComputerSRTable =
        "CREATE TABLE ComputerSR(id int Primary Key NOT NULL GENERATED BY DEFAULT AS IDENTITY, "
            + "locationID int, "
            + "assigneeID int, "
            + "openDate DATE, "
            + "closeDate DATE, "
            + "status int, "
            + "title VARCHAR(50), "
            + "additionalInfo VARCHAR(2500), "
            + "priority int, "
            + "requestDate DATE, "
            + "FOREIGN KEY (locationID) REFERENCES LOCATION(id), "
            + "FOREIGN KEY (assigneeID) REFERENCES EMPLOYEE(id))";
    stmt.execute(createComputerSRTable);
    System.out.println("ComputerSR Table created");

    String createExternalPatientSRTable =
        "CREATE TABLE ExternalPatientSR(id int Primary Key NOT NULL GENERATED BY DEFAULT AS IDENTITY, "
            + "locationID int, "
            + "assigneeID int, "
            + "openDate DATE, "
            + "closeDate DATE, "
            + "status int, "
            + "title VARCHAR(50), "
            + "additionalInfo VARCHAR(2500), "
            + "priority int, "
            + "requestDate DATE, "
            + "destinationID int, "
            + "patientID int, "
            + "equipmentID int, "
            + "FOREIGN KEY (locationID) REFERENCES LOCATION(id), "
            + "FOREIGN KEY (destinationID) REFERENCES LOCATION(id), "
            + "FOREIGN KEY (patientID) REFERENCES PATIENT(id), "
            + "FOREIGN KEY (equipmentID) REFERENCES EQUIPMENT(id), "
            + "FOREIGN KEY (assigneeID) REFERENCES EMPLOYEE(id))";
    stmt.execute(createExternalPatientSRTable);
    System.out.println("ExternalPatientSR Table created");

    String createFacilitiesSRTable =
        "CREATE TABLE FacilitiesSR(id int Primary Key NOT NULL GENERATED BY DEFAULT AS IDENTITY, "
            + "locationID int, "
            + "assigneeID int, "
            + "openDate DATE, "
            + "closeDate DATE, "
            + "status int, "
            + "title VARCHAR(50), "
            + "additionalInfo VARCHAR(2500), "
            + "priority int, "
            + "requestDate DATE, "
            + "FOREIGN KEY (locationID) REFERENCES LOCATION(id), "
            + "FOREIGN KEY (assigneeID) REFERENCES EMPLOYEE(id))";
    stmt.execute(createFacilitiesSRTable);
    System.out.println("FacilitiesSR Table created");

    String createFoodDeliverySRTable =
        "CREATE TABLE FoodDeliverySR(id int Primary Key NOT NULL GENERATED BY DEFAULT AS IDENTITY, "
            + "locationID int, "
            + "assigneeID int, "
            + "openDate DATE, "
            + "closeDate DATE, "
            + "status int, "
            + "title VARCHAR(50), "
            + "additionalInfo VARCHAR(2500), "
            + "priority int, "
            + "requestDate DATE, "
            + "patientID int, "
            + "food VARCHAR(250), "
            + "FOREIGN KEY (locationID) REFERENCES LOCATION(id), "
            + "FOREIGN KEY (patientID) REFERENCES PATIENT(id), "
            + "FOREIGN KEY (assigneeID) REFERENCES EMPLOYEE(id))";
    stmt.execute(createFoodDeliverySRTable);
    System.out.println("FoodDeliverySR Table created");

    String createGiftAndFloralSRTable =
        "CREATE TABLE GiftAndFloralSR(id int Primary Key NOT NULL GENERATED BY DEFAULT AS IDENTITY, "
            + "locationID int, "
            + "assigneeID int, "
            + "openDate DATE, "
            + "closeDate DATE, "
            + "status int, "
            + "title VARCHAR(50), "
            + "additionalInfo VARCHAR(2500), "
            + "priority int, "
            + "requestDate DATE, "
            + "patientID int, "
            + "FOREIGN KEY (locationID) REFERENCES LOCATION(id), "
            + "FOREIGN KEY (patientID) REFERENCES PATIENT(id), "
            + "FOREIGN KEY (assigneeID) REFERENCES EMPLOYEE(id))";
    stmt.execute(createGiftAndFloralSRTable);
    System.out.println("GiftAndFloralSR Table created");

    String createInternalPatientSRTable =
        "CREATE TABLE InternalPatientSR(id int Primary Key NOT NULL GENERATED BY DEFAULT AS IDENTITY, "
            + "locationID int, "
            + "assigneeID int, "
            + "openDate DATE, "
            + "closeDate DATE, "
            + "status int, "
            + "title VARCHAR(50), "
            + "additionalInfo VARCHAR(2500), "
            + "priority int, "
            + "requestDate DATE, "
            + "destinationID int, "
            + "patientID int, "
            + "equipmentID int, "
            + "FOREIGN KEY (locationID) REFERENCES LOCATION(id), "
            + "FOREIGN KEY (destinationID) REFERENCES LOCATION(id), "
            + "FOREIGN KEY (patientID) REFERENCES PATIENT(id), "
            + "FOREIGN KEY (equipmentID) REFERENCES EQUIPMENT(id), "
            + "FOREIGN KEY (assigneeID) REFERENCES EMPLOYEE(id))";
    stmt.execute(createInternalPatientSRTable);
    System.out.println("InternalPatientSR Table created");

    String createLanguageSRTable =
        "CREATE TABLE LanguageSR(id int Primary Key NOT NULL GENERATED BY DEFAULT AS IDENTITY, "
            + "locationID int, "
            + "assigneeID int, "
            + "openDate DATE, "
            + "closeDate DATE, "
            + "status int, "
            + "title VARCHAR(50), "
            + "additionalInfo VARCHAR(2500), "
            + "priority int, "
            + "requestDate DATE, "
            + "language int, "
            + "patientID int, "
            + "FOREIGN KEY (locationID) REFERENCES LOCATION(id), "
            + "FOREIGN KEY (patientID) REFERENCES PATIENT(id), "
            + "FOREIGN KEY (assigneeID) REFERENCES EMPLOYEE(id))";
    stmt.execute(createLanguageSRTable);
    System.out.println("LanguageSR Table created");

    String createLaundrySRTable =
        "CREATE TABLE LaundrySR(id int Primary Key NOT NULL GENERATED BY DEFAULT AS IDENTITY, "
            + "locationID int, "
            + "assigneeID int, "
            + "openDate DATE, "
            + "closeDate DATE, "
            + "status int, "
            + "title VARCHAR(50), "
            + "additionalInfo VARCHAR(2500), "
            + "priority int, "
            + "requestDate DATE, "
            + "FOREIGN KEY (locationID) REFERENCES LOCATION(id), "
            + "FOREIGN KEY (assigneeID) REFERENCES EMPLOYEE(id))";
    stmt.execute(createLaundrySRTable);
    System.out.println("LaundrySR Table created");

    String createEquipmentDeliverySRTable =
        "CREATE TABLE MedicalEquipmentSR(id int Primary Key NOT NULL GENERATED BY DEFAULT AS IDENTITY, "
            + "locationID int, "
            + "assigneeID int, "
            + "openDate DATE, "
            + "closeDate DATE, "
            + "status int, "
            + "title VARCHAR(50), "
            + "additionalInfo VARCHAR(2500), "
            + "priority int, "
            + "requestDate DATE, "
            + "equipmentID int, "
            + "FOREIGN KEY (locationID) REFERENCES LOCATION(id), "
            + "FOREIGN KEY (equipmentID) REFERENCES EQUIPMENT(id), "
            + "FOREIGN KEY (assigneeID) REFERENCES EMPLOYEE(id))";
    stmt.execute(createEquipmentDeliverySRTable);
    System.out.println("MedicalEquipmentSR Table created");

    String createMedicineDeliverySRTable =
        "CREATE TABLE MedicineDeliverySR(id int Primary Key NOT NULL GENERATED BY DEFAULT  AS IDENTITY, "
            + "locationID int, "
            + "assigneeID int, "
            + "openDate DATE, "
            + "closeDate DATE, "
            + "status int, "
            + "title VARCHAR(50), "
            + "additionalInfo VARCHAR(2500), "
            + "priority int, "
            + "requestDate DATE, "
            + "medicineName VARCHAR(200), "
            + "patientID int, "
            + "medicineQuantity VARCHAR(200), "
            + "FOREIGN KEY (locationID) REFERENCES LOCATION(id), "
            + "FOREIGN KEY (patientID) REFERENCES PATIENT(id), "
            + "FOREIGN KEY (assigneeID) REFERENCES EMPLOYEE(id))";
    stmt.execute(createMedicineDeliverySRTable);
    System.out.println("MedicineDeliverySR Table created");

    String createReligionSR =
        "CREATE TABLE ReligionSR(id int Primary Key NOT NULL GENERATED BY DEFAULT  AS IDENTITY, "
            + "locationID int, "
            + "assigneeID int, "
            + "openDate DATE, "
            + "closeDate DATE, "
            + "status int, "
            + "title VARCHAR(50), "
            + "additionalInfo VARCHAR(2500), "
            + "priority int, "
            + "requestDate DATE, "
            + "patientID int, "
            + "religion VARCHAR(200), "
            + "FOREIGN KEY (locationID) REFERENCES LOCATION(id), "
            + "FOREIGN KEY (patientID) REFERENCES PATIENT(id), "
            + "FOREIGN KEY (assigneeID) REFERENCES EMPLOYEE(id))";
    stmt.execute(createReligionSR);
    System.out.println("ReligionSR Table created");

    String createSanitationSRTable =
        "CREATE TABLE SanitationSR(id int Primary Key NOT NULL GENERATED BY DEFAULT AS IDENTITY, "
            + "locationID int, "
            + "assigneeID int, "
            + "openDate DATE, "
            + "closeDate DATE, "
            + "status int, "
            + "title VARCHAR(50), "
            + "additionalInfo VARCHAR(2500), "
            + "priority int, "
            + "requestDate DATE, "
            + "FOREIGN KEY (locationID) REFERENCES LOCATION(id), "
            + "FOREIGN KEY (assigneeID) REFERENCES EMPLOYEE(id))";
    stmt.execute(createSanitationSRTable);
    System.out.println("SanitationSR Table created");

    String createSecuritySRTable =
        "CREATE TABLE SECURITYSR(id int Primary Key NOT NULL GENERATED BY DEFAULT AS IDENTITY, "
            + "locationID int, "
            + "assigneeID int, "
            + "openDate DATE, "
            + "closeDate DATE, "
            + "status int, "
            + "title VARCHAR(50), "
            + "additionalInfo VARCHAR(2500), "
            + "priority int, "
            + "requestDate DATE, "
            + "FOREIGN KEY (locationID) REFERENCES LOCATION(id), "
            + "FOREIGN KEY (assigneeID) REFERENCES EMPLOYEE(id))";
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
    String connectionString =
        "jdbc:derby:memory:ESpikeB;create=true;username=admin;password=admin;";
    if (isClientServer) {
      connectionString = ""; // TODO: Add Client/Server connection string
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

    // Create connection to Embedded DB
    createDBConnection(false);

    // Create DB Tables
    createDBTables();
  }

  public CredentialManager getCredentialManager() {
    return new CredentialManager();
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

  public StandardSRManager getAudioVisualSRManager() {
    return new StandardSRManager(DataBaseObjectType.AudioVisualSR);
  }

  public StandardSRManager getComputerSRManager() {
    return new StandardSRManager(DataBaseObjectType.ComputerSR);
  }

  public PatientTransportationSRManager getExternalPatientSRManager() {
    return new PatientTransportationSRManager(false);
  }

  public StandardSRManager getFacilitiesMaintenanceSRManager() {
    return new StandardSRManager(DataBaseObjectType.FacilitiesMaintenanceSR);
  }

  public FoodDeliverySRManager getFoodDeliverySRManager() {
    return new FoodDeliverySRManager();
  }

  public GiftAndFloralSRManager getGiftAndFloralSRManager() {
    return new GiftAndFloralSRManager();
  }

  public PatientTransportationSRManager getInternalPatientSRManager() {
    return new PatientTransportationSRManager(true);
  }

  public StandardSRManager getLanguageSRManager() {
    return new StandardSRManager(DataBaseObjectType.LanguageInterpreterSR);
  }

  public StandardSRManager getLaundrySRManager() {
    return new StandardSRManager(DataBaseObjectType.LaundrySR);
  }

  public MedicalEquipmentSRManager getMedicalEquipmentSRManager() {
    return new MedicalEquipmentSRManager();
  }

  public MedicineDeliverySRManager getMedicineDeliverySRManager() {
    return new MedicineDeliverySRManager();
  }

  public ReligiousSRManager getReligiousSR() {
    return new ReligiousSRManager();
  }

  public StandardSRManager getSanitationSRManager() {
    return new StandardSRManager(DataBaseObjectType.SanitationSR);
  }

  public StandardSRManager getSecuritySRManager() {
    return new StandardSRManager(DataBaseObjectType.SecuritySR);
  }
}

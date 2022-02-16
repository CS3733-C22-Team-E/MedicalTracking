package edu.wpi.teame.db;

import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.db.objectManagers.*;
import edu.wpi.teame.db.objectManagers.serviceRequests.*;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.ParseException;

public final class DBManager {
  private final String ClientServerConnectionString =
      "jdbc:derby://localhost:1527/ClientServer;create=true;username=admin;password=admin";
  private final String EmbeddedConnectionString =
      "jdbc:derby:memory:EmbeddedE;create=true;username=admin;password=admin";

  private boolean isClientServer = false;
  private static DBManager instance;
  private Connection connection;
  private Statement stmt;

  public static synchronized DBManager getInstance() {
    if (instance == null) {
      instance = new DBManager();
    }
    return instance;
  }

  private DBManager() {}

  public Connection getConnection() {
    return connection;
  }

  public boolean isClientServer() {
    return isClientServer;
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

    String createCredentialTable =
        "CREATE TABLE Credential(id int Primary Key GENERATED BY DEFAULT AS IDENTITY, "
            + "salt VARCHAR(16), "
            + "username VARCHAR(50), "
            + "password VARCHAR(64))";
    stmt.execute(createCredentialTable);
    System.out.println("Credential Table created");
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

    String createFacilitiesMaintenanceSRTable =
        "CREATE TABLE FacilitiesMaintenanceSR(id int Primary Key NOT NULL GENERATED BY DEFAULT AS IDENTITY, "
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
    stmt.execute(createFacilitiesMaintenanceSRTable);
    System.out.println("FacilitiesMaintenanceSR Table created");

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
        "CREATE TABLE InternalPatientTransferSR(id int Primary Key NOT NULL GENERATED BY DEFAULT AS IDENTITY, "
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
        "CREATE TABLE LanguageInterpreterSR(id int Primary Key NOT NULL GENERATED BY DEFAULT AS IDENTITY, "
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
    System.out.println("LanguageInterpreterSR Table created");

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
        "CREATE TABLE RELIGIOUSSR(id int Primary Key NOT NULL GENERATED BY DEFAULT  AS IDENTITY, "
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
    System.out.println("ReligiousSR Table created");

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
            + "requestDate Date, " // change to timestamp
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
    System.out.println("SecuritySR Table created");
  }

  public void loadDBFromCSV(boolean fromBackup)
      throws CsvValidationException, SQLException, IOException, ParseException {
    String subFolder = "switchFiles/";
    if (fromBackup) {
      subFolder = "backup/";
    }

    try {
      getCredentialManager().readCSV("backup/Credentials.csv");
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    getLocationManager().readCSV(subFolder + "TowerLocationsE.csv");
    getEquipmentManager().readCSV(subFolder + "EquipmentE.csv");
    getEmployeeManager().readCSV(subFolder + "EmployeesE.csv");
    getPatientManager().readCSV(subFolder + "Patient.csv");

    getAudioVisualSRManager().readCSV(subFolder + "AudioVisualServiceRequest.csv");
    getComputerSRManager().readCSV(subFolder + "ComputerServiceRequest.csv");
    getExternalPatientSRManager()
        .readCSV(subFolder + "ExternalPatientTransportationServiceRequest.csv");
    getFacilitiesMaintenanceSRManager()
        .readCSV(subFolder + "FacilitiesMaintenanceServiceRequest.csv");
    getFoodDeliverySRManager().readCSV(subFolder + "FoodDeliveryServiceRequest.csv");
    getGiftAndFloralSRManager().readCSV(subFolder + "GiftAndFloralServiceRequest.csv");
    getInternalPatientSRManager()
        .readCSV(subFolder + "InternalPatientTransportationServiceRequest.csv");
    getLanguageSRManager().readCSV(subFolder + "LanguageInterpreterServiceRequest.csv");
    getLaundrySRManager().readCSV(subFolder + "LaundryServiceRequest.csv");
    getMedicalEquipmentSRManager().readCSV(subFolder + "MedicalEquipmentDeliverServiceRequest.csv");
    getMedicineDeliverySRManager().readCSV(subFolder + "MedicineDeliveryServiceRequest.csv");
    getReligiousSRManager().readCSV(subFolder + "ReligiousServiceRequest.csv");
    getSanitationSRManager().readCSV(subFolder + "SanitationServiceRequest.csv");
    getSecuritySRManager().readCSV(subFolder + "SecurityServiceRequest.csv");
  }

  public void writeDBToCSV(boolean isBackup) throws SQLException, IOException {
    String subFolder = "switchFiles/";
    if (isBackup) {
      subFolder = "backup/";
    }

    getLocationManager().writeToCSV(subFolder + "TowerLocationsE.csv");
    getEquipmentManager().writeToCSV(subFolder + "EquipmentE.csv");
    getEmployeeManager().writeToCSV(subFolder + "EmployeesE.csv");
    getPatientManager().writeToCSV(subFolder + "Patient.csv");

    getAudioVisualSRManager().writeToCSV(subFolder + "AudioVisualServiceRequest.csv");
    getComputerSRManager().writeToCSV(subFolder + "ComputerServiceRequest.csv");
    getExternalPatientSRManager()
        .writeToCSV(subFolder + "ExternalPatientTransportationServiceRequest.csv");
    getFacilitiesMaintenanceSRManager()
        .writeToCSV(subFolder + "FacilitiesMaintenanceServiceRequest.csv");
    getFoodDeliverySRManager().writeToCSV(subFolder + "FoodDeliveryServiceRequest.csv");
    getGiftAndFloralSRManager().writeToCSV(subFolder + "GiftAndFloralServiceRequest.csv");
    getInternalPatientSRManager()
        .writeToCSV(subFolder + "InternalPatientTransportationServiceRequest.csv");
    getLanguageSRManager().writeToCSV(subFolder + "LanguageInterpreterServiceRequest.csv");
    getLaundrySRManager().writeToCSV(subFolder + "LaundryServiceRequest.csv");
    getMedicalEquipmentSRManager()
        .writeToCSV(subFolder + "MedicalEquipmentDeliverServiceRequest.csv");
    getMedicineDeliverySRManager().writeToCSV(subFolder + "MedicineDeliveryServiceRequest.csv");
    getReligiousSRManager().writeToCSV(subFolder + "ReligiousServiceRequest.csv");
    getSanitationSRManager().writeToCSV(subFolder + "SanitationServiceRequest.csv");
    getSecuritySRManager().writeToCSV(subFolder + "SecurityServiceRequest.csv");
  }

  public void setupDB() throws SQLException, CsvValidationException, IOException, ParseException {
    // add embedded driver
    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      return;
    }

    // add client-server driver
    try {
      Class.forName("org.apache.derby.jdbc.ClientDriver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      return;
    }

    // Connect to Client/Server... that DB may already have the tables
    try {
      connection = DriverManager.getConnection(ClientServerConnectionString);
      stmt = connection.createStatement();
      isClientServer = true;
      createDBTables();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    // Default connect to Embedded Server
    connection = DriverManager.getConnection(EmbeddedConnectionString);
    stmt = connection.createStatement();
    isClientServer = false;
    createDBTables();
  }

  public void switchConnection(boolean isClientServer)
      throws SQLException, IOException, CsvValidationException, ParseException {
    // Write DB to CSV
    DBManager.getInstance().writeDBToCSV(false);

    // Clean the current dB table
    cleanDBTables();

    // Switch to the connection
    String connectionString = EmbeddedConnectionString;
    if (isClientServer) {
      connectionString = ClientServerConnectionString;
    }

    // Create Connection
    connection = DriverManager.getConnection(connectionString);
    this.isClientServer = isClientServer;
    stmt = connection.createStatement();

    // Load DB from CSV
    DBManager.getInstance().loadDBFromCSV(false);
  }

  private void cleanDBTables() throws SQLException {
    for (DataBaseObjectType dbTable : DataBaseObjectType.values()) {
      stmt.executeUpdate("DELETE " + dbTable.toString());
    }
  }

  public CredentialManager getCredentialManager() throws SQLException, NoSuchAlgorithmException {
    return new CredentialManager();
  }

  public EquipmentManager getEquipmentManager() throws SQLException {
    return new EquipmentManager();
  }

  public LocationManager getLocationManager() throws SQLException {
    return new LocationManager();
  }

  public EmployeeManager getEmployeeManager() throws SQLException {
    return new EmployeeManager();
  }

  public PatientManager getPatientManager() throws SQLException {
    return new PatientManager();
  }

  public StandardSRManager getAudioVisualSRManager() throws SQLException {
    return new StandardSRManager(DataBaseObjectType.AudioVisualSR);
  }

  public StandardSRManager getComputerSRManager() throws SQLException {
    return new StandardSRManager(DataBaseObjectType.ComputerSR);
  }

  public PatientTransportationSRManager getExternalPatientSRManager() throws SQLException {
    return new PatientTransportationSRManager(false);
  }

  public StandardSRManager getFacilitiesMaintenanceSRManager() throws SQLException {
    return new StandardSRManager(DataBaseObjectType.FacilitiesMaintenanceSR);
  }

  public FoodDeliverySRManager getFoodDeliverySRManager() throws SQLException {
    return new FoodDeliverySRManager();
  }

  public GiftAndFloralSRManager getGiftAndFloralSRManager() throws SQLException {
    return new GiftAndFloralSRManager();
  }

  public PatientTransportationSRManager getInternalPatientSRManager() throws SQLException {
    return new PatientTransportationSRManager(true);
  }

  public StandardSRManager getLanguageSRManager() throws SQLException {
    return new StandardSRManager(DataBaseObjectType.LanguageInterpreterSR);
  }

  public StandardSRManager getLaundrySRManager() throws SQLException {
    return new StandardSRManager(DataBaseObjectType.LaundrySR);
  }

  public MedicalEquipmentSRManager getMedicalEquipmentSRManager() throws SQLException {
    return new MedicalEquipmentSRManager();
  }

  public MedicineDeliverySRManager getMedicineDeliverySRManager() throws SQLException {
    return new MedicineDeliverySRManager();
  }

  public ReligiousSRManager getReligiousSRManager() throws SQLException {
    return new ReligiousSRManager();
  }

  public StandardSRManager getSanitationSRManager() throws SQLException {
    return new StandardSRManager(DataBaseObjectType.SanitationSR);
  }

  public StandardSRManager getSecuritySRManager() throws SQLException {
    return new StandardSRManager(DataBaseObjectType.SecuritySR);
  }
}

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
    System.out.println("MedicalEquipmentSR Table created");
  }

  public void loadDBFromCSV()
      throws CsvValidationException, SQLException, IOException, ParseException {
    try {
      new CredentialManager().insert("admin", "admin");

      System.out.println(new CredentialManager().logIn("Amitai", "password"));
      System.out.println(new CredentialManager().logIn("test", "password"));
      System.out.println(new CredentialManager().logIn("Amitai", "amitai"));
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    getLocationManager().readCSV("csv/TowerLocationsE.csv");
    getEquipmentManager().readCSV("csv/EquipmentE.csv");
    getEmployeeManager().readCSV("csv/EmployeesE.csv");
    getPatientManager().readCSV("csv/Patient.csv");

    getAudioVisualSRManager().readCSV("AudioVisualServiceRequest.csv");
    getComputerSRManager().readCSV("ComputerServiceRequest.csv");
    getExternalPatientSRManager().readCSV("ExternalPatientTransportationServiceRequest.csv");
    getFacilitiesMaintenanceSRManager().readCSV("FacilitiesMaintenanceServiceRequest.csv");
    getFoodDeliverySRManager().readCSV("FoodDeliveryServiceRequest.csv");
    getGiftAndFloralSRManager().readCSV("GiftAndFloralServiceRequest.csv");
    getInternalPatientSRManager().readCSV("InternalPatientTransportationServiceRequest.csv");
    getLanguageSRManager().readCSV("LanguageInterpreterServiceRequest.csv");
    getLaundrySRManager().readCSV("LaundryServiceRequest.csv");
    getMedicalEquipmentSRManager().readCSV("MedicalEquipmentDeliverServiceRequest.csv");
    getMedicineDeliverySRManager().readCSV("MedicineDeliveryServiceRequest.csv");
    getReligiousSR().readCSV("ReligiousServiceRequest.csv");
    getSanitationSRManager().readCSV("SanitationServiceRequest.csv");
    getSecuritySRManager().readCSV("SecurityServiceRequest.csv");
  }

  public void writeDBToCSV() throws SQLException, IOException {
    getLocationManager().writeToCSV("csv/TowerLocationsE.csv");
    getEquipmentManager().writeToCSV("csv/EquipmentE.csv");
    getEmployeeManager().writeToCSV("csv/EmployeesE.csv");
    getPatientManager().writeToCSV("csv/Patient.csv");

    getAudioVisualSRManager().writeToCSV("AudioVisualServiceRequest.csv");
    getComputerSRManager().writeToCSV("ComputerServiceRequest.csv");
    getExternalPatientSRManager().writeToCSV("ExternalPatientTransportationServiceRequest.csv");
    getFacilitiesMaintenanceSRManager().writeToCSV("FacilitiesMaintenanceServiceRequest.csv");
    getFoodDeliverySRManager().writeToCSV("FoodDeliveryServiceRequest.csv");
    getGiftAndFloralSRManager().writeToCSV("GiftAndFloralServiceRequest.csv");
    getInternalPatientSRManager().writeToCSV("InternalPatientTransportationServiceRequest.csv");
    getLanguageSRManager().writeToCSV("LanguageInterpreterServiceRequest.csv");
    getLaundrySRManager().writeToCSV("LaundryServiceRequest.csv");
    getMedicalEquipmentSRManager().writeToCSV("MedicalEquipmentDeliverServiceRequest.csv");
    getMedicineDeliverySRManager().writeToCSV("MedicineDeliveryServiceRequest.csv");
    getReligiousSR().writeToCSV("ReligiousServiceRequest.csv");
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

  public ReligiousSRManager getReligiousSR() throws SQLException {
    return new ReligiousSRManager();
  }

  public StandardSRManager getSanitationSRManager() throws SQLException {
    return new StandardSRManager(DataBaseObjectType.SanitationSR);
  }

  public StandardSRManager getSecuritySRManager() throws SQLException {
    return new StandardSRManager(DataBaseObjectType.SecuritySR);
  }
}

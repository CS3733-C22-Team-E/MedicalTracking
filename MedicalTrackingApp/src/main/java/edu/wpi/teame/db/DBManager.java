package edu.wpi.teame.db;

import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.db.objectManagers.*;
import edu.wpi.teame.db.objectManagers.serviceRequests.*;
import edu.wpi.teame.model.enums.DBType;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;

public final class DBManager {
  private final String AzureCloudServerConnectionString =
      "jdbc:sqlserver://cs3733hospitalapp.database.windows.net:1433;database=hospitalappdb;user=medicaltracking@cs3733hospitalapp;password=u@$YK=$A5A*<\"g$$;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
  private final String ClientServerConnectionString =
      "jdbc:derby://localhost:1527/ClientServer;create=true;username=admin;password=admin";
  private final String EmbeddedConnectionString =
      "jdbc:derby:memory:EmbeddedE;create=true;username=admin;password=admin";

  private DBType currentType = DBType.Embedded;
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

  public DBType getCurrentType() {
    return currentType;
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
            + "y int, "
            + "isDeleted int DEFAULT 0)";
    stmt.execute(createLocationsTable);
    System.out.println("Location Table created");

    String createEquipmentTable =
        "CREATE TABLE Equipment(id int Primary Key GENERATED BY DEFAULT AS IDENTITY, "
            + "locationID int, "
            + "name VARCHAR(100), "
            + "type int, "
            + "isClean int, "
            + "hasPatient int, "
            + "isDeleted int DEFAULT 0, "
            + "FOREIGN KEY (locationID) REFERENCES LOCATION(id))";
    stmt.execute(createEquipmentTable);
    System.out.println("Equipment Table created");

    String createEmployeeTable =
        "CREATE TABLE Employee(id int Primary Key GENERATED BY DEFAULT AS IDENTITY, "
            + "department int, "
            + "name VARCHAR(100), "
            + "type int, "
            + "isDeleted int DEFAULT 0)";
    stmt.execute(createEmployeeTable);
    System.out.println("Employee Table created");

    String createPatientTable =
        "CREATE TABLE Patient(id int Primary Key GENERATED BY DEFAULT AS IDENTITY, "
            + "name VARCHAR(100), "
            + "dateOfBirth DATE, "
            + "currentLocationID int,"
            + "isDeleted int DEFAULT 0, "
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
            + "isDeleted int DEFAULT 0, "
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
            + "isDeleted int DEFAULT 0, "
            + "FOREIGN KEY (locationID) REFERENCES LOCATION(id), "
            + "FOREIGN KEY (assigneeID) REFERENCES EMPLOYEE(id))";
    stmt.execute(createComputerSRTable);
    System.out.println("ComputerSR Table created");

    String createDeceasedBodyRemovalSRTable =
        "CREATE TABLE DeceasedBodyRemovalSR(id int Primary Key NOT NULL GENERATED BY DEFAULT AS IDENTITY, "
            + "locationID int, "
            + "assigneeID int, "
            + "openDate DATE, "
            + "closeDate DATE, "
            + "status int, "
            + "title VARCHAR(50), "
            + "additionalInfo VARCHAR(2500), "
            + "priority int, "
            + "requestDate DATE, "
            + "isDeleted int DEFAULT 0, "
            + "FOREIGN KEY (locationID) REFERENCES LOCATION(id), "
            + "FOREIGN KEY (assigneeID) REFERENCES EMPLOYEE(id))";
    stmt.execute(createDeceasedBodyRemovalSRTable);
    System.out.println("DeceasedBodyRemovalSR Table created");

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
            + "isDeleted int DEFAULT 0, "
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
            + "isDeleted int DEFAULT 0, "
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
            + "isDeleted int DEFAULT 0, "
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
            + "isDeleted int DEFAULT 0, "
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
            + "isDeleted int DEFAULT 0, "
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
            + "isDeleted int DEFAULT 0, "
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
            + "isDeleted int DEFAULT 0, "
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
            + "isDeleted int DEFAULT 0, "
            + "FOREIGN KEY (locationID) REFERENCES LOCATION(id), "
            + "FOREIGN KEY (equipmentID) REFERENCES EQUIPMENT(id), "
            + "FOREIGN KEY (assigneeID) REFERENCES EMPLOYEE(id))";
    stmt.execute(createEquipmentDeliverySRTable);
    System.out.println("MedicalEquipmentSR Table created");

    String createMedicineDeliverySRTable =
        "CREATE TABLE MedicineDeliverySR(id int Primary Key NOT NULL GENERATED BY DEFAULT AS IDENTITY, "
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
            + "isDeleted int DEFAULT 0, "
            + "FOREIGN KEY (locationID) REFERENCES LOCATION(id), "
            + "FOREIGN KEY (patientID) REFERENCES PATIENT(id), "
            + "FOREIGN KEY (assigneeID) REFERENCES EMPLOYEE(id))";
    stmt.execute(createMedicineDeliverySRTable);
    System.out.println("MedicineDeliverySR Table created");

    String createMentalHealthSRTable =
        "CREATE TABLE MentalHealthSR(id int Primary Key NOT NULL GENERATED BY DEFAULT AS IDENTITY, "
            + "locationID int, "
            + "assigneeID int, "
            + "openDate DATE, "
            + "closeDate DATE, "
            + "status int, "
            + "title VARCHAR(50), "
            + "additionalInfo VARCHAR(2500), "
            + "priority int, "
            + "requestDate DATE, "
            + "isDeleted int DEFAULT 0, "
            + "FOREIGN KEY (locationID) REFERENCES LOCATION(id), "
            + "FOREIGN KEY (assigneeID) REFERENCES EMPLOYEE(id))";
    stmt.execute(createMentalHealthSRTable);
    System.out.println("MentalHealthSR Table created");

    String createPatientDischargeSRTable =
        "CREATE TABLE PatientDischargeSR(id int Primary Key NOT NULL GENERATED BY DEFAULT AS IDENTITY, "
            + "locationID int, "
            + "assigneeID int, "
            + "openDate DATE, "
            + "closeDate DATE, "
            + "status int, "
            + "title VARCHAR(50), "
            + "additionalInfo VARCHAR(2500), "
            + "priority int, "
            + "requestDate DATE, "
            + "isDeleted int DEFAULT 0, "
            + "FOREIGN KEY (locationID) REFERENCES LOCATION(id), "
            + "FOREIGN KEY (assigneeID) REFERENCES EMPLOYEE(id))";
    stmt.execute(createPatientDischargeSRTable);
    System.out.println("PatientDischargeSR Table created");

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
            + "isDeleted int DEFAULT 0, "
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
            + "isDeleted int DEFAULT 0, "
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
            + "isDeleted int DEFAULT 0, "
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
      CredentialManager.getInstance().readCSV("backup/Credentials.csv");
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    getManager(DataBaseObjectType.Location).readCSV(subFolder + "TowerLocationsE.csv");
    getManager(DataBaseObjectType.Equipment).readCSV(subFolder + "EquipmentE.csv");
    getManager(DataBaseObjectType.Employee).readCSV(subFolder + "EmployeesE.csv");
    getManager(DataBaseObjectType.Patient).readCSV(subFolder + "Patient.csv");

    getManager(DataBaseObjectType.AudioVisualSR)
        .readCSV(subFolder + "AudioVisualServiceRequest.csv");
    getManager(DataBaseObjectType.ComputerSR).readCSV(subFolder + "ComputerServiceRequest.csv");
    getManager(DataBaseObjectType.DeceasedBodySR)
        .readCSV(subFolder + "DeceasedBodyRemovalServiceRequest.csv");
    getManager(DataBaseObjectType.ExternalPatientSR)
        .readCSV(subFolder + "ExternalPatientTransportationServiceRequest.csv");
    getManager(DataBaseObjectType.FacilitiesMaintenanceSR)
        .readCSV(subFolder + "FacilitiesMaintenanceServiceRequest.csv");
    getManager(DataBaseObjectType.FoodDeliverySR)
        .readCSV(subFolder + "FoodDeliveryServiceRequest.csv");
    getManager(DataBaseObjectType.GiftAndFloralSR)
        .readCSV(subFolder + "GiftAndFloralServiceRequest.csv");
    getManager(DataBaseObjectType.InternalPatientTransferSR)
        .readCSV(subFolder + "InternalPatientTransportationServiceRequest.csv");
    getManager(DataBaseObjectType.LanguageInterpreterSR)
        .readCSV(subFolder + "LanguageInterpreterServiceRequest.csv");
    getManager(DataBaseObjectType.LaundrySR).readCSV(subFolder + "LaundryServiceRequest.csv");
    getManager(DataBaseObjectType.MedicalEquipmentSR)
        .readCSV(subFolder + "MedicalEquipmentDeliverServiceRequest.csv");
    getManager(DataBaseObjectType.MedicineDeliverySR)
        .readCSV(subFolder + "MedicineDeliveryServiceRequest.csv");
    getManager(DataBaseObjectType.MentalHealthSR)
        .readCSV(subFolder + "MentalHealthServiceRequest.csv");
    getManager(DataBaseObjectType.PatientDischargeSR)
        .readCSV(subFolder + "PatientDischargeServiceRequest.csv");
    getManager(DataBaseObjectType.ReligiousSR).readCSV(subFolder + "ReligiousServiceRequest.csv");
    getManager(DataBaseObjectType.SanitationSR).readCSV(subFolder + "SanitationServiceRequest.csv");
    getManager(DataBaseObjectType.SecuritySR).readCSV(subFolder + "SecurityServiceRequest.csv");
  }

  public void writeDBToCSV(boolean isBackup) throws SQLException, IOException {
    String subFolder = "switchFiles/";
    if (isBackup) {
      subFolder = "backup/";
    }

    getManager(DataBaseObjectType.Location).writeToCSV(subFolder + "TowerLocationsE.csv");
    getManager(DataBaseObjectType.Equipment).writeToCSV(subFolder + "EquipmentE.csv");
    getManager(DataBaseObjectType.Employee).writeToCSV(subFolder + "EmployeesE.csv");
    getManager(DataBaseObjectType.Patient).writeToCSV(subFolder + "Patient.csv");

    getManager(DataBaseObjectType.AudioVisualSR)
        .writeToCSV(subFolder + "AudioVisualServiceRequest.csv");
    getManager(DataBaseObjectType.ComputerSR).writeToCSV(subFolder + "ComputerServiceRequest.csv");
    getManager(DataBaseObjectType.DeceasedBodySR)
        .writeToCSV(subFolder + "DeceasedBodyRemovalServiceRequest.csv");
    getManager(DataBaseObjectType.ExternalPatientSR)
        .writeToCSV(subFolder + "ExternalPatientTransportationServiceRequest.csv");
    getManager(DataBaseObjectType.FacilitiesMaintenanceSR)
        .writeToCSV(subFolder + "FacilitiesMaintenanceServiceRequest.csv");
    getManager(DataBaseObjectType.FoodDeliverySR)
        .writeToCSV(subFolder + "FoodDeliveryServiceRequest.csv");
    getManager(DataBaseObjectType.GiftAndFloralSR)
        .writeToCSV(subFolder + "GiftAndFloralServiceRequest.csv");
    getManager(DataBaseObjectType.InternalPatientTransferSR)
        .writeToCSV(subFolder + "InternalPatientTransportationServiceRequest.csv");
    getManager(DataBaseObjectType.LanguageInterpreterSR)
        .writeToCSV(subFolder + "LanguageInterpreterServiceRequest.csv");
    getManager(DataBaseObjectType.LaundrySR).writeToCSV(subFolder + "LaundryServiceRequest.csv");
    getManager(DataBaseObjectType.MedicalEquipmentSR)
        .writeToCSV(subFolder + "MedicalEquipmentDeliverServiceRequest.csv");
    getManager(DataBaseObjectType.MedicineDeliverySR)
        .writeToCSV(subFolder + "MedicineDeliveryServiceRequest.csv");
    getManager(DataBaseObjectType.MentalHealthSR)
        .writeToCSV(subFolder + "MentalHealthServiceRequest.csv");
    getManager(DataBaseObjectType.PatientDischargeSR)
        .writeToCSV(subFolder + "PatientDischargeServiceRequest.csv");
    getManager(DataBaseObjectType.ReligiousSR)
        .writeToCSV(subFolder + "ReligiousServiceRequest.csv");
    getManager(DataBaseObjectType.SanitationSR)
        .writeToCSV(subFolder + "SanitationServiceRequest.csv");
    getManager(DataBaseObjectType.SecuritySR).writeToCSV(subFolder + "SecurityServiceRequest.csv");
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
      createDBTables();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    // Default connect to Embedded Server
    connection = DriverManager.getConnection(EmbeddedConnectionString);
    stmt = connection.createStatement();
    createDBTables();
  }

  public void switchConnection(DBType type)
      throws SQLException, IOException, CsvValidationException, ParseException {

    // Write to CSV
    DBManager.getInstance().writeDBToCSV(false);

    // Switch Connection
    String connectionString = "";
    switch (type) {
      case AzureCloud:
        connectionString = AzureCloudServerConnectionString;
        break;
      case ClientServer:
        connectionString = ClientServerConnectionString;
        break;
      case Embedded:
        connectionString = EmbeddedConnectionString;
        break;
    }

    // Create Connection
    connection = DriverManager.getConnection(connectionString);
    stmt = connection.createStatement();
    currentType = type;

    // Check if we should transfer data
    if (currentType != DBType.AzureCloud) {
      cleanDBTables();
      DBManager.getInstance().loadDBFromCSV(false);
    }
  }

  private void cleanDBTables() throws SQLException {
    for (DataBaseObjectType dbTable : DataBaseObjectType.values()) {
      stmt.executeUpdate("DELETE FROM " + dbTable.toTableName());
    }
  }

  @Deprecated
  public EquipmentManager getEquipmentManager() throws SQLException {
    return new EquipmentManager();
  }

  @Deprecated
  public LocationManager getLocationManager() throws SQLException {
    return new LocationManager();
  }

  @Deprecated
  public EmployeeManager getEmployeeManager() throws SQLException {
    return new EmployeeManager();
  }

  @Deprecated
  public PatientManager getPatientManager() throws SQLException {
    return new PatientManager();
  }

  @Deprecated
  public StandardSRManager getAudioVisualSRManager() throws SQLException {
    return new StandardSRManager(DataBaseObjectType.AudioVisualSR);
  }

  @Deprecated
  public StandardSRManager getComputerSRManager() throws SQLException {
    return new StandardSRManager(DataBaseObjectType.ComputerSR);
  }

  @Deprecated
  public PatientTransportationSRManager getExternalPatientSRManager() throws SQLException {
    return new PatientTransportationSRManager(false);
  }

  @Deprecated
  public StandardSRManager getFacilitiesMaintenanceSRManager() throws SQLException {
    return new StandardSRManager(DataBaseObjectType.FacilitiesMaintenanceSR);
  }

  @Deprecated
  public FoodDeliverySRManager getFoodDeliverySRManager() throws SQLException {
    return new FoodDeliverySRManager();
  }

  @Deprecated
  public GiftAndFloralSRManager getGiftAndFloralSRManager() throws SQLException {
    return new GiftAndFloralSRManager();
  }

  @Deprecated
  public PatientTransportationSRManager getInternalPatientSRManager() throws SQLException {
    return new PatientTransportationSRManager(true);
  }

  @Deprecated
  public StandardSRManager getLanguageSRManager() throws SQLException {
    return new StandardSRManager(DataBaseObjectType.LanguageInterpreterSR);
  }

  @Deprecated
  public StandardSRManager getLaundrySRManager() throws SQLException {
    return new StandardSRManager(DataBaseObjectType.LaundrySR);
  }

  @Deprecated
  public MedicalEquipmentSRManager getMedicalEquipmentSRManager() throws SQLException {
    return new MedicalEquipmentSRManager();
  }

  @Deprecated
  public MedicineDeliverySRManager getMedicineDeliverySRManager() throws SQLException {
    return new MedicineDeliverySRManager();
  }

  @Deprecated
  public ReligiousSRManager getReligiousSRManager() throws SQLException {
    return new ReligiousSRManager();
  }

  @Deprecated
  public StandardSRManager getSanitationSRManager() throws SQLException {
    return new StandardSRManager(DataBaseObjectType.SanitationSR);
  }

  @Deprecated
  public StandardSRManager getSecuritySRManager() throws SQLException {
    return new StandardSRManager(DataBaseObjectType.SecuritySR);
  }

  public static ObjectManager getManager(DataBaseObjectType managerType) throws SQLException {
    switch (managerType) {
      case Location:
        return new LocationManager();
      case Equipment:
        return new EquipmentManager();
      case Patient:
        return new PatientManager();
      case Employee:
        return new EmployeeManager();
      case GiftAndFloralSR:
        return new GiftAndFloralSRManager();
      case ExternalPatientSR:
        return new PatientTransportationSRManager(false);
      case InternalPatientTransferSR:
        return new PatientTransportationSRManager(true);
      case ReligiousSR:
        return new ReligiousSRManager();
      case MedicalEquipmentSR:
        return new MedicalEquipmentSRManager();
      case MedicineDeliverySR:
        return new MedicineDeliverySRManager();
      case LaundrySR:
      case ComputerSR:
      case DeceasedBodySR:
      case MentalHealthSR:
      case PatientDischargeSR:
      case SecuritySR:
      case SanitationSR:
      case AudioVisualSR:
      case FoodDeliverySR:
      case LanguageInterpreterSR:
      case FacilitiesMaintenanceSR:
        return new StandardSRManager(managerType);
    }
    return null;
  }
}

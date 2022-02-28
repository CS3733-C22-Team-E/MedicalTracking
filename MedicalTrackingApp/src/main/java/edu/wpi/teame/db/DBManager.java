package edu.wpi.teame.db;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.db.objectManagers.*;
import edu.wpi.teame.model.*;
import edu.wpi.teame.model.enums.DBType;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.mongoCodecs.*;
import edu.wpi.teame.model.serviceRequests.*;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.HashMap;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

public final class DBManager {
  private final String AzureCloudServerConnectionString =
      "jdbc:sqlserver://cs3733hospitalapp.database.windows.net:1433;database=hospitalappdb;user=medicaltracking@cs3733hospitalapp;password=u@$YK=$A5A*<\"g$$;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
  private final String ClientServerConnectionString =
      "jdbc:derby://localhost:1527/ClientServer;create=true;username=admin;password=admin";
  private final String EmbeddedConnectionString =
      "jdbc:derby:memory:EmbeddedE;create=true;username=admin;password=admin";

  private final String MongoDBConnectionString =
      "mongodb+srv://admin:admin@cluster0.45z0f.mongodb.net/TeamEMongos?retryWrites=true&w=majority";

  private HashMap<DataBaseObjectType, ObjectManager> managers;
  private final DBType startUpDB = DBType.Embedded;
  private DBType currentType = DBType.Embedded;
  private static DBManager instance;
  private Connection connection;
  private Statement statement;
  private MongoDatabase mongoDatabase;

  public static synchronized DBManager getInstance() {
    if (instance == null) {
      instance = new DBManager();
    }
    return instance;
  }

  private DBManager() {}

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
    statement.execute(createLocationsTable);
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
    statement.execute(createEquipmentTable);
    System.out.println("Equipment Table created");

    String createEmployeeTable =
        "CREATE TABLE Employee(id int Primary Key GENERATED BY DEFAULT AS IDENTITY, "
            + "department int, "
            + "name VARCHAR(100), "
            + "type int, "
            + "isDeleted int DEFAULT 0)";
    statement.execute(createEmployeeTable);
    System.out.println("Employee Table created");

    String createPatientTable =
        "CREATE TABLE Patient(id int Primary Key GENERATED BY DEFAULT AS IDENTITY, "
            + "name VARCHAR(100), "
            + "dateOfBirth DATE, "
            + "currentLocationID int,"
            + "isDeleted int DEFAULT 0, "
            + "FOREIGN KEY (currentLocationID) REFERENCES LOCATION(id))";
    statement.execute(createPatientTable);
    System.out.println("Patient Table created");

    String createCredentialTable =
        "CREATE TABLE Credential(id int Primary Key GENERATED BY DEFAULT AS IDENTITY, "
            + "salt VARCHAR(16), "
            + "username VARCHAR(50) UNIQUE NOT NULL, "
            + "password VARCHAR(64), "
            + "accessLevel int, "
            + "imageURL VARCHAR(5000), "
            + "isDeleted int DEFAULT 0)";
    statement.execute(createCredentialTable);
    System.out.println("Credential Table created");

    String createEdgeTable =
        "CREATE TABLE Edge(id int Primary Key GENERATED BY DEFAULT AS IDENTITY, "
            + "startID int,"
            + "endID int,"
            + "isDeleted int DEFAULT 0, "
            + "FOREIGN KEY (startID) REFERENCES LOCATION(id), "
            + "FOREIGN KEY (endID) REFERENCES LOCATION(id))";
    statement.execute(createEdgeTable);
    System.out.println("Edge Table created");
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
    statement.execute(createAudioVisualSRTable);
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
    statement.execute(createComputerSRTable);
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
    statement.execute(createDeceasedBodyRemovalSRTable);
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
    statement.execute(createExternalPatientSRTable);
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
    statement.execute(createFacilitiesMaintenanceSRTable);
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
    statement.execute(createFoodDeliverySRTable);
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
    statement.execute(createGiftAndFloralSRTable);
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
    statement.execute(createInternalPatientSRTable);
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
    statement.execute(createLanguageSRTable);
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
    statement.execute(createLaundrySRTable);
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
    statement.execute(createEquipmentDeliverySRTable);
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
    statement.execute(createMedicineDeliverySRTable);
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
    statement.execute(createMentalHealthSRTable);
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
    statement.execute(createPatientDischargeSRTable);
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
    statement.execute(createReligionSR);
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
    statement.execute(createSanitationSRTable);
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
    statement.execute(createSecuritySRTable);
    System.out.println("SecuritySR Table created");
  }

  public void switchConnection(DBType type)
      throws SQLException, IOException, CsvValidationException, ParseException {

    if (type != DBType.MongoDB) {
      // Write to CSV
      if (currentType != DBType.AzureCloud) {
        DBManager.getInstance().writeDBToCSV(false);
      }

      // Switch Edge
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

      // Create Edge
      connection = DriverManager.getConnection(connectionString);
      statement = connection.createStatement();
      currentType = type;

      // Check if we should transfer data
      if (currentType != DBType.AzureCloud) {
        cleanDBTables();
        DBManager.getInstance().loadDBFromCSV(false);
      }
    } else {
      currentType = DBType.MongoDB;
    }

    // Load up the data from DB
    loadDB();
  }

  private ObjectManager createManager(DataBaseObjectType dbType) throws SQLException {
    switch (dbType) {
      case Location:
        return new LocationManager();
      case Equipment:
        return new EquipmentManager();
      case Patient:
        return new PatientManager();
      case Employee:
        return new EmployeeManager();
      case Edge:
        return new EdgeManager();
      case Credential:
        return new CredentialManager();
    }
    return new ServiceRequestManager(dbType);
  }

  public void loadDBFromCSV(boolean fromBackup)
      throws CsvValidationException, SQLException, IOException, ParseException {
    String subFolder = "switchFiles/";
    if (fromBackup) {
      cleanDBTables(); // Erase the tables in the DB to restore from CSV
      subFolder = "backup/";
    }

    for (DataBaseObjectType type : DataBaseObjectType.values()) {
      getManager(type).readCSV(subFolder + type.toTableName() + ".csv");
    }
  }

  public void writeDBToCSV(boolean isBackup) throws SQLException, IOException {
    String subFolder = "switchFiles/";
    if (isBackup) {
      subFolder = "backup/";
    }

    for (DataBaseObjectType type : DataBaseObjectType.values()) {
      getManager(type).writeToCSV(subFolder + type.toTableName() + ".csv");
    }
  }

  public ObjectManager getManager(DataBaseObjectType dbType) throws SQLException {
    return managers.get(dbType);
  }

  public void setupDB() throws SQLException, CsvValidationException, IOException, ParseException {
    // Add server drivers
    try {
      Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
      Class.forName("org.apache.derby.jdbc.ClientDriver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      return;
    }

    // Connect to MongoDB
    mongoDatabase = connectToMongoDatabase();

    // Connect to Client/Server... that DB may already have the tables
    try {
      connection = DriverManager.getConnection(ClientServerConnectionString);
      statement = connection.createStatement();
      createDBTables();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    // Default connect to Embedded Server
    connection = DriverManager.getConnection(EmbeddedConnectionString);
    statement = connection.createStatement();
    createDBTables();

    // Create the object managers
    managers = new HashMap<>();
    for (DataBaseObjectType dbType : DataBaseObjectType.values()) {
      ObjectManager manager = createManager(dbType);
      managers.put(dbType, manager);
    }

    // Connect to default server and load up if needed
    if (startUpDB != DBType.AzureCloud || startUpDB != DBType.MongoDB) {
      loadDBFromCSV(true);
    }
    switchConnection(startUpDB);
  }

  private void cleanDBTables() throws SQLException {
    String deleteQuery = "DELETE FROM ";
    if (currentType == DBType.AzureCloud) {
      deleteQuery = "DELETE ";
    }

    DataBaseObjectType[] dbTables = DataBaseObjectType.values();
    for (int i = dbTables.length - 1; i >= 0; i--) {
      statement.executeUpdate(deleteQuery + dbTables[i].toTableName());
    }
  }

  public void loadDB() throws SQLException {
    for (DataBaseObjectType dbType : DataBaseObjectType.values()) {
      managers.get(dbType).forceGetAll();
    }
  }

  public Connection getConnection() {
    return connection;
  }

  public DBType getCurrentType() {
    return currentType;
  }

  public CodecRegistry getObjectCodecs() {
    // Creates instances of the codecs that will translate the objects
    // so that it can be sent to the database and stored in regular objects

    CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(false).build();
    Codec<Credential> credentialCodec = new CredentialCodec();
    Codec<Edge> edgeCodec = new EdgeCodec();
    Codec<Employee> employeeCodec = new EmployeeCodec();
    Codec<Equipment> equipmentCodec = new EquipmentCodec();
    Codec<PatientTransportationServiceRequest> patientTransportationServiceRequestCodec =
        new PatientTransportationServiceRequestCodec();
    Codec<FoodDeliveryServiceRequest> foodDeliveryServiceRequestCodec =
        new FoodDeliveryServiceRequestCodec();
    Codec<GiftAndFloralServiceRequest> giftAndFloralServiceRequestCodec =
        new GiftAndFloralServiceRequestCodec();
    Codec<LanguageInterpreterServiceRequest> languageInterpreterServiceRequestCodec =
        new LanguageInterpreterServiceRequestCodec();
    Codec<Location> locationCodec = new LocationCodec();
    Codec<MedicalEquipmentServiceRequest> medicalEquipmentServiceRequestCodec =
        new MedicalEquipmentServiceRequestCodec();
    Codec<MedicineDeliveryServiceRequest> medicineDeliveryServiceRequestCodec =
        new MedicineDeliveryServiceRequestCodec();
    Codec<Patient> patientCodec = new PatientCodec();
    Codec<ReligiousServiceRequest> religiousServiceRequestCodec =
        new ReligiousServiceRequestCodec();
    Codec<DeceasedBodyRemovalServiceRequest> deceasedBodyRemovalServiceRequestCodec =
        new DeceasedBodyRemovalServiceRequestCodec();
    Codec<PatientDischargeServiceRequest> patientDischargeServiceRequestCodec =
        new PatientDischargeServiceRequestCodec();
    Codec<MentalHealthServiceRequest> mentalHealthServiceRequestCodec =
        new MentalHealthServiceRequestCodec();
    Codec<ServiceRequest> serviceRequestCodec = new ServiceRequestCodec();

    CodecRegistry customCodecs =
        CodecRegistries.fromCodecs(
            credentialCodec,
            edgeCodec,
            employeeCodec,
            equipmentCodec,
            foodDeliveryServiceRequestCodec,
            giftAndFloralServiceRequestCodec,
            languageInterpreterServiceRequestCodec,
            locationCodec,
            medicalEquipmentServiceRequestCodec,
            medicineDeliveryServiceRequestCodec,
            patientCodec,
            religiousServiceRequestCodec,
            deceasedBodyRemovalServiceRequestCodec,
            patientDischargeServiceRequestCodec,
            mentalHealthServiceRequestCodec,
            patientTransportationServiceRequestCodec,
            serviceRequestCodec);

    CodecRegistry pojoCodecRegistry =
        fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider), customCodecs);

    return pojoCodecRegistry;
  }

  public MongoDatabase connectToMongoDatabase() {
    // Creates the options to connect to MongoDB
    MongoClientSettings options =
        MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(MongoDBConnectionString))
            .build();

    try (MongoClient mongoClient = MongoClients.create(options)) {
      // Gets the database
      MongoDatabase database =
          mongoClient.getDatabase("TeamEMongos").withCodecRegistry(getObjectCodecs());

      return database;
    }
  }

  public MongoDatabase getMongoDatabase() {
    return mongoDatabase;
  }
}

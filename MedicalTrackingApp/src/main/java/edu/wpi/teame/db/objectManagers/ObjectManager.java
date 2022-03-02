package edu.wpi.teame.db.objectManagers;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Updates;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.App;
import edu.wpi.teame.db.CSVLineData;
import edu.wpi.teame.db.CSVManager;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.ISQLSerializable;
import edu.wpi.teame.model.*;
import edu.wpi.teame.model.enums.DBType;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.serviceRequests.*;
import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.bson.conversions.Bson;

public abstract class ObjectManager<T extends ISQLSerializable> implements IManager<T> {
  protected DataBaseObjectType objectType;
  private Date lastLoaded = new Date(0);
  protected List<T> loadedObjects;
  private long refreshInterval;

  public ObjectManager(DataBaseObjectType objectType, long refreshInterval) {
    this.refreshInterval = refreshInterval;
    loadedObjects = new ArrayList<>();
    this.objectType = objectType;
  }

  @Override
  public List<T> getAll() throws SQLException {
    if (DBManager.getInstance().getCurrentType() == DBType.MongoDB) {
      loadedObjects = getAll(getClassFromDBType());
      return loadedObjects;
    }

    if (shouldReload()) {
      loadedObjects = getBy(" WHERE isDeleted = 0");
    }
    return loadedObjects;
  }

  public List<T> getAll(Class<T> cls) throws SQLException {
    if (shouldReload()) {
      FindIterable<T> all =
          DBManager.getInstance()
              .getMongoDatabase()
              .getCollection(objectType.toTableName(), cls)
              .withCodecRegistry(DBManager.getInstance().getObjectCodecs())
              .find(eq("isDeleted", 0));

      loadedObjects = new ArrayList<>();

      for (T one : all) {
        if (cls.cast(one) instanceof ServiceRequest) {
          ((ServiceRequest) cls.cast(one)).setDbType(objectType);
          loadedObjects.add(one);
        } else loadedObjects.add(one);
      }
    }
    return loadedObjects;
  }

  @Override
  public List<T> forceGetAll() throws SQLException {
    lastLoaded = new Date(0);
    return getAll();
  }

  @Override
  public List<T> getDeleted() throws SQLException {

    if (DBManager.getInstance().getCurrentType() == DBType.MongoDB) {

      return getDeleted(getClassFromDBType());
    }

    return getBy(" WHERE isDeleted = 1");
  }

  public List<T> getDeleted(Class<T> cls) throws SQLException {
    List<T> result = new LinkedList<>();
    FindIterable<T> objects =
        DBManager.getInstance()
            .getMongoDatabase()
            .getCollection(objectType.toTableName(), cls)
            .withCodecRegistry(DBManager.getInstance().getObjectCodecs())
            .find(eq("isDeleted", 1));

    for (T object : objects) {
      result.add(object);
    }
    return result;
  }

  @Override
  public T get(int id) throws SQLException {
    getAll();
    for (T object : loadedObjects) {
      if (object.getId() == id) {
        return object;
      }
    }
    return null;
  }

  protected List<T> getBy(String whereClause) throws SQLException {
    String getQuery = "SELECT * FROM " + objectType.toTableName() + " " + whereClause;
    ResultSet resultSet =
        DBManager.getInstance().getConnection().createStatement().executeQuery(getQuery);
    List<T> listResult = new LinkedList<>();
    while (resultSet.next()) {
      listResult.add(getCastedType(resultSet));
    }
    return listResult;
  }

  @Override
  public T insert(T newObject) throws SQLException {
    long id;
    if (DBManager.getInstance().getCurrentType() != DBType.MongoDB) {
      StringBuilder insertQuery = new StringBuilder("INSERT INTO ");
      insertQuery.append(objectType.toTableName()).append(newObject.getTableColumns());
      insertQuery.append(" VALUES(");
      insertQuery.append(newObject.getSQLInsertString()).append(")");
      PreparedStatement insertStatement =
          DBManager.getInstance()
              .getConnection()
              .prepareStatement(insertQuery.toString(), Statement.RETURN_GENERATED_KEYS);
      int rowsAffected = insertStatement.executeUpdate();
      ResultSet resultSet = insertStatement.getGeneratedKeys();

      resultSet.next();
      id = resultSet.getLong(1);
      lastLoaded = new Date(0); // Ensure the table is loaded next time we get
    } else {
      DBManager.getInstance()
          .getMongoDatabase()
          .getCollection(objectType.toTableName(), getClassFromDBType())
          .withCodecRegistry(DBManager.getInstance().getObjectCodecs())
          .insertOne(newObject);
      return get(newObject.getId());
    }

    return get((int) id);
  }

  @Override
  public void update(T updatedObject) throws SQLException {
    if (DBManager.getInstance().getCurrentType() != DBType.MongoDB) {
      StringBuilder updateQuery = new StringBuilder("UPDATE ");
      updateQuery.append(objectType.toTableName()).append(" SET ");
      updateQuery.append(updatedObject.getSQLUpdateString());
      DBManager.getInstance()
          .getConnection()
          .createStatement()
          .executeUpdate(updateQuery.toString());
      lastLoaded = new Date(0); // Ensure the table is loaded next time we get
    } else {
      Bson updates = Updates.combine(updatedObject.getMongoUpdates());

      DBManager.getInstance()
          .getMongoDatabase()
          .getCollection(objectType.toTableName(), getClassFromDBType())
          .updateOne(eq("_id", updatedObject.getId()), updates);
    }
  }

  @Override
  public void remove(int id) throws SQLException {
    if (DBManager.getInstance().getCurrentType() != DBType.MongoDB) {
      StringBuilder markIsDeleted = new StringBuilder("UPDATE ");
      markIsDeleted
          .append(objectType.toTableName())
          .append(" SET isDeleted = 1")
          .append(" WHERE id = ")
          .append(id);
      DBManager.getInstance()
          .getConnection()
          .createStatement()
          .executeUpdate(markIsDeleted.toString());
      lastLoaded = new Date(0); // Ensure the table is loaded next time we get
    } else {
      Bson updates = Updates.combine(Updates.set("isDeleted", 1));
      DBManager.getInstance()
          .getMongoDatabase()
          .getCollection(objectType.toTableName(), getClassFromDBType())
          .updateOne(eq("_id", id), updates);
    }
  }

  @Override
  public void restore(int id) throws SQLException {
    if (DBManager.getInstance().getCurrentType() != DBType.MongoDB) {
      StringBuilder restoreQuery = new StringBuilder("UPDATE ");
      restoreQuery
          .append(objectType.toTableName())
          .append(" SET isDeleted = 0")
          .append(" WHERE id = ")
          .append(id);
      DBManager.getInstance()
          .getConnection()
          .createStatement()
          .executeUpdate(restoreQuery.toString());
      lastLoaded = new Date(0); // Ensure the table is loaded next time we get
    } else {
      Bson updates = Updates.combine(Updates.set("isDeleted", 0));

      DBManager.getInstance()
          .getMongoDatabase()
          .getCollection(objectType.toTableName(), getClassFromDBType())
          .updateOne(eq("_id", id), updates);
    }
  }

  @Override
  public void readCSV(String inputFileName)
      throws IOException, SQLException, CsvValidationException, ParseException {
    InputStream filePath =
        App.class.getClassLoader().getResourceAsStream("edu/wpi/teame/csv/" + inputFileName);
    CSVReader csvReader = new CSVReader(new InputStreamReader(filePath));
    CSVLineData lineData = new CSVLineData(csvReader);

    // Check if we found anything in the CSV file
    if (!lineData.readHeaders()) {
      return;
    }

    while (lineData.readNext()) {
      T object = insert(getCastedType(lineData));
      CSVManager.getInstance()
          .putCsvToDBId(objectType, lineData.getColumnInt("id"), object.getId());
    }
  }

  @Override
  public void writeToCSV(String outputFileName) throws IOException, SQLException {
    String home = System.getProperty("user.home");
    String filePath = home + "/Downloads/MedicalTracking/" + outputFileName;

    CSVWriter writer =
        new CSVWriter(
            new FileWriter(filePath),
            ',',
            CSVWriter.NO_QUOTE_CHARACTER,
            CSVWriter.DEFAULT_ESCAPE_CHARACTER,
            CSVWriter.DEFAULT_LINE_END);

    List<T> dbObjectList = forceGetAll();
    List<String[]> data = new ArrayList<String[]>();
    if (dbObjectList.isEmpty()) {
      return;
    }

    data.add(dbObjectList.get(0).getCSVHeaders());
    for (T dbObject : dbObjectList) {
      data.add(dbObject.toCSVData());
    }
    writer.writeAll(data);
    writer.close();
  }

  private T getCastedType(CSVLineData lineData) throws SQLException, ParseException {
    switch (objectType) {
      case AudioVisualSR:
      case ComputerSR:
      case FacilitiesMaintenanceSR:
      case LaundrySR:
      case SanitationSR:
      case SecuritySR:
        return (T) new ServiceRequest(lineData, objectType);
      case DeceasedBodySR:
        return (T) new DeceasedBodyRemovalServiceRequest(lineData);
      case MentalHealthSR:
        return (T) new MentalHealthServiceRequest(lineData);
      case PatientDischargeSR:
        return (T) new PatientDischargeServiceRequest(lineData);
      case ExternalPatientSR:
        return (T) new PatientTransportationServiceRequest(lineData, false);
      case FoodDeliverySR:
        return (T) new FoodDeliveryServiceRequest(lineData);
      case GiftAndFloralSR:
        return (T) new GiftAndFloralServiceRequest(lineData);
      case InternalPatientTransferSR:
        return (T) new PatientTransportationServiceRequest(lineData, true);
      case LanguageInterpreterSR:
        return (T) new LanguageInterpreterServiceRequest(lineData);
      case MedicalEquipmentSR:
        return (T) new MedicalEquipmentServiceRequest(lineData);
      case MedicineDeliverySR:
        return (T) new MedicineDeliveryServiceRequest(lineData);
      case ReligiousSR:
        return (T) new ReligiousServiceRequest(lineData);
      case Credential:
        return (T) new Credential(lineData);
      case Location:
        return (T) new Location(lineData);
      case Equipment:
        return (T) new Equipment(lineData);
      case Employee:
        return (T) new Employee(lineData);
      case Patient:
        return (T) new Patient(lineData);
      case Edge:
        return (T) new Edge(lineData);
    }
    return null;
  }

  public T getCastedType(ResultSet resultSet) throws SQLException {
    switch (objectType) {
      case AudioVisualSR:
      case ComputerSR:
      case FacilitiesMaintenanceSR:
      case LaundrySR:
      case SanitationSR:
      case SecuritySR:
        return (T) new ServiceRequest(resultSet, objectType);
      case MentalHealthSR:
        return (T) new MentalHealthServiceRequest(resultSet);
      case DeceasedBodySR:
        return (T) new DeceasedBodyRemovalServiceRequest(resultSet);
      case PatientDischargeSR:
        return (T) new PatientDischargeServiceRequest(resultSet);
      case ExternalPatientSR:
        return (T) new PatientTransportationServiceRequest(resultSet, false);
      case FoodDeliverySR:
        return (T) new FoodDeliveryServiceRequest(resultSet);
      case GiftAndFloralSR:
        return (T) new GiftAndFloralServiceRequest(resultSet);
      case InternalPatientTransferSR:
        return (T) new PatientTransportationServiceRequest(resultSet, true);
      case LanguageInterpreterSR:
        return (T) new LanguageInterpreterServiceRequest(resultSet);
      case MedicalEquipmentSR:
        return (T) new MedicalEquipmentServiceRequest(resultSet);
      case MedicineDeliverySR:
        return (T) new MedicineDeliveryServiceRequest(resultSet);
      case ReligiousSR:
        return (T) new ReligiousServiceRequest(resultSet);
      case Credential:
        return (T) new Credential(resultSet);
      case Location:
        return (T) new Location(resultSet);
      case Equipment:
        return (T) new Equipment(resultSet);
      case Employee:
        return (T) new Employee(resultSet);
      case Patient:
        return (T) new Patient(resultSet);
      case Edge:
        return (T) new Edge(resultSet);
    }
    return null;
  }

  private Class<T> getClassFromDBType() {
    switch (objectType) {
      case AudioVisualSR:
        return (Class<T>) ServiceRequest.class;
      case ComputerSR:
        return (Class<T>) ServiceRequest.class;
      case FacilitiesMaintenanceSR:
        return (Class<T>) ServiceRequest.class;
      case LaundrySR:
        return (Class<T>) ServiceRequest.class;
      case SanitationSR:
        return (Class<T>) ServiceRequest.class;
      case SecuritySR:
        return (Class<T>) ServiceRequest.class;
      case PatientDischargeSR:
        return (Class<T>) PatientDischargeServiceRequest.class;
      case DeceasedBodySR:
        return (Class<T>) DeceasedBodyRemovalServiceRequest.class;
      case MentalHealthSR:
        return (Class<T>) MentalHealthServiceRequest.class;
      case ExternalPatientSR:
        return (Class<T>) PatientTransportationServiceRequest.class;
      case FoodDeliverySR:
        return (Class<T>) FoodDeliveryServiceRequest.class;
      case GiftAndFloralSR:
        return (Class<T>) GiftAndFloralServiceRequest.class;
      case InternalPatientTransferSR:
        return (Class<T>) PatientTransportationServiceRequest.class;
      case LanguageInterpreterSR:
        return (Class<T>) LanguageInterpreterServiceRequest.class;
      case MedicalEquipmentSR:
        return (Class<T>) MedicalEquipmentServiceRequest.class;
      case MedicineDeliverySR:
        return (Class<T>) MedicineDeliveryServiceRequest.class;
      case ReligiousSR:
        return (Class<T>) ReligiousServiceRequest.class;
      case Credential:
        return (Class<T>) Credential.class;
      case Location:
        return (Class<T>) Location.class;
      case Equipment:
        return (Class<T>) Equipment.class;
      case Employee:
        return (Class<T>) Employee.class;
      case Patient:
        return (Class<T>) Patient.class;
      case Edge:
        return (Class<T>) Edge.class;
    }
    return null;
  }

  private boolean shouldReload() {
    long millisPassed = new Date().getTime() - lastLoaded.getTime();
    long minutesPassed = (millisPassed / 1000) / 60;
    boolean shouldReload = minutesPassed > refreshInterval;

    if (shouldReload) {
      lastLoaded = new Date();
    }
    return shouldReload;
  }
}

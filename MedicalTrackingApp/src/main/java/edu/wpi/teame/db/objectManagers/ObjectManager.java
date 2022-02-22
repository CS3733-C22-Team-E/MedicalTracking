package edu.wpi.teame.db.objectManagers;

import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.ISQLSerializable;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Equipment;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.Patient;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.serviceRequests.*;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public abstract class ObjectManager<T extends ISQLSerializable> implements IManager<T> {
  protected DataBaseObjectType objectType;
  private Connection connection;
  private Statement statement;

  public ObjectManager(DataBaseObjectType objectType) throws SQLException {
    connection = DBManager.getInstance().getConnection();
    statement = connection.createStatement();
    this.objectType = objectType;
  }

  @Override
  public List<T> getAll() throws SQLException {
    return getBy(" WHERE isDeleted = 0");
  }

  @Override
  public List<T> getDeleted() throws SQLException {
    return getBy(" WHERE isDeleted = 1");
  }

  @Override
  public T get(int id) throws SQLException {
    List<T> results = getBy(" WHERE ID = " + id);
    if (results.isEmpty()) {
      return null;
    }
    return results.get(0);
  }

  protected List<T> getBy(String whereClause) throws SQLException {
    String getQuery = "SELECT * FROM " + objectType.toTableName() + " " + whereClause;
    ResultSet resultSet = statement.executeQuery(getQuery);
    List<T> listResult = new LinkedList<>();
    while (resultSet.next()) {
      listResult.add(getCastedType(resultSet));
    }
    return listResult;
  }

  @Override
  public T insert(T newObject) throws SQLException {
    StringBuilder insertQuery = new StringBuilder("INSERT INTO ");
    insertQuery.append(objectType.toTableName()).append(newObject.getTableColumns());
    insertQuery.append(" VALUES(");
    insertQuery.append(newObject.getSQLInsertString()).append(")");
    System.out.println(insertQuery);
    PreparedStatement insertStatement =
        connection.prepareStatement(insertQuery.toString(), Statement.RETURN_GENERATED_KEYS);
    int rowsAffected = insertStatement.executeUpdate();
    ResultSet resultSet = insertStatement.getGeneratedKeys();

    resultSet.next();
    long id = resultSet.getLong(1);
    return get((int) id);
  }

  @Override
  public void update(T updatedObject) throws SQLException {
    StringBuilder updateQuery = new StringBuilder("UPDATE ");
    updateQuery.append(objectType.toTableName()).append(" SET ");
    updateQuery.append(updatedObject.getSQLUpdateString());
    statement.executeUpdate(updateQuery.toString());
  }

  @Override
  public void remove(int id) throws SQLException {
    StringBuilder markIsDeleted = new StringBuilder("UPDATE ");
    markIsDeleted
        .append(objectType.toTableName())
        .append(" SET isDeleted = 1")
        .append(" WHERE id = ")
        .append(id);
    statement.executeUpdate(markIsDeleted.toString());
  }

  @Override
  public void restore() throws SQLException {
    StringBuilder restoreQuery = new StringBuilder("UPDATE ");
    restoreQuery.append(objectType.toTableName()).append(" SET isDeleted = 0");
    statement.executeUpdate(restoreQuery.toString());
  }

  private T getCastedType(ResultSet resultSet) throws SQLException {
    switch (objectType) {
      case AudioVisualSR:
      case ComputerSR:
      case DeceasedBodySR:
      case FacilitiesMaintenanceSR:
      case LaundrySR:
      case SanitationSR:
      case SecuritySR:
      case MentalHealthSR:
      case PatientDischargeSR:
        return (T) new ServiceRequest(resultSet, objectType);
      case ExternalPatientSR:
        return (T) new PatientTransportationServiceRequest(false, resultSet);
      case FoodDeliverySR:
        return (T) new FoodDeliveryServiceRequest(resultSet);
      case GiftAndFloralSR:
        return (T) new GiftAndFloralServiceRequest(resultSet);
      case InternalPatientTransferSR:
        return (T) new PatientTransportationServiceRequest(true, resultSet);
      case LanguageInterpreterSR:
        return (T) new LanguageInterpreterServiceRequest(resultSet);
      case MedicalEquipmentSR:
        return (T) new MedicalEquipmentServiceRequest(resultSet);
      case MedicineDeliverySR:
        return (T) new MedicineDeliveryServiceRequest(resultSet);
      case ReligiousSR:
        return (T) new ReligiousServiceRequest(resultSet);
      case Location:
        return (T) new Location(resultSet);
      case Equipment:
        return (T) new Equipment(resultSet);
      case Employee:
        return (T) new Employee(resultSet);
      case Patient:
        return (T) new Patient(resultSet);
    }
    return null;
  }
}

package edu.wpi.teame.db.objectManagers;

import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.ISQLSerializable;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Equipment;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public abstract class ObjectManager<T extends ISQLSerializable> implements IManager<T> {
  private DataBaseObjectType objectType;
  private Connection connection;
  private Statement statement;

  public ObjectManager(DataBaseObjectType objectType) {
    connection = DBManager.getInstance().getConnection();
    this.objectType = objectType;

    try {
      statement = connection.createStatement();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  protected List<T> getBy(String whereClause) throws SQLException {
    String getQuery = "SELECT * FROM " + getTableName() + " " + whereClause;
    ResultSet resultSet = statement.executeQuery(getQuery);
    List<T> listResult = new LinkedList<>();
    while (resultSet.next()) {
      listResult.add(getCastedType(resultSet));
    }
    return listResult;
  }

  @Override
  public T get(int id) throws SQLException {
    String getQuery = "SELECT * FROM " + getTableName() + " WHERE ID = " + id;
    ResultSet resultSet = statement.executeQuery(getQuery);
    if (resultSet.next()) {
      return getCastedType(resultSet);
    }
    return null;
  }

  @Override
  public List<T> getAll() throws SQLException {
    String getQuery = "SELECT * FROM " + getTableName();
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
    insertQuery.append(getTableName()).append(newObject.getTableColumns());
    insertQuery.append(" VALUES(");
    insertQuery.append(newObject.toSQLInsertString()).append(")");

    PreparedStatement insertStatement =
        connection.prepareStatement(insertQuery.toString(), Statement.RETURN_GENERATED_KEYS);
    int rowsAffected = insertStatement.executeUpdate();
    ResultSet resultSet = insertStatement.getGeneratedKeys();

    resultSet.next();
    long id = resultSet.getLong(1);
    return get((int) id);
  }

  @Override
  public void remove(int id) throws SQLException {
    StringBuilder removeQuery = new StringBuilder("DELETE FROM ");
    removeQuery.append(getTableName()).append(" WHERE id = ");
    removeQuery.append(id).append("");
    statement.executeUpdate(removeQuery.toString());
  }

  @Override
  public void update(T updatedObject) throws SQLException {
    StringBuilder updateQuery = new StringBuilder("UPDATE ");
    updateQuery.append(getTableName()).append(" SET ");
    updateQuery.append(updatedObject.toSQLUpdateString());
    statement.executeUpdate(updateQuery.toString());
  }

  private T getCastedType(ResultSet resultSet) throws SQLException {
    switch (objectType) {
      case MedicalEquipmentSR:
        return (T) new MedicalEquipmentServiceRequest(resultSet);
      case MedicineDeliverySR:
        return (T) new MedicineDeliveryServiceRequest(resultSet);
      case SanitationSR:
        return (T) new SanitationServiceRequest(resultSet);
      case SecuritySR:
        return (T) new SecurityServiceRequest(resultSet);
      case Location:
        return (T) new Location(resultSet);
      case Equipment:
        return (T) new Equipment(resultSet);
      case Employee:
        return (T) new Employee(resultSet);
      default:
        break;
    }
    return null;
  }

  private String getTableName() {
    return objectType.name().toUpperCase(Locale.ROOT);
  }
}

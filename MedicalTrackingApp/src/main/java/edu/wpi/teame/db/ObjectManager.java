package edu.wpi.teame.db;

import edu.wpi.teame.model.Equipment;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.serviceRequests.ISQLSerializable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class ObjectManager<T extends ISQLSerializable> implements IManager<T> {
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

  @Override
  public T get(String id) throws SQLException {
    String getQuery = "SELECT * FROM " + getTableName() + " WHERE ID = '" + id + "'";
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
  public void insert(T newObject) throws SQLException {
    StringBuilder insertQuery = new StringBuilder("INSERT INTO ");
    insertQuery.append(getTableName()).append(" VALUES('");
    insertQuery.append(newObject.toSQLInsertString()).append(")");
    statement.executeUpdate(insertQuery.toString());
  }

  @Override
  public void remove(String id) throws SQLException {
    StringBuilder insertQuery = new StringBuilder("DELETE FROM ");
    insertQuery.append(getTableName()).append(" WHERE id='");
    insertQuery.append(id).append("'");
    statement.executeUpdate(insertQuery.toString());
  }

  @Override
  public void update(T updatedObject) throws SQLException {
    StringBuilder insertQuery = new StringBuilder("UPDATE ");
    insertQuery.append(getTableName()).append(" SET ");
    insertQuery.append(updatedObject.toSQLUpdateString());
    statement.executeUpdate(insertQuery.toString());
  }

  @Override
  public void readCSV(String csvFile) throws IOException {}

  @Override
  public void writeToCSV(String outputFilePath) {}

  private T getCastedType(ResultSet resultSet)
      throws SQLException {
    switch (objectType) {
      case MedicalEquipmentSR:
        return null;
      case MedicineDeliverySR:
        return null;
      case SanitationSR:
        return null;
      case SecuritySR:
        return null;
      case Location:
        return null;
      case Equipment:
        return (T)(new Equipment(resultSet));
      case Employee:
        return null;
      default:
        break;
    }
    return null;
  }

  private String getTableName() {
    return objectType.name();
  }
}

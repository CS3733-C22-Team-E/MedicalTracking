package edu.wpi.teame.db;

import edu.wpi.teame.model.enums.DataBaseObjectType;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IManager<ISQLSerializable> {
  public ISQLSerializable get(String id) throws SQLException;

  public List<ISQLSerializable> getAll() throws SQLException;

  public void insert(ISQLSerializable newObject) throws SQLException;

  void remove(String id) throws SQLException;

  public void update(ISQLSerializable updatedObject) throws SQLException;

  public void readCSV(String csvFile) throws IOException;

  public void writeToCSV(String outputFilePath);
}

package edu.wpi.teame.db.objectManagers;

import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.db.ISQLSerializable;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public interface IManager<T extends ISQLSerializable> {
  public T get(int id) throws SQLException;

  public List<T> getAll() throws SQLException;

  public List<T> getDeleted() throws SQLException;

  public T insert(T newObject) throws SQLException;

  public void remove(int id) throws SQLException;

  public void update(T updatedObject) throws SQLException;

  public void restore() throws SQLException;

  public void readCSV(String inputFileName)
      throws IOException, SQLException, CsvValidationException, ParseException;

  public void writeToCSV(String outputFileName) throws IOException, SQLException;
}

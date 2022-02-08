package edu.wpi.teame.db.objectManagers;

// import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.db.ISQLSerializable;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IManager<T extends ISQLSerializable> {
  public T get(int id) throws SQLException;

  public List<T> getAll() throws SQLException;

  public T insert(T newObject) throws SQLException;

  void remove(int id) throws SQLException;

  public void update(T updatedObject) throws SQLException;

  public void readCSV(String inputFileName) throws IOException, SQLException;

  public void writeToCSV(String outputFileName) throws IOException;
}

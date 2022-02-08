package edu.wpi.teame.db;

import java.io.IOException;
import java.util.LinkedList;

public interface IManager<ISQLSerializable> {
  public ISQLSerializable get(String id);

  public LinkedList<ISQLSerializable> getAll();

  public void insert(ISQLSerializable newObject);

  public void remove(String id);

  public void update(ISQLSerializable updatedObject);

  public void readCSV(String csvFile) throws IOException;

  public void writeToCSV(String outputFilePath);
}

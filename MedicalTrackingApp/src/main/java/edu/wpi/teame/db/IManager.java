package edu.wpi.teame.db;

import java.io.IOException;
import java.util.LinkedList;

public interface IManager<TableObject> {
  public TableObject get(String id);

  public LinkedList<TableObject> getAll();

  public void insert(TableObject newObject);

  public void remove(String id);

  public void update(TableObject updatedObject);

  public void readCSV(String csvFile) throws IOException;

  public void writeToCSV(String outputFilePath);
}
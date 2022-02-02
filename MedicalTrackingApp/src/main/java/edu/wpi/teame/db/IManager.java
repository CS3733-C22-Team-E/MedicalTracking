package edu.wpi.teame.db;

import java.util.LinkedList;

public interface IManager<TableObject> {
  public TableObject get(String id);

  public LinkedList<TableObject> getAll();

  public void insert(TableObject newObject);

  public void remove(String id);

  public void update(TableObject updatedObject);
}
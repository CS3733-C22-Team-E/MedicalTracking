package edu.wpi.teame.db;

import java.sql.ResultSet;

public final class DBManager {
  private static DBManager instance;

  public static DBManager getInstance() {
    if (instance != null) {
      instance = new DBManager();
    }
    return instance;
  }

  private DBManager() {}

  public ResultSet SQLSelect(String tableName) {
    return null;
  }
}

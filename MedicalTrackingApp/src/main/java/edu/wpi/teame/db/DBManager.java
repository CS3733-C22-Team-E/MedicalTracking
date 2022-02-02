package edu.wpi.teame.db;

import java.sql.*;

public final class DBManager {
  private static DBManager instance;
  private Connection connection;
  private Statement stmt;

  public static DBManager getInstance() {
    if (instance != null) {
      instance = new DBManager();
    }
    return instance;
  }

  private DBManager() {
  }

  public ResultSet SQLSelect(String tableName) {
    return null;
  }
}

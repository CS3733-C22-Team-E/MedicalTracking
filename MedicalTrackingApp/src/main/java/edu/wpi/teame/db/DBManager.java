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
    //add jdbc driver
    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      return;
    }
    //connect to the database
    try {
      connection = DriverManager.getConnection("jdbc:derby:memory:ESpikeB;create=true;username=admin;password=admin;");
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {
      stmt = connection.createStatement();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {
      String createLocationsTable = ""
    }catch ()
  }

  public ResultSet SQLSelect(String tableName) {
    return null;
  }
}

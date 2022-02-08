package edu.wpi.teame.db;

import edu.wpi.teame.model.enums.DataBaseObjectType;

public interface ISQLSerializable {
  public DataBaseObjectType getDBType();

  public String toSQLInsertString();

  public String toSQLUpdateString();
}

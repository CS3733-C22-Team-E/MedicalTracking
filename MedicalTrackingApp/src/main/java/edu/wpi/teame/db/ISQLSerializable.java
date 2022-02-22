package edu.wpi.teame.db;

import edu.wpi.teame.model.enums.DataBaseObjectType;

public interface ISQLSerializable extends ICSVSerializable {
  public DataBaseObjectType getDBType();

  public String getSQLInsertString();

  public String getSQLUpdateString();

  public String getTableColumns();
}

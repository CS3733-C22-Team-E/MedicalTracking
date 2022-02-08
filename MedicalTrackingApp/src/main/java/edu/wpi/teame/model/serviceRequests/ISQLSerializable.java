package edu.wpi.teame.model.serviceRequests;

import edu.wpi.teame.model.enums.DataBaseObjectType;

public interface ISQLSerializable {
  public DataBaseObjectType getDBType();

  public String toSQLInsertString();

  public String toSQLUpdateString();
}

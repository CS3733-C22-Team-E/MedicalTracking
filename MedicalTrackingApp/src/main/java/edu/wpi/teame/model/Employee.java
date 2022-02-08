package edu.wpi.teame.model;

import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.serviceRequests.ISQLSerializable;

public class Employee implements ISQLSerializable {

  @Override
  public DataBaseObjectType getDBType() {
    return DataBaseObjectType.Employee;
  }

  @Override
  public String toSQLInsertString() {
    return null;
  }

  @Override
  public String toSQLUpdateString() {
    return null;
  }
}

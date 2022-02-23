package edu.wpi.teame.model;

import edu.wpi.teame.db.CSVLineData;
import edu.wpi.teame.db.ISQLSerializable;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import java.sql.ResultSet;

public class Edge implements ISQLSerializable {
  private boolean isDeleted;
  private Location start;
  private Location end;
  private int id;

  public Edge(ResultSet resultSet) {}

  public Edge(CSVLineData lineData) {}

  @Override
  public String toString() {
    return "id: " + id + ", startID: " + start.getId() + ", endID: " + end.getId();
  }

  @Override
  public String getSQLUpdateString() {
    return "startID = " + start.getId() + ", endID = '" + end.getId() + " WHERE id = " + id;
  }

  @Override
  public String getSQLInsertString() {
    return start.getId() + ", '" + end.getId();
  }

  @Override
  public String[] toCSVData() {
    return new String[] {
      Integer.toString(id), Integer.toString(start.getId()), Integer.toString(end.getId())
    };
  }

  @Override
  public String[] getCSVHeaders() {
    return new String[] {"id", "startID", "endID", "isDeleted"};
  }

  @Override
  public String getTableColumns() {
    return "(startID, endID, isDeleted)";
  }

  @Override
  public DataBaseObjectType getDBType() {
    return DataBaseObjectType.Edge;
  }

  // Getters and Setters
  public boolean getIsDeleted() {
    return isDeleted;
  }

  public int getId() {
    return id;
  }
}

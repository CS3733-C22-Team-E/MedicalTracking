package edu.wpi.teame.model;

import edu.wpi.teame.db.CSVLineData;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.ISQLSerializable;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Edge implements ISQLSerializable {
  private boolean isDeleted;
  private Location start;
  private Location end;
  private int id;

  public Edge(Location start, Location end, int id) {
    this.start = start;
    this.end = end;
    this.id = id;
  }

  public Edge(ResultSet resultSet) throws SQLException {
    this.start =
        (Location)
            DBManager.getInstance()
                .getManager(DataBaseObjectType.Location)
                .get(resultSet.getInt("startID"));
    this.end =
        (Location)
            DBManager.getInstance()
                .getManager(DataBaseObjectType.Location)
                .get(resultSet.getInt("endID"));
    this.isDeleted = resultSet.getBoolean("isDeleted");
    this.id = resultSet.getInt("id");
  }

  public Edge(CSVLineData lineData) throws SQLException {
    this.start = (Location) lineData.getDBObject(DataBaseObjectType.Location, "startID");
    this.end = (Location) lineData.getDBObject(DataBaseObjectType.Location, "endID");
    this.id = lineData.getColumnInt("id");
    this.isDeleted = false;
  }

  @Override
  public String toString() {
    return new StringBuilder()
        .append("id: ")
        .append(id)
        .append(", startID: ")
        .append(start.getId())
        .append(", endID: ")
        .append(end.getId())
        .toString();
  }

  @Override
  public String getSQLUpdateString() {
    return new StringBuilder()
        .append("startID = ")
        .append(start.getId())
        .append(", endID = '")
        .append(end.getId())
        .append(" WHERE id = ")
        .append(id)
        .toString();
  }

  @Override
  public String getSQLInsertString() {
    return new StringBuilder().append(start.getId()).append(", ").append(end.getId()).toString();
  }

  @Override
  public String[] toCSVData() {
    return new String[] {
      Integer.toString(id), Integer.toString(start.getId()), Integer.toString(end.getId()), "0"
    };
  }

  @Override
  public String[] getCSVHeaders() {
    return new String[] {"id", "startID", "endID", "isDeleted"};
  }

  @Override
  public String getTableColumns() {
    return "(startID, endID)";
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

  public void setId(int id) {
    this.id = id;
  }

  public Location getStart() {
    return start;
  }

  public void setStart(Location start) {
    this.start = start;
  }

  public Location getEnd() {
    return end;
  }

  public void setEnd(Location end) {
    this.end = end;
  }
}

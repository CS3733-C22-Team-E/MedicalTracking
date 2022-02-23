package edu.wpi.teame.model;

import edu.wpi.teame.db.CSVLineData;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.ISQLSerializable;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.EquipmentType;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Equipment implements ISQLSerializable {
  private boolean hasPatient;
  private EquipmentType type;
  private Location location;
  private boolean isDeleted;
  private boolean isClean;
  private String name;
  private int id;

  public Equipment(
      int ID,
      Location location,
      EquipmentType type,
      String name,
      boolean hasPatient,
      boolean isClean) {
    this.id = ID;
    this.location = location;
    this.type = type;
    this.name = name;
    this.hasPatient = hasPatient;
    this.isClean = isClean;
    this.isDeleted = false;
  }

  public Equipment(ResultSet resultSet) throws SQLException {
    this.id = resultSet.getInt("id");
    this.hasPatient = resultSet.getBoolean("hasPatient");
    this.type = EquipmentType.values()[resultSet.getInt("type")];
    this.isDeleted = resultSet.getBoolean("isDeleted");
    this.isClean = resultSet.getBoolean("isClean");
    this.name = resultSet.getString("name");
    this.location =
        (Location)
            DBManager.getManager(DataBaseObjectType.Location).get(resultSet.getInt("locationID"));
  }

  public Equipment(CSVLineData lineData) throws SQLException {
    this.id = lineData.getColumnInt("id");
    this.name = lineData.getColumnString("longName");
    this.isClean = lineData.getColumnBoolean("isClean");
    this.hasPatient = lineData.getColumnBoolean("hasPatient");
    this.type = EquipmentType.values()[(lineData.getColumnInt("nodeType"))];
    this.location = (Location) lineData.getDBObject(DataBaseObjectType.Location, "locationNodeID");
  }

  @Override
  public String toString() {
    String locId = location == null ? "" : "" + location.getId();
    return new StringBuilder()
        .append("id: ")
        .append(id)
        .append(", location: ")
        .append(locId)
        .append(", type: ")
        .append(type)
        .append(", name: ")
        .append(name)
        .append(", hasPatient: ")
        .append(hasPatient)
        .append(", isClean: ")
        .append(isClean)
        .toString();
  }

  @Override
  public String getSQLUpdateString() {
    int isCleanInt = isClean ? 1 : 0;
    int hasPatientInt = hasPatient ? 1 : 0;
    return "locationID = "
        + location.getId()
        + ", name = '"
        + name
        + "', type = "
        + type.ordinal()
        + ", isClean = "
        + isCleanInt
        + ", hasPatient = "
        + hasPatientInt
        + " WHERE id = "
        + id;
  }

  @Override
  public String getSQLInsertString() {
    int isCleanInt = isClean ? 1 : 0;
    int hasPatientInt = hasPatient ? 1 : 0;
    return location.getId()
        + ", '"
        + name
        + "', "
        + type.ordinal()
        + ", "
        + isCleanInt
        + ", "
        + hasPatientInt;
  }

  @Override
  public String[] toCSVData() {
    return new String[] {
      Integer.toString(id),
      Integer.toString(location.getId()),
      Integer.toString(type.ordinal()),
      name,
      hasPatient ? "1" : "0",
      isClean ? "1" : "0"
    };
  }

  @Override
  public String[] getCSVHeaders() {
    return new String[] {"id", "locationNodeID", "nodeType", "longName", "hasPatient", "isClean"};
  }

  @Override
  public String getTableColumns() {
    return "(locationID, name, type, isClean, hasPatient)";
  }

  @Override
  public DataBaseObjectType getDBType() {
    return DataBaseObjectType.Equipment;
  }

  // Getters and Setters
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public boolean getIsDeleted() {
    return isDeleted;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location locationNodeID) {
    this.location = locationNodeID;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public EquipmentType getType() {
    return type;
  }

  public void setType(EquipmentType type) {
    this.type = type;
  }

  public boolean isHasPatient() {
    return hasPatient;
  }

  public void setHasPatient(boolean hasPatient) {
    this.hasPatient = hasPatient;
  }

  public boolean isClean() {
    return isClean;
  }

  public void setClean(boolean clean) {
    isClean = clean;
  }
}

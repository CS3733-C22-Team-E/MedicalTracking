package edu.wpi.teame.model;

import edu.wpi.teame.db.ISQLSerializable;
import edu.wpi.teame.db.objectManagers.LocationManager;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.EquipmentType;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Equipment implements ISQLSerializable {
  private boolean hasPatient;
  private EquipmentType type;
  private Location location;
  private boolean isClean;
  private String name;
  private int id;

  public Equipment(
      int ID,
      Location locationNodeID,
      EquipmentType type,
      String name,
      boolean hasPatient,
      boolean isClean) {
    this.id = ID;
    this.location = locationNodeID;
    this.type = type;
    this.name = name;
    this.hasPatient = hasPatient;
    this.isClean = isClean;
  }

  public Equipment(ResultSet resultSet) throws SQLException {
    this.id = resultSet.getInt("id");
    this.location =
        new LocationManager()
            .get(
                resultSet.getInt(
                    "locationID")); // DBManager.getInstance().get(resultSet.getString("location"));
    this.type = EquipmentType.values()[resultSet.getInt("type")];
    this.name = resultSet.getString("name");
    this.hasPatient = resultSet.getBoolean("hasPatient");
    this.isClean = resultSet.getBoolean("isClean");
    System.out.println(location);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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

  @Override
  public String toString() {
    String locId = location == null ? "" : "" + location.getId();
    return "id: "
        + id
        + ", location: "
        + locId
        + ", type: "
        + type
        + ", name: "
        + name
        + ", hasPatient: "
        + hasPatient
        + ", isClean: "
        + isClean;
  }

  @Override
  public DataBaseObjectType getDBType() {
    return DataBaseObjectType.Equipment;
  }

  @Override
  public String toSQLInsertString() {
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
  public String toSQLUpdateString() {
    return "locationID = "
        + location.getId()
        + ", name = '"
        + name
        + "', type = "
        + type.ordinal()
        + ", isClean = "
        + isClean
        + ", hasPatient = "
        + hasPatient;
  }

  @Override
  public String getTableColumns() {
    return "(locationID, name, type, isClean, hasPatient)";
  }
}

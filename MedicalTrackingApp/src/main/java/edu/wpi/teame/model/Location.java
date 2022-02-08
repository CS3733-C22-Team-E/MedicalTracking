package edu.wpi.teame.model;

import edu.wpi.teame.db.ISQLSerializable;
import edu.wpi.teame.model.enums.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Location implements ISQLSerializable {
  private LocationType type;
  private String shortName;
  private String longName;
  private int id;

  private BuildingType building;
  private FloorType floor;
  private int x;
  private int y;

  public Location(
      int id,
      String longName,
      int x,
      int y,
      FloorType floorType,
      BuildingType buildingType,
      LocationType locationType,
      String shortName) {
    building = buildingType;
    type = locationType;
    floor = floorType;
    this.longName = longName;
    this.id = id;
    this.x = x;
    this.y = y;
    this.shortName = shortName;
  }

  public Location(ResultSet resultSet) throws SQLException {
    this.x = resultSet.getInt("x");
    this.y = resultSet.getInt("y");
    this.id = resultSet.getInt("id");
    this.longName = resultSet.getString("longName");
    this.shortName = resultSet.getString("shortName");
    this.floor = FloorType.values()[resultSet.getInt("floor")];
    this.type = LocationType.values()[resultSet.getInt("locationType")];
    this.building = BuildingType.values()[resultSet.getInt("building")];
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getLongName() {
    return longName;
  }

  public void setLongName(String name) {
    this.longName = name;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public LocationType getType() {
    return type;
  }

  public void setType(LocationType type) {
    this.type = type;
  }

  public BuildingType getBuilding() {
    return building;
  }

  public void setBuilding(BuildingType building) {
    this.building = building;
  }

  public FloorType getFloor() {
    return floor;
  }

  public void setFloor(FloorType floor) {
    this.floor = floor;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public String toString() {
    return "nodeID: "
        + id
        + ", xcoord: "
        + x
        + ", ycoord: "
        + y
        + ", floor: "
        + floor
        + ", building: "
        + building
        + ", nodeType: "
        + type
        + ", longName: "
        + longName
        + ", shortName: "
        + shortName;
  }

  @Override
  public DataBaseObjectType getDBType() {
    return DataBaseObjectType.Location;
  }

  @Override
  public String toSQLInsertString() {
    return id
        + ", "
        + type.ordinal()
        + ", '"
        + shortName
        + "', '"
        + longName
        + "', "
        + building.ordinal()
        + ", "
        + floor.ordinal()
        + ", "
        + x
        + ", "
        + y;
  }

  @Override
  public String toSQLUpdateString() {
    return "locationType = "
        + type.ordinal()
        + ", shortName = '"
        + shortName
        + "', longName = '"
        + longName
        + "', building = "
        + building.ordinal()
        + ", floor = "
        + floor.ordinal()
        + ", x = "
        + x
        + ", y = "
        + y;
  }

  @Override
  public String getTableColumns() {
    return " (id, locationType, shortName, longName, building, floor, x, y)";
  }
}

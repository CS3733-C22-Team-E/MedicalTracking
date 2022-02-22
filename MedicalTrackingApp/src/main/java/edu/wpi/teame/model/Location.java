package edu.wpi.teame.model;

import edu.wpi.teame.db.ISQLSerializable;
import edu.wpi.teame.model.enums.*;
import edu.wpi.teame.view.map.Astar.GraphNode;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Location implements ISQLSerializable, GraphNode {
  private LocationType type;
  private String shortName;
  private String longName;
  private int id;

  private BuildingType building;
  private FloorType floor;
  private int x;
  private int y;

  private boolean isDeleted;

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
    this.isDeleted = false;
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
    this.isDeleted = resultSet.getBoolean("isDeleted");
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
  public String getSQLInsertString() {
    return type.ordinal()
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
  public String getSQLUpdateString() {
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
        + y
        + " WHERE id = "
        + id;
  }

  @Override
  public String getTableColumns() {
    return " (locationType, shortName, longName, building, floor, x, y)";
  }

  public boolean equalsByName(Location l) {
    return this.getLongName().equals(l.getLongName());
  }
}

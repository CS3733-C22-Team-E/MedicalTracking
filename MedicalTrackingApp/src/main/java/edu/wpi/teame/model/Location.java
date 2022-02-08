package edu.wpi.teame.model;

import edu.wpi.teame.db.BuildingType;
import edu.wpi.teame.model.enums.FloorType;
import edu.wpi.teame.model.enums.LocationType;

public class Location {
  private String id;
  private String longName;
  private String shortName;
  private LocationType type;

  private BuildingType building;
  private FloorType floor;
  private int x;
  private int y;

  public Location(
      String id,
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

  public String getId() {
    return id;
  }

  public void setId(String id) {
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
}

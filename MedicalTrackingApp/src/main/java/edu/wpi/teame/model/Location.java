package edu.wpi.teame.model;

import com.mongodb.client.model.Updates;
import edu.wpi.teame.db.CSVLineData;
import edu.wpi.teame.db.ISQLSerializable;
import edu.wpi.teame.model.enums.*;
import edu.wpi.teame.view.map.Astar.GraphNode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.bson.conversions.Bson;

public class Location implements ISQLSerializable, GraphNode {
  private LocationType type;
  private boolean isDeleted;
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
    this.isDeleted = false;
  }

  public Location(ResultSet resultSet) throws SQLException {
    this.x = resultSet.getInt("x");
    this.y = resultSet.getInt("y");
    this.id = resultSet.getInt("id");
    this.longName = resultSet.getString("longName");
    this.shortName = resultSet.getString("shortName");
    this.isDeleted = resultSet.getBoolean("isDeleted");
    this.floor = FloorType.values()[resultSet.getInt("floor")];
    this.type = LocationType.values()[resultSet.getInt("locationType")];
    this.building = BuildingType.values()[resultSet.getInt("building")];
  }

  public Location(CSVLineData lineData) throws SQLException {
    this.id = lineData.getColumnInt("id");
    this.x = lineData.getColumnInt("xcoord");
    this.y = lineData.getColumnInt("ycoord");
    this.longName = lineData.getColumnString("longName");
    this.shortName = lineData.getColumnString("shortName");
    this.floor = FloorType.values()[lineData.getColumnInt("floor")];
    this.type = LocationType.valueOf(lineData.getColumnString("nodeType"));
    this.building = BuildingType.valueOf(lineData.getColumnString("building"));
  }

  // in order for a Codec registry to work properly, this constructor needs to exist
  // for now, it won't instantiate any variables
  public Location() {}

  @Override
  public String toString() {
    return new StringBuilder()
        .append("nodeID: ")
        .append(id)
        .append(", x: ")
        .append(x)
        .append(", y: ")
        .append(y)
        .append(", floor: ")
        .append(floor)
        .append(", building: ")
        .append(building)
        .append(", nodeType: ")
        .append(type)
        .append(", longName: ")
        .append(longName)
        .append(", shortName: ")
        .append(shortName)
        .toString();
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
  public List<Bson> getMongoUpdates() {
    List<Bson> updates = new ArrayList<>();
    updates.add(Updates.set("locationType", type.ordinal()));
    updates.add(Updates.set("shortName", shortName));
    updates.add(Updates.set("longName", longName));
    updates.add(Updates.set("building", building.ordinal()));
    updates.add(Updates.set("floor", floor.ordinal()));
    updates.add(Updates.set("x", x));
    updates.add(Updates.set("y", y));

    return updates;
  }

  @Override
  public String[] toCSVData() {
    return new String[] {
      Integer.toString(id),
      Integer.toString(x),
      Integer.toString(y),
      Integer.toString(floor.ordinal()),
      building.toString(),
      type.toString(),
      longName,
      shortName
    };
  }

  @Override
  public String[] getCSVHeaders() {
    return new String[] {
      "id", "xcoord", "ycoord", "floor", "building", "nodeType", "longName", "shortName"
    };
  }

  @Override
  public String getTableColumns() {
    return " (locationType, shortName, longName, building, floor, x, y)";
  }

  @Override
  public DataBaseObjectType getDBType() {
    return DataBaseObjectType.Location;
  }

  public boolean equalsByName(Location other) {
    return longName.equals(other.longName);
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

  public boolean isDeleted() {
    return isDeleted;
  }

  public void setDeleted(boolean deleted) {
    isDeleted = deleted;
  }
}

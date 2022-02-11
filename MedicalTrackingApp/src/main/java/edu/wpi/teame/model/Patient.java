package edu.wpi.teame.model;

import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.ISQLSerializable;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Patient implements ISQLSerializable {
  private Location currentLocation;
  private String name;
  private int id;

  public Patient(Location currentLocation, String name, int id) {
    this.currentLocation = currentLocation;
    this.name = name;
    this.id = id;
  }

  public Patient(ResultSet resultSet) throws SQLException {
    this.currentLocation =
        DBManager.getInstance().getLocationManager().get(resultSet.getInt("locationID"));
    this.name = resultSet.getString("name");
    this.id = resultSet.getInt("id");
  }

  @Override
  public DataBaseObjectType getDBType() {
    return DataBaseObjectType.Patient;
  }

  @Override
  public String getSQLInsertString() {
    // TODO: Needs to be updated
    return null;
  }

  @Override
  public String getSQLUpdateString() {
    // TODO: Needs to be updated
    return null;
  }

  @Override
  public String getTableColumns() {
    // TODO: Needs to be updated
    return null;
  }

  public Location getCurrentLocation() {
    return currentLocation;
  }

  public void setCurrentLocation(Location currentLocation) {
    this.currentLocation = currentLocation;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}

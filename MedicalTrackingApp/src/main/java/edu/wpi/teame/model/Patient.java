package edu.wpi.teame.model;

import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.ISQLSerializable;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Patient implements ISQLSerializable {
  private Location currentLocation;
  private Date dateOfBirth;
  private String name;
  private int id;

  public Patient(Location currentLocation, Date dateOfBirth, String name, int id) {
    this.currentLocation = currentLocation;
    this.dateOfBirth = dateOfBirth;
    this.name = name;
    this.id = id;
  }

  public Patient(ResultSet resultSet) throws SQLException {
    this.currentLocation =
        DBManager.getInstance().getLocationManager().get(resultSet.getInt("locationID"));
    this.dateOfBirth = resultSet.getDate("dateOfBirth");
    this.name = resultSet.getString("name");
    this.id = resultSet.getInt("id");
  }

  @Override
  public DataBaseObjectType getDBType() {
    return DataBaseObjectType.Patient;
  }

  @Override
  public String getSQLInsertString() {
    return "'" + name + "', '" + dateOfBirth.toString() + "', " + currentLocation.getId();
  }

  @Override
  public String getSQLUpdateString() {
    return "name = '"
        + name
        + "', dateOfBirth = '"
        + dateOfBirth.toString()
        + "', currentLocationID = "
        + currentLocation.getId()
        + " WHERE id = "
        + id;
  }

  @Override
  public String getTableColumns() {
    return "(name, dateOfBirth, currentLocationID)";
  }

  public Location getCurrentLocation() {
    return currentLocation;
  }

  public void setCurrentLocation(Location currentLocation) {
    this.currentLocation = currentLocation;
  }

  public Date getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
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

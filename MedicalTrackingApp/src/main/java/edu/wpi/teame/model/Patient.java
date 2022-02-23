package edu.wpi.teame.model;

import edu.wpi.teame.db.CSVLineData;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.ISQLSerializable;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

public class Patient implements ISQLSerializable {
  private Location currentLocation;
  private Date dateOfBirth;
  private boolean isDeleted;
  private String name;
  private int id;

  public Patient(Location currentLocation, Date dateOfBirth, String name, int id) {
    this.currentLocation = currentLocation;
    this.dateOfBirth = dateOfBirth;
    this.isDeleted = false;
    this.name = name;
    this.id = id;
  }

  public Patient(ResultSet resultSet) throws SQLException {
    this.currentLocation =
        (Location)
            DBManager.getManager(DataBaseObjectType.Location)
                .get(resultSet.getInt("currentLocationID"));
    this.isDeleted = resultSet.getBoolean("isDeleted");
    this.dateOfBirth = resultSet.getDate("dateOfBirth");
    this.name = resultSet.getString("name");
    this.id = resultSet.getInt("id");
  }

  public Patient(CSVLineData lineData) throws SQLException, ParseException {
    this.isDeleted = false;
    this.name = lineData.getColumnString("name");
    this.dateOfBirth = lineData.getColumnDate("dateOfBirth");
    this.currentLocation = (Location) lineData.getDBObject(DataBaseObjectType.Location, "currentLocation");
  }

  @Override
  public String toString() {
    return "patientId: "
            + id
            + ", patientName: "
            + name
            + ", dateOfBirth: "
            + dateOfBirth.toString()
            + ", currentLocationId: "
            + currentLocation.getId()
            + ", currentLocationName: "
            + currentLocation.getLongName();
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
  public String getSQLInsertString() {
    return "'" + name + "', '" + dateOfBirth.toString() + "', " + currentLocation.getId();
  }

  @Override
  public String[] toCSVData() {
    return new String[] {
      name, dateOfBirth.toString(), Integer.toString(currentLocation.getId()), Integer.toString(id)
    };
  }

  @Override
  public String[] getCSVHeaders() {
    return new String[] {"name", "dateOfBirth", "currentLocation", "id"};
  }

  @Override
  public String getTableColumns() {
    return "(name, dateOfBirth, currentLocationID)";
  }

  @Override
  public DataBaseObjectType getDBType() {
    return DataBaseObjectType.Patient;
  }

  // Getters and Setters
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

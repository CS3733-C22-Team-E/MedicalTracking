package edu.wpi.teame.model;

import com.mongodb.client.model.Updates;
import edu.wpi.teame.db.CSVLineData;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.ISQLSerializable;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.bson.conversions.Bson;

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
            DBManager.getInstance()
                .getManager(DataBaseObjectType.Location)
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
    this.currentLocation =
        (Location) lineData.getDBObject(DataBaseObjectType.Location, "currentLocation");
  }

  // in order for a Codec registry to work properly, this constructor needs to exist
  // for now, it only sets dbType
  public Patient() {}

  @Override
  public String toString() {
    return new StringBuilder()
        .append("patientId: ")
        .append(id)
        .append(", patientName: ")
        .append(name)
        .append(", dateOfBirth: ")
        .append(dateOfBirth.toString())
        .append(", currentLocationId: ")
        .append(currentLocation.getId())
        .append(", currentLocationName: ")
        .append(currentLocation.getLongName())
        .toString();
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

  @Override
  public List<Bson> getMongoUpdates() {
    List<Bson> updates = new ArrayList<>();
    updates.add(Updates.set("name", name));
    updates.add(Updates.set("dateOfBirth", dateOfBirth.toString()));
    updates.add(Updates.set("currentLocationID", currentLocation.getId()));
    updates.add(Updates.set("name", name));
    return updates;
  }

  public void setId(int id) {
    this.id = id;
  }

  public boolean getIsDeleted() {
    return isDeleted;
  }

  public void setDeleted(boolean deleted) {
    isDeleted = deleted;
  }
}

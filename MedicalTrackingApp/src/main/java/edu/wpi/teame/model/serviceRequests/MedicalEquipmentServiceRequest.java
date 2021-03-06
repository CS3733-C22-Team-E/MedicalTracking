package edu.wpi.teame.model.serviceRequests;

import com.mongodb.client.model.Updates;
import edu.wpi.teame.db.CSVLineData;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.*;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.ServiceRequestPriority;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.bson.conversions.Bson;

public final class MedicalEquipmentServiceRequest extends ServiceRequest {
  private Equipment equipment;

  public MedicalEquipmentServiceRequest(
      ServiceRequestPriority priority,
      ServiceRequestStatus status,
      String additionalInfo,
      Employee assignee,
      Location location,
      Date requestDate,
      Date closeDate,
      Date openDate,
      String title,
      int id,
      Equipment equipment) {
    super(
        DataBaseObjectType.MedicalEquipmentSR,
        priority,
        status,
        additionalInfo,
        assignee,
        location,
        requestDate,
        closeDate,
        openDate,
        title,
        id);
    this.equipment = equipment;
  }

  public MedicalEquipmentServiceRequest(ResultSet resultSet) throws SQLException {
    super(resultSet, DataBaseObjectType.MedicalEquipmentSR);
    this.equipment =
        (Equipment)
            DBManager.getInstance()
                .getManager(DataBaseObjectType.Equipment)
                .get(resultSet.getInt("equipmentID"));
  }

  public MedicalEquipmentServiceRequest(CSVLineData lineData) throws SQLException, ParseException {
    super(lineData, DataBaseObjectType.MedicalEquipmentSR);
    this.equipment = (Equipment) lineData.getDBObject(DataBaseObjectType.Equipment, "equipmentID");
  }

  // in order for a Codec registry to work properly, this constructor needs to exist
  // for now, it only sets dbType
  public MedicalEquipmentServiceRequest() {
    dbType = DataBaseObjectType.MedicalEquipmentSR;
  }

  @Override
  public String getSQLUpdateString() {
    return getRawUpdateString() + ", equipmentID = " + equipment.getId() + " WHERE id = " + id;
  }

  @Override
  public String getSQLInsertString() {
    return super.getSQLInsertString() + ", " + equipment.getId();
  }

  public List<Bson> getMongoUpdates() {
    List<Bson> updates = super.getMongoUpdates();
    updates.add(Updates.set("equipmentID", equipment.getId()));

    return updates;
  }

  @Override
  public String[] toCSVData() {
    List<String> csvData = new ArrayList<>();
    csvData.addAll(List.of(super.toCSVData()));
    csvData.add(Integer.toString(equipment.getId()));

    String[] retArr = new String[csvData.size()];
    return csvData.toArray(retArr);
  }

  @Override
  public String[] getCSVHeaders() {
    List<String> csvHeaders = new ArrayList<>();
    csvHeaders.addAll(List.of(super.getCSVHeaders()));
    csvHeaders.add("equipmentID");

    String[] retArr = new String[csvHeaders.size()];
    return csvHeaders.toArray(retArr);
  }

  @Override
  public String getTableColumns() {
    return "(locationID, assigneeID, openDate, closeDate, status, title, additionalInfo, priority, requestDate, equipmentID)";
  }

  // Getters and Setters
  public Equipment getEquipment() {
    return equipment;
  }

  public void setEquipment(Equipment equipment) {
    this.equipment = equipment;
  }
}

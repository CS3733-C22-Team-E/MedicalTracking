package edu.wpi.teame.model.serviceRequests;

import edu.wpi.teame.db.CSVLineData;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.objectManagers.EquipmentManager;
import edu.wpi.teame.model.*;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.ServiceRequestPriority;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

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
    this.equipment = (Equipment) DBManager.getManager(DataBaseObjectType.Equipment).get(resultSet.getInt("equipmentID"));
  }

  public MedicalEquipmentServiceRequest(CSVLineData lineData) throws SQLException, ParseException {
    super(lineData, DataBaseObjectType.MedicalEquipmentSR);
    this.equipment = (Equipment) DBManager.getManager(DataBaseObjectType.Equipment).get(lineData.getColumnInt("equipmentID"));
  }

  @Override
  public String getSQLUpdateString() {
    return getRawUpdateString() + ", equipmentID = " + equipment.getId() + " WHERE id = " + id;
  }

  @Override
  public String getSQLInsertString() {
    return super.getSQLInsertString() + ", " + equipment.getId();
  }

  @Override
  public String getTableColumns() {
    return "(locationID, assigneeID, openDate, closeDate, status, title, additionalInfo, priority, requestDate, equipmentID)";
  }

  //Getters and Setters
  public Equipment getEquipment() {
    return equipment;
  }

  public void setEquipment(Equipment equipment) {
    this.equipment = equipment;
  }
}

package edu.wpi.teame.model.serviceRequests;

import edu.wpi.teame.db.objectManagers.EquipmentManager;
import edu.wpi.teame.model.*;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.ServiceRequestPriority;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class MedicalEquipmentServiceRequest extends ServiceRequest {
  private Equipment equipment;

  private MedicalEquipmentServiceRequest(
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
    this.equipment = new EquipmentManager().get(resultSet.getInt("equipmentID"));
  }

  //Todo: Figure this out when we finalize what we're storing and it's names
  @Override
  public String getSQLInsertString() {
    return super.getSQLInsertString() + ", " + equipment.getId();
  }

  //Todo: Figure this out when we finalize what we're storing and it's names
  @Override
  public String getSQLUpdateString() {
    return super.getSQLInsertString() + ", equipment = " + equipment.getId() + "WHERE id = " + id;
  }

  //Todo: Figure this out when we finalize what we're storing and it's names
  @Override
  public String getTableColumns() {
    return super.getTableColumns() + "equipmentID)";
  }

  public Equipment getEquipment() {
    return equipment;
  }

  public void setEquipment(Equipment equipment) {
    this.equipment = equipment;
  }
}

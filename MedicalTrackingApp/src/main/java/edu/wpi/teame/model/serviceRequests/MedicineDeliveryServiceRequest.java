package edu.wpi.teame.model.serviceRequests;

import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.FloorType;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import java.sql.Date;

public class MedicineDeliveryServiceRequest extends ServiceRequest {
  private Date deliveryDate;

  protected MedicineDeliveryServiceRequest(
      ServiceRequestStatus requestStatus,
      Employee assignee,
      Location location,
      FloorType floorType,
      Date closeDate,
      Date openDate,
      int id,
      Date deliveryDate) {
    super(requestStatus, assignee, location, floorType, closeDate, openDate, id);
    this.deliveryDate = deliveryDate;
  }

  @Override
  public String toSQLInsertString() {
    return super.toSQLInsertString() + ", " + deliveryDate.toString();
  }

  @Override
  public String toSQLUpdateString() {
    return super.toSQLUpdateString() + ", deliveryDate = " + deliveryDate.toString();
  }

  @Override
  public DataBaseObjectType getDBType() {
    return DataBaseObjectType.MedicineDeliverySR;
  }

  public Date getDeliveryDate() {
    return deliveryDate;
  }

  public void setDeliveryDate(Date deliveryDate) {
    this.deliveryDate = deliveryDate;
  }
}

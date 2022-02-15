package edu.wpi.teame.model.serviceRequests;

import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.Patient;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.ServiceRequestPriority;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class MedicineDeliveryServiceRequest extends ServiceRequest {
  private String medicineQuantity;
  private String medicineName;
  private Patient patient;

  public MedicineDeliveryServiceRequest(
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
      String medicineName,
      String medicineQuantity,
      Patient patient) {

    super(
        DataBaseObjectType.MedicineDeliverySR,
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
    this.medicineQuantity = medicineQuantity;
    this.medicineName = medicineName;
    this.patient = patient;
  }

  public MedicineDeliveryServiceRequest(ResultSet resultSet) throws SQLException {
    super(resultSet, DataBaseObjectType.MedicineDeliverySR);
    this.patient = DBManager.getInstance().getPatientManager().get(resultSet.getInt("patientID"));
    this.medicineQuantity = resultSet.getString("medicineQuantity");
    this.medicineName = resultSet.getString("medicineName");
  }

  @Override
  public String getSQLInsertString() {
    return super.getSQLInsertString()
        + ", '"
        + medicineName
        + "', "
        + patient.getId()
        + ", '"
        + medicineQuantity
        + "', ";
  }

  @Override
  public String getSQLUpdateString() {
    return super.getSQLUpdateString()
        + ", "
        + "medicineName = '"
        + medicineName
        + "',"
        + "patientID = "
        + patient.getId()
        + ", "
        + "medicineQuantity = '"
        + medicineQuantity
        + "'"
        + "WHERE id = "
        + id;
  }

  @Override
  public String getTableColumns() {
    return super.getTableColumns() + ", medicineName, patientID, medicineQuantity)";
  }

  public String getMedicineQuantity() {
    return medicineQuantity;
  }

  public void setMedicineQuantity(String medicineQuantity) {
    this.medicineQuantity = medicineQuantity;
  }

  public String getMedicineName() {
    return medicineName;
  }

  public void setMedicineName(String medicineName) {
    this.medicineName = medicineName;
  }

  public Patient getPatient() {
    return patient;
  }

  public void setPatient(Patient patient) {
    this.patient = patient;
  }
}

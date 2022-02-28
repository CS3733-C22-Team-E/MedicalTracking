package edu.wpi.teame.model.serviceRequests;

import edu.wpi.teame.db.CSVLineData;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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

  // in order for a Codec registry to work properly, this constructor needs to exist
  // for now, it only sets dbType
  public MedicineDeliveryServiceRequest() {
    dbType = DataBaseObjectType.MedicineDeliverySR;
  }

  public MedicineDeliveryServiceRequest(ResultSet resultSet) throws SQLException {
    super(resultSet, DataBaseObjectType.MedicineDeliverySR);
    this.patient =
        (Patient)
            DBManager.getInstance()
                .getManager(DataBaseObjectType.Patient)
                .get(resultSet.getInt("patientID"));
    this.medicineQuantity = resultSet.getString("medicineQuantity");
    this.medicineName = resultSet.getString("medicineName");
  }

  public MedicineDeliveryServiceRequest(CSVLineData lineData) throws SQLException, ParseException {
    super(lineData, DataBaseObjectType.MedicineDeliverySR);
    this.patient = (Patient) lineData.getDBObject(DataBaseObjectType.Patient, "patientID");
    this.medicineQuantity = lineData.getColumnString("medicineQuantity");
    this.medicineName = lineData.getColumnString("medicineName");
  }

  @Override
  public String getSQLUpdateString() {
    return getRawUpdateString()
        + ", "
        + "medicineName = '"
        + medicineName
        + "', "
        + "patientID = "
        + patient.getId()
        + ", "
        + "medicineQuantity = '"
        + medicineQuantity
        + "' "
        + "WHERE id = "
        + id;
  }

  @Override
  public String getSQLInsertString() {
    String patientID = patient == null ? "NULL" : Integer.toString(patient.getId());
    return super.getSQLInsertString()
        + ", '"
        + medicineName
        + "', "
        + patientID
        + ", '"
        + medicineQuantity
        + "'";
  }

  @Override
  public String[] toCSVData() {
    List<String> csvData = new ArrayList<>();
    csvData.addAll(List.of(super.toCSVData()));
    csvData.add(medicineName);
    csvData.add(Integer.toString(patient.getId()));
    csvData.add(medicineQuantity);

    String[] retArr = new String[csvData.size()];
    return csvData.toArray(retArr);
  }

  @Override
  public String[] getCSVHeaders() {
    List<String> csvHeaders = new ArrayList<>();
    csvHeaders.addAll(List.of(super.getCSVHeaders()));
    csvHeaders.add("medicineName");
    csvHeaders.add("patientID");
    csvHeaders.add("medicineQuantity");

    String[] retArr = new String[csvHeaders.size()];
    return csvHeaders.toArray(retArr);
  }

  @Override
  public String getTableColumns() {
    return "(locationID, assigneeID, openDate, closeDate, status, title, additionalInfo, priority, requestDate"
        + ", medicineName, patientID, medicineQuantity)";
  }

  // Getters and Setters
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

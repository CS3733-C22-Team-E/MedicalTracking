package edu.wpi.teame.db.objectManagers.serviceRequests;

// import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.db.objectManagers.ObjectManager;
import edu.wpi.teame.model.enums.*;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.serviceRequests.MedicalEquipmentServiceRequest;
import java.sql.SQLException;

public final class MedicalEquipmentSRManager extends ObjectManager<MedicalEquipmentServiceRequest> {
  public MedicalEquipmentSRManager() throws SQLException {
    super(DataBaseObjectType.MedicalEquipmentSR);
  }
}

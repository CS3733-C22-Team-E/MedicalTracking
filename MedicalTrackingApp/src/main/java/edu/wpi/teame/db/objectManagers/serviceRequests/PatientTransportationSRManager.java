package edu.wpi.teame.db.objectManagers.serviceRequests;

import edu.wpi.teame.db.objectManagers.*;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.serviceRequests.PatientTransportationServiceRequest;
import java.sql.SQLException;

public final class PatientTransportationSRManager
    extends ObjectManager<PatientTransportationServiceRequest> {
  public PatientTransportationSRManager(boolean isInternal) throws SQLException {
    super(
        isInternal
            ? DataBaseObjectType.InternalPatientTransferSR
            : DataBaseObjectType.ExternalPatientSR);
  }
}

package edu.wpi.teame.db.objectManagers.serviceRequests;

import edu.wpi.teame.db.objectManagers.ObjectManager;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.serviceRequests.LanguageInterpreterServiceRequest;
import java.sql.SQLException;

public final class LanguageInterpreterSRManager
    extends ObjectManager<LanguageInterpreterServiceRequest> {
  public LanguageInterpreterSRManager(DataBaseObjectType dbType) throws SQLException {
    super(dbType);
  }
}

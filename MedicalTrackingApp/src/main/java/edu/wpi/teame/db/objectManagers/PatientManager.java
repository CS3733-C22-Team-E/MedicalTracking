package edu.wpi.teame.db.objectManagers;

import edu.wpi.teame.model.Patient;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import java.sql.SQLException;

public final class PatientManager extends ObjectManager<Patient> {
  public PatientManager() {
    super(DataBaseObjectType.Patient, 5);
  }

  public Patient getByName(String name) throws SQLException {
    return super.getBy("WHERE name = '" + name + "'").get(0);
  }
}

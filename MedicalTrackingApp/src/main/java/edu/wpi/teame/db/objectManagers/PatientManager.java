package edu.wpi.teame.db.objectManagers;

import static com.mongodb.client.model.Filters.eq;

import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Patient;
import edu.wpi.teame.model.enums.DBType;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import java.sql.SQLException;

public final class PatientManager extends ObjectManager<Patient> {
  public PatientManager() {
    super(DataBaseObjectType.Patient, 5);
  }

  public Patient getByName(String name) throws SQLException {
    System.out.println("Mogo getbynsmer");

    if (DBManager.getInstance().getCurrentType() == DBType.MongoDB) {
      Patient patient =
          DBManager.getInstance()
              .getMongoDatabase()
              .getCollection(objectType.toTableName(), Patient.class)
              .withCodecRegistry(DBManager.getInstance().getObjectCodecs())
              .find(eq("name", name))
              .first();
      return patient;
    }

    return super.getBy("WHERE name = '" + name + "'").get(0);
  }
}

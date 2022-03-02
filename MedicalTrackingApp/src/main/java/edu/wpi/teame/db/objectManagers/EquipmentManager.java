package edu.wpi.teame.db.objectManagers;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Equipment;
import edu.wpi.teame.model.enums.*;
import java.io.*;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public final class EquipmentManager extends ObjectManager<Equipment> {
  public EquipmentManager() {
    super(DataBaseObjectType.Equipment, 5);
  }

  public List<Equipment> getByAllAvailable() throws SQLException {
    if (DBManager.getInstance().getCurrentType() == DBType.MongoDB) {
      System.out.println("Mogo getVail");

      FindIterable<Equipment> objects =
          DBManager.getInstance()
              .getMongoDatabase()
              .getCollection(objectType.toTableName(), Equipment.class)
              .withCodecRegistry(DBManager.getInstance().getObjectCodecs())
              .find(Filters.and(eq("hasPatient", 0), eq("isClean", 0)));

      List<Equipment> result = new LinkedList<>();
      for (Equipment equipment : objects) {
        result.add(equipment);
      }
      return result;
    }

    // todo shouldn't isClean be 1 if true?
    return super.getBy("WHERE hasPatient = 0 AND isClean = 0");
  }

  public Equipment getByName(String name) throws SQLException {
    if (DBManager.getInstance().getCurrentType() == DBType.MongoDB) {
      System.out.println("Mogo getbynsm");

      Equipment equipment =
          DBManager.getInstance()
              .getMongoDatabase()
              .getCollection(objectType.toTableName(), Equipment.class)
              .withCodecRegistry(DBManager.getInstance().getObjectCodecs())
              .find(eq("name", name))
              .first();
      return equipment;
    }
    return super.getBy("WHERE name = '" + name + "'").get(0);
  }

  public List<Equipment> getEquipmentByType(EquipmentType equipmentType) throws SQLException {
    return super.getBy("WHERE type = " + equipmentType.ordinal());
  }
}

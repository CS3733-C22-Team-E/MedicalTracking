package edu.wpi.teame.db.objectManagers;

import edu.wpi.teame.model.Equipment;
import edu.wpi.teame.model.enums.*;
import java.io.*;
import java.sql.SQLException;
import java.util.List;

public final class EquipmentManager extends ObjectManager<Equipment> {
  public EquipmentManager() {
    super(DataBaseObjectType.Equipment, 5);
  }

  public List<Equipment> getByAllAvailable() throws SQLException {
    return super.getBy("WHERE hasPatient = 0 AND isClean = 0");
  }

  public Equipment getByName(String name) throws SQLException {
    return super.getBy("WHERE name = '" + name + "'").get(0);
  }

  public List<Equipment> getEquipmentByType(EquipmentType equipmentType) throws SQLException {
    return super.getBy("WHERE type = " + equipmentType.ordinal());
  }
}

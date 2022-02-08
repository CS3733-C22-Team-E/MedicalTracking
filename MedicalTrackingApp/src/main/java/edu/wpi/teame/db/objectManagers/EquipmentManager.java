package edu.wpi.teame.db.objectManagers;

// import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.model.Equipment;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.EquipmentType;
import java.io.IOException;
import java.sql.SQLException;

public final class EquipmentManager extends ObjectManager<Equipment> {
  public EquipmentManager() {
    super(DataBaseObjectType.Equipment);
  }

  public Equipment getByAvailability(EquipmentType equipmentType, boolean hasPatient)
      throws SQLException {
    return super.getBy(
            "WHERE hasPatient = " + hasPatient + " AND type = " + equipmentType.ordinal())
        .get(0);
  }

  @Override
  public void readCSV(String inputFileName) throws IOException, SQLException {}

  @Override
  public void writeToCSV(String outputFileName) throws IOException {}
}

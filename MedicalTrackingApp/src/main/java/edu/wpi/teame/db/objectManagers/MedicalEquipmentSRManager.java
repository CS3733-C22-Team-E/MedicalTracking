package edu.wpi.teame.db.objectManagers;

// import com.opencsv.exceptions.CsvValidationException;
import com.opencsv.CSVWriter;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.serviceRequests.MedicalEquipmentServiceRequest;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicalEquipmentSRManager extends ObjectManager<MedicalEquipmentServiceRequest> {
  public MedicalEquipmentSRManager() {
    super(DataBaseObjectType.MedicalEquipmentSR);
  }

  @Override
  public void readCSV(String inputFileName) throws IOException {}

  @Override
  public void writeToCSV(String outputFileName) throws IOException, SQLException {
    String filePath =
        System.getProperty("user.dir") + "/src/main/resources/edu/wpi/teame/csv/" + outputFileName;

    FileWriter outputFile = new FileWriter(filePath);
    CSVWriter writer =
        new CSVWriter(
            outputFile,
            ',',
            CSVWriter.NO_QUOTE_CHARACTER,
            CSVWriter.DEFAULT_ESCAPE_CHARACTER,
            CSVWriter.DEFAULT_LINE_END);

    List<MedicalEquipmentServiceRequest> listOfSerReq = this.getAll();

    List<String[]> data = new ArrayList<String[]>();
    data.add(
        new String[] {
          "id",
          "locationID",
          "status",
          "employeeID",
          "closeDate",
          "openDate",
          "equipmentID",
          "patient"
        });

    for (MedicalEquipmentServiceRequest serReq : listOfSerReq) {
      data.add(
          new String[] {
            Integer.toString(serReq.getId()),
            Integer.toString(serReq.getLocation().getId()),
            serReq.getStatus().toString(),
            Integer.toString(serReq.getEmployee().getId()),
            serReq.getCloseDate().toString(),
            serReq.getOpenDate().toString(),
            Integer.toString(serReq.getEquipment().getId()),
            serReq.getPatient()
          });
    }
    writer.writeAll(data);
    writer.close();
  }
}

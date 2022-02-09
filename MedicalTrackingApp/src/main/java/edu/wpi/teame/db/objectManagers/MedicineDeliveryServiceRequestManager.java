package edu.wpi.teame.db.objectManagers;

import com.opencsv.CSVWriter;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.serviceRequests.MedicineDeliveryServiceRequest;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicineDeliveryServiceRequestManager
    extends ObjectManager<MedicineDeliveryServiceRequest> {
  public MedicineDeliveryServiceRequestManager() {
    super(DataBaseObjectType.MedicineDeliverySR);
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

    List<MedicineDeliveryServiceRequest> listOfSerReq = this.getAll();

    List<String[]> data = new ArrayList<String[]>();
    data.add(
        new String[] {
          "id", "locationID", "status", "employeeID", "closeDate", "openDate", "deliveryDate"
        });

    for (MedicineDeliveryServiceRequest serReq : listOfSerReq) {
      data.add(
          new String[] {
            Integer.toString(serReq.getId()),
            Integer.toString(serReq.getLocation().getId()),
            serReq.getStatus().toString(),
            Integer.toString(serReq.getEmployee().getId()),
            serReq.getCloseDate().toString(),
            serReq.getOpenDate().toString(),
            serReq.getDeliveryDate().toString()
          });
    }
    writer.writeAll(data);
    writer.close();
  }
}

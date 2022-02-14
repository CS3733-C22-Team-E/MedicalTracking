package edu.wpi.teame.db.objectManagers;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.db.CSVLineData;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import edu.wpi.teame.model.serviceRequests.MedicineDeliveryServiceRequest;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public final class MedicineDeliveryServiceRequestManager
    extends ObjectManager<MedicineDeliveryServiceRequest> {
  public MedicineDeliveryServiceRequestManager() {
    super(DataBaseObjectType.MedicineDeliverySR);
  }

  @Override
  public void readCSV(String inputFileName)
      throws IOException, ParseException, SQLException, CsvValidationException {
    String filePath =
        System.getProperty("user.dir") + "/src/main/resources/edu/wpi/teame/csv/" + inputFileName;
    CSVReader csvReader = new CSVReader(new FileReader(filePath));
    CSVLineData lineData = new CSVLineData(csvReader);

    String[] record;
    while ((record = csvReader.readNext()) != null) {
      lineData.setParsedData(record);

      ServiceRequestStatus status =
          ServiceRequestStatus.values()[lineData.getColumnInt("requestStatus")];
      int employeeID = lineData.getColumnInt("employeeID");
      int locationID = lineData.getColumnInt("locationID");
      Date closeDate = lineData.getColumnDate("closeDate");
      Date openDate = lineData.getColumnDate("openDate");
      int id = lineData.getColumnInt("id");
      Date deliveryDate = lineData.getColumnDate("deliveryDate");

      // select assignee where id = employeeID
      EmployeeManager employeeManager = new EmployeeManager();
      Employee newEmployee = employeeManager.get(employeeID);
      // select location where id = locationID
      LocationManager locationManager = new LocationManager();
      Location newLocation = locationManager.get(locationID);

      //      MedicineDeliveryServiceRequest newMedDeliverySR =
      //          new MedicineDeliveryServiceRequest(
      //              status, newEmployee, newLocation, closeDate, openDate, id, deliveryDate);
      //      DBManager.getInstance().getMedicineDeliverySRManager().insert(newMedDeliverySR);
    }
  }

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
            Integer.toString(serReq.getAssignee().getId()),
            serReq.getCloseDate().toString(),
            serReq.getOpenDate().toString()
          });
    }
    writer.writeAll(data);
    writer.close();
  }
}

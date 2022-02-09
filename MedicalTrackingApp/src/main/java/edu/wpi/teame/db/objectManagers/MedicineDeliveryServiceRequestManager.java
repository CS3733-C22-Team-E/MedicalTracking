package edu.wpi.teame.db.objectManagers;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.db.CSVLineData;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Equipment;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import edu.wpi.teame.model.serviceRequests.MedicalEquipmentServiceRequest;
import edu.wpi.teame.model.serviceRequests.MedicineDeliveryServiceRequest;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;

public class MedicineDeliveryServiceRequestManager
    extends ObjectManager<MedicineDeliveryServiceRequest> {
  public MedicineDeliveryServiceRequestManager() {
    super(DataBaseObjectType.MedicineDeliverySR);
  }

  @Override
  public void readCSV(String inputFileName) throws IOException, ParseException, SQLException, CsvValidationException {
    String filePath =
            System.getProperty("user.dir") + "/src/main/resources/edu/wpi/teame/" + inputFileName;
    CSVReader csvReader = new CSVReader(new FileReader(filePath));
    CSVLineData lineData = new CSVLineData(csvReader);

    String[] record;
    while ((record = csvReader.readNext()) != null) {
      lineData.setParsedData(record);

      ServiceRequestStatus status = ServiceRequestStatus.values()[lineData.getColumnInt("requestStatus")];
      int employeeID = lineData.getColumnInt("employeeID");
      int locationID = lineData.getColumnInt("locationID");
      Date closeDate = lineData.getColumnDate("closeDate");
      Date openDate = lineData.getColumnDate("openDate");
      int id = lineData.getColumnInt("id");
      Date deliveryDate = lineData.getColumnDate("deliveryDate");

      //select employee where id = employeeID
      EmployeeManager employeeManager = new EmployeeManager();
      Employee newEmployee = employeeManager.get(employeeID);
      //select location where id = locationID
      LocationManager locationManager = new LocationManager();
      Location newLocation = locationManager.get(locationID);

      MedicineDeliveryServiceRequest newMedDeliverySR = new MedicineDeliveryServiceRequest(status, newEmployee, newLocation, closeDate, openDate, id, deliveryDate);
      DBManager.getInstance().getMedicineDeliverySRManager().insert(newMedDeliverySR);
    }
  }

  @Override
  public void writeToCSV(String outputFileName) throws IOException {}
}

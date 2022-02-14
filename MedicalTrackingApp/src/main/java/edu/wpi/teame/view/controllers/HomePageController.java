package edu.wpi.teame.view.controllers;

// import com.opencsv.exceptions.CsvValidationException;

import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.App;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.objectManagers.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

public class HomePageController {
  boolean hasLoaded = false;

  @FXML
  private void logout() throws IOException {
    Scene scene = new Scene(FXMLLoader.load(App.class.getResource("view/LoginPage.fxml")));
    App.getAppPrimaryStage().setScene(scene);
    App.getAppPrimaryStage().setFullScreen(true);
  }

  @FXML
  private void onLoadCSVButtonClick()
      throws IOException, CsvValidationException, SQLException, ParseException {
    if (hasLoaded) {
      return;
    }

    DBManager.getInstance().getLocationManager().readCSV("csv/TowerLocationsE.csv");
    DBManager.getInstance().getEquipmentManager().readCSV("csv/EquipmentE.csv");
    DBManager.getInstance().getEmployeeManager().readCSV("csv/EmployeesE.csv");
    DBManager.getInstance()
        .getMedicalEquipmentSRManager()
        .readCSV("MedicalEquipmentDeliverServiceRequestSave.csv");
    DBManager.getInstance()
        .getMedicineDeliverySRManager()
        .readCSV("MedicineDeliveryServiceRequestSave.csv");
    DBManager.getInstance().getSanitationSRManager().readCSV("SanitationServiceRequest.csv");
    DBManager.getInstance().getSecuritySRManager().readCSV("SecurityServiceRequest.csv");
    hasLoaded = true;
  }

  @FXML
  public void writeToCSVFiles(MouseEvent mouseEvent) throws SQLException, IOException {
    LocationManager locationManager = new LocationManager();
    EquipmentManager equipmentManager = new EquipmentManager();
    EmployeeManager employeeManager = new EmployeeManager();
    MedicalEquipmentSRManager medEqpManager = new MedicalEquipmentSRManager();
    MedicineDeliveryServiceRequestManager medReqManager =
        new MedicineDeliveryServiceRequestManager();
    SanitationServiceRequestManager sanReqManager = new SanitationServiceRequestManager();
    SecurityServiceRequestManager secReqManager = new SecurityServiceRequestManager();

    locationManager.writeToCSV("TowerLocationsESave.csv");
    equipmentManager.writeToCSV("EquipmentESave.csv");
    employeeManager.writeToCSV("EmployeesESave.csv");
    medEqpManager.writeToCSV("MedicalEquipmentDeliverServiceRequestSave.csv");
    medReqManager.writeToCSV("MedicineDeliveryServiceRequestSave.csv");
    sanReqManager.writeToCSV("SanitationServiceRequest.csv");
    secReqManager.writeToCSV("SecurityServiceRequest.csv");
  }
}

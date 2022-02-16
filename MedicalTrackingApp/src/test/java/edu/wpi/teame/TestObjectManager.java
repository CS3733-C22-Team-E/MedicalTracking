package edu.wpi.teame;

import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.objectManagers.*;
import edu.wpi.teame.db.objectManagers.serviceRequests.*;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Equipment;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.Patient;
import edu.wpi.teame.model.enums.*;
import edu.wpi.teame.model.serviceRequests.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class TestObjectManager {
  public ObjectManager objectManager;

  private static LocationManager locationManager;
  private static Location loc1;
  private static Location loc2;
  private static Location loc3;
  private static Location loc4;

  private static EquipmentManager equipmentManager;
  private static Equipment equipment1;
  private static Equipment equipment2;
  private static Equipment equipment3;

  private static EmployeeManager employeeManager;
  private static Employee emp1;
  private static Employee emp2;
  private static Employee emp3;

  private static Date date1;
  private static Date date2;
  private static Date date3;

  private static PatientManager patientManager;
  private static Patient patient1;
  private static Patient patient2;
  private static Patient patient3;

  private static StandardSRManager secReqManager;
  private static ServiceRequest secReq1;
  private static ServiceRequest secReq2;
  private static ServiceRequest secReq3;

  private static StandardSRManager sanReqManager;
  private static ServiceRequest sanReq1;
  private static ServiceRequest sanReq2;
  private static ServiceRequest sanReq3;

  private static MedicineDeliverySRManager medReqManager;
  private static MedicineDeliveryServiceRequest medReq1;
  private static MedicineDeliveryServiceRequest medReq2;
  private static MedicineDeliveryServiceRequest medReq3;

  private static MedicalEquipmentSRManager medicalEquipmentSRManager;
  private static MedicalEquipmentServiceRequest sr1;
  private static MedicalEquipmentServiceRequest sr2;
  private static MedicalEquipmentServiceRequest sr3;

  private static FoodDeliverySRManager foodDeliverySRManager;
  private static FoodDeliveryServiceRequest food1;
  private static FoodDeliveryServiceRequest food2;
  private static FoodDeliveryServiceRequest food3;

  private static GiftAndFloralSRManager giftAndFloralSRManager;
  private static GiftAndFloralServiceRequest gift1;
  private static GiftAndFloralServiceRequest gift2;
  private static GiftAndFloralServiceRequest gift3;

  private static LanguageInterpreterSRManager languageInterpreterSRManager;
  private static LanguageInterpreterServiceRequest language1;
  private static LanguageInterpreterServiceRequest language2;
  private static LanguageInterpreterServiceRequest language3;

  private static PatientTransportationSRManager transportationSRManager;
  private static PatientTransportationServiceRequest transport1;
  private static PatientTransportationServiceRequest transport2;
  private static PatientTransportationServiceRequest transport3;

  private static ReligiousSRManager religiousSRManager;
  private static ReligiousServiceRequest reg1;
  private static ReligiousServiceRequest reg2;
  private static ReligiousServiceRequest reg3;

  public TestObjectManager(ObjectManager manager) {
    objectManager = manager;
  }

  @BeforeAll
  public static void setUp() {
    try {
      DBManager.getInstance();

      loc1 =
          new Location(
              1,
              "Center for International Medicine",
              1617,
              825,
              FloorType.FirstFloor,
              BuildingType.Tower,
              LocationType.DEPT,
              "ds");
      loc2 =
          new Location(
              2,
              "Bretholtz Center for Patients and Families",
              1610,
              1120,
              FloorType.FirstFloor,
              BuildingType.Tower,
              LocationType.DEPT,
              "");
      loc3 =
          new Location(
              3,
              "Multifaith Chapel",
              1721,
              931,
              FloorType.FirstFloor,
              BuildingType.Tower,
              LocationType.DEPT,
              "as");

      loc4 =
          new Location(
              4,
              "M Chapel",
              1721,
              931,
              FloorType.FirstFloor,
              BuildingType.Tower,
              LocationType.DEPT,
              "as");

      locationManager = new LocationManager();

      equipment1 = new Equipment(1, loc1, EquipmentType.PBED, "Hospital Bed 1", true, false);
      equipment2 = new Equipment(2, loc2, EquipmentType.PBED, "Hospital Bed 13", false, true);
      equipment3 = new Equipment(3, loc3, EquipmentType.PUMP, "Infusion Pump 1", true, true);

      equipmentManager = new EquipmentManager();

      emp1 = new Employee(1, DepartmentType.FAMILYMEDICINE, "Shannon L", EmployeeType.Admin);
      emp2 =
          new Employee(2, DepartmentType.CLINICALSERVICES, "Madelyn Pearson", EmployeeType.Staff);
      emp3 =
          new Employee(
              3, DepartmentType.PLASTICSURGERY, "John Ronald Reagan Tolkien", EmployeeType.Admin);

      employeeManager = new EmployeeManager();

      date1 = Date.valueOf("2022-01-31");
      date2 = Date.valueOf("2022-01-10");
      date3 = Date.valueOf("2022-01-20");

      patient1 = new Patient(loc1, date1, "Jose Morales", 1);
      patient2 = new Patient(loc2, date2, "Josie Morales", 2);
      patient3 = new Patient(loc3, date3, "Joseph Morales", 3);

      patientManager = new PatientManager();

      secReq1 =
          new ServiceRequest(
              DataBaseObjectType.SecuritySR,
              ServiceRequestPriority.High,
              ServiceRequestStatus.OPEN,
              "I need swecurity",
              emp1,
              loc1,
              Date.valueOf("2015-03-31"),
              null,
              date1,
              "titel",
              1);
      secReq2 =
          new ServiceRequest(
              DataBaseObjectType.SecuritySR,
              ServiceRequestPriority.Low,
              ServiceRequestStatus.PENDING,
              "I need swecurity",
              emp2,
              loc3,
              Date.valueOf("2015-04-31"),
              null,
              date2,
              "titel",
              2);
      secReq3 =
          new ServiceRequest(
              DataBaseObjectType.SecuritySR,
              ServiceRequestPriority.Normal,
              ServiceRequestStatus.CLOSED,
              "I need swecurity",
              emp3,
              loc3,
              Date.valueOf("2017-03-31"),
              null,
              date3,
              "titel",
              3);

      secReqManager = new StandardSRManager(DataBaseObjectType.SecuritySR);

      sanReq1 =
          new ServiceRequest(
              DataBaseObjectType.SanitationSR,
              ServiceRequestPriority.High,
              ServiceRequestStatus.OPEN,
              "I need sanitatrion",
              emp1,
              loc1,
              Date.valueOf("2015-03-31"),
              null,
              date1,
              "titel",
              1);
      sanReq2 =
          new ServiceRequest(
              DataBaseObjectType.SanitationSR,
              ServiceRequestPriority.Low,
              ServiceRequestStatus.CLOSED,
              "I need sanitatrion",
              emp2,
              loc2,
              Date.valueOf("2015-04-31"),
              null,
              date2,
              "titel",
              2);
      sanReq3 =
          new ServiceRequest(
              DataBaseObjectType.SanitationSR,
              ServiceRequestPriority.Normal,
              ServiceRequestStatus.PENDING,
              "I need sanitatrion",
              emp3,
              loc3,
              Date.valueOf("2015-05-31"),
              null,
              date3,
              "titel",
              3);

      sanReqManager = new StandardSRManager(DataBaseObjectType.SanitationSR);

      medReq1 =
          new MedicineDeliveryServiceRequest(
              ServiceRequestPriority.High,
              ServiceRequestStatus.OPEN,
              "I need sanitatrion",
              emp1,
              loc4,
              Date.valueOf("2015-03-31"),
              null,
              date1,
              "titel",
              1,
              "od",
              "od",
              patient1);
      MedicineDeliveryServiceRequest medReq2 =
          new MedicineDeliveryServiceRequest(
              ServiceRequestPriority.Low,
              ServiceRequestStatus.CLOSED,
              "I need sanitatrion",
              emp2,
              loc2,
              Date.valueOf("2015-04-31"),
              null,
              date2,
              "titel",
              2,
              "od",
              "od",
              patient2);
      MedicineDeliveryServiceRequest medReq3 =
          new MedicineDeliveryServiceRequest(
              ServiceRequestPriority.Normal,
              ServiceRequestStatus.PENDING,
              "I need sanitatrion",
              emp3,
              loc3,
              Date.valueOf("2015-03-31"),
              null,
              date3,
              "titel",
              3,
              "od",
              "od",
              patient3);

      medReqManager = new MedicineDeliverySRManager();

      sr1 =
          new MedicalEquipmentServiceRequest(
              ServiceRequestPriority.High,
              ServiceRequestStatus.OPEN,
              "I need sanitatrion",
              emp1,
              loc1,
              Date.valueOf("2015-03-31"),
              null,
              date1,
              "titel",
              1,
              equipment1);

      sr2 =
          new MedicalEquipmentServiceRequest(
              ServiceRequestPriority.Low,
              ServiceRequestStatus.CLOSED,
              "I need sanitatrion",
              emp2,
              loc2,
              Date.valueOf("2015-03-31"),
              null,
              date2,
              "titel",
              2,
              equipment2);

      sr3 =
          new MedicalEquipmentServiceRequest(
              ServiceRequestPriority.Normal,
              ServiceRequestStatus.PENDING,
              "I need sanitatrion",
              emp3,
              loc3,
              Date.valueOf("2015-03-31"),
              null,
              date3,
              "titel",
              3,
              equipment3);

      food1 =
          new FoodDeliveryServiceRequest(
              ServiceRequestPriority.Normal,
              ServiceRequestStatus.PENDING,
              "I need sanitatrion",
              emp1,
              loc1,
              Date.valueOf("2015-03-31"),
              null,
              date1,
              "titel",
              1,
              patient1,
              "potatoes");
      food2 =
          new FoodDeliveryServiceRequest(
              ServiceRequestPriority.Low,
              ServiceRequestStatus.OPEN,
              "I need sanitatrion",
              emp2,
              loc2,
              Date.valueOf("2015-03-31"),
              null,
              date2,
              "titel",
              2,
              patient2,
              "potatoes");
      food3 =
          new FoodDeliveryServiceRequest(
              ServiceRequestPriority.Critical,
              ServiceRequestStatus.OPEN,
              "I need sanitatrion",
              emp2,
              loc2,
              Date.valueOf("2015-03-31"),
              null,
              date3,
              "titel",
              3,
              patient3,
              "potatoes");

      gift1 =
          new GiftAndFloralServiceRequest(
              ServiceRequestPriority.Critical,
              ServiceRequestStatus.OPEN,
              "I need sanitatrion",
              emp2,
              loc2,
              Date.valueOf("2015-03-31"),
              null,
              date1,
              "titel",
              1,
              patient1);

      gift2 =
          new GiftAndFloralServiceRequest(
              ServiceRequestPriority.Low,
              ServiceRequestStatus.CLOSED,
              "I need sanitatrion",
              emp2,
              loc2,
              Date.valueOf("2015-03-31"),
              null,
              date2,
              "titel",
              2,
              patient1);

      gift3 =
          new GiftAndFloralServiceRequest(
              ServiceRequestPriority.Normal,
              ServiceRequestStatus.PENDING,
              "I need sanitatrion",
              emp2,
              loc2,
              Date.valueOf("2015-03-31"),
              null,
              date3,
              "titel",
              3,
              patient1);

      language1 =
          new LanguageInterpreterServiceRequest(
              ServiceRequestPriority.Normal,
              ServiceRequestStatus.PENDING,
              "I need sanitatrion",
              emp2,
              loc2,
              Date.valueOf("2015-03-31"),
              null,
              date1,
              "titel",
              1,
              LanguageType.English,
              patient1);

      language2 =
          new LanguageInterpreterServiceRequest(
              ServiceRequestPriority.Critical,
              ServiceRequestStatus.OPEN,
              "I need sanitatrion",
              emp2,
              loc2,
              Date.valueOf("2015-03-31"),
              null,
              date2,
              "titel",
              2,
              LanguageType.Samoan,
              patient2);

      language3 =
          new LanguageInterpreterServiceRequest(
              ServiceRequestPriority.Low,
              ServiceRequestStatus.CLOSED,
              "I need sanitatrion",
              emp2,
              loc2,
              Date.valueOf("2015-03-31"),
              null,
              date3,
              "titel",
              3,
              LanguageType.Japanese,
              patient3);

      transport1 =
          new PatientTransportationServiceRequest(
              false,
              ServiceRequestPriority.Low,
              ServiceRequestStatus.CLOSED,
              "I need sanitatrion",
              emp2,
              loc2,
              Date.valueOf("2015-03-31"),
              null,
              date1,
              "titel",
              1,
              loc2,
              equipment1,
              patient1);

      transport2 =
          new PatientTransportationServiceRequest(
              true,
              ServiceRequestPriority.Critical,
              ServiceRequestStatus.OPEN,
              "I need sanitatrion",
              emp2,
              loc2,
              Date.valueOf("2015-03-31"),
              null,
              date2,
              "titel",
              2,
              loc3,
              equipment2,
              patient2);

      transport3 =
          new PatientTransportationServiceRequest(
              false,
              ServiceRequestPriority.Low,
              ServiceRequestStatus.CLOSED,
              "I need sanitatrion",
              emp2,
              loc2,
              Date.valueOf("2015-03-31"),
              null,
              date3,
              "titel",
              3,
              loc1,
              equipment3,
              patient3);

      reg1 =
          new ReligiousServiceRequest(
              ServiceRequestPriority.Low,
              ServiceRequestStatus.CLOSED,
              "I need sanitatrion",
              emp2,
              loc2,
              Date.valueOf("2015-03-31"),
              null,
              date1,
              "titel",
              1,
              patient1,
              "me");

      reg2 =
          new ReligiousServiceRequest(
              ServiceRequestPriority.High,
              ServiceRequestStatus.OPEN,
              "I need sanitatrion",
              emp2,
              loc2,
              Date.valueOf("2015-03-31"),
              null,
              date2,
              "titel",
              2,
              patient2,
              "you");

      reg3 =
          new ReligiousServiceRequest(
              ServiceRequestPriority.Normal,
              ServiceRequestStatus.CANCELLED,
              "I need sanitatrion",
              emp2,
              loc2,
              Date.valueOf("2015-03-31"),
              null,
              date3,
              "titel",
              3,
              patient3,
              "none");

      foodDeliverySRManager = DBManager.getInstance().getFoodDeliverySRManager();

      giftAndFloralSRManager = DBManager.getInstance().getGiftAndFloralSRManager();

      transportationSRManager = DBManager.getInstance().getInternalPatientSRManager();

      transportationSRManager = DBManager.getInstance().getExternalPatientSRManager();

      religiousSRManager = DBManager.getInstance().getReligiousSRManager();

      languageInterpreterSRManager =
          new LanguageInterpreterSRManager(DataBaseObjectType.LanguageInterpreterSR);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testInsert() throws SQLException {

    locationManager.insert(loc1);
    Assert.assertEquals(true, locationManager.get(1).equals(loc1));

    equipmentManager.insert(equipment1);
    Assert.assertEquals(true, equipmentManager.get(1).equals(equipment1));

    employeeManager.insert(emp1);
    Assert.assertEquals(true, employeeManager.get(1).equals(emp1));

    patientManager.insert(patient1);
    Assert.assertEquals(true, patientManager.get(1).equals(patient1));

    secReqManager.insert(secReq1);
    Assert.assertEquals(true, secReqManager.get(1).equals(secReq1));

    sanReqManager.insert(sanReq1);
    Assert.assertEquals(true, sanReqManager.get(1).equals(sanReq1));

    medReqManager.insert(medReq1);
    Assert.assertEquals(true, medReqManager.get(1).equals(medReq1));

    medicalEquipmentSRManager.insert(sr1);
    Assert.assertEquals(true, medicalEquipmentSRManager.get(1).equals(sr1));

    foodDeliverySRManager.insert(food1);
    Assert.assertEquals(true, foodDeliverySRManager.get(1).equals(food1));

    giftAndFloralSRManager.insert(gift1);
    Assert.assertEquals(true, giftAndFloralSRManager.get(1).equals(gift1));

    languageInterpreterSRManager.insert(language1);
    Assert.assertEquals(true, languageInterpreterSRManager.get(1).equals(language1));

    patientManager.insert(patient1);
    Assert.assertEquals(true, patientManager.get(1).equals(patient1));

    transportationSRManager.insert(transport1);
    Assert.assertEquals(true, transportationSRManager.get(1).equals(transport1));

    religiousSRManager.insert(reg1);
    Assert.assertEquals(true, religiousSRManager.get(1).equals(reg1));
  }

  //  @Test
  //  public static void testGetAll() {}
  //
  //  @Test
  //  public static void testRemove() {}

  @AfterAll
  public static void cleanDBTables() {}

  @Parameterized.Parameters
  public static List<ObjectManager> objectManagerInstances() throws SQLException {
    return Arrays.asList(
        new ObjectManager[] {new LocationManager(), new EmployeeManager(), new PatientManager()});
  }
}

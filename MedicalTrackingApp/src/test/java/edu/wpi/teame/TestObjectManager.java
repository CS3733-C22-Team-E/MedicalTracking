package edu.wpi.teame;

import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.objectManagers.*;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import edu.wpi.teame.db.objectManagers.serviceRequests.*;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Equipment;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.Patient;
import edu.wpi.teame.model.enums.*;
import edu.wpi.teame.model.serviceRequests.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class TestObjectManager {
  public ObjectManager objectManager;

  private LocationManager locationManager;
  private Location loc1;
  private Location loc2;
  private Location loc3;
  private Location loc4;

  private EquipmentManager equipmentManager;
  private Equipment equipment1;
  private Equipment equipment2;
  private Equipment equipment3;

  private EmployeeManager employeeManager;
  private Employee emp1;
  private Employee emp2;
  private Employee emp3;

  private Date date1;
  private Date date2;
  private Date date3;

  private PatientManager patientManager;
  private Patient patient1;
  private Patient patient2;
  private Patient patient3;

  private StandardSRManager secReqManager;
  private ServiceRequest secReq1;
  private ServiceRequest secReq2;
  private ServiceRequest secReq3;

  private StandardSRManager sanReqManager;
  private ServiceRequest sanReq1;
  private ServiceRequest sanReq2;
  private ServiceRequest sanReq3;

  private MedicineDeliverySRManager medReqManager;
  private MedicineDeliveryServiceRequest medReq1;
  private MedicineDeliveryServiceRequest medReq2;
  private MedicineDeliveryServiceRequest medReq3;

  private MedicalEquipmentSRManager medicalEquipmentSRManager;
  private MedicalEquipmentServiceRequest sr1;
  private MedicalEquipmentServiceRequest sr2;
  private MedicalEquipmentServiceRequest sr3;

  private FoodDeliverySRManager foodDeliverySRManager;
  private FoodDeliveryServiceRequest food1;
  private FoodDeliveryServiceRequest food2;
  private FoodDeliveryServiceRequest food3;

  private GiftAndFloralSRManager giftAndFloralSRManager;
  private GiftAndFloralServiceRequest gift1;
  private GiftAndFloralServiceRequest gift2;
  private GiftAndFloralServiceRequest gift3;

  private LanguageInterpreterSRManager languageInterpreterSRManager;
  private LanguageInterpreterServiceRequest language1;
  private LanguageInterpreterServiceRequest language2;
  private LanguageInterpreterServiceRequest language3;

  private PatientTransportationSRManager transportationSRManager;
  private PatientTransportationServiceRequest transport1;
  private PatientTransportationServiceRequest transport2;
  private PatientTransportationServiceRequest transport3;

  private ReligiousSRManager religiousSRManager;
  private ReligiousServiceRequest reg1;
  private ReligiousServiceRequest reg2;
  private ReligiousServiceRequest reg3;








  public TestObjectManager(ObjectManager manager) {
    objectManager = manager;
  }

  @BeforeAll
  public void setUp() throws SQLException {
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

    equipment1 =
            new Equipment(1, loc1, EquipmentType.PBED, "Hospital Bed 1", true, false);
    equipment2 =
            new Equipment(2, loc2, EquipmentType.PBED, "Hospital Bed 13", false, true);
    equipment3 =
            new Equipment(3, loc3, EquipmentType.PUMP, "Infusion Pump 1", true, true);

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



    transportationSRManager =
            DBManager.getInstance().getInternalPatientSRManager();

    transportationSRManager =
            DBManager.getInstance().getExternalPatientSRManager();

    religiousSRManager = DBManager.getInstance().getReligiousSRManager();

    languageInterpreterSRManager =
            new LanguageInterpreterSRManager(DataBaseObjectType.LanguageInterpreterSR);

  }

  @Test
  public void testGetAll() {}

  @Test
  public void testInsert() {}

  @Test
  public void testRemove() {}

  @AfterAll
  public void cleanDBTables() {}

  @Parameterized.Parameters
  public static List<ObjectManager> objectManagerInstances() throws SQLException {
    return Arrays.asList(
        new ObjectManager[] {new LocationManager(), new EmployeeManager(), new PatientManager()});
  }
}

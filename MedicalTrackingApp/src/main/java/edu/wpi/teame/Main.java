package edu.wpi.teame;

import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.objectManagers.*;
import edu.wpi.teame.db.objectManagers.serviceRequests.*;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Equipment;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.Patient;
import edu.wpi.teame.model.enums.*;
import edu.wpi.teame.model.serviceRequests.*;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class Main {

  public static void main(String[] args)
      throws IOException, SQLException, CsvValidationException, ParseException {
    DBManager.getInstance();

    Location loc1 =
        new Location(
            1,
            "Center for International Medicine",
            1617,
            825,
            FloorType.FirstFloor,
            BuildingType.Tower,
            LocationType.DEPT,
            "ds");
    Location loc2 =
        new Location(
            2,
            "Bretholtz Center for Patients and Families",
            1610,
            1120,
            FloorType.FirstFloor,
            BuildingType.Tower,
            LocationType.DEPT,
            "");
    Location loc3 =
        new Location(
            3,
            "Multifaith Chapel",
            1721,
            931,
            FloorType.FirstFloor,
            BuildingType.Tower,
            LocationType.DEPT,
            "as");

    Location loc4 =
        new Location(
            4,
            "M Chapel",
            1721,
            931,
            FloorType.FirstFloor,
            BuildingType.Tower,
            LocationType.DEPT,
            "as");

    LocationManager location = new LocationManager();
    location.insert(loc1);
    location.insert(loc2);
    location.insert(loc3);
    location.insert(loc4);

    LinkedList<Location> result = (LinkedList<Location>) location.getAll();
    for (Location loc : result) {
      System.out.println(loc);
    }

    location.update(
        new Location(
            1,
            "Center for something",
            1617,
            825,
            FloorType.FirstFloor,
            BuildingType.Tower,
            LocationType.DEPT,
            "some"));

    System.out.println(location.get(1));
    System.out.println();

    Equipment equipment1 =
        new Equipment(1, loc1, EquipmentType.PBED, "Hospital Bed 1", true, false);
    Equipment equipment2 =
        new Equipment(2, loc2, EquipmentType.PBED, "Hospital Bed 13", false, true);
    Equipment equipment3 =
        new Equipment(3, loc3, EquipmentType.PUMP, "Infusion Pump 1", true, true);

    EquipmentManager equipmentManager = new EquipmentManager();
    equipmentManager.insert(equipment1);
    equipmentManager.insert(equipment2);
    equipmentManager.insert(equipment3);

    List<Equipment> equipmentLinkedList = equipmentManager.getAll();
    for (Equipment equip : equipmentLinkedList) {
      System.out.println(equip);
    }

    equipmentManager.update(
        new Equipment(1, loc1, EquipmentType.PBED, "Hospital Bed for jose", true, true));
    System.out.println(equipmentManager.get(1));
    System.out.println();

    /** int id, DepartmentType dept, String name, EmployeeType type */
    Employee emp1 = new Employee(1, DepartmentType.FAMILYMEDICINE, "Shannon L", EmployeeType.Admin);
    Employee emp2 =
        new Employee(2, DepartmentType.CLINICALSERVICES, "Madelyn Pearson", EmployeeType.Staff);
    Employee emp3 =
        new Employee(
            3, DepartmentType.PLASTICSURGERY, "John Ronald Reagan Tolkien", EmployeeType.Admin);

    EmployeeManager employeeManager = new EmployeeManager();
    employeeManager.insert(emp1);
    employeeManager.insert(emp2);
    employeeManager.insert(emp3);

    List<Employee> employeeList = employeeManager.getAll();
    for (Employee emp : employeeList) {
      System.out.println(emp);
    }

    System.out.println("updating employee...");
    employeeManager.update(
        new Employee(1, DepartmentType.PLASTICSURGERY, "Jose L", EmployeeType.Admin));
    employeeList = employeeManager.getAll();
    for (Employee emp : employeeList) {
      System.out.println(emp);
    }

    System.out.println();

    long d = System.currentTimeMillis();
    Date date = new Date(d);
    DateFormat dateFormat = new SimpleDateFormat("MMddyyyy");

    Patient patient1 = new Patient(loc1, date, "Jose Morales", 1);
    Patient patient2 = new Patient(loc2, date, "Josie Morales", 2);
    Patient patient3 = new Patient(loc3, date, "Joseph Morales", 3);

    PatientManager pman = new PatientManager();
    pman.insert(patient1);
    pman.insert(patient2);
    pman.insert(patient3);

    System.out.println("updating patient...");
    pman.update(new Patient(loc1, date, "Jose Morales 2.0", 1));

    System.out.println();
    /**
     * DataBaseObjectType type, ServiceRequestPriority priority, ServiceRequestStatus status, String
     * additionalInfo, Employee assignee, Location location, Date requestDate, Date closeDate, Date
     * openDate, String title, int id
     */
    ServiceRequest secReq1 =
        new ServiceRequest(
            DataBaseObjectType.SecuritySR,
            ServiceRequestPriority.High,
            ServiceRequestStatus.OPEN,
            "I need swecurity",
            emp1,
            loc1,
            Date.valueOf("2015-03-31"),
            null,
            date,
            "titel",
            1);
    ServiceRequest secReq2 =
        new ServiceRequest(
            DataBaseObjectType.SecuritySR,
            ServiceRequestPriority.Low,
            ServiceRequestStatus.PENDING,
            "I need swecurity",
            emp2,
            loc3,
            Date.valueOf("2015-04-31"),
            null,
            date,
            "titel",
            2);
    ServiceRequest secReq3 =
        new ServiceRequest(
            DataBaseObjectType.SecuritySR,
            ServiceRequestPriority.Normal,
            ServiceRequestStatus.CLOSED,
            "I need swecurity",
            emp3,
            loc3,
            Date.valueOf("2017-03-31"),
            null,
            date,
            "titel",
            3);

    StandardSRManager secReqManager = new StandardSRManager(DataBaseObjectType.SecuritySR);
    secReqManager.insert(secReq1);
    secReqManager.insert(secReq2);
    secReqManager.insert(secReq3);

    System.out.println("updating standardSR...");
    secReqManager.update(
        new ServiceRequest(
            DataBaseObjectType.SecuritySR,
            ServiceRequestPriority.Normal,
            ServiceRequestStatus.CLOSED,
            "I need swecurity heheheheheheh",
            emp3,
            loc3,
            Date.valueOf("2017-03-31"),
            null,
            date,
            "titel",
            3));
    System.out.println();

    List<ServiceRequest> securitylist = secReqManager.getAll();
    for (ServiceRequest sec : securitylist) {
      System.out.println(sec);
    }

    //     secReqManager.update(
    //             new ServiceRequest(DataBaseObjectType.SecuritySR, ServiceRequestPriority.High,
    // ServiceRequestStatus.OPEN, "I need swecurity", emp1, loc1,  Date.valueOf("2015-03-31"), null,
    // date, "titel",0));
    //     System.out.println(secReqManager.get(1));
    //     System.out.println();

    ServiceRequest sanReq1 =
        new ServiceRequest(
            DataBaseObjectType.SanitationSR,
            ServiceRequestPriority.High,
            ServiceRequestStatus.OPEN,
            "I need sanitatrion",
            emp1,
            loc1,
            Date.valueOf("2015-03-31"),
            null,
            date,
            "titel",
            1);
    ServiceRequest sanReq2 =
        new ServiceRequest(
            DataBaseObjectType.SanitationSR,
            ServiceRequestPriority.Low,
            ServiceRequestStatus.CLOSED,
            "I need sanitatrion",
            emp2,
            loc2,
            Date.valueOf("2015-04-31"),
            null,
            date,
            "titel",
            2);
    ServiceRequest sanReq3 =
        new ServiceRequest(
            DataBaseObjectType.SanitationSR,
            ServiceRequestPriority.Normal,
            ServiceRequestStatus.PENDING,
            "I need sanitatrion",
            emp3,
            loc3,
            Date.valueOf("2015-05-31"),
            null,
            date,
            "titel",
            3);

    StandardSRManager sanReqManager = new StandardSRManager(DataBaseObjectType.SanitationSR);
    sanReqManager.insert(sanReq1);
    sanReqManager.insert(sanReq2);
    sanReqManager.insert(sanReq3);

    System.out.println("updating sanitationSR...");
    sanReqManager.update(
        new ServiceRequest(
            DataBaseObjectType.SanitationSR,
            ServiceRequestPriority.Critical,
            ServiceRequestStatus.PENDING,
            "I need sanitatrion",
            emp3,
            loc3,
            Date.valueOf("2015-05-31"),
            date,
            date,
            "titel",
            3));

    List<ServiceRequest> sanReqlist = sanReqManager.getAll();
    for (ServiceRequest san : sanReqlist) {
      System.out.println(san);
    }
    System.out.println();

    //     sanReqManager.update(
    //         new SanitationServiceRequest(ServiceRequestStatus.CLOSED, emp1, loc1, date, date,
    // 1));
    //
    //     System.out.println();

    MedicineDeliveryServiceRequest medReq1 =
        new MedicineDeliveryServiceRequest(
            ServiceRequestPriority.High,
            ServiceRequestStatus.OPEN,
            "I need sanitatrion",
            emp1,
            loc4,
            Date.valueOf("2015-03-31"),
            null,
            date,
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
            date,
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
            date,
            "titel",
            3,
            "od",
            "od",
            patient3);

    MedicineDeliverySRManager medReqManager = new MedicineDeliverySRManager();
    medReqManager.insert(medReq1);
    medReqManager.insert(medReq2);
    medReqManager.insert(medReq3);

    System.out.println("updating medicineDeliverySR...");
    medReqManager.update(
        new MedicineDeliveryServiceRequest(
            ServiceRequestPriority.Normal,
            ServiceRequestStatus.PENDING,
            "I need sanitatrion",
            emp3,
            loc3,
            Date.valueOf("2015-03-31"),
            null,
            date,
            "titel",
            3,
            "djkfdshgfuiowelkfjbvhdjkfs",
            "od",
            patient3));

    List<MedicineDeliveryServiceRequest> medReqlist = medReqManager.getAll();
    for (MedicineDeliveryServiceRequest san : medReqlist) {
      System.out.println(san);
    }
    System.out.println();
    //     medReqManager.update(
    //         new MedicineDeliveryServiceRequest(
    //             ServiceRequestStatus.CLOSED, emp1, loc1, date, date, 1, date));

    //     System.out.println(medReqManager.get(1));
    //     System.out.println();

    /**
     * ServiceRequestPriority priority, ServiceRequestStatus status, String additionalInfo, Employee
     * assignee, Location location, Date requestDate, Date closeDate, Date openDate, String title,
     * int id, Equipment equipment
     */
    MedicalEquipmentSRManager medEqpManager = new MedicalEquipmentSRManager();
    MedicalEquipmentServiceRequest sr1 =
        new MedicalEquipmentServiceRequest(
            ServiceRequestPriority.High,
            ServiceRequestStatus.OPEN,
            "I need sanitatrion",
            emp1,
            loc1,
            Date.valueOf("2015-03-31"),
            null,
            date,
            "titel",
            1,
            equipment1);

    MedicalEquipmentServiceRequest sr2 =
        new MedicalEquipmentServiceRequest(
            ServiceRequestPriority.Low,
            ServiceRequestStatus.CLOSED,
            "I need sanitatrion",
            emp2,
            loc2,
            Date.valueOf("2015-03-31"),
            null,
            date,
            "titel",
            2,
            equipment2);

    MedicalEquipmentServiceRequest sr3 =
        new MedicalEquipmentServiceRequest(
            ServiceRequestPriority.Normal,
            ServiceRequestStatus.PENDING,
            "I need sanitatrion",
            emp3,
            loc3,
            Date.valueOf("2015-03-31"),
            null,
            date,
            "titel",
            3,
            equipment3);

    medEqpManager.insert(sr1);
    medEqpManager.insert(sr2);
    medEqpManager.insert(sr3);

    System.out.println("updating medicalEquipmentSR...");
    medEqpManager.update(
        new MedicalEquipmentServiceRequest(
            ServiceRequestPriority.Normal,
            ServiceRequestStatus.PENDING,
            "I need sanitatrion",
            emp2,
            loc1,
            Date.valueOf("2015-03-31"),
            null,
            date,
            "titel.......",
            3,
            equipment3));
    System.out.println();

    List<MedicalEquipmentServiceRequest> medEqpList = medEqpManager.getAll();
    for (MedicalEquipmentServiceRequest test : medEqpList) {
      System.out.println(test);
    }

    FoodDeliveryServiceRequest food1 =
        new FoodDeliveryServiceRequest(
            ServiceRequestPriority.Normal,
            ServiceRequestStatus.PENDING,
            "I need sanitatrion",
            emp1,
            loc1,
            Date.valueOf("2015-03-31"),
            null,
            date,
            "titel",
            1,
            patient1,
            "potatoes");
    FoodDeliveryServiceRequest food2 =
        new FoodDeliveryServiceRequest(
            ServiceRequestPriority.Low,
            ServiceRequestStatus.OPEN,
            "I need sanitatrion",
            emp2,
            loc2,
            Date.valueOf("2015-03-31"),
            null,
            date,
            "titel",
            2,
            patient2,
            "potatoes");
    FoodDeliveryServiceRequest food3 =
        new FoodDeliveryServiceRequest(
            ServiceRequestPriority.Critical,
            ServiceRequestStatus.OPEN,
            "I need sanitatrion",
            emp2,
            loc2,
            Date.valueOf("2015-03-31"),
            null,
            date,
            "titel",
            3,
            patient3,
            "potatoes");

    GiftAndFloralServiceRequest gift1 =
        new GiftAndFloralServiceRequest(
            ServiceRequestPriority.Critical,
            ServiceRequestStatus.OPEN,
            "I need sanitatrion",
            emp2,
            loc2,
            Date.valueOf("2015-03-31"),
            null,
            date,
            "titel",
            1,
            patient1);

    GiftAndFloralServiceRequest gift2 =
        new GiftAndFloralServiceRequest(
            ServiceRequestPriority.Low,
            ServiceRequestStatus.CLOSED,
            "I need sanitatrion",
            emp2,
            loc2,
            Date.valueOf("2015-03-31"),
            null,
            date,
            "titel",
            2,
            patient1);

    GiftAndFloralServiceRequest gift3 =
        new GiftAndFloralServiceRequest(
            ServiceRequestPriority.Normal,
            ServiceRequestStatus.PENDING,
            "I need sanitatrion",
            emp2,
            loc2,
            Date.valueOf("2015-03-31"),
            null,
            date,
            "titel",
            3,
            patient1);

    LanguageInterpreterServiceRequest language1 =
        new LanguageInterpreterServiceRequest(
            ServiceRequestPriority.Normal,
            ServiceRequestStatus.PENDING,
            "I need sanitatrion",
            emp2,
            loc2,
            Date.valueOf("2015-03-31"),
            null,
            date,
            "titel",
            1,
            LanguageType.English,
            patient1);

    LanguageInterpreterServiceRequest language2 =
        new LanguageInterpreterServiceRequest(
            ServiceRequestPriority.Critical,
            ServiceRequestStatus.OPEN,
            "I need sanitatrion",
            emp2,
            loc2,
            Date.valueOf("2015-03-31"),
            null,
            date,
            "titel",
            2,
            LanguageType.Samoan,
            patient2);

    LanguageInterpreterServiceRequest language3 =
        new LanguageInterpreterServiceRequest(
            ServiceRequestPriority.Low,
            ServiceRequestStatus.CLOSED,
            "I need sanitatrion",
            emp2,
            loc2,
            Date.valueOf("2015-03-31"),
            null,
            date,
            "titel",
            3,
            LanguageType.Japanese,
            patient3);

    PatientTransportationServiceRequest transport1 =
        new PatientTransportationServiceRequest(
            false,
            ServiceRequestPriority.Low,
            ServiceRequestStatus.CLOSED,
            "I need sanitatrion",
            emp2,
            loc2,
            Date.valueOf("2015-03-31"),
            null,
            date,
            "titel",
            1,
            loc2,
            equipment1,
            patient1);

    PatientTransportationServiceRequest transport2 =
        new PatientTransportationServiceRequest(
            true,
            ServiceRequestPriority.Critical,
            ServiceRequestStatus.OPEN,
            "I need sanitatrion",
            emp2,
            loc2,
            Date.valueOf("2015-03-31"),
            null,
            date,
            "titel",
            2,
            loc3,
            equipment2,
            patient2);

    PatientTransportationServiceRequest transport3 =
        new PatientTransportationServiceRequest(
            false,
            ServiceRequestPriority.Low,
            ServiceRequestStatus.CLOSED,
            "I need sanitatrion",
            emp2,
            loc2,
            Date.valueOf("2015-03-31"),
            null,
            date,
            "titel",
            3,
            loc1,
            equipment3,
            patient3);

    ReligiousServiceRequest reg1 =
        new ReligiousServiceRequest(
            ServiceRequestPriority.Low,
            ServiceRequestStatus.CLOSED,
            "I need sanitatrion",
            emp2,
            loc2,
            Date.valueOf("2015-03-31"),
            null,
            date,
            "titel",
            1,
            patient1,
            "me");

    ReligiousServiceRequest reg2 =
        new ReligiousServiceRequest(
            ServiceRequestPriority.High,
            ServiceRequestStatus.OPEN,
            "I need sanitatrion",
            emp2,
            loc2,
            Date.valueOf("2015-03-31"),
            null,
            date,
            "titel",
            2,
            patient2,
            "you");

    ReligiousServiceRequest reg3 =
        new ReligiousServiceRequest(
            ServiceRequestPriority.Normal,
            ServiceRequestStatus.CANCELLED,
            "I need sanitatrion",
            emp2,
            loc2,
            Date.valueOf("2015-03-31"),
            null,
            date,
            "titel",
            3,
            patient3,
            "none");

    FoodDeliverySRManager foodMan = DBManager.getInstance().getFoodDeliverySRManager();
    foodMan.insert(food1);
    foodMan.insert(food2);
    foodMan.insert(food3);

    foodMan.update(
        new FoodDeliveryServiceRequest(
            ServiceRequestPriority.Low,
            ServiceRequestStatus.CANCELLED,
            "I need sanitatrion",
            emp2,
            loc2,
            Date.valueOf("2015-03-31"),
            null,
            date,
            "titel",
            3,
            patient3,
            "pizza"));

    List<FoodDeliveryServiceRequest> foodReqlist = foodMan.getAll();
    for (FoodDeliveryServiceRequest san : foodReqlist) {
      System.out.println(san);
    }

    GiftAndFloralSRManager giftMan = DBManager.getInstance().getGiftAndFloralSRManager();
    giftMan.insert(gift1);
    giftMan.insert(gift2);
    giftMan.insert(gift3);

    List<GiftAndFloralServiceRequest> giftReqlist = giftMan.getAll();
    for (GiftAndFloralServiceRequest san : giftReqlist) {
      System.out.println(san);
    }

    giftMan.update(
        new GiftAndFloralServiceRequest(
            ServiceRequestPriority.Critical,
            ServiceRequestStatus.CLOSED,
            "I need sanitatrion",
            emp2,
            loc2,
            Date.valueOf("2015-03-31"),
            null,
            date,
            "New",
            3,
            patient1));

    PatientTransportationSRManager transportMan =
        DBManager.getInstance().getInternalPatientSRManager();
    transportMan.insert(transport2);

    List<PatientTransportationServiceRequest> gtransportReqlist = transportMan.getAll();
    for (PatientTransportationServiceRequest san : gtransportReqlist) {
      System.out.println(san);
    }

    PatientTransportationSRManager extTransportMan =
        DBManager.getInstance().getExternalPatientSRManager();
    extTransportMan.insert(transport1);
    extTransportMan.insert(transport3);

    System.out.println("updating PatientTransportationSR...");
    transportMan.update(
        new PatientTransportationServiceRequest(
            false,
            ServiceRequestPriority.Critical,
            ServiceRequestStatus.CLOSED,
            "I need sanitatrion",
            emp2,
            loc3,
            Date.valueOf("2015-03-31"),
            null,
            date,
            "titel",
            3,
            loc1,
            equipment3,
            patient3));
    System.out.println();

    ReligiousSRManager regman = DBManager.getInstance().getReligiousSR();
    regman.insert(reg1);
    regman.insert(reg2);
    regman.insert(reg3);

    List<ReligiousServiceRequest> regReqlist = regman.getAll();
    System.out.println(regReqlist.size());
    for (ReligiousServiceRequest san : regReqlist) {
      System.out.println(san);
    }

    regman.update(
        new ReligiousServiceRequest(
            ServiceRequestPriority.Low,
            ServiceRequestStatus.CANCELLED,
            "I need sanitatrion",
            emp2,
            loc2,
            Date.valueOf("2015-03-31"),
            date,
            date,
            "titel",
            3,
            patient3,
            "none"));

    System.out.println("testing languageSR............");
    LanguageInterpreterSRManager langMan =
        new LanguageInterpreterSRManager(DataBaseObjectType.LanguageInterpreterSR);
    langMan.insert(language1);
    langMan.insert(language2);
    langMan.insert(language3);

    List<LanguageInterpreterServiceRequest> langReqlist = langMan.getAll();
    System.out.println(langReqlist.size());
    for (LanguageInterpreterServiceRequest lang : langReqlist) {
      System.out.println(lang);
    }

    langMan.update(
        new LanguageInterpreterServiceRequest(
            ServiceRequestPriority.Critical,
            ServiceRequestStatus.CLOSED,
            "updating....ggg",
            emp2,
            loc2,
            Date.valueOf("2015-03-31"),
            null,
            date,
            "titel",
            3,
            LanguageType.Japanese,
            patient3));
    System.out.println();

    DBManager.getInstance().writeDBToCSV();

    foodMan.update(
        new FoodDeliveryServiceRequest(
            ServiceRequestPriority.Low,
            ServiceRequestStatus.CANCELLED,
            "update to readCSV into DB",
            emp2,
            loc2,
            Date.valueOf("2015-03-31"),
            null,
            date,
            "titel",
            3,
            patient3,
            "pizza"));

    DBManager.getInstance().loadDBFromCSV();

    // App.launch(App.class, args);
  }
}

package edu.wpi.teame;

import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.objectManagers.*;
import edu.wpi.teame.db.objectManagers.serviceRequests.MedicalEquipmentSRManager;
import edu.wpi.teame.db.objectManagers.serviceRequests.MedicineDeliverySRManager;
import edu.wpi.teame.db.objectManagers.serviceRequests.StandardSRManager;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Equipment;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.*;
import edu.wpi.teame.model.serviceRequests.MedicalEquipmentServiceRequest;
import edu.wpi.teame.model.serviceRequests.MedicineDeliveryServiceRequest;
import edu.wpi.teame.model.serviceRequests.ServiceRequest;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class Main {

  public static void main(String[] args) throws IOException, SQLException {
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

    LocationManager location = new LocationManager();
    location.insert(loc1);
    location.insert(loc2);
    location.insert(loc3);

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

    //     employeeManager.update(new Employee(1, DepartmentType.PLASTICSURGERY, "Jose L", false));
    //     employeeList = employeeManager.getAll();
    //     for (Employee emp : employeeList) {
    //       System.out.println(emp);
    //     }

    System.out.println();

    long d = System.currentTimeMillis();
    Date date = new Date(d);
    DateFormat dateFormat = new SimpleDateFormat("MMddyyyy");

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

    List<ServiceRequest> sanReqlist = sanReqManager.getAll();
    for (ServiceRequest san : sanReqlist) {
      System.out.println(san);
    }

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
            loc1,
            Date.valueOf("2015-03-31"),
            null,
            date,
            "titel",
            1,
            "od",
            "od",
            null);
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
            1,
            "od",
            "od",
            null);
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
            1,
            "od",
            "od",
            null);

    MedicineDeliverySRManager medReqManager = new MedicineDeliverySRManager();
    medReqManager.insert(medReq1);
    medReqManager.insert(medReq2);
    medReqManager.insert(medReq3);

    List<MedicineDeliveryServiceRequest> medReqlist = medReqManager.getAll();
    for (MedicineDeliveryServiceRequest san : medReqlist) {
      System.out.println(san);
    }

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

    List<MedicalEquipmentServiceRequest> medEqpList = medEqpManager.getAll();
    for (MedicalEquipmentServiceRequest test : medEqpList) {
      System.out.println(test);
    }

    //    System.out.println();
    //    System.out.println();
    //    System.out.println(emp1);
    //    employeeManager.update(new Employee(1, DepartmentType.PLASTICSURGERY, "Jose Morales",
    // false))
    //    System.out.println(employeeManager.get(1));

    // App.launch(App.class, args);
  }
}

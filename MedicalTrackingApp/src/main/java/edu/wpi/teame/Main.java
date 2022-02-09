package edu.wpi.teame;

import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.objectManagers.*;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Equipment;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.*;
import edu.wpi.teame.model.serviceRequests.MedicalEquipmentServiceRequest;
import edu.wpi.teame.model.serviceRequests.MedicineDeliveryServiceRequest;
import edu.wpi.teame.model.serviceRequests.SanitationServiceRequest;
import edu.wpi.teame.model.serviceRequests.SecurityServiceRequest;
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

    /**
     * eSBURNHAM001,FAMILYMEDICINE,Shannon L. Burnham,true eMPEARSON002,CLINICALSERVICES,Madelyn
     * Pearson,false eJTOLKIEN003,PLASTICSURGERY,John Ronald Reagan Tolkien,true
     * eWBI004,NEUROSURGERY,Wenya Linda Bi,true eSDOUGAL005,MICU,Sandra Dougal,false
     * ABC123,CLINICALSERVICES,Brinda Venkataraman,true
     */
    Employee emp1 = new Employee(1, DepartmentType.FAMILYMEDICINE, "Shannon L", true);
    Employee emp2 = new Employee(2, DepartmentType.CLINICALSERVICES, "Madelyn Pearson", false);
    Employee emp3 =
        new Employee(3, DepartmentType.PLASTICSURGERY, "John Ronald Reagan Tolkien", true);

    EmployeeManager employeeManager = new EmployeeManager();
    employeeManager.insert(emp1);
    employeeManager.insert(emp2);
    employeeManager.insert(emp3);

    List<Employee> employeeList = employeeManager.getAll();
    for (Employee emp : employeeList) {
      System.out.println(emp);
    }

    employeeManager.update(new Employee(1, DepartmentType.PLASTICSURGERY, "Jose L", false));
    employeeList = employeeManager.getAll();
    for (Employee emp : employeeList) {
      System.out.println(emp);
    }

    System.out.println();

    long d = System.currentTimeMillis();
    Date date = new Date(d);
    DateFormat dateFormat = new SimpleDateFormat("MMddyyyy");

    /**
     * 1 30 OPEN 1 2092022 09:00 2082022 2 31 OPEN 2 2092022 09:00 2082022 3 32 PENDING 3 2092022
     * 09:00 2082022 4 33 CLOSED 4 2092022 09:00 2082022 5 34 CLOSED 5 2092022 09:00 2082022 6 35
     * CANCELLED 6 2092022 09:00 2082022
     */
    SecurityServiceRequest secReq1 =
        new SecurityServiceRequest(ServiceRequestStatus.OPEN, emp1, loc1, date, date, 1);
    SecurityServiceRequest secReq2 =
        new SecurityServiceRequest(ServiceRequestStatus.PENDING, emp2, loc2, date, date, 2);
    SecurityServiceRequest secReq3 =
        new SecurityServiceRequest(ServiceRequestStatus.CLOSED, emp3, loc3, date, date, 3);

    SecurityServiceRequestManager secReqManager = new SecurityServiceRequestManager();
    secReqManager.insert(secReq1);
    secReqManager.insert(secReq2);
    secReqManager.insert(secReq3);

    List<SecurityServiceRequest> securitylist = secReqManager.getAll();
    for (SecurityServiceRequest sec : securitylist) {
      System.out.println(sec);
    }

    secReqManager.update(
        new SecurityServiceRequest(ServiceRequestStatus.CLOSED, emp1, loc1, date, date, 1));
    System.out.println(secReqManager.get(1));
    System.out.println();

    SanitationServiceRequest sanReq1 =
        new SanitationServiceRequest(ServiceRequestStatus.OPEN, emp1, loc1, date, date, 1);
    SanitationServiceRequest sanReq2 =
        new SanitationServiceRequest(ServiceRequestStatus.PENDING, emp2, loc2, date, date, 2);
    SanitationServiceRequest sanReq3 =
        new SanitationServiceRequest(ServiceRequestStatus.CLOSED, emp3, loc3, date, date, 3);

    SanitationServiceRequestManager sanReqManager = new SanitationServiceRequestManager();
    sanReqManager.insert(sanReq1);
    sanReqManager.insert(sanReq2);
    sanReqManager.insert(sanReq3);

    List<SanitationServiceRequest> sanReqlist = sanReqManager.getAll();
    for (SanitationServiceRequest san : sanReqlist) {
      System.out.println(san);
    }

    sanReqManager.update(
        new SanitationServiceRequest(ServiceRequestStatus.CLOSED, emp1, loc1, date, date, 1));

    System.out.println();
    MedicineDeliveryServiceRequest medReq1 =
        new MedicineDeliveryServiceRequest(
            ServiceRequestStatus.OPEN, emp1, loc1, date, date, 1, date);
    MedicineDeliveryServiceRequest medReq2 =
        new MedicineDeliveryServiceRequest(
            ServiceRequestStatus.PENDING, emp2, loc2, date, date, 2, date);
    MedicineDeliveryServiceRequest medReq3 =
        new MedicineDeliveryServiceRequest(
            ServiceRequestStatus.CLOSED, emp3, loc3, date, date, 3, date);

    MedicineDeliveryServiceRequestManager medReqManager =
        new MedicineDeliveryServiceRequestManager();
    medReqManager.insert(medReq1);
    medReqManager.insert(medReq2);
    medReqManager.insert(medReq3);

    List<MedicineDeliveryServiceRequest> medReqlist = medReqManager.getAll();
    for (MedicineDeliveryServiceRequest san : medReqlist) {
      System.out.println(san);
    }

    medReqManager.update(
        new MedicineDeliveryServiceRequest(
            ServiceRequestStatus.CLOSED, emp1, loc1, date, date, 1, date));

    System.out.println(medReqManager.get(1));
    System.out.println();

    /**
     * ServiceRequestStatus requestStatus, Employee assignee, Location location, Date closeDate,
     * Date openDate, int id, Equipment equipment, String patient
     */
    MedicalEquipmentSRManager medEqpManager = new MedicalEquipmentSRManager();
    MedicalEquipmentServiceRequest sr1 =
        new MedicalEquipmentServiceRequest(
            ServiceRequestStatus.OPEN, emp1, loc1, date, date, 1, equipment1, "Jose Morales");
    MedicalEquipmentServiceRequest sr2 =
        new MedicalEquipmentServiceRequest(
            ServiceRequestStatus.CLOSED, emp2, loc2, date, date, 2, equipment2, "Josie Morales");
    MedicalEquipmentServiceRequest sr3 =
        new MedicalEquipmentServiceRequest(
            ServiceRequestStatus.PENDING, emp3, loc3, date, date, 3, equipment3, "Jose Lopez");

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

    medEqpManager.remove(3);
    medEqpList = medEqpManager.getAll();
    for (MedicalEquipmentServiceRequest san : medEqpList) {
      System.out.println(san);
    }

    equipmentManager.remove(3);
    equipmentLinkedList = equipmentManager.getAll();
    for (Equipment san : equipmentLinkedList) {
      System.out.println(san);
    }

    medReqManager.remove(3);
    medReqlist = medReqManager.getAll();
    for (MedicineDeliveryServiceRequest san : medReqlist) {
      System.out.println(san);
    }

    sanReqManager.remove(3);
    sanReqlist = sanReqManager.getAll();
    for (SanitationServiceRequest san : sanReqlist) {
      System.out.println(san);
    }

    secReqManager.remove(3);
    securitylist = secReqManager.getAll();
    for (SecurityServiceRequest san : securitylist) {
      System.out.println(san);
    }

    employeeManager.remove(3);
    employeeList = employeeManager.getAll();

    for (Employee emp : employeeList) {
      System.out.println(emp);
    }

    location.remove(3);
    result = (LinkedList<Location>) location.getAll();

    for (Location emp : result) {
      System.out.println(emp);
    }

    App.launch(App.class, args);
  }
}

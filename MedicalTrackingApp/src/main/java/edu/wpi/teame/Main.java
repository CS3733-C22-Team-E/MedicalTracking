package edu.wpi.teame;

import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.objectManagers.EmployeeManager;
import edu.wpi.teame.db.objectManagers.EquipmentManager;
import edu.wpi.teame.db.objectManagers.LocationManager;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Equipment;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.*;
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

    //    SecurityServiceRequestManager secReqManager = new SecurityServiceRequestManager();
    //    //secReqManager.insert(secReq1);
    //    //secReqManager.insert(secReq2);
    //    //secReqManager.insert(secReq3);
    //
    //    List<SecurityServiceRequest> securitylist = secReqManager.getAll();
    //    for (SecurityServiceRequest sec : securitylist) {
    //      System.out.println(sec);
    //    }

    App.launch(App.class, args);
  }
}

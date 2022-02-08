package edu.wpi.teame.model.serviceRequests;

import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.FloorType;
import edu.wpi.teame.model.enums.ServiceRequestStatus;

import java.sql.Date;

public class SecurityServiceRequest extends ServiceRequest {
    protected SecurityServiceRequest(ServiceRequestStatus requestStatus, Employee assignee, Location location, FloorType floorType, Date closeDate, Date openDate, int id) {
        super(requestStatus, assignee, location, floorType, closeDate, openDate, id);
    }
}

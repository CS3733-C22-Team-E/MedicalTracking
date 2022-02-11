package edu.wpi.teame.view;

import static javafx.application.Application.launch;

import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.serviceRequests.ServiceRequest;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ServiceRequestBacklog {

  GridPane requestHolder = new GridPane();
  ScrollPane scrollWrapper = new ScrollPane();
  int cardCursor = 0;
  private double SCENEWIDTH;
  private double SCENEHEIGHT;
  private final double VGAP = 2;

  private List<ServiceRequest> serviceRequestsFromDB = new LinkedList<ServiceRequest>();
  private HashMap<Integer, ServiceRequestCard> cardsDisplayedById =
      new HashMap<Integer, ServiceRequestCard>();

  public ServiceRequestBacklog(double width, double height) throws SQLException {
    SCENEWIDTH = width;
    SCENEHEIGHT = height;
  }

  public static void main(String[] args) {
    launch(args);
  }

  private void getSecurityRequests() throws SQLException {
    serviceRequestsFromDB.addAll(DBManager.getInstance().getSanitationSRManager().getAll());
    System.out.println(
        "Added "
            + DBManager.getInstance().getSanitationSRManager().getAll().size()
            + " from Sanitation.");
    serviceRequestsFromDB.addAll(DBManager.getInstance().getSecuritySRManager().getAll());
    System.out.println(
        "Added "
            + DBManager.getInstance().getSecuritySRManager().getAll().size()
            + " from Security.");
    serviceRequestsFromDB.addAll(DBManager.getInstance().getMedicineDeliverySRManager().getAll());
    System.out.println(
        "Added "
            + DBManager.getInstance().getMedicineDeliverySRManager().getAll().size()
            + " from Medicine Delivery.");
    serviceRequestsFromDB.addAll(DBManager.getInstance().getMedicalEquipmentSRManager().getAll());
    System.out.println(
        "Added "
            + DBManager.getInstance().getMedicalEquipmentSRManager().getAll().size()
            + " from Medical Equipment Delivery.");
    System.out.println(
        "There are now " + serviceRequestsFromDB.size() + " service requests in the linked list.");
  }

  public Parent getBacklogScene() throws SQLException {
    getSecurityRequests();
    System.out.println("getBacklogScene");
    requestHolder.setVgap(VGAP);
    scrollWrapper.setPrefSize(SCENEWIDTH, SCENEHEIGHT);
    scrollWrapper.setContent(requestHolder);
    for (ServiceRequest sr : serviceRequestsFromDB) {
      System.out.println("Adding card...");
      if (!cardsDisplayedById.containsKey(sr.getId())) {
        System.out.println("srId " + sr.getId() + " is new.");
        ServiceRequestCard card = new ServiceRequestCard(sr, 0, 1000, SCENEHEIGHT, sr.getId());
        card.setPatientName(
            "John Doe"); // TODO make name a field in SR and have it set in card automatically
        addServiceRequestCard(card);
      }
    }
    return scrollWrapper;
  }

  public void addServiceRequestCard(ServiceRequestCard c) {
    HBox card = c.getCard(SCENEWIDTH, 100);
    requestHolder.add(card, 0, cardsDisplayedById.size());
    cardsDisplayedById.put(c.getServiceRequest().getId(), c);
  }

  // TODO Fix this method. Checkbox doesn't do anything yet
  public void removeServiceRequest() {}
}

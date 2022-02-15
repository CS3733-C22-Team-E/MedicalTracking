package edu.wpi.teame.view;

import static javafx.application.Application.launch;

import edu.wpi.teame.db.DBManager;

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
    serviceRequestsFromDB.addAll(DBManager.getInstance().getSecuritySRManager().getAll());
    serviceRequestsFromDB.addAll(DBManager.getInstance().getMedicineDeliverySRManager().getAll());
    serviceRequestsFromDB.addAll(DBManager.getInstance().getMedicalEquipmentSRManager().getAll());
  }

  public Parent getBacklogScene() throws SQLException {
    getSecurityRequests();
    System.out.println("getBacklogScene");
    requestHolder.setVgap(VGAP);
    scrollWrapper.setPrefSize(SCENEWIDTH, SCENEHEIGHT);
    scrollWrapper.setContent(requestHolder);
    for (ServiceRequest sr : serviceRequestsFromDB) {
      if (!cardsDisplayedById.containsKey(sr.getId())) {
        System.out.println("srId " + sr.getId() + " is new.");
        ServiceRequestCard card = new ServiceRequestCard(sr, 0, this);
        card.setPatientName(
            "John Doe"); // TODO make name a field in SR and have it set in card automatically
        addServiceRequestCard(card);
      }
    }
    return scrollWrapper;
  }

  public void addServiceRequestCard(ServiceRequestCard c) {
    HBox card = c.getCard(1000, 100);
    requestHolder.add(card, 0, cardsDisplayedById.size());
    cardsDisplayedById.put(c.getServiceRequest().getId(), c);
  }

  // TODO Fix this method. Checkbox doesn't do anything yet
  public void removeServiceRequest() {}
}

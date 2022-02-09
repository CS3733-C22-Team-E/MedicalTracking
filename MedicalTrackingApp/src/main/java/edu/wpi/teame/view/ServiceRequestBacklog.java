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
  private HashMap<Integer, HBox> cardHashMap = new HashMap<Integer, HBox>();
  int cardCursor = 0;
  private double SCENEWIDTH;
  private double SCENEHEIGHT;
  private final double VGAP = 2;

  private List<ServiceRequest> allServiceRequests = new LinkedList<ServiceRequest>();

  public ServiceRequestBacklog(double width, double height) throws SQLException {
    SCENEWIDTH = width;
    SCENEHEIGHT = height;
  }

  public static void main(String[] args) {
    launch(args);
  }

  private void getSecurityRequests() throws SQLException {
    System.out.println("Fetching SR from DB");
    allServiceRequests.addAll(DBManager.getInstance().getSecuritySRManager().getAll());
  }

  public Parent getBacklogScene() throws SQLException {
    getSecurityRequests();
    System.out.println("getBacklogScene");
    requestHolder.setVgap(VGAP);
    scrollWrapper.setPrefSize(SCENEWIDTH, SCENEHEIGHT);
    scrollWrapper.setContent(requestHolder);
    for (ServiceRequest sr : allServiceRequests) {
      System.out.println("Adding card...");
      ServiceRequestCard card =
          new ServiceRequestCard(
              this,
              sr.getDBType(),
              sr.getLocation().getLongName(),
              sr.getDBType().name(),
              0,
              SCENEWIDTH / 2,
              SCENEHEIGHT,
              sr.getId());
      card.setPatientName("John Doe");
      card.setFloor(sr.getLocation().getFloor().name());
      card.setRoomNumber(sr.getLocation().getShortName());
      card.setStatus(sr.getStatus().toString());
      addServiceRequestCard(card);
    }
    return scrollWrapper;
  }

  public void addServiceRequestCard(ServiceRequestCard c) {
    HBox card = c.getCard(SCENEWIDTH, 100);
    requestHolder.add(card, 0, cardCursor);
  }

  // TODO Fix this method. Checkbox doesn't do anything yet
  public void removeServiceRequest() {}
}

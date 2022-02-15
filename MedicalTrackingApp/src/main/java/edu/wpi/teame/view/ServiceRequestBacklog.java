package edu.wpi.teame.view;

import static javafx.application.Application.launch;

import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.serviceRequests.ServiceRequest;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ServiceRequestBacklog {

  ScrollPane scrollWrapper = new ScrollPane();
  int cardCursor = 0;
  private double SCENEWIDTH;
  private double SCENEHEIGHT;
  private final double VGAP = 3;

  private List<ServiceRequest> serviceRequestsFromDB = new LinkedList<ServiceRequest>();
  private List<ServiceRequestCard> cardsDisplayed = new LinkedList<ServiceRequestCard>();

  public ServiceRequestBacklog(double width, double height) throws SQLException {
    SCENEWIDTH = width;
    SCENEHEIGHT = height;
    scrollWrapper.setPrefSize(SCENEWIDTH, SCENEHEIGHT);
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
    serviceRequestsFromDB.clear();
    getSecurityRequests();
    System.out.println("getBacklogScene");
    scrollWrapper.setContent(getRequestHolder());
    return scrollWrapper;
  }

  public GridPane getRequestHolder() {
    GridPane requestHolder = new GridPane();
    requestHolder.setVgap(VGAP);
    cardsDisplayed.clear();
    for (ServiceRequest sr : serviceRequestsFromDB) {
      ServiceRequestCard card = new ServiceRequestCard(sr, this);
      card.setPatientName(
          "John Doe"); // TODO make name a field in SR and have it set in card automatically
      addServiceRequestCard(card, requestHolder);
    }
    return requestHolder;
  }

  public void addServiceRequestCard(ServiceRequestCard c, GridPane g) {
    HBox card = c.getCard(SCENEWIDTH / 1.5, 100);
    g.add(card, 0, cardsDisplayed.size());
    cardsDisplayed.add(c);
  }

  // TODO Fix this method. Checkbox doesn't do anything yet
  public void removeServiceRequest(int id) {
    serviceRequestsFromDB.removeIf(sr -> sr.getId() == id);
    scrollWrapper.setContent(getRequestHolder());
  }
}

package edu.wpi.teame.view;

import static javafx.application.Application.launch;

import edu.wpi.teame.db.DBManager;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ServiceRequestBacklog {

  ScrollPane scrollWrapper = new ScrollPane();
  private double SCENEWIDTH;
  private double SCENEHEIGHT;
  private final double VGAP = 3;

  private List<ServiceRequest> serviceRequestsFromDB = new LinkedList<>();
  private List<ServiceRequestCard> cardsDisplayed = new LinkedList<>();

  public ServiceRequestBacklog(double width, double height) {
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
    scrollWrapper.setContent(getRequestHolder());
    return scrollWrapper;
  }

  public GridPane getRequestHolder() {
    GridPane requestHolder = new GridPane();
    requestHolder.setVgap(VGAP);
    cardsDisplayed.clear();
    serviceRequestsFromDB.sort( // TODO This sorts by DATE, not date and time. This should be fixed.
        new Comparator<ServiceRequest>() {
          @Override
          public int compare(ServiceRequest serviceRequest, ServiceRequest t1) {
            return serviceRequest.getOpenDate().compareTo(t1.getOpenDate());
          }
        });
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

  public void removeServiceRequest(int id) {
    serviceRequestsFromDB.removeIf(sr -> sr.getId() == id);
    scrollWrapper.setContent(getRequestHolder());
    // TODO update DB on delete
  }
}

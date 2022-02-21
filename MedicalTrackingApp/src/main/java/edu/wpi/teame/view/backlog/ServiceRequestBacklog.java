package edu.wpi.teame.view.backlog;

import static javafx.application.Application.launch;

import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.objectManagers.ObjectManager;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import edu.wpi.teame.model.serviceRequests.ServiceRequest;
import java.awt.*;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ServiceRequestBacklog {

  ScrollPane scrollWrapper = new ScrollPane();
  private double SCENEWIDTH;
  private double SCENEHEIGHT;
  private final double VGAP = 3;
  private double CARDWIDTH;

  private List<ServiceRequest> serviceRequestsFromDB = new LinkedList<>();
  private List<ServiceRequestCard> cardsDisplayed = new LinkedList<>();
  private List<ServiceRequest> deadServiceRequests = new LinkedList<>();
  private List<ServiceRequestCard> deadCards = new LinkedList<>();

  public ServiceRequestBacklog(double width, double height) {
    SCENEWIDTH = width;
    SCENEHEIGHT = height;
    scrollWrapper.setPrefSize(SCENEWIDTH, SCENEHEIGHT);
    CARDWIDTH = SCENEWIDTH / 1.5;
  }

  public static void main(String[] args) {
    launch(args);
  }

  private void getSecurityRequests() throws SQLException {
    System.out.println("Getting SR");
    serviceRequestsFromDB.clear();

    serviceRequestsFromDB.addAll(DBManager.getInstance().getSanitationSRManager().getAll());
    serviceRequestsFromDB.addAll(DBManager.getInstance().getSecuritySRManager().getAll());
    serviceRequestsFromDB.addAll(DBManager.getInstance().getMedicineDeliverySRManager().getAll());
    serviceRequestsFromDB.addAll(DBManager.getInstance().getMedicalEquipmentSRManager().getAll());
    serviceRequestsFromDB.addAll(DBManager.getInstance().getAudioVisualSRManager().getAll());
    serviceRequestsFromDB.addAll(DBManager.getInstance().getExternalPatientSRManager().getAll());
    serviceRequestsFromDB.addAll(DBManager.getInstance().getComputerSRManager().getAll());
    serviceRequestsFromDB.addAll(
        DBManager.getInstance().getFacilitiesMaintenanceSRManager().getAll());
    serviceRequestsFromDB.addAll(DBManager.getInstance().getFoodDeliverySRManager().getAll());
    serviceRequestsFromDB.addAll(DBManager.getInstance().getGiftAndFloralSRManager().getAll());
    serviceRequestsFromDB.addAll(DBManager.getInstance().getInternalPatientSRManager().getAll());
    serviceRequestsFromDB.addAll(DBManager.getInstance().getLanguageSRManager().getAll());
    serviceRequestsFromDB.addAll(DBManager.getInstance().getLaundrySRManager().getAll());
    serviceRequestsFromDB.addAll(DBManager.getInstance().getReligiousSRManager().getAll());
  }

  public Parent getBacklogScene() throws SQLException {
    serviceRequestsFromDB.clear();
    scrollWrapper.setContent(getRequestHolder());
    return scrollWrapper;
  }

  public GridPane getRequestHolder() throws SQLException {
    getSecurityRequests();
    GridPane requestHolder = new GridPane();
    requestHolder.setVgap(VGAP);
    cardsDisplayed.clear();
    deadServiceRequests.clear();
    //    serviceRequestsFromDB.sort(
    //        new Comparator<ServiceRequest>() {
    //          @Override
    //          public int compare(ServiceRequest serviceRequest, ServiceRequest t1) {
    //            return
    // serviceRequest.getOpenDate().toInstant().compareTo(t1.getOpenDate().toInstant());
    //          }
    //        });
    for (ServiceRequest sr : serviceRequestsFromDB) {
      if (sr.getStatus().equals(ServiceRequestStatus.CLOSED)
          || sr.getStatus().equals(ServiceRequestStatus.CANCELLED)) {
        deadServiceRequests.add(sr);
      } else {
        ServiceRequestCard card = new ServiceRequestCard(sr, this);
        addServiceRequestCard(card, requestHolder);
      }
    }
    for (ServiceRequest sr : deadServiceRequests) {
      ServiceRequestCard card = new ServiceRequestCard(sr, this, true);
      addServiceRequestCard(card, requestHolder);
    }
    requestHolder.add(getRefreshBar(), 0, 0);
    return requestHolder;
  }

  public void addServiceRequestCard(ServiceRequestCard c, GridPane g) {
    HBox card = c.getCard(CARDWIDTH, 100);
    g.add(card, 0, cardsDisplayed.size() + 1);
    cardsDisplayed.add(c);
  }

  public void killServiceRequest(ServiceRequest sr) throws SQLException {
    sr.setStatus(ServiceRequestStatus.CLOSED);
    ObjectManager m = sr.getDBType().getDBManagerInstance();
    m.update(sr);
    scrollWrapper.setContent(getRequestHolder());
  }

  public void removeServiceRequest(ServiceRequest sr) throws SQLException {
    ObjectManager m = sr.getDBType().getDBManagerInstance();
    m.remove(sr.getId());
    scrollWrapper.setContent(getRequestHolder());
  }

  public HBox getRefreshBar() {
    HBox refreshBar = new HBox();
    refreshBar.setPrefSize(CARDWIDTH, 50);
    Text refreshText = new Text("Click to refresh...");
    refreshText.setFont(Font.font(24));
    refreshBar.getChildren().add(refreshText);
    refreshBar.setAlignment(Pos.CENTER);
    Background noHoverBG = new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY));
    refreshBar.setBackground(noHoverBG);
    Background hoverBG = new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY));
    refreshBar.setOnMouseExited(e -> {
      refreshBar.setBackground(noHoverBG);
    });
    refreshBar.setOnMouseEntered(e -> {
      refreshBar.setBackground(hoverBG);
    });
    refreshBar.setOnMouseClicked(
        e -> {
          try {
            scrollWrapper.setContent(getRequestHolder());
          } catch (SQLException ex) {
            ex.printStackTrace();
          }
        });
    return refreshBar;
  }
}

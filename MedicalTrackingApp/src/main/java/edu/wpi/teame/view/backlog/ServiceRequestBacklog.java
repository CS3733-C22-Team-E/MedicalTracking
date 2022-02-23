package edu.wpi.teame.view.backlog;

import static javafx.application.Application.launch;

import com.jfoenix.controls.JFXCheckBox;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.objectManagers.ObjectManager;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import edu.wpi.teame.model.serviceRequests.ServiceRequest;
import java.awt.*;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class ServiceRequestBacklog {

  ScrollPane scrollWrapper = new ScrollPane();
  private double SCENEWIDTH;
  private double SCENEHEIGHT;
  private final double VGAP = 3;
  private double CARDWIDTH;

  private HashMap<String, Boolean> filterMap = new HashMap<String, Boolean>();
  private List<ServiceRequest> serviceRequestsFromDB = new LinkedList<>();
  private List<ServiceRequestCard> cardsDisplayed = new LinkedList<>();
  private List<ServiceRequest> deadServiceRequests = new LinkedList<>();
  private List<ServiceRequestCard> deadCards = new LinkedList<>();

  public ServiceRequestBacklog(double width, double height) {
    SCENEWIDTH = width;
    SCENEHEIGHT = height;
    scrollWrapper.setPrefSize(SCENEWIDTH, SCENEHEIGHT);
    CARDWIDTH = SCENEWIDTH;
  }

  public static void main(String[] args) {
    launch(args);
  }

  private void getSecurityRequests() throws SQLException {
    System.out.println("Getting SR");
    serviceRequestsFromDB.clear();
    serviceRequestsFromDB.addAll(DBManager.getManager(DataBaseObjectType.SanitationSR).getAll());
    serviceRequestsFromDB.addAll(DBManager.getManager(DataBaseObjectType.SecuritySR).getAll());
    serviceRequestsFromDB.addAll(
        DBManager.getManager(DataBaseObjectType.MedicineDeliverySR).getAll());
    serviceRequestsFromDB.addAll(
        DBManager.getManager(DataBaseObjectType.MedicalEquipmentSR).getAll());
    serviceRequestsFromDB.addAll(DBManager.getManager(DataBaseObjectType.AudioVisualSR).getAll());
    serviceRequestsFromDB.addAll(
        DBManager.getManager(DataBaseObjectType.ExternalPatientSR).getAll());
    serviceRequestsFromDB.addAll(DBManager.getManager(DataBaseObjectType.ComputerSR).getAll());
    serviceRequestsFromDB.addAll(
        DBManager.getManager(DataBaseObjectType.FacilitiesMaintenanceSR).getAll());
    serviceRequestsFromDB.addAll(DBManager.getManager(DataBaseObjectType.FoodDeliverySR).getAll());
    serviceRequestsFromDB.addAll(DBManager.getManager(DataBaseObjectType.GiftAndFloralSR).getAll());
    serviceRequestsFromDB.addAll(
        DBManager.getManager(DataBaseObjectType.InternalPatientTransferSR).getAll());
    serviceRequestsFromDB.addAll(
        DBManager.getManager(DataBaseObjectType.LanguageInterpreterSR).getAll());
    serviceRequestsFromDB.addAll(DBManager.getManager(DataBaseObjectType.LaundrySR).getAll());
    serviceRequestsFromDB.addAll(DBManager.getManager(DataBaseObjectType.ReligiousSR).getAll());
    serviceRequestsFromDB.addAll(DBManager.getManager(DataBaseObjectType.DeceasedBodySR).getAll());
    serviceRequestsFromDB.addAll(
        DBManager.getManager(DataBaseObjectType.PatientDischargeSR).getAll());
  }

  public Parent getBacklogScene() throws SQLException {
    serviceRequestsFromDB.clear();
    scrollWrapper.setContent(getRequestHolder());
    return scrollWrapper;
  }

  public HBox getTitle() throws SQLException {

    HBox tBox = new HBox();
    Text title = new Text("Service Request Backlog");
    title.setFont(Font.font("Arial", FontWeight.BOLD, 56));
    title.setFill(Color.WHITE);
    title.setTextAlignment(TextAlignment.CENTER);
    title.setWrappingWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2);
    Stop[] stops =
        new Stop[] {
          new Stop(0, Color.color(0.458823529, 0.474509804, 1)),
          new Stop(1, Color.color(0.698039216, 0.141176471, 0.937254902))
        };
    LinearGradient lg1 = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
    tBox.setBackground(new Background(new BackgroundFill(lg1, CornerRadii.EMPTY, Insets.EMPTY)));
    tBox.setPadding(new Insets(10, 0, 10, 0));
    tBox.getChildren().add(title);
    tBox.setAlignment(Pos.CENTER);

    return tBox;
  }

  public GridPane getRequestHolder() throws SQLException {
    System.out.println("g");
    getSecurityRequests();
    GridPane requestHolder = new GridPane();
    requestHolder.setVgap(VGAP);
    cardsDisplayed.clear();
    deadServiceRequests.clear();
    serviceRequestsFromDB = sortServiceRequestsFromDB(serviceRequestsFromDB);
    System.out.println(serviceRequestsFromDB.size());
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
    requestHolder.add(getTitle(), 0, 0);
    requestHolder.add(getRefreshBar(), 0, 1);
    requestHolder.add(getFilterBar(), 0, 2);
    //    GridPane titleGridPane = new GridPane();
    //    titleGridPane.set
    return requestHolder;
  }

  public void addServiceRequestCard(ServiceRequestCard c, GridPane g) {
    HBox card = c.getCard(CARDWIDTH, 100);
    g.add(card, 0, cardsDisplayed.size() + 3);
    cardsDisplayed.add(c);
  }

  public void killServiceRequest(ServiceRequest sr) throws SQLException {
    sr.setStatus(ServiceRequestStatus.CLOSED);
    ObjectManager m = DBManager.getManager(sr.getDBType());
    m.update(sr);
    scrollWrapper.setContent(getRequestHolder());
  }

  public void removeServiceRequest(ServiceRequest sr) throws SQLException {
    ObjectManager m = DBManager.getManager(sr.getDBType());
    m.remove(sr.getId());
    scrollWrapper.setContent(getRequestHolder());
  }

  public HBox getRefreshBar() {
    HBox refreshBar = new HBox();
    refreshBar.setPrefSize(CARDWIDTH, 50);
    Text refreshText = new Text("Click to refresh.");
    refreshText.setFont(Font.font(24));
    refreshBar.getChildren().add(refreshText);
    refreshBar.setAlignment(Pos.CENTER);
    Background noHoverBG =
        new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY));
    refreshBar.setBackground(noHoverBG);
    Background hoverBG =
        new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY));
    refreshBar.setOnMouseExited(
        e -> {
          refreshBar.setBackground(noHoverBG);
        });
    refreshBar.setOnMouseEntered(
        e -> {
          refreshBar.setBackground(hoverBG);
        });
    refreshBar.setOnMouseClicked(
        e -> {
          ((Text) refreshBar.getChildren().get(0)).setText("Refreshing...");
          // I'm trying to get this done quickly and have no idea how to make a timer
          // so I'm gonna do something really stupid but I know it'll work.
          ScaleTransition wait2 = new ScaleTransition(new Duration(200), refreshText);
          wait2.setOnFinished(
              ev -> {
                try {
                  scrollWrapper.setContent(getRequestHolder());
                } catch (SQLException ex) {
                  ex.printStackTrace();
                }
              });
          ScaleTransition wait1 = new ScaleTransition(new Duration(500), refreshText);
          wait1.setOnFinished(
              ev -> {
                ((Text) refreshBar.getChildren().get(0)).setText("Done!");
                wait2.play();
              });
          wait1.play();
          // lol
        });
    return refreshBar;
  }

  private LinkedList<ServiceRequest> sortServiceRequestsFromDB(
      List<ServiceRequest> l) { // DB Sees all SR as critical
    LinkedList<ServiceRequest> p1 = new LinkedList<ServiceRequest>();
    LinkedList<ServiceRequest> p2 = new LinkedList<ServiceRequest>();
    LinkedList<ServiceRequest> p3 = new LinkedList<ServiceRequest>();
    LinkedList<ServiceRequest> p4 = new LinkedList<ServiceRequest>();
    LinkedList<ServiceRequest> preFilterList = new LinkedList<ServiceRequest>();

    for (ServiceRequest request : l) {
      switch (request.getPriority()) {
        case Critical:
          p1.add(request);
          break;
        case High:
          p2.add(request);
          break;
        case Normal:
          p3.add(request);
          break;
        case Low:
          p4.add(request);
          break;
      }
    }
    p1.sort(
        new Comparator<ServiceRequest>() {
          @Override
          public int compare(ServiceRequest serviceRequest, ServiceRequest t1) {
            if (serviceRequest.getOpenDate().getTime() == t1.getOpenDate().getTime()) {
              return 0;
            }
            return serviceRequest.getOpenDate().getTime() > t1.getOpenDate().getTime() ? 1 : -1;
          }
        });
    p2.sort(
        new Comparator<ServiceRequest>() {
          @Override
          public int compare(ServiceRequest serviceRequest, ServiceRequest t1) {
            if (serviceRequest.getOpenDate().getTime() == t1.getOpenDate().getTime()) {
              return 0;
            }
            return serviceRequest.getOpenDate().getTime() > t1.getOpenDate().getTime() ? 1 : -1;
          }
        });
    p3.sort(
        new Comparator<ServiceRequest>() {
          @Override
          public int compare(ServiceRequest serviceRequest, ServiceRequest t1) {
            if (serviceRequest.getOpenDate().getTime() == t1.getOpenDate().getTime()) {
              return 0;
            }
            return serviceRequest.getOpenDate().getTime() > t1.getOpenDate().getTime() ? 1 : -1;
          }
        });
    p4.sort(
        new Comparator<ServiceRequest>() {
          @Override
          public int compare(ServiceRequest serviceRequest, ServiceRequest t1) {
            if (serviceRequest.getOpenDate().getTime() == t1.getOpenDate().getTime()) {
              return 0;
            }
            return serviceRequest.getOpenDate().getTime() > t1.getOpenDate().getTime() ? 1 : -1;
          }
        });
    preFilterList.addAll(p1);
    preFilterList.addAll(p2);
    preFilterList.addAll(p3);
    preFilterList.addAll(p4);

    if (filterMap.isEmpty()) {
      return preFilterList;
    }
    LinkedList<ServiceRequest> filteredList = new LinkedList<ServiceRequest>();
    for (ServiceRequest sr : preFilterList) {
      if (filterMap.get(sr.getAssignee().getName())) {
        filteredList.add(sr);
      }
    }
    return filteredList;
  }

  private HBox getFilterBar() throws SQLException {
    HBox filterBar = new HBox();
    filterBar.setPrefSize(CARDWIDTH, 50);
    filterBar.setAlignment(Pos.CENTER);
    filterBar.setSpacing(10);
    List<Object> DBEmployeeList =
        Objects.requireNonNull(DBManager.getManager(DataBaseObjectType.Employee)).getAll();
    if (DBEmployeeList.isEmpty()) {
      Text t = new Text("No employees loaded to filter.");
      filterBar.getChildren().add(t);
      return filterBar;
    }
    for (Object DBObject : DBEmployeeList) {
      Employee e = (Employee) DBObject;
      JFXCheckBox eBox = new JFXCheckBox();
      String eBoxText = e.getName();
      if (eBoxText.length() > 18) {
        eBoxText = eBoxText.substring(0, 10) + "...";
      }
      eBox.setText(eBoxText);
      if (filterMap.containsKey(e.getName())) {
        eBox.setSelected(filterMap.get(e.getName()));
      } else {
        filterMap.put(e.getName(), true);
        eBox.setSelected(true);
      }
      eBox.setOnAction(
          ev -> {
            Boolean oldVal = filterMap.get(e.getName());
            filterMap.replace(e.getName(), !oldVal);
            try {
              scrollWrapper.setContent(getRequestHolder());
            } catch (SQLException ex) {
              ex.printStackTrace();
            }
          });
      filterBar.getChildren().add(eBox);
    }
    return filterBar;
  }
}

package edu.wpi.teame.model;

import static javafx.application.Application.launch;

import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.MedicalEquipmentServiceRequest;
import edu.wpi.teame.model.enums.ServiceRequestTypes;
import java.util.HashMap;
import java.util.LinkedList;
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

  public ServiceRequestBacklog(double width, double height) {
    SCENEWIDTH = width;
    SCENEHEIGHT = height;
  }

  public static void main(String[] args) {
    launch(args);
  }

  public Parent getBacklogScene() {
    requestHolder.setVgap(VGAP);
    scrollWrapper.setPrefSize(SCENEWIDTH, SCENEHEIGHT);
    scrollWrapper.setContent(requestHolder);

    testAddSR(20);

    return scrollWrapper;
  }

  public void getFromDB() { // TODO Implement DB
    LinkedList<MedicalEquipmentServiceRequest> medicalEquipmentServiceRequests =
        DBManager.getInstance().getMEServiceRequestManager().getAll();
  }

  public void addServiceRequestCard(ServiceRequestCard c) {
    HBox card = c.getCard(SCENEWIDTH, 100);
    requestHolder.add(card, 0, cardCursor);
  }

  private void testAddSR(int num) {
    ServiceRequestCard testCard =
        new ServiceRequestCard(
            this,
            ServiceRequestTypes.MedicalEquipment,
            "Test Card",
            "Test",
            0,
            SCENEWIDTH,
            SCENEHEIGHT,
            cardCursor);
    for (int i = 0; i < num; i++) {
      addServiceRequestCard(testCard);
      cardCursor++;
    }
  }

  // TODO Fix this method. Checkbox doesn't do anything yet
  public void removeServiceRequest() {}
}

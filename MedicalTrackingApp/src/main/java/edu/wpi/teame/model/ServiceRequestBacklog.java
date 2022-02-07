package edu.wpi.teame.model;

import static javafx.application.Application.launch;

import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.MedicalEquipmentServiceRequest;
import edu.wpi.teame.model.enums.ServiceRequestTypes;
import java.util.LinkedList;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

public class ServiceRequestBacklog {

  GridPane requestHolder = new GridPane();
  ScrollPane scrollWrapper = new ScrollPane();
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

  public void getFromDB() {
    LinkedList<MedicalEquipmentServiceRequest> medicalEquipmentServiceRequests =
        DBManager.getInstance().getMEServiceRequestManager().getAll();
  }

  public void addServiceRequestCard(ServiceRequestCard c) {
    requestHolder.add(c.getCard(500, 100), 0, cardCursor);
    cardCursor++;
  }

  private void testAddSR(int num) {
    ServiceRequestCard testCard =
        new ServiceRequestCard(
            ServiceRequestTypes.MedicalEquipment, "Test Card", "Test", 0, SCENEWIDTH, SCENEHEIGHT);
    for (int i = 0; i < num; i++) {
      addServiceRequestCard(testCard);
    }
  }
}

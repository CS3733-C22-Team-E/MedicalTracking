package edu.wpi.teame.view;

import static javafx.application.Application.launch;

import java.util.HashMap;
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

    return scrollWrapper;
  }

  public void addServiceRequestCard(ServiceRequestCard c) {
    HBox card = c.getCard(SCENEWIDTH, 100);
    requestHolder.add(card, 0, cardCursor);
  }

  // TODO Fix this method. Checkbox doesn't do anything yet
  public void removeServiceRequest() {}
}

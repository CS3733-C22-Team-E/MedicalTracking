package edu.wpi.teame.model;

import static javafx.application.Application.launch;

import com.jfoenix.controls.JFXButton;
import edu.wpi.teame.App;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.Equipment;
import edu.wpi.teame.db.EquipmentType;
import edu.wpi.teame.db.FloorType;
import java.util.ArrayList;
import java.util.LinkedList;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;

/** Constructs a scene with a pannable Map background. */
public class PannableView {
  private final Image backgroundImage;
  private final int WIDTH = 1280;
  private final int HEIGHT = 720;
  private final int ICONSIZE = 60;

  private double mapImageWidth = 0;
  private double mapImageHeight = 0;

  private boolean hamburgerDeployed = false;
  private boolean addMode = false;

  private ArrayList<ImageView> hamburgerDeployments = new ArrayList<ImageView>();
  private ArrayList<MapIcon> mapIcons = new ArrayList<MapIcon>();
  private StackPane layout = new StackPane();

  private FloorType currFloor;

  public PannableView(FloorType floor) {
    backgroundImage = new Image(App.class.getResource(getMapImg(floor)).toString());
    mapImageHeight = backgroundImage.getHeight();
    mapImageWidth = backgroundImage.getWidth();
    currFloor = floor;
  }

  private String getMapImg(FloorType f) {
    switch (f) {
      case GroundFloor:
        return "images/map/00_thegroundfloor.png";
      case LowerLevel1:
        return "images/map/00_thelowerlevel1.png";
      case LowerLevel2:
        return "images/map/00_thelowerlevel2.png";
      case FirstFloor:
        return "images/map/01_thefirstfloor.png";
      case SecondFloor:
        return "images/map/02_thesecondfloor.png";
      case ThirdFloor:
        return "images/map/03_thethirdfloor.png";
      default:
        return "";
    }
  }

  public Parent getMapScene(double height, double width) {
    layout.setOnMouseClicked(
        (e -> {
          if (addMode) {
            addMapIcon(e.getX(), e.getY(), "AppIcon.png");
          }
        }));
    updateLayoutChildren();
    layout.setScaleX(.5);
    layout.setScaleY(.5);

    ScrollPane scroll = createScrollPane(layout);

    StackPane staticWrapper = new StackPane();
    staticWrapper
        .getChildren()
        .setAll(scroll, createHamburgerButton(), createZoomInButton(), createZoomOutButton());
    addHamburgerDeployments();
    for (ImageView imageView : hamburgerDeployments) {
      staticWrapper.getChildren().add(imageView);
    }

    scroll.setPrefSize(width, height);
    scroll.setHvalue(scroll.getHmin() + (scroll.getHmax() - scroll.getHmin()) / 2);
    scroll.setVvalue(scroll.getVmin() + (scroll.getVmax() - scroll.getVmin()) / 2);

    return staticWrapper;
  }

  private void updateLayoutChildren() {
    layout.getChildren().setAll(new ImageView(backgroundImage));
    for (MapIcon icon : mapIcons) {
      layout.getChildren().add(icon.getButton());
    }
  }

  private void addMapIcon(double xCoordinate, double yCoordinate, String type) {
    Image iconImage = new Image(App.class.getResource("images/Icons/" + type).toString());
    ImageView iconGraphic = new ImageView(iconImage);
    iconGraphic.setFitWidth(30);
    iconGraphic.setFitHeight(30);
    final JFXButton newButton = new JFXButton();
    newButton.setGraphic(iconGraphic);
    double x = xCoordinate - mapImageWidth / 2;
    double y = yCoordinate - mapImageHeight / 2;
    newButton.setTranslateX(x);
    newButton.setTranslateY(y);
    newButton.setOnAction(
        (event) -> {
          newButton.setVisible(false);
        });
    Tooltip tooltip = new Tooltip(type);
    Tooltip.install(newButton, tooltip);
    MapIcon newIcon = new MapIcon(newButton, type);
    mapIcons.add(newIcon);
    updateLayoutChildren();
  }

  private JFXButton createZoomInButton() {
    Image zoomIcon = new Image(App.class.getResource("images/Icons/ZoomIn.png").toString());
    ImageView icon = new ImageView(zoomIcon);
    icon.setFitWidth(30);
    icon.setFitHeight(30);
    final JFXButton zoomInButton = new JFXButton("", icon);
    zoomInButton.setTranslateX(WIDTH / 2 - (icon.getFitWidth() + 10));
    zoomInButton.setTranslateY(-(HEIGHT / 2 - (icon.getFitHeight() + 50)));
    zoomInButton.setOnAction(
        (event) -> {
          zoomIn();
        });
    return zoomInButton;
  }

  private JFXButton createZoomOutButton() {
    Image zoomIcon = new Image(App.class.getResource("images/Icons/ZoomOut.png").toString());
    ImageView icon = new ImageView(zoomIcon);
    icon.setFitWidth(30);
    icon.setFitHeight(30);
    final JFXButton zoomOutButton = new JFXButton("", icon);
    zoomOutButton.setTranslateX(WIDTH / 2 - (icon.getFitWidth() + 10));
    zoomOutButton.setTranslateY(-(HEIGHT / 2 - (icon.getFitHeight() + 90)));
    zoomOutButton.setOnAction(
        (event) -> {
          zoomOut();
        });
    return zoomOutButton;
  }

  private JFXButton createHamburgerButton() {
    Image hamburgerIcon =
        new Image(App.class.getResource("images/Icons/HamburgerMenu.png").toString());
    ImageView icon = new ImageView(hamburgerIcon);
    icon.setFitHeight(30);
    icon.setFitWidth(30);
    final JFXButton hamburgerButton = new JFXButton("", icon);
    hamburgerButton.setTranslateX(WIDTH / 2 - (icon.getFitWidth() + 10));
    hamburgerButton.setTranslateY(-(HEIGHT / 2 - (icon.getFitHeight() + 10)));
    hamburgerButton.setOnAction(
        (event) -> {
          hamburgerDeployed = !hamburgerDeployed;
          addMode = !addMode;
          // deployHamburger();
        });
    return hamburgerButton;
  }

  private void addHamburgerDeployments() {
    String[] allIcons = {"EquipmentStorageIcon.png", "HospitalBedIcon.png"};
    int iconNum = 0;
    for (String icon : allIcons) {
      Image imageIcon = new Image(App.class.getResource("images/Icons/" + icon).toString());
      ImageView imageVew = new ImageView(imageIcon);
      imageVew.setFitWidth(ICONSIZE);
      imageVew.setFitHeight(ICONSIZE);
      imageVew.setTranslateX(WIDTH / 2 - 10 - ICONSIZE);
      imageVew.setTranslateY(-(HEIGHT / 2) + 100 + iconNum * (ICONSIZE) + iconNum * 10);
      imageVew.setVisible(false);
      hamburgerDeployments.add(imageVew);
      iconNum++;
    }
  }

  private void deployHamburger() {
    for (ImageView imageView : hamburgerDeployments) {
      imageView.setVisible(hamburgerDeployed);
    }
  }

  private ScrollPane createScrollPane(Pane layout) {
    ScrollPane scroll = new ScrollPane();
    scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scroll.setPannable(true);
    scroll.setPrefSize(WIDTH, HEIGHT);
    scroll.setContent(layout);
    return scroll;
  }

  private ImageView getImageViewFromEquipmentType(EquipmentType t) {
    ImageView equipmentIcon = new ImageView();
    equipmentIcon.setFitHeight(30);
    equipmentIcon.setFitWidth(30);
    String png = "";
    switch (t) {
      case PBED:
        png = "HospitalBedIcon.png";
        break;
      case PUMP:
        png = "PumpIcon.png";
        break;
      case RECL:
        png = "ReclinerIcon.png";
        break;
      case XRAY:
        png = "XRayIcon.png";
        break;
      default:
        png = "";
        break;
    }
    Image i = new Image(App.class.getResource("images/Icons/" + png).toString());
    equipmentIcon.setImage(i);
    return equipmentIcon;
  }

  public MapIcon convertLocationToMapIcon(Equipment equip) {
    MapIcon retval =
        new MapIcon(
            (double) equip.getLocationNode().getX(),
            (double) equip.getLocationNode().getY(),
            equip.getLocationNode().getLongName(),
            getImageViewFromEquipmentType(equip.getType()));
    mapIcons.add(retval);
    updateLayoutChildren();
    return retval;
  }

  public void getFromDB() {
    LinkedList<Equipment> equipment = DBManager.getInstance().getEquipmentManager().getAll();
    for (Equipment currEquipment : equipment) {
      if (currEquipment.getLocationNode().getFloor() == currFloor) {
        convertLocationToMapIcon(currEquipment);
      }
    }
  }

  public static void main(String[] args) {
    launch(args);
  }

  private void zoomIn() {
    if (layout.getScaleX() < 1.5) {
      layout.setScaleX(layout.getScaleX() * 1.1);
      layout.setScaleY(layout.getScaleY() * 1.1);
    }
  }

  private void zoomOut() {
    if (layout.getScaleX() > .2) {
      layout.setScaleX(layout.getScaleX() * 1 / (1.1));
      layout.setScaleY(layout.getScaleY() * 1 / (1.1));
    }
  }
}

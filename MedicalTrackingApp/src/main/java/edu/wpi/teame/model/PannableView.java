package edu.wpi.teame.model;

import static javafx.application.Application.launch;

import com.jfoenix.controls.JFXButton;
import edu.wpi.teame.App;
<<<<<<< Updated upstream
=======
import edu.wpi.teame.Pannable;
>>>>>>> Stashed changes
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.Equipment;
import edu.wpi.teame.db.EquipmentType;
import edu.wpi.teame.db.FloorType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;

/** Constructs a scene with a pannable Map background. */
public class PannableView {

  // Init constants
  private final Image backgroundImage;
  private final int WIDTH = 1280;
  private final int HEIGHT = 720;
  private final int ICONSIZE = 60;
  private final double ZOOMINMAX = 1.5;
  private final double ZOOMOUTMAX = .2;
  private final double MAPIMGWIDTH = 5000;
  private final double MAPIMGHEIGHT = 3400;
  private final double ZOOMAMPLIFIER = .1;

  // Init booleans
  private boolean hamburgerDeployed = false;
  private boolean addMode = false;

  // Init data structures
  private ArrayList<ImageView> hamburgerDeployments = new ArrayList<ImageView>();
  private ArrayList<MapIcon> mapIcons = new ArrayList<MapIcon>();
  private HashMap<EquipmentType, ImageView> TypeGraphics = new HashMap<EquipmentType, ImageView>();

  // Init panes
  private StackPane layout = new StackPane();

  // Init everything else
  private FloorType currFloor;

  // Constructor sets the background image and currentFloor enum
  public PannableView(FloorType floor) {
    backgroundImage = new Image(App.class.getResource(getMapImg(floor)).toString());
    currFloor = floor;
  }

<<<<<<< Updated upstream
  // Convert from enum to a background image
  private String getMapImg(FloorType f) {
=======
  private String getMapImg(MapFloor f) {
>>>>>>> Stashed changes
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

  // This is essentially the Main function
  // getMapScene returns the entire map page
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
    layout.setOnScroll(
        (event) -> {
          double scrollVal = event.getDeltaY();
          // Zoom amplifier is accounted for inside handleScrollZoom
          handleScrollZoom(scrollVal);
        });
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
<<<<<<< Updated upstream

    return staticWrapper;
=======
    TypeGraphics.put(
        EquipmentType.PBED, new ImageView(new Image("images/Icons/HospitalBedIcon.png")));
    TypeGraphics.put(EquipmentType.XRAY, new ImageView(new Image("images/Icons/XRayIcon.png")));
    TypeGraphics.put(EquipmentType.PUMP, new ImageView(new Image("image/Icons/ReclinerIcon.png")));
    TypeGraphics.put(EquipmentType.PUMP, new ImageView(new Image("image/Icons/PumpIcon.png")));
>>>>>>> Stashed changes
  }

  // Must be called whenever an icon is added to the map
  private void updateLayoutChildren() {
    layout.getChildren().setAll(new ImageView(backgroundImage));
    for (MapIcon icon : mapIcons) {
      layout.getChildren().add(icon.getButton());
    }
  }

  // Adds icon on click
  // TODO make this meaningful. Right now it just makes a button with whatever icon you tell it to
  // make
  private void addMapIcon(double xCoordinate, double yCoordinate, String type) {
    Image iconImage = new Image(App.class.getResource("images/Icons/" + type).toString());
    ImageView iconGraphic = new ImageView(iconImage);
    iconGraphic.setFitWidth(30);
    iconGraphic.setFitHeight(30);
    final JFXButton newButton = new JFXButton();
    newButton.setGraphic(iconGraphic);
    double x = xCoordinate - MAPIMGWIDTH / 2;
    double y = yCoordinate - MAPIMGHEIGHT / 2;
    newButton.setTranslateX(x);
    newButton.setTranslateY(y);
    newButton.setOnAction(
        (event) -> {
          // TODO Implement click functionality.
          // Click currently removes added nodes. Does not yet work on equipment nodes.
          // For click functionality on equipment nodes, add an onclick in the equipment translator
          newButton.setVisible(false);
        });
    Tooltip tooltip = new Tooltip(type);
    Tooltip.install(newButton, tooltip);
    MapIcon newIcon = new MapIcon(newButton, type);
    mapIcons.add(newIcon);
    updateLayoutChildren();
  }

  // Init zoomInButton
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
          // double value zoomAmplifier is 1 for buttons
          zoomIn(1);
        });
    return zoomInButton;
  }

  // init zoomOutButton
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
          // double value zoomAmplifier is 1 for buttons
          zoomOut(1);
        });
    return zoomOutButton;
  }

  // Init currently unused hamburger button
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

  // Adds icons to the hamburger menu
  // TODO styling— probably a complete redesign
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

  // Displays the hamburger menu by just setting all hamburgerDeployments to visible
  private void deployHamburger() {
    // Hamburger currently unused
    for (ImageView imageView : hamburgerDeployments) {
      imageView.setVisible(hamburgerDeployed);
    }
  }

  // Init ScrollPane that holds the StackPane containing map and all icons
  private ScrollPane createScrollPane(Pane layout) {
    ScrollPane scroll = new ScrollPane();
    scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scroll.setPannable(true);
    scroll.setPrefSize(WIDTH, HEIGHT);
    scroll.setContent(layout);
    return scroll;
  }

<<<<<<< Updated upstream
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
            equip.getName(),
            getImageViewFromEquipmentType(equip.getType()));
=======
  public MapIcon ConvertLocationToMapIcon(Equipment equip) {
    MapIcon retval =
        new MapIcon(
            (double) equip.getLocationNode().getX(),
            (double) equip.getLocationNode().getX(),
            equip.getLocationNode().getLongName(),
            TypeGraphics.get(equip.getType()));
>>>>>>> Stashed changes
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

  // This zoomBar is totally broken. I'm not quite sure how to fix it.
  // TODO make this zoomBar work and display properly so that it may be used like a setting
  private Slider createZoomBar() {
    Slider zoomBar = new Slider();
    zoomBar.setMax(ZOOMINMAX);
    zoomBar.setMin(ZOOMOUTMAX);
    zoomBar.setOnMouseMoved(
        (event) -> {
          zoomBarScrolled(zoomBar.getValue());
        });
    zoomBar.setTranslateX(WIDTH / 2 - (zoomBar.getWidth() + 10));
    zoomBar.setTranslateY(-(HEIGHT / 2 - (zoomBar.getHeight() + 50)));
    return zoomBar;
  }

  // Currently unused because zoomBar is busted
  private void zoomBarScrolled(double value) {
    if (layout.getScaleX() < ZOOMINMAX && layout.getScaleX() > ZOOMOUTMAX) {
      layout.setScaleX(value);
      layout.setScaleY(value);
    }
  }

  // Called when user scrolls in or out on the layout ScrollPane (map)
  private void handleScrollZoom(double scrollVal) {
    // positive scroll values are up and negative scroll values are down
    // TODO add a setting to invert scrolling (this makes sense to me— I usually reverse mine)
    if (scrollVal > 0) {
      zoomIn(ZOOMAMPLIFIER);
    } else {
      zoomOut(ZOOMAMPLIFIER);
    }
  }

  // Catch-all zoomIn method
  private void zoomIn(double amp) {
    amp /= 10; // amp must be low so that the image does not scale too far
    if (layout.getScaleX() < ZOOMINMAX) {
      layout.setScaleX(layout.getScaleX() * (1 + amp));
      layout.setScaleY(layout.getScaleY() * (1 + amp));
    }
  }

  // Catch-all zoomOut method
  private void zoomOut(double amp) {
    amp /= 10; // amp must be low so that the image does not scale too far
    if (layout.getScaleX() > ZOOMOUTMAX) {
      layout.setScaleX(layout.getScaleX() * 1 / (1 + amp));
      layout.setScaleY(layout.getScaleY() * 1 / (1 + amp));
    }
  }
}

package edu.wpi.teame.model;

import static javafx.application.Application.launch;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teame.App;
import edu.wpi.teame.db.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.util.Pair;

/** Constructs a scene with a pannable Map background. */
public class PannableView {

  // Init constants
  private Image backgroundImage;
  private final int WIDTH = 1280;
  private final int HEIGHT = 720;
  private final double ZOOMINMAX = 1.5;
  private final double ZOOMOUTMAX = .2;
  private double MAPIMGWIDTH;
  private double MAPIMGHEIGHT;
  private final double ZOOMAMPLIFIER = .1;

  // Init booleans
  private boolean invertZoomScroll = false;
  private boolean showLocationNodes = false;

  // Init data structures
  private ArrayList<ImageView> hamburgerDeployments = new ArrayList<ImageView>();
  private HashMap<FloorType, ArrayList<MapEquipmentIcon>> mapIconsByFloor = new HashMap<>();
  private HashMap<FloorType, ArrayList<MapLocationDot>> locationsByFloor = new HashMap<>();
  private HashMap<EquipmentType, ImageView> TypeGraphics = new HashMap<EquipmentType, ImageView>();
  private ContextMenu EquipmentMenu;
  private ContextMenu PaneMenu;
  private ContextMenu AddMenu;

  // Keeping track of Events
  private JFXButton lastPressed;
  private double PressX;
  private double PressY;

  // Init panes
  private StackPane layout = new StackPane();

  // Init everything else
  private FloorType currFloor;

  // Constructor sets the background image and currentFloor enum
  public PannableView(FloorType floor) {
    for (FloorType currFloor : FloorType.values()) {
      mapIconsByFloor.put(currFloor, new ArrayList<>());
      locationsByFloor.put(currFloor, new ArrayList<>());
    }
    switchFloors(floor);
  }

  private void switchFloors(FloorType newFloor) {
    backgroundImage = new Image(getImageResource(getMapImg(newFloor)));
    MAPIMGHEIGHT = backgroundImage.getHeight();
    MAPIMGWIDTH = backgroundImage.getWidth();
    currFloor = newFloor;
    updateLayoutChildren();
  }

  // Convert from enum to a background image
  private String getMapImg(FloorType f) {
    switch (f) {
      case GroundFloor:
        return "images/map/00_thegroundfloorNew.png";
      case LowerLevel1:
        return "images/map/00_thelowerlevel1New.png";
      case LowerLevel2:
        return "images/map/00_thelowerlevel2New.png";
      case FirstFloor:
        return "images/map/01_thefirstfloorNew.png";
      case SecondFloor:
        return "images/map/02_thesecondfloorNew.png";
      case ThirdFloor:
        return "images/map/03_thethirdfloorNew.png";
      default:
        return "";
    }
  }

  // Checks if X and Y strings are on the map coordinates;
  private boolean coordinateChecker(String X, String Y) {
    double doubleX = Double.parseDouble(X);
    double doubleY = Double.parseDouble(Y);
    return doubleX > 0 && doubleX < 5000 && doubleY > 0 && doubleY < 3400;
  }

  // Removes map icon if it contains the button passed in
  public void deleteMapIcon(JFXButton button) {
    mapIconsByFloor.get(currFloor).removeIf(mapIcon -> mapIcon.Button.equals(button));
  }

  // This is essentially the Main function
  // getMapScene returns the entire map page
  public Parent getMapScene(double height, double width) {
    TypeGraphics.put(
        EquipmentType.PBED,
        new ImageView(new Image(getImageResource("images/Icons/HospitalBedIcon.png"))));
    TypeGraphics.put(
        EquipmentType.XRAY,
        new ImageView(new Image(getImageResource("images/Icons/XRayIcon.png"))));
    TypeGraphics.put(
        EquipmentType.RECL,
        new ImageView(new Image(getImageResource("images/Icons/ReclinerIcon.png"))));
    TypeGraphics.put(
        EquipmentType.PUMP,
        new ImageView(new Image(getImageResource("images/Icons/PumpIcon.png"))));
    System.out.println("Added Graphics");
    EquipmentMenu = new ContextMenu();
    EquipmentMenu.getStyleClass().add("combo-box");
    PaneMenu = new ContextMenu();
    AddMenu = new ContextMenu();
    MenuItem AddMenuItem1 = new MenuItem("1");
    AddMenu.getItems().add(AddMenuItem1);
    for (EquipmentType currEquipment : EquipmentType.values()) {
      MenuItem currItem = new MenuItem(currEquipment.toString());
      currItem.setOnAction(
          event ->
              addMapIcon(
                  PressX,
                  PressY,
                  getImageViewFromEquipmentType(currEquipment),
                  currEquipment.toString()));
      PaneMenu.getItems().add(currItem);
      PaneMenu.getStyleClass().add("combo-box");
    }

    MenuItem equipmentItem1 = new MenuItem("Delete");
    equipmentItem1.setOnAction(
        event -> {
          //            lastPressed.setVisible(false);
          //            lastPressed.setDisable(false);
          deleteMapIcon(lastPressed);
          updateLayoutChildren();
        });
    MenuItem equipmentItem2 = new MenuItem("Edit");
    equipmentItem2.setOnAction(
        event -> {
          Dialog<Pair<Double, Double>> dialog = new Dialog<>();
          dialog.setTitle("Move Equipment");
          dialog.setHeaderText("Choose the X and Y Position");
          // Set the button types.
          ButtonType Move = new ButtonType("Move", ButtonBar.ButtonData.OK_DONE);
          dialog.getDialogPane().getButtonTypes().addAll(Move, ButtonType.CANCEL);

          // Create the username and password labels and fields.
          GridPane grid = new GridPane();
          grid.setHgap(10);
          grid.setVgap(10);
          grid.setPadding(new Insets(20, 150, 10, 10));

          TextField XPosition = new TextField();
          XPosition.setPromptText("X Position");
          TextField YPosition = new TextField();
          YPosition.setPromptText("Y Position");

          grid.add(new Label("X Position:"), 0, 0);
          grid.add(XPosition, 1, 0);
          grid.add(new Label("Y Position:"), 0, 1);
          grid.add(YPosition, 1, 1);

          // Enable/Disable login button depending on whether a username was entered.
          Node MoveButton = dialog.getDialogPane().lookupButton(Move);
          MoveButton.setDisable(true);

          // Do some validation (using the Java 8 lambda syntax).
          XPosition.textProperty()
              .addListener(
                  (observable, oldValue, newValue) -> {
                    MoveButton.setDisable(
                        newValue.trim().isEmpty()
                            || YPosition.getText().isBlank()
                            || !coordinateChecker(XPosition.getText(), YPosition.getText()));
                  });
          YPosition.textProperty()
              .addListener(
                  (observable, oldValue, newValue) -> {
                    MoveButton.setDisable(
                        newValue.trim().isEmpty()
                            || XPosition.getText().isBlank()
                            || !coordinateChecker(XPosition.getText(), YPosition.getText()));
                  });

          dialog.getDialogPane().setContent(grid);

          // Convert the result to a username-password-pair when the login button is clicked.
          dialog.setResultConverter(
              dialogButton -> {
                if (dialogButton == Move) {
                  double xCo = Double.parseDouble(XPosition.getText());
                  double yCo = Double.parseDouble(YPosition.getText());
                  double x = xCo - MAPIMGWIDTH / 2;
                  double y = yCo - MAPIMGHEIGHT / 2;
                  lastPressed.setTranslateX(x);
                  lastPressed.setTranslateY(y);
                }
                return null;
              });
          dialog.showAndWait();
        });
    EquipmentMenu.getItems().addAll(equipmentItem1, equipmentItem2);
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
        .setAll(scroll, createZoomInButton(), createZoomOutButton(), createFloorSwitcher());
    staticWrapper.getChildren().addAll(createFilterCheckBoxes());
    for (ImageView imageView : hamburgerDeployments) {
      staticWrapper.getChildren().add(imageView);
    }

    scroll.setPrefSize(width, height);
    scroll.setHvalue(scroll.getHmin() + (scroll.getHmax() - scroll.getHmin()) / 2);
    scroll.setVvalue(scroll.getVmin() + (scroll.getVmax() - scroll.getVmin()) / 2);

    layout.setOnMouseReleased(
        event -> {
          if (event.getButton() == MouseButton.SECONDARY) {
            PressX = event.getX();
            PressY = event.getY();
            scroll.setContextMenu(PaneMenu);
            PaneMenu.show(scroll, event.getScreenX(), event.getScreenY());
          }
        });
    return staticWrapper;
  }

  // Must be called whenever an icon is added to the map
  private void updateLayoutChildren() {
    layout.getChildren().setAll(new ImageView(backgroundImage));
    for (MapEquipmentIcon icon : mapIconsByFloor.get(currFloor)) {
      layout.getChildren().add(icon.getButton());
    }
    for (MapLocationDot dot : locationsByFloor.get(currFloor)) {
      layout.getChildren().add(dot.getImageView());
    }
  }

  private void addMapIcon(
      double xCoordinate,
      double yCoordinate,
      ImageView image,
      String toolTip,
      FloorType floor,
      Equipment equipment) {
    image.setFitWidth(30);
    image.setFitHeight(30);
    final JFXButton newButton = new JFXButton();
    newButton.setGraphic(image);
    double x = xCoordinate - MAPIMGWIDTH / 2;
    double y = yCoordinate - MAPIMGHEIGHT / 2;
    newButton.setTranslateX(x);
    newButton.setTranslateY(y);
    newButton.setOpacity(1);
    newButton.setContextMenu(EquipmentMenu);
    newButton.setOnMouseReleased(
        event -> {
          if (event.getButton() == MouseButton.SECONDARY) {
            lastPressed = newButton;
            newButton.getContextMenu().show(newButton, event.getScreenX(), event.getScreenY());
          }
        });
    Tooltip tooltip = new Tooltip(toolTip);
    Tooltip.install(newButton, tooltip);
    MapEquipmentIcon newIcon = new MapEquipmentIcon(newButton, toolTip, equipment);
    mapIconsByFloor.get(floor).add(newIcon);
    draggable(newIcon);
    updateLayoutChildren();
  }
  // Show all of the Equipment types passed in
  private void filter(EquipmentType e) {
    for (MapEquipmentIcon mapIcon : mapIconsByFloor.get(currFloor)) {
      if (mapIcon.equipment.getType() == e) {
        mapIcon.getButton().setVisible(!mapIcon.getButton().isVisible());
      }
    }
  }
  // Adds icon on click
  // TODO make this meaningful. Right now it just makes a button with whatever icon you tell it to
  // make
  private void addMapIcon(double xCoordinate, double yCoordinate, ImageView image, String toolTip) {
    image.setFitWidth(30);
    image.setFitHeight(30);
    final JFXButton newButton = new JFXButton();
    newButton.setGraphic(image);
    double x = xCoordinate - MAPIMGWIDTH / 2;
    double y = yCoordinate - MAPIMGHEIGHT / 2;
    newButton.setTranslateX(x);
    newButton.setTranslateY(y);
    newButton.setOpacity(1);
    newButton.setContextMenu(EquipmentMenu);
    newButton.setOnMouseReleased(
        event -> {
          if (event.getButton() == MouseButton.SECONDARY) {
            lastPressed = newButton;
            newButton.getContextMenu().show(newButton, event.getScreenX(), event.getScreenY());
          }
        });
    Tooltip tooltip = new Tooltip(toolTip);
    Tooltip.install(newButton, tooltip);
    // TODO new MapEquipment Icon add equipment to parameter
    MapEquipmentIcon newIcon = new MapEquipmentIcon(newButton, toolTip);
    draggable(newIcon);
    mapIconsByFloor.get(currFloor).add(newIcon);
    updateLayoutChildren();
  }

  private ArrayList<JFXCheckBox> createFilterCheckBoxes() {
    ArrayList<JFXCheckBox> retval = new ArrayList<>();

    for (EquipmentType currEquipment : EquipmentType.values()) {
      JFXCheckBox equipmentCheckBox = new JFXCheckBox(currEquipment.toString());
      equipmentCheckBox.getStyleClass().add("combo-box");

      equipmentCheckBox.setSelected(true);
      equipmentCheckBox.setOnAction(event -> filter(currEquipment));
      retval.add(equipmentCheckBox);

      equipmentCheckBox.setTranslateY(
          -30 * currEquipment.ordinal() - Screen.getPrimary().getVisualBounds().getHeight() / 2.8);
      equipmentCheckBox.setTranslateX(-Screen.getPrimary().getVisualBounds().getHeight() / 3);
    }

    JFXCheckBox locationsCheckBox = new JFXCheckBox("Location Dots");
    locationsCheckBox.getStyleClass().add("combo-box");
    locationsCheckBox.setSelected(false);
    locationsCheckBox.setOnAction(
        event -> {
          for (MapLocationDot dot : locationsByFloor.get(currFloor)) {
            dot.getImageView().setVisible(!dot.getImageView().isVisible());
          }
          showLocationNodes = !showLocationNodes;
        });
    locationsCheckBox.setTranslateY(
        -30 * retval.size() - Screen.getPrimary().getVisualBounds().getHeight() / 2.8);
    locationsCheckBox.setTranslateX(-Screen.getPrimary().getVisualBounds().getHeight() / 3);
    retval.add(locationsCheckBox);

    return retval;
  }
  // Init zoomInButton
  private JFXButton createZoomInButton() {
    Image zoomIcon = new Image(getImageResource("images/Icons/ZoomIn.png"));
    ImageView icon = new ImageView(zoomIcon);
    icon.setFitWidth(30);
    icon.setFitHeight(30);
    final JFXButton zoomInButton = new JFXButton("", icon);
    zoomInButton.setTranslateX(Screen.getPrimary().getVisualBounds().getHeight() / 1.45);
    zoomInButton.setTranslateY(-Screen.getPrimary().getVisualBounds().getHeight() / 2 + 60);
    zoomInButton.setOnAction(
        (event) -> {
          // double value zoomAmplifier is 1 for buttons
          zoomIn(1);
        });
    return zoomInButton;
  }

  // init zoomOutButton
  private JFXButton createZoomOutButton() {
    Image zoomIcon = new Image(getImageResource("images/Icons/ZoomOut.png"));
    ImageView icon = new ImageView(zoomIcon);
    icon.setFitWidth(30);
    icon.setFitHeight(30);
    final JFXButton zoomOutButton = new JFXButton("", icon);
    zoomOutButton.setTranslateX(Screen.getPrimary().getVisualBounds().getHeight() / 1.45);
    zoomOutButton.setTranslateY(-Screen.getPrimary().getVisualBounds().getHeight() / 2 + 10);
    zoomOutButton.setOnAction(
        (event) -> {
          // double value zoomAmplifier is 1 for buttons
          zoomOut(1);
        });
    return zoomOutButton;
  }

  // init ComboBox
  private JFXComboBox<String> createFloorSwitcher() {
    final JFXComboBox<String> comboBox = new JFXComboBox<>();
    for (FloorType floorType : FloorType.values()) {
      comboBox.getItems().add(floorType.toString());
    }
    comboBox.setTranslateX(-Screen.getPrimary().getVisualBounds().getHeight() / 2);
    comboBox.setTranslateY(-Screen.getPrimary().getVisualBounds().getHeight() / 2 + 10);
    comboBox.setFocusColor(Color.rgb(0, 0, 255));
    comboBox.setOnAction(event -> switchFloors(currFloor.getFloorFromString(comboBox.getValue())));
    return comboBox;
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

  private ImageView getImageViewFromEquipmentType(EquipmentType t) {
    ImageView equipmentIcon = new ImageView();
    equipmentIcon.setFitHeight(30);
    equipmentIcon.setFitWidth(30);
    String png;
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
    Image i = new Image(getImageResource("images/Icons/" + png));
    equipmentIcon.setImage(i);
    return equipmentIcon;
  }

  public void convertEquipmentToMapIcon(Equipment equip) {
    addMapIcon(
        equip.getLocationNode().getX(),
        equip.getLocationNode().getY(),
        getImageViewFromEquipmentType(equip.getType()),
        equip.getName(),
        equip.getLocationNode().getFloor(),
        equip);
  }

  public void getFromDB() {
    LinkedList<Equipment> equipment = DBManager.getInstance().getEquipmentManager().getAll();
    mapIconsByFloor.get(currFloor).clear();
    locationsByFloor.get(currFloor).clear();
    for (Equipment currEquipment : equipment) {
      convertEquipmentToMapIcon(currEquipment);
    }
    LinkedList<Location> locations = DBManager.getInstance().getLocationManager().getAll();
    for (Location currLocation : locations) {
      locationToMapElement(currLocation);
    }
  }

  public static void main(String[] args) {
    launch(args);
  }

  // Called when user scrolls in or out on the layout ScrollPane (map)
  private void handleScrollZoom(double scrollVal) {
    // positive scroll values are up and negative scroll values are down
    if (invertZoomScroll) {
      if (scrollVal > 0) {
        zoomOut(ZOOMAMPLIFIER);
      } else {
        zoomIn(ZOOMAMPLIFIER);
      }
    } else {
      if (scrollVal > 0) {
        zoomIn(ZOOMAMPLIFIER);
      } else {
        zoomOut(ZOOMAMPLIFIER);
      }
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

  // Used in draggable() for tracking start and end locations of a node
  private static class Position {
    double x;
    double y;
  }

  private void draggable(MapEquipmentIcon i) {
    JFXButton node = i.getButton();
    final Position startingPosition = new Position();

    // Prompt the user that the node can be clicked
    node.addEventHandler(
        MouseEvent.MOUSE_ENTERED,
        event -> {
          node.setCursor(Cursor.HAND);
        });
    node.addEventHandler(
        MouseEvent.MOUSE_EXITED,
        event -> {
          node.setCursor(Cursor.DEFAULT);
        });

    // Prompt the user that the node can be dragged
    node.addEventHandler(
        MouseEvent.MOUSE_PRESSED,
        event -> {
          if (event.getButton() == MouseButton.PRIMARY) {
            System.out.println("Started");
            for (MapLocationDot dot : locationsByFloor.get(currFloor)) {
              dot.getImageView().setVisible(true);
            }
            node.setCursor(Cursor.MOVE);
            // When a press event occurs, the location coordinates of the event are cached
            startingPosition.x = node.getTranslateX();
            startingPosition.y = node.getTranslateY();
            System.out.println(startingPosition.x + " " + startingPosition.y);
            System.out.println(startingPosition.x + " " + startingPosition.y);
          }
        });
    node.addEventHandler(
        MouseEvent.MOUSE_RELEASED,
        event -> {
          Point2D updatedLocation =
              layout.sceneToLocal(new Point2D(event.getSceneX(), event.getSceneY()));
          Location nearestLocation =
              getClosestLocation(updatedLocation.getX(), updatedLocation.getY());
          if (nearestLocation == null) {
            System.out.println("No node close enough to snap.");
            node.setTranslateX(startingPosition.x);
            node.setTranslateY(startingPosition.y);
          } else {
            System.out.println("Snapping to nearest node.");
            node.setTranslateX(nearestLocation.getX() - MAPIMGWIDTH / 2);
            node.setTranslateY(nearestLocation.getY() - MAPIMGHEIGHT / 2);
            i.getEquipment().setLocationNode(nearestLocation);
            System.out.println("Equipment location node updated.");
          }
          System.out.println("Done");
          node.setCursor(Cursor.DEFAULT);

          if (!showLocationNodes) {
            System.out.println("Hiding location nodes.");
            for (MapLocationDot dot : locationsByFloor.get(currFloor)) {
              dot.getImageView().setVisible(false);
            }
          }

          updateLayoutChildren();
        });

    // Realize drag and drop function
    node.addEventHandler(
        MouseEvent.MOUSE_DRAGGED,
        event -> {
          if (event.getButton() == MouseButton.PRIMARY) {

            Point2D updatedLocation =
                layout.sceneToLocal(new Point2D(event.getSceneX(), event.getSceneY()));
            double x = updatedLocation.getX() - MAPIMGWIDTH / 2;
            double y = updatedLocation.getY() - MAPIMGHEIGHT / 2;
            node.setTranslateX(x);
            node.setTranslateY(y);
          }
        });
  }

  public void setInvertZoomScroll(boolean value) {
    invertZoomScroll = value;
  }

  public String getImageResource(String filePath) {
    return Objects.requireNonNull(App.class.getResource(filePath)).toString();
  }

  private void locationToMapElement(Location location) {
    ImageView locationDot = new ImageView();
    locationDot.setImage(new Image(getImageResource("images/Icons/LocationDot.png")));
    locationDot.setFitWidth(10);
    locationDot.setFitHeight(10);
    double x = location.getX() - MAPIMGWIDTH / 2;
    double y = location.getY() - MAPIMGHEIGHT / 2;
    locationDot.setTranslateX(x);
    locationDot.setTranslateY(y);
    locationDot.setVisible(false);
    MapLocationDot newDot = new MapLocationDot(locationDot, location);
    locationsByFloor.get(location.getFloor()).add(newDot);
    Tooltip t = new Tooltip(location.getLongName());
    Tooltip.install(locationDot, t);
    updateLayoutChildren();
  }

  private Location getClosestLocation(double x, double y) {
    Location closestLocation = null;
    double closestDistance = Double.MAX_VALUE;
    for (MapLocationDot dot : locationsByFloor.get(currFloor)) {
      Location l = dot.getLocation();
      double d = dot.getDistanceToLocation(x, y);
      System.out.println("Testing location: " + dot.getLocation().getShortName());
      if (d < closestDistance) {
        System.out.println("Found new closest location.");
        closestDistance = d;
        closestLocation = l;
      }
    }
    if (closestDistance > 50) {
      System.out.println("Not close enough to snap.");
      return null;
    }
    System.out.println("Closest Location:");
    System.out.println(closestLocation.getLongName());
    System.out.println(closestDistance);
    return closestLocation;
  }
}

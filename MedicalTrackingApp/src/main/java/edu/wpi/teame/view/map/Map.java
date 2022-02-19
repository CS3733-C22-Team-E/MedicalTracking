package edu.wpi.teame.view.map;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teame.App;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Equipment;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.EquipmentType;
import edu.wpi.teame.model.enums.FloorType;
import edu.wpi.teame.model.enums.ServiceRequestPriority;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import edu.wpi.teame.model.serviceRequests.ServiceRequest;
import edu.wpi.teame.view.controllers.LandingPageController;
import edu.wpi.teame.view.controllers.ServiceRequestDirectoryPageController;
import edu.wpi.teame.view.controllers.serviceRequests.MedicalEquipmentDeliveryServiceRequestPageServiceRequestController;
import edu.wpi.teame.view.map.Astar.AstarVisualizer;
import edu.wpi.teame.view.map.Astar.MapIntegration.PathFinder;
import edu.wpi.teame.view.map.Icons.MapEquipmentIcon;
import edu.wpi.teame.view.map.Icons.MapLocationDot;
import edu.wpi.teame.view.map.Icons.MapServiceRequestIcon;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.util.Pair;

public class Map {
  private final HashMap<FloorType, Image> Images = new HashMap<>();
  private final int WIDTH = 0;
  private final int HEIGHT = 0;
  private final double ZOOMINMAX = 1.5;
  private final double ZOOMOUTMAX = .2;
  private final HashMap<FloorType, ArrayList<MapEquipmentIcon>> mapIconsByFloor = new HashMap<>();
  private final HashMap<FloorType, ArrayList<MapLocationDot>> locationsByFloor = new HashMap<>();
  private final HashMap<FloorType, ArrayList<RadialEquipmentMenu>> radialMenusByFloor =
      new HashMap<>();
  private final HashMap<EquipmentType, Image> TypeGraphics = new HashMap<EquipmentType, Image>();
  private final HashMap<FloorType, ArrayList<MapServiceRequestIcon>> ActiveSRByFloor =
      new HashMap<>();
  private final ContextMenu EquipmentClicked = new ContextMenu();
  private final ContextMenu PaneMenu = new ContextMenu();
  private final StackPane layout = new StackPane();
  private final LandingPageController appController;
  private Image backgroundImage;
  private double MAPHEIGHT = 0;
  private double MAPWIDTH = 0;
  private boolean showLocationNodes = false;
  private JFXButton lastPressed;
  private Point2D lastPressedPoint = new Point2D(0, 0);
  private Location lastPressedLocation;
  private Location location;
  private FloorType currFloor;
  private ArrayList<ServiceRequest> oldSR = new ArrayList<ServiceRequest>();
  private PathFinder Pather;
  private ArrayList<Location> PathFinding = new ArrayList<>();

  public Map(FloorType floor, LandingPageController app) {
    appController = app;
    currFloor = floor;
    for (FloorType currFloor : FloorType.values()) {
      Images.put(currFloor, new Image(getImageResource(getMapImg(currFloor))));
      mapIconsByFloor.put(currFloor, new ArrayList<>());
      locationsByFloor.put(currFloor, new ArrayList<>());
      radialMenusByFloor.put(currFloor, new ArrayList<>());
      ActiveSRByFloor.put(currFloor, new ArrayList<>());
    }
    System.out.println("Loaded Maps");
    switchFloors(floor);
  }

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

  private void switchFloors(FloorType floor) {
    currFloor = floor;
    backgroundImage = Images.get(floor);
    MAPHEIGHT = backgroundImage.getHeight();
    MAPWIDTH = backgroundImage.getWidth();
    updateLayoutChildren();
    AstarVisualizer newVisualizer = new AstarVisualizer(layout);
    newVisualizer.setMap(MAPWIDTH, MAPHEIGHT);
    // newVisualizer.createConnection(new Point2D(1865, 1108), new Point2D(2213, 1247));
  }

  private boolean coordinateChecker(String X, String Y) {
    double doubleX = Double.parseDouble(X);
    double doubleY = Double.parseDouble(Y);
    return doubleX > 0 && doubleX < MAPWIDTH && doubleY > 0 && doubleY < MAPHEIGHT;
  }

  public void deleteMapIcon(JFXButton button) {
    mapIconsByFloor.get(currFloor).removeIf(mapIcon -> mapIcon.Button.equals(button));
  }

  public MenuItem createEquipmentMenuItem(EquipmentType equiptype) {
    MenuItem retval = new MenuItem(equiptype.toString());
    retval.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            Equipment ToBeInserted =
                new Equipment(0, lastPressedLocation, equiptype, equiptype.toString(), false, true);
            try {
              DBManager.getInstance().getEquipmentManager().insert(ToBeInserted);
              addEquipmentToMap(ToBeInserted);
            } catch (SQLException e) {
              e.printStackTrace();
            }
          }
        });
    return retval;
  }

  public void showEditLastPressedDialog() {
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
            double x = xCo - MAPWIDTH / 2;
            double y = yCo - MAPHEIGHT / 2;
            lastPressed.setTranslateX(x);
            lastPressed.setTranslateY(y);
          }
          return null;
        });
    dialog.showAndWait();
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
    for (MapServiceRequestIcon icon : ActiveSRByFloor.get(currFloor)) {
      layout.getChildren().addAll(icon.progressIndicator, icon.Icon);
    }
    updateRadialMenus();
    createNewRadialMenus();
    for (RadialEquipmentMenu rm : radialMenusByFloor.get(currFloor)) {
      layout.getChildren().add(rm.getButton());
    }
    showEquipmentRemovedFromRadialMenus();
  }

  // Init ScrollPane that holds the StackPane containing map and all Icons
  private ZoomableScrollPane createScrollPane(Pane layout) {
    ZoomableScrollPane scroll = new ZoomableScrollPane(layout);
    scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scroll.setPannable(true);
    scroll.setPrefSize(WIDTH, HEIGHT);
    return scroll;
  }

  // init ComboBox
  private JFXComboBox<String> createFloorSwitcher() {
    final JFXComboBox<String> comboBox = new JFXComboBox<>();
    comboBox.setValue(currFloor.toString());
    for (FloorType floorType : FloorType.values()) {
      comboBox.getItems().add(floorType.toString());
    }
    comboBox.setTranslateX(Screen.getPrimary().getVisualBounds().getHeight() / 2.8);
    comboBox.setTranslateY(-Screen.getPrimary().getVisualBounds().getHeight() / 2 + 10);
    comboBox.setFocusColor(Color.rgb(0, 0, 255));
    comboBox.setOnAction(event -> switchFloors(FloorType.valueOf(comboBox.getValue())));
    return comboBox;
  }

  public String getImageResource(String filePath) {
    return Objects.requireNonNull(App.class.getResource(filePath)).toString();
  }

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
          try {
            zoomIn(1);
          } catch (SQLException e) {
            e.printStackTrace();
          }
        });
    return zoomInButton;
  }

  // Show all of the Equipment types passed in
  private void filter(EquipmentType e) {
    for (MapEquipmentIcon mapIcon : mapIconsByFloor.get(currFloor)) {
      if (mapIcon.equipment.getType() == e) {
        mapIcon.getButton().setVisible(!mapIcon.getButton().isVisible());
      }
    }
  }

  private ArrayList<JFXCheckBox> createFilterCheckBoxes() {
    ArrayList<JFXCheckBox> retval = new ArrayList<>();

    for (EquipmentType currEquipment : EquipmentType.values()) {
      JFXCheckBox equipmentCheckBox = new JFXCheckBox(currEquipment.toString());
      equipmentCheckBox.getStyleClass().add("combo-box");

      equipmentCheckBox.setSelected(true);
      equipmentCheckBox.setOnAction(event -> filter(currEquipment));
      retval.add(equipmentCheckBox);
      equipmentCheckBox.setPrefWidth(30);
      equipmentCheckBox.setPrefHeight(30);
      equipmentCheckBox.setTranslateY(
          -30 * currEquipment.ordinal() - Screen.getPrimary().getVisualBounds().getHeight() / 2.8);
      equipmentCheckBox.setTranslateX(Screen.getPrimary().getVisualBounds().getWidth() / 3.4);
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
    locationsCheckBox.setPrefHeight(30);
    locationsCheckBox.setPrefWidth(30);
    locationsCheckBox.setTranslateY(
        -30 * retval.size() - Screen.getPrimary().getVisualBounds().getHeight() / 2.8);
    locationsCheckBox.setTranslateX(Screen.getPrimary().getVisualBounds().getWidth() / 3.4);
    retval.add(locationsCheckBox);

    return retval;
  }

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
          try {
            Pather.FindAndDrawRoute(97, 89);
          } catch (SQLException e) {
            e.printStackTrace();
          }
          try {
            RefreshSRfromDB();
          } catch (SQLException e) {
            e.printStackTrace();
          }
        });
    return zoomOutButton;
  }

  // Catch-all zoomOut method
  private void zoomOut(double amp) {
    amp /= 10; // amp must be low so that the image does not scale too far
    if (layout.getScaleX() > ZOOMOUTMAX) {
      layout.setScaleX(layout.getScaleX() * 1 / (1 + amp));
      layout.setScaleY(layout.getScaleY() * 1 / (1 + amp));
    }
  }

  // Catch-all zoomIn method
  private void zoomIn(double amp) throws SQLException {
    amp /= 10; // amp must be low so that the image does not scale too far
    if (layout.getScaleX() < ZOOMINMAX) {
      layout.setScaleX(layout.getScaleX() * (1 + amp));
      layout.setScaleY(layout.getScaleY() * (1 + amp));
    }
  }

  public Parent getMapScene(double height, double width) throws SQLException {
    // Load Icon Graphics
    for (EquipmentType currEquip : EquipmentType.values()) {
      TypeGraphics.put(
          currEquip,
          new Image(
              App.class.getResource(currEquip.getResource()).toString(),
              20.0,
              20.0,
              true,
              true,
              true));
    }
    System.out.println("Icons Graphics Load");

    // Creating OnClickPane Menu
    PaneMenu.getStyleClass().add("combo-box");
    for (EquipmentType currEquipment : EquipmentType.values()) {
      PaneMenu.getItems().add(createEquipmentMenuItem(currEquipment));
    }
    // Creating Equipment Icon Pressed
    EquipmentClicked.getStyleClass().add("combo-box");
    MenuItem deleteMenuItem = new MenuItem("Delete");
    deleteMenuItem.setOnAction(
        event -> {
          deleteMapIcon(lastPressed);
          updateLayoutChildren();
        });
    MenuItem addMenuItem = new MenuItem("Edit");
    addMenuItem.setOnAction(
        event -> {
          showEditLastPressedDialog();
        });
    EquipmentClicked.getItems().addAll(deleteMenuItem, addMenuItem);
    updateLayoutChildren();
    layout.setScaleX(.5);
    layout.setScaleY(.5);
    // TODO
    ZoomableScrollPane scroll = createScrollPane(layout);
    StackPane staticWrapper = new StackPane();
    layout.setOnScroll(
        new EventHandler<ScrollEvent>() {
          @Override
          public void handle(ScrollEvent event) {
            scroll.zoomNode.fireEvent(event);
          }
        });
    staticWrapper
        .getChildren()
        .setAll(scroll, createZoomInButton(), createZoomOutButton(), createFloorSwitcher());
    staticWrapper.getChildren().addAll(createFilterCheckBoxes());
    // setting size of scroll pane and setting the bar values
    scroll.setPrefSize(width, height);
    scroll.setHvalue(scroll.getHmin() + (scroll.getHmax() - scroll.getHmin()) / 2);
    scroll.setVvalue(scroll.getVmin() + (scroll.getVmax() - scroll.getVmin()) / 2);
    layout.setOnMouseReleased(
        event -> {
          if (event.getButton() == MouseButton.SECONDARY) {
            // In Pixel Coordinates
            lastPressedPoint = layout.sceneToLocal(event.getSceneX(), event.getSceneY());
            scroll.setContextMenu(PaneMenu);
            PaneMenu.show(scroll, event.getScreenX(), event.getScreenY());
          }
        });
    layout.setOnMouseMoved(this::closeRadialMenus);
    Pather = new PathFinder(layout);
    System.out.println("Init Complete");
    return staticWrapper;
  }

  private MapEquipmentIcon addEquipmentToMap(Equipment equipment) {
    final JFXButton Icon = new JFXButton(); // Create new button
    Icon.setOpacity(.85);
    ImageView newGraphic = new ImageView();
    newGraphic.setImage(TypeGraphics.get(equipment.getType()));

    Icon.setGraphic(newGraphic); // Set Graphic to Equipment Type
    Icon.setTranslateX(
        equipment.getLocation().getX()
            - MAPWIDTH / 2); // Convert to Translate Coordinates and set X
    Icon.setTranslateY(equipment.getLocation().getY() - MAPHEIGHT / 2); // ^^ with y
    // Set Context Menu
    Icon.setContextMenu(EquipmentClicked);
    // Set Context Menu to Show
    Icon.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            // Set last pressed button
            lastPressed = Icon;
            Icon.getContextMenu().show(Icon, event.getScreenX(), event.getScreenY());
          }
        });
    Tooltip tooltip = new Tooltip(equipment.getName());
    Tooltip.install(Icon, tooltip);
    MapEquipmentIcon newMapIcon = new MapEquipmentIcon(Icon, equipment);
    draggable(newMapIcon);
    mapIconsByFloor.get(equipment.getLocation().getFloor()).add(newMapIcon);
    updateLayoutChildren();
    return newMapIcon;
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
            System.out.println("Started drag");
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
            node.setTranslateX(nearestLocation.getX() - MAPWIDTH / 2);
            node.setTranslateY(nearestLocation.getY() - MAPHEIGHT / 2);
            i.getEquipment().setLocation(nearestLocation);

            try {
              DBManager.getInstance().getEquipmentManager().update(i.getEquipment());
            } catch (SQLException e) {
              e.printStackTrace();
            }

            MedicalEquipmentDeliveryServiceRequestPageServiceRequestController
                medicalEquipmentSRController =
                    (MedicalEquipmentDeliveryServiceRequestPageServiceRequestController)
                        ServiceRequestDirectoryPageController.medicalEquipmentSRTab.controller;

            medicalEquipmentSRController.equipment.setText(i.getEquipment().getName());
            medicalEquipmentSRController.requestDate.setValue(LocalDate.now());
            medicalEquipmentSRController.locationText.setText(
                i.getEquipment().getLocation().getLongName());
            medicalEquipmentSRController.status.setValue(ServiceRequestStatus.OPEN);
            medicalEquipmentSRController.priority.setValue(ServiceRequestPriority.Normal);

            System.out.println("Done");
            node.setCursor(Cursor.DEFAULT);

            if (!showLocationNodes) {
              System.out.println("Hiding location nodes.");
              for (MapLocationDot dot : locationsByFloor.get(currFloor)) {
                dot.getImageView().setVisible(false);
              }
            }
            updateLayoutChildren();

            for (RadialEquipmentMenu rm : radialMenusByFloor.get(currFloor)) {
              rm.hide();
            }
          }
        });

    // Realize drag and drop function
    node.addEventHandler(
        MouseEvent.MOUSE_DRAGGED,
        event -> {
          if (event.getButton() == MouseButton.PRIMARY) {
            Point2D updatedLocation =
                layout.sceneToLocal(new Point2D(event.getSceneX(), event.getSceneY()));
            double x = updatedLocation.getX() - MAPWIDTH / 2;
            double y = updatedLocation.getY() - MAPHEIGHT / 2;
            node.setTranslateX(x);
            node.setTranslateY(y);
          }
        });
  }

  private Location getClosestLocation(double x, double y) {
    Location closestLocation = null;
    double closestDistance = Double.MAX_VALUE;
    for (MapLocationDot dot : locationsByFloor.get(currFloor)) {
      Location l = dot.getLocation();
      double d = dot.getDistanceToLocation(x, y);
      if (d < closestDistance) {
        closestDistance = d;
        closestLocation = l;
      }
    }
    if (closestDistance > 50) {
      return null;
    }
    return closestLocation;
  }

  public void getFromDB() throws SQLException {
    List<Equipment> equipment = DBManager.getInstance().getEquipmentManager().getAll();
    mapIconsByFloor.get(currFloor).clear();
    locationsByFloor.get(currFloor).clear();
    for (Equipment currEquipment : equipment) {
      addEquipmentToMap(currEquipment);
    }

    List<Location> locations = DBManager.getInstance().getLocationManager().getAll();
    for (Location currLocation : locations) {
      locationToMapElement(currLocation);
    }
    Pather.SelectFloor(FloorType.ThirdFloor);
  }

  public void RefreshSRfromDB() throws SQLException {
    ArrayList<ServiceRequest> serviceRequestsFromDB = new ArrayList<>();
    serviceRequestsFromDB.addAll(DBManager.getInstance().getSanitationSRManager().getAll());
    serviceRequestsFromDB.addAll(DBManager.getInstance().getSecuritySRManager().getAll());
    serviceRequestsFromDB.addAll(DBManager.getInstance().getMedicineDeliverySRManager().getAll());
    serviceRequestsFromDB.addAll(DBManager.getInstance().getMedicalEquipmentSRManager().getAll());
    serviceRequestsFromDB.stream()
        .forEach(
            serviceRequest -> {
              if (oldSR.contains(serviceRequest)) {
                serviceRequestsFromDB.remove(serviceRequest);
              } else {
                oldSR.add(serviceRequest);
                ServiceRequestToMapElement(serviceRequest);
              }
            });
  }

  private void locationToMapElement(Location location) {
    ImageView locationDot = new ImageView();
    locationDot.setImage(new Image(getImageResource("images/Icons/LocationDot.png")));
    locationDot.setFitWidth(10);
    locationDot.setFitHeight(10);
    double x = location.getX() - MAPWIDTH / 2;
    double y = location.getY() - MAPHEIGHT / 2;
    locationDot.setTranslateX(x);
    locationDot.setTranslateY(y);
    locationDot.setVisible(false);
    MapLocationDot newDot = new MapLocationDot(locationDot, location);
    locationsByFloor.get(location.getFloor()).add(newDot);
    Tooltip t = new Tooltip(location.getLongName());
    Tooltip.install(locationDot, t);
    updateLayoutChildren();
    locationDot.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            if (event.getButton() == MouseButton.SECONDARY) {
              lastPressedLocation = location;
              PaneMenu.show(locationDot, event.getScreenX(), event.getScreenY());
            }
            else{

            }
          }
        });
  }

  private void ServiceRequestToMapElement(ServiceRequest SR) {
    double X = SR.getLocation().getX() - MAPWIDTH / 2;
    double Y = SR.getLocation().getY() - MAPHEIGHT / 2;
    MapServiceRequestIcon newIcon = new MapServiceRequestIcon(SR, X, Y);
    newIcon.addToList(ActiveSRByFloor.get(currFloor));
    newIcon.startTimer(20);
    updateLayoutChildren();
  }

  private void createNewRadialMenus() {
    System.out.println("Creating radial menus...");
    for (MapLocationDot dot : locationsByFloor.get(currFloor)) {
      List<MapEquipmentIcon> mapEquipmentIconsAtLocation = new LinkedList<>();
      for (MapEquipmentIcon i : mapIconsByFloor.get(currFloor)) {
        Equipment e = i.getEquipment();
        if (e.getLocation().equalsByName(dot.getLocation())) {
          mapEquipmentIconsAtLocation.add(i);
        }
      }

      // If there's more than 1 equipment per location, create a radial menu.
      if (mapEquipmentIconsAtLocation.size() > 1) {
        System.out.println("Equipment sharing location!");
        RadialEquipmentMenu r = new RadialEquipmentMenu(mapEquipmentIconsAtLocation);
        // Make sure we don't already have this radial menu:
        boolean found = false;
        for (RadialEquipmentMenu rm : radialMenusByFloor.get(currFloor)) {
          if (r.toString().equals(rm.toString())) {
            found = true;
          }
        }
        if (!found) {
          System.out.println("New radial menu detected— adding to HashMap...");
          radialMenusByFloor.get(currFloor).add(r);
        }
      }
    }

    // Put on map
    for (RadialEquipmentMenu rm : radialMenusByFloor.get(currFloor)) {
      rm.hideIndividualIcons();
      rm.place(MAPWIDTH, MAPHEIGHT);
    }
  }

  private void closeRadialMenus(MouseEvent e) {
    for (RadialEquipmentMenu rm : radialMenusByFloor.get(currFloor)) {
      Point2D mouseLocation = layout.sceneToLocal(new Point2D(e.getSceneX(), e.getSceneY()));
      double distance = rm.getDistanceToCoordinate(mouseLocation.getX(), mouseLocation.getY());
      if (distance > rm.getRadius()) {
        rm.hide();
      }
    }
  }

  private void updateRadialMenus() {
    for (RadialEquipmentMenu rm : radialMenusByFloor.get(currFloor)) {
      if (!rm.getIcons().isEmpty()) {
        for (MapEquipmentIcon i : rm.getIcons()) {
          if (!i.getEquipment().getLocation().equalsByName(rm.getLocation())) {
            rm.removeEquipmentIcon(i);
          }
        }
      }

      if (rm.getIcons().size() < 2) {
        for (MapEquipmentIcon i : rm.getIcons()) {
          i.getButton().setVisible(true);
        }
        radialMenusByFloor.get(currFloor).remove(rm);
        rm.kill();
      }
    }
  }

  // This is the longest method name I will tolerate. Do not do this.
  private void showEquipmentRemovedFromRadialMenus() {
    LinkedList<MapEquipmentIcon> iconsInMenus = new LinkedList<>();
    for (RadialEquipmentMenu rm : radialMenusByFloor.get(currFloor)) {
      rm.getButton().setVisible(true);
      iconsInMenus.addAll(rm.getIcons());
    }
    for (MapEquipmentIcon i : mapIconsByFloor.get(currFloor)) {
      if (!i.getButton().isVisible() && !iconsInMenus.contains(i)) {
        i.getButton().setVisible(true);
      }
    }
  }

  private static class Position {
    double x;
    double y;
  }
}

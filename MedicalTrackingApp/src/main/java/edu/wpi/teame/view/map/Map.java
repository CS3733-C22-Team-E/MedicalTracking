package edu.wpi.teame.view.map;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teame.App;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.objectManagers.EmployeeManager;
import edu.wpi.teame.db.objectManagers.LocationManager;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Equipment;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.*;
import edu.wpi.teame.model.serviceRequests.MedicalEquipmentServiceRequest;
import edu.wpi.teame.model.serviceRequests.ServiceRequest;
import edu.wpi.teame.view.controllers.AutoCompleteTextField;
import edu.wpi.teame.view.controllers.LandingPageController;
import edu.wpi.teame.view.controllers.ServiceRequestDirectoryPageController;
import edu.wpi.teame.view.controllers.serviceRequests.MedicalEquipmentDeliveryServiceRequestPageServiceRequestController;
import edu.wpi.teame.view.map.Astar.MapIntegration.PathFinder;
import edu.wpi.teame.view.map.Icons.MapEquipmentIcon;
import edu.wpi.teame.view.map.Icons.MapLocationDot;
import edu.wpi.teame.view.map.Icons.MapServiceRequestIcon;
import edu.wpi.teame.view.map.RadialMenu.RadialCheckMenuItem;
import edu.wpi.teame.view.map.RadialMenu.RadialContainerMenuItem;
import edu.wpi.teame.view.map.RadialMenu.RadialMenu;
import edu.wpi.teame.view.map.RadialMenu.RadialMenuItem;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.application.Platform;
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
import jfxtras.scene.menu.CirclePopupMenu;

public class Map {
  private final HashMap<FloorType, Image> Images = new HashMap<>();
  private final int WIDTH = 0;
  private final int HEIGHT = 0;
  private final double ZOOMINMAX = 1.5;
  private final double ZOOMOUTMAX = .2;
  private final HashMap<FloorType, ArrayList<MapEquipmentIcon>> mapIconsByFloor = new HashMap<>();
  private final HashMap<FloorType, ArrayList<MapLocationDot>> locationsByFloor = new HashMap<>();
  private final HashMap<EquipmentType, Image> TypeGraphics = new HashMap<>();
  private final HashMap<FloorType, ArrayList<MapServiceRequestIcon>> ActiveSRByFloor =
      new HashMap<>();
  //  private final HashMap<FloorType, HashSet<Radial>>
  private final ContextMenu EquipmentClicked = new ContextMenu();
  private final StackPane layout = new StackPane();
  private final LandingPageController appController;
  private Image backgroundImage;
  private double MAPHEIGHT = 0;
  private double MAPWIDTH = 0;
  private boolean showLocationNodes = false;
  private JFXButton lastPressed;
  private Location lastPressedLocation;
  private Location location;
  private FloorType currFloor;
  private ArrayList<ServiceRequest> oldSR = new ArrayList<ServiceRequest>();
  private PathFinder Navigation = null;
  private ArrayList<MapLocationDot> PathFindingLocations = new ArrayList<>();

  public Map(FloorType floor, LandingPageController app) throws SQLException {
    appController = app;
    currFloor = floor;
    for (FloorType currFloor : FloorType.values()) {
      Images.put(currFloor, new Image(getImageResource(getMapImg(currFloor))));
      mapIconsByFloor.put(currFloor, new ArrayList<>());
      locationsByFloor.put(currFloor, new ArrayList<>());
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

  public void switchFloors(FloorType floor) throws SQLException {
    currFloor = floor;
    backgroundImage = Images.get(floor);
    MAPHEIGHT = backgroundImage.getHeight();
    MAPWIDTH = backgroundImage.getWidth();
    updateLayoutChildren();
    if (Navigation != null) {
      Navigation.switchFloors(floor);
      Navigation.SelectFloor(MAPWIDTH, MAPHEIGHT);
      System.out.println(MAPWIDTH + " " + MAPHEIGHT);
    }
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
    MenuItem retval = new MenuItem();
    retval.setGraphic(new ImageView(TypeGraphics.get(equiptype)));

    retval.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            Equipment ToBeInserted =
                new Equipment(0, lastPressedLocation, equiptype, equiptype.toString(), false, true);
            try {
              DBManager.getInstance().getManager(DataBaseObjectType.Equipment).insert(ToBeInserted);
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
    if (Navigation != null) {
      Navigation.switchFloors(currFloor);
    }
    for (MapEquipmentIcon icon : mapIconsByFloor.get(currFloor)) {
      layout.getChildren().add(icon.getButton());
    }
    for (MapLocationDot dot : locationsByFloor.get(currFloor)) {
      layout.getChildren().add(dot.getIcon());
    }
    for (MapServiceRequestIcon icon : ActiveSRByFloor.get(currFloor)) {
      if (!layout.getChildren().contains(icon.progressIndicator)) {
        layout.getChildren().addAll(icon.progressIndicator, icon.Icon);
      }
    }
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
  @Deprecated
  private JFXComboBox<String> createFloorSwitcher() {
    final JFXComboBox<String> comboBox = new JFXComboBox<>();
    comboBox.setValue(currFloor.toString());
    for (FloorType floorType : FloorType.values()) {
      comboBox.getItems().add(floorType.toString());
    }
    comboBox.setTranslateX(Screen.getPrimary().getVisualBounds().getHeight() / 2.8);
    comboBox.setTranslateY(-Screen.getPrimary().getVisualBounds().getHeight() / 2 + 10);
    comboBox.setFocusColor(Color.rgb(0, 0, 255));
    comboBox.setOnAction(
        event -> {
          try {
            switchFloors(FloorType.valueOf(comboBox.getValue()));
          } catch (SQLException e) {
            e.printStackTrace();
          }
        });
    return comboBox;
  }

  public String getImageResource(String filePath) {
    return Objects.requireNonNull(App.class.getResource(filePath)).toString();
  }

  @Deprecated
  private JFXButton createZoomInButton() {
    Image zoomIcon = new Image(getImageResource("images/Icons/ZoomIn.png"));
    ImageView icon = new ImageView(zoomIcon);
    icon.setFitWidth(30);
    icon.setFitHeight(30);
    final JFXButton zoomInButton = new JFXButton("", icon);
    zoomInButton.setTranslateX(Screen.getPrimary().getVisualBounds().getHeight() / 1.45);
    zoomInButton.setTranslateY(-Screen.getPrimary().getVisualBounds().getHeight() / 2 + 50);
    zoomInButton.setOnAction(
        (event) -> {
          // double value zoomAmplifier is 1 for buttons
          zoomIn(1);
        });
    return zoomInButton;
  }

  public RadialMenu createMainController(StackPane stack) {
    Image zoomIn = new Image(getImageResource("images/Icons/ZoomIn.png"));
    Image zoomOut = new Image(getImageResource("images/Icons/ZoomOut.png"));
    Image refresh = new Image(getImageResource("images/Icons/RefreshIcon.png"));
    Image FilterIcon = new Image(getImageResource("images/Icons/FilterIcon.png"));
    Image Location = new Image(getImageResource("images/Icons/Location.png"));
    ImageView ZoomIN = new ImageView(zoomIn);
    ZoomIN.setFitWidth(30);
    ZoomIN.setFitHeight(30);
    ImageView ZoomOUT = new ImageView(zoomOut);
    ZoomOUT.setFitHeight(30);
    ZoomOUT.setFitWidth(30);
    ImageView REFRESH = new ImageView(refresh);
    REFRESH.setFitHeight(27);
    REFRESH.setFitWidth(27);
    ImageView FILTER = new ImageView(FilterIcon);
    FILTER.setFitWidth(30);
    FILTER.setFitHeight(30);
    ImageView Material =
        new ImageView(new Image(App.class.getResource("images/Icons/RadialIcon.png").toString()));
    Material.setFitHeight(30);
    Material.setFitWidth(30);
    RadialMenuItem ZoomIn =
        new RadialMenuItem(
            45,
            ZoomIN,
            new EventHandler<ActionEvent>() {
              @Override
              public void handle(ActionEvent event) {
                zoomIn(1);
              }
            });
    ZoomIn.ZoomButton = true;
    RadialMenuItem ZoomOut =
        new RadialMenuItem(
            45,
            ZoomOUT,
            new EventHandler<ActionEvent>() {
              @Override
              public void handle(ActionEvent event) {
                zoomOut(1);
              }
            });
    ZoomOut.ZoomButton = true;
    RadialMenuItem Refresh =
        new RadialMenuItem(
            45,
            REFRESH,
            new EventHandler<ActionEvent>() {
              @Override
              public void handle(ActionEvent event) {
                try {
                  RefreshSRfromDB();
                } catch (SQLException e) {
                  e.printStackTrace();
                }
              }
            });
    RadialContainerMenuItem FilterCheckBoxes = new RadialContainerMenuItem(45, FILTER);
    ImageView Floor =
        new ImageView(new Image(App.class.getResource("images/Icons/FloorSwitch.png").toString()));
    Floor.setFitHeight(30);
    Floor.setFitWidth(30);
    RadialContainerMenuItem FloorSwitch = new RadialContainerMenuItem(35, "Switch Floor", Floor);
    for (EquipmentType currEquipment : EquipmentType.values()) {
      ImageView CurrImageView = new ImageView(TypeGraphics.get(currEquipment));
      CurrImageView.setFitWidth(25);
      CurrImageView.setFitHeight(25);
      RadialCheckMenuItem tobeadded =
          new RadialCheckMenuItem(
              45, CurrImageView, true, Color.color(0.11764705882, 0.8431372549, 0.37647058823));
      tobeadded.setOnMouseClicked(
          event -> {
            filter(currEquipment);
            tobeadded.setSelected(!tobeadded.isSelected());
          });

      FilterCheckBoxes.addMenuItem(tobeadded);
    }
    ImageView LocationImageView = new ImageView(Location);
    LocationImageView.setFitHeight(35);
    LocationImageView.setFitWidth(35);
    RadialCheckMenuItem Locations =
        new RadialCheckMenuItem(
            45, LocationImageView, true, Color.color(0.11764705882, 0.8431372549, 0.37647058823));
    Locations.setOnMouseClicked(
        event -> {
          Locations.setSelected(!Locations.isSelected());
          for (FloorType currFloor : FloorType.values()) {
            for (MapLocationDot dot : locationsByFloor.get(currFloor)) {
              dot.getIcon().setVisible(!dot.getIcon().isVisible());
            }
          }
        });
    FilterCheckBoxes.addMenuItem(Locations);
    for (FloorType currFloor : FloorType.values()) {
      ImageView floorer =
          new ImageView(App.class.getResource("images/Icons/RadialIcon.png").toString());
      floorer.setFitWidth(35);
      floorer.setFitHeight(35);
      RadialCheckMenuItem floor =
          new RadialCheckMenuItem(35, floorer, false, Color.color(.117, .844, .38));
      if (currFloor == FloorType.ThirdFloor) {
        floor.setSelected(true);
      } else {
        floor.setSelected(false);
      }
      floor.setOnMouseClicked(
          event -> {
            FloorSwitch.items.forEach(
                item -> {
                  RadialCheckMenuItem i = (RadialCheckMenuItem) item;
                  i.setSelected(false);
                });
            floor.setSelected(true);
            try {
              switchFloors(currFloor);
            } catch (SQLException e) {
              e.printStackTrace();
            }
          });
      FloorSwitch.addMenuItem(floor);
    }
    RadialMenu MainController =
        new RadialMenu(
            0,
            30,
            80,
            3,
            Color.color(0.09803921568, 0.07843137254, 0.07843137254, .75),
            Color.color(.117, .844, .38, .85),
            Color.PAPAYAWHIP,
            Color.PAPAYAWHIP,
            true,
            RadialMenu.CenterVisibility.ALWAYS,
            Material,
            stack);
    MainController.addMenuItem(ZoomIn);
    MainController.addMenuItem(ZoomOut);
    MainController.addMenuItem(Refresh);
    MainController.addMenuItem(FilterCheckBoxes);
    MainController.addMenuItem(FloorSwitch);
    return MainController;
  }

  // Show all of the Equipment types passed in
  private void filter(EquipmentType e) {
    for (MapEquipmentIcon mapIcon : mapIconsByFloor.get(currFloor)) {
      if (mapIcon.equipment.getType() == e) {
        mapIcon.getButton().setVisible(!mapIcon.getButton().isVisible());
      }
    }
  }

  @Deprecated
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
    locationsCheckBox.setSelected(true);
    locationsCheckBox.setOnAction(
        event -> {
          for (MapLocationDot dot : locationsByFloor.get(currFloor)) {
            dot.getIcon().setVisible(!dot.getIcon().isVisible());
          }
          showLocationNodes = !showLocationNodes;
        });
    locationsCheckBox.setPrefHeight(30);
    locationsCheckBox.setPrefWidth(30);
    locationsCheckBox.setTranslateY(
        -30 * retval.size() - Screen.getPrimary().getVisualBounds().getHeight() / 2.8);
    locationsCheckBox.setTranslateX(Screen.getPrimary().getVisualBounds().getWidth() / 3.4);
    locationsCheckBox.setSelected(true);
    retval.add(locationsCheckBox);

    JFXCheckBox serviceRequestsCheckbox = new JFXCheckBox("Service Requests");
    serviceRequestsCheckbox.getStyleClass().add("combo-box");
    serviceRequestsCheckbox.setSelected(true);
    serviceRequestsCheckbox.setOnAction(
        event -> {
          for (MapServiceRequestIcon i : ActiveSRByFloor.get(currFloor)) {
            i.getIcon().setVisible(!i.getIcon().isVisible());
            i.getFillProgressIndicator().setVisible(serviceRequestsCheckbox.isSelected());
          }
        });
    serviceRequestsCheckbox.setPrefHeight(30);
    serviceRequestsCheckbox.setPrefWidth(30);
    serviceRequestsCheckbox.setTranslateY(
        -30 * retval.size() - Screen.getPrimary().getVisualBounds().getHeight() / 2.8);
    serviceRequestsCheckbox.setTranslateX(Screen.getPrimary().getVisualBounds().getWidth() / 3.4);
    retval.add(serviceRequestsCheckbox);

    return retval;
  }

  @Deprecated
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

  @Deprecated
  private JFXButton createRefreshButton() {
    Image zoomIcon = new Image(getImageResource("images/Icons/RefreshIcon.png"));
    ImageView icon = new ImageView(zoomIcon);
    icon.setFitWidth(30);
    icon.setFitHeight(30);
    final JFXButton zoomOutButton = new JFXButton("", icon);
    zoomOutButton.setTranslateX(Screen.getPrimary().getVisualBounds().getHeight() / 1.45);
    zoomOutButton.setTranslateY(-Screen.getPrimary().getVisualBounds().getHeight() / 2 + 90);
    zoomOutButton.setOnAction(
        (event) -> {
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
  private void zoomIn(double amp) {
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
    // Creating Equipment Icon Pressed
    //    EquipmentClicked.getStyleClass().add("combo-box");
    //    MenuItem deleteMenuItem = new MenuItem("Delete");
    //    deleteMenuItem.setOnAction(
    //        event -> {
    //          deleteMapIcon(lastPressed);
    //          updateLayoutChildren();
    //        });
    //    MenuItem addMenuItem = new MenuItem("Edit");
    //    addMenuItem.setOnAction(
    //        event -> {
    //          showEditLastPressedDialog();
    //        });
    //    EquipmentClicked.getItems().addAll(deleteMenuItem, addMenuItem);
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
    staticWrapper.getChildren().setAll(scroll, createMainController(staticWrapper));
    // setting size of scroll pane and setting the bar values
    scroll.setPrefSize(width, height);
    scroll.setHvalue(scroll.getHmin() + (scroll.getHmax() - scroll.getHmin()) / 2);
    scroll.setVvalue(scroll.getVmin() + (scroll.getVmax() - scroll.getVmin()) / 2);
    System.out.println("Init Complete");
    Navigation = new PathFinder(layout, backgroundImage.getWidth(), backgroundImage.getHeight());
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
              dot.getIcon().setVisible(true);
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
              DBManager.getInstance()
                  .getManager(DataBaseObjectType.Equipment)
                  .update(i.getEquipment());
            } catch (SQLException e) {
              e.printStackTrace();
            }
            // AutoFill
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
                dot.getIcon().setVisible(false);
              }
            }
            updateLayoutChildren();
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
    List<Equipment> equipment =
        DBManager.getInstance().getManager(DataBaseObjectType.Equipment).getAll();
    mapIconsByFloor.get(currFloor).clear();
    locationsByFloor.get(currFloor).clear();
    for (Equipment currEquipment : equipment) {
      addEquipmentToMap(currEquipment);
    }

    List<Location> locations =
        DBManager.getInstance().getManager(DataBaseObjectType.Location).getAll();
    for (Location currLocation : locations) {
      locationToMapElement(currLocation);
    }
    Navigation.refreshLocationsFromDB();
    Navigation.createConnections();
    Navigation.switchFloors(currFloor);
  }

  public void RefreshSRfromDB() throws SQLException {
    ArrayList<ServiceRequest> serviceRequestsFromDB = new ArrayList<>();
    serviceRequestsFromDB.addAll(
        DBManager.getInstance().getManager(DataBaseObjectType.AudioVisualSR).getAll());
    serviceRequestsFromDB.addAll(
        DBManager.getInstance().getManager(DataBaseObjectType.ComputerSR).getAll());
    serviceRequestsFromDB.addAll(
        DBManager.getInstance().getManager(DataBaseObjectType.FoodDeliverySR).getAll());
    serviceRequestsFromDB.addAll(
        DBManager.getInstance().getManager(DataBaseObjectType.GiftAndFloralSR).getAll());
    serviceRequestsFromDB.addAll(
        DBManager.getInstance().getManager(DataBaseObjectType.InternalPatientTransferSR).getAll());
    serviceRequestsFromDB.addAll(
        DBManager.getInstance().getManager(DataBaseObjectType.ExternalPatientSR).getAll());
    serviceRequestsFromDB.addAll(
        DBManager.getInstance().getManager(DataBaseObjectType.LanguageInterpreterSR).getAll());
    serviceRequestsFromDB.addAll(
        DBManager.getInstance().getManager(DataBaseObjectType.LaundrySR).getAll());
    serviceRequestsFromDB.addAll(
        DBManager.getInstance().getManager(DataBaseObjectType.ReligiousSR).getAll());
    serviceRequestsFromDB.addAll(
        DBManager.getInstance().getManager(DataBaseObjectType.SecuritySR).getAll());
    serviceRequestsFromDB.addAll(
        DBManager.getInstance().getManager(DataBaseObjectType.MedicalEquipmentSR).getAll());
    serviceRequestsFromDB.addAll(
        DBManager.getInstance().getManager(DataBaseObjectType.MedicineDeliverySR).getAll());
    serviceRequestsFromDB.addAll(
        DBManager.getInstance().getManager(DataBaseObjectType.FacilitiesMaintenanceSR).getAll());
    serviceRequestsFromDB.addAll(
        DBManager.getInstance().getManager(DataBaseObjectType.SanitationSR).getAll());
    serviceRequestsFromDB.stream()
        .forEach(
            serviceRequest -> {
              if (oldSR.contains(serviceRequest)) {
                serviceRequestsFromDB.remove(serviceRequest);
              } else {
                oldSR.add(serviceRequest);
                try {
                  ServiceRequestToMapElement(serviceRequest);
                  if (serviceRequest.getDBType() == DataBaseObjectType.MedicalEquipmentSR) {
                    MedicalEquipmentServiceRequest newSr =
                        (MedicalEquipmentServiceRequest) serviceRequest;
                    Location InitEquipmentLocation = newSr.getEquipment().getLocation();
                    List<Location> routeSnap =
                        Navigation.FindAndDrawRoute(
                            newSr.getEquipment().getLocation().getId(),
                            newSr.getLocation().getId(),
                            currFloor);

                    Timer newTimer = new Timer();
                    mapIconsByFloor
                        .get(newSr.getLocation().getFloor())
                        .forEach(
                            icon -> {
                              if (newSr.getEquipment().getId() == icon.getEquipment().getId()) {
                                System.out.println("Found Equipment");
                                int period = (int) 20 * 1000 / routeSnap.size();
                                final int[] counter = {0};
                                newTimer.scheduleAtFixedRate(
                                    new TimerTask() {
                                      @Override
                                      public void run() {
                                        Platform.runLater(
                                            new Runnable() {
                                              @Override
                                              public void run() {
                                                if (counter[0] > routeSnap.size() - 1) {
                                                  Navigation.RemoveRoute(
                                                      InitEquipmentLocation, newSr.getLocation());
                                                  newTimer.cancel();
                                                }
                                                if (counter[0] <= routeSnap.size() - 1) {
                                                  icon.getButton()
                                                      .setTranslateX(
                                                          routeSnap.get(counter[0]).getX()
                                                              - MAPWIDTH / 2);
                                                  icon.getButton()
                                                      .setTranslateY(
                                                          routeSnap.get(counter[0]).getY()
                                                              - MAPHEIGHT / 2);
                                                  newSr
                                                      .getEquipment()
                                                      .setLocation(routeSnap.get(counter[0]));
                                                  try {
                                                    DBManager.getInstance()
                                                        .getManager(DataBaseObjectType.Equipment)
                                                        .update(newSr.getEquipment());
                                                  } catch (SQLException e) {
                                                    e.printStackTrace();
                                                  }
                                                }
                                              }
                                            });

                                        //                                        try {
                                        //                                          Location newL =
                                        //
                                        // routeSnap.get(routeSnap.size() - 1);
                                        //                                          Equipment newE =
                                        // newSr.getEquipment();
                                        //
                                        // newE.setLocation(newL);
                                        //
                                        // DBManager.getInstance().getManager(
                                        //
                                        // DataBaseObjectType.Equipment)
                                        //
                                        // .update(newE);
                                        //                                        } catch
                                        // (SQLException e) {
                                        //
                                        // e.printStackTrace();
                                        //                                        }
                                        counter[0]++;
                                      }
                                    },
                                    0,
                                    period);
                              }
                            });
                  }
                } catch (SQLException e) {
                  e.printStackTrace();
                }
              }
            });
  }

  private void locationToMapElement(Location location) {
    double mapWidthTest = Images.get(location.getFloor()).getWidth();
    double mapHeightTest = Images.get(location.getFloor()).getHeight();
    double x = location.getX() - mapWidthTest / 2;
    double y = location.getY() - mapHeightTest / 2;
    MapLocationDot newDot = new MapLocationDot(location, x, y);
    locationsByFloor.get(location.getFloor()).add(newDot);
    Tooltip t = new Tooltip(location.getLongName() + " ID: " + location.getId());
    Tooltip.install(newDot.getIcon(), t);
    CirclePopupMenu PaneMenu = new CirclePopupMenu(newDot.getIcon(), null);
    for (EquipmentType currEquipment : EquipmentType.values()) {
      PaneMenu.getItems().add(createEquipmentMenuItem(currEquipment));
    }
    updateLayoutChildren();
    newDot
        .getIcon()
        .setOnMouseClicked(
            new EventHandler<MouseEvent>() {
              @Override
              public void handle(MouseEvent event) {
                System.out.println("Node Pressed: " + location.getId());
                if (event.getButton() == MouseButton.SECONDARY) {
                  lastPressedLocation = location;
                  PaneMenu.show(event.getScreenX(), event.getScreenY());
                  Timer timer = new Timer();
                  timer.schedule(
                      new TimerTask() {
                        @Override
                        public void run() {
                          PaneMenu.hide();
                        }
                      },
                      2000);
                } else if (PathFindingLocations.size() < 2) {
                  newDot.getIcon().setFill(Color.BLUE);
                  PathFindingLocations.add(newDot);
                  if (PathFindingLocations.size() == 2) {
                    try {
                      PathFindingLocations.get(0).getIcon().setFill(Color.YELLOW);
                      PathFindingLocations.get(1).getIcon().setFill(Color.GREEN);
                      Navigation.FindAndDrawRoute(
                          PathFindingLocations.get(0).getLocation().getId(),
                          PathFindingLocations.get(1).getLocation().getId(),
                          currFloor);

                    } catch (SQLException e) {
                      e.printStackTrace();
                    }
                  }
                } else if (PathFindingLocations.size() == 2) {
                  PathFindingLocations.stream()
                      .forEach(
                          LocationDot -> {
                            LocationDot.getIcon().setFill(Color.BLACK);
                          });
                  PathFindingLocations.clear();
                  PathFindingLocations.add(newDot);
                  newDot.getIcon().setFill(Color.BLUE);
                }
              }
            });

    newDot
        .getIcon()
        .setOnMouseEntered(
            new EventHandler<MouseEvent>() {
              @Override
              public void handle(MouseEvent event) {
                DisplayRadialMenu(checkLocationForSharedEquipment(location));
              }
            });
    newDot
        .getIcon()
        .setOnMouseExited(
            new EventHandler<MouseEvent>() {
              @Override
              public void handle(MouseEvent event) {
                Timer close = new Timer();
                close.schedule(
                    new TimerTask() {
                      @Override
                      public void run() {
                        CloseRadialMenu(checkLocationForSharedEquipment(location));
                      }
                    },
                    1250);
              }
            });
  }

  private void ServiceRequestToMapElement(ServiceRequest SR) throws SQLException {
    double X = SR.getLocation().getX() - MAPWIDTH / 2;
    double Y = SR.getLocation().getY() - MAPHEIGHT / 2;
    MapServiceRequestIcon newIcon = new MapServiceRequestIcon(SR, X, Y);
    newIcon.addToList(ActiveSRByFloor.get(currFloor));
    newIcon.startTimer(20);
    layout.getChildren().addAll(newIcon.getFillProgressIndicator(), newIcon.Icon);
    // updateLayoutChildren();
    ContextMenu ServiceRequestMenu = new ContextMenu();
    ServiceRequestMenu.setStyle(
        "-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #7579ff, #b224ef); -fx-background-radius: 10px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0)");
    RadioMenuItem CompleteServiceRequest = new RadioMenuItem("Complete Service Request");
    RadioMenuItem UpdateServiceRequest = new RadioMenuItem("Update Service Request");
    AutoCompleteTextField LocationField = new AutoCompleteTextField();
    AutoCompleteTextField AssigneeServiceRequest = new AutoCompleteTextField();
    CustomMenuItem Assignee = new CustomMenuItem(AssigneeServiceRequest);
    CustomMenuItem Location = new CustomMenuItem(LocationField);
    ((LocationManager) DBManager.getInstance().getManager(DataBaseObjectType.Location))
        .getAll()
        .forEach(
            location -> {
              LocationField.getEntries().add(location.getLongName());
            });
    ((EmployeeManager) DBManager.getInstance().getManager(DataBaseObjectType.Employee))
        .getAll()
        .forEach(
            employee -> {
              AssigneeServiceRequest.getEntries().add(employee.getName());
            });
    UpdateServiceRequest.setSelected(false);
    UpdateServiceRequest.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            try {
              Location newLocation =
                  ((LocationManager)
                          DBManager.getInstance().getManager(DataBaseObjectType.Location))
                      .getByName(LocationField.getText());
              Employee newEmployee =
                  ((EmployeeManager)
                          DBManager.getInstance().getManager(DataBaseObjectType.Employee))
                      .getByAssignee(AssigneeServiceRequest.getText());
              if (newLocation != null) {
                SR.setLocation(newLocation);
                newIcon.Icon.setTranslateX(newLocation.getX() - MAPWIDTH / 2);
                newIcon.Icon.setTranslateY(newLocation.getY() - MAPHEIGHT / 2);
                newIcon.progressIndicator.setTranslateX(newIcon.Icon.getTranslateX());
                newIcon.progressIndicator.setTranslateY(newIcon.Icon.getTranslateY());
                System.out.println(
                    "Updated Service Request Location to:" + SR.getLocation().getLongName());
              }
              if (newEmployee != null) {
                SR.setAssignee(newEmployee);
                System.out.println(
                    "Updated Service Request Assignee to: " + SR.getAssignee().getName());
              }
            } catch (SQLException e) {
              e.printStackTrace();
            }

            try {
              DBManager.getInstance().getManager(SR.getDBType()).update(SR);
            } catch (SQLException e) {
              e.printStackTrace();
            }
          }
        });
    CompleteServiceRequest.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            newIcon.cancelTimer();
            CompleteServiceRequest.setSelected(true);
          }
        });
    Location.setHideOnClick(false);
    Assignee.setHideOnClick(false);
    CompleteServiceRequest.setSelected(false);
    ServiceRequestMenu.getItems().add(Assignee);
    ServiceRequestMenu.getItems().add(Location);
    ServiceRequestMenu.getItems().add(CompleteServiceRequest);
    ServiceRequestMenu.getItems().add(UpdateServiceRequest);

    newIcon.progressIndicator.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            ServiceRequestMenu.show(newIcon.Icon, event.getScreenX(), event.getScreenY());
          }
        });
  }

  private ArrayList<MapEquipmentIcon> checkLocationForSharedEquipment(Location location) {
    AtomicInteger count = new AtomicInteger();
    ArrayList<MapEquipmentIcon> SharedAtLocation = new ArrayList<>();
    mapIconsByFloor
        .get(location.getFloor())
        .forEach(
            equipment -> {
              if (equipment.getEquipment().getLocation().getId() == location.getId()) {
                SharedAtLocation.add(equipment);
              }
            });
    return SharedAtLocation;
  }

  private void DisplayRadialMenu(ArrayList<MapEquipmentIcon> EquipAtLocation) {
    int size = EquipAtLocation.size();
    int radius = 35;
    if (size > 1) {
      double angle = 2 * Math.PI / size;
      for (int i = 0; i < size; i++) {
        double currX = EquipAtLocation.get(i).getButton().getTranslateX();
        double currY = EquipAtLocation.get(i).getButton().getTranslateY();
        currX += radius * Math.cos(i * angle);
        currY += radius * Math.sin(i * angle);
        EquipAtLocation.get(i).getButton().setTranslateX(currX);
        EquipAtLocation.get(i).getButton().setTranslateY(currY);
      }
    } else {
      return;
    }
  }

  private void CloseRadialMenu(ArrayList<MapEquipmentIcon> EquipAtLocation) {
    for (MapEquipmentIcon icon : EquipAtLocation) {
      icon.getButton()
          .setTranslateX(icon.getEquipment().getLocation().getX() - backgroundImage.getWidth() / 2);
      icon.getButton()
          .setTranslateY(
              icon.getEquipment().getLocation().getY() - backgroundImage.getHeight() / 2);
    }
  }

  private static class Position {
    double x;
    double y;
  }
}

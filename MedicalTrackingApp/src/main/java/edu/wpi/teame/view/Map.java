package edu.wpi.teame.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teame.App;
import edu.wpi.teame.db.*;
import edu.wpi.teame.model.Equipment;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.EquipmentType;
import edu.wpi.teame.model.enums.FloorType;
import edu.wpi.teame.view.ProgressBar.FillProgressIndicator;
import java.sql.SQLException;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.util.Pair;

public class Map {
  private HashMap<FloorType, Image> Images = new HashMap<FloorType, Image>();
  private Image backgroundImage;
  private final int WIDTH = 0;
  private final int HEIGHT = 0;
  private double MAPHEIGHT = 0;
  private double MAPWIDTH = 0;
  private final double ZOOMINMAX = 1.5;
  private final double ZOOMOUTMAX = .2;
  private boolean showLocationNodes = false;
  private HashMap<FloorType, ArrayList<MapEquipmentIcon>> mapIconsByFloor = new HashMap<>();
  private HashMap<FloorType, ArrayList<MapLocationDot>> locationsByFloor = new HashMap<>();
  private HashMap<EquipmentType, Image> TypeGraphics = new HashMap<EquipmentType, Image>();
  private ContextMenu EquipmentClicked = new ContextMenu();
  private ContextMenu PaneMenu = new ContextMenu();
  private JFXButton lastPressed;
  private Point2D lastPressedPoint = new Point2D(0, 0);
  private StackPane layout = new StackPane();
  private FloorType currFloor;

  public Map(FloorType floor) {
    currFloor = floor;
    for (FloorType currFloor : FloorType.values()) {
      Images.put(currFloor, new Image(App.class.getResource(getMapImg(currFloor)).toString()));
      mapIconsByFloor.put(currFloor, new ArrayList<>());
      locationsByFloor.put(currFloor, new ArrayList<>());
    }

    System.out.println("Loaded Maps");
    switchFloors(floor);
  }

  public void refreshServiceRequest() {}

  private void addServiceRequestToMap() {
    FillProgressIndicator indicator = new FillProgressIndicator();
    indicator.setTranslateX(0);
    indicator.setTranslateY(0);
    indicator.setProgress(0);
    Timer newTimer = new Timer();
    layout.getChildren().add(indicator);
    newTimer.scheduleAtFixedRate(
        new TimerTask() {
          @Override
          public void run() {
            int progress = indicator.getProgress();
            if (progress == 100) {
              indicator.setVisible(false);
              newTimer.cancel();
              updateLayoutChildren();
              return;
            }
            indicator.setProgress(progress + 5);
          }
        },
        0,
        500);
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
            // TODO handle adding MapIcon of type equiptype
            // Given X,Y,EquipmentType,Floor
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
  // init ComboBox
  private JFXComboBox<String> createFloorSwitcher() {
    final JFXComboBox<String> comboBox = new JFXComboBox<>();
    for (FloorType floorType : FloorType.values()) {
      comboBox.getItems().add(floorType.toString());
    }
    comboBox.setTranslateX(-Screen.getPrimary().getVisualBounds().getHeight() / 2);
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
          zoomIn(1);
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

  public Parent getMapScene(double height, double width) {
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
    layout.setOnScroll(
        new EventHandler<ScrollEvent>() {
          @Override
          public void handle(ScrollEvent event) {
            double scrollVal = event.getDeltaY();
          }
        });
    ScrollPane scroll = createScrollPane(layout);
    StackPane staticWrapper = new StackPane();
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
    addServiceRequestToMap();
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
            node.setTranslateX(nearestLocation.getX() - MAPWIDTH / 2);
            node.setTranslateY(nearestLocation.getY() - MAPHEIGHT / 2);
            i.getEquipment().setLocation(nearestLocation);
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
  }
}

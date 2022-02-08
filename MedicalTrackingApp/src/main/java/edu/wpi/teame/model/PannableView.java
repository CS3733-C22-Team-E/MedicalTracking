package edu.wpi.teame.model;

import static javafx.application.Application.launch;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teame.App;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.Equipment;
import edu.wpi.teame.db.EquipmentType;
import edu.wpi.teame.db.FloorType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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
  private final int ICONSIZE = 60;
  private final double ZOOMINMAX = 1.5;
  private final double ZOOMOUTMAX = .2;
  private double MAPIMGWIDTH;
  private double MAPIMGHEIGHT;
  private final double ZOOMAMPLIFIER = .1;

  // Init booleans
  private boolean hamburgerDeployed = false;
  private boolean addMode = false;
  private boolean invertZoomScroll = false;

  // Init data structures
  private ArrayList<ImageView> hamburgerDeployments = new ArrayList<ImageView>();
  // private ArrayList<MapIcon> mapIcons = new ArrayList<MapIcon>();
  private HashMap<FloorType, ArrayList<MapEquipmentIcon>> mapIconsByFloor = new HashMap<>();
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
    }
    switchFloors(floor);
  }

  private void switchFloors(FloorType newFloor) {
    backgroundImage = new Image(App.class.getResource(getMapImg(newFloor)).toString());
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
  private boolean CoordinateChecker(String X, String Y) {
    Double doubleX = Double.parseDouble(X);
    Double doubleY = Double.parseDouble(Y);
    return doubleX > 0 && doubleX < 5000 && doubleY > 0 && doubleY < 3400;
  }

  public void deleteMapIcon(JFXButton button) {
    for (MapEquipmentIcon mapIcon : mapIconsByFloor.get(currFloor)) {
      if (mapIcon.Button.equals(button)) {
        mapIconsByFloor.get(currFloor).remove(mapIcon);
      }
    }
  }
  // This is essentially the Main function
  // getMapScene returns the entire map page
  public Parent getMapScene(double height, double width) {
    TypeGraphics.put(
        EquipmentType.PBED,
        new ImageView(
            new Image(App.class.getResource("images/Icons/HospitalBedIcon.png").toString())));
    TypeGraphics.put(
        EquipmentType.XRAY,
        new ImageView(new Image(App.class.getResource("images/Icons/XRayIcon.png").toString())));
    TypeGraphics.put(
        EquipmentType.RECL,
        new ImageView(
            new Image(App.class.getResource("images/Icons/ReclinerIcon.png").toString())));
    TypeGraphics.put(
        EquipmentType.PUMP,
        new ImageView(new Image(App.class.getResource("images/Icons/PumpIcon.png").toString())));
    System.out.println("Addded Graphics");
    EquipmentMenu = new ContextMenu();
    EquipmentMenu.getStyleClass().add("combo-box");
    PaneMenu = new ContextMenu();
    AddMenu = new ContextMenu();
    MenuItem AddMenuItem1 = new MenuItem("1");
    AddMenu.getItems().add(AddMenuItem1);
    for (EquipmentType currEquipment : EquipmentType.values()) {
      MenuItem currItem = new MenuItem(currEquipment.toString());
      currItem.setOnAction(
          new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

              addMapIcon(
                  PressX,
                  PressY,
                  getImageViewFromEquipmentType(currEquipment),
                  currEquipment.toString());
            }
          });
      PaneMenu.getItems().add(currItem);
      PaneMenu.getStyleClass().add("combo-box");
    }

    MenuItem equipmentItem1 = new MenuItem("Delete");
    equipmentItem1.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            //            lastPressed.setVisible(false);
            //            lastPressed.setDisable(false);
            deleteMapIcon(lastPressed);
            updateLayoutChildren();
          }
        });
    MenuItem equipmentItem2 = new MenuItem("Edit");
    equipmentItem2.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
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
                              || !CoordinateChecker(XPosition.getText(), YPosition.getText()));
                    });
            YPosition.textProperty()
                .addListener(
                    (observable, oldValue, newValue) -> {
                      MoveButton.setDisable(
                          newValue.trim().isEmpty()
                              || XPosition.getText().isBlank()
                              || !CoordinateChecker(XPosition.getText(), YPosition.getText()));
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
          }
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
    staticWrapper.getChildren().addAll(createCheckBoxes());
    for (ImageView imageView : hamburgerDeployments) {
      staticWrapper.getChildren().add(imageView);
    }

    scroll.setPrefSize(width, height);
    scroll.setHvalue(scroll.getHmin() + (scroll.getHmax() - scroll.getHmin()) / 2);
    scroll.setVvalue(scroll.getVmin() + (scroll.getVmax() - scroll.getVmin()) / 2);

    layout.setOnMouseReleased(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            if (event.getButton() == MouseButton.SECONDARY) {
              PressX = event.getX();
              PressY = event.getY();
              scroll.setContextMenu(PaneMenu);
              PaneMenu.show(scroll, event.getScreenX(), event.getScreenY());
            }
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
  }

  private void addMapIcon(
      double xCoordinate,
      double yCoordinate,
      ImageView image,
      String toolTip,
      FloorType floor,
      Equipment equipment) {
    ImageView iconGraphic = image;
    iconGraphic.setFitWidth(30);
    iconGraphic.setFitHeight(30);
    final JFXButton newButton = new JFXButton();
    newButton.setGraphic(iconGraphic);
    double x = xCoordinate - MAPIMGWIDTH / 2;
    double y = yCoordinate - MAPIMGHEIGHT / 2;
    newButton.setTranslateX(x);
    newButton.setTranslateY(y);
    newButton.setOpacity(1);
    newButton.setContextMenu(EquipmentMenu);
    draggable(newButton);
    newButton.setOnMouseReleased(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            if (event.getButton() == MouseButton.SECONDARY) {
              lastPressed = newButton;
              newButton.getContextMenu().show(newButton, event.getScreenX(), event.getScreenY());
            }
          }
        });
    Tooltip tooltip = new Tooltip(toolTip);
    Tooltip.install(newButton, tooltip);
    MapEquipmentIcon newIcon = new MapEquipmentIcon(newButton, toolTip, equipment);
    mapIconsByFloor.get(floor).add(newIcon);
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
    ImageView iconGraphic = image;
    iconGraphic.setFitWidth(30);
    iconGraphic.setFitHeight(30);
    final JFXButton newButton = new JFXButton();
    newButton.setGraphic(iconGraphic);
    double x = xCoordinate - MAPIMGWIDTH / 2;
    double y = yCoordinate - MAPIMGHEIGHT / 2;
    newButton.setTranslateX(x);
    newButton.setTranslateY(y);
    newButton.setOpacity(1);
    newButton.setContextMenu(EquipmentMenu);
    draggable(newButton);
    newButton.setOnMouseReleased(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            if (event.getButton() == MouseButton.SECONDARY) {
              lastPressed = newButton;
              newButton.getContextMenu().show(newButton, event.getScreenX(), event.getScreenY());
            }
          }
        });
    Tooltip tooltip = new Tooltip(toolTip);
    Tooltip.install(newButton, tooltip);
    // TODO new MapEquipment Icon add equipment to parameter
    MapEquipmentIcon newIcon = new MapEquipmentIcon(newButton, toolTip);
    mapIconsByFloor.get(currFloor).add(newIcon);
    updateLayoutChildren();
  }

  private ArrayList<JFXCheckBox> createCheckBoxes() {
    ArrayList<JFXCheckBox> retval = new ArrayList<>();

    for (EquipmentType currEquipment : EquipmentType.values()) {
      JFXCheckBox equipmentCheckBox = new JFXCheckBox(currEquipment.toString());
      equipmentCheckBox.getStyleClass().add("combo-box");

      equipmentCheckBox.setSelected(true);
      equipmentCheckBox.setOnAction(
          new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
              filter(currEquipment);
            }
          });
      retval.add(equipmentCheckBox);

      equipmentCheckBox.setTranslateY(
          -30 * currEquipment.ordinal() - Screen.getPrimary().getVisualBounds().getHeight() / 2.8);
      equipmentCheckBox.setTranslateX(-Screen.getPrimary().getVisualBounds().getHeight() / 3);
    }
    return retval;
  }
  // Init zoomInButton
  private JFXButton createZoomInButton() {
    Image zoomIcon = new Image(App.class.getResource("images/Icons/ZoomIn.png").toString());
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
    Image zoomIcon = new Image(App.class.getResource("images/Icons/ZoomOut.png").toString());
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
    final JFXComboBox<String> comboBox = new JFXComboBox();
    for (FloorType floorType : FloorType.values()) {
      comboBox.getItems().add(floorType.toString());
    }
    comboBox.setTranslateX(-Screen.getPrimary().getVisualBounds().getHeight() / 2);
    comboBox.setTranslateY(-Screen.getPrimary().getVisualBounds().getHeight() / 2 + 10);
    comboBox.setFocusColor(Color.rgb(0, 0, 255));
    comboBox.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            switchFloors(currFloor.getFloorFromString(comboBox.getValue()));
          }
        });
    return comboBox;
  }

  // Init currently unused hamburger button
  //  private JFXButton createHamburgerButton() {
  //    Image hamburgerIcon =
  //        new Image(App.class.getResource("images/Icons/HamburgerMenu.png").toString());
  //    ImageView icon = new ImageView(hamburgerIcon);
  //    icon.setFitHeight(30);
  //    icon.setFitWidth(30);
  //    final JFXButton hamburgerButton = new JFXButton("", icon);
  //    hamburgerButton.setTranslateX(Screen.getPrimary().getVisualBounds().getHeight() / 1.45);
  //    hamburgerButton.setTranslateY(-Screen.getPrimary().getVisualBounds().getHeight() / 2 + 110);
  //    hamburgerButton.setOnAction(
  //        (event) -> {
  //          hamburgerDeployed = !hamburgerDeployed;
  //          addMode = !addMode;
  //          // deployHamburger();
  //        });
  //    return hamburgerButton;
  //  }

  //  // Adds icons to the hamburger menu
  //  // TODO styling— probably a complete redesign
  //  private void addHamburgerDeployments() {
  //    String[] allIcons = {"EquipmentStorageIcon.png", "HospitalBedIcon.png"};
  //    int iconNum = 0;
  //    for (String icon : allIcons) {
  //      Image imageIcon = new Image(App.class.getResource("images/Icons/" + icon).toString());
  //      ImageView imageVew = new ImageView(imageIcon);
  //      imageVew.setFitWidth(ICONSIZE);
  //      imageVew.setFitHeight(ICONSIZE);
  //      imageVew.setTranslateX(WIDTH / 2 - 10 - ICONSIZE);
  //      imageVew.setTranslateY(-(HEIGHT / 2) + 100 + iconNum * (ICONSIZE) + iconNum * 10);
  //      imageVew.setVisible(false);
  //      hamburgerDeployments.add(imageVew);
  //      iconNum++;
  //    }
  //  }

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

  public void convertLocationToMapIcon(Equipment equip) {
    //    MapIcon retval = new MapIcon(
    //            (double) equip.getLocationNode().getX(),
    //            (double) equip.getLocationNode().getY(),
    //            equip.getName(),
    //            getImageViewFromEquipmentType(equip.getType()));
    //    mapIcons.add(retval);
    addMapIcon(
        (double) equip.getLocationNode().getX(),
        (double) equip.getLocationNode().getY(),
        getImageViewFromEquipmentType(equip.getType()),
        equip.getName(),
        equip.getLocationNode().getFloor(),
        equip);
    updateLayoutChildren();
  }

  public void getFromDB() {
    LinkedList<Equipment> equipment = DBManager.getInstance().getEquipmentManager().getAll();
    for (Equipment currEquipment : equipment) {
      //      if (currEquipment.getLocationNode().getFloor() == currFloor) {
      //        convertLocationToMapIcon(currEquipment);
      //      }
      convertLocationToMapIcon(currEquipment);
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

  private static class Position {
    double x;
    double y;
  }

  private void draggable(JFXButton node) {
    final Position pos = new Position();

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
            node.setCursor(Cursor.MOVE);
            // When a press event occurs, the location coordinates of the event are cached
            pos.x = node.getTranslateX();
            pos.y = node.getTranslateY();
            System.out.println(pos.x + " " + pos.y);
          }
        });
    node.addEventHandler(
        MouseEvent.MOUSE_RELEASED,
        event -> {
          System.out.println("Done");
          node.setCursor(Cursor.DEFAULT);
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
}

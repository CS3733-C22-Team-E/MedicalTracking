package edu.wpi.teame.model;

import static javafx.application.Application.launch;

import com.jfoenix.controls.JFXButton;
import edu.wpi.teame.Pannable;
import edu.wpi.teame.model.enums.MapFloor;
import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

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

  public PannableView(MapFloor floor) {
    String mapImg = getMapImg(floor);
    backgroundImage = new Image(Pannable.class.getResource(mapImg).toString());
    mapImageHeight = backgroundImage.getHeight();
    mapImageWidth = backgroundImage.getWidth();
  }

  private String getMapImg(MapFloor f) {
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

  public void start(Stage stage) {
    layout.setOnMouseClicked(
        (e -> {
          if (addMode) {
            addMapIcon(e.getX(), e.getY(), "AppIcon.png");
            System.out.println(e.getX());
            System.out.println(e.getY());
          }
        }));
    updateLayoutChildren();

    ScrollPane scroll = createScrollPane(layout);

    StackPane staticWrapper = new StackPane();
    staticWrapper
        .getChildren()
        .setAll(scroll, createHamburgerButton(), createZoomInButton(), createZoomOutButton());
    addHamburgerDeployments();
    for (ImageView imageView : hamburgerDeployments) {
      staticWrapper.getChildren().add(imageView);
    }

    Scene scene = new Scene(staticWrapper);
    stage.setScene(scene);
    stage.show();

    scroll.prefWidthProperty().bind(scene.widthProperty());
    scroll.prefHeightProperty().bind(scene.widthProperty());

    scroll.setHvalue(scroll.getHmin() + (scroll.getHmax() - scroll.getHmin()) / 2);
    scroll.setVvalue(scroll.getVmin() + (scroll.getVmax() - scroll.getVmin()) / 2);
  }

  private void updateLayoutChildren() {
    layout.getChildren().setAll(new ImageView(backgroundImage));
    for (MapIcon icon : mapIcons) {
      layout.getChildren().add(icon.getButton());
    }
  }

  private void addMapIcon(double xCoordinate, double yCoordinate, String type) {
    Image iconImage = new Image(Pannable.class.getResource("images/Icons/" + type).toString());
    ImageView iconGraphic = new ImageView(iconImage);
    iconGraphic.setFitWidth(30);
    iconGraphic.setFitHeight(30);
    final JFXButton newButton = new JFXButton(type, iconGraphic);
    double x = xCoordinate - mapImageWidth / 2;
    double y = yCoordinate - mapImageHeight / 2;
    newButton.setTranslateX(x);
    newButton.setTranslateY(y);
    newButton.setOnAction(
        (event) -> {
          newButton.setVisible(false);
        });
    MapIcon newIcon = new MapIcon(newButton, type);
    mapIcons.add(newIcon);
    updateLayoutChildren();
  }

  private JFXButton createZoomInButton() {
    Image zoomIcon = new Image(Pannable.class.getResource("images/Icons/ZoomIn.png").toString());
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
    Image zoomIcon = new Image(Pannable.class.getResource("images/Icons/ZoomOut.png").toString());
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
        new Image(Pannable.class.getResource("images/Icons/HamburgerMenu.png").toString());
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
      Image imageIcon = new Image(Pannable.class.getResource("images/Icons/" + icon).toString());
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

  public static void main(String[] args) {
    launch(args);
  }

  private void zoomIn() {
    layout.setScaleX(layout.getScaleX() * 1.1);
    layout.setScaleY(layout.getScaleY() * 1.1);
  }

  private void zoomOut() {
    layout.setScaleX(layout.getScaleX() * 1 / (1.1));
    layout.setScaleY(layout.getScaleY() * 1 / (1.1));
  }
}

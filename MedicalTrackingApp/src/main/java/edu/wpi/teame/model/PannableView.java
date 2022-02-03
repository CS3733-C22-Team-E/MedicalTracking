package edu.wpi.teame.model;

import static javafx.application.Application.launch;

import com.jfoenix.controls.JFXButton;
import edu.wpi.teame.Pannable;
import javafx.event.*;
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

  private boolean hamburgerOpen = false;

  private ScrollPane hamburgerMenu;

  public PannableView(String imageURL) {
    backgroundImage =
        new Image(Pannable.class.getResource("images/map/00_thelowerlevel1.png").toString());
  }

  public void start(Stage stage) {

    // construct the scene contents over a stacked background.
    StackPane layout = new StackPane();
    layout.getChildren().setAll(new ImageView(backgroundImage), createKillButton());

    // wrap the scene contents in a pannable scroll pane.
    ScrollPane scroll = createScrollPane(layout);

    initHamburgerMenu();

    StackPane staticWrapper = new StackPane();
    staticWrapper.getChildren().setAll(scroll, createHamburgerButton(), hamburgerMenu);

    // show the scene.
    Scene scene = new Scene(staticWrapper);
    stage.setScene(scene);
    stage.show();

    // bind the preferred size of the scroll area to the size of the scene.
    scroll.prefWidthProperty().bind(scene.widthProperty());
    scroll.prefHeightProperty().bind(scene.widthProperty());

    // center the scroll contents.
    scroll.setHvalue(scroll.getHmin() + (scroll.getHmax() - scroll.getHmin()) / 2);
    scroll.setVvalue(scroll.getVmin() + (scroll.getVmax() - scroll.getVmin()) / 2);
  }

  private Button createKillButton() {
    final Button killButton = new Button("Kill the evil witch");
    killButton.setStyle("-fx-base: firebrick;");
    killButton.setTranslateX(65);
    killButton.setTranslateY(-130);
    killButton.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent t) {
            killButton.setStyle("-fx-base: forestgreen;");
            killButton.setText("Ding-Dong! The Witch is Dead");
          }
        });
    return killButton;
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
          hamburgerOpen = !hamburgerOpen;
          updateHamburgerMenu(hamburgerMenu);
        });
    return hamburgerButton;
  }

  private void initHamburgerMenu() {
    String[] allIcons = {"EquipmentStorageIcon.png", "HospitalBedIcon.png"};
    hamburgerMenu = new ScrollPane();
    hamburgerMenu.setPrefSize(100, 100);
    populateHamburgerMenu(hamburgerMenu, allIcons);
    hamburgerMenu.setVisible(false);
  }

  private void populateHamburgerMenu(ScrollPane scrollPane, String[] icons) {
    GridPane grid = new GridPane();
    grid.setVgap(20);
    int iconNum = 0;
    for (String icon : icons) {
      ImageView node =
          new ImageView(new Image(Pannable.class.getResource("images/Icons/" + icon).toString()));
      node.setFitWidth(30);
      node.setFitHeight(30);
      grid.add(node, 0, iconNum);
    }
    grid.setTranslateX(0);
    grid.setTranslateY(0);
    scrollPane.setContent(grid);
  }

  private void updateHamburgerMenu(ScrollPane hamburgerMenu) {
    hamburgerMenu.setVisible(hamburgerOpen);
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
}

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

  public PannableView(String imageURL) {
    backgroundImage =
        new Image("https://www.narniaweb.com/wp-content/uploads/2009/08/NarniaMap.jpg");
  }

  public void start(Stage stage) {

    // construct the scene contents over a stacked background.
    StackPane layout = new StackPane();
    layout.getChildren().setAll(new ImageView(backgroundImage), createKillButton());

    // wrap the scene contents in a pannable scroll pane.
    ScrollPane scroll = createScrollPane(layout);

    StackPane staticWrapper = new StackPane();
    staticWrapper.getChildren().setAll(scroll, createAddButton());

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

  /** @return a control to place on the scene. */
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

  private JFXButton createAddButton() {
    Image hamburgerIcon =
        new Image(Pannable.class.getResource("images/Icons/HamburgerMenu.png").toString());
    ImageView icon = new ImageView(hamburgerIcon);
    icon.setFitHeight(30);
    icon.setFitWidth(30);
    final JFXButton addButton = new JFXButton("", icon);
    addButton.setTranslateX(610);
    addButton.setTranslateY(-320);
    addButton.setOnAction(
        (event) -> {
          addButton.setStyle("-fx-base: forestgreen;");
          addButton.setText("Add mode on!");
        });
    return addButton;
  }

  /** @return a ScrollPane which scrolls the layout. */
  private ScrollPane createScrollPane(Pane layout) {
    ScrollPane scroll = new ScrollPane();
    scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scroll.setPannable(true);
    scroll.setPrefSize(1280, 720);
    scroll.setContent(layout);
    return scroll;
  }

  public static void main(String[] args) {
    launch(args);
  }
}

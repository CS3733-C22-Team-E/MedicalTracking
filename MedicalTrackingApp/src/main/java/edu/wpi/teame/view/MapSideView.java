package edu.wpi.teame.view;

import edu.wpi.teame.App;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MapSideView {
  private final StackPane layout = new StackPane();
  private Image backgroundImage;
  private double MAPHEIGHT = 1343;
  private double MAPWIDTH = 859;

  public MapSideView() {
    backgroundImage =
        new Image(App.class.getResource("images/map/Tower - Side View.jpg").toString());
  }

  public Parent getMapScene() {
    layout.getChildren().add(new ImageView(backgroundImage));
    ZoomableScrollPane scroll = createScrollPane(layout);
    StackPane staticWrapper = new StackPane();
    staticWrapper.getChildren().add(scroll);
    layout.setOnMouseClicked(
        event -> {
          System.out.println(
              layout.sceneToLocal(new Point2D(event.getSceneX(), event.getSceneY())));
        });
    Rectangle LL1 = createRectangle(121, 1175, 415, 52);
    Rectangle AdmittingF1 = createRectangle(268, 1123, 220, 52);
    Rectangle WestPlazaF1 = createRectangle(488, 1123, 47, 52);
    Rectangle FamCtr = createRectangle(195, 1123, 73, 52);
    Rectangle ICUSuP_F3 = createRectangle(195, 1023, 75, 52);
    Rectangle MICUClean = createRectangle(269, 1023, 43, 52);
    Rectangle DirtyMicu = createRectangle(333, 1023, 21, 52);
    Rectangle InfusDial = createRectangle(417, 1023, 117, 52);
    layout
        .getChildren()
        .addAll(LL1, AdmittingF1, WestPlazaF1, FamCtr, ICUSuP_F3, MICUClean, DirtyMicu, InfusDial);
    return staticWrapper;
  }

  private ZoomableScrollPane createScrollPane(Pane layout) {
    ZoomableScrollPane scroll = new ZoomableScrollPane(layout);
    scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scroll.setPannable(true);
    // scroll.setPrefSize(WIDTH, HEIGHT);
    return scroll;
  }

  private Rectangle createRectangle(
      double PixelXLeftTop, double PixelYLeftTop, double width, double height) {
    Rectangle retval = new Rectangle();
    retval.setWidth(width);
    retval.setHeight(height);
    retval.setTranslateX(PixelXLeftTop + retval.getWidth() / 2 - MAPWIDTH / 2);
    retval.setTranslateY(PixelYLeftTop + retval.getHeight() / 2 - MAPHEIGHT / 2);
    retval.setFill(Color.BLUE);
    retval.setOpacity(.35);
    return retval;
  }
}

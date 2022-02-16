package edu.wpi.teame.view;

import edu.wpi.teame.App;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Equipment;
import edu.wpi.teame.model.enums.FloorType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MapSideView {
  private final StackPane layout = new StackPane();
  private Image backgroundImage;
  private double MAPHEIGHT = 1343;
  private double MAPWIDTH = 859;
  private int[][] EquipmentManagementArray = new int[2][4];
  ListView<Equipment> newListview = new ListView<Equipment>();

  public MapSideView() {
    for (int i = 0; i < 4; ++i) {
      for (byte j = 0; i < 2; ++i) {
        this.EquipmentManagementArray[i][j] = 0;
      }
    }
    backgroundImage =
        new Image(App.class.getResource("images/map/Tower - Side View.jpg").toString());
    newListview.setPrefSize(200, 100);
    newListview.setMaxSize(400, 100);
    newListview.getStyleClass().add("combo-box");
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
    Rectangle Floor3 = this.createRectangle(125.0D, 1025.0D, 413.0D, 52.0D);
    Rectangle Floor1 = this.createRectangle(125.0D, 1125.0D, 413.0D, 52.0D);
    Rectangle LL1 = this.createRectangle(121.0D, 1175.0D, 415.0D, 52.0D);

    LL1.setOnMouseEntered(
        new EventHandler<MouseEvent>() {
          public void handle(MouseEvent event) {
            try {
              newListview.getItems().clear();
              MapSideView.this.getEquipmentByFloorFromDB(FloorType.LowerLevel1).stream()
                  .forEach(
                      (equipment) -> {
                        System.out.println("Sorting");
                        newListview.getItems().add(equipment);
                        if (equipment.isClean()) {
                          MapSideView.this
                              .EquipmentManagementArray[0][equipment.getType().ordinal()]++;
                        } else {
                          MapSideView.this
                              .EquipmentManagementArray[1][equipment.getType().ordinal()]++;
                        }
                      });
            } catch (SQLException var3) {
              var3.printStackTrace();
            }
          }
        });
    Floor1.setOnMouseEntered(
        new EventHandler<MouseEvent>() {
          public void handle(MouseEvent event) {
            newListview.getItems().clear();
            try {
              MapSideView.this.getEquipmentByFloorFromDB(FloorType.FirstFloor).stream()
                  .forEach(
                      (equipment) -> {
                        System.out.println("Sorting");
                        newListview.getItems().add(equipment);
                        if (equipment.isClean()) {
                          MapSideView.this
                              .EquipmentManagementArray[0][equipment.getType().ordinal()]++;
                        } else {
                          MapSideView.this
                              .EquipmentManagementArray[1][equipment.getType().ordinal()]++;
                        }
                      });
            } catch (SQLException var3) {
              var3.printStackTrace();
            }
          }
        });
    Floor3.setOnMouseEntered(
        new EventHandler<MouseEvent>() {
          public void handle(MouseEvent event) {
            newListview.getItems().clear();
            try {
              MapSideView.this.getEquipmentByFloorFromDB(FloorType.ThirdFloor).stream()
                  .forEach(
                      (equipment) -> {
                        System.out.println("Sorting");
                        newListview.getItems().add(equipment);
                        if (equipment.isClean()) {
                          MapSideView.this
                              .EquipmentManagementArray[0][equipment.getType().ordinal()]++;
                        } else {
                          MapSideView.this
                              .EquipmentManagementArray[1][equipment.getType().ordinal()]++;
                        }
                      });
            } catch (SQLException var3) {
              var3.printStackTrace();
            }
          }
        });

    layout.getChildren().addAll(Floor3, Floor1, LL1, newListview);
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

  private List<Equipment> getEquipmentByFloorFromDB(FloorType floor) throws SQLException {
    List<Equipment> equipmentList = new ArrayList();
    for (Equipment equipment : DBManager.getInstance().getEquipmentManager().getAll()) {
      if (equipment.getLocation().getFloor() == floor) {
        equipmentList.add(equipment);
      }
    }
    return equipmentList;
  }
}

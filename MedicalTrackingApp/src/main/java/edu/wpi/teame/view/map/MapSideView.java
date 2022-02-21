package edu.wpi.teame.view.map;

import edu.wpi.teame.App;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Equipment;
import edu.wpi.teame.model.enums.FloorType;
import edu.wpi.teame.view.controllers.LandingPageController;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MapSideView {
  private final StackPane layout = new StackPane();
  private Image backgroundImage;
  private double MAPHEIGHT = 1343;
  private double MAPWIDTH = 859;
  private int[][] EquipmentManagementArray = new int[2][4];
  ListView<Equipment> AllEquipmentView = new ListView<Equipment>();
  GridPane holder = new GridPane();
  public ArrayList<TextField> cleanFields = new ArrayList<TextField>();
  public ArrayList<TextField> dirtyFields = new ArrayList<TextField>();
  private LandingPageController Tabber;
  private Map MapView;

  public MapSideView(LandingPageController Tab, Map mapView) {
    Tabber = Tab;
    MapView = mapView;
    for (int i = 0; i < 4; ++i) {
      for (byte j = 0; i < 2; ++i) {
        this.EquipmentManagementArray[i][j] = 0;
      }
    }
    for (int i = 0; i < 4; i++) {
      TextField clean = new TextField("0");
      clean.setMaxWidth(35);
      cleanFields.add(clean);
      TextField dirty = new TextField("0");
      dirty.setMaxWidth(35);
      dirtyFields.add(dirty);
      holder.add(cleanFields.get(i), i + 1, 1);
      holder.add(dirtyFields.get(i), i + 1, 2);
    }
    backgroundImage =
        new Image(App.class.getResource("images/map/Tower - Side View.jpg").toString());
    AllEquipmentView.setPrefSize(600, 200);
    AllEquipmentView.setMaxSize(AllEquipmentView.getPrefWidth(), AllEquipmentView.getPrefHeight());
    AllEquipmentView.getStyleClass().add("combo-box");
    holder.setAlignment(Pos.CENTER);
    Label Clean = new Label("Clean");
    Label Dirty = new Label("Dirty");
    holder.add(Clean, 0, 1);
    holder.add(Dirty, 0, 2);
    Label PBED = new Label("Patient Beds");
    Label XRAY = new Label("XRAYs");
    Label INFUSIONPUMP = new Label("Infusion Pumps");
    Label RECLINER = new Label("Recliners");
    holder.add(PBED, 1, 0);
    holder.add(INFUSIONPUMP, 2, 0);
    holder.add(XRAY, 3, 0);
    holder.add(RECLINER, 4, 0);
    holder.setMaxSize(600, 200);
    holder.setHgap(10);
    holder.setVgap(10);
  }

  public Parent getMapScene() {
    layout.getChildren().add(new ImageView(backgroundImage));
    ZoomableScrollPane scroll = createScrollPane(layout);
    StackPane staticWrapper = new StackPane();
    GridPane Grid = new GridPane();
    Grid.setMouseTransparent(true);
    Grid.getChildren().add(AllEquipmentView);
    Grid.setVgrow(AllEquipmentView, Priority.ALWAYS);
    Grid.setAlignment(Pos.TOP_CENTER);
    staticWrapper.getChildren().add(scroll);
    Rectangle Floor3 = this.createRectangle(125.0D, 1025.0D, 413.0D, 52.0D);
    Rectangle Floor1 = this.createRectangle(125.0D, 1125.0D, 413.0D, 52.0D);
    Rectangle LL1 = this.createRectangle(121.0D, 1175.0D, 415.0D, 52.0D);

    LL1.setOnMouseEntered(
        new EventHandler<MouseEvent>() {
          public void handle(MouseEvent event) {
            for (int i = 0; i < 4; i++) {
              EquipmentManagementArray[0][i] = 0;
              EquipmentManagementArray[1][i] = 0;
            }
            try {
              AllEquipmentView.getItems().clear();
              MapSideView.this.getEquipmentByFloorFromDB(FloorType.LowerLevel1).stream()
                  .forEach(
                      (equipment) -> {
                        System.out.println("Sorting");
                        AllEquipmentView.getItems().add(equipment);
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
            updateFieldsinGrid();
          }
        });
    LL1.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            Tabber.mainTabPane.getSelectionModel().select(2);
            try {
              MapView.switchFloors(FloorType.LowerLevel1);
            } catch (SQLException e) {
              e.printStackTrace();
            }
          }
        });
    Floor1.setOnMouseEntered(
        new EventHandler<MouseEvent>() {
          public void handle(MouseEvent event) {
            for (int i = 0; i < 4; i++) {
              EquipmentManagementArray[0][i] = 0;
              EquipmentManagementArray[1][i] = 0;
            }
            AllEquipmentView.getItems().clear();
            try {
              MapSideView.this.getEquipmentByFloorFromDB(FloorType.FirstFloor).stream()
                  .forEach(
                      (equipment) -> {
                        System.out.println("Sorting");
                        AllEquipmentView.getItems().add(equipment);
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
            updateFieldsinGrid();
          }
        });
    Floor1.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            Tabber.mainTabPane.getSelectionModel().select(2);
            try {
              MapView.switchFloors(FloorType.FirstFloor);
            } catch (SQLException e) {
              e.printStackTrace();
            }
          }
        });
    Floor3.setOnMouseEntered(
        new EventHandler<MouseEvent>() {
          public void handle(MouseEvent event) {
            AllEquipmentView.getItems().clear();
            for (int i = 0; i < 4; i++) {
              EquipmentManagementArray[0][i] = 0;
              EquipmentManagementArray[1][i] = 0;
            }
            try {
              MapSideView.this.getEquipmentByFloorFromDB(FloorType.ThirdFloor).stream()
                  .forEach(
                      (equipment) -> {
                        System.out.println("Sorting");
                        AllEquipmentView.getItems().add(equipment);
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
            updateFieldsinGrid();
          }
        });
    Floor3.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            Tabber.mainTabPane.getSelectionModel().select(2);
            try {
              MapView.switchFloors(FloorType.ThirdFloor);
            } catch (SQLException e) {
              e.printStackTrace();
            }
          }
        });
    layout.getChildren().addAll(Floor3, Floor1, LL1);
    holder.setMouseTransparent(true);
    holder.getStyleClass().add("combo-box");
    staticWrapper.getChildren().add(holder);
    Grid.setColumnSpan(AllEquipmentView, 3);
    return staticWrapper;
  }

  private void updateFieldsinGrid() {
    for (int i = 0; i < 4; i++) {
      cleanFields.get(i).setText(String.valueOf(EquipmentManagementArray[1][i]));
      dirtyFields.get(i).setText(String.valueOf(EquipmentManagementArray[0][i]));
    }
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

  private void SwitchToMapView(FloorType floor) {}
}

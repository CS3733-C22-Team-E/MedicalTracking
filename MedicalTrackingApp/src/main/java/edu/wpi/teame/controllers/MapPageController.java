package edu.wpi.teame.controllers;

import edu.wpi.teame.App;
import edu.wpi.teame.db.FloorType;
import edu.wpi.teame.model.MapIcon;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Pair;

public class MapPageController implements Initializable {
  private final HashMap<String, String> ResourceNames = new HashMap<String, String>();
  private final HashMap<FloorType, ArrayList<ImageView>> FloorIconLists = new HashMap<>();
  @FXML private ComboBox<String> dropDown;
  @FXML private ImageView mapImageView;
  @FXML private GridPane pane;
  @FXML private AnchorPane mapPane;
  @FXML private Text Xposition;
  @FXML private Text Yposition;
  @FXML private Text LabelX;
  @FXML private Text LabelY;
  private double conversionFactorX;
  private double conversionFactorY;
  private boolean deletedButton = false;
  private boolean dragged = false;
  private FloorType floor = FloorType.LowerLevel1;

  @FXML
  void switchImage(String name) {
    mapImageView.setImage(new Image(App.class.getResource(name).toString()));
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    FloorIconLists.put(FloorType.GroundFloor, new ArrayList<ImageView>());
    FloorIconLists.put(FloorType.LowerLevel1, new ArrayList<ImageView>());
    FloorIconLists.put(FloorType.LowerLevel2, new ArrayList<ImageView>());
    FloorIconLists.put(FloorType.FirstFloor, new ArrayList<ImageView>());
    FloorIconLists.put(FloorType.SecondFloor, new ArrayList<ImageView>());
    FloorIconLists.put(FloorType.ThirdFloor, new ArrayList<ImageView>());

    ResourceNames.put("Patient Room", "images/Icons/HospitalBedIcon.png");
    ResourceNames.put("Equipment Storage Room", "images/Icons/EquipmentStorageIcon.png");
    System.out.println("Setting Up");
    conversionFactorX = 1.055 * 5000 / mapImageView.getFitWidth();
    conversionFactorY = 1.004 * 3400 / mapImageView.getFitHeight();
    dropDown.setItems(
        FXCollections.observableArrayList(
            "Ground Floor",
            "Lower Level 1",
            "Lower Level 2",
            "First Floor",
            "Second Floor",
            "Third Floor"));
    mapPane.setOnMouseMoved(
        event -> {
          Xposition.setText(String.valueOf((int) (conversionFactorX * event.getX())));
          Yposition.setText(String.valueOf((int) (conversionFactorY * event.getY())));
        });
    mapPane.setOnMouseExited(
        event -> {
          Xposition.setText("Not on Map");
          Yposition.setText("Not on Map");
        });
  }

  @FXML
  private void createNewIconButton() {
    CreateMapIconDialogBox();
  }

  /**
   * @param PixelX
   * @param PixelY
   * @param pane
   * @param icon
   * @param FitWidth
   * @param FitHeight
   * @param Opacity
   * @return
   */
  @FXML
  private ImageView createMapIcon(
      Double PixelX,
      Double PixelY,
      Pane pane,
      String icon,
      int FitWidth,
      int FitHeight,
      Double Opacity,
      String descriptor) {
    ImageView newIcon = new ImageView();
    newIcon.setImage(new Image(App.class.getResource(icon).toString()));
    newIcon.setOpacity(Opacity);
    newIcon.setFitWidth(FitWidth);
    newIcon.setFitHeight(FitHeight);
    newIcon.setLayoutX(ConvertPixelXToLayoutX(PixelX) - FitWidth / 2);
    newIcon.setLayoutY(ConvertPixelYToLayoutY(PixelY) - FitHeight / 2);
    newIcon.setOnMouseClicked(
        event -> {
          if (!dragged) {
            HandleMapIconClick(newIcon);
          } else {
            dragged = false;
          }
        });

    Tooltip tooltip = new Tooltip(descriptor);
    Tooltip.install(newIcon, tooltip);
    pane.getChildren().add(newIcon);
    draggable(newIcon);
    FloorIconLists.get(floor).add(newIcon);
    return newIcon;
  }

  @FXML
  private void deletedButtonClick() {
    deletedButton = !deletedButton;
  }

  private void updateMap(FloorType floor_) {
    mapPane.getChildren().addAll(FloorIconLists.get(floor));
    mapPane.getChildren().addAll(Xposition, Yposition, LabelX, LabelY);
    floor = floor_;
  }

  @FXML
  private void comboBoxChanged() {
    mapPane.getChildren().clear();
    System.out.println(dropDown.getValue());

    switch (dropDown.getValue()) {
      case "Ground Floor":
        switchImage("images/map/00_thegroundfloor.png");
        updateMap(FloorType.GroundFloor);
        break;
      case "Lower Level 1":
        switchImage("images/map/00_thelowerlevel1.png");
        updateMap(FloorType.LowerLevel1);
        break;
      case "Lower Level 2":
        switchImage("images/map/00_thelowerlevel2.png");
        updateMap(FloorType.LowerLevel2);
        break;
      case "First Floor":
        switchImage("images/map/01_thefirstfloor.png");
        updateMap(FloorType.FirstFloor);
        break;
      case "Second Floor":
        switchImage("images/map/02_thesecondfloor.png");
        updateMap(FloorType.SecondFloor);
        break;
      case "Third Floor":
        switchImage("images/map/03_thethirdfloor.png");
        updateMap(FloorType.ThirdFloor);
        break;
    }
  }

  double ConvertPixelXToLayoutX(double PixelX) {
    return (PixelX / 5000) * mapPane.getLayoutBounds().getWidth();
  }

  double ConvertPixelYToLayoutY(double PixelY) {
    return (PixelY / 3400) * mapPane.getLayoutBounds().getHeight();
  }

  double ConvertLayoutXToPixelX(double LayoutX) {
    return (LayoutX * 5000) / mapPane.getLayoutBounds().getWidth();
  }

  double ConvertLayoutYToPixelY(double LayoutY) {
    return (LayoutY * 3400) / mapPane.getLayoutBounds().getHeight();
  }

  private void CreateMapIconDialogBox() {
    Dialog<MapIcon> dialog = new Dialog<>();
    dialog.setTitle("Add Icon to Map");
    dialog.getDialogPane().setMinSize(200, 200);
    dialog.setHeaderText("Choose the details");
    ButtonType Create = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(Create, ButtonType.CANCEL);
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));
    TextField XPosition = new TextField();
    XPosition.setPromptText("X Position");
    TextField YPosition = new TextField();
    YPosition.setPromptText("Y Position");
    ComboBox<String> Descriptor = new ComboBox<>();
    Descriptor.setItems(
        FXCollections.observableArrayList("Patient Room", "Equipment Storage Room"));
    grid.add(new Label("X Position:"), 0, 0);
    grid.add(XPosition, 1, 0);
    grid.add(new Label("Y Position:"), 0, 1);
    grid.add(YPosition, 1, 1);
    grid.add(new Label("Type:"), 0, 2);
    grid.add(Descriptor, 1, 2);
    dialog.getDialogPane().setContent(grid);
    Node CreateButton = dialog.getDialogPane().lookupButton(Create);
    XPosition.textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              CreateButton.setDisable(
                  newValue.trim().isEmpty()
                      || YPosition.getText().isBlank()
                      || !CoordinateChecker(XPosition.getText(), YPosition.getText()));
            });
    YPosition.textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              CreateButton.setDisable(
                  newValue.trim().isEmpty()
                      || XPosition.getText().isBlank()
                      || !CoordinateChecker(XPosition.getText(), YPosition.getText()));
            });
    dialog.setResultConverter(
        dialogButton -> {
          if (dialogButton == Create) {
            // Hash Map with string names for resources and key is the dialog combo pane response
            createMapIcon(
                Double.parseDouble(XPosition.getText()),
                Double.parseDouble(YPosition.getText()),
                mapPane,
                ResourceNames.get(Descriptor.getValue()),
                20,
                20,
                .7,
                Descriptor.getValue());
          }
          return null;
        });

    dialog.showAndWait();
  }

  private void HandleMapIconClick(ImageView mapIcon) {
    if (deletedButton) {
      mapPane.getChildren().remove(mapIcon);
      System.out.println(mapIcon.getLayoutX());
      System.out.println(mapIcon.getLayoutY());
      deletedButton = false;
      return;
    }
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
            mapIcon.setLayoutX(ConvertPixelXToLayoutX(Double.parseDouble(XPosition.getText())));
            mapIcon.setLayoutY(ConvertPixelYToLayoutY(Double.parseDouble(YPosition.getText())));
            mapIcon.setVisible(true);
          }
          return null;
        });
    dialog.showAndWait();
  }

  private boolean CoordinateChecker(String X, String Y) {
    Double doubleX = Double.parseDouble(X);
    Double doubleY = Double.parseDouble(Y);
    return doubleX > 0 && doubleX < 5000 && doubleY > 0 && doubleY < 3400;
  }

  private static class Position {
    double x;
    double y;
  }

  private void draggable(Node node) {
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
          node.setCursor(Cursor.MOVE);
          // When a press event occurs, the location coordinates of the event are cached
          pos.x = event.getX();
          pos.y = event.getY();
        });
    node.addEventHandler(
        MouseEvent.MOUSE_RELEASED,
        event -> {
          node.setCursor(Cursor.DEFAULT);
        });

    // Realize drag and drop function
    node.addEventHandler(
        MouseEvent.MOUSE_DRAGGED,
        event -> {
          double distanceX = event.getX() - pos.x;
          double distanceY = event.getY() - pos.y;

          double x = node.getLayoutX() + distanceX;
          double y = node.getLayoutY() + distanceY;

          // Update mouse location while dragging
          Xposition.setText(String.valueOf((int) ConvertLayoutXToPixelX(x)));
          Yposition.setText(String.valueOf((int) ConvertLayoutYToPixelY(y)));

          // After calculating X and y, relocate the node to the specified coordinate point (x, y)
          node.relocate(x, y);
          dragged = true;
        });
  }
}

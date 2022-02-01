package edu.wpi.teamname.views;

import edu.wpi.teamname.App;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.util.Pair;

public class MapViewController {
  // private final HashMap<String, Image> mapImages = new HashMap<String, Image>();
  @FXML private ComboBox dropDown;
  @FXML private ImageView mapImageView;
  @FXML private GridPane pane;
  @FXML private AnchorPane mapPane;
  @FXML private Text coordinateText;
  private boolean set = false;
  private double conversionFactorX;
  private double conversionFactorY;

  // private Collection<ImageView> marks = new ArrayList<ImageView>();
  @FXML
  void switchImage(String name) {
    mapImageView.setImage(new Image(App.class.getResource(name).toString()));
  }

  @FXML
  private void setup() {
    if (!set) {
      System.out.println("Setting Up");
      pane.setPrefHeight(Screen.getPrimary().getBounds().getHeight());
      pane.setPrefWidth(Screen.getPrimary().getBounds().getWidth());
      pane.autosize();
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
      set = true;
    }

    mapPane.setOnMouseClicked(e -> handleMouseClick(e.getX(), e.getY()));
    mapPane.setOnMouseMoved(e -> updateMouseCoordinates(e.getX(), e.getY()));
  }

  @FXML
  private void handleMouseClick(double x, double y) {
    System.out.println("[" + x + ", " + y + "]");
  }

  @FXML
  private void createNewIconButton() {
    CreateMapIconDialogBox();
    // createMapIcon(1300.0, 2225.0, mapPane, "images/504015.png", 20, 20, .7);
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
      Double Opacity) {
    ImageView retval = new ImageView();
    retval.setImage(new Image(App.class.getResource(icon).toString()));
    retval.setOpacity(Opacity);
    pane.getChildren().add(retval);
    retval.setFitWidth(FitWidth);
    retval.setFitHeight(FitHeight);
    retval.setLayoutX(ConvertPixelXToLayoutX(PixelX) - FitWidth / 2);
    retval.setLayoutY(ConvertPixelYToLayoutY(PixelY) - FitHeight / 2);
    retval.setOnMouseClicked(event -> ShowRelocateDialogBox(retval));
    retval.setOnMouseEntered(event -> System.out.println("Mouse Entered"));
    retval.setOnMouseExited(event -> System.out.println("Mouse Exited"));
    return retval;
  }

  //  @FXML
  //  private ImageView createMapIcon(Optional<MapIcon> icon) {
  //    ImageView retval = new ImageView();
  //    retval.setImage(new
  // Image(App.class.getResource(icon.get().getIconResourcePath()).toString()));
  //    retval.setOpacity(.7);
  //    mapPane.getChildren().add(retval);
  //    retval.setFitWidth(20);
  //    retval.setFitHeight(20);
  //    retval.setLayoutX(ConvertPixelXToLayoutX(icon.get().getPixelX()) - 20 / 2);
  //    retval.setLayoutY(ConvertPixelYToLayoutY(icon.get().getPixelY()) - 20 / 2);
  //    retval.setOnMouseClicked(event -> ShowRelocateDialogBox(retval));
  //    retval.setOnMouseEntered(event -> System.out.println("Mouse Entered"));
  //    retval.setOnMouseExited(event -> System.out.println("Mouse Exited"));
  //    return retval;
  //  }

  @FXML
  private void comboBoxChanged() {
    System.out.println(dropDown.getValue().toString());
    switch (dropDown.getValue().toString()) {
      case "Ground Floor":
        switchImage("images/00_thegroundfloor.png");
        break;
      case "Lower Level 1":
        switchImage("images/00_thelowerlevel1.png");
        break;
      case "Lower Level 2":
        switchImage("images/00_thelowerlevel2.png");
        break;
      case "First Floor":
        switchImage("images/01_thefirstfloor.png");
        break;
      case "Second Floor":
        switchImage("images/02_thesecondfloor.png");
        break;
      case "Third Floor":
        switchImage("images/03_thethirdfloor.png");
        break;
    }
  }

  double ConvertPixelXToLayoutX(double PixelX) {
    return (PixelX / 5000) * mapPane.getLayoutBounds().getWidth();
  }

  double ConvertPixelYToLayoutY(double PixelY) {
    return (PixelY / 3400) * mapPane.getLayoutBounds().getHeight();
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
    dialog.setResultConverter(
        dialogButton -> {
          if (dialogButton == Create) {
            createMapIcon(
                Double.parseDouble(XPosition.getText()),
                Double.parseDouble(YPosition.getText()),
                mapPane,
                "images/504015.png",
                20,
                20,
                .7);
          }
          return null;
        });

    dialog.showAndWait();
  }

  private void ShowRelocateDialogBox(ImageView node) {
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
              MoveButton.setDisable(newValue.trim().isEmpty() || YPosition.getText().isBlank());
            });
    YPosition.textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              MoveButton.setDisable(newValue.trim().isEmpty() || XPosition.getText().isBlank());
            });

    dialog.getDialogPane().setContent(grid);

    // Convert the result to a username-password-pair when the login button is clicked.
    dialog.setResultConverter(
        dialogButton -> {
          if (dialogButton == Move) {
            node.setLayoutX(ConvertPixelXToLayoutX(Double.parseDouble(XPosition.getText())));
            node.setLayoutY(ConvertPixelYToLayoutY(Double.parseDouble(YPosition.getText())));
            node.setVisible(true);
          }
          return null;
        });
    dialog.showAndWait();
  }

  public void updateMouseCoordinates(double x, double y) {
    coordinateText.setText("[ " + String.valueOf(x) + ", " + String.valueOf(y) + " ]");
    //TODO Does not work correctly when window is resized :(
  }
}

package edu.wpi.teamname.views;

import edu.wpi.teamname.App;
import java.awt.*;
import java.util.Optional;
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
import javafx.stage.Screen;
import javafx.util.Pair;

public class MapViewController {
  // private final HashMap<String, Image> mapImages = new HashMap<String, Image>();
  @FXML private ComboBox dropDown;
  @FXML private ImageView mapImageView;
  @FXML private GridPane pane;
  @FXML private AnchorPane mapPane;
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
  }

  @FXML
  private void handleMouseClick(double x, double y) {
    System.out.println("[" + x + ", " + y + "]");
  }

  @FXML
  private void createNewStack() {
    // Actually should implement dialog box asking type of location and position
    createMapIcon(1300.0, 2225.0, mapPane, "images/504015.png", 20, 20, .7);
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
    retval.setOnMouseClicked(event -> showDialogBox(retval));
    retval.setOnMouseEntered(event -> System.out.println("Mouse Entered"));
    retval.setOnMouseExited(event -> System.out.println("Mouse Exited"));
    return retval;
  }

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
    return (PixelX / 5000) * mapImageView.getLayoutBounds().getWidth();
  }

  double ConvertPixelYToLayoutY(double PixelY) {
    return (PixelY / 3400) * mapImageView.getLayoutBounds().getHeight();
  }

  private void showDialogBox(ImageView node) {
    //    TextInputDialog inputdialog = new TextInputDialog("Enter some Text");
    //    inputdialog.setContentText("Text: ");
    //    inputdialog.setHeaderText("JavaFX Input Dialog Example");
    //    Button button = new Button("JavaFX Input Dialog");
    //    inputdialog.showAndWait();
    //    // Double InputX = Double.parseDouble()
    //    node.setLayoutX(ConvertPixelXToLayoutX(1900));
    //    node.setLayoutY(ConvertPixelYToLayoutY(1900));

    //    Dialog<Pair<String, String>> inputDialog = new Dialog<Pair<String, String>>();
    //    inputDialog.setTitle("Choose Where to move this");
    //    ButtonType loginButtonType = new ButtonType("Move", ButtonBar.ButtonData.OK_DONE);
    //    inputDialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
    //    GridPane grid = new GridPane();
    //    grid.setHgap(10);
    //    grid.setVgap(10);
    //    grid.setPadding(new Insets(20, 150, 10, 10));
    //    TextField PositionX = new TextField();
    //    PositionX.setPromptText("X Position");
    //    TextField PositionY = new TextField();
    //    PositionY.setPromptText("Y Position");
    //    inputDialog.getDialogPane().getChildren().addAll(PositionX, PositionY);
    //    inputDialog.showAndWait();
    Dialog<Pair<Double, Double>> dialog = new Dialog<>();
    dialog.setTitle("Move Equipment");
    dialog.setHeaderText("Choose the X and Y Position");

    // Set the icon (must be included in the project).
    // dialog.setGraphic();

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
            return new Pair<Double, Double>(
                Double.parseDouble(XPosition.getText()), Double.parseDouble(YPosition.getText()));
          }
          return null;
        });
    Optional<Pair<Double, Double>> result = dialog.showAndWait();
    node.setLayoutX(ConvertPixelXToLayoutX(result.get().getKey()));
    node.setLayoutY(ConvertPixelYToLayoutY(result.get().getValue()));
  }
}

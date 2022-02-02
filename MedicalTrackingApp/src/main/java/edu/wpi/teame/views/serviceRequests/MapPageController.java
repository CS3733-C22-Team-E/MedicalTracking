package edu.wpi.teame.views.serviceRequests;

import edu.wpi.teame.App;
import edu.wpi.teame.views.MapIcon;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import javafx.util.Pair;

public class MapPageController implements Initializable {
  private final HashMap<String, String> ResourceNames = new HashMap<String, String>();
  @FXML private ComboBox<String> dropDown;
  @FXML private ImageView mapImageView;
  @FXML private GridPane pane;
  @FXML private AnchorPane mapPane;
  @FXML private Text Xposition;
  @FXML private Text Yposition;
  @FXML private Text coordinateText;
  private double conversionFactorX;
  private double conversionFactorY;
  private boolean deletedButton = false;

  @FXML
  void switchImage(String name) {
    mapImageView.setImage(new Image(App.class.getResource(name).toString()));
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ResourceNames.put("Patient Room", "images/HospitalBedIcon.png");
    ResourceNames.put("Equipment Storage Room", "images/noun-suitcase-325866.png");
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
    mapPane.setOnMouseClicked(e -> handleMouseClick(e.getX(), e.getY()));
    mapPane.setOnMouseMoved(
        event -> {
          Xposition.setText(String.valueOf((int) conversionFactorX * event.getX()));
          Yposition.setText(String.valueOf((int) conversionFactorY * event.getY()));
        });
    mapPane.setOnMouseExited(
        event -> {
          Xposition.setText("Not on Map");
          Yposition.setText("Not on Map");
        });
  }

  @FXML
  private void handleMouseClick(double x, double y) {
    Xposition.setText(String.valueOf((int) x));
    Yposition.setText(String.valueOf((int) y));
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
    newIcon.setOnMouseClicked(event -> HandleMapIconClick(newIcon));
    Tooltip tooltip = new Tooltip(descriptor);
    Tooltip.install(newIcon, tooltip);
    pane.getChildren().add(newIcon);
    return newIcon;
  }

  @FXML
  private void deletedButtonClick() {
    deletedButton = !deletedButton;
  }

  @FXML
  private void comboBoxChanged() {
    System.out.println(dropDown.getValue());
    switch (dropDown.getValue()) {
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
}

package edu.wpi.teame.view;

import com.jfoenix.controls.JFXCheckBox;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.FloorType;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import edu.wpi.teame.model.serviceRequests.ServiceRequest;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class ServiceRequestCard {

  private int hexColor;

  // Styling
  private int BORDERRADIUS = 10;

  // Details
  private final ServiceRequestBacklog backlog;
  private String patientName;
  private Location location;
  private final ServiceRequest sr;
  private final Color color;

  public ServiceRequestCard(ServiceRequest serviceRequest, ServiceRequestBacklog b) {
    sr = serviceRequest;
    backlog = b;
    color = getServiceRequestColor();
    location = sr.getLocation();
  }

  public HBox getCard(double width, double height) {
    // Setup grid
    HBox card = new HBox();
    card.setEffect(new DropShadow(5, color));
    card.setBackground(
        new Background(
            new BackgroundFill(Color.WHITE, new CornerRadii(BORDERRADIUS), Insets.EMPTY)));

    createSpacer(card);
    card.getChildren().add(getDoneCheckbox());
    createSpacer(card);

    GridPane titleAndDescription = new GridPane();
    titleAndDescription.setAlignment(Pos.CENTER);
    titleAndDescription.add(getTitleText(), 0, 0);
    titleAndDescription.add(getSeparatorH(), 0, 1);
    titleAndDescription.add(getDescriptionText(), 0, 2);
    card.getChildren().add(titleAndDescription);
    createSpacer(card);

    GridPane detailsGrid = new GridPane();
    detailsGrid.setAlignment(Pos.CENTER);
    detailsGrid.setHgap(20);
    detailsGrid.add(generateDetailText("Patient Name: "), 2, 0);
    detailsGrid.add(getSeparatorH(), 2, 1);
    detailsGrid.add(generateDetailText("Location: "), 2, 2);
    detailsGrid.add(getSeparatorH(), 2, 3);
    detailsGrid.add(generateDetailText("Floor: "), 2, 4);
    detailsGrid.add(getSeparatorH(), 2, 5);
    detailsGrid.add(generateDetailText("Status: "), 2, 6);
    detailsGrid.add(getSeparatorH(), 2, 7);

    detailsGrid.add(generateDetailText(patientName), 3, 0);
    detailsGrid.add(getSeparatorH(), 3, 1);
    detailsGrid.add(generateDetailText(location.getLongName()), 3, 2);
    detailsGrid.add(getSeparatorH(), 3, 3);
    detailsGrid.add(generateDetailText(location.getFloor().name()), 3, 4);
    detailsGrid.add(getSeparatorH(), 3, 5);
    detailsGrid.add(generateDetailText(sr.getStatus().name()), 3, 6);
    detailsGrid.add(getSeparatorH(), 3, 7);
    card.getChildren().add(detailsGrid);
    createSpacer(card);

    card.setAlignment(Pos.CENTER_RIGHT);
    card.setPrefSize(width, height);
    card.setFillHeight(false);
    setHoverStyling(card);
    return card;
  }

  private Text generateDetailText(String text) {
    Text detailText = new Text(text);
    detailText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
    detailText.setFill(Color.BLACK);
    detailText.setTextAlignment(TextAlignment.RIGHT);
    return detailText;
  }

  private HBox getTitleText() {
    HBox textBox = new HBox();
    Text titleText = new Text(sr.getDBType().shortName());
    titleText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
    textBox.getChildren().add(titleText);
    Text srText = new Text(" Service Request");
    srText.setFont(Font.font("Arial", 12));
    srText.setFill(Color.LIGHTGRAY);
    textBox.getChildren().add(srText);
    textBox.setAlignment(Pos.BASELINE_CENTER);
    return textBox;
  }

  // Commit
  private JFXCheckBox getDoneCheckbox() {
    JFXCheckBox doneBox = new JFXCheckBox();
    doneBox.setCheckedColor(Color.LIGHTBLUE);
    doneBox.setCheckedColor(Color.LIGHTGRAY);
    doneBox.setScaleX(2);
    doneBox.setScaleY(2);
    doneBox.setOnMouseClicked(
        (event -> {
          deleteRequest(backlog);
        }));
    Tooltip t = new Tooltip("Click to delete");
    Tooltip.install(doneBox, t);
    return doneBox;
  }

  private Text getDescriptionText() {
    Text descriptionText = new Text(sr.getLocation().getLongName());
    // TODO This will be SR Description field in future ^
    descriptionText.setFont(Font.font("Arial", 12));
    descriptionText.setFill(Color.DARKGRAY);
    descriptionText.setTextAlignment(TextAlignment.CENTER);
    return descriptionText;
  }

  private Separator getSeparatorH() {
    Separator titleSeparator = new Separator();
    titleSeparator.setScaleX(1);
    titleSeparator.setScaleY(1);
    titleSeparator.setHalignment(HPos.CENTER);
    return titleSeparator;
  }

  private void deleteRequest(ServiceRequestBacklog b) {
    // TODO Implement this method
  }

  public void setPatientName(String patientName) {
    this.patientName = patientName;
  } // TODO change this to SR patient field

  public void setLocation(Location location) {
    this.location = location;
  }

  public void setFloor(FloorType floor) {
    this.location.setFloor(floor);
  }

  public void setStatus(ServiceRequestStatus status) {
    this.sr.setStatus(status);
  }

  private void setHoverStyling(HBox c) {
    c.setOnMouseEntered(
        e -> {
          c.setBackground(
              new Background(
                  new BackgroundFill(
                      Color.LIGHTGRAY, new CornerRadii(BORDERRADIUS), Insets.EMPTY)));
        });

    c.setOnMouseExited(
        e -> {
          c.setBackground(
              new Background(
                  new BackgroundFill(Color.WHITE, new CornerRadii(BORDERRADIUS), Insets.EMPTY)));
        });
  }

  public ServiceRequest getServiceRequest() {
    return this.sr;
  }

  private void createSpacer(HBox c) {
    final Region spacer = new Region();
    // Make it always grow or shrink according to the available space
    HBox.setHgrow(spacer, Priority.ALWAYS);
    c.getChildren().add(spacer);
  }

  private Color getServiceRequestColor() {
    return this.sr.getDBType().getColor();
  }
}

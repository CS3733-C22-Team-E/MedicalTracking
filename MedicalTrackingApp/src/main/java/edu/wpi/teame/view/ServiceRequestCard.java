package edu.wpi.teame.view;

import com.jfoenix.controls.JFXCheckBox;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.FloorType;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import edu.wpi.teame.model.serviceRequests.ServiceRequest;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class ServiceRequestCard {

  private int hexColor;
  private String title;
  private String description;
  private DataBaseObjectType type;
  private double WIDTH;
  private double HEIGHT;
  private ServiceRequestBacklog backlog;
  private int backlogID;

  // Styling
  private Color BORDERCOLOR = Color.GREEN;
  private int BORDERRADIUS = 10;
  private int BORDERWIDTH = 5;
  private Border BORDER =
      new Border(
          new BorderStroke(
              BORDERCOLOR,
              BorderStrokeStyle.SOLID,
              new CornerRadii(BORDERRADIUS),
              new BorderWidths(BORDERWIDTH)));

  private Color BORDERHOVERCOLOR = Color.BLUE;
  private Border HOVERBOARDER =
      new Border(
          new BorderStroke(
              BORDERHOVERCOLOR,
              BorderStrokeStyle.SOLID,
              new CornerRadii(BORDERRADIUS),
              new BorderWidths(BORDERWIDTH)));

  // Details
  private String patientName;
  private Location location;
  private String floor;
  private String status;
  private final ServiceRequest sr;

  public ServiceRequestCard(
      ServiceRequest serviceRequest, int SRColor, double cardWidth, double cardHeight, int ID) {
    sr = serviceRequest;
    hexColor = SRColor;
    title = serviceRequest.getDBType().toString();
    description =
        serviceRequest
            .getLocation()
            .getLongName(); // TODO This will be SR Description field in future
    type = serviceRequest.getDBType();
    WIDTH = cardWidth;
    HEIGHT = cardHeight;
    backlogID = ID;

    floor = sr.getLocation().getFloor().name();
    location = sr.getLocation();
    status = sr.getStatus().toString();
  }

  // TODO Cards are displayed with wrong width
  // TODO Cards are displayed with funky internal spacing (Squished!)
  public HBox getCard(double width, double height) {
    // Setup grid
    HBox card = new HBox();
    card.setBorder(BORDER);
    card.setBackground(
        new Background(
            new BackgroundFill(Color.WHITE, new CornerRadii(BORDERRADIUS), Insets.EMPTY)));
    card.setPrefSize(width, height);
    setHoverStyling(card);

    card.getChildren().add(getDoneCheckbox());

    GridPane titleAndDescription = new GridPane();
    titleAndDescription.setAlignment(Pos.CENTER);
    titleAndDescription.add(getTitleText(), 0, 0);
    titleAndDescription.add(getSeparatorH(), 0, 1);
    titleAndDescription.add(getDescriptionText(), 0, 2);
    card.getChildren().add(titleAndDescription);

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
    detailsGrid.add(generateDetailText(status), 3, 6);
    detailsGrid.add(getSeparatorH(), 3, 7);

    card.getChildren().add(detailsGrid);

    return card;
  }

  private Text generateDetailText(String text) {
    Text detailText = new Text(text);
    detailText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
    detailText.setFill(Color.BLACK);
    detailText.setTextAlignment(TextAlignment.RIGHT);
    return detailText;
  }

  private Text getTitleText() {
    Text titleText = new Text(title);
    titleText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
    titleText.setTextAlignment(TextAlignment.CENTER);
    return titleText;
  }

  // Commit
  private JFXCheckBox getDoneCheckbox() {
    JFXCheckBox doneBox = new JFXCheckBox();
    doneBox.setCheckedColor(Color.LIGHTBLUE);
    doneBox.setCheckedColor(Color.LIGHTGRAY);
    doneBox.setAlignment(Pos.CENTER_LEFT);
    doneBox.setScaleX(2);
    doneBox.setScaleY(2);
    doneBox.setPadding(new Insets(40, 40, 40, 40));
    doneBox.setOnMouseClicked(
        (event -> {
          deleteRequest(backlog);
        }));
    Tooltip t = new Tooltip("Click to delete");
    Tooltip.install(doneBox, t);
    return doneBox;
  }

  private Text getDescriptionText() {
    Text descriptionText = new Text(description);
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
          c.setBorder(HOVERBOARDER);
          c.setBackground(
              new Background(
                  new BackgroundFill(
                      Color.LIGHTGRAY, new CornerRadii(BORDERRADIUS), Insets.EMPTY)));
        });

    c.setOnMouseExited(
        e -> {
          c.setBorder(BORDER);
          c.setBackground(
              new Background(
                  new BackgroundFill(Color.WHITE, new CornerRadii(BORDERRADIUS), Insets.EMPTY)));
        });
  }

  public ServiceRequest getServiceRequest() {
    return this.sr;
  }
}

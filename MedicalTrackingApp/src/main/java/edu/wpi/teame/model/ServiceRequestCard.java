package edu.wpi.teame.model;

import com.jfoenix.controls.JFXCheckBox;
import edu.wpi.teame.model.enums.ServiceRequestTypes;
import javafx.geometry.Pos;
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
  private ServiceRequestTypes type;
  private double WIDTH;
  private double HEIGHT;
  private ServiceRequestBacklog backlog;
  private int backlogID;

  // Styling
  private Color BORDERCOLOR = Color.GREEN;
  private int BORDERRADIUS = 10;
  private int BORDERWIDTH = 5;

  public ServiceRequestCard(
      ServiceRequestBacklog backlog,
      ServiceRequestTypes SRType,
      String SRDescription,
      String SRTitle,
      int SRColor,
      double cardWidth,
      double cardHeight,
      int ID) {
    backlog = backlog;
    hexColor = SRColor;
    title = SRTitle;
    description = SRDescription;
    type = SRType;
    WIDTH = cardWidth;
    HEIGHT = cardHeight;
    backlogID = ID;
  }

  // TODO Cards are displayed with wrong width
  // TODO Cards are displayed with funky internal spacing (Squished!)
  public HBox getCard(double width, double height) {
    // Setup grid
    HBox card = new HBox();
    card.setBorder(
        new Border(
            new BorderStroke(
                BORDERCOLOR,
                BorderStrokeStyle.SOLID,
                new CornerRadii(BORDERRADIUS),
                new BorderWidths(BORDERWIDTH))));
    card.setPrefSize(width, height);

    card.getChildren().add(getDoneCheckbox());

    GridPane titleAndDescription = new GridPane();
    titleAndDescription.setAlignment(Pos.CENTER);
    titleAndDescription.add(getTitleText(), 0, 0);
    titleAndDescription.add(getDescriptionText(), 0, 1);
    card.getChildren().add(titleAndDescription);

    return card;
  }

  private Text getTitleText() {
    Text titleText = new Text(title);
    titleText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
    titleText.setTextAlignment(TextAlignment.CENTER);
    return titleText;
  }

  private JFXCheckBox getDoneCheckbox() {
    JFXCheckBox doneBox = new JFXCheckBox();
    doneBox.setCheckedColor(Color.LIGHTBLUE);
    doneBox.setCheckedColor(Color.LIGHTGRAY);
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
    descriptionText.setFill(Color.LIGHTGRAY);
    descriptionText.setTextAlignment(TextAlignment.CENTER);
    return descriptionText;
  }

  private void deleteRequest(ServiceRequestBacklog b) {
    // TODO Implement this method
  }
}

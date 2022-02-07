package edu.wpi.teame.model;

import com.jfoenix.controls.JFXCheckBox;
import edu.wpi.teame.model.enums.ServiceRequestTypes;
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
  private Color BORDERCOLOR = Color.LIGHTGRAY;

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

  public GridPane getCard(double width, double height) {
    // Setup grid
    GridPane card = new GridPane();
    card.setBorder(
        new Border(
            new BorderStroke(
                BORDERCOLOR, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));
    card.setPrefSize(width, height);

    card.add(getDoneCheckbox(), 0, 0);
    card.add(getTitleText(), 1, 0);

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

  private void deleteRequest(ServiceRequestBacklog b) {
    b.removeServiceRequest(this.backlogID);
    System.out.println("Deleting ID: " + this.backlogID);
  }
}

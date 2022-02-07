package edu.wpi.teame.model;

import edu.wpi.teame.model.enums.ServiceRequestTypes;
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

  public ServiceRequestCard(
      ServiceRequestTypes SRType,
      String SRDescription,
      String SRTitle,
      int SRColor,
      double cardWidth,
      double cardHeight) {
    hexColor = SRColor;
    title = SRTitle;
    description = SRDescription;
    type = SRType;
    WIDTH = cardWidth;
    HEIGHT = cardHeight;
  }

  public GridPane getCard(double width, double height) {
    GridPane card = new GridPane();
    card.setBorder(
        new Border(
            new BorderStroke(
                Color.DARKGREY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));
    card.setPrefSize(width, height);
    Text titleText = new Text(title);
    titleText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
    titleText.setTextAlignment(TextAlignment.CENTER);
    card.add(titleText, 0, 0);
    return card;
  }
}

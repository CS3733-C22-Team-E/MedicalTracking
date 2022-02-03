package edu.wpi.teame.model;

import edu.wpi.teame.App;
import edu.wpi.teame.model.enums.SortOrder;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;

public class StyledTab extends Tab implements Comparable<StyledTab> {
  public static final double Height = 250;
  public static final double Width = 35;

  private SortOrder tabOrder;
  private String tabPageUrl;
  private String tabName;
  private Image tabIcon;

  public StyledTab(String name, SortOrder order, String pageUrl) throws IOException {
    tabPageUrl = pageUrl;
    tabOrder = order;
    tabName = name;

    Label label = new Label(tabName);
    label.setTextAlignment(TextAlignment.CENTER);
    label.setStyle("-fx-text-fill: -fx-text-color");
    label.setRotate(90.0);

    AnchorPane anchorPane = new AnchorPane();
    anchorPane.getChildren().add(label);

    Parent pageNode = new FXMLLoader(App.class.getResource(tabPageUrl)).load();
    pageNode.setStyle("@../css/mainStyle.css");
    GridPane gridPane = createContentPane();
    gridPane.add(pageNode, 0, 0);

    setGraphic(anchorPane);
    setContent(gridPane);
    setText("");
  }

  private GridPane createContentPane() {
    GridPane gridPane = new GridPane();
    gridPane.setAlignment(Pos.CENTER);

    ColumnConstraints columnConstraints = new ColumnConstraints();
    RowConstraints rowConstraints = new RowConstraints();
    columnConstraints.setHalignment(HPos.CENTER);
    columnConstraints.setHgrow(Priority.ALWAYS);
    rowConstraints.setValignment(VPos.CENTER);
    rowConstraints.setVgrow(Priority.ALWAYS);

    gridPane.getColumnConstraints().add(columnConstraints);
    gridPane.getRowConstraints().add(rowConstraints);
    return gridPane;
  }

  @Override
  public int compareTo(StyledTab o) {
    int order = tabOrder.compareTo(o.tabOrder);
    if (order != 0) {
      return order;
    }
    return tabName.compareTo(o.tabName);
  }
}

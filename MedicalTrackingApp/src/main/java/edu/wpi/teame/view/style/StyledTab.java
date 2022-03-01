package edu.wpi.teame.view.style;

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
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;

public class StyledTab extends Tab implements Comparable<StyledTab> {
  public static final double Height = 250;
  public static final double Width = 35;

  private AnchorPane anchorPane;
  private ImageView imageView;
  private Label tabText;

  public Object controller;
  private Parent tabPage;
  private Image tabIcon;

  private SortOrder tabOrder;
  private String tabPageUrl;
  private String tabName;

  public StyledTab(String name, SortOrder order, Parent page) throws IOException {
    tabPageUrl = null;
    tabOrder = order;
    tabName = name;
    tabPage = page;
    setUpTab();
  }

  public StyledTab(String name, SortOrder order, Parent page, Image icon) throws IOException {
    tabPageUrl = null;
    tabOrder = order;
    tabName = name;
    tabPage = page;
    tabIcon = icon;
    setUpTab();
  }

  public StyledTab(String name, SortOrder order, String pageUrl) throws IOException {
    tabPageUrl = pageUrl;
    tabOrder = order;
    tabName = name;
    tabIcon = null;

    FXMLLoader loader = new FXMLLoader(App.class.getResource(tabPageUrl));
    tabPage = loader.load();
    controller = loader.getController();
    setUpTab();
  }

  public StyledTab(String name, SortOrder order, String pageUrl, Image icon) throws IOException {
    tabPageUrl = pageUrl;
    tabOrder = order;
    tabName = name;
    tabIcon = icon;

    FXMLLoader loader = new FXMLLoader(App.class.getResource(tabPageUrl));
    tabPage = loader.load();
    controller = loader.getController();
    setUpTab();
  }

  public void setTabPage(Parent p) {
    tabPage = p;
  }

  public void toggleTabSize(boolean showImage) {
    if (showImage) {
      anchorPane.getChildren().remove(0);
      anchorPane.getChildren().add(imageView);
      return;
    }

    anchorPane.getChildren().remove(0);
    anchorPane.getChildren().add(tabText);
  }

  private void setUpTab() {
    tabText = new Label(tabName);
    tabText.setTextAlignment(TextAlignment.CENTER);
    tabText.setRotate(90.0);

    imageView = new ImageView();
    imageView.setImage(tabIcon);
    imageView.setFitHeight(50);
    imageView.setFitWidth(50);
    imageView.setRotate(90);

    anchorPane = new AnchorPane();
    anchorPane.getChildren().add(tabText);

    tabPage.setStyle("@../css/mainStyle.css");
    GridPane gridPane = createContentPane();
    gridPane.add(tabPage, 0, 0);

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

  public AnchorPane getAnchorPane() {
    return anchorPane;
  }

  public ImageView getImageView() {
    return imageView;
  }

  public Label getTabText() {
    return tabText;
  }
}

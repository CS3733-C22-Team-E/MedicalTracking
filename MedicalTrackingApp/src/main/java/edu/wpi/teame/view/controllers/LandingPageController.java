package edu.wpi.teame.view.controllers;

import edu.wpi.teame.App;
import edu.wpi.teame.model.enums.FloorType;
import edu.wpi.teame.model.enums.SortOrder;
import edu.wpi.teame.view.StyledTab;
import edu.wpi.teame.view.backlog.ServiceRequestBacklog;
import edu.wpi.teame.view.map.Map;
import edu.wpi.teame.view.map.MapSideView;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import lombok.SneakyThrows;

public class LandingPageController implements Initializable {
  @FXML public AnchorPane mainAnchorPane;
  private boolean shouldEnlarge = true;
  @FXML public TabPane mainTabPane;

  @Override
  @SneakyThrows
  public void initialize(URL location, ResourceBundle resources) {
    mainAnchorPane.setPrefHeight(Screen.getPrimary().getBounds().getHeight());
    mainAnchorPane.setPrefWidth(Screen.getPrimary().getBounds().getWidth());
    mainAnchorPane.autosize();
    // Get the tab content size using our init tab
    double tabContentHeight =
        mainTabPane.getTabs().get(0).getContent().getBoundsInParent().getHeight();
    double tabContentWidth =
        mainTabPane.getTabs().get(0).getContent().getBoundsInParent().getWidth();
    mainTabPane.getTabs().clear();

    mainTabPane.setTabMaxHeight(StyledTab.Height);
    mainTabPane.setMaxWidth(StyledTab.Width);
    mainTabPane.setRotateGraphic(true);

    List<StyledTab> tabs = new ArrayList<>();
    tabs.add(
        new StyledTab(
            "Home",
            SortOrder.First,
            "view/HomePage.fxml",
            new Image(App.class.getResource("images/Icons/pageIcons/Home.png").toString())));

    StyledTab directoryTab =
        new StyledTab(
            "Service Request Directory",
            SortOrder.ByName,
            "view/ServiceRequestDirectoryPage.fxml",
            new Image(App.class.getResource("images/Icons/pageIcons/SRDirectory.png").toString()));
    directoryTab.setOnSelectionChanged(
        new EventHandler<Event>() {
          @Override
          public void handle(Event event) {
            shouldEnlarge = !shouldEnlarge;
            System.out.println(shouldEnlarge);
          }
        });
    tabs.add(directoryTab);

    Map mapView = new Map(FloorType.ThirdFloor, this);
    StyledTab mapTab =
        new StyledTab(
            "Hospital Map",
            SortOrder.ByName,
            mapView.getMapScene(tabContentHeight, tabContentWidth),
            new Image(App.class.getResource("images/Icons/pageIcons/MapView.png").toString()));
    mapTab.setOnSelectionChanged(
        new EventHandler<Event>() {
          @Override
          public void handle(Event event) {
            try {
              mapView.getFromDB();
            } catch (SQLException e) {
              e.printStackTrace();
            }
          }
        });
    tabs.add(mapTab);

    MapSideView mapSideView = new MapSideView();
    StyledTab mapSideViewTab =
        new StyledTab(
            "Hospital Map Side-View",
            SortOrder.ByName,
            mapSideView.getMapScene(),
            new Image(App.class.getResource("images/Icons/pageIcons/SideView.png").toString()));
    tabs.add(mapSideViewTab);

    ServiceRequestBacklog backlogView =
        new ServiceRequestBacklog(
            Screen.getPrimary().getBounds().getWidth() - StyledTab.Width,
            Screen.getPrimary().getBounds().getHeight());
    StyledTab backlogTab =
        new StyledTab(
            "Service Request Backlog",
            SortOrder.ByName,
            backlogView.getBacklogScene(),
            new Image(App.class.getResource("images/Icons/pageIcons/SRBacklog.png").toString()));
    backlogTab.setOnSelectionChanged(
        (event) -> {
          if (backlogTab.isSelected()) {
            try {
              backlogTab.setTabPage(backlogView.getBacklogScene());
            } catch (SQLException e) {
              e.printStackTrace();
            }
          }
        });
    tabs.add(backlogTab);

    tabs.add(
        new StyledTab(
            "Settings",
            SortOrder.ByName,
            "view/tabs/SettingsPage.fxml",
            new Image(App.class.getResource("images/Icons/pageIcons/Settings.png").toString())));

    tabs.sort(StyledTab::compareTo);
    mainTabPane.getTabs().setAll(tabs);

    shouldEnlarge = false;
    updateTabSize();
  }

  @FXML
  public void updateTabSize() {
    if (shouldEnlarge) {
      mainTabPane.setTabMaxHeight(StyledTab.Height);
      mainTabPane.setTabMinHeight(StyledTab.Height);
      mainTabPane.setTabMaxWidth(StyledTab.Width);
      mainTabPane.setTabMinWidth(StyledTab.Width);
    } else {
      mainTabPane.setTabMaxHeight(75);
      mainTabPane.setTabMinHeight(75);
      mainTabPane.setTabMaxWidth(75);
      mainTabPane.setTabMinWidth(75);
    }

    for (Tab tab : mainTabPane.getTabs()) {
      ((StyledTab) tab).toggleTabSize(!shouldEnlarge);
    }
  }
}

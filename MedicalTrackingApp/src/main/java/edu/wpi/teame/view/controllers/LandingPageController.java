package edu.wpi.teame.view.controllers;

import edu.wpi.teame.model.enums.FloorType;
import edu.wpi.teame.model.enums.SortOrder;
import edu.wpi.teame.view.Map;
import edu.wpi.teame.view.MapSideView;
import edu.wpi.teame.view.ServiceRequestBacklog;
import edu.wpi.teame.view.StyledTab;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import lombok.SneakyThrows;

public class LandingPageController implements Initializable {
  @FXML public TabPane mainTabPane;
  @FXML public AnchorPane mainAnchorPane;

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
    tabs.add(new StyledTab("Home", SortOrder.First, "view/HomePage.fxml"));
    tabs.add(
        new StyledTab(
            "Service Request Directory",
            SortOrder.ByName,
            "view/ServiceRequestDirectoryPage.fxml"));

    Map mapView = new Map(FloorType.ThirdFloor, this);
    StyledTab mapTab =
        new StyledTab(
            "Hospital Map",
            SortOrder.ByName,
            mapView.getMapScene(tabContentHeight, tabContentWidth));
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
        new StyledTab("Hospital Map Side-View", SortOrder.ByName, mapSideView.getMapScene());
    tabs.add(mapSideViewTab);

    ServiceRequestBacklog backlogView =
        new ServiceRequestBacklog(
            Screen.getPrimary().getBounds().getWidth() - StyledTab.Width,
            Screen.getPrimary().getBounds().getHeight());
    StyledTab backlogTab =
        new StyledTab("Service Request Backlog", SortOrder.ByName, backlogView.getBacklogScene());
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

    tabs.add(new StyledTab("Settings", SortOrder.ByName, "view/tabs/SettingsPage.fxml"));

    tabs.sort(StyledTab::compareTo);
    mainTabPane.getTabs().setAll(tabs);
  }
}

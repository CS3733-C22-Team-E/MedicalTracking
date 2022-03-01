package edu.wpi.teame.view.controllers;

import edu.wpi.teame.App;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.objectManagers.CredentialManager;
import edu.wpi.teame.model.enums.AccessLevel;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.FloorType;
import edu.wpi.teame.model.enums.SortOrder;
import edu.wpi.teame.view.backlog.ServiceRequestBacklog;
import edu.wpi.teame.view.map.Map;
import edu.wpi.teame.view.map.MapSideView;
import edu.wpi.teame.view.style.IStyleable;
import edu.wpi.teame.view.style.StyleManager;
import edu.wpi.teame.view.style.StyledTab;
import edu.wpi.teame.view.style.TabHoverAnimation;
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

public class LandingPageController implements Initializable, IStyleable {
  @FXML public AnchorPane mainAnchorPane;
  @FXML public TabPane mainTabPane;

  private StyledTab credentialManagementPage = null;
  private boolean shouldEnlarge = true;
  private StyledTab adminDBPage = null;
  public StyledTab homeTabPage = null;
  public StyledTab mapTabPage = null;
  public StyledTab backlogTab = null;

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
    homeTabPage =
        new StyledTab(
            "Home",
            SortOrder.First,
            "view/HomePage.fxml",
            new Image(App.class.getResource("images/Icons/pageIcons/Home.png").toString()));
    TabHoverAnimation.install(homeTabPage);
    tabs.add(homeTabPage);

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
    TabHoverAnimation.install(directoryTab);
    tabs.add(directoryTab);

    Map mapView = new Map(FloorType.ThirdFloor, this);
    mapTabPage =
        new StyledTab(
            "Hospital Map",
            SortOrder.ByName,
            mapView.getMapScene(tabContentHeight, tabContentWidth),
            new Image(App.class.getResource("images/Icons/pageIcons/MapView.png").toString()));
    mapTabPage.setOnSelectionChanged(
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
    TabHoverAnimation.install(mapTabPage);
    tabs.add(mapTabPage);

    StyledTab sideViewMap =
        new StyledTab(
            "Side View Map",
            SortOrder.ByName,
            "view/SideViewMap.fxml",
            new Image(App.class.getResource("images/Icons/pageIcons/SideView.png").toString()));
    TabHoverAnimation.install(sideViewMap);
    tabs.add(sideViewMap);

    ServiceRequestBacklog backlogView =
        new ServiceRequestBacklog(
            Screen.getPrimary().getBounds().getWidth() - StyledTab.Width - 20,
            Screen.getPrimary().getBounds().getHeight());
    backlogTab =
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
    TabHoverAnimation.install(backlogTab);
    tabs.add(backlogTab);

    // Only load the DB Management page on Admin log in
    adminDBPage =
        new StyledTab(
            "DB Management",
            SortOrder.ByName,
            "view/tabs/DBManagementPage.fxml",
            new Image(App.class.getResource("images/Icons/pageIcons/AdminDBIcon.png").toString()));
    TabHoverAnimation.install(adminDBPage);
    tabs.add(adminDBPage);

    StyledTab covidTab =
        new StyledTab(
            "Covid-19 Info",
            SortOrder.ByName,
            "view/tabs/CovidInfoPage.fxml",
            new Image(App.class.getResource("images/Icons/pageIcons/CovidInfo.png").toString()));
    TabHoverAnimation.install(covidTab);
    tabs.add(covidTab);

    StyledTab settingsTab =
        new StyledTab(
            "Settings",
            SortOrder.ByName,
            "view/tabs/SettingsPage.fxml",
            new Image(App.class.getResource("images/Icons/pageIcons/Settings.png").toString()));
    TabHoverAnimation.install(settingsTab);
    tabs.add(settingsTab);

    credentialManagementPage =
        new StyledTab(
            "Credential Manager",
            SortOrder.ByName,
            "view/tabs/CredentialManagementPage.fxml",
            new Image(
                App.class
                    .getResource("images/Icons/pageIcons/CredentialManagement.png")
                    .toString()));
    TabHoverAnimation.install(settingsTab);
    tabs.add(credentialManagementPage);

    tabs.sort(StyledTab::compareTo);
    mainTabPane.getTabs().setAll(tabs);

    shouldEnlarge = false;
    updateTabSize();

    // Set style
    StyleManager.getInstance().subscribe(this);
  }

  @FXML
  public void toggleAdminDBPage() throws SQLException {
    AccessLevel currentAccess =
        ((CredentialManager) DBManager.getInstance().getManager(DataBaseObjectType.Credential))
            .getCurrentUser()
            .getAccessLevel();
    switch (currentAccess) {
      case Admin:
        mainTabPane.getTabs().add(adminDBPage);
        mainTabPane.getTabs().add(credentialManagementPage);
        break;

      default:
      case Staff:
        mainTabPane.getTabs().remove(adminDBPage);
        mainTabPane.getTabs().remove(credentialManagementPage);
        break;
    }
  }

  private void updateTabSize() {
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

  @Override
  public void updateStyle() {
    StyleManager.getInstance().getCurrentStyle().setPaneStyle(mainAnchorPane, true);
    StyleManager.getInstance().getCurrentStyle().setTabPaneStyle(mainTabPane);
  }
}

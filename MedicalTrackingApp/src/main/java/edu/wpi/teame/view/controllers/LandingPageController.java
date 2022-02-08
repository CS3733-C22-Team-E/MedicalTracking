package edu.wpi.teame.view.controllers;

import edu.wpi.teame.model.enums.FloorType;
import edu.wpi.teame.model.enums.SortOrder;
import edu.wpi.teame.view.PannableView;
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
  @FXML private AnchorPane mainAnchorPane;
  @FXML private TabPane mainTabPane;

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

    PannableView mapView = new PannableView(FloorType.ThirdFloor);
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

    ServiceRequestBacklog backlogView =
        new ServiceRequestBacklog(
            Screen.getPrimary().getBounds().getWidth() - tabContentWidth,
            Screen.getPrimary().getBounds().getHeight() - tabContentHeight);
    StyledTab backlogTab =
        new StyledTab("Service Request Backlog", SortOrder.ByName, backlogView.getBacklogScene());
    backlogTab.setOnSelectionChanged(
        (event) -> {
          // TODO something on click
        });
    tabs.add(backlogTab);

    tabs.add(
        new StyledTab(
            "Medical Equipment Delivery",
            SortOrder.ByName,
            "view/serviceRequests/MedicalEquipmentDeliveryServiceRequestPage.fxml"));

    tabs.add(
        new StyledTab(
            "Food Delivery",
            SortOrder.ByName,
            "view/serviceRequests/FoodDeliveryServiceRequestPage.fxml"));

    tabs.add(
        new StyledTab(
            "Medicine Delivery",
            SortOrder.ByName,
            "view/serviceRequests/MedicineDeliveryServiceRequestPage.fxml"));

    tabs.add(
        new StyledTab(
            "Gift And Floral Delivery",
            SortOrder.ByName,
            "view/serviceRequests/GiftAndFloralServiceRequestPage.fxml"));

    tabs.add(
        new StyledTab(
            "Language Services",
            SortOrder.ByName,
            "view/serviceRequests/LanguageInterpreterServiceRequestPage.fxml"));

    tabs.add(
        new StyledTab(
            "Sanitation Services",
            SortOrder.ByName,
            "view/serviceRequests/SanitationServiceRequestPage.fxml"));

    tabs.add(
        new StyledTab(
            "Laundry Services",
            SortOrder.ByName,
            "view/serviceRequests/LaundryServiceRequestPage.fxml"));

    tabs.add(
        new StyledTab(
            "Religious Services",
            SortOrder.ByName,
            "view/serviceRequests/ReligiousServiceRequestPage.fxml"));

    tabs.add(
        new StyledTab(
            "Internal Patient Transportation",
            SortOrder.ByName,
            "view/serviceRequests/InternalPatientTransportationServiceRequestPage.fxml"));

    tabs.add(
        new StyledTab(
            "External Patient Transportation",
            SortOrder.ByName,
            "view/serviceRequests/ExternalPatientTransportationServiceRequestPage.fxml"));

    tabs.add(
        new StyledTab(
            "Audio/Video Services",
            SortOrder.ByName,
            "view/serviceRequests/AudioVisualServiceRequestPage.fxml"));

    tabs.add(
        new StyledTab(
            "Computer Services",
            SortOrder.ByName,
            "view/serviceRequests/ComputerServiceRequestPage.fxml"));

    tabs.add(
        new StyledTab(
            "Security Services",
            SortOrder.ByName,
            "view/serviceRequests/SecurityServiceRequestPage.fxml"));

    tabs.add(
        new StyledTab(
            "Facilities Maintenance",
            SortOrder.ByName,
            "view/serviceRequests/FacilitiesMaintenanceServiceRequestPage.fxml"));

    tabs.sort(StyledTab::compareTo);
    mainTabPane.getTabs().setAll(tabs);
  }
}

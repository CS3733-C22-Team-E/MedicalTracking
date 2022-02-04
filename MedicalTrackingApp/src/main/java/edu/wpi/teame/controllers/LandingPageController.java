package edu.wpi.teame.controllers;

import edu.wpi.teame.db.FloorType;
import edu.wpi.teame.model.PannableView;
import edu.wpi.teame.model.StyledTab;
import edu.wpi.teame.model.enums.SortOrder;
import java.net.URL;
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
    tabs.add(new StyledTab("Home", SortOrder.First, "views/HomePage.fxml"));

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
            mapView.getFromDB();
          }
        });
    tabs.add(mapTab);

    tabs.add(
        new StyledTab(
            "Medical Equipment Delivery",
            SortOrder.ByName,
            "views/serviceRequests/MedicalEquipmentDeliveryServiceRequestPage.fxml"));

    tabs.add(
        new StyledTab(
            "Food Delivery",
            SortOrder.ByName,
            "views/serviceRequests/FoodDeliveryServiceRequestPage.fxml"));

    tabs.add(
        new StyledTab(
            "Medicine Delivery",
            SortOrder.ByName,
            "views/serviceRequests/MedicineDeliveryServiceRequestPage.fxml"));

    tabs.add(
        new StyledTab(
            "Gift And Floral Delivery",
            SortOrder.ByName,
            "views/serviceRequests/GiftAndFloralServiceRequestPage.fxml"));

    tabs.add(
        new StyledTab(
            "Language Services",
            SortOrder.ByName,
            "views/serviceRequests/LanguageInterpreterServiceRequestPage.fxml"));

    tabs.add(
        new StyledTab(
            "Sanitation Services",
            SortOrder.ByName,
            "views/serviceRequests/SanitationServiceRequestPage.fxml"));

    tabs.add(
        new StyledTab(
            "Laundry Services",
            SortOrder.ByName,
            "views/serviceRequests/LaundryServiceRequestPage.fxml"));

    tabs.add(
        new StyledTab(
            "Religious Services",
            SortOrder.ByName,
            "views/serviceRequests/ReligiousServiceRequestPage.fxml"));

    tabs.add(
        new StyledTab(
            "Internal Patient Transportation",
            SortOrder.ByName,
            "views/serviceRequests/InternalPatientTransportationServiceRequestPage.fxml"));

    tabs.add(
        new StyledTab(
            "External Patient Transportation",
            SortOrder.ByName,
            "views/serviceRequests/ExternalPatientTransportationServiceRequestPage.fxml"));

    tabs.add(
        new StyledTab(
            "Audio/Video Services",
            SortOrder.ByName,
            "views/serviceRequests/AudioVisualServiceRequestPage.fxml"));

    tabs.add(
        new StyledTab(
            "Computer Services",
            SortOrder.ByName,
            "views/serviceRequests/ComputerServiceRequestPage.fxml"));

    tabs.add(
        new StyledTab(
            "Security Services",
            SortOrder.ByName,
            "views/serviceRequests/SecurityServiceRequestPage.fxml"));

    tabs.add(
        new StyledTab(
            "Facilities Maintenance",
            SortOrder.ByName,
            "views/serviceRequests/FacilitiesMaintenanceServiceRequestPage.fxml"));

    tabs.sort(StyledTab::compareTo);
    mainTabPane.getTabs().setAll(tabs);
  }
}

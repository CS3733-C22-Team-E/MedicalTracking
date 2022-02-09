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
            getPageUrl(ServiceRequestTypes.MedicalEquipmentDelivery)));

    tabs.add(
        new StyledTab(
            "Food Delivery", SortOrder.ByName, getPageUrl(ServiceRequestTypes.FoodDelivery)));

    tabs.add(
        new StyledTab(
            "Medicine Delivery",
            SortOrder.ByName,
            getPageUrl(ServiceRequestTypes.MedicineDelivery)));

    tabs.add(
        new StyledTab(
            "Gift And Floral Delivery",
            SortOrder.ByName,
            getPageUrl(ServiceRequestTypes.GiftAndFloral)));

    tabs.add(
        new StyledTab(
            "Language Services",
            SortOrder.ByName,
            getPageUrl(ServiceRequestTypes.LanguageInterpreter)));

    tabs.add(
        new StyledTab(
            "Sanitation Services", SortOrder.ByName, getPageUrl(ServiceRequestTypes.Sanitation)));

    tabs.add(
        new StyledTab(
            "Laundry Services", SortOrder.ByName, getPageUrl(ServiceRequestTypes.Laundry)));

    tabs.add(
        new StyledTab(
            "Religious Services", SortOrder.ByName, getPageUrl(ServiceRequestTypes.Religious)));

    tabs.add(
        new StyledTab(
            "Internal Patient Transportation",
            SortOrder.ByName,
            getPageUrl(ServiceRequestTypes.InternalPatientTransportation)));

    tabs.add(
        new StyledTab(
            "External Patient Transportation",
            SortOrder.ByName,
            getPageUrl(ServiceRequestTypes.ExternalPatientTransportation)));

    tabs.add(
        new StyledTab(
            "Audio/Video Services", SortOrder.ByName, getPageUrl(ServiceRequestTypes.AudioVisual)));

    tabs.add(
        new StyledTab(
            "Computer Services", SortOrder.ByName, getPageUrl(ServiceRequestTypes.Computer)));

    tabs.add(
        new StyledTab(
            "Security Services", SortOrder.ByName, getPageUrl(ServiceRequestTypes.Security)));

    tabs.add(
        new StyledTab(
            "Facilities Maintenance",
            SortOrder.ByName,
            getPageUrl(ServiceRequestTypes.FacilitiesMaintenance)));

    tabs.sort(StyledTab::compareTo);
    mainTabPane.getTabs().setAll(tabs);
  }

  private String getPageUrl(ServiceRequestTypes t) {
    String url = "views/serviceRequests/";
    switch (t) {
      case Laundry:
        url += "Laundry";
        break;
      case Computer:
        url += "Computer";
        break;
      case Security:
        url += "Security";
        break;
      case Religious:
        url += "Religious";
        break;
      case AudioVisual:
        url += "AudioVisual";
        break;
      case FoodDelivery:
        url += "FoodDelivery";
        break;
      case GiftAndFloral:
        url += "GiftAndFloral";
        break;
      case MedicalEquipmentDelivery:
        url += "MedicalEquipmentDelivery";
        break;
      case MedicineDelivery:
        url += "MedicineDelivery";
        break;
      case LanguageInterpreter:
        url += "LanguageInterpreter";
        break;
      case FacilitiesMaintenance:
        url += "FacilitiesMaintenance";
        break;
      case InternalPatientTransportation:
        url += "InternalPatientTransportation";
        break;
      case ExternalPatientTransportation:
        url += "ExternalPatientTransportation";
        break;
      case Sanitation:
        url += "Sanitation";
        break;
    }
    url += "ServiceRequestPage.fxml";
    return url;
  }
}

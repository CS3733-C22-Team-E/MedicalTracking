package edu.wpi.teame.view.controllers;

import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.FloorType;
import edu.wpi.teame.model.enums.SortOrder;
import edu.wpi.teame.view.Map;
import edu.wpi.teame.view.ServiceRequestBacklog;
import edu.wpi.teame.view.StyledTab;
import edu.wpi.teame.view.controllers.serviceRequests.MedicalEquipmentDeliveryServiceRequestPageServiceRequestController;
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
  @FXML public AnchorPane mainAnchorPane;
  @FXML public TabPane mainTabPane;
  public MedicalEquipmentDeliveryServiceRequestPageServiceRequestController test;

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

    ServiceRequestBacklog backlogView =
        new ServiceRequestBacklog(
            Screen.getPrimary().getBounds().getWidth() - tabContentWidth,
            Screen.getPrimary().getBounds().getHeight() - tabContentHeight);
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

    tabs.add(
        new StyledTab(
            "Medical Equipment Delivery",
            SortOrder.ByName,
            getPageUrl(DataBaseObjectType.MedicalEquipmentSR)));

    tabs.add(
        new StyledTab(
            "Food Delivery", SortOrder.ByName, getPageUrl(DataBaseObjectType.FoodDeliverySR)));

    tabs.add(
        new StyledTab(
            "Medicine Delivery",
            SortOrder.ByName,
            getPageUrl(DataBaseObjectType.MedicineDeliverySR)));

    tabs.add(
        new StyledTab(
            "Gift And Floral Delivery",
            SortOrder.ByName,
            getPageUrl(DataBaseObjectType.GiftAndFloralSR)));

    tabs.add(
        new StyledTab(
            "Language Services",
            SortOrder.ByName,
            getPageUrl(DataBaseObjectType.LanguageInterpreterSR)));

    tabs.add(
        new StyledTab(
            "Sanitation Services", SortOrder.ByName, getPageUrl(DataBaseObjectType.SanitationSR)));

    tabs.add(
        new StyledTab(
            "Laundry Services", SortOrder.ByName, getPageUrl(DataBaseObjectType.LaundrySR)));

    tabs.add(
        new StyledTab(
            "Religious Services", SortOrder.ByName, getPageUrl(DataBaseObjectType.ReligiousSR)));

    tabs.add(
        new StyledTab(
            "Internal Patient Transportation",
            SortOrder.ByName,
            getPageUrl(DataBaseObjectType.InternalPatientTransferSR)));

    tabs.add(
        new StyledTab(
            "External Patient Transportation",
            SortOrder.ByName,
            getPageUrl(DataBaseObjectType.ExternalPatientTransportation)));

    tabs.add(
        new StyledTab(
            "Audio/Video Services",
            SortOrder.ByName,
            getPageUrl(DataBaseObjectType.AudioVisualSR)));

    tabs.add(
        new StyledTab(
            "Computer Services", SortOrder.ByName, getPageUrl(DataBaseObjectType.ComputerSR)));

    tabs.add(
        new StyledTab(
            "Security Services", SortOrder.ByName, getPageUrl(DataBaseObjectType.SecuritySR)));

    tabs.add(
        new StyledTab(
            "Facilities Maintenance",
            SortOrder.ByName,
            getPageUrl(DataBaseObjectType.FacilitiesMaintenanceSR)));

    tabs.sort(StyledTab::compareTo);
    mainTabPane.getTabs().setAll(tabs);
    test =
        (MedicalEquipmentDeliveryServiceRequestPageServiceRequestController)
            tabs.get(11).controller;
  }

  private String getPageUrl(DataBaseObjectType t) {
    String url = "view/tabs/";
    switch (t) {
      case LaundrySR:
        url += "Laundry";
        break;
      case ComputerSR:
        url += "Computer";
        break;
      case SecuritySR:
        url += "Security";
        break;
      case ReligiousSR:
        url += "Religious";
        break;
      case AudioVisualSR:
        url += "AudioVisual";
        break;
      case FoodDeliverySR:
        url += "FoodDelivery";
        break;
      case GiftAndFloralSR:
        url += "GiftAndFloral";
        break;
      case MedicalEquipmentSR:
        url += "MedicalEquipmentDelivery";
        break;
      case MedicineDeliverySR:
        url += "MedicineDelivery";
        break;
      case LanguageInterpreterSR:
        url += "LanguageInterpreter";
        break;
      case FacilitiesMaintenanceSR:
        url += "FacilitiesMaintenance";
        break;
      case InternalPatientTransferSR:
        url += "InternalPatientTransportation";
        break;
      case ExternalPatientTransportation:
        url += "ExternalPatientTransportation";
        break;
      case SanitationSR:
        url += "Sanitation";
        break;
      default:
        return null;
    }
    url += "ServiceRequestPage.fxml";
    return url;
  }
}

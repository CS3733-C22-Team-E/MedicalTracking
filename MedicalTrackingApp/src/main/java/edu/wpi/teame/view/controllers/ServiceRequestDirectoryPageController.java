package edu.wpi.teame.view.controllers;

import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.SortOrder;
import edu.wpi.teame.view.style.IStyleable;
import edu.wpi.teame.view.style.StyleManager;
import edu.wpi.teame.view.style.StyledTab;
import edu.wpi.teame.view.style.TabHoverAnimation;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import lombok.SneakyThrows;

public class ServiceRequestDirectoryPageController implements Initializable, IStyleable {
  public static StyledTab medicalEquipmentSRTab;
  @FXML public AnchorPane mainAnchorPane;
  @FXML public TabPane mainTabPane;

  @Override
  @SneakyThrows
  public void initialize(URL location, ResourceBundle resources) {
    mainAnchorPane.setPrefHeight(Screen.getPrimary().getBounds().getHeight());
    mainAnchorPane.setPrefWidth(Screen.getPrimary().getBounds().getWidth());
    mainAnchorPane.autosize();
    mainTabPane.getTabs().clear();

    mainTabPane.setTabMaxHeight(StyledTab.Height);
    mainTabPane.setMaxWidth(StyledTab.Width);
    mainTabPane.setRotateGraphic(true);

    List<StyledTab> tabs = new ArrayList<>();
    medicalEquipmentSRTab =
        new StyledTab(
            "Equipment Delivery",
            SortOrder.ByName,
            getPageUrl(DataBaseObjectType.MedicalEquipmentSR));
    TabHoverAnimation.install(medicalEquipmentSRTab);
    tabs.add(medicalEquipmentSRTab);

    tabs.add(
        TabHoverAnimation.install(
            new StyledTab(
                "Food Delivery", SortOrder.ByName, getPageUrl(DataBaseObjectType.FoodDeliverySR))));

    tabs.add(
        TabHoverAnimation.install(
            new StyledTab(
                "Medicine Delivery",
                SortOrder.ByName,
                getPageUrl(DataBaseObjectType.MedicineDeliverySR))));

    tabs.add(
        TabHoverAnimation.install(
            new StyledTab(
                "Gift/Floral Delivery",
                SortOrder.ByName,
                getPageUrl(DataBaseObjectType.GiftAndFloralSR))));

    tabs.add(
        TabHoverAnimation.install(
            new StyledTab(
                "Language Services",
                SortOrder.ByName,
                getPageUrl(DataBaseObjectType.LanguageInterpreterSR))));

    tabs.add(
        TabHoverAnimation.install(
            new StyledTab(
                "Sanitation Services",
                SortOrder.ByName,
                getPageUrl(DataBaseObjectType.SanitationSR))));

    tabs.add(
        TabHoverAnimation.install(
            new StyledTab(
                "Laundry Services", SortOrder.ByName, getPageUrl(DataBaseObjectType.LaundrySR))));

    tabs.add(
        TabHoverAnimation.install(
            new StyledTab(
                "Religious Services",
                SortOrder.ByName,
                getPageUrl(DataBaseObjectType.ReligiousSR))));

    tabs.add(
        TabHoverAnimation.install(
            new StyledTab(
                "Internal Transportation",
                SortOrder.ByName,
                getPageUrl(DataBaseObjectType.InternalPatientTransferSR))));

    tabs.add(
        TabHoverAnimation.install(
            new StyledTab(
                "External Transportation",
                SortOrder.ByName,
                getPageUrl(DataBaseObjectType.ExternalPatientSR))));

    tabs.add(
        TabHoverAnimation.install(
            new StyledTab(
                "Audio/Video Services",
                SortOrder.ByName,
                getPageUrl(DataBaseObjectType.AudioVisualSR))));

    tabs.add(
        TabHoverAnimation.install(
            new StyledTab(
                "Computer Services", SortOrder.ByName, getPageUrl(DataBaseObjectType.ComputerSR))));

    tabs.add(
        TabHoverAnimation.install(
            new StyledTab(
                "Security Services", SortOrder.ByName, getPageUrl(DataBaseObjectType.SecuritySR))));

    tabs.add(
        TabHoverAnimation.install(
            new StyledTab(
                "Facilities Maintenance",
                SortOrder.ByName,
                getPageUrl(DataBaseObjectType.FacilitiesMaintenanceSR))));

    tabs.add(
        TabHoverAnimation.install(
            new StyledTab(
                "Mental Health Services",
                SortOrder.ByName,
                getPageUrl(DataBaseObjectType.MentalHealthSR))));

    tabs.add(
        TabHoverAnimation.install(
            new StyledTab(
                "Deceased Body Removal",
                SortOrder.ByName,
                getPageUrl(DataBaseObjectType.DeceasedBodySR))));

    tabs.add(
        TabHoverAnimation.install(
            new StyledTab(
                "Patient Discharge",
                SortOrder.ByName,
                getPageUrl(DataBaseObjectType.PatientDischargeSR))));

    tabs.sort(StyledTab::compareTo);
    mainTabPane.getTabs().setAll(tabs);

    StyleManager.getInstance().subscribe(this);
  }

  @Override
  public void updateStyle() {
    StyleManager.getInstance().getCurrentStyle().setPaneStyle(mainAnchorPane, true);
    StyleManager.getInstance().getCurrentStyle().setTabPaneStyle(mainTabPane);
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
      case ExternalPatientSR:
        url += "ExternalPatientTransportation";
        break;
      case SanitationSR:
        url += "Sanitation";
        break;
      case MentalHealthSR:
        url += "MentalHealth";
        break;
      case DeceasedBodySR:
        url += "DeceasedBodyRemoval";
        break;
      case PatientDischargeSR:
        url += "PatientDischarge";
        break;
      default:
        return null;
    }
    url += "ServiceRequestPage.fxml";
    return url;
  }
}

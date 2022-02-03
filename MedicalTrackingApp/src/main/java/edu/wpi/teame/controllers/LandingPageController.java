package edu.wpi.teame.controllers;

import edu.wpi.teame.App;
import edu.wpi.teame.model.StyledTab;
import edu.wpi.teame.model.enums.SortOrder;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import lombok.SneakyThrows;

public class LandingPageController implements Initializable {
  @FXML private Tab internalPatientTransportationTab;
  @FXML private Tab externalPatientTransportationTab;
  @FXML private Tab computerServiceRequestTab;
  @FXML private Tab securityServiceRequestTab;
  @FXML private Tab facilitiesMaintenanceTab;
  @FXML private Tab languageInterpreterTab;
  @FXML private Tab audioVisualServicesTab;
  @FXML private Tab sanitationServicesTab;
  @FXML private Tab religiousRequestTab;
  @FXML private Tab medicalEquipmentTab;
  @FXML private Tab medicineDeliveryTab;
  @FXML private Tab laundryServicesTab;
  @FXML private Tab foodDeliveryTab;
  @FXML private Tab giftDeliveryTab;
  @FXML private Tab homeTab;
  @FXML private Tab mapTab;

  @FXML private AnchorPane mainAnchorPane;
  @FXML private TabPane mainTabPane;
  private double tabHeight = 250;
  private double tabWidth = 35;

  @Override
  @SneakyThrows
  public void initialize(URL location, ResourceBundle resources) {
    mainAnchorPane.setPrefHeight(Screen.getPrimary().getBounds().getHeight());
    mainAnchorPane.setPrefWidth(Screen.getPrimary().getBounds().getWidth());
    mainAnchorPane.autosize();

    mainTabPane.setTabMaxHeight(tabHeight);
    mainTabPane.setRotateGraphic(true);
    mainTabPane.setMaxWidth(tabWidth);

    List<StyledTab> tabs = new ArrayList<>();
    tabs.add(new StyledTab("Home", SortOrder.First, "views/HomePage.fxml"));
    tabs.add(new StyledTab("Hospital Map", SortOrder.ByName, "views/MapPage.fxml"));

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
                    "",
                    SortOrder.ByName,
                    "views/serviceRequests/.fxml"));

    tabs.add(
            new StyledTab(
                    "",
                    SortOrder.ByName,
                    "views/serviceRequests/.fxml"));

    tabs.add(
            new StyledTab(
                    "",
                    SortOrder.ByName,
                    "views/serviceRequests/.fxml"));

    tabs.sort(StyledTab::compareTo);
    mainTabPane.getTabs().setAll(tabs);
    
    //
    //    configureTab(
    //        laundryServicesTab,
    //        "Laundry Services",
    //        "views/serviceRequests/LaundryServiceRequestPage.fxml");
    //
    //    configureTab(
    //        religiousRequestTab,
    //        "Religious Services",
    //        "views/serviceRequests/ReligiousServiceRequestPage.fxml");
    //
    //    configureTab(
    //        internalPatientTransportationTab,
    //        "Internal Patient Transportation",
    //        "views/serviceRequests/InternalPatientTransportationServiceRequestPage.fxml");
    //
    //    configureTab(
    //        externalPatientTransportationTab,
    //        "External Patient Transportation",
    //        "views/serviceRequests/ExternalPatientTransportationServiceRequestPage.fxml");
    //
    //    configureTab(
    //        audioVisualServicesTab,
    //        "Audio/Video Services",
    //        "views/serviceRequests/AudioVisualServiceRequestPage.fxml");
    //
    //    configureTab(
    //        computerServiceRequestTab,
    //        "Computer Services",
    //        "views/serviceRequests/ComputerServiceRequestPage.fxml");
    //
    //    configureTab(
    //        securityServiceRequestTab,
    //        "Security Services",
    //        "views/serviceRequests/SecurityServiceRequestPage.fxml");
    //
    //    configureTab(
    //        facilitiesMaintenanceTab,
    //        "Facilities Maintenance",
    //        "views/serviceRequests/FacilitiesMaintenanceServiceRequestPage.fxml");
  }

  private void configureTab(Tab tab, String title, String pageUrl) throws IOException {
    Label label = new Label(title);
    label.setTextAlignment(TextAlignment.CENTER);
    label.setStyle("-fx-text-fill: -fx-text-color");

    AnchorPane anchorPane = new AnchorPane();
    anchorPane.getChildren().add(label);
    label.setRotate(90.0);

    tab.setGraphic(anchorPane);
    tab.setText("");

    if (pageUrl != null) {
      Parent pageNode = new FXMLLoader(App.class.getResource(pageUrl)).load();
      pageNode.setStyle("@../css/mainStyle.css");

      // Create a gridPane to center the page we load into the tab
      GridPane gridPane = createGridPane();
      gridPane.add(pageNode, 0, 0);
      tab.setContent(gridPane);
    }
  }

  private GridPane createGridPane() {
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
}

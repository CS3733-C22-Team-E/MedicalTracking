package edu.wpi.teame.views;

import edu.wpi.teame.App;
import java.io.IOException;
import java.net.URL;
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
  @FXML private TabPane tabContainer;
  private double tabHeight = 250;
  private double tabWidth = 35;

  @Override
  @SneakyThrows
  public void initialize(URL location, ResourceBundle resources) {
    mainAnchorPane.setPrefHeight(Screen.getPrimary().getBounds().getHeight());
    mainAnchorPane.setPrefWidth(Screen.getPrimary().getBounds().getWidth());
    mainAnchorPane.autosize();

    tabContainer.setTabMaxHeight(tabHeight);
    tabContainer.setRotateGraphic(true);
    tabContainer.setMaxWidth(tabWidth);

    configureTab(homeTab, "Home", "views/HomePage.fxml");
    configureTab(mapTab, "Hospital Map", "views/MapPage.fxml");
    configureTab(medicineDeliveryTab, "Medicine Delivery", null);
    configureTab(sanitationServicesTab, "Sanitation Services", null);

    configureTab(
        medicalEquipmentTab,
        "Medical Equipment",
        "views/serviceRequests/MedicalEquipmentDeliveryServiceRequestPage.fxml");

    configureTab(
        externalPatientTransportationTab,
        "External Patient Transportation",
        "views/serviceRequests/ExternalPatientTransportationServiceRequestPage.fxml");

    configureTab(
        facilitiesMaintenanceTab,
        "Facilities Maintenance",
        "views/serviceRequests/FacilitiesMaintenanceServiceRequestPage.fxml");

    configureTab(
        internalPatientTransportationTab,
        "Internal Patient Transportation",
        "views/serviceRequests/InternalPatientTransportationServiceRequestPage.fxml");

    configureTab(
        foodDeliveryTab,
        "Food Delivery",
        "views/serviceRequests/FoodDeliveryServiceRequestPage.fxml");

    configureTab(
        giftDeliveryTab,
        "Gift Delivery",
        "views/serviceRequests/GiftAndFloralServiceRequestPage.fxml");

    configureTab(
        laundryServicesTab,
        "Laundry Services",
        "views/serviceRequests/LaundryServiceRequestPage.fxml");

    configureTab(
        languageInterpreterTab,
        "Language Services",
        "views/serviceRequests/LanguageInterpreterServiceRequestPage.fxml");

    configureTab(
        audioVisualServicesTab,
        "Audio/Video Services",
        "views/serviceRequests/AudioVisualServiceRequestPage.fxml");

    configureTab(
        computerServiceRequestTab,
        "Computer Services",
        "views/serviceRequests/ComputerServiceRequestPage.fxml");

    configureTab(
        religiousRequestTab,
        "Religious Services",
        "views/serviceRequests/ReligiousServiceRequestPage.fxml");

    configureTab(
        securityServiceRequestTab,
        "Security Services",
        "views/serviceRequests/SecurityServiceRequestPage.fxml");

    configureTab(
        securityServiceRequestTab,
        "Security Service Request",
        "views/serviceRequests/SecurityServiceRequestPage.fxml");
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
      pageNode.setStyle("@../viewStyleSheets/mainStyle.css");

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

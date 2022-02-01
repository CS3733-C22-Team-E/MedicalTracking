package edu.wpi.teamname.views;

import edu.wpi.teamname.App;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;

public class ServiceRequestLandingPageController {
  @FXML private Tab internalPatientTransportationTab;
  @FXML private Tab externalPatientTransportationTab;
  @FXML private Tab computerServiceRequestTab;
  @FXML private Tab securityServiceRequestTab;
  @FXML private Tab langugageInterpreterTab;
  @FXML private Tab audioVisualServicesTab;
  @FXML private Tab sanitationServicesTab;
  @FXML private Tab religiousRequestTab;
  @FXML private Tab medicalEquipmentTab;
  @FXML private Tab medicineDeliveryTab;
  @FXML private Tab laundryServicesTab;
  @FXML private Tab foodDeliveryTab;
  @FXML private Tab giftDeliveryTab;
  @FXML private Tab homeTab;

  @FXML private TabPane tabContainer;
  private double tabHeight = 150;
  private double tabWidth = 40;

  @FXML
  private void setup() throws IOException {
    tabContainer.setTabMaxHeight(tabHeight);
    tabContainer.setRotateGraphic(true);
    tabContainer.setMaxWidth(tabWidth);

    configureTab(homeTab, "Home", null);
    configureTab(medicalEquipmentTab, "", null);
    configureTab(medicineDeliveryTab, "", null);
    configureTab(sanitationServicesTab, "", null);
    configureTab(externalPatientTransportationTab, "", null);
    configureTab(internalPatientTransportationTab, "", null);

    configureTab(
            foodDeliveryTab,
            "Food Delivery",
            "Views/FoodDeliveryServiceRequest.fxml");

    configureTab(
            giftDeliveryTab,
            "Gift Delivery",
            "Views/GiftandFloralServiceRequest.fxml");

    configureTab(
            laundryServicesTab,
            "Laundry Services",
            "Views/LaundryServiceRequest.fxml");

    configureTab(
        langugageInterpreterTab,
        "Language Services",
        "Views/LanguageInterpreterServiceRequest.fxml");

    configureTab(
            audioVisualServicesTab,
            "Audio/Video Services",
            "Views/AudioVisualServiceRequest.fxml");

    configureTab(
            computerServiceRequestTab,
            "Computer Services",
            "Views/ComputerServiceRequest.fxml");

    configureTab(
            religiousRequestTab,
            "Religious Services",
            "Views/ReligiousServiceRequest.fxml");

    configureTab(
        securityServiceRequestTab,
        "Security Services",
        "Views/SecurityServiceRequestController.fxml");

    configureTab(
        securityServiceRequestTab,
        "Security Service Request",
        "Views/SecurityServiceRequestController.fxml");
  }

  private void configureTab(Tab tab, String title, String pageUrl) throws IOException {
    Label label = new Label(title);
    label.setTextAlignment(TextAlignment.CENTER);

    AnchorPane anchorPane = new AnchorPane();
    anchorPane.getChildren().add(label);
    anchorPane.setRotate(90.0);

    tab.setGraphic(anchorPane);
    tab.setText("");

    if (pageUrl != null) {
      Parent newPage = FXMLLoader.load(App.class.getResource(pageUrl));
      tab.setContent(newPage);
    }
  }
}

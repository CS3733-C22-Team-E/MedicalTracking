package edu.wpi.teamname.views;

import edu.wpi.teamname.App;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

public class ServiceRequestLandingPageController {
  @FXML private AnchorPane internalPatientTransportationTab;
  @FXML private AnchorPane externalPatientTransportationTab;
  @FXML private AnchorPane computerServiceRequestTab;
  @FXML private AnchorPane securityServiceRequestTab;
  @FXML private AnchorPane langugageInterpreterTab;
  @FXML private AnchorPane audioVisualServicesTab;
  @FXML private AnchorPane sanitationServicesTab;
  @FXML private AnchorPane religiousRequestTab;
  @FXML private AnchorPane medicalEquipmentTab;
  @FXML private AnchorPane medicineDeliveryTab;
  @FXML private AnchorPane laundryServicesTab;
  @FXML private AnchorPane foodDeliveryTab;
  @FXML private AnchorPane giftDeliveryTab;

  @FXML
  private void setup() throws IOException {
    Parent foodDeliveryPage =
        FXMLLoader.load(App.class.getResource("Views/FoodDeliveryServiceRequest.fxml"));
    foodDeliveryTab.getChildren().add(foodDeliveryPage);

    Parent giftDeliveryPage =
        FXMLLoader.load(App.class.getResource("Views/GiftandFloralServiceRequest.fxml"));
    giftDeliveryTab.getChildren().add(giftDeliveryPage);

    Parent laundryServicesPage =
        FXMLLoader.load(App.class.getResource("Views/LaundryServiceRequest.fxml"));
    laundryServicesTab.getChildren().add(laundryServicesPage);

    Parent languageInterpreterDeliveryPage =
        FXMLLoader.load(App.class.getResource("Views/LanguageInterpreterServiceRequest.fxml"));
    langugageInterpreterTab.getChildren().add(languageInterpreterDeliveryPage);

    Parent audioVideoServiceRequestPage =
        FXMLLoader.load(App.class.getResource("Views/AudioVisualServiceRequest.fxml"));
    audioVisualServicesTab.getChildren().add(audioVideoServiceRequestPage);

    Parent computerServiceRequestPage =
        FXMLLoader.load(App.class.getResource("Views/ComputerServiceRequest.fxml"));
    computerServiceRequestTab.getChildren().add(computerServiceRequestPage);

    Parent religiousServicesPage =
        FXMLLoader.load(App.class.getResource("Views/ReligiousServiceRequest.fxml"));
    religiousRequestTab.getChildren().add(religiousServicesPage);

    Parent securityServiceRequestPage =
        FXMLLoader.load(App.class.getResource("Views/SecurityServiceRequestController.fxml"));
    securityServiceRequestTab.getChildren().add(securityServiceRequestPage);
  }

  @FXML
  private void religiousServicesButtonClick() throws IOException {
    Parent pane = FXMLLoader.load(App.class.getResource("Views/ReligiousServiceRequest.fxml"));
    App.changeScene(pane);
  }

  @FXML
  private void mapPageButtonClick() throws IOException {
    Parent pane = FXMLLoader.load(App.class.getResource("Views/MapView.fxml"));
    App.changeScene(pane);
  }

  @FXML
  private void securityServiceRequestButtonClick() throws IOException {
    Parent pane =
        FXMLLoader.load(App.class.getResource("Views/SecurityServiceRequestController.fxml"));
    App.changeScene(pane);
  }
}

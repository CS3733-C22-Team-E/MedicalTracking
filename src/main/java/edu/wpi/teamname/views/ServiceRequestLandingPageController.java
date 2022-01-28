package edu.wpi.teamname.views;

import edu.wpi.teamname.App;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

public class ServiceRequestLandingPageController {
  @FXML private Button medEquipment;
  @FXML private Button foodDelivery;
  @FXML private Button giftFloralDelivery;
  @FXML private Button medicineDelivery;
  @FXML private Button religiousRequest;

  @FXML
  private void medEquipmentButtonClick() throws IOException {
    Parent pane = FXMLLoader.load(App.class.getResource("Views/PlaceHolder.fxml"));
    App.changeScene(pane);
  }

  @FXML
  private void foodDeliveryButtonClick() throws IOException {
    Parent pane =
        FXMLLoader.load(App.class.getResource("Views/Food Delivery Service Request.FXML"));
    App.changeScene(pane);
  }

  @FXML
  private void giftFloralDeliveryButtonClick() throws IOException {
    Parent pane =
        FXMLLoader.load(App.class.getResource("Views/Gift and Floral Service Request.FXML"));
    App.changeScene(pane);
  }

  @FXML
  private void medicineDelivery() throws IOException {
    Parent pane = FXMLLoader.load(App.class.getResource("Views/PlaceHolder.fxml"));
    App.changeScene(pane);
  }

  @FXML
  private void laundryServicesButtonClick() throws IOException {
    Parent pane = FXMLLoader.load(App.class.getResource("Views/LaundryServiceRequestForm.fxml"));
    App.changeScene(pane);
  }

  @FXML
  private void languageInterpreter() throws IOException {
    Parent pane =
        FXMLLoader.load(App.class.getResource("Views/LanguageInterpreterServiceRequest.fxml"));
    App.changeScene(pane);
  }

  @FXML
  private void audioVideoServiceButtonClick() throws IOException {
    Parent page = FXMLLoader.load(App.class.getResource("Views/AudioVisualServiceRequest.fxml"));
    App.changeScene(page);
  }

  @FXML
  private void computerServiceRequestButtonClick() throws IOException {
    Parent page = FXMLLoader.load(App.class.getResource("Views/ComputerServiceRequest.fxml"));
    App.changeScene(page);
  }

  @FXML
  private void religiousServicesButtonClick() throws IOException {
    Parent pane = FXMLLoader.load(App.class.getResource("Views/ReligiousServiceRequest.fxml"));
    App.changeScene(pane);
  }

  @FXML
  private void mapPageButtonClick() throws IOException {
    Parent pane = FXMLLoader.load(App.class.getResource("Views/MapViewPage.fxml"));
    App.changeScene(pane);
  }
}

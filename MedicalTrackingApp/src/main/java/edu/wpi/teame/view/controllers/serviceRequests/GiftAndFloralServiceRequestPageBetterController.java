package edu.wpi.teame.view.controllers.serviceRequests;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;

public class GiftAndFloralServiceRequestPageBetterController
        extends ServiceRequestController{

    @FXML private JFXButton sendButton;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        /*
        christianityChoices.setItems(
                FXCollections.observableArrayList("Blessing", "Last Rites", "Communion"));
        judaismChoices.setItems(
                FXCollections.observableArrayList("Chaplain", "Shabbat", "Request Kosher Food"));
        islamChoices.setItems(
                FXCollections.observableArrayList("Mosque Trip", "Jumuah", "Prayer Time"));
        hinduismChoices.setItems(FXCollections.observableArrayList("Chaplain", "Prayer Time"));
        */
    }

}

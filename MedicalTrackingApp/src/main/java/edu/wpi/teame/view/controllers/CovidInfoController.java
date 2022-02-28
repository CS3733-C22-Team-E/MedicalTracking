package edu.wpi.teame.view.controllers;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class CovidInfoController implements Initializable {

  @FXML private Button infoButton;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    infoButton.setOnMouseClicked(
        listener -> {
          try {
            openCovidWebsite();
          } catch (URISyntaxException e) {
            e.printStackTrace();
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
  }

  // TODO
  // When infoButton is pressed it opens up the CDC covid site
  // https://www.cdc.gov/coronavirus/2019-ncov/index.html
  public void openCovidWebsite() throws URISyntaxException, IOException {
    Desktop d = Desktop.getDesktop();
    d.browse(new URI("https://www.cdc.gov/coronavirus/2019-ncov/index.html"));
  }
}

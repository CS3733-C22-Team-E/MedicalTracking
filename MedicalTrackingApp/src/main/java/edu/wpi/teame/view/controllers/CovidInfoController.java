package edu.wpi.teame.view.controllers;

import edu.wpi.teame.view.style.IStyleable;
import edu.wpi.teame.view.style.StyleManager;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class CovidInfoController implements Initializable, IStyleable {
  @FXML private Button infoButton;
  @FXML private Label headerLabel;
  @FXML AnchorPane mainAnchorPane;
  @FXML private Label header1;
  @FXML private Label header2;
  @FXML private Label header3;
  @FXML private Label label1;
  @FXML private Label label2;
  @FXML private Label label3;
  @FXML private Label label4;
  @FXML private Label label5;
  @FXML private Label label6;
  @FXML private Label label7;
  @FXML private Label label8;
  @FXML private Label label9;
  @FXML private Label label10;
  @FXML private Label label11;
  @FXML private Label label12;
  @FXML private Label label13;
  @FXML private Label label14;
  @FXML private Label label15;
  @FXML private Label label16;
  @FXML private Label label17;
  @FXML private Label label18;
  @FXML private Label label19;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    StyleManager.getInstance().subscribe(this);

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

  // When infoButton is pressed it opens up the CDC covid site
  // https://www.cdc.gov/coronavirus/2019-ncov/index.html
  public void openCovidWebsite() throws URISyntaxException, IOException {
    Desktop d = Desktop.getDesktop();
    d.browse(new URI("https://www.cdc.gov/coronavirus/2019-ncov/index.html"));
  }

  @Override
  public void updateStyle() {
    StyleManager.getInstance().getCurrentStyle().setPaneStyle(mainAnchorPane, true);
    StyleManager.getInstance().getCurrentStyle().setTitleStyle(headerLabel);
    StyleManager.getInstance().getCurrentStyle().setButtonStyle(infoButton);
    StyleManager.getInstance().getCurrentStyle().setHeaderStyle(header1);
    StyleManager.getInstance().getCurrentStyle().setHeaderStyle(header2);
    StyleManager.getInstance().getCurrentStyle().setHeaderStyle(header3);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(label1);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(label2);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(label3);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(label4);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(label5);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(label6);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(label7);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(label8);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(label9);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(label10);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(label11);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(label12);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(label13);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(label14);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(label15);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(label16);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(label17);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(label18);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(label19);
  }
}

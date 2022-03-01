package edu.wpi.teame.view.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import edu.wpi.teame.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class AboutPageController implements Initializable {
  @FXML private AnchorPane anchorPane;
  @FXML private GridPane gridPane;
  @FXML private Button backButton;
  @FXML private StackPane stackPane;
  @FXML private JFXDialog dialog;

  @FXML private Circle samayCircle;
  @FXML private Label samay;
  @FXML private Circle amitaiCircle;
  @FXML private Label amitai;
  @FXML private Circle joeCircle;
  @FXML private Label joe;
  @FXML private Circle maanavCircle;
  @FXML private Label maanav;
  @FXML private Circle joseCircle;
  @FXML private Label jose;
  @FXML private Circle haohaoCircle;
  @FXML private Label haohao;
  @FXML private Circle jeremyCircle;
  @FXML private Label jeremy;
  @FXML private Circle brindaCircle;
  @FXML private Label brinda;
  @FXML private Circle camiloCircle;
  @FXML private Label camilo;

  private Scene landingPage = null;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try {
      landingPage = new Scene(FXMLLoader.load(App.class.getResource("view/LandingPage.fxml")));
    } catch (IOException e) {
      e.printStackTrace();
    }

    stackPane.setAlignment(dialog, Pos.CENTER);

    Image samImage = new Image("edu/wpi/teame/images/About/samay.png");
    ImagePattern samPattern = new ImagePattern(samImage);
    samayCircle.setFill(samPattern);

    Image amiImage = new Image("edu/wpi/teame/images/About/amitai.png");
    ImagePattern amiPattern = new ImagePattern(amiImage);
    amitaiCircle.setFill(amiPattern);

    Image joeImage = new Image("edu/wpi/teame/images/About/joe2.JPG");
    ImagePattern joePattern = new ImagePattern(joeImage);
    joeCircle.setFill(joePattern);

    Image maaImage = new Image("edu/wpi/teame/images/About/maanav.png");
    ImagePattern maaPattern = new ImagePattern(maaImage);
    maanavCircle.setFill(maaPattern);

    Image josImage = new Image("edu/wpi/teame/images/About/jose.png");
    ImagePattern josPattern = new ImagePattern(josImage);
    joseCircle.setFill(josPattern);

    Image haoImage = new Image("edu/wpi/teame/images/About/haohao.png");
    ImagePattern haoPattern = new ImagePattern(haoImage);
    haohaoCircle.setFill(haoPattern);

    Image jerImage = new Image("edu/wpi/teame/images/About/jeremy.png");
    ImagePattern jerPattern = new ImagePattern(jerImage);
    jeremyCircle.setFill(jerPattern);

    Image briImage = new Image("edu/wpi/teame/images/About/brinda.png");
    ImagePattern briPattern = new ImagePattern(briImage);
    brindaCircle.setFill(briPattern);

    Image camImage = new Image("edu/wpi/teame/images/About/camilo.png");
    ImagePattern camPattern = new ImagePattern(camImage);
    camiloCircle.setFill(camPattern);
  }

  @FXML
  public void samayPopup(MouseEvent Event) {
    JFXDialogLayout info = new JFXDialogLayout();

    info.setHeading(new Text("About Samay"));
    Text text = new Text("");
    text.setFont(Font.font("System", FontWeight.BOLD, 25));
    info.setBody(text);

    dialog = new JFXDialog(stackPane, info, JFXDialog.DialogTransition.CENTER);

    JFXButton backButton = new JFXButton("Back");
    backButton.setOnAction(event -> dialog.close());
    info.setActions(backButton);
    dialog.show();
  }

  @FXML
  public void amitaiPopup(MouseEvent Event) {
    JFXDialogLayout info = new JFXDialogLayout();

    info.setHeading(new Text("About Amitai"));
    Text text = new Text("");
    text.setFont(Font.font("System", FontWeight.BOLD, 25));
    info.setBody(text);

    dialog = new JFXDialog(stackPane, info, JFXDialog.DialogTransition.CENTER);

    JFXButton backButton = new JFXButton("Back");
    backButton.setOnAction(event -> dialog.close());
    info.setActions(backButton);
    dialog.show();
  }

  @FXML
  public void joePopup(MouseEvent Event) {
    JFXDialogLayout info = new JFXDialogLayout();

    info.setHeading(new Text("About Joe"));
    Text text = new Text("");
    text.setFont(Font.font("System", FontWeight.BOLD, 25));
    info.setBody(text);

    dialog = new JFXDialog(stackPane, info, JFXDialog.DialogTransition.CENTER);

    JFXButton backButton = new JFXButton("Back");
    backButton.setOnAction(event -> dialog.close());
    info.setActions(backButton);
    dialog.show();
  }

  @FXML
  public void maanavPopup(MouseEvent Event) {
    JFXDialogLayout info = new JFXDialogLayout();

    info.setHeading(new Text("About Maanav"));
    Text text = new Text("");
    text.setFont(Font.font("System", FontWeight.BOLD, 25));
    info.setBody(text);

    dialog = new JFXDialog(stackPane, info, JFXDialog.DialogTransition.CENTER);

    JFXButton backButton = new JFXButton("Back");
    backButton.setOnAction(event -> dialog.close());
    info.setActions(backButton);
    dialog.show();
  }

  @FXML
  public void josePopup(MouseEvent Event) {
    JFXDialogLayout info = new JFXDialogLayout();

    info.setHeading(new Text("About Jose"));
    Text text = new Text("");
    text.setFont(Font.font("System", FontWeight.BOLD, 25));
    info.setBody(text);

    dialog = new JFXDialog(stackPane, info, JFXDialog.DialogTransition.CENTER);

    JFXButton backButton = new JFXButton("Back");
    backButton.setOnAction(event -> dialog.close());
    info.setActions(backButton);
    dialog.show();
  }

  @FXML
  public void haohaoPopup(MouseEvent Event) {
    JFXDialogLayout info = new JFXDialogLayout();

    info.setHeading(new Text("About Haohao"));
    Text text = new Text("");
    text.setFont(Font.font("System", FontWeight.BOLD, 25));
    info.setBody(text);

    dialog = new JFXDialog(stackPane, info, JFXDialog.DialogTransition.CENTER);

    JFXButton backButton = new JFXButton("Back");
    backButton.setOnAction(event -> dialog.close());
    info.setActions(backButton);
    dialog.show();
  }

  @FXML
  public void jeremyPopup(MouseEvent Event) {
    JFXDialogLayout info = new JFXDialogLayout();

    info.setHeading(new Text("About Jeremy"));
    Text text = new Text("");
    text.setFont(Font.font("System", FontWeight.BOLD, 25));
    info.setBody(text);

    dialog = new JFXDialog(stackPane, info, JFXDialog.DialogTransition.CENTER);

    JFXButton backButton = new JFXButton("Back");
    backButton.setOnAction(event -> dialog.close());
    info.setActions(backButton);
    dialog.show();
  }

  @FXML
  public void brindaPopup(MouseEvent Event) {
    JFXDialogLayout info = new JFXDialogLayout();

    info.setHeading(new Text("About Brinda"));
    Text text =
        new Text(
            "Brinda is a junior double majoring in "
                + "\n"
                + "Actuarial Mathematics and Computer Science."
                + "\n"
                + "She loves her two kittens and playing"
                + "\n"
                + "the violin.");
    text.setFont(Font.font("System", FontWeight.BOLD, 25));
    info.setBody(text);

    dialog = new JFXDialog(stackPane, info, JFXDialog.DialogTransition.CENTER);

    JFXButton backButton = new JFXButton("Back");
    backButton.setOnAction(event -> dialog.close());
    info.setActions(backButton);
    dialog.show();
  }

  @FXML
  public void camiloPopup(MouseEvent Event) {
    JFXDialogLayout info = new JFXDialogLayout();

    info.setHeading(new Text("About Camilo"));
    Text text = new Text("");
    text.setFont(Font.font("System", FontWeight.BOLD, 25));
    info.setBody(text);

    dialog = new JFXDialog(stackPane, info, JFXDialog.DialogTransition.CENTER);

    JFXButton backButton = new JFXButton("Back");
    backButton.setOnAction(event -> dialog.close());
    info.setActions(backButton);
    dialog.show();
  }

  @FXML
  private void exitAbout() {
    App.getAppPrimaryStage().setScene(landingPage);
    App.getAppPrimaryStage().show();
    App.getAppPrimaryStage().setFullScreen(true);
  }
}

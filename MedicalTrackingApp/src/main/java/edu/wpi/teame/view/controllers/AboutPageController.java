package edu.wpi.teame.view.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import edu.wpi.teame.App;
import edu.wpi.teame.view.style.IStyleable;
import edu.wpi.teame.view.style.StyleManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class AboutPageController implements Initializable, IStyleable {
  @FXML private AnchorPane anchorPane;
  @FXML private Pane pane;
  @FXML private GridPane gridPane;
  @FXML private Button backButton;
  @FXML private StackPane stackPane;
  @FXML private JFXDialog dialog;

  @FXML private Text teamName;
  @FXML private Label profWong;
  @FXML private Label teamCoach;
  @FXML private Label thankYou;

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

    StyleManager.getInstance().subscribe(this);
  }

  @FXML
  public void samayPopup(MouseEvent Event) {
    JFXDialogLayout info = new JFXDialogLayout();
    Paint textColor = StyleManager.getInstance().getCurrentStyle().getParagraphColor();
    Text heading = new Text("About Samay");
    heading.setFill(textColor);
    info.setHeading(heading);
    Paint color = StyleManager.getInstance().getCurrentStyle().getHighlightColor();
    info.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));

    Text text =
        new Text(
            "Samay is majoring in Robotics Engineering.\n"
                + "He listens to the same five songs\n"
                + "on repeat and he's on varsity swim.");
    text.setFont(Font.font("System", FontWeight.BOLD, 25));
    info.setBody(text);

    dialog = new JFXDialog(stackPane, info, JFXDialog.DialogTransition.CENTER);

    JFXButton backButton = new JFXButton("Back");
    backButton.setOnAction(event -> dialog.close());
    info.setActions(backButton);

    text.setFill(textColor);
    StyleManager.getInstance().getCurrentStyle().setButtonStyle(backButton);

    dialog.show();
  }

  @FXML
  public void amitaiPopup(MouseEvent Event) {
    JFXDialogLayout info = new JFXDialogLayout();
    Paint textColor = StyleManager.getInstance().getCurrentStyle().getParagraphColor();
    Text heading = new Text("About Amitai");
    heading.setFill(textColor);
    info.setHeading(heading);
    Paint color = StyleManager.getInstance().getCurrentStyle().getHighlightColor();
    info.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));

    Text text =
        new Text(
            "Amitai is majoring in Computer Science.\n"
                + "He monkeys monkily and on the weekends,\n"
                + "loves to monkey.");
    text.setFont(Font.font("System", FontWeight.BOLD, 25));
    info.setBody(text);

    dialog = new JFXDialog(stackPane, info, JFXDialog.DialogTransition.CENTER);

    JFXButton backButton = new JFXButton("Back");
    backButton.setOnAction(event -> dialog.close());
    info.setActions(backButton);

    text.setFill(textColor);
    StyleManager.getInstance().getCurrentStyle().setButtonStyle(backButton);

    dialog.show();
  }

  @FXML
  public void joePopup(MouseEvent Event) {
    JFXDialogLayout info = new JFXDialogLayout();
    Paint textColor = StyleManager.getInstance().getCurrentStyle().getParagraphColor();
    Text heading = new Text("About Joe");
    heading.setFill(textColor);
    info.setHeading(heading);
    Paint color = StyleManager.getInstance().getCurrentStyle().getHighlightColor();
    info.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));

    Text text =
        new Text(
            "Joe is majoring in Computer Science.\n"
                + "He 3D prints album covers and codes in\n"
                + "his roller skates.");
    text.setFont(Font.font("System", FontWeight.BOLD, 25));
    info.setBody(text);

    dialog = new JFXDialog(stackPane, info, JFXDialog.DialogTransition.CENTER);

    JFXButton backButton = new JFXButton("Back");
    backButton.setOnAction(event -> dialog.close());
    info.setActions(backButton);

    text.setFill(textColor);
    StyleManager.getInstance().getCurrentStyle().setButtonStyle(backButton);

    dialog.show();
  }

  @FXML
  public void maanavPopup(MouseEvent Event) {
    JFXDialogLayout info = new JFXDialogLayout();
    Paint textColor = StyleManager.getInstance().getCurrentStyle().getParagraphColor();
    Text heading = new Text("About Maanav");
    heading.setFill(textColor);
    info.setHeading(heading);
    Paint color = StyleManager.getInstance().getCurrentStyle().getHighlightColor();
    info.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));

    Text text =
        new Text(
            "Maanav is majoring in Robotics Engineering.\n"
                + "He rides a motorcycle so stay off the roads :/\n"
                + "He gave his friend/lover/cousin Krish a great\n"
                + "nickname!");
    text.setFont(Font.font("System", FontWeight.BOLD, 25));
    info.setBody(text);

    dialog = new JFXDialog(stackPane, info, JFXDialog.DialogTransition.CENTER);

    JFXButton backButton = new JFXButton("Back");
    backButton.setOnAction(event -> dialog.close());
    info.setActions(backButton);

    text.setFill(textColor);
    StyleManager.getInstance().getCurrentStyle().setButtonStyle(backButton);

    dialog.show();
  }

  @FXML
  public void josePopup(MouseEvent Event) {
    JFXDialogLayout info = new JFXDialogLayout();
    Paint textColor = StyleManager.getInstance().getCurrentStyle().getParagraphColor();
    Text heading = new Text("About Jose");
    heading.setFill(textColor);
    info.setHeading(heading);
    Paint color = StyleManager.getInstance().getCurrentStyle().getHighlightColor();
    info.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));

    Text text = new Text("Jose is majoring in Computer Science.\n" + "He has a katana. Watch out!");
    text.setFont(Font.font("System", FontWeight.BOLD, 25));
    info.setBody(text);

    dialog = new JFXDialog(stackPane, info, JFXDialog.DialogTransition.CENTER);

    JFXButton backButton = new JFXButton("Back");
    backButton.setOnAction(event -> dialog.close());
    info.setActions(backButton);

    text.setFill(textColor);
    StyleManager.getInstance().getCurrentStyle().setButtonStyle(backButton);

    dialog.show();
  }

  @FXML
  public void haohaoPopup(MouseEvent Event) {
    JFXDialogLayout info = new JFXDialogLayout();
    Paint textColor = StyleManager.getInstance().getCurrentStyle().getParagraphColor();
    Text heading = new Text("About Haohao");
    heading.setFill(textColor);
    info.setHeading(heading);
    Paint color = StyleManager.getInstance().getCurrentStyle().getHighlightColor();
    info.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));

    Text text =
        new Text(
            "Haohao is majoring in Robotics Engineering.\n" + "She loves to sleep in Unity Hall.");
    text.setFont(Font.font("System", FontWeight.BOLD, 25));
    info.setBody(text);

    dialog = new JFXDialog(stackPane, info, JFXDialog.DialogTransition.CENTER);

    JFXButton backButton = new JFXButton("Back");
    backButton.setOnAction(event -> dialog.close());
    info.setActions(backButton);

    text.setFill(textColor);
    StyleManager.getInstance().getCurrentStyle().setButtonStyle(backButton);

    dialog.show();
  }

  @FXML
  public void jeremyPopup(MouseEvent Event) {
    JFXDialogLayout info = new JFXDialogLayout();
    Paint textColor = StyleManager.getInstance().getCurrentStyle().getParagraphColor();
    Text heading = new Text("About Jeremy");
    heading.setFill(textColor);
    info.setHeading(heading);
    Paint color = StyleManager.getInstance().getCurrentStyle().getHighlightColor();
    info.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));

    Text text =
        new Text(
            "Jeremy is majoring in Robotics Engineering.\n"
                + "He wears almost exclusively Adidas pants and\n"
                + "likes fingerless gloves! Also subscribe to\n"
                + "manodabplays on YouTube :)");
    text.setFont(Font.font("System", FontWeight.BOLD, 25));
    info.setBody(text);

    dialog = new JFXDialog(stackPane, info, JFXDialog.DialogTransition.CENTER);

    JFXButton backButton = new JFXButton("Back");
    backButton.setOnAction(event -> dialog.close());
    info.setActions(backButton);

    text.setFill(textColor);
    StyleManager.getInstance().getCurrentStyle().setButtonStyle(backButton);

    dialog.show();
  }

  @FXML
  public void brindaPopup(MouseEvent Event) {
    JFXDialogLayout info = new JFXDialogLayout();
    Paint textColor = StyleManager.getInstance().getCurrentStyle().getParagraphColor();
    Text heading = new Text("About Brinda");
    heading.setFill(textColor);
    info.setHeading(heading);
    Paint color = StyleManager.getInstance().getCurrentStyle().getHighlightColor();
    info.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));

    Text text =
        new Text(
            "Brinda is a junior double majoring in "
                + "\n"
                + "Actuarial Mathematics and Computer Science."
                + "\n"
                + "She loves her two kittens and playing"
                + "\n"
                + "the violin :)");
    text.setFont(Font.font("System", FontWeight.BOLD, 25));
    info.setBody(text);

    dialog = new JFXDialog(stackPane, info, JFXDialog.DialogTransition.CENTER);

    JFXButton backButton = new JFXButton("Back");
    backButton.setOnAction(event -> dialog.close());
    info.setActions(backButton);

    text.setFill(textColor);
    StyleManager.getInstance().getCurrentStyle().setButtonStyle(backButton);

    dialog.show();
  }

  @FXML
  public void camiloPopup(MouseEvent Event) {
    JFXDialogLayout info = new JFXDialogLayout();
    Paint textColor = StyleManager.getInstance().getCurrentStyle().getParagraphColor();
    Text heading = new Text("About Camilo");
    heading.setFill(textColor);
    info.setHeading(heading);
    Paint color = StyleManager.getInstance().getCurrentStyle().getHighlightColor();
    info.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));

    Text text =
        new Text(
            "Camilo is majoring in Computer Science.\n"
                + "He makes music! rimbo on Soundcloud :)\n"
                + "He's secretly in love with Maanav and is\n"
                + "too scared to share his feelings. Send him\n"
                + "letters of support at ccordoba@wpi.edu");
    text.setFont(Font.font("System", FontWeight.BOLD, 25));
    info.setBody(text);

    dialog = new JFXDialog(stackPane, info, JFXDialog.DialogTransition.CENTER);

    JFXButton backButton = new JFXButton("Back");
    backButton.setOnAction(event -> dialog.close());
    info.setActions(backButton);

    text.setFill(textColor);
    StyleManager.getInstance().getCurrentStyle().setButtonStyle(backButton);

    dialog.show();
  }

  @FXML
  private void exitAbout() {
    App.getAppPrimaryStage().setScene(landingPage);
    App.getAppPrimaryStage().show();
    App.getAppPrimaryStage().setFullScreen(true);
  }

  @Override
  public void updateStyle() {
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(samay);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(amitai);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(joe);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(maanav);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(jose);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(haohao);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(camilo);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(brinda);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(jeremy);

    //    StyleManager.getInstance().getCurrentStyle().setTextFieldStyle(teamName);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(profWong);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(teamCoach);
    StyleManager.getInstance().getCurrentStyle().setLabelStyle(thankYou);

    StyleManager.getInstance().getCurrentStyle().setButtonStyle(backButton);
    StyleManager.getInstance().getCurrentStyle().setPaneStyle(pane, true);
  }
}

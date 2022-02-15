package edu.wpi.teame.view;

import edu.wpi.teame.App;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class SRSentAnimation {

  private StackPane stackPane;

  public SRSentAnimation() {
    stackPane = new StackPane();
    stackPane.setAlignment(Pos.CENTER);
    // stackPane.setPrefSize(100, 100);
  }

  public StackPane getStackPane() {
    stackPane.setBackground(
        new Background(new BackgroundFill(Color.TRANSPARENT, new CornerRadii(10), Insets.EMPTY)));
    stackPane.setEffect(new DropShadow(5, Color.WHITE));
    return this.stackPane;
  }

  public void play() {
    Text sentText = new Text("Sent!");
    sentText.setFont(Font.font("Arial", 24));
    sentText.setOpacity(0);

    ImageView wheel =
        new ImageView(new Image(App.class.getResource("images/icons/LoadingWheel.png").toString()));
    wheel.setFitWidth(100);
    wheel.setFitHeight(100);

    ImageView rocket =
        new ImageView(new Image(App.class.getResource("images/icons/Rocket.png").toString()));
    rocket.setFitWidth(100);
    rocket.setFitHeight(100);
    rocket.setOpacity(0);

    stackPane.getChildren().add(wheel);
    stackPane.getChildren().add(rocket);
    stackPane.getChildren().add(sentText);

    FadeTransition textFadeOut = new FadeTransition(new Duration(2000), sentText);
    textFadeOut.setFromValue(100);
    textFadeOut.setToValue(0);
    FadeTransition stackPaneFadeOut = new FadeTransition(new Duration(2000), stackPane);
    textFadeOut.setFromValue(100);
    textFadeOut.setToValue(0);
    FadeTransition textFadeIn = new FadeTransition(new Duration(200), sentText);
    textFadeIn.setFromValue(0);
    textFadeIn.setToValue(100);
    textFadeIn.setOnFinished(
        e -> {
          stackPane.setEffect(new DropShadow(5, Color.GOLD));
          textFadeOut.play();
          stackPaneFadeOut.play();
        });
    TranslateTransition rocketTranslate = new TranslateTransition(new Duration(400), rocket);
    rocketTranslate.setFromY(0);
    rocketTranslate.setToY(-600);
    rocketTranslate.setOnFinished(
        e -> {
          textFadeIn.play();
          stackPane.setEffect(new DropShadow(5, Color.YELLOW));
        });
    FadeTransition rocketFadeIn = new FadeTransition(new Duration(100), rocket);
    rocketFadeIn.setFromValue(0);
    rocketFadeIn.setToValue(100);
    rocketFadeIn.setOnFinished(
        e -> {
          rocketTranslate.play();
          wheel.setOpacity(0);
        });
    RotateTransition wheelRotate = new RotateTransition(new Duration(500), wheel);
    wheelRotate.setFromAngle(0);
    wheelRotate.setToAngle(Math.random() * 360 + 270); // Random spin
    wheelRotate.setOnFinished(
        e -> {
          System.out.println("rotate done");
          rocketFadeIn.play();
        });
    wheelRotate.play();
  }
}

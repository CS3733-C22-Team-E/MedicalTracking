package edu.wpi.teame;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {
  private static Stage primaryStage;

  @Override
  public void init() {
    log.info("Starting Up");
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    this.primaryStage = primaryStage;
    Scene primaryScene =
        new Scene(FXMLLoader.load(getClass().getResource("views/LandingPage.fxml")));
    primaryStage.setScene(primaryScene);
    primaryStage.setResizable(true);
    primaryStage.show();
    primaryStage
        .getIcons()
        .add(new Image(App.class.getResource("images/hospital-icon.png").toString()));
    primaryStage.setTitle("Hospital App");
  }

  public static void switchFullScreenStatus() {
    primaryStage.setFullScreen(!primaryStage.isFullScreen());
  }

  public static void changeScene(Parent Switchto) throws IOException {
    primaryStage.getScene().setRoot(Switchto);
    primaryStage.sizeToScene();
  }

  public static void backToLandingPage() throws IOException {
    Parent pane = FXMLLoader.load(App.class.getResource("views/LandingPage.fxml"));
    changeScene(pane);
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}

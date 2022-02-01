package edu.wpi.teame;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {
  private static Stage appPrimaryStage;

  @Override
  public void init() {
    log.info("Starting Up");
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    appPrimaryStage = primaryStage;
    appPrimaryStage.setResizable(true);
    appPrimaryStage.setFullScreen(true);
    appPrimaryStage.setTitle("Hospital App");

    appPrimaryStage
        .getIcons()
        .add(new Image(App.class.getResource("images/hospital-icon.png").toString()));

    Scene primaryScene =
        new Scene(FXMLLoader.load(getClass().getResource("views/LandingPage.fxml")));
    appPrimaryStage.setScene(primaryScene);
    appPrimaryStage.show();
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}

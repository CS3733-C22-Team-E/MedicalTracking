package edu.wpi.teamname;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        new Scene(FXMLLoader.load(getClass().getResource("Views/ServiceRequestLandingPage.fxml")));
    primaryStage.setScene(primaryScene);
    primaryStage.show();
  }

  public static void changeScene(Parent Switchto) throws IOException {

    primaryStage.getScene().setRoot(Switchto);
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}

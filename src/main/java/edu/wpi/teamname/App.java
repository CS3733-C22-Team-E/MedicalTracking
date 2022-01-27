package edu.wpi.teamname;

import java.awt.*;
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
    primaryStage.setResizable(true);
    primaryStage.show();
  }

  public static void changeScene(Parent Switchto) throws IOException {
    primaryStage.getScene().setRoot(Switchto);
    primaryStage.sizeToScene();
  }

  public static void backToLandingPage() throws IOException {
    Parent pane = FXMLLoader.load(App.class.getResource("Views/ServiceRequestLandingPage.fxml"));
    changeScene(pane);
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}

package edu.wpi.teame;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {
  private static Stage appPrimaryStage;

  public static Stage getAppPrimaryStage() {
    return appPrimaryStage;
  }

  @Override
  public void init() {
    log.info("Starting Up");
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    appPrimaryStage = primaryStage;
    appPrimaryStage.setTitle("Hospital App");
    appPrimaryStage.setFullScreen(true);
    appPrimaryStage.setResizable(true);

    appPrimaryStage
        .getIcons()
        .add(new Image(App.class.getResource("images/Icons/AppIcon.png").toString()));

    Scene primaryScene = new Scene(FXMLLoader.load(App.class.getResource("view/LoginPage.fxml")));
    appPrimaryStage.setScene(primaryScene);
    appPrimaryStage.setOnCloseRequest(
        new EventHandler<WindowEvent>() {
          @Override
          public void handle(WindowEvent event) {
            Dialog dialog = new Dialog();
            dialog.setTitle("Are you sure you want to quit?");
            ImageView newIcon =
                new ImageView(
                    new Image(App.class.getResource("images/icons/ExitIcon.png").toString()));
            newIcon.setFitHeight(30);
            newIcon.setFitWidth(30);
            dialog.getDialogPane().setGraphic(newIcon);
            ButtonType OK = new ButtonType("Exit", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(OK, ButtonType.CANCEL);
            dialog.setResultConverter(
                dialogButton -> {
                  if (dialogButton == OK) {
                    appPrimaryStage.close();
                  } else {
                    event.consume();
                  }
                  return null;
                });
            dialog.showAndWait();
          }
        });
    appPrimaryStage.show();
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}

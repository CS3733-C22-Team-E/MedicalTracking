package edu.wpi.teame;

import edu.wpi.teame.model.PannableView;
import java.io.IOException;
import javafx.application.Application;
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
    PannableView pv = new PannableView("String");
    pv.start(primaryStage);
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}

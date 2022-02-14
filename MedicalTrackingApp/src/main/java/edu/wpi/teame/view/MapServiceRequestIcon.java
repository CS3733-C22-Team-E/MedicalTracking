package edu.wpi.teame.view;

import edu.wpi.teame.App;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.view.ProgressBar.FillProgressIndicator;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class MapServiceRequestIcon {
  Timer timer;
  FillProgressIndicator progressIndicator;
  ImageView Icon;
  Pane onPane;
  static HashMap<DataBaseObjectType, ImageView> Graphics =
      new HashMap<DataBaseObjectType, ImageView>();

  static {
    Graphics.put(
        DataBaseObjectType.AudioVisualSR,
        new ImageView(
            new Image(
                App.class
                    .getResource("images/Icons/ServiceRequestIcons/AudioVisual.png")
                    .toString())));
    Graphics.put(
        DataBaseObjectType.ComputerSR,
        new ImageView(
            new Image(
                App.class
                    .getResource("images/Icons/ServiceRequestIcons/Computer.png")
                    .toString())));
    Graphics.put(
        DataBaseObjectType.FoodDeliverySR,
        new ImageView(
            new Image(
                App.class
                    .getResource("images/Icons/ServiceRequestIcons/FoodDelivery.png")
                    .toString())));
    Graphics.put(
        DataBaseObjectType.GiftAndFloralSR,
        new ImageView(
            new Image(
                App.class
                    .getResource("images/Icons/ServiceRequestIcons/GiftAndFloral.png")
                    .toString())));
    Graphics.put(
        DataBaseObjectType.InternalPatientTransferSR,
        new ImageView(
            new Image(
                App.class
                    .getResource("images/Icons/ServiceRequestIcons/InternalPatientTransfer.png")
                    .toString())));
    Graphics.put(
        DataBaseObjectType.ExternalPatientTransportation,
        new ImageView(
            new Image(
                App.class
                    .getResource("images/Icons/ServiceRequestIcons/ExternalPatientTransportation.png")
                    .toString())));
    Graphics.put(
        DataBaseObjectType.LanguageInterpreterSR,
        new ImageView(
            new Image(
                App.class
                    .getResource("images/Icons/ServiceRequestIcons/LanguageInterpreter.png")
                    .toString())));
    Graphics.put(
        DataBaseObjectType.LaundrySR,
        new ImageView(
            new Image(
                App.class
                    .getResource("images/Icons/ServiceRequestIcons/Laundry.png")
                    .toString())));
    Graphics.put(
        DataBaseObjectType.ReligiousSR,
        new ImageView(
            new Image(
                App.class
                    .getResource("images/Icons/ServiceRequestIcons/Religious.png")
                    .toString())));
    Graphics.put(
        DataBaseObjectType.SecuritySR,
        new ImageView(
            new Image(
                App.class
                    .getResource("images/Icons/ServiceRequestIcons/Security.png")
                    .toString())));
    Graphics.put(
        DataBaseObjectType.MedicalEquipmentSR,
        new ImageView(
            new Image(
                App.class
                    .getResource("images/Icons/ServiceRequestIcons/MedicalEquipment.png")
                    .toString())));
    Graphics.put(
        DataBaseObjectType.MedicineDeliverySR,
        new ImageView(
            new Image(
                App.class
                    .getResource("images/Icons/ServiceRequestIcons/MedicineDelivery.png")
                    .toString())));
    Graphics.put(
        DataBaseObjectType.FacilitiesMaintenanceSR,
        new ImageView(
            new Image(
                App.class
                    .getResource("images/Icons/ServiceRequestIcons/FacilitiesMaintenance.png")
                    .toString())));
    Graphics.put(
        DataBaseObjectType.SanitationSR,
        new ImageView(
            new Image(
                App.class
                    .getResource("images/Icons/ServiceRequestIcons/Sanitation.png")
                    .toString())));
  }

  double PixelX;
  double PixelY;

  public MapServiceRequestIcon(Pane pane, double X, double Y, DataBaseObjectType SRType) {
    this.timer = new Timer();
    progressIndicator = new FillProgressIndicator();
    progressIndicator.setProgress(0);
    Icon = Graphics.get(SRType);
    Icon.setTranslateX(X);
    Icon.setTranslateY(Y);
    Icon.setFitHeight(35);
    Icon.setFitWidth(35);
    progressIndicator.setTranslateX(X);
    progressIndicator.setTranslateY(Y);
    pane.getChildren().addAll(progressIndicator, Icon);
    onPane = pane;
  }

  public MapServiceRequestIcon(Pane pane, Location location, DataBaseObjectType SRType) {
    this.timer = new Timer();
    progressIndicator = new FillProgressIndicator();
    progressIndicator.setProgress(0);
    Icon = Graphics.get(SRType);
    Icon.setTranslateX(location.getX());
    Icon.setTranslateY(location.getY());
    Icon.setFitHeight(35);
    Icon.setFitWidth(35);
    Tooltip.install(Icon, new Tooltip(location.getShortName()));
    progressIndicator.setTranslateX(location.getX());
    progressIndicator.setTranslateY(location.getY());
    pane.getChildren().addAll(progressIndicator, Icon);
    onPane = pane;
  }

  public void startTimer(int seconds) {
    timer.scheduleAtFixedRate(
        new TimerTask() {
          @Override
          public void run() {
            progressIndicator.setProgress(progressIndicator.getProgress() + 1);
            if (progressIndicator.getProgress() >= 100) {
              progressIndicator.setVisible(false);
              Icon.setVisible(false);
              onPane.getChildren().removeAll(progressIndicator, Icon);
            }
          }
        },
        0,
        (int) (seconds * 10));
  }

  public void setTimerTask(TimerTask timerTask, int seconds) {
    timer.cancel();
    timer.scheduleAtFixedRate(timerTask, 0, seconds);
  }

  public void cancelTimer() {
    timer.cancel();
  }
}

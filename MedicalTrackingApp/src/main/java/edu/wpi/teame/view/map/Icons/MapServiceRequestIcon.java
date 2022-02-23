package edu.wpi.teame.view.map.Icons;

import edu.wpi.teame.App;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.serviceRequests.ServiceRequest;
import edu.wpi.teame.view.ProgressBar.FillProgressIndicator;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MapServiceRequestIcon {
  public FillProgressIndicator progressIndicator;
  public ImageView Icon;
  ServiceRequest SR;
  Timer timer;

  ArrayList<MapServiceRequestIcon> attachedTo;
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
        DataBaseObjectType.ExternalPatientSR,
        new ImageView(
            new Image(
                App.class
                    .getResource(
                        "images/Icons/ServiceRequestIcons/ExternalPatientTransportation.png")
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
                App.class.getResource("images/Icons/ServiceRequestIcons/Laundry.png").toString())));
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

  public MapServiceRequestIcon startTimer(int seconds) {
    timer.scheduleAtFixedRate(
        new TimerTask() {
          @Override
          public void run() {
            Platform.runLater(
                () -> {
                  progressIndicator.setProgress(progressIndicator.getProgress() + 1);
                  if (progressIndicator.getProgress() >= 100) {
                    progressIndicator.setVisible(false);
                    Icon.setVisible(false);
                    attachedTo.remove(this);
                    try {
                      DBManager.getManager(SR.getDBType()).remove(SR.getId());
                    } catch (SQLException e) {
                      e.printStackTrace();
                    }
                  }
                });
          }
        },
        0,
        (int) (seconds * 10));
    return this;
  }

  public MapServiceRequestIcon(ServiceRequest SR_, double TranslateX, double TranslateY) {
    timer = new Timer();
    progressIndicator = new FillProgressIndicator(SR_.getPriority());
    Icon = Graphics.get(SR_.getDBType());
    SR = SR_;
    this.setTranslateXandY(TranslateX, TranslateY);
    Icon.setFitWidth(35);
    Icon.setFitHeight(35);
  }

  public void setTranslateXandY(double Tx, double Ty) {
    Icon.setTranslateX(Tx);
    Icon.setTranslateY(Ty);
    progressIndicator.setTranslateX(Tx);
    progressIndicator.setTranslateY(Ty);
  }

  public void setTimerTask(TimerTask timerTask, int seconds) {
    timer.cancel();
    timer.scheduleAtFixedRate(timerTask, 0, seconds * 1000);
  }

  public void cancelTimer() {
    timer.cancel();
    progressIndicator.setVisible(false);
    Icon.setVisible(false);
    attachedTo.remove(this);
    try {
      DBManager.getManager(SR.getDBType()).remove(SR.getId());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void addToList(ArrayList<MapServiceRequestIcon> SRList) {
    attachedTo = SRList;
    SRList.add(this);
  }

  public ImageView getIcon() {
    return this.Icon;
  }

  public FillProgressIndicator getFillProgressIndicator() {
    return this.progressIndicator;
  }
}

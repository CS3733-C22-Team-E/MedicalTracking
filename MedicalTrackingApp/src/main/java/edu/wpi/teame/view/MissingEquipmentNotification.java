package edu.wpi.teame.view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public class MissingEquipmentNotification {
  static Alert alert;

  public MissingEquipmentNotification() {}

  public static void show(String equipmentName) {
    ButtonType b = new ButtonType("Okay.", ButtonBar.ButtonData.CANCEL_CLOSE);
    alert =
        new Alert(
            Alert.AlertType.ERROR, "There are no clean \"" + equipmentName + "\" remaining!", b);
    alert.show();
  }
}

package edu.wpi.teame.view;

import com.jfoenix.controls.JFXButton;
import edu.wpi.teame.model.Equipment;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;

public class MapEquipmentIcon {
  JFXButton Button;
  public Equipment equipment;

  public MapEquipmentIcon(JFXButton button, Equipment equip) {
    equipment = equip;
    Button = button;
  }

  public Equipment getEquipment() {
    return this.equipment;
  }

  public MapEquipmentIcon(Double x, Double y, String descriptor, Node graphic) {
    Button = new JFXButton();
    Button.setGraphic(graphic);
    Tooltip tooltip = new Tooltip(descriptor);
    Tooltip.install(Button, tooltip);
    Button.setTranslateX(x - (2500));
    Button.setTranslateY(y - (1700));
    Button.setOpacity(.5);
  }

  public JFXButton getButton() {
    return Button;
  }
}

package edu.wpi.teame.view;

import com.jfoenix.controls.JFXButton;
import edu.wpi.teame.db.Equipment;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;

public class MapEquipmentIcon {
  JFXButton Button;
  String Descriptor;
  Equipment equipment;

  public MapEquipmentIcon(JFXButton button, String descriptor, Equipment equip) {
    equipment = equip;
    Button = button;
    Descriptor = descriptor;
  }

  public MapEquipmentIcon(JFXButton button, String descriptor) {
    Button = button;
    Descriptor = descriptor;
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

  public String getDescriptor() {
    return Descriptor;
  }

  public void setDescriptor(String descriptor) {
    Descriptor = descriptor;
  }
}

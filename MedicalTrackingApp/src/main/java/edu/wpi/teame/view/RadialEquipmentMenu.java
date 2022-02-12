package edu.wpi.teame.view;

import com.jfoenix.controls.JFXButton;
import edu.wpi.teame.App;
import edu.wpi.teame.model.Location;
import java.util.List;
import java.util.Objects;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RadialEquipmentMenu {

  private final List<MapEquipmentIcon> icons;
  private final Location location;
  private final JFXButton button;

  public RadialEquipmentMenu(List<MapEquipmentIcon> iconList) {
    icons = iconList;
    location = iconList.get(0).getEquipment().getLocation();
    ImageView hamburgerIcon =
        new ImageView(
            new Image(
                Objects.requireNonNull(App.class.getResource("images/Icons/HamburgerMenu.png"))
                    .toString(),
                20,
                20,
                true,
                true,
                true));
    button = new JFXButton();
    button.setGraphic(hamburgerIcon);
    button.setOnMouseEntered(
        e -> {
          display();
        });
    button.setOnMouseExited(
        e -> {
          hide();
        });
  }

  public JFXButton getButton() {
    return button;
  }

  public void display() {
    // TODO implement this method
    System.out.println("Displaying radial menu.");
  }

  public void hide() {
    // TODO implement this method
    System.out.println("Hiding radial menu.");
  }

  public void addEquipmentIcon(MapEquipmentIcon i) {
    icons.add(i);
  }

  public void removeEquipmentIcon(MapEquipmentIcon i) {
    icons.remove(i);
  }

  public int size() {
    return icons.size();
  }

  public Location getLocation() {
    return location;
  }

  public String toString() {
    StringBuilder s = new StringBuilder("Contains equipment: ");
    for (MapEquipmentIcon i : icons) {
      s.append("[");
      s.append(i.getEquipment().getId());
      s.append("], ");
    }
    return s.toString();
  }

  public void hideIndividualIcons() {
    for (MapEquipmentIcon i : icons) {
      i.getButton().setVisible(false);
    }
  }

  public void place(double mapW, double mapH) {
    button.setTranslateX(location.getX() - mapW / 2);
    button.setTranslateY(location.getY() - mapH / 2);
  }
}

package edu.wpi.teame.view;

import com.jfoenix.controls.JFXButton;
import edu.wpi.teame.App;
import edu.wpi.teame.model.Location;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class RadialEquipmentMenu {

  private static class Position {
    double x;
    double y;
  }

  private boolean deployed = false;

  private final List<MapEquipmentIcon> icons;
  private final Location location;
  private final JFXButton button;
  private final double radius = 50;
  private HashMap<MapEquipmentIcon, Position> originalPositions =
      new HashMap<MapEquipmentIcon, Position>();
  private double MAPWIDTH;
  private double MAPHEIGHT;

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
          if (!deployed) {
            display();
          }
        });
  }

  public double getDistanceToCoordinate(double xCoordinate, double yCoordinate) {
    return Math.sqrt(
        Math.pow((xCoordinate - this.location.getX()), 2)
            + Math.pow((yCoordinate - this.location.getY()), 2));
  }

  public double getRadius() {
    return radius;
  }

  public JFXButton getButton() {
    return button;
  }

  public void display() {
    // oh fuck I'm gonna have to do some trig here :(
    int N = icons.size();
    double angle = (Math.PI * 2 / N);
    originalPositions.clear();
    int numDisplayed = 0;
    for (MapEquipmentIcon i : icons) {
      Position op = new Position();
      op.x = i.getButton().getTranslateX();
      op.y = i.getButton().getTranslateY();
      originalPositions.put(i, op);
      double newX = op.x + Math.sin(angle * numDisplayed) * radius;
      double newY = op.y - Math.cos(angle * numDisplayed) * radius;
      i.getButton().setTranslateX(newX);
      i.getButton().setTranslateY(newY);
      i.getButton().setVisible(true);
      numDisplayed++;
    }
    deployed = true;
  }

  public void hide() {
    for (MapEquipmentIcon i : icons) {
      i.getButton().setVisible(false);
      if (!originalPositions.isEmpty()) {
        Position op = originalPositions.get(i);
        i.getButton().setTranslateX(op.x);
        i.getButton().setTranslateY(op.y);
      }
    }
    deployed = false;
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

  public void kill() {
    for (MapEquipmentIcon i : icons) {
      i.getButton().setVisible(true);
      if (!originalPositions.isEmpty()) {
        Position op = originalPositions.get(i);
        i.getButton().setTranslateX(op.x);
        i.getButton().setTranslateY(op.y);
      }
    }
    this.button.setVisible(false);
  }

  public List<MapEquipmentIcon> getIcons() {
    return icons;
  }

  public void hideIndividualIcons() {
    for (MapEquipmentIcon i : icons) {
      i.getButton().setVisible(false);
    }
  }

  public void place(double mapW, double mapH) {
    MAPWIDTH = mapW;
    MAPHEIGHT = mapH;
    button.setTranslateX(location.getX() - MAPWIDTH / 2);
    button.setTranslateY(location.getY() - MAPHEIGHT / 2);
  }

  private boolean isMouseOutsideRadius(MouseEvent e) {
    System.out.println(this.location.getX());
    System.out.println(this.location.getY());
    System.out.println(e.getSceneX());
    double x = e.getSceneX() + MAPWIDTH / 2;
    double y = e.getSceneY() + MAPHEIGHT / 2;
    System.out.println(e.getSceneY());
    double distance =
        Math.sqrt(
            Math.pow((x - this.location.getX()), 2) + Math.pow((y - this.location.getY()), 2));
    System.out.println(distance);
    return distance > radius;
  }
}

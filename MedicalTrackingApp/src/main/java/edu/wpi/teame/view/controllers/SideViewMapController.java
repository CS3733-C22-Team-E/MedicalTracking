package edu.wpi.teame.view.controllers;

import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.objectManagers.EquipmentManager;
import edu.wpi.teame.model.Equipment;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.EquipmentType;
import edu.wpi.teame.model.enums.FloorType;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class SideViewMapController implements Initializable {

  @FXML public Button thirdFloor;
  @FXML public Button secondFloor;
  @FXML public Button firstFloor;
  @FXML public Button lowerLevel1;
  @FXML public Button lowerLevel2;

  @FXML public Label xRayLabel;
  @FXML public Label hospitalBedLabel;
  @FXML public Label reclinerLabel;
  @FXML public Label cleanPumpLabel;
  @FXML public Label dirtyPumpLabel;

  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  @FXML
  private void makeThirdFloorPieVisible() {

    List<Equipment> bedEquipmentList = null;
    List<Equipment> xrayEquipmentList = null;
    List<Equipment> reclinerEquipmentList = null;
    List<Equipment> pumpsEquipmentList = null;

    int bedCounter = 0;
    int xrayCounter = 0;
    int reclinerCounter = 0;
    int dirtyPumpsCounter = 0;
    int cleanPumpsCounter = 0;

    try {
      bedEquipmentList =
          ((EquipmentManager) DBManager.getInstance().getManager(DataBaseObjectType.Equipment))
              .getEquipmentByType(EquipmentType.PBED);
      xrayEquipmentList =
          ((EquipmentManager) DBManager.getInstance().getManager(DataBaseObjectType.Equipment))
              .getEquipmentByType(EquipmentType.XRAY);
      reclinerEquipmentList =
          ((EquipmentManager) DBManager.getInstance().getManager(DataBaseObjectType.Equipment))
              .getEquipmentByType(EquipmentType.RECL);
      pumpsEquipmentList =
          ((EquipmentManager) DBManager.getInstance().getManager(DataBaseObjectType.Equipment))
              .getEquipmentByType(EquipmentType.PUMP);

      for (int i = 0; i < bedEquipmentList.size(); i++) {
        if (bedEquipmentList.get(i).getLocation().getFloor() == FloorType.ThirdFloor) {
          bedCounter++;
        }
      }

      for (int i = 0; i < xrayEquipmentList.size(); i++) {
        if (xrayEquipmentList.get(i).getLocation().getFloor() == FloorType.ThirdFloor) {
          xrayCounter++;
        }
      }

      for (int i = 0; i < reclinerEquipmentList.size(); i++) {
        if (reclinerEquipmentList.get(i).getLocation().getFloor() == FloorType.ThirdFloor) {
          reclinerCounter++;
        }
      }

      for (int i = 0; i < pumpsEquipmentList.size(); i++) {
        if (pumpsEquipmentList.get(i).getLocation().getFloor() == FloorType.ThirdFloor) {
          if (pumpsEquipmentList.get(i).isClean()) {
            cleanPumpsCounter++;
          } else {
            dirtyPumpsCounter++;
          }
        }
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    xRayLabel.setText("X-Rays: " + xrayCounter);
    hospitalBedLabel.setText("Patient Beds: " + bedCounter);
    reclinerLabel.setText("Recliners: " + reclinerCounter);
    cleanPumpLabel.setText("Clean Pumps: " + cleanPumpsCounter);
    dirtyPumpLabel.setText("Dirty Pumps: " + dirtyPumpsCounter);
  }

  @FXML
  private void makeSecondFloorPieVisible() {

    List<Equipment> bedEquipmentList = null;
    List<Equipment> xrayEquipmentList = null;
    List<Equipment> reclinerEquipmentList = null;
    List<Equipment> pumpsEquipmentList = null;

    int bedCounter = 0;
    int xrayCounter = 0;
    int reclinerCounter = 0;
    int dirtyPumpsCounter = 0;
    int cleanPumpsCounter = 0;

    try {
      bedEquipmentList =
          ((EquipmentManager) DBManager.getInstance().getManager(DataBaseObjectType.Equipment))
              .getEquipmentByType(EquipmentType.PBED);
      xrayEquipmentList =
          ((EquipmentManager) DBManager.getInstance().getManager(DataBaseObjectType.Equipment))
              .getEquipmentByType(EquipmentType.XRAY);
      reclinerEquipmentList =
          ((EquipmentManager) DBManager.getInstance().getManager(DataBaseObjectType.Equipment))
              .getEquipmentByType(EquipmentType.RECL);
      pumpsEquipmentList =
          ((EquipmentManager) DBManager.getInstance().getManager(DataBaseObjectType.Equipment))
              .getEquipmentByType(EquipmentType.PUMP);

      for (int i = 0; i < bedEquipmentList.size(); i++) {
        if (bedEquipmentList.get(i).getLocation().getFloor() == FloorType.SecondFloor) {
          bedCounter++;
        }
      }

      for (int i = 0; i < xrayEquipmentList.size(); i++) {
        if (xrayEquipmentList.get(i).getLocation().getFloor() == FloorType.SecondFloor) {
          xrayCounter++;
        }
      }

      for (int i = 0; i < reclinerEquipmentList.size(); i++) {
        if (reclinerEquipmentList.get(i).getLocation().getFloor() == FloorType.SecondFloor) {
          reclinerCounter++;
        }
      }

      for (int i = 0; i < pumpsEquipmentList.size(); i++) {
        if (pumpsEquipmentList.get(i).getLocation().getFloor() == FloorType.SecondFloor) {
          if (pumpsEquipmentList.get(i).isClean()) {
            cleanPumpsCounter++;
          } else {
            dirtyPumpsCounter++;
          }
        }
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    xRayLabel.setText("X-Rays: " + xrayCounter);
    hospitalBedLabel.setText("Patient Beds: " + bedCounter);
    reclinerLabel.setText("Recliners: " + reclinerCounter);
    cleanPumpLabel.setText("Clean Pumps: " + cleanPumpsCounter);
    dirtyPumpLabel.setText("Dirty Pumps: " + dirtyPumpsCounter);
  }

  @FXML
  private void makeFirstFloorPieVisible() {

    List<Equipment> bedEquipmentList = null;
    List<Equipment> xrayEquipmentList = null;
    List<Equipment> reclinerEquipmentList = null;
    List<Equipment> pumpsEquipmentList = null;

    int bedCounter = 0;
    int xrayCounter = 0;
    int reclinerCounter = 0;
    int dirtyPumpsCounter = 0;
    int cleanPumpsCounter = 0;

    try {
      bedEquipmentList =
          ((EquipmentManager) DBManager.getInstance().getManager(DataBaseObjectType.Equipment))
              .getEquipmentByType(EquipmentType.PBED);
      xrayEquipmentList =
          ((EquipmentManager) DBManager.getInstance().getManager(DataBaseObjectType.Equipment))
              .getEquipmentByType(EquipmentType.XRAY);
      reclinerEquipmentList =
          ((EquipmentManager) DBManager.getInstance().getManager(DataBaseObjectType.Equipment))
              .getEquipmentByType(EquipmentType.RECL);
      pumpsEquipmentList =
          ((EquipmentManager) DBManager.getInstance().getManager(DataBaseObjectType.Equipment))
              .getEquipmentByType(EquipmentType.PUMP);

      for (int i = 0; i < bedEquipmentList.size(); i++) {
        if (bedEquipmentList.get(i).getLocation().getFloor() == FloorType.FirstFloor) {
          bedCounter++;
        }
      }

      for (int i = 0; i < xrayEquipmentList.size(); i++) {
        if (xrayEquipmentList.get(i).getLocation().getFloor() == FloorType.FirstFloor) {
          xrayCounter++;
        }
      }

      for (int i = 0; i < reclinerEquipmentList.size(); i++) {
        if (reclinerEquipmentList.get(i).getLocation().getFloor() == FloorType.FirstFloor) {
          reclinerCounter++;
        }
      }

      for (int i = 0; i < pumpsEquipmentList.size(); i++) {
        if (pumpsEquipmentList.get(i).getLocation().getFloor() == FloorType.FirstFloor) {
          if (pumpsEquipmentList.get(i).isClean()) {
            cleanPumpsCounter++;
          } else {
            dirtyPumpsCounter++;
          }
        }
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    xRayLabel.setText("X-Rays: " + xrayCounter);
    hospitalBedLabel.setText("Patient Beds: " + bedCounter);
    reclinerLabel.setText("Recliners: " + reclinerCounter);
    cleanPumpLabel.setText("Clean Pumps: " + cleanPumpsCounter);
    dirtyPumpLabel.setText("Dirty Pumps: " + dirtyPumpsCounter);
  }

  @FXML
  private void makeLowerLevel1PieVisible() {

    List<Equipment> bedEquipmentList = null;
    List<Equipment> xrayEquipmentList = null;
    List<Equipment> reclinerEquipmentList = null;
    List<Equipment> pumpsEquipmentList = null;

    int bedCounter = 0;
    int xrayCounter = 0;
    int reclinerCounter = 0;
    int dirtyPumpsCounter = 0;
    int cleanPumpsCounter = 0;

    try {
      bedEquipmentList =
          ((EquipmentManager) DBManager.getInstance().getManager(DataBaseObjectType.Equipment))
              .getEquipmentByType(EquipmentType.PBED);
      xrayEquipmentList =
          ((EquipmentManager) DBManager.getInstance().getManager(DataBaseObjectType.Equipment))
              .getEquipmentByType(EquipmentType.XRAY);
      reclinerEquipmentList =
          ((EquipmentManager) DBManager.getInstance().getManager(DataBaseObjectType.Equipment))
              .getEquipmentByType(EquipmentType.RECL);
      pumpsEquipmentList =
          ((EquipmentManager) DBManager.getInstance().getManager(DataBaseObjectType.Equipment))
              .getEquipmentByType(EquipmentType.PUMP);

      for (int i = 0; i < bedEquipmentList.size(); i++) {
        if (bedEquipmentList.get(i).getLocation().getFloor() == FloorType.FirstFloor) {
          bedCounter++;
        }
      }

      for (int i = 0; i < xrayEquipmentList.size(); i++) {
        if (xrayEquipmentList.get(i).getLocation().getFloor() == FloorType.LowerLevel1) {
          xrayCounter++;
        }
      }

      for (int i = 0; i < reclinerEquipmentList.size(); i++) {
        if (reclinerEquipmentList.get(i).getLocation().getFloor() == FloorType.LowerLevel1) {
          reclinerCounter++;
        }
      }

      for (int i = 0; i < pumpsEquipmentList.size(); i++) {
        if (pumpsEquipmentList.get(i).getLocation().getFloor() == FloorType.LowerLevel1) {
          if (pumpsEquipmentList.get(i).isClean()) {
            cleanPumpsCounter++;
          } else {
            dirtyPumpsCounter++;
          }
        }
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    xRayLabel.setText("X-Rays: " + xrayCounter);
    hospitalBedLabel.setText("Patient Beds: " + bedCounter);
    reclinerLabel.setText("Recliners: " + reclinerCounter);
    cleanPumpLabel.setText("Clean Pumps: " + cleanPumpsCounter);
    dirtyPumpLabel.setText("Dirty Pumps: " + dirtyPumpsCounter);
  }

  @FXML
  private void makeLowerLevel2PieVisible() {

    List<Equipment> bedEquipmentList = null;
    List<Equipment> xrayEquipmentList = null;
    List<Equipment> reclinerEquipmentList = null;
    List<Equipment> pumpsEquipmentList = null;

    int bedCounter = 0;
    int xrayCounter = 0;
    int reclinerCounter = 0;
    int dirtyPumpsCounter = 0;
    int cleanPumpsCounter = 0;

    try {
      bedEquipmentList =
          ((EquipmentManager) DBManager.getInstance().getManager(DataBaseObjectType.Equipment))
              .getEquipmentByType(EquipmentType.PBED);
      xrayEquipmentList =
          ((EquipmentManager) DBManager.getInstance().getManager(DataBaseObjectType.Equipment))
              .getEquipmentByType(EquipmentType.XRAY);
      reclinerEquipmentList =
          ((EquipmentManager) DBManager.getInstance().getManager(DataBaseObjectType.Equipment))
              .getEquipmentByType(EquipmentType.RECL);
      pumpsEquipmentList =
          ((EquipmentManager) DBManager.getInstance().getManager(DataBaseObjectType.Equipment))
              .getEquipmentByType(EquipmentType.PUMP);

      for (int i = 0; i < bedEquipmentList.size(); i++) {
        if (bedEquipmentList.get(i).getLocation().getFloor() == FloorType.LowerLevel2) {
          bedCounter++;
        }
      }

      for (int i = 0; i < xrayEquipmentList.size(); i++) {
        if (xrayEquipmentList.get(i).getLocation().getFloor() == FloorType.LowerLevel2) {
          xrayCounter++;
        }
      }

      for (int i = 0; i < reclinerEquipmentList.size(); i++) {
        if (reclinerEquipmentList.get(i).getLocation().getFloor() == FloorType.LowerLevel2) {
          reclinerCounter++;
        }
      }

      for (int i = 0; i < pumpsEquipmentList.size(); i++) {
        if (pumpsEquipmentList.get(i).getLocation().getFloor() == FloorType.LowerLevel2) {
          if (pumpsEquipmentList.get(i).isClean()) {
            cleanPumpsCounter++;
          } else {
            dirtyPumpsCounter++;
          }
        }
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    xRayLabel.setText("X-Rays: " + xrayCounter);
    hospitalBedLabel.setText("Patient Beds: " + bedCounter);
    reclinerLabel.setText("Recliners: " + reclinerCounter);
    cleanPumpLabel.setText("Clean Pumps: " + cleanPumpsCounter);
    dirtyPumpLabel.setText("Dirty Pumps: " + dirtyPumpsCounter);
  }
}

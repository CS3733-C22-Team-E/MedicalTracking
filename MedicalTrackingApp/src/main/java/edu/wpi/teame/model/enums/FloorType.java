package edu.wpi.teame.model.enums;

public enum FloorType {
  LowerLevel2,
  LowerLevel1,
  GroundFloor,
  FirstFloor,
  SecondFloor,
  ThirdFloor;

  public FloorType getFloorFromString(String floor) {
    switch (floor) {
      case "Lower Level 1":
        return FloorType.LowerLevel1;
      case "Lower Level 2":
        return FloorType.LowerLevel2;
      case "Ground Floor":
        return FloorType.GroundFloor;
      case "First Floor":
        return FloorType.FirstFloor;
      case "Second Floor":
        return FloorType.SecondFloor;
      case "Third Floor":
        return FloorType.ThirdFloor;
    }
    return null;
  }

  @Override
  public String toString() {
    switch (this) {
      case LowerLevel1:
        return "Lower Level 1";
      case LowerLevel2:
        return "Lower Level 2";
      case GroundFloor:
        return "Ground Floor";
      case FirstFloor:
        return "First Floor";
      case SecondFloor:
        return "Second Floor";
      case ThirdFloor:
        return "Third Floor";
    }
    return null;
  }
}

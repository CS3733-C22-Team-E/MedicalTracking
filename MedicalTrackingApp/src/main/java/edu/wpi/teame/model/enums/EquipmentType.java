package edu.wpi.teame.model.enums;

public enum EquipmentType {
  PBED,
  PUMP,
  XRAY,
  RECL;

  public static EquipmentType getValue(String value) {
    switch (value) {
      case "Patient Bed":
        return PBED;
      case "Infusion Pump":
        return PUMP;
      case "X-Ray":
        return XRAY;
      case "Recliner":
        return RECL;
    }
    return null;
  }

  @Override
  public String toString() {
    switch (this) {
      case PBED:
        return "Patient Bed";
      case PUMP:
        return "Infusion Pump";
      case XRAY:
        return "X-Ray";
      case RECL:
        return "Recliner";
    }
    return null;
  }
}

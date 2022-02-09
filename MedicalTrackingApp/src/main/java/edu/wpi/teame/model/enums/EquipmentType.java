package edu.wpi.teame.model.enums;

public enum EquipmentType {
  PBED,
  PUMP,
  XRAY,
  RECL;

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

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

  public String getResource() {
    switch (this) {
      case PBED:
        return "images/Icons/HospitalBedIcon.png";
      case PUMP:
        return "images/Icons/PumpIcon.png";
      case XRAY:
        return "images/Icons/XRayIcon.png";
      case RECL:
        return "images/Icons/ReclinerIcon.png";
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

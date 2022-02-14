package edu.wpi.teame.model.enums;

import javafx.scene.paint.Color;

public enum DataBaseObjectType {
  AudioVisualSR,
  ComputerSR,
  FoodDeliverySR,
  GiftAndFloralSR,
  InternalPatientTransferSR,
  ExternalPatientTransportation,
  LanguageInterpreterSR,
  LaundrySR,
  ReligiousSR,
  SecuritySR,
  MedicalEquipmentSR,
  MedicineDeliverySR,
  FacilitiesMaintenanceSR,
  SanitationSR,
  Location,
  Equipment,
  Employee;

  @Override
  public String toString() {
    switch (this) {
      case AudioVisualSR:
        return "Audio/Visual Service Request";
      case ComputerSR:
        return "Computer Service Request";
      case FoodDeliverySR:
        return "Food Delivery Service Request";
      case GiftAndFloralSR:
        return "Gift/Floral Delivery Service Request";
      case ExternalPatientTransportation:
        return "External Patient Transportation Service Request";
      case LanguageInterpreterSR:
        return "Language Interpreter Service Request";
      case InternalPatientTransferSR:
        return "Internal Patient Transportation Service Request";
      case LaundrySR:
        return "Laundry Service Request";
      case SecuritySR:
        return "Security Service Request";
      case ReligiousSR:
        return "Religious Service Request";
      case SanitationSR:
        return "Sanitation Service Request";
      case MedicalEquipmentSR:
        return "Medical Equipment Delivery Service Request";
      case MedicineDeliverySR:
        return "Medicine Delivery Service Request";
      case FacilitiesMaintenanceSR:
        return "Facilities Maintenance Service Request";
      case Employee:
        return "Employee";
      case Location:
        return "Location";
      case Equipment:
        return "Equipment";
    }
    return null;
  }

  public Color getColor() {
    switch (this) {
      case AudioVisualSR:
        return Color.CYAN;
      case ComputerSR:
        return Color.SILVER;
      case FoodDeliverySR:
        return Color.SALMON;
      case GiftAndFloralSR:
        return Color.MEDIUMAQUAMARINE;
      case ExternalPatientTransportation:
        return Color.SEAGREEN;
      case LanguageInterpreterSR:
        return Color.CORAL;
      case InternalPatientTransferSR:
        return Color.SPRINGGREEN;
      case LaundrySR:
        return Color.WHITESMOKE;
      case SecuritySR:
        return Color.ROYALBLUE;
      case ReligiousSR:
        return Color.GOLD;
      case SanitationSR:
        return Color.AQUA;
      case MedicalEquipmentSR:
      case Equipment:
        return Color.LIGHTGRAY;
      case MedicineDeliverySR:
        return Color.TOMATO;
      case FacilitiesMaintenanceSR:
        return Color.THISTLE;
      case Employee:
        return Color.BLUE;
      case Location:
        return Color.GREEN;
    }
    return null;
  }
}

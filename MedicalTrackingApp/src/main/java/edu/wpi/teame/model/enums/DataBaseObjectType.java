package edu.wpi.teame.model.enums;

import javafx.scene.paint.Color;

public enum DataBaseObjectType {
  AudioVisualSR,
  ComputerSR,
  FoodDeliverySR,
  GiftAndFloralSR,
  InternalPatientTransferSR,
  ExternalPatientSR,
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
  Patient,
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
      case ExternalPatientSR:
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
      case ExternalPatientSR:
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

  public String shortName() {
    switch (this) {
      case AudioVisualSR:
        return "Audio/Visual";
      case ComputerSR:
        return "Computer";
      case FoodDeliverySR:
        return "Food Delivery";
      case GiftAndFloralSR:
        return "Gift/Floral Delivery";
      case ExternalPatientSR:
        return "External Patient Transportation";
      case LanguageInterpreterSR:
        return "Language Interpreter";
      case InternalPatientTransferSR:
        return "Internal Patient Transportation";
      case LaundrySR:
        return "Laundry";
      case SecuritySR:
        return "Security";
      case ReligiousSR:
        return "Religious";
      case SanitationSR:
        return "Sanitation";
      case MedicalEquipmentSR:
        return "Medical Equipment Delivery";
      case MedicineDeliverySR:
        return "Medicine Delivery";
      case FacilitiesMaintenanceSR:
        return "Facilities Maintenance";
      case Employee:
        return "Employee";
      case Location:
        return "Location";
      case Equipment:
        return "Equipment";
    }
    return null;
  }

  public String getDescription() {
    String s = "Fulfil a";
    switch (this) {
      case AudioVisualSR:
        s += "n Audio/Visual Service Request";
      case ComputerSR:
        s += " Computer Service Request";
      case FoodDeliverySR:
        s += " Food Delivery Service Request";
      case GiftAndFloralSR:
        s += " Gift/Floral Delivery Service Request";
      case ExternalPatientSR:
        s += "n External Patient Transportation Service Request";
      case LanguageInterpreterSR:
        s += " Language Interpreter Service Request";
      case InternalPatientTransferSR:
        s += "n Internal Patient Transportation Service Request";
      case LaundrySR:
        s += " Laundry Service Request";
      case SecuritySR:
        s += " Security Service Request";
      case ReligiousSR:
        s += " Religious Service Request";
      case SanitationSR:
        s += " Sanitation Service Request";
      case MedicalEquipmentSR:
        s += " Medical Equipment Delivery Service Request";
      case MedicineDeliverySR:
        s += " Medicine Delivery Service Request";
      case FacilitiesMaintenanceSR:
        s += " Facilities Maintenance Service Request";
      case Employee:
        return "This is an employee.";
      case Location:
        return "This is a location.";
      case Equipment:
        return "This is an equipment.";
    }
    return s + " at the requested location.";
  }
}

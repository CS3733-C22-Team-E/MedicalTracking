package edu.wpi.teame.model.enums;

import javafx.scene.paint.Color;

public enum DataBaseObjectType {
  Credential,
  Location,
  Equipment,
  Patient,
  Employee,
  Edge,

  AudioVisualSR,
  ComputerSR,
  DeceasedBodySR,
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
  MentalHealthSR,
  PatientDischargeSR,
  FacilitiesMaintenanceSR,
  SanitationSR;

  @Override
  public String toString() {
    switch (this) {
      case AudioVisualSR:
        return "Audio/Visual Service Request";
      case ComputerSR:
        return "Computer Service Request";
      case DeceasedBodySR:
        return "Deceased Body Removal Service Request";
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
      case PatientDischargeSR:
        return "Patient Discharge Service Request";
      case FacilitiesMaintenanceSR:
        return "Facilities Maintenance Service Request";
      case MentalHealthSR:
        return "Mental Health Service Request";
      case Patient:
        return "Patient";
      case Employee:
        return "Employee";
      case Credential:
        return "Credential";
      case Location:
        return "Location";
      case Equipment:
        return "Equipment";
      case Edge:
        return "Edge";
    }
    return null;
  }

  public static DataBaseObjectType getValue(String string) {
    switch (string) {
      case "Audio/Visual Service Request":
        return AudioVisualSR;
      case "Computer Service Request":
        return ComputerSR;
      case "Deceased Body Removal Service Request":
        return DeceasedBodySR;
      case "Food Delivery Service Request":
        return FoodDeliverySR;
      case "Gift/Floral Delivery Service Request":
        return GiftAndFloralSR;
      case "External Patient Transportation Service Request":
        return ExternalPatientSR;
      case "Language Interpreter Service Request":
        return LanguageInterpreterSR;
      case "Internal Patient Transportation Service Request":
        return InternalPatientTransferSR;
      case "Laundry Service Request":
        return LaundrySR;
      case "Security Service Request":
        return SecuritySR;
      case "Religious Service Request":
        return ReligiousSR;
      case "Sanitation Service Request":
        return SanitationSR;
      case "Medical Equipment Delivery Service Request":
        return MedicalEquipmentSR;
      case "Medicine Delivery Service Request":
        return MedicineDeliverySR;
      case "Facilities Maintenance Service Request":
        return FacilitiesMaintenanceSR;
      case "Patient Discharge Service Request":
        return PatientDischargeSR;
      case "Mental Health Service Request":
        return MentalHealthSR;
      case "Patient":
        return Patient;
      case "Employee":
        return Employee;
      case "Location":
        return Location;
      case "Equipment":
        return Equipment;
      case "Edge":
        return Edge;
      case "Credential":
        return Credential;
    }
    return null;
  }

  public String toTableName() {
    switch (this) {
      case AudioVisualSR:
        return "AudioVisualSR";
      case ComputerSR:
        return "ComputerSR";
      case DeceasedBodySR:
        return "DeceasedBodySR";
      case FoodDeliverySR:
        return "FoodDeliverySR";
      case GiftAndFloralSR:
        return "GiftAndFloralSR";
      case ExternalPatientSR:
        return "ExternalPatientSR";
      case LanguageInterpreterSR:
        return "LanguageInterpreterSR";
      case InternalPatientTransferSR:
        return "InternalPatientTransferSR";
      case LaundrySR:
        return "LaundrySR";
      case SecuritySR:
        return "SecuritySR";
      case ReligiousSR:
        return "ReligiousSR";
      case SanitationSR:
        return "SanitationSR";
      case MedicalEquipmentSR:
        return "MedicalEquipmentSR";
      case MedicineDeliverySR:
        return "MedicineDeliverySR";
      case PatientDischargeSR:
        return "PatientDischargeSR";
      case FacilitiesMaintenanceSR:
        return "FacilitiesMaintenanceSR";
      case MentalHealthSR:
        return "MentalHealthSR";
      case Employee:
        return "Employee";
      case Location:
        return "Location";
      case Equipment:
        return "Equipment";
      case Patient:
        return "Patient";
      case Edge:
        return "Edge";
      case Credential:
        return "Credential";
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
      case MentalHealthSR:
        return Color.RED;
      case DeceasedBodySR:
        return Color.BLACK;
      case PatientDischargeSR:
        return Color.YELLOW;
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
      case MentalHealthSR:
        return "Mental Health";
      case DeceasedBodySR:
        return "Deceased Body";
      case PatientDischargeSR:
        return "Patient Discharge";
      case Employee:
        return "Employee";
      case Location:
        return "Location";
      case Equipment:
        return "Equipment";
      case Edge:
        return "Edge";
      case Credential:
        return "Credential";
    }
    return null;
  }

  public String getDescription() {
    String s = "Fulfil a";
    switch (this) {
      case AudioVisualSR:
        s += "n Audio/Visual Service Request";
        break;
      case ComputerSR:
        s += " Computer Service Request";
        break;
      case FoodDeliverySR:
        s += " Food Delivery Service Request";
        break;
      case GiftAndFloralSR:
        s += " Gift/Floral Delivery Service Request";
        break;
      case ExternalPatientSR:
        s += "n External Patient Transportation Service Request";
        break;
      case LanguageInterpreterSR:
        s += " Language Interpreter Service Request";
        break;
      case InternalPatientTransferSR:
        s += "n Internal Patient Transportation Service Request";
        break;
      case LaundrySR:
        s += " Laundry Service Request";
        break;
      case SecuritySR:
        s += " Security Service Request";
        break;
      case ReligiousSR:
        s += " Religious Service Request";
        break;
      case SanitationSR:
        s += " Sanitation Service Request";
        break;
      case MedicalEquipmentSR:
        s += " Medical Equipment Delivery Service Request";
        break;
      case MedicineDeliverySR:
        s += " Medicine Delivery Service Request";
        break;
      case FacilitiesMaintenanceSR:
        s += " Facilities Maintenance Service Request";
        break;
      case MentalHealthSR:
        s += " Mental Health Service Request";
        break;
      case DeceasedBodySR:
        s += " Deceased Body Removal Service Request";
        break;
      case PatientDischargeSR:
        s += " Patient Discharge Service Request";
        break;
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

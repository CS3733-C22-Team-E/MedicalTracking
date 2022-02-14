package edu.wpi.teame.model.enums;

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
}

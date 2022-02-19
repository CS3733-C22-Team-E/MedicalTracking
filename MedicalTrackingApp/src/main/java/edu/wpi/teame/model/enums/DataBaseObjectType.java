package edu.wpi.teame.model.enums;

import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.objectManagers.ObjectManager;
import java.sql.SQLException;
import javafx.scene.paint.Color;

public enum DataBaseObjectType {
  Location,
  Equipment,
  Patient,
  Employee,

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
  SanitationSR;

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

  public String toTableName() {
    switch (this) {
      case AudioVisualSR:
        return "AudioVisualSR";
      case ComputerSR:
        return "ComputerSR";
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
      case FacilitiesMaintenanceSR:
        return "FacilitiesMaintenanceSR";
      case Employee:
        return "Employee";
      case Location:
        return "Location";
      case Equipment:
        return "Equipment";
      case Patient:
        return "Patient";
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
      case Employee:
        return "This is an employee.";
      case Location:
        return "This is a location.";
      case Equipment:
        return "This is an equipment.";
    }
    return s + " at the requested location.";
  }

  public ObjectManager getDBManagerInstance() throws SQLException {
    switch (this) {
      case AudioVisualSR:
        return DBManager.getInstance().getAudioVisualSRManager();
      case ComputerSR:
        return DBManager.getInstance().getComputerSRManager();
      case FoodDeliverySR:
        return DBManager.getInstance().getFoodDeliverySRManager();
      case GiftAndFloralSR:
        return DBManager.getInstance().getGiftAndFloralSRManager();
      case ExternalPatientSR:
        return DBManager.getInstance().getExternalPatientSRManager();
      case LanguageInterpreterSR:
        return DBManager.getInstance().getLanguageSRManager();
      case InternalPatientTransferSR:
        return DBManager.getInstance().getInternalPatientSRManager();
      case LaundrySR:
        return DBManager.getInstance().getLaundrySRManager();
      case SecuritySR:
        return DBManager.getInstance().getSecuritySRManager();
      case ReligiousSR:
        return DBManager.getInstance().getReligiousSRManager();
      case SanitationSR:
        return DBManager.getInstance().getSanitationSRManager();
      case MedicalEquipmentSR:
        return DBManager.getInstance().getMedicalEquipmentSRManager();
      case MedicineDeliverySR:
        return DBManager.getInstance().getMedicineDeliverySRManager();
      case FacilitiesMaintenanceSR:
        return DBManager.getInstance().getFacilitiesMaintenanceSRManager();
      case Employee:
        return DBManager.getInstance().getEmployeeManager();
      case Location:
        return DBManager.getInstance().getLocationManager();
      case Equipment:
        return DBManager.getInstance().getEquipmentManager();
    }
    return null;
  }
}

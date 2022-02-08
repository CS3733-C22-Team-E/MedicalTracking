package edu.wpi.teame.db;

public class MedicalEquipmentServiceRequest {
  private int id;
  private String patient;
  private Location roomID;
  private String startTIme;
  private String endTime;
  private String date;
  private String assignee;
  private Equipment equipmentID;
  private MedicalEquipmentServiceRequestStatus status;

  /*
  private String id;
  private String patient;
  private Location room;
  private String startTIme;
  private String endTime;
  private String date;
  private String assignee;
  private Equipment equipment;
  private MedicalEquipmentServiceRequestStatus status;
   */

  public MedicalEquipmentServiceRequest(
      int id,
      String patient,
      Location room,
      String startTIme,
      String endTime,
      String date,
      String assignee,
      Equipment equipment,
      MedicalEquipmentServiceRequestStatus status) {
    this.id = id;
    this.patient = patient;
    this.roomID = room;
    this.startTIme = startTIme;
    this.endTime = endTime;
    this.date = date;
    this.assignee = assignee;
    this.equipmentID = equipment;
    this.status = status;
  }

  public int getId() {
    return id;
  }

  public String getPatient() {
    return patient;
  }

  public Location getRoom() {
    return roomID;
  }

  public String getStartTIme() {
    return startTIme;
  }

  public String getEndTime() {
    return endTime;
  }

  public String getDate() {
    return date;
  }

  public String getAssignee() {
    return assignee;
  }

  public Equipment getEquipment() {
    return equipmentID;
  }

  public MedicalEquipmentServiceRequestStatus getStatus() {
    return status;
  }

  public void setId(int id) {
    id = id;
  }

  public void setPatient(String patient) {
    this.patient = patient;
  }

  public void setRoom(Location room) {
    this.roomID = room;
  }

  public void setStartTIme(String startTIme) {
    this.startTIme = startTIme;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public void setAssignee(String assignee) {
    this.assignee = assignee;
  }

  public void setEquipment(Equipment equipment) {
    this.equipmentID = equipment;
  }

  public void setStatus(MedicalEquipmentServiceRequestStatus status) {
    this.status = status;
  }

  public String toString() {
    return "ID: "
        + id
        + ", Patient: "
        + patient
        + ", roomID: "
        + roomID.getId()
        + ", startTime: "
        + startTIme
        + ", endTime: "
        + endTime
        + ", date: "
        + date
        + ", assignee: "
        + assignee
        + ", equipmentID: "
        + equipmentID.getNodeID()
        + ", status: "
        + status;
  }
}

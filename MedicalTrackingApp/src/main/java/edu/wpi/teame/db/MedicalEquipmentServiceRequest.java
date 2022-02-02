package edu.wpi.teame.db;

public class MedicalEquipmentServiceRequest {
    private String id;
    private String patient;
    private String room;
    private String startTIme;
    private String endTime;
    private String date;
    private String assignee;
    private Equipment equipment;
    private String status;


    public MedicalEquipmentServiceRequest(String id, String patient, String room, String startTIme, String endTime, String date, String assignee, Equipment equipment, String status) {
        this.id = id;
        this.patient = patient;
        this.room = room;
        this.startTIme = startTIme;
        this.endTime = endTime;
        this.date = date;
        this.assignee = assignee;
        this.equipment = equipment;
        this.status = status;
    }

    public String getPatient() {
        return patient;
    }

    public String getRoom() {
        return room;
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
        return equipment;
    }

    public String getStatus() {
        return status;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public void setRoom(String room) {
        this.room = room;
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
        this.equipment = equipment;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

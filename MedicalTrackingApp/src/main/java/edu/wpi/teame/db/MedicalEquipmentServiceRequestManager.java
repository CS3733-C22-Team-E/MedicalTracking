package edu.wpi.teame.db;

import java.sql.*;
import java.util.LinkedList;

public class MedicalEquipmentServiceRequestManager
    implements IManager<MedicalEquipmentServiceRequest> {
  private Connection connection;
  private Statement stmt;

  public MedicalEquipmentServiceRequestManager() {
    connection = DBManager.getInstance().getConnection();

    try {
      stmt = connection.createStatement();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public MedicalEquipmentServiceRequest get(String id) {
    String getQuery = "SELECT * FROM EQUIPMENTSERVICEREQUEST WHERE id='" + id + "'";
    MedicalEquipmentServiceRequest result = null;
    try {
      ResultSet rset = stmt.executeQuery(getQuery);

      while (rset.next()) {
        result =
            new MedicalEquipmentServiceRequest(
                rset.getString("id"),
                rset.getString("patient"),
                rset.getString("room"),
                rset.getString("startTime"),
                rset.getString("endTime"),
                rset.getString("date"),
                rset.getString("assignee"),
                rset.getString("equipment"),
                rset.getString("status"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public LinkedList<MedicalEquipmentServiceRequest> getAll() {
    String getQuery = "SELECT * FROM EQUIPMENTSERVICEREQUEST";
    LinkedList<MedicalEquipmentServiceRequest> result = new LinkedList<>();
    try {
      ResultSet rset = stmt.executeQuery(getQuery);

      while (rset.next()) {
        result.add(
                new MedicalEquipmentServiceRequest(
                        rset.getString("id"),
                        rset.getString("patient"),
                        rset.getString("room"),
                        rset.getString("startTime"),
                        rset.getString("endTime"),
                        rset.getString("date"),
                        rset.getString("assignee"),
                        rset.getString("equipment"),
                        rset.getString("status")));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public void insert(MedicalEquipmentServiceRequest newObject) {
    String insertQuery =
        "INSERT INTO EQUIPMENTSERVICEREQUEST VALUES('"
            + newObject.getId()
            + "','"
            + newObject.getPatient()
            + "','"
            + newObject.getRoom()
            + "','"
            + newObject.getStartTIme()
            + "','"
            + newObject.getEndTime()
            + "','"
            + newObject.getDate()
            + "','"
            + newObject.getAssignee()
            + "','"
            + newObject.getEquipment()
            + "','"
            + newObject.getStatus()
            + "')";

    try {
      stmt.executeUpdate(insertQuery);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void remove(String id) {
    String removeQuery = "DELETE FROM EQUIPMENTSERVICEREQUEST WHERE nodeID='" + id + "'";

    try {
      stmt.executeUpdate(removeQuery);
    } catch (SQLException e) {
      System.out.println("Case 4: Could not drop nodeID");
      e.printStackTrace();
    }
  }

  /*
  private String id;
  private String patient;
  private String room;
  private String startTIme;
  private String endTime;
  private String date;
  private String assignee;
  private String equipment;
  private String status;
   */

  @Override
  public void update(MedicalEquipmentServiceRequest updatedObject) {
    String updateQuery =
        "UPDATE LOCATIONS SET patient = '"
            + updatedObject.getPatient()
            + "', room = '"
            + updatedObject.getRoom()
            + "', startTime = '"
            + updatedObject.getStartTIme()
            + "', endTime = '"
            + updatedObject.getEndTime()
            + "', date = '"
            + updatedObject.getDate()
            + "', assignee = '"
            + updatedObject.getAssignee()
            + "', equipment = '"
            + updatedObject.getEquipment()
            + "', status = '"
            + updatedObject.getStatus()
            + "' WHERE id = '"
            + updatedObject.getId()
            + "'";

    try {
      stmt.executeUpdate(updateQuery);
      System.out.println("Location updated successfully");
    } catch (SQLException e) {
      System.out.println("Case2: Could not update Locations table");
      e.printStackTrace();
    }
  }
}

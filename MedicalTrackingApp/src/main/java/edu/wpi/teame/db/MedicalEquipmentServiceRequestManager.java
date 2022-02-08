package edu.wpi.teame.db;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    LocationManager locationTable = DBManager.getInstance().getLocationManager();
    EquipmentManager equipmentTable = DBManager.getInstance().getEquipmentManager();
    try {
      ResultSet rset = stmt.executeQuery(getQuery);

      while (rset.next()) {
        Location serReqLocation = locationTable.get(rset.getString("roomID"));
        Equipment serReqEquipment = equipmentTable.get(rset.getString("equipmentID"));

        result =
            new MedicalEquipmentServiceRequest(
                rset.getInt("id"),
                rset.getString("patient"),
                serReqLocation,
                rset.getString("startTime"),
                rset.getString("endTime"),
                rset.getString("date"),
                rset.getString("assignee"),
                serReqEquipment,
                MedicalEquipmentServiceRequestStatus.values()[rset.getInt("status")]);
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
    LocationManager locationTable = DBManager.getInstance().getLocationManager();
    EquipmentManager equipmentTable = DBManager.getInstance().getEquipmentManager();
    try {
      ResultSet rset = stmt.executeQuery(getQuery);

      while (rset.next()) {
        Location serReqLocation = locationTable.get(rset.getString("roomID"));
        Equipment serReqEquipment = equipmentTable.get(rset.getString("equipmentID"));

        result.add(
            new MedicalEquipmentServiceRequest(
                rset.getInt("id"),
                rset.getString("patient"),
                serReqLocation,
                rset.getString("startTime"),
                rset.getString("endTime"),
                rset.getString("date"),
                rset.getString("assignee"),
                serReqEquipment,
                MedicalEquipmentServiceRequestStatus.values()[rset.getInt("status")]));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;
    /**
     * + "patient VARCHAR(100), " + "roomID VARCHAR(10) ," + "startTime VARCHAR(50)," + "endTime
     * VARCHAR(50)," + "date VARCHAR(100)," + "assignee VARCHAR(100)," + "equipmentID VARCHAR(10),"
     * + "status int," + "FOREIGN KEY (roomID) REFERENCES LOCATIONS(id)," + "FOREIGN KEY
     * (equipmentID) REFERENCES EQUIPMENT(id))";
     */
  }

  @Override
  public void insert(MedicalEquipmentServiceRequest newObject) {
    String insertQuery =
        "INSERT INTO EQUIPMENTSERVICEREQUEST (patient, roomID, startTime, endTime, date, assignee, equipmentID, status)VALUES('"
            + newObject.getPatient()
            + "','"
            + newObject.getRoom().getId()
            + "','"
            + newObject.getStartTIme()
            + "','"
            + newObject.getEndTime()
            + "','"
            + newObject.getDate()
            + "','"
            + newObject.getAssignee()
            + "','"
            + newObject.getEquipment().getNodeID()
            + "',"
            + newObject.getStatus().ordinal()
            + ")";

    newObject.getEquipment().setHasPatient(true);
    newObject.getEquipment().setLocationNode(newObject.getRoom());
    DBManager.getInstance().getEquipmentManager().update(newObject.getEquipment());

    try {
      stmt.executeUpdate(insertQuery);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void remove(String id) {
    String removeQuery = "DELETE FROM EQUIPMENTSERVICEREQUEST WHERE id='" + id + "'";

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
        "UPDATE EQUIPMENTSERVICEREQUEST SET patient = '"
            + updatedObject.getPatient()
            + "', roomID = '"
            + updatedObject.getRoom().getId()
            + "', startTime = '"
            + updatedObject.getStartTIme()
            + "', endTime = '"
            + updatedObject.getEndTime()
            + "', date = '"
            + updatedObject.getDate()
            + "', assignee = '"
            + updatedObject.getAssignee()
            + "', equipmentID = '"
            + updatedObject.getEquipment().getNodeID()
            + "', status = "
            + updatedObject.getStatus().ordinal()
            + " WHERE id = '"
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

  @Override
  public void readCSV(String csvFile) {
    try {
      String path = System.getProperty("user.dir") + "/src/main/resources/edu/wpi/teame/" + csvFile;
      File file = new File(path);
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);
      String line = " ";
      String[] tempArr;

      LocationManager locTable = DBManager.getInstance().getLocationManager();
      EquipmentManager equipTable = DBManager.getInstance().getEquipmentManager();

      boolean firstLine = true;
      String delimiter = ",";
      while ((line = br.readLine()) != null) {
        if (!firstLine) {
          tempArr = line.split(delimiter);
          MedicalEquipmentServiceRequest tempSerReq =
              new MedicalEquipmentServiceRequest(
                  Integer.parseInt(tempArr[0]),
                  1 >= tempArr.length ? "" : tempArr[1],
                  2 >= tempArr.length ? null : locTable.get(tempArr[2]),
                  3 >= tempArr.length ? "" : tempArr[3],
                  4 >= tempArr.length ? "" : tempArr[4],
                  5 >= tempArr.length ? "" : tempArr[5],
                  6 >= tempArr.length ? "" : tempArr[6],
                  7 >= tempArr.length ? null : equipTable.get(tempArr[7]),
                  8 >= tempArr.length
                      ? null
                      : MedicalEquipmentServiceRequestStatus.valueOf(tempArr[8]));

          insert(tempSerReq);
        } else {
          firstLine = false;
        }
      }

      br.close();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  @Override
  public void writeToCSV(String outputFilePath) {
    String csvSeparator = ",";
    try {
      try {
        File myObj = new File(outputFilePath + ".csv");
        if (myObj.createNewFile()) {
          System.out.println("File created: " + myObj.getName());
        } else {
          System.out.println("File already exists.");
        }
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }

      BufferedWriter bw =
          new BufferedWriter(
              new OutputStreamWriter(new FileOutputStream(outputFilePath + ".csv"), "UTF-8"));

      // Create a title for the CSV as the list of Locations doesn't have one
      // Put the title at the start of the locations list
      StringBuffer title =
          new StringBuffer(
              "ServiceRequestID,Patient,Room,StartTime,EndTime,Date,Assignee,Equipment,Status");
      // Add buffer string to buffered writer
      bw.write(title.toString());
      // Drops down to next row
      bw.newLine();

      LinkedList<MedicalEquipmentServiceRequest> serReqList = getAll();

      // Go through each Location in the list
      for (MedicalEquipmentServiceRequest serReq : serReqList) {
        String locId = Integer.toString(serReq.getId());
        String room = serReq.getRoom().getId();
        String equipmentID = serReq.getEquipment().getNodeID();
        String status = serReq.getStatus().toString();

        // Create a single temporary string buffer
        StringBuffer oneLine = new StringBuffer();
        // Add nodeID to buffer
        oneLine.append(locId.trim().length() == 0 ? "" : serReq.getId());
        // Add comma separator
        oneLine.append(csvSeparator);
        // Add xcoord to buffer
        oneLine.append(
            serReq.getPatient() == null || (serReq.getPatient().trim().length() == 0)
                ? ""
                : serReq.getPatient());
        // Add comma separator
        oneLine.append(csvSeparator);
        // Add ycoord to buffer
        oneLine.append(room == null || (room.trim().length() == 0) ? "" : room);
        // Add comma separator
        oneLine.append(csvSeparator);
        // Add floor to buffer
        oneLine.append(
            serReq.getStartTIme() == null || (serReq.getStartTIme().trim().length() == 0)
                ? ""
                : serReq.getStartTIme());
        // Add comma separator
        oneLine.append(csvSeparator);
        // Add building to buffer
        oneLine.append(
            serReq.getEndTime() == null || (serReq.getEndTime().trim().length() == 0)
                ? ""
                : serReq.getEndTime());
        // Add comma separator
        oneLine.append(csvSeparator);
        // Add nodeType to buffer
        oneLine.append(
            serReq.getDate() == null || (serReq.getDate().trim().length() == 0)
                ? ""
                : serReq.getDate());
        // Add comma separator
        oneLine.append(csvSeparator);
        // Add longName to buffer
        oneLine.append(
            serReq.getAssignee() == null || (serReq.getAssignee().trim().length() == 0)
                ? ""
                : serReq.getAssignee());
        // Add comma separator
        oneLine.append(csvSeparator);
        // Add shortName to buffer
        oneLine.append(
            equipmentID == null || (equipmentID.trim().length() == 0) ? "" : equipmentID);
        // Add comma separator
        oneLine.append(csvSeparator);
        // Add longName to buffer
        oneLine.append(status == null || (status.trim().length() == 0) ? "" : status);

        // Add buffer string to buffered writer
        bw.write(oneLine.toString());
        // Drops down to next row
        bw.newLine();
      }
      // Applies buffer to file
      bw.flush();
      bw.close();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      e.printStackTrace();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

package edu.wpi.teame.db;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class EquipmentManager implements IManager<Equipment> {
  private Connection connection;
  private Statement stmt;

  public EquipmentManager() {
    connection = DBManager.getInstance().getConnection();

    try {
      stmt = connection.createStatement();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  // get the Equipment object with the specified ID
  public Equipment get(String id) {
    String getQuery = "SELECT * FROM EQUIPMENT WHERE ID = '" + id + "'";
    Equipment result = null;
    LocationManager locationTable = DBManager.getInstance().getLocationManager();
    try {
      ResultSet rset = stmt.executeQuery(getQuery);

      while (rset.next()) {
        String nodeID = rset.getString("ID");
        Location locationNode = locationTable.get(rset.getString("locationNode"));
        // FloorType floor = FloorType.valueOf(rset.getString("floor"));
        // BuildingType building = BuildingType.valueOf(rset.getString("building"));
        EquipmentType type = EquipmentType.values()[rset.getInt("type")];
        String name = rset.getString("name");
        boolean hasPatient = rset.getBoolean("hasPatient");
        boolean isClean = rset.getBoolean("isClean");

        // save the tuple that resulted from the query
        result = new Equipment(nodeID, locationNode, type, name, hasPatient, isClean);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;
  }

  public Equipment getAvailable() {
    String getQuery = "SELECT * FROM EQUIPMENT WHERE hasPatient = FALSE";
    Equipment result = null;
    LocationManager locationTable = DBManager.getInstance().getLocationManager();
    try {
      ResultSet rset = stmt.executeQuery(getQuery);

      while (rset.next()) {
        String nodeID = rset.getString("ID");
        Location locationNode = locationTable.get(rset.getString("locationNode"));
        // FloorType floor = FloorType.valueOf(rset.getString("floor"));
        // BuildingType building = BuildingType.valueOf(rset.getString("building"));
        EquipmentType type = EquipmentType.values()[rset.getInt("type")];
        String name = rset.getString("name");
        boolean hasPatient = rset.getBoolean("hasPatient");
        boolean isClean = rset.getBoolean("isClean");

        // save the tuple that resulted from the query
        result = new Equipment(nodeID, locationNode, type, name, hasPatient, isClean);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public LinkedList<Equipment> getAll() {
    String getQuery = "SELECT * FROM EQUIPMENT";
    LinkedList<Equipment> result = new LinkedList<>();

    LocationManager locationTable = DBManager.getInstance().getLocationManager();
    try {
      ResultSet rset = stmt.executeQuery(getQuery);

      while (rset.next()) {
        String nodeID = rset.getString("ID");
        Location locationNode = locationTable.get(rset.getString("locationNode"));
        // FloorType floor = FloorType.valueOf(rset.getString("floor"));
        // BuildingType building = BuildingType.valueOf(rset.getString("building"));
        EquipmentType type = EquipmentType.values()[rset.getInt("type")];
        String name = rset.getString("name");
        boolean hasPatient = rset.getBoolean("hasPatient");
        boolean isClean = rset.getBoolean("isClean");

        // add next tuple to return list
        result.add(new Equipment(nodeID, locationNode, type, name, hasPatient, isClean));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public void insert(Equipment newObject) {
    String insertStmt =
        "INSERT INTO EQUIPMENT VALUES('"
            + newObject.getNodeID()
            + "', '"
            + ((newObject.getLocationNode() == null)
                ? ""
                : newObject
                    .getLocationNode()
                    .getId()) // todo handle error where location is not defined in the db
            + "', "
            + newObject.getType().ordinal()
            + ", '"
            + newObject.getName()
            + "', "
            + newObject.isHasPatient()
            + ", "
            + newObject.isClean()
            + ")";

    try {
      stmt.executeUpdate(insertStmt);
    } catch (SQLException e) {
      System.out.println("Case2: Could not update Locations table");
      e.printStackTrace();
    }
  }

  @Override
  public void remove(String id) {
    String removeQuery = "DELETE FROM EQUIPMENT WHERE id = '" + id + "'";
    try {
      stmt.executeUpdate(removeQuery);
    } catch (SQLException e) {
      System.out.println("Could not drop nodeID");
      e.printStackTrace();
    }
  }

  @Override
  public void update(Equipment updatedObject) {
    /*
    String updateQuery =
            "UPDATE Equipment SET ID = '"
                    + updatedObject.getNodeID()
                    + "', locationNode = '"
                    + updatedObject.getlocationNode()
                    + "', floor = '"
                    + updatedObject.getFloor()
                    + "', building = '"
                    + updatedObject.getBuilding()
                    + "', hasPatient = '"
                    + updatedObject.getType()
                    + "', longName = '"
                    + updatedObject.getName()
                    + "' WHERE id = '"
                    + updatedObject.getID()
                    + "'";
    try {
      stmt.executeUpdate(updateQuery);
      System.out.println("Location updated successfully");
    } catch (SQLException e) {
      System.out.println("Case2: Could not update Locations table");
      e.printStackTrace();
    }

     */

    String updateQuery =
        "UPDATE EQUIPMENT SET "
            + "locationNode = '"
            + updatedObject.getLocationNode().getId()
            + "', type = "
            + updatedObject.getType().ordinal()
            + ", name = '"
            + updatedObject.getName()
            + "', hasPatient = "
            + updatedObject.isHasPatient()
            + ", isClean = "
            + updatedObject.isClean()
            + " WHERE id = '"
            + updatedObject.getNodeID()
            + "'";

    try {
      stmt.executeUpdate(updateQuery);
    } catch (SQLException e) {
      System.out.println("Case2: Could not update Locations table");
      e.printStackTrace();
    }
  }

  @Override
  public void readCSV(String csvFile) throws IOException {
    String path = System.getProperty("user.dir") + "/src/main/resources/edu/wpi/teame/" + csvFile;

    File file = new File(path);
    FileReader fr = new FileReader(file);
    BufferedReader br = new BufferedReader(fr);
    String line = " ";
    String[] tempArr;

    LocationManager locTable = DBManager.getInstance().getLocationManager();
    boolean firstLine = true;
    String delimiter = ",";
    while ((line = br.readLine()) != null) {
      if (!firstLine) {
        tempArr = line.split(delimiter);
        Equipment tempEquipment =
            new Equipment(
                tempArr[0],
                1 >= tempArr.length ? null : locTable.get(tempArr[1]),
                2 >= tempArr.length ? null : EquipmentType.valueOf(tempArr[2]),
                3 >= tempArr.length ? "" : tempArr[3],
                4 >= tempArr.length ? false : Boolean.parseBoolean(tempArr[4]),
                5 >= tempArr.length ? false : Boolean.parseBoolean(tempArr[5]));

        insert(tempEquipment);
      } else {
        firstLine = false;
      }
    }

    br.close();
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
          new StringBuffer("nodeID,locationNodeID,nodeType,longName,hasPatient,isClean");
      // Add buffer string to buffered writer
      bw.write(title.toString());
      // Drops down to next row
      bw.newLine();

      LinkedList<Equipment> equipmentList = getAll();

      // Go through each Location in the list
      for (Equipment equipment : equipmentList) {
        String locationNode = equipment.getLocationNode().getId();
        String equipmentType = equipment.getType().toString();
        String hasPatient = Boolean.toString(equipment.isHasPatient());
        String isClean = Boolean.toString(equipment.isClean());

        // Create a single temporary string buffer
        StringBuffer oneLine = new StringBuffer();
        // Add nodeID to buffer
        oneLine.append(equipment.getNodeID().trim().length() == 0 ? "" : equipment.getNodeID());
        // Add comma separator
        oneLine.append(csvSeparator);
        // Add xcoord to buffer
        oneLine.append(
            locationNode == null || (locationNode.trim().length() == 0) ? "" : locationNode);
        // Add comma separator
        oneLine.append(csvSeparator);
        // Add ycoord to buffer
        oneLine.append(
            equipmentType == null || (equipmentType.trim().length() == 0) ? "" : equipmentType);
        // Add comma separator
        oneLine.append(csvSeparator);
        // Add floor to buffer
        oneLine.append(
            equipment.getName() == null || (equipment.getName().trim().length() == 0)
                ? ""
                : equipment.getName());
        // Add comma separator
        oneLine.append(csvSeparator);
        // Add building to buffer
        oneLine.append(hasPatient == null || (hasPatient.trim().length() == 0) ? "" : hasPatient);
        // Add comma separator
        oneLine.append(csvSeparator);
        // Add nodeType to buffer
        oneLine.append(isClean == null || (isClean.trim().length() == 0) ? "" : isClean);

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

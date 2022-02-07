package edu.wpi.teame.db;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class LocationManager implements IManager<Location> {
  private static Connection connection;
  private static Statement stmt;

  public LocationManager() {
    connection = DBManager.getInstance().getConnection();

    try {
      stmt = connection.createStatement();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Location get(String id) {
    Location result = null;
    String getQuery = "SELECT * FROM LOCATIONS WHERE id='" + id + "'";
    try {
      ResultSet rset = stmt.executeQuery(getQuery);
      while (rset.next()) {
        int x = rset.getInt("x");
        int y = rset.getInt("y");
        String longName = rset.getString("longName");
        FloorType floor = FloorType.values()[rset.getInt("floor")];
        BuildingType building = BuildingType.values()[rset.getInt("building")];
        LocationType locationType = LocationType.values()[rset.getInt("locationType")];
        String shortName = rset.getString("shortName");

        // convert strings to proper type
        result = new Location(id, longName, x, y, floor, building, locationType, shortName);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public LinkedList<Location> getAll() {
    String getAllQuery = "SELECT * FROM LOCATIONS";
    LinkedList<Location> result = new LinkedList<>();

    try {
      ResultSet rset = stmt.executeQuery(getAllQuery);
      while (rset.next()) {
        int x = rset.getInt("x");
        int y = rset.getInt("y");
        String id = rset.getString("id");
        String name = rset.getString("longName");
        FloorType floor = FloorType.values()[rset.getInt("floor")];
        BuildingType building = BuildingType.values()[rset.getInt("building")];
        LocationType locationType = LocationType.values()[rset.getInt("locationType")];
        String shortName = rset.getString("shortName");

        // convert strings to proper type
        result.add(new Location(id, name, x, y, floor, building, locationType, shortName));
      }
    } catch (SQLException e) {
      System.out.println("Could not retrieve all of the locations from the table");
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public void insert(Location newObject) {
    String insertQuery =
        "INSERT INTO LOCATIONS VALUES('"
            + newObject.getId()
            + "',"
            + newObject.getX()
            + ","
            + newObject.getY()
            + ","
            + newObject.getFloor().ordinal()
            + ","
            + newObject.getBuilding().ordinal()
            + ","
            + newObject.getType().ordinal()
            + ",'"
            + newObject.getLongName()
            + "','"
            + newObject.getShortName()
            + "')";
    try {
      stmt.executeUpdate(insertQuery);
    } catch (SQLException e) {
      System.out.println("Could not insert into Locations");
      e.printStackTrace();
    }
  }

  @Override
  public void remove(String id) {
    String removeQuery = "DELETE FROM LOCATIONS WHERE id='" + id + "'";
    try {
      stmt.executeUpdate(removeQuery);
    } catch (SQLException e) {
      System.out.println("Case 4: Could not drop nodeID");
      e.printStackTrace();
    }
  }

  // floor,building,nodeType,longName,shortName
  @Override
  public void update(Location updatedObject) {
    String updateQuery =
        "UPDATE LOCATIONS SET x = "
            + updatedObject.getY()
            + ", y = "
            + updatedObject.getX()
            + ", floor = "
            + updatedObject.getFloor().ordinal()
            + ", building = "
            + updatedObject.getBuilding().ordinal()
            + ", locationType = "
            + updatedObject.getType().ordinal()
            + ", longName = '"
            + updatedObject.getLongName()
            + "', shortName = '"
            + updatedObject.getShortName()
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

  @Override
  public void readCSV(String csvFile) throws IOException {
    csvFile =
        System.getProperty("user.dir")
            + "\\src\\main\\resources\\edu\\wpi\\teame\\csv\\TowerLocationsE.csv";

    File file = new File(csvFile);
    FileReader fr = new FileReader(file);
    BufferedReader br = new BufferedReader(fr);
    String line = " ";
    String[] tempArr;

    boolean firstLine = true;
    String delimiter = ",";
    while ((line = br.readLine()) != null) {
      if (!firstLine) {
        tempArr = line.split(delimiter);
        Location tempLocation =
            new Location(
                tempArr[0],
                6 >= tempArr.length ? "" : tempArr[6],
                1 >= tempArr.length ? -1 : Integer.parseInt(tempArr[1]),
                2 >= tempArr.length ? -1 : Integer.parseInt(tempArr[2]),
                3 >= tempArr.length ? null : csvValToFloorType(tempArr[3]),
                4 >= tempArr.length ? null : BuildingType.valueOf(tempArr[4]),
                5 >= tempArr.length ? null : LocationType.valueOf(tempArr[5]),
                7 >= tempArr.length ? "" : tempArr[7]);

        insert(tempLocation);
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
          new StringBuffer("nodeID,xcoord,ycoord,floor,building,nodeType,longName,shortName");
      // Add buffer string to buffered writer
      bw.write(title.toString());
      // Drops down to next row
      bw.newLine();

      LinkedList<Location> locationList = getAll();

      // Go through each Location in the list
      for (Location location : locationList) {
        String xcoord = String.valueOf(location.getX());
        String ycoord = String.valueOf(location.getY());
        String locationType = location.getType().toString();
        String buildingType = location.getBuilding().toString();
        String floorType = location.getFloor().toString();

        // Create a single temporary string buffer
        StringBuffer oneLine = new StringBuffer();
        // Add nodeID to buffer
        oneLine.append(location.getId().trim().length() == 0 ? "" : location.getId());
        // Add comma separator
        oneLine.append(csvSeparator);
        // Add xcoord to buffer
        oneLine.append(xcoord == null || (xcoord.trim().length() == 0) ? "" : locationType);
        // Add comma separator
        oneLine.append(csvSeparator);
        // Add ycoord to buffer
        oneLine.append(ycoord == null || (ycoord.trim().length() == 0) ? "" : ycoord);
        // Add comma separator
        oneLine.append(csvSeparator);
        // Add floor to buffer
        oneLine.append(floorType == null || (floorType.trim().length() == 0) ? "" : floorType);
        // Add comma separator
        oneLine.append(csvSeparator);
        // Add building to buffer
        oneLine.append(
            buildingType == null || (buildingType.trim().length() == 0) ? "" : buildingType);
        // Add comma separator
        oneLine.append(csvSeparator);
        // Add nodeType to buffer
        oneLine.append(
            locationType == null || (locationType.trim().length() == 0) ? "" : locationType);
        // Add comma separator
        oneLine.append(csvSeparator);
        // Add longName to buffer
        oneLine.append(
            location.getLongName() == null || (location.getLongName().trim().length() == 0)
                ? ""
                : location.getLongName());
        // Add comma separator
        oneLine.append(csvSeparator);
        // Add shortName to buffer
        oneLine.append(
            location.getShortName() == null || (location.getShortName().trim().length() == 0)
                ? ""
                : location.getShortName());

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

  private FloorType csvValToFloorType(String csvVal) {
    switch (csvVal) {
      case "1":
        return FloorType.FirstFloor;
      case "2":
        return FloorType.SecondFloor;
      case "3":
        return FloorType.ThirdFloor;
      case "L1":
        return FloorType.LowerLevel1;
      case "L2":
        return FloorType.LowerLevel2;

      default:
        return null;
    }
  }
}

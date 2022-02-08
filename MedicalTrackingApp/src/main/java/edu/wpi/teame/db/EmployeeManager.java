package edu.wpi.teame.db;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class EmployeeManager implements IManager<Employee> {
  private static Connection connection;
  private static Statement stmt;

  public EmployeeManager() {
    connection = DBManager.getInstance().getConnection();

    try {
      stmt = connection.createStatement();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Employee get(String id) {
    Employee result = null;
    String getQuery = "SELECT * FROM Employees WHERE id='" + id + "'";
    try {
      ResultSet rset = stmt.executeQuery(getQuery);
      while (rset.next()) {
        String employeeID = rset.getString("employeeID");
        DepartmentType dept = DepartmentType.values()[rset.getInt("dept")];
        String name = rset.getString("name");
        boolean isDoctor = rset.getBoolean("isDoctor");

        // convert strings to proper type
        result = new Employee(employeeID, dept, name, isDoctor);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Could not select from Employees");
    }
    return result;
  }

  @Override
  public LinkedList<Employee> getAll() {
    LinkedList<Employee> result = new LinkedList<Employee>();
    String getQuery = "SELECT * FROM Employees";
    try {
      ResultSet rset = stmt.executeQuery(getQuery);
      while (rset.next()) {
        String employeeID = rset.getString("employeeID");
        DepartmentType dept = DepartmentType.values()[rset.getInt("dept")];
        String name = rset.getString("name");
        boolean isDoctor = rset.getBoolean("isDoctor");

        // convert strings to proper type
        result.add(new Employee(employeeID, dept, name, isDoctor));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Could not select all tuples from Employees");
    }
    return result;
  }

  @Override
  public void insert(Employee newObject) {
    String insertQuery =
        "INSERT INTO Employees VALUES('"
            + newObject.getEmployeeID()
            + "',"
            + newObject.getDept().ordinal()
            + ",'"
            + newObject.getName()
            + "',"
            + newObject.isDoctor()
            + ")";
    try {
      stmt.executeUpdate(insertQuery);
    } catch (SQLException e) {
      System.out.println("Could not insert into Employees");
      e.printStackTrace();
    }
  }

  @Override
  public void remove(String id) {
    String removeQuery = "DELETE FROM Employees WHERE id='" + id + "'";
    try {
      stmt.executeUpdate(removeQuery);
    } catch (SQLException e) {
      System.out.println("Could not delete from Employees");
      e.printStackTrace();
    }
  }

  @Override
  public void update(Employee updatedObject) {
    String updateQuery =
        "UPDATE LOCATIONS SET employeeID = "
            + updatedObject.getEmployeeID()
            + ", dept = "
            + updatedObject.getDept()
            + ", name = "
            + updatedObject.getName()
            + ", isDoctor = "
            + updatedObject.isDoctor()
            + ")";
    try {
      stmt.executeUpdate(updateQuery);
      System.out.println("Employee updated successfully");
    } catch (SQLException e) {
      System.out.println("Could not update Employees table");
      e.printStackTrace();
    }
  }

  @Override
  public void readCSV(String csvFile) throws IOException {

    try {
      String path = System.getProperty("user.dir") + "/src/main/resources/edu/wpi/teame/" + csvFile;
      File file = new File(path);
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);
      String line = " ";
      String[] tempArr;

      boolean firstLine = true;
      String delimiter = ",";
      while ((line = br.readLine()) != null) {
        if (!firstLine) {
          tempArr = line.split(delimiter);
          String tempID = tempArr[0];
          DepartmentType tempDept = csvValToDepartmentType(tempArr[1]);
          String tempName = tempArr[2];
          boolean tempIsDoctor = Boolean.parseBoolean(tempArr[3]);

          Employee tempEmployee =
              new Employee(
                  tempID,
                  1 >= tempArr.length ? null : tempDept,
                  2 >= tempArr.length ? "" : tempName,
                  3 >= tempArr.length ? false : tempIsDoctor);

          insert(tempEmployee);
        } else {
          firstLine = false;
        }
      }
      br.close();
    } catch (IOException e) {
      System.out.println("Could not read Employees.csv.");
      e.printStackTrace();
    }
  }

  private DepartmentType csvValToDepartmentType(String csvVal) {
    switch (csvVal) {
      case "Family Medicine":
        return DepartmentType.FAMILYMEDICINE;
      case "Clinical Services":
        return DepartmentType.CLINICALSERVICES;
      case "Plastic Surgery":
        return DepartmentType.PLASTICSURGERY;
      case "Neurosurgery":
        return DepartmentType.NEUROSURGERY;
      case "Medical Intensive Care Unit":
        return DepartmentType.MICU;

      default:
        return null;
    }
  }

  @Override
  public void writeToCSV(String outputFilePath) {

    // create CSV or find CSV to write to
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
        System.out.println("Could not create this CSV file: " + outputFilePath);
        e.printStackTrace();
      }

      BufferedWriter bw =
          new BufferedWriter(
              new OutputStreamWriter(new FileOutputStream(outputFilePath + ".csv"), "UTF-8"));

      // Create a title for the CSV as the list of Locations doesn't have one
      // Put the title at the start of the locations list
      StringBuffer title = new StringBuffer("employeeID,dept,name,isDoctor");
      // Add buffer string to buffered writer
      bw.write(title.toString());
      // Drops down to next row
      bw.newLine();

      LinkedList<Employee> employeesList = getAll();

      // Go through each Location in the list
      for (Employee employee : employeesList) {
        String employeeID = employee.getEmployeeID();
        String dept = employee.getDept().toString();
        String name = employee.getName();
        String isDoctor = String.valueOf(employee.isDoctor());

        // Create a single temporary string buffer
        StringBuffer oneLine = new StringBuffer();
        // Add employeeID to buffer
        oneLine.append(employeeID.trim().length() == 0 ? "" : employeeID);
        // Add comma separator
        oneLine.append(csvSeparator);
        // Add dept to buffer
        oneLine.append(dept == null || (dept.trim().length() == 0) ? "" : dept);
        // Add comma separator
        oneLine.append(csvSeparator);
        // Add name to buffer
        oneLine.append(name == null || (name.trim().length() == 0) ? "" : name);
        // Add comma separator
        oneLine.append(csvSeparator);
        // Add isDoctor to buffer
        oneLine.append(isDoctor == null || (isDoctor.trim().length() == 0) ? "" : isDoctor);

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

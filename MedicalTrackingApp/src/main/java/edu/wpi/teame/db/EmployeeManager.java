package edu.wpi.teame.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
    LinkedList<Employee> result = null;
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
            + newObject.getDept()
            + ","
            + newObject.getName()
            + ","
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
    csvFile =
        System.getProperty("user.dir")
            + "\\src\\main\\resources\\edu\\wpi\\teame\\csv\\EmployeesE.csv";

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
        /*
        Employee tempEmployee =
                new Employee(
                        tempArr[0],
                        6 >= tempArr.length ? "" : tempArr[6],
                        1 >= tempArr.length ? -1 : Integer.parseInt(tempArr[1]), //if there is only one node in tempArray, there will not be a value for this field. don't input
                        2 >= tempArr.length ? -1 : Integer.parseInt(tempArr[2]),
                        3 >= tempArr.length ? null : csvValToFloorType(tempArr[3]),
                        4 >= tempArr.length ? null : BuildingType.valueOf(tempArr[4]),
                        5 >= tempArr.length ? null : LocationType.valueOf(tempArr[5]),
                        7 >= tempArr.length ? "" : tempArr[7]);
         */
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
  public void writeToCSV(String outputFilePath) {}
}

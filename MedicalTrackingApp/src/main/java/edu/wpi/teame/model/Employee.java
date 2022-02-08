package edu.wpi.teame.model;

import edu.wpi.teame.db.ISQLSerializable;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.DepartmentType;

public class Employee implements ISQLSerializable {
  private DepartmentType dept;
  private String employeeID;
  private boolean isDoctor;
  private String name;

  public Employee(String id, DepartmentType dept, String name, boolean isDoctor) {
    employeeID = id;
    this.dept = dept;
    this.name = name;
    this.isDoctor = isDoctor;
  }

  public String toString() {
    StringBuilder employeeString = new StringBuilder();
    employeeString.append("employeeID: ").append(this.employeeID).append(" ");
    employeeString.append("department: ").append(this.dept).append(" ");
    employeeString.append("name: ").append(this.name).append(" ");
    employeeString.append("isDoctor: ").append(this.isDoctor);

    return employeeString.toString();
  }

  // GETTERS & SETTERS
  public String getEmployeeID() {
    return employeeID;
  }

  public void setEmployeeID(String employeeID) {
    this.employeeID = employeeID;
  }

  public DepartmentType getDept() {
    return dept;
  }

  public void setDept(DepartmentType dept) {
    this.dept = dept;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isDoctor() {
    return isDoctor;
  }

  public void setDoctor(boolean doctor) {
    isDoctor = doctor;
  }

  @Override
  public DataBaseObjectType getDBType() {
    return DataBaseObjectType.Employee;
  }

  @Override
  public String toSQLInsertString() {
    return null;
  }

  @Override
  public String toSQLUpdateString() {
    return null;
  }
}

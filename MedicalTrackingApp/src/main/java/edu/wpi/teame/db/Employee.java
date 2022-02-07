package edu.wpi.teame.db;

public class Employee {
  private String employeeID;
  private DepartmentType dept;
  private String name;
  private boolean isDoctor;

  public Employee(String id, DepartmentType dept, String name, boolean isDoctor) {
    employeeID = id;
    this.dept = dept;
    this.name = name;
    this.isDoctor = isDoctor;
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
}

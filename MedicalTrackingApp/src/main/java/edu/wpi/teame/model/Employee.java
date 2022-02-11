package edu.wpi.teame.model;

import edu.wpi.teame.db.ISQLSerializable;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.DepartmentType;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Employee implements ISQLSerializable {
  private DepartmentType department;
  private boolean isDoctor;
  private String name;
  private int id;

  public Employee(int id, DepartmentType dept, String name, boolean isDoctor) {
    this.isDoctor = isDoctor;
    this.department = dept;
    this.name = name;
    this.id = id;
  }

  public Employee(ResultSet resultSet) throws SQLException {
    this.isDoctor = resultSet.getBoolean("isDoctor");
    this.department = DepartmentType.values()[resultSet.getInt("department")];
    this.name = resultSet.getString("name");
    this.id = resultSet.getInt("id");
  }

  public String toString() {
    StringBuilder employeeString = new StringBuilder();
    employeeString.append("id: ").append(this.id).append(" ");
    employeeString.append("department: ").append(this.department).append(" ");
    employeeString.append("name: ").append(this.name).append(" ");
    employeeString.append("isDoctor: ").append(this.isDoctor);

    return employeeString.toString();
  }

  // GETTERS & SETTERS
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public DepartmentType getDepartment() {
    return department;
  }

  public void setDepartment(DepartmentType department) {
    this.department = department;
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
  public String getSQLInsertString() {
    int isDoctorInt = isDoctor ? 1 : 0;
    return department.ordinal() + ", '" + name + "', " + isDoctorInt;
  }

  @Override
  public String getSQLUpdateString() {
    int isDoctorInt = isDoctor ? 1 : 0;
    return "department = "
        + department.ordinal()
        + ", name = '"
        + name
        + "', isDoctor = "
        + isDoctorInt
        + " WHERE id = "
        + id;
  }

  /**
   * + "department int, " + "name VARCHAR(100), " + "isDoctor int)
   *
   * @return
   */
  @Override
  public String getTableColumns() {
    return "(department, name, isDoctor)";
  }
}

package edu.wpi.teame.model;

import edu.wpi.teame.db.ISQLSerializable;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.DepartmentType;
import edu.wpi.teame.model.enums.EmployeeType;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Employee implements ISQLSerializable {
  private DepartmentType department;
  private EmployeeType type;
  private String name;
  private int id;
  private boolean isDeleted;

  public Employee(int id, DepartmentType dept, String name, EmployeeType type) {
    this.department = dept;
    this.type = type;
    this.name = name;
    this.id = id;
    this.isDeleted = false;
  }

  public Employee(ResultSet resultSet) throws SQLException {
    this.type = EmployeeType.values()[resultSet.getInt("type")];
    this.department = DepartmentType.values()[resultSet.getInt("department")];
    this.name = resultSet.getString("name");
    this.id = resultSet.getInt("id");
    this.isDeleted = resultSet.getBoolean("isDeleted");
  }

  public String toString() {
    StringBuilder employeeString = new StringBuilder();
    employeeString.append("id: ").append(this.id).append(" ");
    employeeString.append("department: ").append(this.department).append(" ");
    employeeString.append("name: ").append(this.name).append(" ");
    employeeString.append("type: ").append(this.type);
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

  public EmployeeType getType() {
    return type;
  }

  public void setType(EmployeeType type) {
    this.type = type;
  }

  public boolean isDeleted() {
    return isDeleted;
  }

  public void setDeleted(boolean deleted) {
    isDeleted = deleted;
  }

  @Override
  public DataBaseObjectType getDBType() {
    return DataBaseObjectType.Employee;
  }

  @Override
  public String getSQLInsertString() {
    return department.ordinal() + ", '" + name + "', " + type.ordinal();
  }

  @Override
  public String getSQLUpdateString() {
    return "department = "
        + department.ordinal()
        + ", name = '"
        + name
        + "', type = "
        + type.ordinal()
        + " WHERE id = "
        + id;
  }

  @Override
  public String getTableColumns() {
    return "(department, name, type)";
  }
}

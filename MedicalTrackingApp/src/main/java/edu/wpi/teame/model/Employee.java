package edu.wpi.teame.model;

import edu.wpi.teame.db.CSVLineData;
import edu.wpi.teame.db.ISQLSerializable;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.DepartmentType;
import edu.wpi.teame.model.enums.EmployeeType;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Employee implements ISQLSerializable {
  private DepartmentType department;
  private boolean isDeleted;
  private EmployeeType type;
  private String name;
  private int id;

  public Employee(int id, DepartmentType dept, String name, EmployeeType type) {
    this.department = dept;
    this.isDeleted = false;
    this.type = type;
    this.name = name;
    this.id = id;
  }

  public Employee(ResultSet resultSet) throws SQLException {
    this.type = EmployeeType.values()[resultSet.getInt("type")];
    this.department = DepartmentType.values()[resultSet.getInt("department")];
    this.isDeleted = resultSet.getBoolean("isDeleted");
    this.name = resultSet.getString("name");
    this.id = resultSet.getInt("id");
  }

  public Employee(CSVLineData lineData) throws SQLException {
    this.department = DepartmentType.values()[(lineData.getColumnInt("department"))];
    this.type = EmployeeType.values()[(lineData.getColumnInt("type"))];
    this.name = lineData.getColumnString("name");
  }

  // in order for a Codec registry to work properly, this constructor needs to exist
  // for now, it won't instantiate any variables
  public Employee() {}

  @Override
  public String toString() {
    return new StringBuilder()
        .append("id: ")
        .append(this.id)
        .append(" ")
        .append("department: ")
        .append(this.department)
        .append(" ")
        .append("name: ")
        .append(this.name)
        .append(" ")
        .append("type: ")
        .append(this.type)
        .toString();
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
  public String getSQLInsertString() {
    return department.ordinal() + ", '" + name + "', " + type.ordinal();
  }

  @Override
  public String[] toCSVData() {
    return new String[] {
      Integer.toString(id),
      Integer.toString(department.ordinal()),
      name,
      Integer.toString(type.ordinal())
    };
  }

  @Override
  public String[] getCSVHeaders() {
    return new String[] {"id", "department", "name", "type"};
  }

  @Override
  public String getTableColumns() {
    return "(department, name, type)";
  }

  @Override
  public DataBaseObjectType getDBType() {
    return DataBaseObjectType.Employee;
  }

  // GETTERS & SETTERS
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public boolean getIsDeleted() {
    return isDeleted;
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
}

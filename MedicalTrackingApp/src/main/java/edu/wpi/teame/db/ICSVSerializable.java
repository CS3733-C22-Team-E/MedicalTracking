package edu.wpi.teame.db;

public interface ICSVSerializable {
  public String[] toCSVData();

  public String[] getCSVHeaders();
}

package edu.wpi.teame.db;

import java.util.HashMap;

public final class CSVManager {
  public HashMap<String, Integer> locationIDMap = null;
  private static CSVManager instance;

  public static synchronized CSVManager getInstance() {
    if (instance == null) {
      instance = new CSVManager();
    }
    return instance;
  }

  private CSVManager() {
    locationIDMap = new HashMap<String, Integer>();
  }
}

package edu.wpi.teame.db;

import edu.wpi.teame.model.Location;
import java.util.HashMap;

public final class CSVManager {
  public HashMap<String, Location> locationIDMap = null;
  private static CSVManager instance;

  public static synchronized CSVManager getInstance() {
    if (instance == null) {
      instance = new CSVManager();
    }
    return instance;
  }

  private CSVManager() {
    locationIDMap = new HashMap<String, Location>();
  }
}

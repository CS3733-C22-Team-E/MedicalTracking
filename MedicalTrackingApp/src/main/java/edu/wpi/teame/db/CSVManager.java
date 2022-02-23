package edu.wpi.teame.db;

import edu.wpi.teame.model.enums.DataBaseObjectType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CSVManager {
  private List<HashMap<Integer, Integer>> csvToDBIDs = null;
  private static CSVManager instance;

  public static synchronized CSVManager getInstance() {
    if (instance == null) {
      instance = new CSVManager();
    }
    return instance;
  }

  private CSVManager() {
    csvToDBIDs = new ArrayList<>();
    for (DataBaseObjectType ignored : DataBaseObjectType.values()) {
      csvToDBIDs.add(new HashMap<>());
    }
  }

  public int getDBId(DataBaseObjectType objectType, int csvID) {
    if (csvToDBIDs.size() == 0 || csvToDBIDs.get(objectType.ordinal()).size() == 0) {
      return csvID;
    }

    // Check if the csvID exists
    if (!csvToDBIDs.get(objectType.ordinal()).containsKey(csvID)) {
      return csvID;
    }

    return csvToDBIDs.get(objectType.ordinal()).get(csvID);
  }

  public void putCsvToDBId(DataBaseObjectType objectType, int csvID, int DBId) {
    csvToDBIDs.get(objectType.ordinal()).put(csvID, DBId);
  }
}

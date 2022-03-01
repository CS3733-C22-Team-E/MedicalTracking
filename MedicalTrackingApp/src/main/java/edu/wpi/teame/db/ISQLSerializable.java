package edu.wpi.teame.db;

import edu.wpi.teame.model.enums.DataBaseObjectType;
import java.util.List;
import org.bson.conversions.Bson;

public interface ISQLSerializable extends ICSVSerializable {
  public DataBaseObjectType getDBType();

  public String getSQLInsertString();

  public String getSQLUpdateString();

  public String getTableColumns();

  public boolean getIsDeleted();

  public int getId();

  public List<Bson> getMongoUpdates();
}

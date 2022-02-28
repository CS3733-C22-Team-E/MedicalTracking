package edu.wpi.teame.model.mongoCodecs;

import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Equipment;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.EquipmentType;
import java.sql.SQLException;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class EquipmentCodec implements Codec<Equipment> {
  @Override
  public Equipment decode(BsonReader reader, DecoderContext decoderContext) {
    // Creates empty Object and sets fields along the way
    Equipment equipment = new Equipment();

    // places cursor at the beginning of the BSON reader
    reader.readStartDocument();

    // Reader has the name of keys(columns)
    // Checks to see what the name is and sets the value in the object properly
    while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
      String fieldName = reader.readName();
      if (fieldName.equals("_id")) {
        equipment.setId(reader.readInt32());
      } else if (fieldName.equals("locationID")) {
        try {
          equipment.setLocation(
              (Location)
                  DBManager.getInstance()
                      .getManager(DataBaseObjectType.Location)
                      .get(reader.readInt32()));
        } catch (SQLException e) {
          e.printStackTrace();
          System.exit(2);
        }
      } else if (fieldName.equals("name")) {
        equipment.setName(reader.readString());
      } else if (fieldName.equals("type")) {
        equipment.setType(EquipmentType.values()[reader.readInt32()]);
      } else if (fieldName.equals("isClean")) {
        equipment.setClean(reader.readInt32() == 1);
      } else if (fieldName.equals("hasPatient")) {
        equipment.setHasPatient(reader.readInt32() == 1);
      } else if (fieldName.equals("isDeleted")) {
        equipment.setDeleted(reader.readInt32() == 1);
      }
    }

    // closes reader
    reader.readEndDocument();
    return equipment;
  }

  @Override
  public void encode(BsonWriter writer, Equipment value, EncoderContext encoderContext) {
    // Creates a document on the writer and sets each key value pair we're storing
    writer.writeStartDocument();
    writer.writeInt32("_id", value.getId());
    writer.writeInt32("locationID", value.getLocation().getId());
    writer.writeString("name", value.getName());
    writer.writeInt32("type", value.getType().ordinal());
    writer.writeInt32("isClean", value.isClean() ? 1 : 0);
    writer.writeInt32("hasPatient", value.isHasPatient() ? 1 : 0);
    writer.writeInt32("isDeleted", value.isDeleted() ? 1 : 0);
    writer.writeEndDocument();
  }

  @Override
  public Class<Equipment> getEncoderClass() {
    return Equipment.class;
  }
}

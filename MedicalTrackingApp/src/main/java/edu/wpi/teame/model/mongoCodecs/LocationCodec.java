package edu.wpi.teame.model.mongoCodecs;

import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.BuildingType;
import edu.wpi.teame.model.enums.FloorType;
import edu.wpi.teame.model.enums.LocationType;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class LocationCodec implements Codec<Location> {
  @Override
  public Location decode(BsonReader reader, DecoderContext decoderContext) {
    Location location = new Location();
    reader.readStartDocument();
    while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
      String fieldName = reader.readName();
      if (fieldName.equals("_id")) {
        location.setId(reader.readInt32());
      } else if (fieldName.equals("locationType")) {
        location.setType(LocationType.values()[reader.readInt32()]);
      } else if (fieldName.equals("shortName")) {
        location.setShortName(reader.readString());
      } else if (fieldName.equals("longName")) {
        location.setLongName(reader.readString());
      } else if (fieldName.equals("building")) {
        location.setBuilding(BuildingType.values()[reader.readInt32()]);
      } else if (fieldName.equals("floor")) {
        location.setFloor(FloorType.values()[reader.readInt32()]);
      } else if (fieldName.equals("x")) {
        location.setX(reader.readInt32());
      } else if (fieldName.equals("y")) {
        location.setY(reader.readInt32());
      } else if (fieldName.equals("isDeleted")) {
        location.setDeleted(reader.readInt32() == 1);
      }
    }
    reader.readEndDocument();
    return location;
  }

  @Override
  public void encode(BsonWriter writer, Location value, EncoderContext encoderContext) {
    writer.writeStartDocument();
    writer.writeInt32("_id", value.getId());
    writer.writeInt32("locationType", value.getType().ordinal());
    writer.writeString("shortName", value.getShortName());
    writer.writeString("longName", value.getLongName());
    writer.writeInt32("building", value.getBuilding().ordinal());
    writer.writeInt32("floor", value.getFloor().ordinal());
    writer.writeInt32("x", value.getX());
    writer.writeInt32("y", value.getY());
    writer.writeInt32("isDeleted", value.getIsDeleted() ? 1 : 0);
    writer.writeEndDocument();
  }

  @Override
  public Class<Location> getEncoderClass() {
    return Location.class;
  }
}

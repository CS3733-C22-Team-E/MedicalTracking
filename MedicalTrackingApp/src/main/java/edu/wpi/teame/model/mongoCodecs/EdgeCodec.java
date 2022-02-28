package edu.wpi.teame.model.mongoCodecs;

import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Edge;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import java.sql.SQLException;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class EdgeCodec implements Codec<Edge> {
  @Override
  public Edge decode(BsonReader reader, DecoderContext decoderContext) {
    //Creates empty Object and sets fields along the way
    Edge edge = new Edge();

    //places cursor at the beginning of the BSON reader
    reader.readStartDocument();

    //Reader has the name of keys(columns)
    //Checks to see what the name is and sets the value in the object properly
    while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
      String fieldName = reader.readName();
      if (fieldName.equals("_id")) {
        edge.setId(reader.readInt32());
      } else if (fieldName.equals("startID")) {
        try {
          edge.setStart(
              (Location)
                  DBManager.getInstance()
                      .getManager(DataBaseObjectType.Location)
                      .get(reader.readInt32()));
        } catch (SQLException e) {
          e.printStackTrace();
          System.exit(2);
        }
      } else if (fieldName.equals("endID")) {
        try {
          edge.setEnd(
              (Location)
                  DBManager.getInstance()
                      .getManager(DataBaseObjectType.Location)
                      .get(reader.readInt32()));
        } catch (SQLException e) {
          e.printStackTrace();
          System.exit(2);
        }
      } else if (fieldName.equals("isDeleted")) {
        edge.setDeleted(reader.readInt32() == 1);
      }
    }

    //closes reader
    reader.readEndDocument();

    return edge;
  }

  @Override
  public void encode(BsonWriter writer, Edge value, EncoderContext encoderContext) {
    //Creates a document on the writer and sets each key value pair we're storing
    writer.writeStartDocument();
    writer.writeInt32("_id", value.getId());
    writer.writeInt32("startID", value.getStart().getId());
    writer.writeInt32("endID", value.getEnd().getId());
    writer.writeInt32("isDeleted", value.getIsDeleted() ? 1 : 0);
    writer.writeEndDocument();
  }

  @Override
  public Class<Edge> getEncoderClass() {
    return Edge.class;
  }
}

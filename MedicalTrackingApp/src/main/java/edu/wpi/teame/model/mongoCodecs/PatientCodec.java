package edu.wpi.teame.model.mongoCodecs;

import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.Patient;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class PatientCodec implements Codec<Patient> {
  @Override
  public Patient decode(BsonReader reader, DecoderContext decoderContext) {
    Patient patient = new Patient();
    reader.readStartDocument();

    while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
      String fieldName = reader.readName();
      if (fieldName.equals("_id")) {
        patient.setId(reader.readInt32());
      } else if (fieldName.equals("name")) {
        patient.setName(reader.readString());
      } else if (fieldName.equals("dateOfBirth")) {
        String DOB = reader.readString();
        SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date;
        try {
          date = sfd.parse(DOB);
        } catch (ParseException e) {
          e.printStackTrace();
          date = new Date();
        }
        patient.setDateOfBirth(new java.sql.Date(date.getTime()));
      } else if (fieldName.equals("currentLocationID")) {
        try {
          patient.setCurrentLocation(
              (Location)
                  DBManager.getInstance()
                      .getManager(DataBaseObjectType.Location)
                      .get(reader.readInt32()));
        } catch (SQLException e) {
          e.printStackTrace();
          System.exit(2);
        }
      } else if (fieldName.equals("isDeleted")) {
        patient.setDeleted(reader.readInt32() == 1);
      }
    }
    reader.readEndDocument();
    return patient;
  }

  @Override
  public void encode(BsonWriter writer, Patient value, EncoderContext encoderContext) {
    writer.writeStartDocument();
    writer.writeInt32("_id", value.getId());
    writer.writeString("name", value.getName());
    writer.writeString("dateOfBirth", value.getDateOfBirth().toString());
    writer.writeInt32("isDeleted", value.getIsDeleted() ? 1 : 0);
    writer.writeEndDocument();
  }

  @Override
  public Class<Patient> getEncoderClass() {
    return Patient.class;
  }
}

package edu.wpi.teame.model.mongoCodecs;

import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Equipment;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.ServiceRequestPriority;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import edu.wpi.teame.model.serviceRequests.MedicalEquipmentServiceRequest;
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

public class MedicalEquipmentServiceRequestCodec implements Codec<MedicalEquipmentServiceRequest> {
  @Override
  public MedicalEquipmentServiceRequest decode(BsonReader reader, DecoderContext decoderContext) {
    MedicalEquipmentServiceRequest serviceRequest = new MedicalEquipmentServiceRequest();
    reader.readStartDocument();

    SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd");

    while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
      String fieldName = reader.readName();
      if (fieldName.equals("_id")) {
        serviceRequest.setId(reader.readInt32());
      } else if (fieldName.equals("locationID")) {
        try {
          serviceRequest.setLocation(
              (Location)
                  DBManager.getInstance()
                      .getManager(DataBaseObjectType.Location)
                      .get(reader.readInt32()));
        } catch (SQLException e) {
          e.printStackTrace();
          System.exit(2);
        }
      } else if (fieldName.equals("assigneeID")) {
        try {
          serviceRequest.setAssignee(
              (Employee)
                  DBManager.getInstance()
                      .getManager(DataBaseObjectType.Employee)
                      .get(reader.readInt32()));
        } catch (SQLException e) {
          e.printStackTrace();
          System.exit(2);
        }
      } else if (fieldName.equals("openDate")) {
        String openDate = reader.readString();
        java.util.Date date;
        try {
          date = sfd.parse(openDate);
        } catch (ParseException e) {
          e.printStackTrace();
          date = new Date();
        }

        serviceRequest.setOpenDate(new java.sql.Date(date.getTime()));
      } else if (fieldName.equals("closeDate")) {
        String closeDate = reader.readString();
        java.util.Date date;
        try {
          date = sfd.parse(closeDate);
        } catch (ParseException e) {
          e.printStackTrace();
          date = new Date();
        }

        serviceRequest.setCloseDate(new java.sql.Date(date.getTime()));
      } else if (fieldName.equals("status")) {
        serviceRequest.setStatus(ServiceRequestStatus.values()[reader.readInt32()]);
      } else if (fieldName.equals("title")) {
        serviceRequest.setTitle(reader.readString());
      } else if (fieldName.equals("additionalInfo")) {
        serviceRequest.setAdditionalInfo(reader.readString());
      } else if (fieldName.equals("priority")) {
        serviceRequest.setPriority(ServiceRequestPriority.values()[reader.readInt32()]);
      } else if (fieldName.equals("requestDate")) {
        String requestDate = reader.readString();
        java.util.Date date;
        try {
          date = sfd.parse(requestDate);
        } catch (ParseException e) {
          e.printStackTrace();
          date = new Date();
        }
        serviceRequest.setRequestDate(new java.sql.Date(date.getTime()));
      } else if (fieldName.equals("isDeleted")) {
        serviceRequest.setDeleted(reader.readInt32() == 1);
      } else if (fieldName.equals("equipmentID")) {
        try {
          serviceRequest.setEquipment(
              (Equipment)
                  DBManager.getInstance()
                      .getManager(DataBaseObjectType.Equipment)
                      .get(reader.readInt32()));
        } catch (SQLException e) {
          e.printStackTrace();
          System.exit(2);
        }
      }
    }
    reader.readEndDocument();
    return serviceRequest;
  }

  @Override
  public void encode(
      BsonWriter writer, MedicalEquipmentServiceRequest value, EncoderContext encoderContext) {
    writer.writeStartDocument();
    writer.writeInt32("_id", value.getId());
    writer.writeInt32("locationID", value.getLocation().getId());
    writer.writeInt32("assigneeID", value.getAssignee().getId());
    writer.writeString("openDate", value.getOpenDate().toString());
    writer.writeString("closeDate", value.getCloseDate().toString());
    writer.writeInt32("status", value.getStatus().ordinal());
    writer.writeString("title", value.getTitle());
    writer.writeString("additionalInfo", value.getAdditionalInfo());
    writer.writeInt32("priority", value.getPriority().ordinal());
    writer.writeString("requestDate", value.getRequestDate().toString());
    writer.writeInt32("equipmentID", value.getEquipment().getId());
    writer.writeInt32("isDeleted", value.getIsDeleted() ? 1 : 0);
    writer.writeEndDocument();
  }

  @Override
  public Class<MedicalEquipmentServiceRequest> getEncoderClass() {
    return MedicalEquipmentServiceRequest.class;
  }
}
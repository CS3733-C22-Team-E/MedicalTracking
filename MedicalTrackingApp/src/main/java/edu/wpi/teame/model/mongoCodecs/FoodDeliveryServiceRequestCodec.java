package edu.wpi.teame.model.mongoCodecs;

import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.Patient;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.ServiceRequestPriority;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import edu.wpi.teame.model.serviceRequests.FoodDeliveryServiceRequest;
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

public class FoodDeliveryServiceRequestCodec implements Codec<FoodDeliveryServiceRequest> {
  @Override
  public FoodDeliveryServiceRequest decode(BsonReader reader, DecoderContext decoderContext) {
    // Creates empty Object and sets fields along the way
    FoodDeliveryServiceRequest serviceRequest = new FoodDeliveryServiceRequest();

    // places cursor at the beginning of the BSON reader
    reader.readStartDocument();
    SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd");

    // Reader has the name of keys(columns)
    // Checks to see what the name is and sets the value in the object properly
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
          SimpleDateFormat sfdNew = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          try {
            date = sfdNew.parse(openDate);
          } catch (ParseException ex) {
            ex.printStackTrace();
            date = new Date();
          }
        }

        serviceRequest.setOpenDate(new java.sql.Date(date.getTime()));
      } else if (fieldName.equals("closeDate")) {
        String closeDate = reader.readString();
        java.util.Date date;
        try {
          date = sfd.parse(closeDate);
        } catch (ParseException e) {
          e.printStackTrace();
          SimpleDateFormat sfdNew = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          try {
            date = sfdNew.parse(closeDate);
          } catch (ParseException ex) {
            ex.printStackTrace();
            date = new Date();
          }
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
          SimpleDateFormat sfdNew = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          try {
            date = sfdNew.parse(requestDate);
          } catch (ParseException ex) {
            ex.printStackTrace();
            date = new Date();
          }
        }
        serviceRequest.setRequestDate(new java.sql.Date(date.getTime()));
      } else if (fieldName.equals("isDeleted")) {
        serviceRequest.setDeleted(reader.readInt32() == 1);
      } else if (fieldName.equals("patientID")) {
        try {
          serviceRequest.setPatient(
              (Patient)
                  DBManager.getInstance()
                      .getManager(DataBaseObjectType.Patient)
                      .get(reader.readInt32()));
        } catch (SQLException e) {
          e.printStackTrace();
          System.exit(2);
        }
      } else if (fieldName.equals("food")) {
        serviceRequest.setFood(reader.readString());
      }
    }

    // closes reader
    reader.readEndDocument();
    return serviceRequest;
  }

  @Override
  public void encode(
      BsonWriter writer, FoodDeliveryServiceRequest value, EncoderContext encoderContext) {
    // Creates a document on the writer and sets each key value pair we're storing
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
    writer.writeInt32("patientID", value.getPatient().getId());
    writer.writeString("food", value.getFood());
    writer.writeInt32("isDeleted", value.getIsDeleted() ? 1 : 0);
    writer.writeEndDocument();
  }

  @Override
  public Class<FoodDeliveryServiceRequest> getEncoderClass() {
    return FoodDeliveryServiceRequest.class;
  }
}

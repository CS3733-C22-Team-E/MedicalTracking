package edu.wpi.teame;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.mongodb.ConnectionString;
import com.mongodb.client.*;
import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.*;
import edu.wpi.teame.model.enums.*;
import edu.wpi.teame.model.mongoCodecs.*;
import edu.wpi.teame.model.serviceRequests.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

public class Main {
  public static void main(String[] args)
      throws IOException, SQLException, CsvValidationException, ParseException,
          NoSuchAlgorithmException {

    // Setup the DB
    DBManager.getInstance().setupDB();
    DBManager.getInstance().loadDB();

    CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(false).build();
    Codec<Credential> credentialCodec = new CredentialCodec();
    Codec<Edge> edgeCodec = new EdgeCodec();
    Codec<Employee> employeeCodec = new EmployeeCodec();
    Codec<Equipment> equipmentCodec = new EquipmentCodec();
    Codec<PatientTransportationServiceRequest> externalPatientTransportSRCodec =
        new ExternalPatientTransportationServiceRequestCodec();
    Codec<PatientTransportationServiceRequest> internalPatientTranspotSRCodec =
        new InternalPatientTransportationServiceRequestCodec();
    Codec<FoodDeliveryServiceRequest> foodDeliveryServiceRequestCodec =
        new FoodDeliveryServiceRequestCodec();
    Codec<GiftAndFloralServiceRequest> giftAndFloralServiceRequestCodec =
        new GiftAndFloralServiceRequestCodec();
    Codec<LanguageInterpreterServiceRequest> languageInterpreterServiceRequestCodec =
        new LanguageInterpreterServiceRequestCodec();
    Codec<Location> locationCodec = new LocationCodec();
    Codec<MedicalEquipmentServiceRequest> medicalEquipmentServiceRequestCodec =
        new MedicalEquipmentServiceRequestCodec();
    Codec<MedicineDeliveryServiceRequest> medicineDeliveryServiceRequestCodec =
        new MedicineDeliveryServiceRequestCodec();
    Codec<Patient> patientCodec = new PatientCodec();
    Codec<ReligiousServiceRequest> religiousServiceRequestCodec =
        new ReligiousServiceRequestCodec();
    Codec<DeceasedBodyRemovalServiceRequest> deceasedBodyRemovalServiceRequestCodec =
        new DeceasedBodyRemovalServiceRequestCodec();
    Codec<ServiceRequest> serviceRequestCodec = new ServiceRequestCodec();

    CodecRegistry customCodecs =
        CodecRegistries.fromCodecs(
            credentialCodec,
            edgeCodec,
            employeeCodec,
            equipmentCodec,
            foodDeliveryServiceRequestCodec,
            giftAndFloralServiceRequestCodec,
            languageInterpreterServiceRequestCodec,
            locationCodec,
            medicalEquipmentServiceRequestCodec,
            medicineDeliveryServiceRequestCodec,
            patientCodec,
            religiousServiceRequestCodec,
            deceasedBodyRemovalServiceRequestCodec,
            serviceRequestCodec);

    CodecRegistry pojoCodecRegistry =
        fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider), customCodecs);

    ConnectionString connectionString =
        new ConnectionString(
            "mongodb+srv://admin:admin@cluster0.yleso.mongodb.net/TeamEMongo?retryWrites=true&w=majority&tls=true&ssl=true");

    String connectString =
        "mongodb+srv://admin:admin@cluster0.45z0f.mongodb.net/TeamEMongos?retryWrites=true&w=majority";

    Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);

    try (MongoClient mongoClient = MongoClients.create(connectString)) {
      MongoDatabase database = mongoClient.getDatabase("TeamEMongos");
      MongoCollection<ServiceRequest> audioVisualSRCollection =
          mongoClient
              .getDatabase("TeamEMongos")
              .getCollection("AudioVisualSR", ServiceRequest.class)
              .withCodecRegistry(pojoCodecRegistry);
      System.out.println("Got audioVis collection");
      ServiceRequest audioVisualSR =
          new ServiceRequest(
              DataBaseObjectType.AudioVisualSR,
              ServiceRequestPriority.Critical,
              ServiceRequestStatus.OPEN,
              "My additional info",
              (Employee) DBManager.getInstance().getManager(DataBaseObjectType.Employee).get(12),
              (Location) DBManager.getInstance().getManager(DataBaseObjectType.Location).get(154),
              new java.sql.Date(0),
              new java.sql.Date(0),
              new java.sql.Date(new java.util.Date().getTime()),
              "",
              2);

      ServiceRequest results = audioVisualSRCollection.find(eq("_id", 1)).first();
      System.out.println(results);

      try {
        MongoCollection<ServiceRequest> audioVisualSRsCollection =
            database
                .getCollection("AudioVisualSR", ServiceRequest.class)
                .withCodecRegistry(pojoCodecRegistry);
        System.out.println("Got audioVis collection");
        ServiceRequest audioVisualSRs =
            new ServiceRequest(
                DataBaseObjectType.AudioVisualSR,
                ServiceRequestPriority.Critical,
                ServiceRequestStatus.OPEN,
                "My additional info",
                (Employee) DBManager.getInstance().getManager(DataBaseObjectType.Employee).get(12),
                (Location) DBManager.getInstance().getManager(DataBaseObjectType.Location).get(154),
                new java.sql.Date(0),
                new java.sql.Date(0),
                new java.sql.Date(new java.util.Date().getTime()),
                "",
                2);

        audioVisualSRCollection.insertOne(audioVisualSRs);

        ServiceRequest audVisResult = audioVisualSRsCollection.find(eq("_id", 1)).first();

        System.out.println("Audio Vis result");
        System.out.println(audVisResult);
      } catch (Exception e) {
        e.printStackTrace();
      }

      try {
        MongoCollection<ServiceRequest> computerSRCollection =
            database
                .getCollection("ComputerSR", ServiceRequest.class)
                .withCodecRegistry(pojoCodecRegistry);
        System.out.println("Got ComputerSR collection");
        ServiceRequest computerSR =
            new ServiceRequest(
                DataBaseObjectType.ComputerSR,
                ServiceRequestPriority.Critical,
                ServiceRequestStatus.OPEN,
                "My additional info",
                (Employee) DBManager.getInstance().getManager(DataBaseObjectType.Employee).get(12),
                (Location) DBManager.getInstance().getManager(DataBaseObjectType.Location).get(154),
                new java.sql.Date(0),
                new java.sql.Date(0),
                new java.sql.Date(new java.util.Date().getTime()),
                "Computersr",
                1);

        computerSRCollection.insertOne(computerSR);

        ServiceRequest computerSRResult = computerSRCollection.find(eq("_id", 1)).first();

        System.out.println("computerSR result");
        System.out.println(computerSRResult);
      } catch (Exception e) {
        e.printStackTrace();
      }

      try {
        MongoCollection<Credential> credentialMongoCollection =
            database
                .getCollection("Credential", Credential.class)
                .withCodecRegistry(pojoCodecRegistry);
        System.out.println("Got Credential collection");
        Credential credential = new Credential(2, "ca8a811e", "Jose", "admin", AccessLevel.Admin);

        credentialMongoCollection.insertOne(credential);

        Credential credentialResult = credentialMongoCollection.find(eq("_id", 2)).first();
        System.out.println("Credential result");
        System.out.println(credentialResult);
      } catch (Exception e) {
        e.printStackTrace();
      }

      try {
        MongoCollection<DeceasedBodyRemovalServiceRequest>
            deceasedBodyRemovalServiceRequestMongoCollection =
                database
                    .getCollection("DeceasedBodyRemovalSR", DeceasedBodyRemovalServiceRequest.class)
                    .withCodecRegistry(pojoCodecRegistry);
        System.out.println("Got DeceasedBodyRemovalSR collection");
        DeceasedBodyRemovalServiceRequest deceasedBodyRemovalServiceRequest =
            new DeceasedBodyRemovalServiceRequest(
                ServiceRequestPriority.Critical,
                ServiceRequestStatus.OPEN,
                "My additional info",
                (Employee) DBManager.getInstance().getManager(DataBaseObjectType.Employee).get(12),
                (Location) DBManager.getInstance().getManager(DataBaseObjectType.Location).get(154),
                new java.sql.Date(0),
                new java.sql.Date(0),
                new java.sql.Date(new java.util.Date().getTime()),
                "Me dead",
                1,
                (Patient) DBManager.getInstance().getManager(DataBaseObjectType.Patient).get(2));

        //
        deceasedBodyRemovalServiceRequestMongoCollection.insertOne(
            deceasedBodyRemovalServiceRequest);

        DeceasedBodyRemovalServiceRequest deceasedBodyRemovalServiceRequest1 =
            deceasedBodyRemovalServiceRequestMongoCollection.find(eq("_id", 1)).first();

        System.out.println("DeceasedBodyRemovalServiceRequest result");
        System.out.println(deceasedBodyRemovalServiceRequest1);
      } catch (Exception e) {
        e.printStackTrace();
      }

      try {
        MongoCollection<Edge> edgeMongoCollection =
            database.getCollection("Edge", Edge.class).withCodecRegistry(pojoCodecRegistry);
        System.out.println("Got Edge collection");
        Edge edge =
            new Edge(
                (Location) DBManager.getInstance().getManager(DataBaseObjectType.Location).get(154),
                (Location) DBManager.getInstance().getManager(DataBaseObjectType.Location).get(155),
                1);

        edgeMongoCollection.insertOne(edge);

        Edge edge1 = edgeMongoCollection.find(eq("_id", 1)).first();
        System.out.println("edge1 result");
        System.out.println(edge1);
      } catch (Exception e) {
        e.printStackTrace();
      }

      try {
        MongoCollection<Employee> employeeMongoCollection =
            database.getCollection("Employee", Employee.class).withCodecRegistry(pojoCodecRegistry);
        System.out.println("Got Employee collection");
        Employee employee =
            new Employee(1, DepartmentType.CLINICALSERVICES, "Jose Morales", EmployeeType.Admin);

        employeeMongoCollection.insertOne(employee);

        Employee employee1 = employeeMongoCollection.find(eq("_id", 1)).first();
        System.out.println("Employee1 result");
        System.out.println(employee1);
      } catch (Exception e) {
        e.printStackTrace();
      }

      try {
        MongoCollection<Equipment> equipmentMongoCollection =
            database
                .getCollection("Equipment", Equipment.class)
                .withCodecRegistry(pojoCodecRegistry);
        System.out.println("Got equipment collection");
        Equipment equipment =
            new Equipment(
                1,
                (Location) DBManager.getInstance().getManager(DataBaseObjectType.Location).get(154),
                EquipmentType.PBED,
                "My bed",
                false,
                false);

        equipmentMongoCollection.insertOne(equipment);

        Equipment equipment1 = equipmentMongoCollection.find(eq("_id", 1)).first();
        System.out.println("equipment1 result");
        System.out.println(equipment1);
      } catch (Exception e) {
        e.printStackTrace();
      }

      try {
        MongoCollection<PatientTransportationServiceRequest> equipmentMongoCollection =
            database
                .getCollection("ExternalPatientSR", PatientTransportationServiceRequest.class)
                .withCodecRegistry(
                    fromRegistries(CodecRegistries.fromCodecs(externalPatientTransportSRCodec)));
        System.out.println("Got ExternalPatientSR collection");
        PatientTransportationServiceRequest patientTransportationServiceRequest =
            new PatientTransportationServiceRequest(
                false,
                ServiceRequestPriority.High,
                ServiceRequestStatus.OPEN,
                "My additional info",
                (Employee) DBManager.getInstance().getManager(DataBaseObjectType.Employee).get(12),
                (Location) DBManager.getInstance().getManager(DataBaseObjectType.Location).get(154),
                new java.sql.Date(0),
                new java.sql.Date(0),
                new java.sql.Date(new java.util.Date().getTime()),
                "extPatient",
                1,
                (Location) DBManager.getInstance().getManager(DataBaseObjectType.Location).get(154),
                (Equipment)
                    DBManager.getInstance().getManager(DataBaseObjectType.Equipment).get(48),
                (Patient) DBManager.getInstance().getManager(DataBaseObjectType.Patient).get(2));

        equipmentMongoCollection.insertOne(patientTransportationServiceRequest);

        PatientTransportationServiceRequest patientTransportationServiceRequest1 =
            equipmentMongoCollection.find(eq("_id", 1)).first();
        System.out.println("ExternalPatientSR result");
        System.out.println(patientTransportationServiceRequest1);
      } catch (Exception e) {
        e.printStackTrace();
      }

      MongoCollection<FoodDeliveryServiceRequest> foodSerReq =
          database.getCollection("FoodDeliveryServiceRequest", FoodDeliveryServiceRequest.class);
      System.out.println("Got FoodDeliveryServiceRequest collection");

      FoodDeliveryServiceRequest food =
          new FoodDeliveryServiceRequest(
              ServiceRequestPriority.Critical,
              ServiceRequestStatus.OPEN,
              "My additional info",
              (Employee) DBManager.getInstance().getManager(DataBaseObjectType.Employee).get(12),
              (Location) DBManager.getInstance().getManager(DataBaseObjectType.Location).get(154),
              new Date(0),
              new Date(0),
              new Date(new java.util.Date().getTime()),
              "",
              2,
              (Patient) DBManager.getInstance().getManager(DataBaseObjectType.Patient).get(2),
              "Test shit");

      foodSerReq.insertOne(food);

      ResultSet rset =
          DBManager.getInstance()
              .getConnection()
              .createStatement()
              .executeQuery("Select * from Equipment");
      while (rset.next()) {
        System.out.println(rset.getInt("id"));
      }

      FoodDeliveryServiceRequest foodResult = foodSerReq.find(eq("_id", 0)).first();
      System.out.println(foodResult);
    }

    //    MongoClientSettings settings =
    //        MongoClientSettings.builder()
    //            .applyConnectionString(connectionString)
    //            .applyToSocketSettings(
    //                builder -> {
    //                  builder.connectTimeout(5000, MILLISECONDS);
    //                  builder.readTimeout(5000, MILLISECONDS);
    //                })
    //            .build();
    //    MongoClient mongoClient = MongoClients.create(settings);
    //    MongoDatabase database =
    //        mongoClient.getDatabase("TeamEMongo").withCodecRegistry(pojoCodecRegistry);
    //
    //        try {
    //          MongoCollection<ServiceRequest> audioVisualSRCollection =
    //              database
    //                  .getCollection("AudioVisualSR", ServiceRequest.class)
    //                  .withCodecRegistry(pojoCodecRegistry);
    //          System.out.println("Got audioVis collection");
    //          ServiceRequest audioVisualSR =
    //              new ServiceRequest(
    //                  DataBaseObjectType.AudioVisualSR,
    //                  ServiceRequestPriority.Critical,
    //                  ServiceRequestStatus.OPEN,
    //                  "My additional info",
    //                  (Employee)
    //     DBManager.getInstance().getManager(DataBaseObjectType.Employee).get(12),
    //                  (Location)
    //     DBManager.getInstance().getManager(DataBaseObjectType.Location).get(154),
    //                  new java.sql.Date(0),
    //                  new java.sql.Date(0),
    //                  new java.sql.Date(new java.util.Date().getTime()),
    //                  "",
    //                  2);
    //
    //          // audioVisualSRCollection.insertOne(audioVisualSR);
    //
    //          ServiceRequest audVisResult = null;
    //
    //          FindIterable result = audioVisualSRCollection.find(eq("_id", 1));
    //          result.forEach(
    //              o -> {
    //                System.out.println(o);
    //              });
    //          DeceasedBodyRemovalServiceRequest deceasedBodyRemovalServiceRequest1 = null;
    //          for (Object test : result) {
    //            audVisResult = (ServiceRequest) test;
    //            break;
    //          }
    //
    //          System.out.println("Audio Vis result");
    //          System.out.println(audVisResult);
    //        } catch (Exception e) {
    //          e.printStackTrace();
    //        }
    //
    //        try {
    //          MongoCollection<ServiceRequest> computerSRCollection =
    //              database
    //                  .getCollection("ComputerSR", ServiceRequest.class)
    //                  .withCodecRegistry(pojoCodecRegistry);
    //          System.out.println("Got ComputerSR collection");
    //          ServiceRequest computerSR =
    //              new ServiceRequest(
    //                  DataBaseObjectType.ComputerSR,
    //                  ServiceRequestPriority.Critical,
    //                  ServiceRequestStatus.OPEN,
    //                  "My additional info",
    //                  (Employee)
    //     DBManager.getInstance().getManager(DataBaseObjectType.Employee).get(12),
    //                  (Location)
    //     DBManager.getInstance().getManager(DataBaseObjectType.Location).get(154),
    //                  new java.sql.Date(0),
    //                  new java.sql.Date(0),
    //                  new java.sql.Date(new java.util.Date().getTime()),
    //                  "Computersr",
    //                  1);
    //
    //          // computerSRCollection.insertOne(computerSR);
    //
    //          ServiceRequest computerSRResult = null;
    //
    //          FindIterable result = computerSRCollection.find(eq("_id", 1));
    //          DeceasedBodyRemovalServiceRequest deceasedBodyRemovalServiceRequest1 = null;
    //          for (Object test : result) {
    //            computerSRResult = (ServiceRequest) test;
    //            break;
    //          }
    //
    //          System.out.println("computerSR result");
    //          System.out.println(computerSRResult);
    //        } catch (Exception e) {
    //          e.printStackTrace();
    //        }
    //
    //        try {
    //          MongoCollection<Credential> credentialMongoCollection =
    //              database
    //                  .getCollection("Credential", Credential.class)
    //                  .withCodecRegistry(pojoCodecRegistry);
    //          System.out.println("Got Credential collection");
    //          Credential credential = new Credential(2, "ca8a811e", "Jose", "admin",
    //     AccessLevel.Admin);
    //
    //          // credentialMongoCollection.insertOne(credential);
    //
    //          Credential credentialResult = credentialMongoCollection.find(eq("_id", 2)).first();
    //          System.out.println("Credential result");
    //          System.out.println(credentialResult);
    //        } catch (Exception e) {
    //          e.printStackTrace();
    //        }
    //
    //        try {
    //          MongoCollection<DeceasedBodyRemovalServiceRequest>
    //              deceasedBodyRemovalServiceRequestMongoCollection =
    //                  database
    //                      .getCollection("DeceasedBodyRemovalSR",
    //     DeceasedBodyRemovalServiceRequest.class)
    //                      .withCodecRegistry(pojoCodecRegistry);
    //          System.out.println("Got DeceasedBodyRemovalSR collection");
    //          DeceasedBodyRemovalServiceRequest deceasedBodyRemovalServiceRequest =
    //              new DeceasedBodyRemovalServiceRequest(
    //                  ServiceRequestPriority.Critical,
    //                  ServiceRequestStatus.OPEN,
    //                  "My additional info",
    //                  (Employee)
    //     DBManager.getInstance().getManager(DataBaseObjectType.Employee).get(12),
    //                  (Location)
    //     DBManager.getInstance().getManager(DataBaseObjectType.Location).get(154),
    //                  new java.sql.Date(0),
    //                  new java.sql.Date(0),
    //                  new java.sql.Date(new java.util.Date().getTime()),
    //                  "Me dead",
    //                  1,
    //                  (Patient)
    //     DBManager.getInstance().getManager(DataBaseObjectType.Patient).get(2));
    //
    //          //
    //
    // deceasedBodyRemovalServiceRequestMongoCollection.insertOne(deceasedBodyRemovalServiceRequest);
    //
    //          FindIterable result =
    // deceasedBodyRemovalServiceRequestMongoCollection.find(eq("_id",
    //     1));
    //          DeceasedBodyRemovalServiceRequest deceasedBodyRemovalServiceRequest1 = null;
    //          for (Object test : result) {
    //            deceasedBodyRemovalServiceRequest1 = (DeceasedBodyRemovalServiceRequest) test;
    //            break;
    //          }
    //
    //          System.out.println("DeceasedBodyRemovalServiceRequest result");
    //          System.out.println(deceasedBodyRemovalServiceRequest1);
    //        } catch (Exception e) {
    //          e.printStackTrace();
    //        }
    //
    //        try {
    //          MongoCollection<Edge> edgeMongoCollection =
    //              database.getCollection("Edge", Edge.class).withCodecRegistry(pojoCodecRegistry);
    //          System.out.println("Got Edge collection");
    //          Edge edge =
    //              new Edge(
    //                  (Location)
    //     DBManager.getInstance().getManager(DataBaseObjectType.Location).get(154),
    //                  (Location)
    //     DBManager.getInstance().getManager(DataBaseObjectType.Location).get(155),
    //                  1);
    //
    //          // edgeMongoCollection.insertOne(edge);
    //
    //          Edge edge1 = edgeMongoCollection.find(eq("_id", 1)).first();
    //          System.out.println("edge1 result");
    //          System.out.println(edge1);
    //        } catch (Exception e) {
    //          e.printStackTrace();
    //        }
    //
    //        try {
    //          MongoCollection<Employee> employeeMongoCollection =
    //              database.getCollection("Employee",
    //     Employee.class).withCodecRegistry(pojoCodecRegistry);
    //          System.out.println("Got Employee collection");
    //          Employee employee =
    //              new Employee(1, DepartmentType.CLINICALSERVICES, "Jose Morales",
    //     EmployeeType.Admin);
    //
    //          // employeeMongoCollection.insertOne(employee);
    //
    //          Employee employee1 = employeeMongoCollection.find(eq("_id", 1)).first();
    //          System.out.println("Employee1 result");
    //          System.out.println(employee1);
    //        } catch (Exception e) {
    //          e.printStackTrace();
    //        }
    //
    //        try {
    //          MongoCollection<Equipment> equipmentMongoCollection =
    //              database.getCollection("Equipment",
    //     Equipment.class).withCodecRegistry(pojoCodecRegistry);
    //          System.out.println("Got equipment collection");
    //          Equipment equipment =
    //              new Equipment(
    //                  1,
    //                  (Location)
    //     DBManager.getInstance().getManager(DataBaseObjectType.Location).get(154),
    //                  EquipmentType.PBED,
    //                  "My bed",
    //                  false,
    //                  false);
    //
    //          equipmentMongoCollection.insertOne(equipment);
    //
    //          Equipment equipment1 = equipmentMongoCollection.find(eq("_id", 1)).first();
    //          System.out.println("equipment1 result");
    //          System.out.println(equipment1);
    //        } catch (Exception e) {
    //          e.printStackTrace();
    //        }
    //
    //        try {
    //          MongoCollection<PatientTransportationServiceRequest> equipmentMongoCollection =
    //              database
    //                  .getCollection("ExternalPatientSR",
    // PatientTransportationServiceRequest.class)
    //                  .withCodecRegistry(
    //
    //     fromRegistries(CodecRegistries.fromCodecs(externalPatientTransportSRCodec)));
    //          System.out.println("Got ExternalPatientSR collection");
    //          PatientTransportationServiceRequest patientTransportationServiceRequest =
    //              new PatientTransportationServiceRequest(
    //                  false,
    //                  ServiceRequestPriority.High,
    //                  ServiceRequestStatus.OPEN,
    //                  "My additional info",
    //                  (Employee)
    //     DBManager.getInstance().getManager(DataBaseObjectType.Employee).get(12),
    //                  (Location)
    //     DBManager.getInstance().getManager(DataBaseObjectType.Location).get(154),
    //                  new java.sql.Date(0),
    //                  new java.sql.Date(0),
    //                  new java.sql.Date(new java.util.Date().getTime()),
    //                  "extPatient",
    //                  1,
    //                  (Location)
    //     DBManager.getInstance().getManager(DataBaseObjectType.Location).get(154),
    //                  (Equipment)
    //     DBManager.getInstance().getManager(DataBaseObjectType.Equipment).get(12),
    //                  (Patient)
    //     DBManager.getInstance().getManager(DataBaseObjectType.Patient).get(2));
    //
    //          equipmentMongoCollection.insertOne(patientTransportationServiceRequest);
    //          System.out.println("currently equpiment is");
    //          System.out.println(
    //              (Equipment)
    //     DBManager.getInstance().getManager(DataBaseObjectType.Equipment).get(48));
    //
    //          PatientTransportationServiceRequest patientTransportationServiceRequest1 =
    //              equipmentMongoCollection.find(eq("_id", 1)).first();
    //          System.out.println("ExternalPatientSR result");
    //          System.out.println(patientTransportationServiceRequest1);
    //        } catch (Exception e) {
    //          e.printStackTrace();
    //        }
    //
    //        MongoCollection<FoodDeliveryServiceRequest> foodSerReq =
    //            database.getCollection("FoodDeliveryServiceRequest",
    //     FoodDeliveryServiceRequest.class);
    //        System.out.println("Got FoodDeliveryServiceRequest collection");
    //
    //        FoodDeliveryServiceRequest food =
    //            new FoodDeliveryServiceRequest(
    //                ServiceRequestPriority.Critical,
    //                ServiceRequestStatus.OPEN,
    //                "My additional info",
    //                (Employee)
    //     DBManager.getInstance().getManager(DataBaseObjectType.Employee).get(12),
    //                (Location)
    //     DBManager.getInstance().getManager(DataBaseObjectType.Location).get(154),
    //                new Date(0),
    //                new Date(0),
    //                new Date(new java.util.Date().getTime()),
    //                "",
    //                2,
    //                (Patient)
    // DBManager.getInstance().getManager(DataBaseObjectType.Patient).get(2),
    //                "Test shit");
    //
    //        // foodSerReq.insertOne(food);
    //
    //        ResultSet rset =
    //            DBManager.getInstance()
    //                .getConnection()
    //                .createStatement()
    //                .executeQuery("Select * from Equipment");
    //        while (rset.next()) {
    //          System.out.println(rset.getInt("id"));
    //        }
    //
    //        FoodDeliveryServiceRequest foodResult = foodSerReq.find(eq("_id", 0)).first();
    //        System.out.println(foodResult);
    // Launch App
    // App.launch(App.class, args);
  }
}

package edu.wpi.teame;

import com.mongodb.client.*;
import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.objectManagers.ObjectManager;
import edu.wpi.teame.model.*;
import edu.wpi.teame.model.enums.*;
import edu.wpi.teame.model.mongoCodecs.*;
import edu.wpi.teame.model.serviceRequests.*;
import org.sqlite.core.DB;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

public class Main {
  public static void main(String[] args)
      throws IOException, SQLException, CsvValidationException, ParseException,
          NoSuchAlgorithmException {

    // Setup the DB
    DBManager.getInstance().setupDB();
    DBManager.getInstance().loadDB();

    ResultSet rset = DBManager.getInstance().getConnection().createStatement().executeQuery("Select * From LOCATION");
    while(rset.next()) {
      Location location = new Location(rset);
      DBManager.getInstance()
              .getMongoDatabase()
              .getCollection("Location", Location.class)
              .withCodecRegistry(DBManager.getInstance().getObjectCodecs())
              .insertOne(location);
    }

    rset = DBManager.getInstance().getConnection().createStatement().executeQuery("Select * From Equipment");
    while(rset.next()) {
      Equipment equipment = new Equipment(rset);
      DBManager.getInstance()
              .getMongoDatabase()
              .getCollection("Equipment", Equipment.class)
              .withCodecRegistry(DBManager.getInstance().getObjectCodecs())
              .insertOne(equipment);
    }

    rset = DBManager.getInstance().getConnection().createStatement().executeQuery("Select * From Edge");
    while(rset.next()) {
      Edge edge = new Edge(rset);
      DBManager.getInstance()
              .getMongoDatabase()
              .getCollection("Edge", Edge.class)
              .withCodecRegistry(DBManager.getInstance().getObjectCodecs())
              .insertOne(edge);
    }

    rset = DBManager.getInstance().getConnection().createStatement().executeQuery("Select * From Credential");
    while(rset.next()) {
      Credential credential = new Credential(rset);
      DBManager.getInstance()
              .getMongoDatabase()
              .getCollection("Credential", Credential.class)
              .withCodecRegistry(DBManager.getInstance().getObjectCodecs())
              .insertOne(credential);
    }

    rset = DBManager.getInstance().getConnection().createStatement().executeQuery("Select * From Employee");
    while(rset.next()) {
      Employee employee =new Employee(rset);
      DBManager.getInstance()
              .getMongoDatabase()
              .getCollection("Employee", Employee.class)
              .withCodecRegistry(DBManager.getInstance().getObjectCodecs())
              .insertOne(employee);
    }

    rset = DBManager.getInstance().getConnection().createStatement().executeQuery("Select * From Patient");
    while(rset.next()) {
      Patient patient =new Patient(rset);
      DBManager.getInstance()
              .getMongoDatabase()
              .getCollection("Patient", Patient.class)
              .withCodecRegistry(DBManager.getInstance().getObjectCodecs())
              .insertOne(patient);
    }

    rset = DBManager.getInstance().getConnection().createStatement().executeQuery("Select * From MentalHealthSR");
    while(rset.next()) {
      MentalHealthServiceRequest mentalHealthServiceRequest = new MentalHealthServiceRequest(rset);
      DBManager.getInstance()
              .getMongoDatabase()
              .getCollection("MentalHealthSR", MentalHealthServiceRequest.class)
              .withCodecRegistry(DBManager.getInstance().getObjectCodecs())
              .insertOne(mentalHealthServiceRequest);
    }

    rset = DBManager.getInstance().getConnection().createStatement().executeQuery("Select * From FacilitiesMaintenanceSR");
    while(rset.next()) {
      ServiceRequest serviceRequest = new ServiceRequest(rset, DataBaseObjectType.FacilitiesMaintenanceSR);
      DBManager.getInstance()
              .getMongoDatabase()
              .getCollection("FacilitiesMaintenanceSR", ServiceRequest.class)
              .withCodecRegistry(DBManager.getInstance().getObjectCodecs())
              .insertOne(serviceRequest);
    }



    /*
    CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(false).build();
    Codec<Credential> credentialCodec = new CredentialCodec();
    Codec<Edge> edgeCodec = new EdgeCodec();
    Codec<Employee> employeeCodec = new EmployeeCodec();
    Codec<Equipment> equipmentCodec = new EquipmentCodec();
    Codec<PatientTransportationServiceRequest> patientTransportationServiceRequestCodec =
        new PatientTransportationServiceRequestCodec();
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
    Codec<PatientDischargeServiceRequest> patientDischargeServiceRequestCodec =
        new PatientDischargeServiceRequestCodec();
    Codec<MentalHealthServiceRequest> mentalHealthServiceRequestCodec =
        new MentalHealthServiceRequestCodec();
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
            patientDischargeServiceRequestCodec,
            mentalHealthServiceRequestCodec,
            patientTransportationServiceRequestCodec,
            serviceRequestCodec);

    CodecRegistry pojoCodecRegistry =
        fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider), customCodecs);

    ConnectionString connectionString =
        new ConnectionString(
            "mongodb+srv://admin:admin@cluster0.yleso.mongodb.net/TeamEMongo?retryWrites=true&w=majority&tls=true&ssl=true");

    String connectString =
        "mongodb+srv://admin:admin@cluster0.45z0f.mongodb.net/TeamEMongos?retryWrites=true&w=majority";

    Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);

    MongoClientSettings options =
        MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(connectString))
            .build();
    try (MongoClient mongoClient = MongoClients.create(options)) {
      MongoDatabase database =
          mongoClient.getDatabase("TeamEMongos").withCodecRegistry(pojoCodecRegistry);
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

        // audioVisualSRCollection.insertOne(audioVisualSRs);

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

        // computerSRCollection.insertOne(computerSR);

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

        // credentialMongoCollection.insertOne(credential);

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
        // deceasedBodyRemovalServiceRequestMongoCollection.insertOne(deceasedBodyRemovalServiceRequest);

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

        // edgeMongoCollection.insertOne(edge);

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

        // employeeMongoCollection.insertOne(employee);

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

        // equipmentMongoCollection.insertOne(equipment);

        Equipment equipment1 = equipmentMongoCollection.find(eq("_id", 1)).first();
        System.out.println("equipment1 result");
        System.out.println(equipment1);
      } catch (Exception e) {
        e.printStackTrace();
      }

      try {
        MongoCollection<PatientTransportationServiceRequest> externalPatientSR =
            database
                .getCollection("ExternalPatientSR", PatientTransportationServiceRequest.class)
                .withCodecRegistry(pojoCodecRegistry);
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

        // equipmentMongoCollection.insertOne(patientTransportationServiceRequest);

        // todo get this codec for tranportation working
        PatientTransportationServiceRequest patientTransportationServiceRequest1 =
            externalPatientSR.find(eq("_id", 1)).first();
        patientTransportationServiceRequest1.setDbType(DataBaseObjectType.ExternalPatientSR);
        System.out.println("ExternalPatientSR result");
        System.out.println(patientTransportationServiceRequest1);
      } catch (Exception e) {
        e.printStackTrace();
      }

      try {
        MongoCollection<PatientTransportationServiceRequest> internalPatientTransferSR =
            database
                .getCollection(
                    "InternalPatientTransferSR", PatientTransportationServiceRequest.class)
                .withCodecRegistry(pojoCodecRegistry);
        System.out.println("Got InternalPatientTransferSR collection");
        PatientTransportationServiceRequest patientTransportationServiceRequest =
            new PatientTransportationServiceRequest(
                true,
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

        // internalPatientTransferSR.insertOne(patientTransportationServiceRequest);

        // todo get this codec for tranportation working
        PatientTransportationServiceRequest patientTransportationServiceRequest1 =
            internalPatientTransferSR.find(eq("_id", 1)).first();
        patientTransportationServiceRequest1.setDbType(
            DataBaseObjectType.InternalPatientTransferSR);
        System.out.println("internalPatientSr result");
        System.out.println(patientTransportationServiceRequest1);
      } catch (Exception e) {
        e.printStackTrace();
      }

      try {
        MongoCollection<ServiceRequest> facilitiesMongoCollection =
            database
                .getCollection("FacilitiesMaintenanceSR", ServiceRequest.class)
                .withCodecRegistry(pojoCodecRegistry);
        System.out.println("Got facilites collection");
        ServiceRequest equipment =
            new ServiceRequest(
                DataBaseObjectType.FacilitiesMaintenanceSR,
                ServiceRequestPriority.Critical,
                ServiceRequestStatus.OPEN,
                "My additional info",
                (Employee) DBManager.getInstance().getManager(DataBaseObjectType.Employee).get(12),
                (Location) DBManager.getInstance().getManager(DataBaseObjectType.Location).get(154),
                new java.sql.Date(0),
                new java.sql.Date(0),
                new java.sql.Date(new java.util.Date().getTime()),
                "I blew up the toilet",
                1);

        // facilitiesMongoCollection.insertOne(equipment);

        ServiceRequest facilities1 = facilitiesMongoCollection.find(eq("_id", 1)).first();
        System.out.println("facilities1 result");
        System.out.println(facilities1);
      } catch (Exception e) {
        e.printStackTrace();
      }

      try {
        MongoCollection<GiftAndFloralServiceRequest> giftAndFloralServiceRequestMongoCollection =
            database
                .getCollection("GiftAndFloralSR", GiftAndFloralServiceRequest.class)
                .withCodecRegistry(pojoCodecRegistry);
        System.out.println("Got GiftAndFloralSR collection");
        GiftAndFloralServiceRequest giftAndFloralServiceRequest =
            new GiftAndFloralServiceRequest(
                ServiceRequestPriority.Critical,
                ServiceRequestStatus.OPEN,
                "My additional info",
                (Employee) DBManager.getInstance().getManager(DataBaseObjectType.Employee).get(12),
                (Location) DBManager.getInstance().getManager(DataBaseObjectType.Location).get(154),
                new java.sql.Date(0),
                new java.sql.Date(0),
                new java.sql.Date(new java.util.Date().getTime()),
                "Iwant flower",
                1,
                (Patient) DBManager.getInstance().getManager(DataBaseObjectType.Patient).get(2));

        // giftAndFloralServiceRequestMongoCollection.insertOne(giftAndFloralServiceRequest);

        ServiceRequest flower1 =
            giftAndFloralServiceRequestMongoCollection.find(eq("_id", 1)).first();
        System.out.println("GiftAndFloralSR result");
        System.out.println(flower1);
      } catch (Exception e) {
        e.printStackTrace();
      }

      try {
        MongoCollection<LanguageInterpreterServiceRequest>
            languageInterpreterServiceRequestMongoCollection =
                database
                    .getCollection("LanguageInterpreterSR", LanguageInterpreterServiceRequest.class)
                    .withCodecRegistry(pojoCodecRegistry);
        System.out.println("Got LanguageInterpreterSR collection");
        LanguageInterpreterServiceRequest languageInterpreterServiceRequest =
            new LanguageInterpreterServiceRequest(
                ServiceRequestPriority.Critical,
                ServiceRequestStatus.OPEN,
                "My additional info",
                (Employee) DBManager.getInstance().getManager(DataBaseObjectType.Employee).get(12),
                (Location) DBManager.getInstance().getManager(DataBaseObjectType.Location).get(154),
                new java.sql.Date(0),
                new java.sql.Date(0),
                new java.sql.Date(new java.util.Date().getTime()),
                "Can't speak english",
                1,
                LanguageType.English,
                (Patient) DBManager.getInstance().getManager(DataBaseObjectType.Patient).get(2));

        // languageInterpreterServiceRequestMongoCollection.insertOne(languageInterpreterServiceRequest);

        LanguageInterpreterServiceRequest languageInterpreterServiceRequest1 =
            languageInterpreterServiceRequestMongoCollection.find(eq("_id", 1)).first();
        System.out.println("LanguageInterpreterSR result");
        System.out.println(languageInterpreterServiceRequest1);
      } catch (Exception e) {
        e.printStackTrace();
      }

      try {
        MongoCollection<ServiceRequest> laundryMongoCollection =
            database
                .getCollection("LaundrySR", ServiceRequest.class)
                .withCodecRegistry(pojoCodecRegistry);
        System.out.println("Got LaundrySR collection");
        ServiceRequest laundySR =
            new ServiceRequest(
                DataBaseObjectType.LaundrySR,
                ServiceRequestPriority.Critical,
                ServiceRequestStatus.OPEN,
                "My additional info",
                (Employee) DBManager.getInstance().getManager(DataBaseObjectType.Employee).get(12),
                (Location) DBManager.getInstance().getManager(DataBaseObjectType.Location).get(154),
                new java.sql.Date(0),
                new java.sql.Date(0),
                new java.sql.Date(new java.util.Date().getTime()),
                "Can't speak english",
                1);

        // laundryMongoCollection.insertOne(laundySR);

        ServiceRequest laundrySR1 = laundryMongoCollection.find(eq("_id", 1)).first();
        System.out.println("LaundrySR result");
        System.out.println(laundrySR1);
      } catch (Exception e) {
        e.printStackTrace();
      }

      try {
        MongoCollection<MedicalEquipmentServiceRequest>
            medicalEquipmentServiceRequestMongoCollection =
                database
                    .getCollection("MedicalEquipmentSR", MedicalEquipmentServiceRequest.class)
                    .withCodecRegistry(pojoCodecRegistry);
        System.out.println("Got MedicalEquipmentSR collection");
        MedicalEquipmentServiceRequest medicalEquipmentServiceRequest =
            new MedicalEquipmentServiceRequest(
                ServiceRequestPriority.Critical,
                ServiceRequestStatus.OPEN,
                "My additional info",
                (Employee) DBManager.getInstance().getManager(DataBaseObjectType.Employee).get(12),
                (Location) DBManager.getInstance().getManager(DataBaseObjectType.Location).get(154),
                new java.sql.Date(0),
                new java.sql.Date(0),
                new java.sql.Date(new java.util.Date().getTime()),
                "Need pump",
                1,
                (Equipment)
                    DBManager.getInstance().getManager(DataBaseObjectType.Equipment).get(49));

        // medicalEquipmentServiceRequestMongoCollection.insertOne(medicalEquipmentServiceRequest);

        MedicalEquipmentServiceRequest medicalEquipmentServiceRequest1 =
            medicalEquipmentServiceRequestMongoCollection.find(eq("_id", 1)).first();
        System.out.println("MedicalEquipmentSR result");
        System.out.println(medicalEquipmentServiceRequest1);
      } catch (Exception e) {
        e.printStackTrace();
      }

      try {
        MongoCollection<MedicineDeliveryServiceRequest>
            medicineDeliveryServiceRequestMongoCollection =
                database
                    .getCollection("MedicineDeliverySR", MedicineDeliveryServiceRequest.class)
                    .withCodecRegistry(pojoCodecRegistry);
        System.out.println("Got MedicineDeliverySR collection");
        MedicineDeliveryServiceRequest medicineDeliveryServiceRequest =
            new MedicineDeliveryServiceRequest(
                ServiceRequestPriority.Critical,
                ServiceRequestStatus.OPEN,
                "My additional info",
                (Employee) DBManager.getInstance().getManager(DataBaseObjectType.Employee).get(12),
                (Location) DBManager.getInstance().getManager(DataBaseObjectType.Location).get(154),
                new java.sql.Date(0),
                new java.sql.Date(0),
                new java.sql.Date(new java.util.Date().getTime()),
                "Need morphine",
                1,
                "Morphine",
                "1 Liter",
                (Patient) DBManager.getInstance().getManager(DataBaseObjectType.Patient).get(2));

        // medicineDeliveryServiceRequestMongoCollection.insertOne(medicineDeliveryServiceRequest);

        MedicineDeliveryServiceRequest medicalEquipmentServiceRequest1 =
            medicineDeliveryServiceRequestMongoCollection.find(eq("_id", 1)).first();
        System.out.println("MedicineDeliverySR result");
        System.out.println(medicalEquipmentServiceRequest1);
      } catch (Exception e) {
        e.printStackTrace();
      }

      try {
        MongoCollection<PatientDischargeServiceRequest>
            patientDischargeServiceRequestMongoCollection =
                database
                    .getCollection("PatientDischargeSR", PatientDischargeServiceRequest.class)
                    .withCodecRegistry(pojoCodecRegistry);
        System.out.println("Got PatientDischargeSR collection");
        PatientDischargeServiceRequest patientDischargeServiceRequest =
            new PatientDischargeServiceRequest(
                ServiceRequestPriority.Critical,
                ServiceRequestStatus.OPEN,
                "My additional info",
                (Employee) DBManager.getInstance().getManager(DataBaseObjectType.Employee).get(12),
                (Location) DBManager.getInstance().getManager(DataBaseObjectType.Location).get(154),
                new java.sql.Date(0),
                new java.sql.Date(0),
                new java.sql.Date(new java.util.Date().getTime()),
                "Need to leave",
                1,
                (Patient) DBManager.getInstance().getManager(DataBaseObjectType.Patient).get(2));

        // patientDischargeServiceRequestMongoCollection.insertOne(patientDischargeServiceRequest);

        PatientDischargeServiceRequest patientDischargeServiceRequest1 =
            patientDischargeServiceRequestMongoCollection.find(eq("_id", 1)).first();
        System.out.println("PatientDischargeSR result");
        System.out.println(patientDischargeServiceRequest1);
      } catch (Exception e) {
        e.printStackTrace();
      }

      try {
        MongoCollection<ReligiousServiceRequest> religiousServiceRequestMongoCollection =
            database
                .getCollection("RELIGIOUSSR", ReligiousServiceRequest.class)
                .withCodecRegistry(pojoCodecRegistry);
        System.out.println("Got RELIGIOUSSR collection");
        ReligiousServiceRequest religiousServiceRequest =
            new ReligiousServiceRequest(
                ServiceRequestPriority.Critical,
                ServiceRequestStatus.OPEN,
                "My additional info",
                (Employee) DBManager.getInstance().getManager(DataBaseObjectType.Employee).get(12),
                (Location) DBManager.getInstance().getManager(DataBaseObjectType.Location).get(154),
                new java.sql.Date(0),
                new java.sql.Date(0),
                new java.sql.Date(new java.util.Date().getTime()),
                "Need god",
                1,
                (Patient) DBManager.getInstance().getManager(DataBaseObjectType.Patient).get(2),
                "ME");

        // religiousServiceRequestMongoCollection.insertOne(religiousServiceRequest);

        ReligiousServiceRequest religiousServiceRequest1 =
            religiousServiceRequestMongoCollection.find(eq("_id", 1)).first();
        System.out.println("RELIGIOUSSR result");
        System.out.println(religiousServiceRequest1);
      } catch (Exception e) {
        e.printStackTrace();
      }

      try {
        MongoCollection<MentalHealthServiceRequest> mentalHealthServiceRequestMongoCollection =
            database
                .getCollection("MentalHealthSR", MentalHealthServiceRequest.class)
                .withCodecRegistry(pojoCodecRegistry);
        System.out.println("Got MentalHealthSR collection");
        MentalHealthServiceRequest mentalHealthServiceRequest =
            new MentalHealthServiceRequest(
                ServiceRequestPriority.Critical,
                ServiceRequestStatus.OPEN,
                "My additional info",
                (Employee) DBManager.getInstance().getManager(DataBaseObjectType.Employee).get(12),
                (Location) DBManager.getInstance().getManager(DataBaseObjectType.Location).get(154),
                new java.sql.Date(0),
                new java.sql.Date(0),
                new java.sql.Date(new java.util.Date().getTime()),
                "Need my mental health back :(",
                1,
                (Patient) DBManager.getInstance().getManager(DataBaseObjectType.Patient).get(2));

        // mentalHealthServiceRequestMongoCollection.insertOne(mentalHealthServiceRequest);

        MentalHealthServiceRequest mentalHealthServiceRequest1 =
            mentalHealthServiceRequestMongoCollection.find(eq("_id", 1)).first();
        System.out.println("MentalHealthSR result");
        System.out.println(mentalHealthServiceRequest1);
      } catch (Exception e) {
        e.printStackTrace();
      }

      try {
        MongoCollection<ServiceRequest> sanitationMongoCollection =
            database
                .getCollection("SanitationSR", ServiceRequest.class)
                .withCodecRegistry(pojoCodecRegistry);
        System.out.println("Got SanitationSR collection");
        ServiceRequest sanitationSR =
            new ServiceRequest(
                DataBaseObjectType.SanitationSR,
                ServiceRequestPriority.Critical,
                ServiceRequestStatus.OPEN,
                "My additional info",
                (Employee) DBManager.getInstance().getManager(DataBaseObjectType.Employee).get(12),
                (Location) DBManager.getInstance().getManager(DataBaseObjectType.Location).get(154),
                new java.sql.Date(0),
                new java.sql.Date(0),
                new java.sql.Date(new java.util.Date().getTime()),
                "Need cleanup isle 3",
                1);

        // sanitationMongoCollection.insertOne(sanitationSR);

        ServiceRequest sanitationSr1 = sanitationMongoCollection.find(eq("_id", 1)).first();
        System.out.println("SanitationSR result");
        System.out.println(sanitationSr1);
      } catch (Exception e) {
        e.printStackTrace();
      }

      try {
        MongoCollection<ServiceRequest> securityMongoCollection =
            database
                .getCollection("SECURITYSR", ServiceRequest.class)
                .withCodecRegistry(pojoCodecRegistry);
        System.out.println("Got RELSECURITYSRIGIOUSSR collection");
        ServiceRequest securitySR =
            new ServiceRequest(
                DataBaseObjectType.SecuritySR,
                ServiceRequestPriority.Critical,
                ServiceRequestStatus.OPEN,
                "My additional info",
                (Employee) DBManager.getInstance().getManager(DataBaseObjectType.Employee).get(12),
                (Location) DBManager.getInstance().getManager(DataBaseObjectType.Location).get(154),
                new java.sql.Date(0),
                new java.sql.Date(0),
                new java.sql.Date(new java.util.Date().getTime()),
                "Need backup",
                1);

        // securityMongoCollection.insertOne(securitySR);

        ServiceRequest securitySr1 = securityMongoCollection.find(eq("_id", 1)).first();
        System.out.println("SECURITYSR result");
        System.out.println(securitySr1);
      } catch (Exception e) {
        e.printStackTrace();
      }

      try {
        MongoCollection<FoodDeliveryServiceRequest> foodDeliveryServiceRequestMongoCollection =
            database
                .getCollection("FoodDeliveryServiceRequest", FoodDeliveryServiceRequest.class)
                .withCodecRegistry(pojoCodecRegistry);
        System.out.println("Got FoodDeliveryServiceRequest collection");
        FoodDeliveryServiceRequest foodDeliveryServiceRequest =
            new FoodDeliveryServiceRequest(
                ServiceRequestPriority.Critical,
                ServiceRequestStatus.OPEN,
                "My additional info",
                (Employee) DBManager.getInstance().getManager(DataBaseObjectType.Employee).get(12),
                (Location) DBManager.getInstance().getManager(DataBaseObjectType.Location).get(154),
                new java.sql.Date(0),
                new java.sql.Date(0),
                new java.sql.Date(new java.util.Date().getTime()),
                "Need food",
                1,
                (Patient) DBManager.getInstance().getManager(DataBaseObjectType.Patient).get(2),
                "Cheesebuger");

        // foodDeliveryServiceRequestMongoCollection.insertOne(foodDeliveryServiceRequest);

        FoodDeliveryServiceRequest foodDeliveryServiceRequest1 =
            foodDeliveryServiceRequestMongoCollection.find(eq("_id", 1)).first();
        System.out.println("FoodDeliveryServiceRequest result");
        System.out.println(foodDeliveryServiceRequest1);
      } catch (Exception e) {
        e.printStackTrace();
      }

      try {
        MongoCollection<Location> locationMongoCollection =
            database.getCollection("Location", Location.class).withCodecRegistry(pojoCodecRegistry);
        System.out.println("Got Location collection");
        Location location =
            new Location(
                1,
                "My house",
                150,
                150,
                FloorType.ThirdFloor,
                BuildingType.Tower,
                LocationType.BATH,
                "home");

        // locationMongoCollection.insertOne(location);

        Location location1 = locationMongoCollection.find(eq("_id", 1)).first();
        System.out.println("Location result");
        System.out.println(location1);
      } catch (Exception e) {
        e.printStackTrace();
      }

      try {
        MongoCollection<Patient> patientMongoCollection =
            database.getCollection("Patient", Patient.class).withCodecRegistry(pojoCodecRegistry);
        System.out.println("Got Patient collection");
        Patient patient =
            new Patient(
                (Location) DBManager.getInstance().getManager(DataBaseObjectType.Location).get(155),
                new Date(0),
                "Jose Morales",
                1);

        // patientMongoCollection.insertOne(patient);

        Patient patient1 = patientMongoCollection.find(eq("_id", 1)).first();
        System.out.println("Patient result");
        System.out.println(patient1);
      } catch (Exception e) {
        e.printStackTrace();
      }

      //      MongoCollection<FoodDeliveryServiceRequest> foodSerReq =
      //          database.getCollection("FoodDeliveryServiceRequest",
      // FoodDeliveryServiceRequest.class);
      //      System.out.println("Got FoodDeliveryServiceRequest collection");
      //
      //      FoodDeliveryServiceRequest food =
      //          new FoodDeliveryServiceRequest(
      //              ServiceRequestPriority.Critical,
      //              ServiceRequestStatus.OPEN,
      //              "My additional info",
      //              (Employee)
      // DBManager.getInstance().getManager(DataBaseObjectType.Employee).get(12),
      //              (Location)
      // DBManager.getInstance().getManager(DataBaseObjectType.Location).get(154),
      //              new Date(0),
      //              new Date(0),
      //              new Date(new java.util.Date().getTime()),
      //              "",
      //              2,
      //              (Patient)
      // DBManager.getInstance().getManager(DataBaseObjectType.Patient).get(2),
      //              "Test shit");
      //
      //      foodSerReq.insertOne(food);
      //
      //      ResultSet rset =
      //          DBManager.getInstance()
      //              .getConnection()
      //              .createStatement()
      //              .executeQuery("Select * from Equipment");
      //      while (rset.next()) {
      //        System.out.println(rset.getInt("id"));
      //      }
      //
      //      FoodDeliveryServiceRequest foodResult = foodSerReq.find(eq("_id", 0)).first();
      //      System.out.println(foodResult);
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


     */
    // Launch App
    //App.launch(App.class, args);
  }
}

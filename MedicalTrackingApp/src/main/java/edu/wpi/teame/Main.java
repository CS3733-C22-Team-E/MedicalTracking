package edu.wpi.teame;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.eq;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.Patient;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.enums.ServiceRequestPriority;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import edu.wpi.teame.model.mongoCodecs.FoodDeliveryServiceRequestCodec;
import edu.wpi.teame.model.serviceRequests.FoodDeliveryServiceRequest;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
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
    Codec<FoodDeliveryServiceRequest> locationCodec = new FoodDeliveryServiceRequestCodec();
    CodecRegistry customCodecs = CodecRegistries.fromCodecs(locationCodec);

    CodecRegistry pojoCodecRegistry =
        fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider), customCodecs);

    ConnectionString connectionString =
        new ConnectionString(
            "mongodb+srv://admin:admin@cluster0.yleso.mongodb.net/TeamEMongo?retryWrites=true&w=majority");

    MongoClientSettings settings =
        MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .applyToSocketSettings(
                builder -> {
                  builder.connectTimeout(4000, MILLISECONDS);
                  builder.readTimeout(4000, MILLISECONDS);
                })
            .serverApi(ServerApi.builder().version(ServerApiVersion.V1).build())
            .build();
    MongoClient mongoClient = MongoClients.create(settings);
    MongoDatabase database =
        mongoClient.getDatabase("TeamEMongo").withCodecRegistry(pojoCodecRegistry);
    // database.createCollection("FoodDeliveryServiceRequest");
    MongoCollection<FoodDeliveryServiceRequest> foodSerReq =
        database.getCollection("FoodDeliveryServiceRequest", FoodDeliveryServiceRequest.class);
    System.out.println("Got FoodDeliveryServiceRequest");

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
    //
    //    ResultSet set =
    //        DBManager.getInstance()
    //            .getConnection()
    //            .createStatement()
    //            .executeQuery("SELECT * FROM Patient");
    //    while (set.next()) {
    //      System.out.println(set.getInt("id"));
    //    }

    // foodSerReq.insertOne(food);

    FoodDeliveryServiceRequest result = foodSerReq.find(eq("_id", 0)).first();
    System.out.println(result);
    // Launch App
    // App.launch(App.class, args);
  }
}

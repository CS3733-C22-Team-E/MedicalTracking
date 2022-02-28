package edu.wpi.teame.db;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.OperationContext;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.*;
import edu.wpi.teame.model.Face;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;

public class FacialRecognitionManager {
  private final String faceIdPath =
      "detect?detectionModel=detection_01&returnFaceAttributes=occlusion&returnFaceId=true";
  private final String storageConnectionString =
      "DefaultEndpointsProtocol=https;AccountName=cs3733storage;AccountKey=0iJAxNWYXnUaeJj6qsRxAX+9+5i3zkbL7V+MiUK0jm/s9stlSAnBFUIPEUxYOvQATvI9KjX8dZm0+AStOcN1+A==;EndpointSuffix=core.windows.net";
  private final String azureEndpoint =
      "https://cs3733-hospitalapp-faces.cognitiveservices.azure.com/face/v1.0/";
  private final String apiKey = "fcf544e2dc7d433c953af9b02e4dd110";

  public FacialRecognitionManager() {}

  public Face findSimilar(Face testFace, Face[] masterFaces, double minimumConfidence)
      throws IOException, ParseException {
    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    HttpPost request = new HttpPost(azureEndpoint + "findsimilars");
    request.addHeader("content-type", "application/json");
    request.addHeader("Ocp-apim-subscription-key", apiKey);

    List<String> faceIds = new ArrayList<>(masterFaces.length);
    for (Face face : masterFaces) {
      faceIds.add(face.getFaceId());
    }

    JSONObject combined = new JSONObject();
    combined.put("faceIds", faceIds);
    combined.put("faceId", testFace.getFaceId());

    StringEntity jsonInput = new StringEntity(combined.toString());
    request.setEntity(jsonInput);

    CloseableHttpResponse response = httpClient.execute(request);
    String jsonString = EntityUtils.toString(response.getEntity(), "UTF-8");
    JSONArray jsonArray = new JSONArray(jsonString);

    double highestConfidence = minimumConfidence;
    String mostSimilarFaceId = "";
    for (int i = 0; i < jsonArray.length(); i++) {
      double confidence = jsonArray.optJSONObject(i).getDouble("confidence");
      if (confidence > highestConfidence) {
        mostSimilarFaceId = jsonArray.optJSONObject(i).getString("faceId");
        System.out.println("Confidence: " + confidence);
        highestConfidence = confidence;
      }
    }

    for (Face face : masterFaces) {
      if (face.getFaceId().equals(mostSimilarFaceId)) {
        return face;
      }
    }
    return null;
  }

  public Face getFaceID(String imageURL) throws IOException, ParseException {
    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    HttpPost request = new HttpPost(azureEndpoint + faceIdPath);
    request.addHeader("content-type", "application/json");
    request.addHeader("Ocp-apim-subscription-key", apiKey);

    StringEntity jsonInput = new StringEntity("{ \"url\": \"" + imageURL + "\" }");
    request.setEntity(jsonInput);

    CloseableHttpResponse response = httpClient.execute(request);
    String jsonString = EntityUtils.toString(response.getEntity(), "UTF-8");
    jsonString = jsonString.replace("[", "").replace("]", "");
    if (jsonString.isEmpty()) {
      return null;
    }

    JSONObject jsonObject = new JSONObject(jsonString);
    String faceId = jsonObject.getString("faceId");
    boolean mouthOccluded =
        jsonObject
            .getJSONObject("faceAttributes")
            .getJSONObject("occlusion")
            .getBoolean("mouthOccluded");
    return new Face(imageURL, faceId, mouthOccluded);
  }

  public String uploadImage(File imageFile, boolean masterImage)
      throws IOException, URISyntaxException, StorageException, InvalidKeyException {
    CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
    CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

    String containerName = masterImage ? "fr-images" : "login-images";
    CloudBlobContainer container = blobClient.getContainerReference(containerName);

    String imageName = UUID.randomUUID() + ".png";
    String imageURL =
        "https://cs3733storage.blob.core.windows.net/" + containerName + "/" + imageName;

    container.createIfNotExists(
        BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(), new OperationContext());
    CloudBlockBlob blob = container.getBlockBlobReference(imageName);
    blob.uploadFromFile(imageFile.getAbsolutePath());
    return imageURL;
  }
}

package edu.wpi.teame.db;

import java.io.IOException;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

public class FacialRecognitionManager {
  private final String azureEndpoint =
      "https://cs3733-hospitalapp-faces.cognitiveservices.azure.com/";
  private final String faceIdPath =
      "face/v1.0/detect?detectionModel=detection_01&faceIDTimeToLive=1800&returnFaceAttributes=occlusion&returnFaceId=true";
  private final String apiKey = "fcf544e2dc7d433c953af9b02e4dd110";

  public FacialRecognitionManager() {}

  public String getFaceID(String imageURL) throws IOException, ParseException {
    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    HttpPost request = new HttpPost(azureEndpoint + faceIdPath);
    request.addHeader("content-type", "application/json");
    request.addHeader("Ocp-apim-subscription-key", apiKey);

    StringEntity jsonInput = new StringEntity("{ \"url\": \"" + imageURL + "\" }");
    request.setEntity(jsonInput);

    CloseableHttpResponse response = httpClient.execute(request);
    String jsonString = EntityUtils.toString(response.getEntity(), "UTF-8");
    return "";
  }
}

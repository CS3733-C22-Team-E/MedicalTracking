package edu.wpi.teame.db.objectManagers;

import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.db.FacialRecognitionManager;
import edu.wpi.teame.model.Credential;
import edu.wpi.teame.model.enums.AccessLevel;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import org.apache.hc.core5.http.ParseException;

public final class CredentialManager extends ObjectManager<Credential> {
  private FacialRecognitionManager faceRecognizer = null;
  private HashMap<String, String> userImages = null;
  private Credential currentUser;

  public CredentialManager() {
    super(DataBaseObjectType.Credential, 0);
    faceRecognizer = new FacialRecognitionManager();
  }

  public boolean logIn(String imageURL) throws IOException, ParseException {
    userImages = new HashMap<>(loadedObjects.size());
    for (Credential user : loadedObjects) {
      if (!user.getImageURL().isEmpty()) {
        userImages.put(user.getImageURL(), user.getUsername());
      }
    }

    faceRecognizer.getFaceID(
        "https://cs3733storage.blob.core.windows.net/fr-images/WilsonWongMaster.png");
    return false;
  }

  public boolean logIn(String username, String password)
      throws SQLException, NoSuchAlgorithmException {
    // Get credential salt
    String salt = getSalt(username);

    // Check credential log in
    Credential credential = new Credential(0, salt, username, password, AccessLevel.Staff);
    List<Credential> credentialList = forceGetAll();

    for (Credential cred : credentialList) {
      if (cred.equals(credential)) {
        currentUser = cred;
        return true;
      }
    }
    return false;
  }

  public boolean hasUsername(String username) throws SQLException {
    String hasUsernameQuery = "SELECT Salt FROM CREDENTIAL WHERE username = '" + username + "'";
    ResultSet resultSet =
        DBManager.getInstance().getConnection().createStatement().executeQuery(hasUsernameQuery);
    return resultSet.next();
  }

  private String getSalt(String username) throws SQLException {
    String getSaltQuery = "SELECT Salt FROM Credential WHERE username = '" + username + "'";
    ResultSet saltQueryResultSet =
        DBManager.getInstance().getConnection().createStatement().executeQuery(getSaltQuery);
    if (!saltQueryResultSet.next()) {
      return null; // No Username found
    }
    return saltQueryResultSet.getString("salt");
  }

  public void logOut() {
    currentUser = null;
  }

  public Credential getCurrentUser() {
    return currentUser;
  }
}

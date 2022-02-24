package edu.wpi.teame.db.objectManagers;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.App;
import edu.wpi.teame.db.CSVLineData;
import edu.wpi.teame.db.DBManager;
import edu.wpi.teame.model.enums.AccessLevel;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

public final class CredentialManager {
  private AccessLevel currentUserLevel = null;
  private static CredentialManager instance;
  private MessageDigest messageDigest;
  private Connection connection;
  private Statement statement;

  public static synchronized CredentialManager getInstance()
      throws SQLException, NoSuchAlgorithmException {
    if (instance == null) {
      instance = new CredentialManager();
    }
    return instance;
  }

  private CredentialManager() throws NoSuchAlgorithmException, SQLException {
    messageDigest = MessageDigest.getInstance("SHA-256");
    connection = DBManager.getInstance().getConnection();
    statement = connection.createStatement();
  }

  public boolean logIn(String username, String password) throws SQLException {
    // Get credential salt
    String getSaltQuery = "SELECT * FROM Credential WHERE username = '" + username + "'";
    ResultSet saltQueryResultSet = statement.executeQuery(getSaltQuery);
    if (!saltQueryResultSet.next()) {
      return false; // No Username found
    }
    String salt = saltQueryResultSet.getString("salt");

    // Check credential log in
    String getQuery =
        "SELECT accessLevel FROM Credential WHERE username = '"
            + username
            + "' AND password = '"
            + hashPassword(password, stringToBytes(salt))
            + "'";
    ResultSet getQueryResultSet = statement.executeQuery(getQuery);

    if (!getQueryResultSet.next()) {
      return false;
    }

    // Save current user access leval
    currentUserLevel = AccessLevel.values()[getQueryResultSet.getInt("accessLevel")];
    return true;
  }

  public void insert(String username, String password)
      throws SQLException, NoSuchAlgorithmException {
    if (hasUsername(username)) {
      return;
    }

    byte[] salt = createSalt();
    String insertQuery =
        "INSERT INTO CREDENTIAL (salt, username, password) VALUES('"
            + bytesToString(salt)
            + "', '"
            + username
            + "', '"
            + hashPassword(password, salt)
            + "')";
    statement.executeUpdate(insertQuery);
  }

  private void insert(String salt, String username, String hashedPassword)
      throws SQLException, NoSuchAlgorithmException {
    if (hasUsername(username)) {
      return;
    }

    String insertQuery =
        "INSERT INTO CREDENTIAL (salt, username, password) VALUES('"
            + salt
            + "', '"
            + username
            + "', '"
            + hashedPassword
            + "')";
    statement.executeUpdate(insertQuery);
  }

  public void remove(String oldUsername) throws SQLException, NoSuchAlgorithmException {
    String insertQuery = "DELETE FROM CREDENTIAL WHERE username = '" + oldUsername + "'";
    statement.executeUpdate(insertQuery);
  }

  public boolean hasUsername(String username) throws SQLException, NoSuchAlgorithmException {
    String hasUsernameQuery = "SELECT Salt FROM CREDENTIAL WHERE username = '" + username + "'";
    ResultSet resultSet = statement.executeQuery(hasUsernameQuery);
    return resultSet.next();
  }

  public void readCSV(String inputFileName)
      throws IOException, SQLException, CsvValidationException, ParseException,
          NoSuchAlgorithmException {
    InputStream filePath = App.class.getResourceAsStream("csv/" + inputFileName);
    CSVReader csvReader = new CSVReader(new InputStreamReader(filePath));
    CSVLineData lineData = new CSVLineData(csvReader);

    while (lineData.readNext()) {
      String salt = lineData.getColumnString("salt");
      String username = lineData.getColumnString("username");
      String hashedPassword = lineData.getColumnString("password");
      insert(salt, username, hashedPassword);
    }
  }

  private String hashPassword(String password, byte[] salt) {
    messageDigest.update(salt);
    byte[] bytes = messageDigest.digest(password.getBytes());
    messageDigest.reset();
    return bytesToString(bytes);
  }

  private String bytesToString(byte[] bytes) {
    StringBuilder sb = new StringBuilder();
    for (byte aByte : bytes) {
      sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
    }
    return sb.toString();
  }

  private byte[] stringToBytes(String string) {
    byte[] byteArr = new byte[string.length() / 2];
    for (int i = 0; i < byteArr.length; i++) {
      int index = i * 2;
      int val = Integer.parseInt(string.substring(index, index + 2), 16);
      byteArr[i] = (byte) val;
    }
    return byteArr;
  }

  private byte[] createSalt() {
    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[4];
    random.nextBytes(salt);
    return salt;
  }

  public void logOut() {
    currentUserLevel = null;
  }

  public AccessLevel getCurrentUserLevel() {
    return currentUserLevel;
  }
}

package edu.wpi.teame.db;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.App;
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
import java.util.Objects;

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
        "SELECT id FROM Credential WHERE username = '"
            + username
            + "' AND password = '"
            + hashPassword(password, stringToBytes(salt))
            + "'";
    ResultSet getQueryResultSet = statement.executeQuery(getQuery);
    if (!getQueryResultSet.next()) {
      return false;
    }

    // TODO: load in the correct access level and fix the select above ^^
    currentUserLevel =
        Objects.equals(username, "admin")
            ? AccessLevel.Admin
            : AccessLevel.Staff; // AccessLevel.values()[getQueryResultSet.getInt(0)];
    return true;
  }

  public void insert(String username, String password)
      throws SQLException, NoSuchAlgorithmException {
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

  public void readCSV(String inputFileName)
      throws IOException, SQLException, CsvValidationException, ParseException,
          NoSuchAlgorithmException {

    //    ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    //    InputStream is =
    //        classloader.getResourceAsStream("../../../../../resources/csv/backup/" +
    // inputFileName);
    //    System.out.println(is);
    InputStream filePath = App.class.getResourceAsStream("csv/" + inputFileName);
    System.out.println(filePath);
    // System.getProperty("user.dir") + "/src/main/resources/edu/wpi/teame/csv/" + inputFileName;
    CSVReader csvReader = new CSVReader(new InputStreamReader(filePath));
    CSVLineData lineData = new CSVLineData(csvReader);

    String[] record;
    while ((record = csvReader.readNext()) != null) {
      lineData.setParsedData(record);

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

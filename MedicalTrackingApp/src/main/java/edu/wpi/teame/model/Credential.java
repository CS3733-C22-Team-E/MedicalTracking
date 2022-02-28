package edu.wpi.teame.model;

import edu.wpi.teame.db.CSVLineData;
import edu.wpi.teame.db.ISQLSerializable;
import edu.wpi.teame.model.enums.AccessLevel;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Credential implements ISQLSerializable {
  private MessageDigest messageDigest;
  private boolean isDeleted = false;
  private AccessLevel accessLevel;

  private String username;
  private String password;
  private String imageURL;
  private byte[] salt;
  private int id;

  public Credential(
      int id, String salt, String username, String password, AccessLevel accessLevel) {
    createHasher();
    this.id = id;
    this.imageURL = "";
    this.isDeleted = false;
    this.username = username;
    this.salt = stringToBytes(salt);
    this.accessLevel = accessLevel;
    this.password = hashPassword(password, this.salt);
  }

  public Credential(int id, String username, String password, AccessLevel accessLevel) {
    createHasher();
    this.id = id;
    this.imageURL = "";
    this.isDeleted = false;
    this.salt = createSalt();
    this.username = username;
    this.accessLevel = accessLevel;
    this.password = hashPassword(password, this.salt);
  }

  public Credential(CSVLineData lineData) {
    createHasher();
    this.id = lineData.getColumnInt("id");
    this.imageURL = lineData.getColumnString("imageURL");
    this.username = lineData.getColumnString("username");
    this.password = lineData.getColumnString("password");
    this.isDeleted = lineData.getColumnBoolean("isDeleted");
    this.salt = stringToBytes(lineData.getColumnString("salt"));
    this.accessLevel = AccessLevel.values()[lineData.getColumnInt("accessLevel")];
  }

  public Credential(ResultSet resultSet) throws SQLException {
    createHasher();
    this.id = resultSet.getInt("id");
    this.imageURL = resultSet.getString("imageURL");
    this.username = resultSet.getString("username");
    this.password = resultSet.getString("password");
    this.isDeleted = resultSet.getBoolean("isDeleted");
    this.salt = stringToBytes(resultSet.getString("salt"));
    this.accessLevel = AccessLevel.values()[resultSet.getInt("accessLevel")];
  }

  @Override
  public String toString() {
    return new StringBuilder()
        .append("id: ")
        .append(id)
        .append(", username: ")
        .append(username)
        .append(", password: ")
        .append(password)
        .toString();
  }

  @Override
  public String getSQLUpdateString() {
    return new StringBuilder()
        .append("salt = ")
        .append(bytesToString(salt))
        .append("username = ")
        .append(username)
        .append("password = ")
        .append(password)
        .append("accessLevel = ")
        .append(accessLevel.ordinal())
        .append("imageURL = ")
        .append(imageURL)
        .append(" WHERE id = ")
        .append(id)
        .toString();
  }

  @Override
  public String getSQLInsertString() {
    return new StringBuilder()
        .append("'")
        .append(bytesToString(salt))
        .append("', '")
        .append(username)
        .append("', '")
        .append(password)
        .append("', ")
        .append(accessLevel.ordinal())
        .append(", '")
        .append(imageURL)
        .append("'")
        .toString();
  }

  @Override
  public String[] toCSVData() {
    return new String[] {
      Integer.toString(id),
      bytesToString(salt),
      username,
      password,
      Integer.toString(accessLevel.ordinal()),
      imageURL,
      "0"
    };
  }

  @Override
  public String[] getCSVHeaders() {
    return new String[] {
      "id", "salt", "username", "password", "accessLevel", "imageURL", "isDeleted"
    };
  }

  @Override
  public String getTableColumns() {
    return "(salt, username, password, accessLevel, imageURL)";
  }

  @Override
  public DataBaseObjectType getDBType() {
    return DataBaseObjectType.Credential;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) { // Check if instance is equal
      return true;
    }

    // Check object class type and if null
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Credential other = (Credential) o;
    return username.equals(other.username) && password.equals(password);
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

  private void createHasher() {
    try {
      messageDigest = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException ex) {
      System.out.println(ex.toString());
    }
  }

  // Getters and Setters
  @Override
  public boolean getIsDeleted() {
    return isDeleted;
  }

  @Override
  public int getId() {
    return id;
  }

  public AccessLevel getAccessLevel() {
    return accessLevel;
  }

  public String getUsername() {
    return username;
  }

  public String getImageURL() {
    if (imageURL == null) {
      return "";
    }

    return imageURL;
  }
}

package edu.wpi.teame.model;

public class Face {
  private boolean mouthOccluded = false;
  private String imageURL = null;
  private String faceId = null;

  public Face(String imageURL, String id, boolean mouthOccluded) {
    this.mouthOccluded = mouthOccluded;
    this.imageURL = imageURL;
    this.faceId = id;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    return faceId.equals(((Face) obj).faceId);
  }

  @Override
  public int hashCode() {
    return faceId.hashCode();
  }

  // Getters and Setters
  public boolean isMouthOccluded() {
    return mouthOccluded;
  }

  public String getImageURL() {
    return imageURL;
  }

  public String getFaceId() {
    return faceId;
  }
}

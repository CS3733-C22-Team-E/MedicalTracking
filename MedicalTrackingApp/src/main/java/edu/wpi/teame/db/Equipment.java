package edu.wpi.teame.db;

public class Equipment {
  private String ID;
  private Location locationNode;
  // private FloorType floor;
  // private BuildingType building;
  private EquipmentType type;
  private String name;
  private boolean hasPatient;
  private boolean isClean;

  public Equipment(
      String ID,
      Location locationNodeID,
      EquipmentType type,
      String name,
      boolean hasPatient,
      boolean isClean) {
    this.ID = ID;
    this.locationNode = locationNodeID;
    // this.floor = floor;
    // this.building = building;
    this.type = type;
    this.name = name;
    this.hasPatient = hasPatient;
    this.isClean = isClean;
  }

  public String getNodeID() {
    return ID;
  }

  public void setNodeID(String nodeID) {
    this.ID = nodeID;
  }

  public Location getLocationNode() {
    return locationNode;
  }

  public void setLocationNode(Location locationNodeID) {
    this.locationNode = locationNodeID;
  }
  /*
     public FloorType getFloor() {
         return floor;
     }

     public void setFloor(FloorType floor) {
         this.floor = floor;
     }

     public BuildingType getBuilding() {
         return building;
     }

     public void setBuilding(BuildingType building) {
         this.building = building;
     }

  */
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public EquipmentType getType() {
    return type;
  }

  public void setType(EquipmentType type) {
    this.type = type;
  }

  public boolean isHasPatient() {
    return hasPatient;
  }

  public void setHasPatient(boolean hasPatient) {
    this.hasPatient = hasPatient;
  }

  public boolean isClean() {
    return isClean;
  }

  public void setClean(boolean clean) {
    isClean = clean;
  }

  public String toString() {
    return "ID: "
        + ID
        + ", locationNode: "
        + locationNode.getId()
        + ", type: "
        + type
        + ", name: "
        + name
        + ", hasPatient: "
        + hasPatient
        + ", isClean: "
        + isClean;
  }
}

package edu.wpi.teame.db;

public class Location {
    private String id;
    private String name;
    private LocationType type;

    private BuildingType building;
    private FloorType floor;
    private int x;
    private int y;

    public Location(String id, String name, int x, int y, FloorType floorType, BuildingType buildingType, LocationType locationType) {
        building = buildingType;
        type = locationType;
        floor = floorType;
        this.name = name;
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocationType getType() {
        return type;
    }

    public void setType(LocationType type) {
        this.type = type;
    }

    public BuildingType getBuilding() {
        return building;
    }

    public void setBuilding(BuildingType building) {
        this.building = building;
    }

    public FloorType getFloor() {
        return floor;
    }

    public void setFloor(FloorType floor) {
        this.floor = floor;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

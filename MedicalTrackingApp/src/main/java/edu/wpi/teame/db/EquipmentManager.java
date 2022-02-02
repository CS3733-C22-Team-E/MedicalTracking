package edu.wpi.teame.db;

import java.sql.*;
import java.util.LinkedList;

public class EquipmentManager implements IManager<Equipment>{
    private Connection connection;
    private Statement stmt;


    public EquipmentManager() {
        connection = DBManager.getInstance().getConnection();

        try {
            stmt = connection.createStatement();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Equipment get(String id) {
        String getQuery = "SELECT * FROM EQUIPMENT WHERE";
        return null;

    }

    @Override
    public LinkedList<Equipment> getAll() {
        return null;
    }

    @Override
    public void insert(Equipment newObject) {

    }

    @Override
    public void remove(String id) {

    }

    @Override
    public void update(Equipment updatedObject) {

    }
}

package edu.wpi.teame.db;

import java.util.LinkedList;
import java.sql.*;

public class MedicalEquipmentServiceRequestManager implements IManager<MedicalEquipmentServiceRequest>{
    private Connection connection;
    private Statement stmt;

    public MedicalEquipmentServiceRequestManager() {
        connection = DBManager.getInstance().getConnection();

        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public MedicalEquipmentServiceRequest get(String id) {
        String getQuery = "SELECT * FROM EQUIPMENTSERVICEREQUEST WHERE id='" + id + "'";
        MedicalEquipmentServiceRequest result = null;
        try{
            ResultSet rset = stmt.executeQuery(getQuery);

            while(rset.next()) {
                result = new MedicalEquipmentServiceRequest(rset.getString("id"), rset.getString("id"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public LinkedList<MedicalEquipmentServiceRequest> getAll() {
        return null;
    }

    @Override
    public void insert(MedicalEquipmentServiceRequest newObject) {

    }

    @Override
    public void remove(String id) {

    }

    @Override
    public void update(MedicalEquipmentServiceRequest updatedObject) {

    }
}

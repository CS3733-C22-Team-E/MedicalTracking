package edu.wpi.teame.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class EmployeeManager implements IManager<Employee> {
    private static Connection connection;
    private static Statement stmt;

    public EmployeeManager() {
        connection = DBManager.getInstance().getConnection();

        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Employee get(String id) {
        return null;
    }

    @Override
    public LinkedList<Employee> getAll() {
        return null;
    }

    @Override
    public void insert(Employee newObject) {

    }

    @Override
    public void remove(String id) {

    }

    @Override
    public void update(Employee updatedObject) {

    }

    @Override
    public void readCSV(String csvFile) throws IOException {

    }

    @Override
    public void writeToCSV(String outputFilePath) {

    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author dell
 */
// DatabaseConnection.java
package com.tms.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/tourism_management?useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Database connection established successfully.");
            return connection;
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to establish database connection. Check configuration.");
        }
    }

    public static void main(String[] args) {
        Connection testConnection = getConnection();
        if (testConnection != null) {
            System.out.println("Database connectivity test passed!");
            try {

                testConnection.close();
                System.out.println("Connection closed successfully after testing.");
            } catch (SQLException e) {
                System.err.println("Error closing the database connection: " + e.getMessage());
            }
        } else {
            System.err.println("Database connectivity test failed. Please check your configuration.");
        }
    }
}

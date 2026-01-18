/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author dell
 */
package com.tms.user;

import com.tms.database.DatabaseConnection;

import java.sql.*;

public class UserManager {

    public boolean loginUser(String email, String password) {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {

            if (connection == null) {
                System.out.println("Failed to connect to the database.");
                return false;
            }

            stmt.setString(1, email);
            stmt.setString(2, password);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("Login successful for user: " + email);
                    return true;
                } else {
                    System.out.println("Invalid email or password.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error during login: " + e.getMessage());
        }
        return false;
    }

    public int getUserID(String email) {
        int userID = -1; // Default value if no user is found

        String query = "SELECT user_id FROM users WHERE email = ?";
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    userID = resultSet.getInt("user_id");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user ID: " + e.getMessage());
        }
        return userID;
    }

    public boolean registerUser(String name, String email, String password) {
        // Check if email already exists
        String checkQuery = "SELECT email FROM users WHERE email = ?";
        // Insert a new user
        String insertQuery = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection == null) {
                System.out.println("Failed to connect to the database.");
                return false;
            }

            try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
                checkStmt.setString(1, email);
                try (ResultSet resultSet = checkStmt.executeQuery()) {
                    if (resultSet.next()) {
                        System.out.println("User with this email already exists.");
                        return false; 
                    }
                }
            }

            try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                insertStmt.setString(1, name);
                insertStmt.setString(2, email);

                insertStmt.setString(3, password);

                int rowsInserted = insertStmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("User registered successfully!");
                    return true; 
                }
            }
        } catch (SQLException e) {
            System.err.println("Error during registration: " + e.getMessage());
        }

        return false; 
    }

    public boolean updatePassword(String email, String newPassword) {
        String query = "UPDATE users SET password = ? WHERE email = ?";
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {

            if (connection == null) {
                System.out.println("Failed to connect to the database.");
                return false;
            }

            stmt.setString(1, newPassword);
            stmt.setString(2, email);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Password updated successfully for email: " + email);
                return true;
            } else {
                System.out.println("No user found with email: " + email);
            }
        } catch (SQLException e) {
            System.err.println("Error updating password: " + e.getMessage());
        }
        return false;
    }

    public boolean userExists(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {

            if (connection == null) {
                System.out.println("Failed to connect to the database.");
                return false;
            }

            stmt.setString(1, email);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking user existence: " + e.getMessage());
        }
        return false;
    }
}

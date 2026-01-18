/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author dell
 */
// DestinationManager.java
// DestinationManager.java
package com.tms.destination;

import com.tms.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DestinationManager {

    public ArrayList<Destination> getAllDestinations() {
        ArrayList<Destination> destinations = new ArrayList<>();
        String query = "SELECT * FROM destinations";

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection == null) {
                throw new SQLException("Database connection is null. Check your configuration.");
            }

            try (PreparedStatement stmt = connection.prepareStatement(query); ResultSet resultSet = stmt.executeQuery()) {

                while (resultSet.next()) {
       
                    Destination destination = new Destination(
                            resultSet.getInt("destination_id"),
                            resultSet.getString("name"),
                            resultSet.getString("location"),
                            resultSet.getDouble("latitude"),
                            resultSet.getDouble("longitude"),
                            resultSet.getString("description"),
                            resultSet.getString("image_path")
                    );
                    destinations.add(destination);

                    System.out.println("Loaded Destination: " + destination.getName() + " (ID: " + destination.getDestinationID() + ")");
                }

                if (destinations.isEmpty()) {
                    System.out.println("No destinations found in the database.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching destinations: " + e.getMessage());
            e.printStackTrace();
        }

        return destinations;
    }

    public Destination getDestinationDetails(int destinationID) {
        String query = "SELECT * FROM destinations WHERE destination_id = ?";
        Destination destination = null;

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection == null) {
                throw new SQLException("Database connection is null. Check your configuration.");
            }

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, destinationID);

                try (ResultSet resultSet = stmt.executeQuery()) {
                    if (resultSet.next()) {
                        destination = new Destination(
                                resultSet.getInt("destination_id"),
                                resultSet.getString("name"),
                                resultSet.getString("location"),
                                resultSet.getDouble("latitude"),
                                resultSet.getDouble("longitude"),
                                resultSet.getString("description"),
                                resultSet.getString("image_path")
                        );

                        System.out.println("Fetched Destination Details: " + destination.getName() + " (ID: " + destinationID + ")");
                    } else {
                        System.out.println("No destination found with ID: " + destinationID);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching destination details for ID " + destinationID + ": " + e.getMessage());
            e.printStackTrace();
        }

        return destination;
    }

    /**
     * Log all destinations in the database for debugging purposes.
     */
    public void printAllDestinations() {
        ArrayList<Destination> destinations = getAllDestinations();
        if (destinations.isEmpty()) {
            System.out.println("No destinations found to print.");
        } else {
            System.out.println("\n=== All Destinations ===");
            for (Destination destination : destinations) {
                System.out.println("ID: " + destination.getDestinationID());
                System.out.println("Name: " + destination.getName());
                System.out.println("Location: " + destination.getLocation());
                System.out.println("Latitude: " + destination.getLatitude());
                System.out.println("Longitude: " + destination.getLongitude());
                System.out.println("Description: " + destination.getDescription());
                System.out.println("Image Path: " + destination.getImagePath());
                System.out.println("-----------------------------");
            }
        }
    }
}

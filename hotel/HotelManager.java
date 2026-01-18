/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author dell
 */
package com.tms.hotel;

import com.tms.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelManager {

    public List<Hotel> getHotelsForDestination(int destinationID) {
        List<Hotel> hotels = new ArrayList<>();
        String query = "SELECT hotel_id, hotel_name, room_type, price_per_night, availability FROM hotels WHERE destination_id = ?";

        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, destinationID); // Use destination_id to filter hotels
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Map each row to a Hotel object
                    Hotel hotel = new Hotel(
                            rs.getInt("hotel_id"),
                            rs.getString("name"),
                            rs.getString("type"),
                            rs.getDouble("price"),
                            rs.getInt("availability")
                    );
                    hotels.add(hotel);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching hotels for destination: " + e.getMessage());
        }
        return hotels;
    }

    /**
     * Fetches all hotels for a specific package from the database and maps them
     * to Hotel objects.
     *
     * @param packageID The package ID to filter hotels by.
     * @return List of Hotel objects associated with the package.
     */
    public List<Hotel> getHotelsForPackage(int packageID) {
        List<Hotel> hotels = new ArrayList<>();
        String query = "SELECT * FROM hotels WHERE package_id = ?";
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, packageID); // Use package_id to filter hotels
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Map each row to a Hotel object
                    Hotel hotel = new Hotel(
                            rs.getInt("hotel_id"),
                            rs.getString("hotel_name"),
                            rs.getString("room_type"),
                            rs.getDouble("price_per_night"),
                            rs.getInt("availability")
                    );

                    hotels.add(hotel);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching hotels for package: " + e.getMessage());
        }
        return hotels;
    }
}

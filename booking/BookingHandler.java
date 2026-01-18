/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author dell
 */
package com.tms.booking;

import com.tms.database.DatabaseConnection;
import com.tms.utils.DebugLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BookingHandler {

    public boolean isAlreadyBooked(int userID, int packageID) {
        DebugLogger.log("Checking if package is already booked for userID: " + userID + ", packageID: " + packageID);

        if (userID <= 0 || packageID <= 0) {
            throw new IllegalArgumentException("Invalid userID or packageID provided.");
        }

        String query = "SELECT COUNT(*) FROM bookings WHERE user_id = ? AND package_id = ? AND status = 'Confirmed'";
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userID);
            stmt.setInt(2, packageID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    DebugLogger.log("Package booking count for userID " + userID + ": " + count);
                    return count > 0;
                }
            }
        } catch (Exception e) {
            DebugLogger.logError("Error checking booking status", e);
            throw new RuntimeException("Failed to check booking status. Please try again later.");
        }
        return false;
    }

    // Save a new booking after payment confirmation
    public boolean saveBookingAfterPayment(int userID, int packageID, int hotelID, int transportID, double amount) {
        DebugLogger.log("Saving booking for userID: " + userID + ", packageID: " + packageID);

        // Validate parameters
        if (userID <= 0 || packageID <= 0 || hotelID <= 0 || transportID <= 0) {
            throw new IllegalArgumentException("Invalid parameters provided for booking.");
        }

        String insertBookingQuery = "INSERT INTO bookings (user_id, package_id, hotel_id, transport_id, booking_date, status) VALUES (?, ?, ?, ?, NOW(), 'Confirmed')";
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(insertBookingQuery)) {
            stmt.setInt(1, userID);
            stmt.setInt(2, packageID);
            stmt.setInt(3, hotelID);
            stmt.setInt(4, transportID);

            int rowsAffected = stmt.executeUpdate();
            DebugLogger.log("Rows affected during booking insertion: " + rowsAffected);

            if (rowsAffected > 0) {
                return true; 
            } else {
                throw new RuntimeException("Booking was not saved. Please try again.");
            }
        } catch (Exception e) {
            DebugLogger.logError("Error saving booking", e);
            throw new RuntimeException("Failed to save booking. Please contact support.");
        }
    }

    public List<Booking> getBookingsByUser(int userID) {
        DebugLogger.log("Retrieving bookings for userID: " + userID);

        if (userID <= 0) {
            throw new IllegalArgumentException("Invalid userID provided.");
        }

        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT b.booking_id, h.hotel_name, t.transport_type, b.status, b.booking_date "
                + "FROM bookings b "
                + "LEFT JOIN hotels h ON b.hotel_id = h.hotel_id "
                + "LEFT JOIN transport t ON b.transport_id = t.transport_id "
                + "WHERE b.user_id = ?";
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userID);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Booking booking = new Booking(
                            rs.getInt("booking_id"),
                            rs.getString("hotel_name"),
                            rs.getString("transport_type"),
                            rs.getString("status"),
                            rs.getTimestamp("booking_date")
                    );
                    bookings.add(booking);
                }
                DebugLogger.log("Total bookings retrieved for userID " + userID + ": " + bookings.size());
            }
        } catch (Exception e) {
            DebugLogger.logError("Error retrieving bookings", e);
            throw new RuntimeException("Failed to retrieve bookings. Please try again later.");
        }
        return bookings;
    }

    public boolean cancelBooking(int bookingID) {
        DebugLogger.log("Attempting to cancel booking with bookingID: " + bookingID);

        if (bookingID <= 0) {
            throw new IllegalArgumentException("Invalid bookingID provided.");
        }

        String cancelQuery = "DELETE FROM bookings WHERE booking_id = ?";
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(cancelQuery)) {
            stmt.setInt(1, bookingID);
            int rowsAffected = stmt.executeUpdate();
            DebugLogger.log("Rows affected during booking cancellation: " + rowsAffected);
            return rowsAffected > 0;
        } catch (Exception e) {
            DebugLogger.logError("Error canceling booking with bookingID: " + bookingID, e);
            throw new RuntimeException("Failed to cancel booking. Please contact support.");
        }
    }
}

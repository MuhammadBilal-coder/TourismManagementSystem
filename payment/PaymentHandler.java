/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author dell
 */
package com.tms.payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.tms.database.DatabaseConnection;

public class PaymentHandler {

    public boolean savePayment(int bookingID, double amount, String paymentMethod, String transactionID) {
        String query = "INSERT INTO payments (booking_id, amount, payment_method, payment_status, transaction_id) VALUES (?, ?, ?, 'Success', ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, bookingID);
            stmt.setDouble(2, amount);
            stmt.setString(3, paymentMethod);
            stmt.setString(4, transactionID);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Payment saved successfully for Transaction ID: " + transactionID);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error saving payment: " + e.getMessage());
        }
        return false;
    }

    public boolean processPayment(double amount) {

        System.out.println("Processing payment for amount: " + amount);
        return true; // Always returns success for now
    }

    // Update payment status in the database
    public boolean updatePaymentStatus(String transactionID, String status) {
        String query = "UPDATE payments SET payment_status = ? WHERE transaction_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, status);
            stmt.setString(2, transactionID);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Payment status updated to: " + status + " for Transaction ID: " + transactionID);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error updating payment status: " + e.getMessage());
        }
        return false;
    }

    // Check if a booking exists in the database
    public boolean isBookingValid(int bookingID) {
        String query = "SELECT 1 FROM bookings WHERE booking_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, bookingID);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Returns true if booking exists
        } catch (SQLException e) {
            System.err.println("Error checking booking validity: " + e.getMessage());
        }
        return false;
    }
}

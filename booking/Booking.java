/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author dell
 */
package com.tms.booking;

import java.sql.Timestamp;

public class Booking {

    private int bookingID;
    private String hotelName;
    private String transportName;
    private String status;
    private Timestamp bookingDate;

    // Constructor
    public Booking(int bookingID, String hotelName, String transportName, String status, Timestamp bookingDate) {
        this.bookingID = bookingID;
        this.hotelName = hotelName;
        this.transportName = transportName;
        this.status = status;
        this.bookingDate = bookingDate;
    }

    // Getter methods
    public int getBookingID() {
        return bookingID;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getTransportName() {
        return transportName;
    }

    public String getStatus() {
        return status;
    }

    public Timestamp getBookingDate() {
        return bookingDate;
    }

    // Setter methods (optional, if needed)
    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public void setTransportName(String transportName) {
        this.transportName = transportName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setBookingDate(Timestamp bookingDate) {
        this.bookingDate = bookingDate;
    }

    // Helper method for displaying the booking details
    public String displayBookingDetails() {
        return "Booking Details:\n"
                + "Booking ID: " + bookingID + "\n"
                + "Hotel Name: " + hotelName + "\n"
                + "Transport Name: " + transportName + "\n"
                + "Status: " + status + "\n"
                + "Booking Date: " + bookingDate + "\n";
    }

    @Override
    public String toString() {
        return "Booking{"
                + "bookingID=" + bookingID
                + ", hotelName='" + hotelName + '\''
                + ", transportName='" + transportName + '\''
                + ", status='" + status + '\''
                + ", bookingDate=" + bookingDate
                + '}';
    }

    public static Booking createDefaultBooking() {
        return new Booking(0, "Default Hotel", "Default Transport", "Pending", new Timestamp(System.currentTimeMillis()));
    }
}

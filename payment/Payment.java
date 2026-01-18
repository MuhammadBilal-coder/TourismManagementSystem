/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author dell
 */
package com.tms.payment;

public class Payment {

    private int paymentID;
    private int bookingID;
    private double amount;
    private String paymentMethod;
    private String paymentStatus;
    private String transactionID;

    // Constructor
    public Payment(int bookingID, double amount, String paymentMethod) {
        this.bookingID = bookingID;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = "Pending";
        this.transactionID = generateTransactionID();
    }

    public int getPaymentID() {
        return paymentID;
    }

    public double getAmount() {
        return amount;
    }

    // Generate a unique transaction ID
    private String generateTransactionID() {
        return "TXN" + System.currentTimeMillis();
    }

    // Getters and Setters
    public int getBookingID() {
        return bookingID;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }
}

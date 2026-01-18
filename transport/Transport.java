/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author dell
 */
package com.tms.transport;

public class Transport {

    private int transportID;
    private String transportType;
    private int capacity;
    private double ratePerDay;

    // Constructor
    public Transport(int transportID, String transportType, int capacity, double ratePerDay) {
        this.transportID = transportID;
        this.transportType = transportType;
        this.capacity = capacity;
        this.ratePerDay = ratePerDay;
    }

    // Getters
    public int getTransportID() {
        return transportID;
    }

    public String getTransportType() {
        return transportType;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getRatePerDay() {
        return ratePerDay;
    }

    @Override
    public String toString() {
        return transportType + " (Capacity: " + capacity + ", Rate: " + ratePerDay + ")";
    }
}

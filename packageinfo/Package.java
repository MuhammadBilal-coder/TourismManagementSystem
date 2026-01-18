/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author dell
 */
package com.tms.packageinfo;

public class Package {

    private int packageID;
    private int destinationID;
    private String type; 
    private double price;
    private String activities; 
    private String hotelOptions;
    private String transportOptions;

    public Package(int packageID, int destinationID, String type, double price, String activities, String hotelOptions, String transportOptions) {
        this.packageID = packageID;
        this.destinationID = destinationID;
        this.type = type;
        this.price = price;
        this.activities = activities;
        this.hotelOptions = hotelOptions;
        this.transportOptions = transportOptions;
    }

    public Package() {
    }

    // Getters
    public int getPackageID() {
        return packageID;
    }

    public int getDestinationID() {
        return destinationID;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public String getActivities() {
        return activities;
    }

    public String getHotelOptions() {
        return hotelOptions;
    }

    public String getTransportOptions() {
        return transportOptions;
    }

    // Setters
    public void setPackageID(int packageID) {
        this.packageID = packageID;
    }

    public void setDestinationID(int destinationID) {
        this.destinationID = destinationID;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public void setHotelOptions(String hotelOptions) {
        this.hotelOptions = hotelOptions;
    }

    public void setTransportOptions(String transportOptions) {
        this.transportOptions = transportOptions;
    }

    // Display package details
    @Override
    public String toString() {
        return "Package: " + type + " | Price: " + price + " | Activities: " + activities;
    }
}

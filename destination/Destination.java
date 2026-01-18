/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author dell
 */
// Destination.java
package com.tms.destination;

public class Destination {

    private int destinationID;
    private String name;
    private String location;
    private double latitude;
    private double longitude;
    private String description;
    private String imagePath;

    public Destination(int destinationID, String name, String location, double latitude, double longitude, String description, String imagePath) {
        this.destinationID = destinationID;
        this.name = name;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.imagePath = imagePath;
    }

    // Getters
    public int getDestinationID() {
        return destinationID;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void displayDestinationDetails() {
        System.out.println("ID: " + destinationID);
        System.out.println("Name: " + name);
        System.out.println("Location: " + location);
        System.out.println("Latitude: " + latitude);
        System.out.println("Longitude: " + longitude);
        System.out.println("Description: " + description);
        System.out.println("Image Path: " + imagePath);
    }
}

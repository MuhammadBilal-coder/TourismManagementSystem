/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author dell
 */
package com.tms.hotel;

public class Hotel {
    private int hotelID;
    private String name;
    private String type;
    private double price;
    private int availability;

    public Hotel(int hotelID, String name, String type, double price, int availability) {
        this.hotelID = hotelID;
        this.name = name;
        this.type = type;
        this.price = price;
        this.availability = availability;
    }

    public int getHotelID() {
        return hotelID;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public int getAvailability() {
        return availability;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "hotelID=" + hotelID +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", availability=" + availability +
                '}';
    }
}

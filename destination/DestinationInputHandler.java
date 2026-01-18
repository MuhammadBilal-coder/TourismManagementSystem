/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author dell
 */
//package com.tms.destination;
//
//import java.util.ArrayList;
//import java.util.Scanner;
//
//public class DestinationInputHandler {
//
//    private final DestinationManager destinationManager;
//
//    public DestinationInputHandler() {
//        this.destinationManager = new DestinationManager();
//    }
//
//    public void run() {
//        Scanner scanner = new Scanner(System.in);
//
//        while (true) {
//            System.out.println("\n==== Destination Management ====");
//            System.out.println("1. View All Destinations");
//            System.out.println("2. See Destination Details");
//            System.out.println("3. Exit");
//            System.out.print("Choose an option: ");
//
//            int choice;
//            try {
//                choice = Integer.parseInt(scanner.nextLine()); // Read input safely
//            } catch (NumberFormatException e) {
//                System.out.println("Invalid option! Please enter a number.");
//                continue;
//            }
//
//            switch (choice) {
//                case 1 ->
//                    viewAllDestinations();
//                case 2 ->
//                    seeDestinationDetails(scanner);
//                case 3 -> {
//                    System.out.println("Exiting Destination Management...");
//                    return;
//                }
//                default ->
//                    System.out.println("Invalid option! Please try again.");
//            }
//        }
//    }
//
//    private void viewAllDestinations() {
//        ArrayList<Destination> destinations = destinationManager.getAllDestinations(); // Updated to fetch Destination objects
//        System.out.println("\n=== All Destinations ===");
//        if (destinations.isEmpty()) {
//            System.out.println("No destinations available.");
//        } else {
//            for (Destination destination : destinations) {
//                System.out.println("ID: " + destination.getDestinationID() + ", Name: " + destination.getName());
//            }
//        }
//    }
//
//    private void seeDestinationDetails(Scanner scanner) {
//        System.out.print("Enter the Destination ID to see details: ");
//        int destinationID;
//        try {
//            destinationID = Integer.parseInt(scanner.nextLine());
//        } catch (NumberFormatException e) {
//            System.out.println("Invalid input. Please enter a numeric ID.");
//            return;
//        }
//
//        Destination destination = destinationManager.getDestinationDetails(destinationID); // Fetch details using updated method
//        if (destination != null) {
//            System.out.println("\n=== Destination Details ===");
//            System.out.println("ID: " + destination.getDestinationID());
//            System.out.println("Name: " + destination.getName());
//            System.out.println("Location: " + destination.getLocation());
//            System.out.println("Latitude: " + destination.getLatitude());
//            System.out.println("Longitude: " + destination.getLongitude());
//            System.out.println("Description: " + destination.getDescription());
//            System.out.println("Image Path: " + destination.getImagePath()); // Include image path
//        } else {
//            System.out.println("Destination not found. Please check the ID and try again.");
//        }
//    }
//}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author dell
 */
package com.tms.packageinfo;

import com.tms.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PackageManager {

    public ArrayList<Package> getPackagesByDestination(int destinationID) {
        ArrayList<Package> packages = new ArrayList<>();
        String query = "SELECT * FROM packages WHERE destination_id = ?";

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection == null) {
                throw new SQLException("Database connection is null. Please check your configuration.");
            }

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, destinationID);

                try (ResultSet resultSet = stmt.executeQuery()) {
                    while (resultSet.next()) {
                        // Construct the Package object
                        Package pkg = new Package(
                                resultSet.getInt("package_id"),
                                resultSet.getInt("destination_id"),
                                resultSet.getString("type"),
                                resultSet.getDouble("price"),
                                resultSet.getString("activities"),
                                resultSet.getString("hotel_options"),
                                resultSet.getString("transport_options")
                        );

                        packages.add(pkg);

                        System.out.println("Loaded Package: " + pkg.getType() + " for Destination ID: " + destinationID);
                    }
                }
            }
        } catch (SQLException e) {

            System.err.println("Error retrieving packages for Destination ID " + destinationID + ": " + e.getMessage());
            e.printStackTrace();
        }

        // Log if no packages are found
        if (packages.isEmpty()) {
            System.out.println("No packages found for Destination ID: " + destinationID);
        }

        return packages;
    }

    public boolean addPackage(Package pkg) {
        String query = "INSERT INTO packages (destination_id, type, price, activities, hotel_options, transport_options) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection == null) {
                throw new SQLException("Database connection is null. Please check your configuration.");
            }

            try (PreparedStatement stmt = connection.prepareStatement(query)) {

                stmt.setInt(1, pkg.getDestinationID());
                stmt.setString(2, pkg.getType());
                stmt.setDouble(3, pkg.getPrice());
                stmt.setString(4, pkg.getActivities());
                stmt.setString(5, pkg.getHotelOptions());
                stmt.setString(6, pkg.getTransportOptions());

                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Package added successfully: " + pkg.getType() + " for Destination ID: " + pkg.getDestinationID());
                    return true;
                }
            }
        } catch (SQLException e) {

            System.err.println("Error adding package: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public Package getPackageDetails(int packageID) {
        String query = "SELECT * FROM packages WHERE package_id = ?";
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection == null) {
                throw new SQLException("Database connection is null. Please check your configuration.");
            }

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, packageID);

                try (ResultSet resultSet = stmt.executeQuery()) {
                    if (resultSet.next()) {

                        Package pkg = new Package(
                                resultSet.getInt("package_id"),
                                resultSet.getInt("destination_id"),
                                resultSet.getString("type"),
                                resultSet.getDouble("price"),
                                resultSet.getString("activities"),
                                resultSet.getString("hotel_options"),
                                resultSet.getString("transport_options")
                        );

                        System.out.println("Retrieved Package: " + pkg.getType() + " for Package ID: " + packageID);
                        return pkg;
                    }
                }
            }
        } catch (SQLException e) {

            System.err.println("Error retrieving package details for Package ID " + packageID + ": " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("No package found with ID: " + packageID);
        return null;
    }

    public List<Package> getPackagesByActivityAndDestination(String activity, int destinationID) {
        List<Package> packages = new ArrayList<>();
        String query = "SELECT * FROM packages WHERE activities LIKE ? AND destination_id = ?";

        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + activity + "%");
            stmt.setInt(2, destinationID);

            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    packages.add(new Package(
                            resultSet.getInt("package_id"),
                            resultSet.getInt("destination_id"),
                            resultSet.getString("type"),
                            resultSet.getDouble("price"),
                            resultSet.getString("activities"),
                            resultSet.getString("hotel_options"),
                            resultSet.getString("transport_options")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return packages;
    }

    public List<Package> getAllPackages() {
        List<Package> packages = new ArrayList<>();
        String query = "SELECT * FROM packages"; 

        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Package pkg = new Package(
                        rs.getInt("package_id"),
                        rs.getInt("destination_id"),
                        rs.getString("type"),
                        rs.getDouble("price"),
                        rs.getString("activities"),
                        rs.getString("hotel_options"),
                        rs.getString("transport_options")
                );
                packages.add(pkg);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching packages: " + e.getMessage());
        }

        return packages;
    }

}

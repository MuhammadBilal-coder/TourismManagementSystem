/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author dell
 */
package com.tms.transport;

import com.tms.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransportManager {

    public List<Transport> getTransportsForPackage(int packageID) {
        List<Transport> transports = new ArrayList<>();
        String query = "SELECT * FROM transport WHERE package_id = ? ORDER BY destination_id";

        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, packageID);

            System.out.println("Executing query: " + query + " with packageID: " + packageID);

            // Execute the query and process the result set
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Transport transport = new Transport(
                            rs.getInt("transport_id"),
                            rs.getString("transport_type"),
                            rs.getInt("capacity"),
                            rs.getDouble("rate_per_day")
                    );

                    transports.add(transport);
                }
            }
        } catch (SQLException e) {

            System.err.println("Error fetching transports for package: " + e.getMessage());
            e.printStackTrace(); 
        }

        return transports; 
    }
}

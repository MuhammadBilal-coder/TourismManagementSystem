/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author dell
 */
package com.tms.user;

import com.tms.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class UserInputHandler {

    public boolean run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n==== User Management ====");
            System.out.println("1. Login");
            System.out.println("2. Sign Up");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    if (loginUser(scanner)) {
                        return true; // Successful login
                    } else {
                        System.out.println("Login failed! Try again.");
                    }
                }
                case 2 ->
                    signUpUser(scanner);
                case 3 -> {
                    System.out.println("Exiting User Management...");
                    return false;
                }
                default ->
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private boolean loginUser(Scanner scanner) {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                System.out.println("Welcome, " + resultSet.getString("name") + "!");
                return true; // Login successful
            } else {
                System.out.println("Invalid email or password!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Login failed
    }

    private void signUpUser(Scanner scanner) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        Connection connection = DatabaseConnection.getConnection();
        String query = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Sign up successful! You can now log in.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

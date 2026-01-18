/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author dell
 */
package com.tms.gui;

import com.tms.destination.Destination;
import com.tms.destination.DestinationManager;
import com.tms.packageinfo.Package;
import com.tms.packageinfo.PackageManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;
import javax.swing.border.LineBorder;

public class DestinationsScreen {

    private int currentIndex = 0;
    private final PackageManager packageManager = new PackageManager();
    private int userID; // Store userID

    // Modified constructor to accept userID
    public DestinationsScreen(int userID) {
        this.userID = userID; // Assign userID passed from login
        JFrame frame = new JFrame("Destinations");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1400, 900);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);

        // Background panel with gradient
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, Color.CYAN, 0, getHeight(), Color.WHITE);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new BorderLayout());

        // Header Label
        JLabel headerLabel = new JLabel("It's Time To Start Your Adventures", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Serif", Font.BOLD, 40));
        headerLabel.setForeground(Color.DARK_GRAY);
        backgroundPanel.add(headerLabel, BorderLayout.NORTH);

        // Fetch destinations using DestinationManager
        DestinationManager destinationManager = new DestinationManager();
        List<Destination> destinations = destinationManager.getAllDestinations();

        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(null);
        displayPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Labels for image, title, and description
        JLabel imageLabel = new JLabel();
        JLabel titleLabel = new JLabel("", SwingConstants.CENTER);
        JLabel descriptionLabel = new JLabel("", SwingConstants.CENTER);

        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.BLACK);
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        descriptionLabel.setForeground(Color.DARK_GRAY);

        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        imageLabel.setBounds(400, 50, 600, 400);
        titleLabel.setBounds(400, 470, 600, 40);
        descriptionLabel.setBounds(300, 520, 800, 100);

        displayPanel.add(imageLabel);
        displayPanel.add(titleLabel);
        displayPanel.add(descriptionLabel);

        // Update display with destinations
        if (!destinations.isEmpty()) {
            updateDisplay(destinations, imageLabel, titleLabel, descriptionLabel, currentIndex);
        } else {
            titleLabel.setText("No Destinations Available");
            descriptionLabel.setText("Please try again later.");
        }

        // Left and right buttons for navigation
        JButton leftButton = new JButton("<");
        JButton rightButton = new JButton(">");

        leftButton.setBounds(50, 250, 100, 100);
        rightButton.setBounds(1250, 250, 100, 100);

        leftButton.addActionListener(e -> {
            if (currentIndex > 0) {
                currentIndex--;
                updateDisplay(destinations, imageLabel, titleLabel, descriptionLabel, currentIndex);
            }
        });

        rightButton.addActionListener(e -> {
            if (currentIndex < destinations.size() - 1) {
                currentIndex++;
                updateDisplay(destinations, imageLabel, titleLabel, descriptionLabel, currentIndex);
            }
        });

        displayPanel.add(leftButton);
        displayPanel.add(rightButton);

        // View Packages button
        JButton viewPackagesButton = new JButton("View Packages");
        viewPackagesButton.setBounds(600, 600, 200, 50); // Position the button
        viewPackagesButton.addActionListener(e -> {
            if (!destinations.isEmpty()) {
                Destination destination = destinations.get(currentIndex);
                displayPackagesForDestination(destination.getDestinationID());
            } else {
                JOptionPane.showMessageDialog(null, "No destinations available.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        displayPanel.add(viewPackagesButton); // Corrected the panel reference

        // Add the display panel to background
        backgroundPanel.add(displayPanel, BorderLayout.CENTER);
        frame.add(backgroundPanel);
        frame.setVisible(true);
    }

    private void updateDisplay(List<Destination> destinations, JLabel imageLabel, JLabel titleLabel, JLabel descriptionLabel, int index) {
        Destination destination = destinations.get(index);
        titleLabel.setText(destination.getName());
        descriptionLabel.setText("<html><div style='text-align: center;'>" + destination.getDescription() + "</div></html>");

        // Load image dynamically based on the file path
        File imageFile = new File(destination.getImagePath());
        if (imageFile.exists()) {
            ImageIcon imageIcon = new ImageIcon(imageFile.getAbsolutePath());
            Image img = imageIcon.getImage().getScaledInstance(600, 400, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(img));
        } else {
            imageLabel.setIcon(null); // Handle missing image gracefully
        }
    }

    private void displayPackagesForDestination(int destinationID) {
        JFrame packageFrame = new JFrame("Packages for Destination ID: " + destinationID);
        packageFrame.setSize(800, 600);
        packageFrame.setLocationRelativeTo(null);

        // Main Panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Top Panel for Dropdown and Back Button
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.setBackground(Color.LIGHT_GRAY);

        // Add Dropdown for Activity Filters
        JComboBox<String> activityFilter = new JComboBox<>(new String[]{"All Activities", "Hiking", "Fishing", "Bonfire", "Kayaking"});
        activityFilter.setFont(new Font("Arial", Font.PLAIN, 14));
        activityFilter.setBackground(new Color(220, 240, 250));
        topPanel.add(activityFilter);

        // Add Live Feedback Label
        JLabel feedbackLabel = new JLabel("Showing all activities");
        feedbackLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        feedbackLabel.setForeground(Color.DARK_GRAY);
        topPanel.add(feedbackLabel);

        // Add Back Button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.setBackground(new Color(255, 200, 200));
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> packageFrame.dispose());
        topPanel.add(backButton);

        // Scrollable Center Panel for Package Details
        JPanel packagePanel = new JPanel();
        packagePanel.setLayout(new BoxLayout(packagePanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(packagePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Add Top Panel and ScrollPane to Main Panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Fetch and Display Packages Based on Activity Filter
        activityFilter.addActionListener(e -> {
            String selectedActivity = activityFilter.getSelectedItem().toString();
            feedbackLabel.setText("Showing results for: " + selectedActivity);

            SwingWorker<List<Package>, Void> worker = new SwingWorker<>() {
                @Override
                protected List<Package> doInBackground() {
                    if (selectedActivity.equals("All Activities")) {
                        return packageManager.getPackagesByDestination(destinationID); // Fetch all packages
                    } else {
                        return packageManager.getPackagesByActivityAndDestination(selectedActivity, destinationID); // Fetch filtered packages
                    }
                }

                @Override
                protected void done() {
                    try {
                        List<Package> packages = get();
                        packagePanel.removeAll(); // Clear previous results

                        if (packages.isEmpty()) {
                            JLabel noDataLabel = new JLabel("No packages available for the selected activity.");
                            noDataLabel.setFont(new Font("Arial", Font.ITALIC, 16));
                            noDataLabel.setHorizontalAlignment(SwingConstants.CENTER);
                            packagePanel.add(noDataLabel);
                        } else {
                            for (Package pkg : packages) {
                                JPanel packageInfoPanel = new JPanel(new GridLayout(4, 1));
                                packageInfoPanel.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLUE, 1), pkg.getType() + " Package"));
                                packageInfoPanel.setBackground(new Color(240, 248, 255));
                                packageInfoPanel.add(new JLabel("Price: " + pkg.getPrice()));
                                packageInfoPanel.add(new JLabel("Activities: " + pkg.getActivities()));
                                packageInfoPanel.add(new JLabel("Hotels: " + pkg.getHotelOptions()));
                                packageInfoPanel.add(new JLabel("Transport: " + pkg.getTransportOptions()));

                                // Add a Booking button for each package
                                JButton bookButton = new JButton("Book Now");
                                bookButton.addActionListener(e1 -> {
                                    // Open the Booking screen for this package
                                    new BookingScreen(userID, pkg); // Pass userID and package
                                });
                                packageInfoPanel.add(bookButton);
                                packagePanel.add(packageInfoPanel);
                            }
                        }

                        packagePanel.revalidate();
                        packagePanel.repaint();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(packageFrame, "Error loading packages: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            };
            worker.execute();
        });

        // Add Main Panel to Frame and Display
        packageFrame.add(mainPanel);
        packageFrame.setVisible(true);
    }
}

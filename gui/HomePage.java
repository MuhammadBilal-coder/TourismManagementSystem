/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author dell
 */
package com.tms.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class HomePage {

    public HomePage(int userID) {

        JFrame frame = new JFrame("Home Page");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                ImageIcon backgroundImage = new ImageIcon("src/homepageimages/m1.jpg");
                Image img = backgroundImage.getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel headerLabel = new JLabel("Welcome to Tourism Management System");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(headerLabel, gbc);

        JButton myBookingsButton = createStyledButton("My Bookings");
        myBookingsButton.addActionListener((ActionEvent e) -> {
            new MyBookingsScreen(userID);
        });
        gbc.gridy = 1;
        backgroundPanel.add(myBookingsButton, gbc);

        JButton exploreButton = createStyledButton("Explore Destinations");
        exploreButton.addActionListener((ActionEvent e) -> {
            new DestinationsScreen(userID);
        });
        gbc.gridy = 2;
        backgroundPanel.add(exploreButton, gbc);

        JButton logoutButton = createStyledButton("Log Out");
        logoutButton.addActionListener((ActionEvent e) -> {
            frame.dispose();
            new LoginForm();
        });
        gbc.gridy = 3;
        backgroundPanel.add(logoutButton, gbc);

        frame.add(backgroundPanel);
        frame.setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setPreferredSize(new Dimension(200, 40));
        button.setFocusPainted(false);
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 149, 237));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 130, 180));
            }
        });
        return button;
    }

}

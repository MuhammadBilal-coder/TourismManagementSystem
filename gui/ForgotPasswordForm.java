/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author dell
 */
package com.tms.gui;

import com.tms.user.UserManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ForgotPasswordForm {

    public ForgotPasswordForm() {
        JFrame frame = new JFrame("Forgot Password");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600); // Initial size for the window
        frame.setResizable(true); // Allow resizing to enable maximize button

        // Set colorful background
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, Color.CYAN, 0, getHeight(), Color.MAGENTA);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel emailLabel = new JLabel("Enter your registered email:");
        emailLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(emailLabel, gbc);

        JTextField emailField = new JTextField(20);
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(emailField, gbc);

        JLabel newPasswordLabel = new JLabel("Enter new password:");
        newPasswordLabel.setForeground(Color.WHITE);
        gbc.gridy = 2;
        panel.add(newPasswordLabel, gbc);

        JPasswordField newPasswordField = new JPasswordField(20);
        gbc.gridy = 3;
        panel.add(newPasswordField, gbc);

        JLabel confirmPasswordLabel = new JLabel("Confirm new password:");
        confirmPasswordLabel.setForeground(Color.WHITE);
        gbc.gridy = 4;
        panel.add(confirmPasswordLabel, gbc);

        JPasswordField confirmPasswordField = new JPasswordField(20);
        gbc.gridy = 5;
        panel.add(confirmPasswordField, gbc);

        JButton resetButton = new JButton("Reset Password");
        gbc.gridy = 6;
        panel.add(resetButton, gbc);

        JButton backButton = new JButton("Back to Login");
        gbc.gridy = 7;
        panel.add(backButton, gbc);

        resetButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String newPassword = new String(newPasswordField.getPassword()).trim();
            String confirmPassword = new String(confirmPasswordField.getPassword()).trim();

            if (email.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(frame, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            UserManager userManager = new UserManager();
            boolean emailExists = userManager.userExists(email);

            if (!emailExists) {
                JOptionPane.showMessageDialog(frame, "Email not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean isUpdated = userManager.updatePassword(email, newPassword);
            if (isUpdated) {
                JOptionPane.showMessageDialog(frame, "Password reset successful! You can now log in with your new password.", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
                new LoginForm();
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to reset password. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            new LoginForm();
        });

        frame.add(panel);
        frame.setVisible(true);
    }
}

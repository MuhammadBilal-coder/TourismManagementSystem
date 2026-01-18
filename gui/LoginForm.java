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

public class LoginForm {

    private LoginListener loginListener;
    private JFrame frame; 

    public LoginForm() {

        frame = new JFrame("Login Form");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500); 
        frame.setResizable(true);

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(135, 206, 250), 0, getHeight(), Color.WHITE);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15); 

        JLabel headerLabel = new JLabel("Have an account?");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 22));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        backgroundPanel.add(headerLabel, gbc);

        JTextField emailField = new JTextField("Email");
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        emailField.setForeground(Color.GRAY);
        emailField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (emailField.getText().equals("Email")) {
                    emailField.setText("");
                    emailField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (emailField.getText().isEmpty()) {
                    emailField.setText("Email");
                    emailField.setForeground(Color.GRAY);
                }
            }
        });
        emailField.setPreferredSize(new Dimension(250, 40));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        backgroundPanel.add(emailField, gbc);

        JPasswordField passField = new JPasswordField("Password");
        passField.setFont(new Font("Arial", Font.PLAIN, 14));
        passField.setForeground(Color.GRAY);
        passField.setEchoChar((char) 0); 
        passField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (String.valueOf(passField.getPassword()).equals("Password")) {
                    passField.setText("");
                    passField.setForeground(Color.BLACK);
                    passField.setEchoChar('●'); // Set echo char to show dots for password
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (String.valueOf(passField.getPassword()).isEmpty()) {
                    passField.setText("Password");
                    passField.setForeground(Color.GRAY);
                    passField.setEchoChar((char) 0); // Show text instead of dots
                }
            }
        });
        passField.setPreferredSize(new Dimension(250, 40));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        backgroundPanel.add(passField, gbc);

        JButton loginButton = new JButton("Sign In");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(new Color(255, 182, 193)); 
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setPreferredSize(new Dimension(250, 50));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        backgroundPanel.add(loginButton, gbc);

        JCheckBox rememberMe = new JCheckBox("Remember Me");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        backgroundPanel.add(rememberMe, gbc);

        // Forgot Password link
        JLabel forgotPassword = new JLabel("Forgot Password?");
        forgotPassword.setFont(new Font("Arial", Font.PLAIN, 12));
        forgotPassword.setForeground(new Color(100, 100, 255)); // Blue text
        forgotPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forgotPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                frame.dispose(); 
                new ForgotPasswordForm(); 
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        backgroundPanel.add(forgotPassword, gbc);

        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.setFont(new Font("Arial", Font.BOLD, 14));
        createAccountButton.setBackground(new Color(135, 206, 235));
        createAccountButton.setForeground(Color.WHITE);
        createAccountButton.setFocusPainted(false);
        createAccountButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        createAccountButton.setPreferredSize(new Dimension(250, 40));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        backgroundPanel.add(createAccountButton, gbc);

        createAccountButton.addActionListener(e -> {
            frame.dispose(); 
            new RegistrationForm(); 
        });
// addActionListener(e -> {...}) ka kaam hai button ke click hone par event ko handle karna. e -> {...} ek lambda expression hai jisme button press hone ke baad hone wala kaam likha jata hai, jaise email aur password validate karna aur user ko result dikhana.
        loginButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = new String(passField.getPassword()).trim();

            if (email.isEmpty() || password.isEmpty() || email.equals("Email") || password.equals("Password")) {
                JOptionPane.showMessageDialog(frame, "Email and Password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate login using UserManager
            UserManager userManager = new UserManager();
            boolean isValid = userManager.loginUser(email, password);

            if (isValid) {
                JOptionPane.showMessageDialog(frame, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose(); // Close login form

                int userID = userManager.getUserID(email); // Fetch userID after successful login
                new HomePage(userID); // Open the Home Page
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid email or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.add(backgroundPanel);

        frame.setVisible(true);
    }

    public interface LoginListener {

        void onLoginSuccess();

        void onLoginFailed();
    }
}

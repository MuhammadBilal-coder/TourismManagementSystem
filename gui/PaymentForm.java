/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author dell
 */
// PaymentForm.java
// PaymentForm.java
package com.tms.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.YearMonth;

public class PaymentForm extends JDialog { 

    private boolean paymentSuccess = false;
    private final double amount;
    private final int bookingID;

    public PaymentForm(double amount, int bookingID, JFrame parent) { // Accept parent frame for modal behavior
        super(parent, "Payment Gateway", true);  // Call JDialog's constructor for modal behavior
        this.amount = amount;
        this.bookingID = bookingID;
        initializeUI();
    }

    private void initializeUI() {
        setSize(400, 500);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel();
        JLabel lblHeader = new JLabel("Enter Payment Details");
        lblHeader.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(lblHeader);
        add(headerPanel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Cardholder Name:"));
        JTextField txtCardHolder = new JTextField();
        formPanel.add(txtCardHolder);

        formPanel.add(new JLabel("Card Number (16 digits):"));
        JTextField txtCardNumber = new JTextField();
        formPanel.add(txtCardNumber);

        formPanel.add(new JLabel("Expiry Date (MM/YY):"));
        JTextField txtExpiry = new JTextField();
        formPanel.add(txtExpiry);

        formPanel.add(new JLabel("CVV (3 digits):"));
        JTextField txtCVV = new JTextField();
        formPanel.add(txtCVV);

        formPanel.add(new JLabel("Email:"));
        JTextField txtEmail = new JTextField();
        formPanel.add(txtEmail);

        add(formPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnSubmit = new JButton("Submit");
        JButton btnCancel = new JButton("Cancel");

        // Submit Button Action
        btnSubmit.addActionListener((ActionEvent e) -> {
            if (validateFields(txtCardHolder, txtCardNumber, txtExpiry, txtCVV, txtEmail)) {
                paymentSuccess = true;
                JOptionPane.showMessageDialog(this, "Payment successful! Amount: " + amount, "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();  // Close the dialog after successful payment
            }
        });

        // Cancel Button Action
        btnCancel.addActionListener((ActionEvent e) -> {
            paymentSuccess = false;
            JOptionPane.showMessageDialog(this, "Payment was canceled.", "Canceled", JOptionPane.WARNING_MESSAGE);
            dispose();  // Close the dialog after cancellation
        });

        buttonPanel.add(btnSubmit);
        buttonPanel.add(btnCancel);
        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(getParent());  // Center the dialog relative to the parent frame
    }

    private boolean validateFields(JTextField cardHolder, JTextField cardNumber, JTextField expiry, JTextField cvv, JTextField email) {
        StringBuilder validationErrors = new StringBuilder();

        if (cardHolder.getText().trim().isEmpty() || !cardHolder.getText().matches("[a-zA-Z ]+")) {
            validationErrors.append("Invalid cardholder name.\n");
        }
        if (!cardNumber.getText().matches("\\d{16}")) {
            validationErrors.append("Invalid 16-digit card number.\n");
        }
        if (!expiry.getText().matches("(0[1-9]|1[0-2])/\\d{2}")) {
            validationErrors.append("Invalid expiry date (MM/YY format).\n");
        } else {
            String[] parts = expiry.getText().split("/");
            int month = Integer.parseInt(parts[0]);
            int year = 2000 + Integer.parseInt(parts[1]);
            if (YearMonth.now().isAfter(YearMonth.of(year, month))) {
                validationErrors.append("Card has expired.\n");
            }
        }
        if (!cvv.getText().matches("\\d{3}")) {
            validationErrors.append("Invalid 3-digit CVV.\n");
        }
        if (!email.getText().matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            validationErrors.append("Invalid email address.\n");
        }

        if (validationErrors.length() > 0) {
            JOptionPane.showMessageDialog(this, validationErrors.toString(), "Validation Errors", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    public boolean isPaymentSuccessful() {
        return paymentSuccess;  // Return the payment status
    }
}

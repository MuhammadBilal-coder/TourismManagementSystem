/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author dell
 */
package com.tms.gui;

import com.tms.booking.Booking;
import com.tms.booking.BookingHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.List;

public class MyBookingsScreen {

    public MyBookingsScreen(int userID) {
        BookingHandler bookingHandler = new BookingHandler();

        // Create the main frame
        JFrame frame = new JFrame("My Bookings");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTable bookingsTable = createBookingsTable(userID, bookingHandler);
        JScrollPane scrollPane = new JScrollPane(bookingsTable);

        JButton cancelButton = new JButton("Cancel Selected Booking");
        cancelButton.addActionListener((ActionEvent e) -> {
            int selectedRow = bookingsTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "Please select a booking to cancel.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int bookingID = (int) bookingsTable.getValueAt(selectedRow, 0);
            if (bookingHandler.cancelBooking(bookingID)) {
                JOptionPane.showMessageDialog(frame, "Booking canceled successfully.");
                ((DefaultTableModel) bookingsTable.getModel()).removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to cancel booking.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Add components to the panel
        panel.add(scrollPane);
        panel.add(cancelButton);

        // Add the panel to the frame
        frame.add(panel);
        frame.setVisible(true);
    }

    private JTable createBookingsTable(int userID, BookingHandler bookingHandler) {
        String[] columnNames = {"Booking ID", "Hotel Name", "Transport Name", "Status", "Booking Date"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        List<Booking> bookings = bookingHandler.getBookingsByUser(userID);
        for (Booking booking : bookings) {
            tableModel.addRow(new Object[]{
                booking.getBookingID(),
                booking.getHotelName(),
                booking.getTransportName(),
                booking.getStatus(),
                booking.getBookingDate()
            });
        }

        JTable table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return table;
    }
}

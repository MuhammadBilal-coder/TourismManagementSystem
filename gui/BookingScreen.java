/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author dell
 */
package com.tms.gui;

import com.tms.booking.BookingHandler;
import com.tms.hotel.Hotel;
import com.tms.hotel.HotelManager;
import com.tms.packageinfo.Package;
import com.tms.transport.Transport;
import com.tms.transport.TransportManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class BookingScreen {

    public BookingScreen(int userID, Package selectedPackage) {
        JFrame frame = new JFrame("Book a Package");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel hotelLabel = new JLabel("Select Hotel:");
        JComboBox<Hotel> hotelDropdown = new JComboBox<>();
        populateHotelDropdownAsync(hotelDropdown, selectedPackage);

        JLabel transportLabel = new JLabel("Select Transport:");
        JComboBox<Transport> transportDropdown = new JComboBox<>();
        populateTransportDropdownAsync(transportDropdown, selectedPackage);

        JButton bookButton = new JButton("Book Now");
        bookButton.addActionListener((ActionEvent e) -> handleBookNow(userID, selectedPackage, hotelDropdown, transportDropdown, frame));

        panel.add(hotelLabel);
        panel.add(hotelDropdown);
        panel.add(transportLabel);
        panel.add(transportDropdown);
        panel.add(bookButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void populateHotelDropdownAsync(JComboBox<Hotel> hotelDropdown, Package selectedPackage) {
        new SwingWorker<List<Hotel>, Void>() {
            @Override
            protected List<Hotel> doInBackground() throws Exception {
                return new HotelManager().getHotelsForPackage(selectedPackage.getPackageID());
            }

            @Override
            protected void done() {
                try {
                    List<Hotel> hotels = get();
                    if (hotels.isEmpty()) {
                        hotelDropdown.addItem(new Hotel(0, "No Hotels Available", "N/A", 0.0, 0));
                    } else {
                        hotels.forEach(hotelDropdown::addItem);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error fetching hotels: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }

    private void populateTransportDropdownAsync(JComboBox<Transport> transportDropdown, Package selectedPackage) {
        new SwingWorker<List<Transport>, Void>() {
            @Override
            protected List<Transport> doInBackground() throws Exception {
                return new TransportManager().getTransportsForPackage(selectedPackage.getPackageID());
            }

            @Override
            protected void done() {
                try {
                    List<Transport> transports = get();
                    if (transports.isEmpty()) {
                        transportDropdown.addItem(new Transport(0, "No Transport Available", 0, 0.0));
                    } else {
                        transports.forEach(transportDropdown::addItem);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error fetching transports: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }

    private void handleBookNow(int userID, Package selectedPackage, JComboBox<Hotel> hotelDropdown, JComboBox<Transport> transportDropdown, JFrame frame) {
        Hotel selectedHotel = (Hotel) hotelDropdown.getSelectedItem();
        Transport selectedTransport = (Transport) transportDropdown.getSelectedItem();

        if (selectedHotel == null || selectedHotel.getHotelID() == 0) {
            JOptionPane.showMessageDialog(frame, "Please select a valid hotel.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (selectedTransport == null || selectedTransport.getTransportID() == 0) {
            JOptionPane.showMessageDialog(frame, "Please select a valid transport.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                BookingHandler bookingHandler = new BookingHandler();
                return bookingHandler.isAlreadyBooked(userID, selectedPackage.getPackageID());
            }

            @Override
            protected void done() {
                try {
                    Boolean alreadyBooked = get();
                    if (Boolean.TRUE.equals(alreadyBooked)) {
                        JOptionPane.showMessageDialog(frame, "This package is already booked.", "Warning", JOptionPane.WARNING_MESSAGE);
                    } else {
                        openPaymentForm(selectedPackage, userID, selectedHotel, selectedTransport, frame);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }

    private void openPaymentForm(Package selectedPackage, int userID, Hotel selectedHotel, Transport selectedTransport, JFrame parentFrame) {

        PaymentForm paymentForm = new PaymentForm(selectedPackage.getPrice(), 0, parentFrame);
        paymentForm.setVisible(true);  

        boolean paymentSuccess = paymentForm.isPaymentSuccessful();  
        handlePaymentResult(paymentSuccess, new BookingHandler(), userID, selectedPackage, selectedHotel, selectedTransport, selectedPackage.getPrice());
    }

    private void handlePaymentResult(boolean paymentSuccess, BookingHandler bookingHandler, int userID, Package selectedPackage, Hotel selectedHotel, Transport selectedTransport, double packagePrice) {
        if (paymentSuccess) {
            new SwingWorker<Boolean, Void>() {
                @Override
                protected Boolean doInBackground() throws Exception {
                    return bookingHandler.saveBookingAfterPayment(userID, selectedPackage.getPackageID(), selectedHotel.getHotelID(), selectedTransport.getTransportID(), packagePrice);
                }

                @Override
                protected void done() {
                    try {
                        boolean success = get();
                        if (success) {
                            JOptionPane.showMessageDialog(null, "Booking saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "An error occurred while saving your booking.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }.execute();
        } else {
            JOptionPane.showMessageDialog(null, "Payment failed. Booking not completed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

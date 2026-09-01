package airline.gui.customer;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import airline.customer.Customer;
import airline.customer.CustomerManager;
import airline.util.ImagePanel;
import airline.customer.Booking;
import airline.customer.Payment;
import airline.flight.Flight;

public class CustomerUI {
    private JFrame customerFrame;
    private Customer customerLoggedIn;
    private JButton homeBtn;
    private JFrame parentFrame;
    private JPanel footerPanel;

    public CustomerUI(JFrame parentFrame, Customer customerLoggedIn, JButton homeBtn, JPanel footerPanel,
            CustomerManager customerManager) {
        this.parentFrame = parentFrame;
        this.customerLoggedIn = customerLoggedIn;
        this.homeBtn = homeBtn;
        this.footerPanel = footerPanel;

    }

    public void showCustomerPage() {
        customerFrame = new JFrame("Customer Panel");
        JPanel backgroundPanel = new ImagePanel("resources/images/ticket.png");
        backgroundPanel.setLayout(new BorderLayout());
        customerFrame.add(backgroundPanel, BorderLayout.CENTER);

        // ===== NORTH PANEL =====
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
        northPanel.setBackground(new Color(230, 230, 250));

        // ---- Top sub-panel ----
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(235, 235, 255));

        JLabel welcomeLabel = new JLabel("SkyBlue");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 34));
        welcomeLabel.setForeground(new Color(0, 102, 204));

        JLabel welcomeCustomer = new JLabel("Welcome " + customerLoggedIn.getName());
        welcomeCustomer.setFont(new Font("Arial", Font.PLAIN, 25));
        welcomeCustomer.setForeground(new Color(0, 0, 0));

        // bottom
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setBackground(new Color(220, 220, 240));

        JLabel availabelFlights = new JLabel("View Available Flights"); // display krni hein sarri flights
        availabelFlights.setFont(new Font("Arial", Font.PLAIN, 18));
        availabelFlights.setForeground(new Color(70, 130, 180));
        availabelFlights.setCursor(new Cursor(Cursor.HAND_CURSOR)); // show hand on hover

        // Add mouse listener for click
        availabelFlights.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                parentFrame.setVisible(false);
                new FlightGridViewer4(false);
            }
        });

        JLabel bookFlight = new JLabel("Book Flight");
        bookFlight.setFont(new Font("Arial", Font.PLAIN, 18));
        bookFlight.setForeground(new Color(70, 130, 180));
        bookFlight.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add mouse listener for click
        bookFlight.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FlightGridViewer4 viewer = new FlightGridViewer4(true);
                viewer.setCustomerUI(CustomerUI.this);
            }
        });

        JLabel cancelBooking = new JLabel("Cancel Booking");
        cancelBooking.setFont(new Font("Arial", Font.PLAIN, 18));
        cancelBooking.setForeground(new Color(70, 130, 180));
        cancelBooking.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add mouse listener for click
        cancelBooking.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showCancelBooking();
            }
        });

        JLabel viewMyTicket = new JLabel("My Ticket");
        viewMyTicket.setFont(new Font("Arial", Font.PLAIN, 18));
        viewMyTicket.setForeground(new Color(70, 130, 180));
        viewMyTicket.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add mouse listener for click
        viewMyTicket.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showMyTicket();
            }
        });

        JLabel viewReservations = new JLabel("My Reservation");
        viewReservations.setFont(new Font("Arial", Font.PLAIN, 18));
        viewReservations.setForeground(new Color(70, 130, 180));
        viewReservations.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add mouse listener for click
        viewReservations.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showMyReservations();
            }
        });

        JLabel checkPaymentStatus = new JLabel("Payment Status");
        checkPaymentStatus.setFont(new Font("Arial", Font.PLAIN, 18));
        checkPaymentStatus.setForeground(new Color(70, 130, 180));
        checkPaymentStatus.setCursor(new Cursor(Cursor.HAND_CURSOR));

        checkPaymentStatus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showPaymentStatus();
            }
        });

        JLabel bookingHistory = new JLabel("Booking History");
        bookingHistory.setFont(new Font("Arial", Font.PLAIN, 18));
        bookingHistory.setForeground(new Color(70, 130, 180));
        bookingHistory.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add mouse listener for click
        bookingHistory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showBookingHistory();
            }
        });

        JLabel checkIn = new JLabel("Check In");
        checkIn.setFont(new Font("Arial", Font.PLAIN, 18));
        checkIn.setForeground(new Color(70, 130, 180));
        checkIn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add mouse listener for click
        checkIn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showCheckInOptions();
            }
        });



        bottomPanel.add(Box.createHorizontalStrut(30));
        bottomPanel.add(availabelFlights);
        bottomPanel.add(Box.createHorizontalStrut(30));
        bottomPanel.add(bookFlight);
        bottomPanel.add(Box.createHorizontalStrut(30));
        bottomPanel.add(viewMyTicket);
        bottomPanel.add(Box.createHorizontalStrut(30));
        bottomPanel.add(viewReservations);
        bottomPanel.add(Box.createHorizontalStrut(30));
        bottomPanel.add(checkIn);
        bottomPanel.add(Box.createHorizontalStrut(30));
        bottomPanel.add(checkPaymentStatus);
        bottomPanel.add(Box.createHorizontalStrut(30));
        bottomPanel.add(cancelBooking);
        bottomPanel.add(Box.createHorizontalStrut(30));
        bottomPanel.add(bookingHistory);
        bottomPanel.add(Box.createHorizontalStrut(170));
        bottomPanel.add(homeBtn);

        topPanel.add(welcomeLabel);
        topPanel.add(Box.createHorizontalStrut(450));
        topPanel.add(welcomeCustomer);
        topPanel.add(Box.createHorizontalStrut(620));

        northPanel.add(topPanel);
        northPanel.add(bottomPanel);

        customerFrame.add(footerPanel, BorderLayout.SOUTH);
        customerFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        customerFrame.add(northPanel, BorderLayout.NORTH);
        customerFrame.setVisible(true);
    }

    private JPanel createInfoRow(String label, Object value) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row.setBackground(Color.WHITE);
        row.setMaximumSize(new Dimension(850, 25));

        JLabel lblLabel = new JLabel("    " + label);
        lblLabel.setFont(new Font("Arial", Font.BOLD, 13));
        lblLabel.setPreferredSize(new Dimension(150, 20));

        JLabel lblValue = new JLabel(value != null ? value.toString() : "N/A");
        lblValue.setFont(new Font("Arial", Font.PLAIN, 13));

        row.add(lblLabel);
        row.add(lblValue);
        return row;
    }

    public void bookingHandler(Flight f) {
        // icon ka actionlistener call krna hyyy------------------------------display
        // editable form
        if (!f.isDomestic()) {
            showVisaAndPassportFrame(f);
        } else {
            showSeatSelectionFrame(f);
        }
    }

    private void showSeatSelectionFrame(Flight f) {
        JFrame seatFrame = new JFrame("Select Seat");
        seatFrame.setSize(400, 250);
        seatFrame.setLocationRelativeTo(null);
        seatFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(240, 246, 255));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Select Seat Type");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        JPanel seatPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        seatPanel.setBackground(new Color(240, 246, 255));
        JLabel seatLabel = new JLabel("Select Seat Type: ");
        seatLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        JRadioButton economyBtn = new JRadioButton("Economy");
        JRadioButton businessBtn = new JRadioButton("Business");

        economyBtn.setBackground(new Color(240, 246, 255));
        businessBtn.setBackground(new Color(240, 246, 255));
        economyBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        businessBtn.setFont(new Font("Arial", Font.PLAIN, 15));

        ButtonGroup seatGroup = new ButtonGroup();
        seatGroup.add(economyBtn);
        seatGroup.add(businessBtn);

        seatPanel.add(seatLabel);
        seatPanel.add(economyBtn);
        seatPanel.add(businessBtn);

        JPanel submitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        submitPanel.setBackground(new Color(240, 246, 255));
        JButton submitBtn = new JButton("Continue to Payment");
        submitBtn.setFont(new Font("Arial", Font.BOLD, 16));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setBackground(new Color(70, 130, 180));
        submitBtn.setFocusPainted(false);
        submitPanel.add(submitBtn);

        mainPanel.add(titlePanel);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(seatPanel);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(submitPanel);

        seatFrame.add(mainPanel);
        seatFrame.setVisible(true);

        submitBtn.addActionListener(e -> {
            String seatClass = economyBtn.isSelected() ? "Economy" : businessBtn.isSelected() ? "Business" : "";

            if (seatClass.isEmpty()) {
                JOptionPane.showMessageDialog(seatFrame, "Please select a seat type!",
                        "Seat Required", JOptionPane.ERROR_MESSAGE);
                return;
            }

            seatFrame.dispose();
            showBookingSummaryAndPayment(f, seatClass);
        });
    }

    private void showVisaAndPassportFrame(Flight f) {
        JFrame docFrame = new JFrame("Enter Travel Documents");
        docFrame.setSize(480, 380);
        docFrame.setLocationRelativeTo(null);
        docFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(240, 246, 255));

        // Title
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Travel Documents & Seat Selection");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        // Passport
        JPanel passportPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passportPanel.setBackground(new Color(240, 246, 255));
        JLabel passportLabel = new JLabel("Passport Number: ");
        passportLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JTextField passportField = new JTextField(20);
        passportPanel.add(passportLabel);
        passportPanel.add(Box.createHorizontalStrut(10));
        passportPanel.add(passportField);

        // Visa
        JPanel visaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        visaPanel.setBackground(new Color(240, 246, 255));
        JLabel visaLabel = new JLabel("Visa Number: ");
        visaLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JTextField visaField = new JTextField(20);
        visaPanel.add(visaLabel);
        visaPanel.add(Box.createHorizontalStrut(10));
        visaPanel.add(visaField);

        // Seat Type Panel
        JPanel seatPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        seatPanel.setBackground(new Color(240, 246, 255));
        JLabel seatLabel = new JLabel("Select Seat Type: ");
        seatLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        JRadioButton economyBtn = new JRadioButton("Economy");
        JRadioButton businessBtn = new JRadioButton("Business");

        economyBtn.setBackground(new Color(240, 246, 255));
        businessBtn.setBackground(new Color(240, 246, 255));

        economyBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        businessBtn.setFont(new Font("Arial", Font.PLAIN, 15));

        ButtonGroup seatGroup = new ButtonGroup();
        seatGroup.add(economyBtn);
        seatGroup.add(businessBtn);

        seatPanel.add(seatLabel);
        seatPanel.add(Box.createHorizontalStrut(10));
        seatPanel.add(economyBtn);
        seatPanel.add(businessBtn);

        // Submit
        JPanel submitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        submitPanel.setBackground(new Color(240, 246, 255));
        JButton submitBtn = new JButton("Submit");
        submitBtn.setFont(new Font("Arial", Font.BOLD, 16));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setBackground(new Color(70, 130, 180));
        submitBtn.setFocusPainted(false);
        submitPanel.add(submitBtn);

        // Add to Final
        mainPanel.add(titlePanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(passportPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(visaPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(seatPanel);
        mainPanel.add(Box.createVerticalStrut(25));
        mainPanel.add(submitPanel);

        docFrame.add(mainPanel);
        docFrame.setVisible(true);

        // Handle submit
        submitBtn.addActionListener(e -> {
            String passport = passportField.getText().trim();
            String visa = visaField.getText().trim();
            String seatClass = economyBtn.isSelected() ? "Economy" : businessBtn.isSelected() ? "Business" : "";

            // All validations...
            if (passport.isEmpty()) {
                JOptionPane.showMessageDialog(docFrame, "Passport number cannot be empty!",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!customerLoggedIn.isPassportValid(passport)) {
                JOptionPane.showMessageDialog(docFrame,
                        "Invalid Passport Format!\n\nRules:\n• 8-9 characters long\n• Only capital letters (A-Z) and numbers (0-9)\n• Example: AB1234567",
                        "Invalid Passport", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (visa.isEmpty()) {
                JOptionPane.showMessageDialog(docFrame, "Visa number cannot be empty!",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!customerLoggedIn.isVisaValid(visa)) {
                JOptionPane.showMessageDialog(docFrame,
                        "Invalid Visa Format!\n\nRules:\n• 6-10 characters long\n• Only capital letters (A-Z) and numbers (0-9)\n• Example: V12345ABC",
                        "Invalid Visa", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (seatClass.isEmpty()) {
                JOptionPane.showMessageDialog(docFrame, "Please select a seat type!",
                        "Seat Required", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Set passport and visa
            customerLoggedIn.setPassportNumber(passport);
            customerLoggedIn.setVisaNumber(visa);

            docFrame.dispose();
            // CHANGE: Now show booking summary and payment
            showBookingSummaryAndPayment(f, seatClass);
        });

    }

    private boolean hasAlreadyBooked(Flight f) {
        if (customerLoggedIn.getBookings() == null)
            return false;

        for (Booking booking : customerLoggedIn.getBookings()) {
            if (booking.getStatus().equals("Confirmed") &&
                    booking.getFlight().getFlightNo().equals(f.getFlightNo())) {
                return true;
            }
        }
        return false;
    }

    private void showBookingSummaryAndPayment(Flight f, String seatClass) {
        if (hasAlreadyBooked(f)) {
            JOptionPane.showMessageDialog(null,
                    "You have already booked this flight!\nPlease check your reservations.",
                    "Duplicate Booking",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        JFrame summaryFrame = new JFrame("Booking Summary");
        summaryFrame.setSize(500, 600);
        summaryFrame.setLocationRelativeTo(null);
        summaryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(240, 246, 255));

        // Title
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Booking Summary");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        // Passenger Info (Read-only)
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(new Color(240, 246, 255));

        JLabel infoTitle = new JLabel("PASSENGER INFORMATION");
        infoTitle.setFont(new Font("Arial", Font.BOLD, 16));
        infoTitle.setForeground(new Color(70, 130, 180));
        infoPanel.add(infoTitle);
        infoPanel.add(Box.createVerticalStrut(10));

        infoPanel.add(createInfoRow("Name:", customerLoggedIn.getName()));
        infoPanel.add(createInfoRow("Date of Birth:", customerLoggedIn.getDateOfBirth()));
        infoPanel.add(createInfoRow("CNIC:", customerLoggedIn.getCNIC()));
        infoPanel.add(createInfoRow("Email:", customerLoggedIn.getEmail()));
        infoPanel.add(createInfoRow("Phone:", customerLoggedIn.getPhoneNumber()));

        if (!f.isDomestic()) {
            infoPanel.add(createInfoRow("Passport:", customerLoggedIn.getPassportNumber()));
            infoPanel.add(createInfoRow("Visa:", customerLoggedIn.getVisaNumber()));
        }

        infoPanel.add(Box.createVerticalStrut(10));

        // Flight Info
        JLabel flightTitle = new JLabel("FLIGHT INFORMATION");
        flightTitle.setFont(new Font("Arial", Font.BOLD, 16));
        flightTitle.setForeground(new Color(70, 130, 180));
        infoPanel.add(flightTitle);
        infoPanel.add(Box.createVerticalStrut(10));

        infoPanel.add(createInfoRow("Flight:", f.getFlightNo()));
        infoPanel.add(createInfoRow("From:", f.getOrigin()));
        infoPanel.add(createInfoRow("To:", f.getDestination()));
        infoPanel.add(createInfoRow("Seat Class:", seatClass));

        infoPanel.add(Box.createVerticalStrut(10));

        // Fare Breakdown
        double flightPrice = f.getPrice();
        double bookingFee = f.isDomestic() ? 500 : 1000;
        double businessClassPrice = 10000;

        // Calculate total fare (make it final for use in lambda)
        final double totalFare;
        if (seatClass.equalsIgnoreCase("Business")) {
            totalFare = flightPrice + bookingFee + businessClassPrice;
        } else {
            totalFare = flightPrice + bookingFee;
        }

        JLabel fareTitle = new JLabel("FARE BREAKDOWN");
        fareTitle.setFont(new Font("Arial", Font.BOLD, 16));
        fareTitle.setForeground(new Color(70, 130, 180));
        infoPanel.add(fareTitle);
        infoPanel.add(Box.createVerticalStrut(10));

        infoPanel.add(createInfoRow("Flight Price:", "PKR " + flightPrice));
        infoPanel.add(createInfoRow("Booking Fee:", "PKR " + bookingFee));
        if (seatClass.equalsIgnoreCase("Business"))
            infoPanel.add(createInfoRow("Business Seat Price:", "PKR " + businessClassPrice));
        infoPanel.add(createInfoRow("Total Fare:", "PKR " + totalFare));

        // Cancellation Policy
        JLabel policyLabel = new JLabel("* Cancellations allowed up to 24 hours before departure");
        policyLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        policyLabel.setForeground(Color.RED);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(policyLabel);

        // Pay Button
        JPanel payPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        payPanel.setBackground(new Color(240, 246, 255));
        JButton payBtn = new JButton("Pay Amount");
        payBtn.setFont(new Font("Arial", Font.BOLD, 16));
        payBtn.setForeground(Color.WHITE);
        payBtn.setBackground(new Color(0, 128, 0));
        payBtn.setFocusPainted(false);
        payPanel.add(payBtn);

        mainPanel.add(titlePanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(infoPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(payPanel);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        summaryFrame.add(scrollPane);
        summaryFrame.setVisible(true);

        payBtn.addActionListener(e -> {
            summaryFrame.dispose();
            showPaymentMethodFrame(f, seatClass, totalFare);
        });
    }

    private void showPaymentMethodFrame(Flight f, String seatClass, double totalFare) {
        JFrame paymentFrame = new JFrame("Payment");
        paymentFrame.setSize(450, 400);
        paymentFrame.setLocationRelativeTo(null);
        paymentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(240, 246, 255));

        // Title
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Payment Method");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        // Amount Display Panel (will be updated)
        JPanel amountPanel = new JPanel();
        amountPanel.setLayout(new BoxLayout(amountPanel, BoxLayout.Y_AXIS));
        amountPanel.setBackground(new Color(240, 246, 255));

        JLabel originalAmountLabel = new JLabel("Original Amount: PKR " + totalFare);
        originalAmountLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        originalAmountLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel discountLabel = new JLabel("");
        discountLabel.setFont(new Font("Arial", Font.BOLD, 16));
        discountLabel.setForeground(new Color(0, 128, 0));
        discountLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel finalAmountLabel = new JLabel("Total Amount: PKR " + totalFare);
        finalAmountLabel.setFont(new Font("Arial", Font.BOLD, 18));
        finalAmountLabel.setForeground(new Color(0, 128, 0));
        finalAmountLabel.setHorizontalAlignment(JLabel.CENTER);

        amountPanel.add(originalAmountLabel);
        amountPanel.add(discountLabel);
        amountPanel.add(finalAmountLabel);

        // Payment Method Selection
        JPanel methodPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        methodPanel.setBackground(new Color(240, 246, 255));
        JLabel methodLabel = new JLabel("Payment Method: ");
        methodLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        String[] methods = { "EasyPaisa", "JazzCash", "Bank Transfer" };
        JComboBox<String> methodBox = new JComboBox<>(methods);
        methodBox.setFont(new Font("Arial", Font.PLAIN, 16));
        methodPanel.add(methodLabel);
        methodPanel.add(methodBox);

        // Promo Code
        JPanel promoCodePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        promoCodePanel.setBackground(new Color(240, 246, 255));
        JButton promoCodeBtn = new JButton("Use PromoCode");
        promoCodeBtn.setFont(new Font("Arial", Font.BOLD, 14));
        promoCodeBtn.setForeground(Color.WHITE);
        promoCodeBtn.setBackground(new Color(0, 128, 0));
        promoCodeBtn.setFocusPainted(false);
        promoCodePanel.add(promoCodeBtn);

        // Track discount values
        final double[] appliedDiscount = { 0.0 };
        final double[] discountPercentage = { 0.0 };

        promoCodeBtn.addActionListener(e -> {
            String promoCode = customerLoggedIn.getPromoCode();
            if (promoCode == null || promoCode.isEmpty()) {
                JOptionPane.showMessageDialog(paymentFrame,
                        "You have collected no promo code to get discount!",
                        "No promo code collected", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            discountPercentage[0] = getDiscountFromFile(promoCode);
            if (discountPercentage[0] == 0) {
                JOptionPane.showMessageDialog(paymentFrame,
                        "Invalid promo code!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            appliedDiscount[0] = totalFare * (discountPercentage[0] / 100);
            double newTotal = totalFare - appliedDiscount[0];

            discountLabel.setText("Discount (" + (int) discountPercentage[0] + "%): -PKR " +
                    String.format("%.2f", appliedDiscount[0]));
            finalAmountLabel.setText("Total Amount: PKR " + String.format("%.2f", newTotal));

            JOptionPane.showMessageDialog(paymentFrame,
                    "Promo code applied successfully!\n" +
                            "Discount: " + (int) discountPercentage[0] + "%\n" +
                            "You saved: PKR " + String.format("%.2f", appliedDiscount[0]),
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            // NULL THE PROMO CODE AFTER USE
            customerLoggedIn.setPromoCode(null);

            promoCodeBtn.setEnabled(false);
        });

        // Account Details
        JPanel accountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        accountPanel.setBackground(new Color(240, 246, 255));
        JLabel accountLabel = new JLabel("Account/Mobile: ");
        accountLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JTextField accountField = new JTextField(20);
        accountField.setToolTipText("Enter IBAN for Bank or Mobile Number for EasyPaisa/JazzCash");
        accountPanel.add(accountLabel);
        accountPanel.add(accountField);

        // Submit Payment
        JPanel submitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        submitPanel.setBackground(new Color(240, 246, 255));
        JButton submitBtn = new JButton("Confirm Payment");
        submitBtn.setFont(new Font("Arial", Font.BOLD, 16));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setBackground(new Color(0, 128, 0));
        submitBtn.setFocusPainted(false);
        submitPanel.add(submitBtn);

        mainPanel.add(titlePanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(amountPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(methodPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(accountPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(promoCodePanel);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(submitPanel);

        paymentFrame.add(mainPanel);
        paymentFrame.setVisible(true);

        submitBtn.addActionListener(e -> {
            String method = (String) methodBox.getSelectedItem();
            String account = accountField.getText().trim();

            if (account.isEmpty()) {
                JOptionPane.showMessageDialog(paymentFrame,
                        "Please enter account details!",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate based on method
            if (method.equals("Bank Transfer")) {
                if (!account.matches("PK\\d{2}[A-Z]{4}\\d{16}")) {
                    JOptionPane.showMessageDialog(paymentFrame,
                            "Invalid IBAN format!\nExample: PK36SCBL0000001123456702",
                            "Invalid IBAN", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                if (!account.matches("03\\d{9}")) {
                    JOptionPane.showMessageDialog(paymentFrame,
                            "Invalid mobile number!\nFormat: 03XXXXXXXXX",
                            "Invalid Number", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Check aircraft and seat availability
            if (f.getAircraft() == null || f.getAircraft().getSeats().isEmpty()) {
                JOptionPane.showMessageDialog(paymentFrame,
                        "No seats available for this flight!",
                        "Booking Failed", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Calculate final amount after discount
            double finalAmount = totalFare - appliedDiscount[0];

            // Create booking
            String bookingId = "BK" + System.currentTimeMillis();
            Booking booking = new Booking(bookingId, f);

            // Try to assign seat
            if (booking.setSeat(f.getAircraft(), seatClass)) {
                // Create payment with discounted amount
                String paymentId = "PAY" + System.currentTimeMillis();
                Payment payment = new Payment(paymentId, method, account, finalAmount);
                booking.setPayment(payment);
                booking.setStatus("Confirmed");
                booking.setBookingDate();

                // Add to customer bookings
                customerLoggedIn.addBooking(booking);

                String message = "Payment Successful!\nBooking Confirmed\nBooking ID: " + bookingId;
                if (appliedDiscount[0] > 0) {
                    message += "\n\nDiscount Applied: " + (int) discountPercentage[0] + "%\n" +
                            "Amount Saved: PKR " + String.format("%.2f", appliedDiscount[0]) + "\n" +
                            "Final Amount Paid: PKR " + String.format("%.2f", finalAmount);
                }

                JOptionPane.showMessageDialog(paymentFrame, message, "Success", JOptionPane.INFORMATION_MESSAGE);

                paymentFrame.dispose();
            } else {
                booking.setStatus("Cancelled");
                customerLoggedIn.addBooking(booking);
                JOptionPane.showMessageDialog(paymentFrame,
                        "No " + seatClass + " seats available!\nBooking cancelled.",
                        "Booking Failed", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public double getDiscountFromFile(String promoCode) {
        try {
            File file = new File("resources/txt_file/promodeals.txt");
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split("\\|"); // [discount, title, description, code]
                if (parts.length == 4) {
                    String codeInFile = parts[3].trim();

                    if (codeInFile.equalsIgnoreCase(promoCode)) {
                        String discountStr = parts[0].replace("%", "").trim();
                        sc.close();
                        return Double.parseDouble(discountStr);
                    }
                }
            }
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void showMyReservations() {
        showMyReservations(false);
    }

    private void showCancelBooking() {
        showMyReservations(true);
    }

    private void showMyReservations(boolean showCancelButton) {
        if (customerLoggedIn.getBookings() == null || customerLoggedIn.getBookings().isEmpty()) {
            JOptionPane.showMessageDialog(customerFrame,
                    "You don't have any bookings yet!",
                    "No Reservations",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JFrame reservationFrame = new JFrame(showCancelButton ? "Cancel Booking" : "My Reservations");
        reservationFrame.setSize(900, 700);
        reservationFrame.setLocationRelativeTo(customerFrame);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(240, 240, 240));

        boolean hasReservations = false;

        for (Booking booking : customerLoggedIn.getBookings()) {
            if (booking.getStatus().equals("Confirmed")) {
                JPanel ticketCard = createTicketCardWithCancel(booking, reservationFrame, showCancelButton);
                mainPanel.add(ticketCard);
                mainPanel.add(Box.createVerticalStrut(20));
                hasReservations = true;
            }
        }

        if (!hasReservations) {
            JOptionPane.showMessageDialog(customerFrame,
                    "You don't have any active reservations!",
                    "No Reservations",
                    JOptionPane.INFORMATION_MESSAGE);
            reservationFrame.dispose();
            return;
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);

        JButton closeBtn = new JButton("Close");
        closeBtn.setBackground(new Color(70, 130, 180));
        closeBtn.setForeground(Color.WHITE);
        closeBtn.setFont(new Font("Arial", Font.BOLD, 16));
        closeBtn.setFocusPainted(false);
        closeBtn.addActionListener(e -> reservationFrame.dispose());
        bottomPanel.add(closeBtn);

        reservationFrame.add(scrollPane, BorderLayout.CENTER);
        reservationFrame.add(bottomPanel, BorderLayout.SOUTH);
        reservationFrame.setVisible(true);
    }

    private JPanel createTicketCardWithCancel(Booking booking, JFrame parentFrame, boolean showCancelButton) {
        Flight f = booking.getFlight();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");

        JPanel ticketCard = new JPanel();
        ticketCard.setLayout(new BorderLayout(0, 0));
        ticketCard.setBackground(Color.WHITE);
        ticketCard.setMaximumSize(new Dimension(850, 500));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(41, 98, 255));

        JPanel headerLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 12));
        headerLeft.setBackground(new Color(41, 98, 255));
        JLabel headerLabel = new JLabel("Booking Details");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerLabel.setForeground(Color.WHITE);
        headerLeft.add(headerLabel);

        JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 8));
        headerRight.setBackground(new Color(41, 98, 255));
        JLabel statusBadge = new JLabel("  " + booking.getStatus() + "  ");
        statusBadge.setFont(new Font("Segoe UI", Font.BOLD, 12));
        if (booking.getStatus().equals("Confirmed"))
            statusBadge.setForeground(Color.WHITE);
        else
            statusBadge.setForeground(Color.RED);
        statusBadge.setOpaque(true);
        statusBadge.setBackground(new Color(34, 197, 94));
        headerRight.add(statusBadge);

        headerPanel.add(headerLeft, BorderLayout.WEST);
        headerPanel.add(headerRight, BorderLayout.EAST);

        ticketCard.add(headerPanel, BorderLayout.NORTH);

        // Content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);

        contentPanel.add(Box.createVerticalStrut(20));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);

        infoPanel.add(Box.createHorizontalStrut(25));
        infoPanel.add(createSectionTitle("Booking Information"));
        infoPanel.add(createDetailRow("Booking ID", booking.getBookingId()));
        infoPanel.add(createDetailRow("Booking Date", booking.getFormattedBookingDate()));
        infoPanel.add(Box.createVerticalStrut(15));

        infoPanel.add(createSectionTitle("Flight Information"));
        infoPanel.add(createDetailRow("Flight Number", f.getFlightNo()));
        infoPanel.add(createDetailRow("Flight Type", f.getFlightType()));
        infoPanel.add(createDetailRow("Route", f.getOrigin() + " → " + f.getDestination()));
        infoPanel.add(createDetailRow("Departure",
                dateFormat.format(f.getDepartureTime()) + " at " + timeFormat.format(f.getDepartureTime())));
        infoPanel.add(createDetailRow("Arrival",
                dateFormat.format(f.getArrivalTime()) + " at " + timeFormat.format(f.getArrivalTime())));
        infoPanel.add(Box.createVerticalStrut(15));

        infoPanel.add(createSectionTitle("Seat & Fare Information"));
        infoPanel.add(createDetailRow("Seat Number", booking.getSeat().getSeatNumber()));
        infoPanel.add(createDetailRow("Seat Class", booking.getSeat().getSeatClass()));
        infoPanel.add(createDetailRow("Total Fare", "PKR " + String.format("%.2f", booking.getTotalFare())));
        infoPanel.add(Box.createVerticalStrut(15));

        infoPanel.add(createSectionTitle("Payment Information"));
        if (booking.getPayment() != null) {
            Payment payment = booking.getPayment();
            infoPanel.add(createDetailRow("Payment Status", payment.getStatus()));
            infoPanel.add(createDetailRow("Payment Method", payment.getPaymentMethod()));
            infoPanel.add(createDetailRow("Payment Date", payment.getFormattedPaymentDate()));
        } else {
            infoPanel.add(createDetailRow("Payment Status", "Not Paid"));
        }
        infoPanel.add(Box.createHorizontalStrut(25));

        contentPanel.add(infoPanel);
        contentPanel.add(Box.createVerticalStrut(20));

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        ticketCard.add(scrollPane, BorderLayout.CENTER);

        // Footer
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(new Color(248, 250, 252));

        if (showCancelButton) {
            JButton cancelBtn = new JButton("Cancel This Booking");
            cancelBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            cancelBtn.setForeground(Color.WHITE);
            cancelBtn.setBackground(new Color(220, 38, 38));
            cancelBtn.setFocusPainted(false);

            cancelBtn.addActionListener(e -> {
                if (!booking.canCancel()) {
                    JOptionPane.showMessageDialog(parentFrame,
                            "Cannot cancel! Cancellations are only allowed 24 hours before departure.",
                            "Cancellation Not Allowed",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(parentFrame,
                        "Are you sure you want to cancel this booking?\n" +
                                "Booking ID: " + booking.getBookingId() + "\n" +
                                "Refund Amount: PKR " + String.format("%.2f", booking.getTotalFare()),
                        "Confirm Cancellation",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    if (customerLoggedIn.cancelBooking(booking.getBookingId())) {
                        JOptionPane.showMessageDialog(parentFrame,
                                "Booking cancelled successfully!\n" +
                                        "Refund of PKR " + String.format("%.2f", booking.getTotalFare())
                                        + " will be processed within 7-10 business days.",
                                "Cancellation Successful",
                                JOptionPane.INFORMATION_MESSAGE);
                        parentFrame.dispose();
                    }
                }
            });

            footerPanel.add(cancelBtn);
        } else {
            JLabel footerLabel = new JLabel("Thank you for choosing SkyBlue Airlines");
            footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            footerLabel.setForeground(new Color(100, 116, 139));
            footerPanel.add(footerLabel);
        }

        ticketCard.add(footerPanel, BorderLayout.SOUTH);

        return ticketCard;
    }

    private JPanel createTicketCard(Booking booking) {
        return createTicketCardWithCancel(booking, null, false);
    }

    // Helper method for section titles
    private JLabel createSectionTitle(String title) {
        JLabel label = new JLabel(title);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(30, 41, 59));
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    // Helper method for detail rows
    private JPanel createDetailRow(String label, String value) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(Color.WHITE);
        row.setMaximumSize(new Dimension(800, 30));
        row.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblLabel.setForeground(new Color(100, 116, 139));
        lblLabel.setPreferredSize(new Dimension(180, 25));

        JLabel lblValue = new JLabel(value != null ? value : "N/A");
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblValue.setForeground(new Color(15, 23, 42));

        row.add(lblLabel, BorderLayout.WEST);
        row.add(lblValue, BorderLayout.CENTER);

        return row;
    }

    public void showMyTicket() {
        if (customerLoggedIn.getBookings() == null || customerLoggedIn.getBookings().isEmpty()) {
            JOptionPane.showMessageDialog(customerFrame,
                    "You don't have any bookings yet!\nPlease book a flight first.",
                    "No Tickets Found",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JFrame ticketFrame = new JFrame("My Tickets");
        ticketFrame.setSize(900, 700);
        ticketFrame.setLocationRelativeTo(customerFrame);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(240, 240, 240));

        boolean hasConfirmedTickets = false;

        for (Booking booking : customerLoggedIn.getBookings()) {
            if (!booking.getStatus().equals("Confirmed"))
                continue;
            JPanel ticketCard = createAirlineTicket(booking);
            mainPanel.add(ticketCard);
            mainPanel.add(Box.createVerticalStrut(20));
            hasConfirmedTickets = true;
        }

        if (!hasConfirmedTickets) {
            JOptionPane.showMessageDialog(customerFrame,
                    "You don't have any confirmed tickets!\nAll bookings are either cancelled or completed.",
                    "No Active Tickets",
                    JOptionPane.INFORMATION_MESSAGE);
            ticketFrame.dispose();
            return;
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);

        JButton closeBtn = new JButton("Close");
        closeBtn.setBackground(new Color(70, 130, 180));
        closeBtn.setForeground(Color.WHITE);
        closeBtn.setFont(new Font("Arial", Font.BOLD, 16));
        closeBtn.setFocusPainted(false);
        closeBtn.addActionListener(e -> ticketFrame.dispose());
        bottomPanel.add(closeBtn);

        ticketFrame.add(scrollPane, BorderLayout.CENTER);
        ticketFrame.add(bottomPanel, BorderLayout.SOUTH);
        ticketFrame.setVisible(true);
    }

    private JPanel createAirlineTicket(Booking booking) {
        Flight f = booking.getFlight();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        JPanel ticketCard = new JPanel();
        ticketCard.setLayout(new BorderLayout());
        ticketCard.setBackground(Color.WHITE);
        ticketCard.setMaximumSize(new Dimension(850, 220));
        ticketCard.setBorder(BorderFactory.createLineBorder(new Color(25, 65, 115), 2));

        // TOP HEADER
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(new Color(25, 65, 115));
        JLabel headerLabel = new JLabel("✈ AIRLINE TICKET");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);

        // MAIN CONTENT AREA
        JPanel contentPanel = new JPanel(new GridLayout(3, 4, 15, 10));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // Row 1
        contentPanel.add(createTicketField("NAME OF PASSENGER", customerLoggedIn.getName()));
        contentPanel.add(createTicketField("DATE", dateFormat.format(f.getDepartureTime())));
        contentPanel.add(createTicketField("TIME", timeFormat.format(f.getDepartureTime())));
        contentPanel.add(createTicketField("SEAT", booking.getSeat().getSeatNumber()));

        // Row 2
        contentPanel.add(createTicketField("CLASS", booking.getSeat().getSeatClass()));
        contentPanel.add(createTicketField("GATE", f.getGateNumber() != null ? f.getGateNumber() : "TBA"));
        contentPanel.add(createTicketField("FLIGHT", f.getFlightNo()));
        contentPanel.add(createTicketField("BOOKING ID", booking.getBookingId()));

        // Row 3
        contentPanel.add(createTicketField("FROM", f.getOrigin()));
        contentPanel.add(createTicketField("DESTINATION", f.getDestination()));
        contentPanel.add(createTicketField("FARE", "PKR " + String.format("%.0f", booking.getTotalFare())));
        contentPanel.add(createTicketField("STATUS", booking.getStatus()));

        ticketCard.add(headerPanel, BorderLayout.NORTH);
        ticketCard.add(contentPanel, BorderLayout.CENTER);

        return ticketCard;
    }

    private JPanel createTicketField(String label, String value) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        lblLabel.setForeground(Color.GRAY);

        JLabel lblValue = new JLabel(value != null ? value : "N/A");
        lblValue.setFont(new Font("Arial", Font.BOLD, 14));
        lblValue.setForeground(Color.BLACK);

        panel.add(lblLabel);
        panel.add(lblValue);

        return panel;
    }

    private void showPaymentStatus() {
        if (customerLoggedIn.getBookings() == null || customerLoggedIn.getBookings().isEmpty()) {
            JOptionPane.showMessageDialog(customerFrame,
                    "You don't have any bookings yet!",
                    "No Payment Records",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JFrame paymentStatusFrame = new JFrame("Payment Status");
        paymentStatusFrame.setSize(900, 700);
        paymentStatusFrame.setLocationRelativeTo(customerFrame);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(240, 240, 240));

        for (Booking booking : customerLoggedIn.getBookings()) {
            JPanel paymentCard = createPaymentStatusCard(booking);
            mainPanel.add(paymentCard);
            mainPanel.add(Box.createVerticalStrut(15));
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);

        JButton closeBtn = new JButton("Close");
        closeBtn.setBackground(new Color(70, 130, 180));
        closeBtn.setForeground(Color.WHITE);
        closeBtn.setFont(new Font("Arial", Font.BOLD, 16));
        closeBtn.setFocusPainted(false);
        closeBtn.addActionListener(e -> paymentStatusFrame.dispose());
        bottomPanel.add(closeBtn);

        paymentStatusFrame.add(scrollPane, BorderLayout.CENTER);
        paymentStatusFrame.add(bottomPanel, BorderLayout.SOUTH);
        paymentStatusFrame.setVisible(true);
    }

    private JPanel createPaymentStatusCard(Booking booking) {
        Flight f = booking.getFlight();
        Payment payment = booking.getPayment();

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setMaximumSize(new Dimension(850, 220));
        card.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2));

        JLabel titleLabel = new JLabel("  PAYMENT INFORMATION");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(70, 130, 180));
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(10));

        card.add(createInfoRow("Booking ID:", booking.getBookingId()));
        card.add(createInfoRow("Origin:", f.getOrigin()));
        card.add(createInfoRow("Destination:", f.getDestination()));

        if (payment != null) {
            card.add(createInfoRow("Payment Status:", payment.getStatus()));
            card.add(createInfoRow("Payment Method:", payment.getPaymentMethod()));
            card.add(createInfoRow("Amount Paid:", "PKR " + payment.getAmount()));
            card.add(createInfoRow("Payment Date:", payment.getFormattedPaymentDate()));
        } else {
            card.add(createInfoRow("Payment Status:", "Not Paid"));
            card.add(createInfoRow("Booking Status:", booking.getStatus()));
        }

        return card;
    }

    private void showBookingHistory() {
        if (customerLoggedIn.getBookings() == null || customerLoggedIn.getBookings().isEmpty()) {
            JOptionPane.showMessageDialog(customerFrame,
                    "You don't have any booking history!",
                    "No History",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JFrame historyFrame = new JFrame("Booking History");
        historyFrame.setSize(900, 700);
        historyFrame.setLocationRelativeTo(customerFrame);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(240, 240, 240));

        // Show ALL bookings (Confirmed, Cancelled, Completed)
        for (Booking booking : customerLoggedIn.getBookings()) {
            JPanel ticketCard = createTicketCard(booking);
            mainPanel.add(ticketCard);
            mainPanel.add(Box.createVerticalStrut(20));
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);

        JButton closeBtn = new JButton("Close");
        closeBtn.setBackground(new Color(70, 130, 180));
        closeBtn.setForeground(Color.WHITE);
        closeBtn.setFont(new Font("Arial", Font.BOLD, 16));
        closeBtn.setFocusPainted(false);
        closeBtn.addActionListener(e -> historyFrame.dispose());
        bottomPanel.add(closeBtn);

        historyFrame.add(scrollPane, BorderLayout.CENTER);
        historyFrame.add(bottomPanel, BorderLayout.SOUTH);
        historyFrame.setVisible(true);
    }

    private void showCheckInOptions() {

        customerLoggedIn.reloadBookings();
        ArrayList<Booking> eligibleBookings = customerLoggedIn.getAvailableCheckInBookings();

        if (eligibleBookings == null || eligibleBookings.isEmpty()) {
            JOptionPane.showMessageDialog(customerFrame,
                    "No flights are currently available for online check-in.\n" +
                            "Check-in opens 4 hours before departure and closes 1 hour before departure.",
                    "Check-In Not Available", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JFrame checkInFrame = new JFrame("Online Check-In");
        checkInFrame.setSize(650, 500);
        checkInFrame.setLocationRelativeTo(customerFrame);
        checkInFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 246, 255));

        // Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Select Flight to Check In");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Flights
        JPanel flightsListPanel = new JPanel();
        flightsListPanel.setLayout(new BoxLayout(flightsListPanel, BoxLayout.Y_AXIS));
        flightsListPanel.setBackground(new Color(240, 246, 255));
        flightsListPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        JScrollPane scrollPane = new JScrollPane(flightsListPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null); // Keep it clean

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy 'at' hh:mm a");

        for (Booking booking : eligibleBookings) {
            Flight f = booking.getFlight();

            JPanel flightRow = new JPanel(new BorderLayout(15, 0));
            flightRow.setMaximumSize(new Dimension(600, 70));
            flightRow.setAlignmentX(Component.CENTER_ALIGNMENT);
            flightRow.setBackground(Color.WHITE);
            flightRow.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(5, 0, 5, 0),
                    BorderFactory.createLineBorder(new Color(200, 200, 220))));

            JPanel detailsPanel = new JPanel();
            detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
            detailsPanel.setBackground(Color.WHITE);
            detailsPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            JLabel flightRoute = new JLabel(
                    f.getOrigin() + " ➝ " + f.getDestination() + " (Flight: " + f.getFlightNo() + ")");
            flightRoute.setFont(new Font("Arial", Font.BOLD, 14));
            flightRoute.setForeground(new Color(0, 51, 102));

            JLabel timeInfo = new JLabel(
                    "ID: " + booking.getBookingId() + " | Departs: " + sdf.format(f.getDepartureTime()));
            timeInfo.setFont(new Font("Arial", Font.PLAIN, 12));

            detailsPanel.add(flightRoute);
            detailsPanel.add(timeInfo);

            flightRow.add(detailsPanel, BorderLayout.CENTER);
            JButton checkInButton = new JButton("Check In Now");
            checkInButton.setFont(new Font("Arial", Font.BOLD, 14));
            checkInButton.setBackground(new Color(0, 153, 51));
            checkInButton.setForeground(Color.WHITE);
            checkInButton.setFocusPainted(false);
            checkInButton.setPreferredSize(new Dimension(150, 40));

            JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonWrapper.setBackground(Color.WHITE);
            buttonWrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
            buttonWrapper.add(checkInButton);

            flightRow.add(buttonWrapper, BorderLayout.EAST);

            // Add ActionListener
            checkInButton.addActionListener(e -> {
                String result = customerLoggedIn.checkInFlight(booking.getBookingId());

                if ("checked in successfully".equals(result)) {
                    JOptionPane.showMessageDialog(checkInFrame,
                            "Check-In successful for Flight " + f.getFlightNo() + "!\n\n" +
                                    "Your **Booking Status** has been updated to **'Completed'**.",
                            "Check-In Success", JOptionPane.INFORMATION_MESSAGE);

                    checkInFrame.dispose();
                    showCheckInOptions();
                } else {
                    JOptionPane.showMessageDialog(checkInFrame,
                            "Check-In Failed: " + result,
                            "Check-In Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            flightsListPanel.add(flightRow);
            flightsListPanel.add(Box.createVerticalStrut(10));
        }

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        checkInFrame.add(mainPanel);
        checkInFrame.setVisible(true);
    }

    public Customer getLoggedInCustomer() {
        return this.customerLoggedIn;
    }


    public JFrame getFrame() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getFrame'");
    }

}
